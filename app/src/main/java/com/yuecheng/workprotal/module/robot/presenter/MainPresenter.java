package com.yuecheng.workprotal.module.robot.presenter;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.TextUnderstanderListener;
import com.iflytek.cloud.UnderstanderResult;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.yuecheng.workprotal.R;
import com.yuecheng.workprotal.module.robot.OpenH5Activity;
import com.yuecheng.workprotal.MainApplication;
import com.yuecheng.workprotal.module.robot.bean.DateBean;
import com.yuecheng.workprotal.module.robot.bean.LocationBean;
import com.yuecheng.workprotal.module.robot.bean.MusicBean;
import com.yuecheng.workprotal.module.robot.bean.SemanticBean;
import com.yuecheng.workprotal.module.robot.bean.SemanticComprehensionResult;
import com.yuecheng.workprotal.module.robot.bean.SlotsBean;
import com.yuecheng.workprotal.module.robot.bean.TalkBean;
import com.yuecheng.workprotal.module.robot.bean.WeatherBean;
import com.yuecheng.workprotal.module.robot.adapter.TalkListAdapter;
import com.yuecheng.workprotal.module.robot.model.IMainModel;
import com.yuecheng.workprotal.module.robot.model.MainModel;
import com.yuecheng.workprotal.module.robot.service.MusicService;
import com.yuecheng.workprotal.module.robot.view.IMainView;
import com.yuecheng.workprotal.module.robot.view.VoiceActivity;
import com.yuecheng.workprotal.utils.DeviceUtils;
import com.yuecheng.workprotal.utils.LogUtils;
import com.yuecheng.workprotal.utils.StringUtils;
import com.yuecheng.workprotal.module.robot.action.CallAction;
import com.yuecheng.workprotal.module.robot.action.CallView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class MainPresenter implements IMainPresenter {
    private static final String APP = "app";
    private static final String LAUNCH = "LAUNCH";
    private static final String ANSWER = "ANSWER";
    private static final String PLAY = "PLAY";
    private static final String MUSIC = "music";
    private static final String WEATHER = "weather";
    private static final String QUERY = "QUERY";
    private static final String OPEN = "OPEN";
    private static final String CALL = "CALL";
    private static final String VIEW = "VIEW";
    private static final String TELEPHONE = "telephone";
    private static final String MESSAGE = "message";
    private static final String WEBSITE = "website";
    private static final String CLOSE = "CLOSE";
    private static final String TVCONTROL = "tvControl";

    private static IMainView mIMainView;
    private static IMainModel mIMainModel;
    private static Handler mHandler;
    /**
     * 存储听写结果
     */
    private HashMap<String, String> mIatResults = new LinkedHashMap<>();
    private static List<TalkBean> mTalkBeanList = new ArrayList<>();

    public MainPresenter(IMainView IMainView) {
        mIMainView = IMainView;
        mHandler = new Handler(Looper.getMainLooper());
        mIMainModel = new MainModel();
        initData();
    }
    private void initData() {
        //show default data
        TalkBean talkBean = new TalkBean(((Activity) mIMainView).getResources().getString(R.string.talk_first),
                System.currentTimeMillis(), TalkListAdapter.VIEW_TYPE_ROBOT_CHAT);
        mTalkBeanList.add(talkBean);
        mIMainView.updateList(mTalkBeanList);
    }



    @Override
    public void startVoiceRobot() {
        //语音听写
        mIMainModel.recognizeVoice((Activity) mIMainView, new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean isLast) {
                onRecognizerSuccess(recognizerResult, isLast);
            }

            @Override
            public void onError(SpeechError speechError) {
                onRecognizerError(speechError);
            }
        });
    }

    public void understandText(String text) {
        displayMessage(text);//展示发送的消息
        //语义理解
        mIMainModel.understandText((Activity) mIMainView, text, new TextUnderstanderListener() {
            @Override
            public void onResult(UnderstanderResult understanderResult) {
                onTextUnderstanderSuccess(understanderResult);
            }

            @Override
            public void onError(SpeechError speechError) {
                onTextUnderstanderError(speechError);
            }
        });
    }

    private void onRecognizerSuccess(RecognizerResult recognizerResult, boolean isLast) {
        if (recognizerResult == null) return;
        if (isLast) return;
        StringBuffer text = new StringBuffer();
        try {
            JSONObject joResult = new JSONObject(recognizerResult.getResultString());
            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                // 转写结果词，默认使用第一个结果
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                JSONObject obj = items.getJSONObject(0);
                text.append(obj.getString("w"));
            }


            String sn = null;
            // 读取json结果中的sn字段
            sn = joResult.optString("sn");

            mIatResults.put(sn, text.toString());

            StringBuffer resultBuffer = new StringBuffer();
            for (String key : mIatResults.keySet()) {
                resultBuffer.append(mIatResults.get(key));
            }
            final String question = resultBuffer.toString();
            LogUtils.d("语音听写的识别结果：" + question);
            //进行语义理解
            understandText(question);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void displayMessage(final String question) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                TalkBean talkBean = new TalkBean(question,
                        System.currentTimeMillis(), TalkListAdapter.VIEW_TYPE_USER);
                mTalkBeanList.add(talkBean);
                mIMainView.updateList(mTalkBeanList);
            }
        });
    }

    private void onRecognizerError(SpeechError speechError) {
        LogUtils.e("语音听写失败，错误码=" + speechError.getErrorCode());
    }

    private void onTextUnderstanderSuccess(UnderstanderResult understanderResult) {
        if (understanderResult == null) return;
        try {
            //根据语音生成一个json串，之后在获取其中的字段  打开本地应用{"semantic":{"slots":{"name":"qq"}},"rc":0,"operation":"LAUNCH","service":"app","text":"打开QQ"}
           // 提供外部链接{"semantic":{"slots":{"name":"百度","url":"http:\/\/www.baidu.com"}},"rc":0,"operation":"OPEN","service":"website","text":"打开百度"}
            //既有应用也提供外部链接{"semantic":{"slots":{"name":"淘宝"}},"rc":0,"operation":"LAUNCH","service":"app","moreResults":[{"semantic":{"slots":{"name":"淘宝","url":"http:\/\/www.taobao.com\/"}},"rc":0,"operation":"OPEN","service":"website","text":"打开淘宝"}],"text":"打开淘宝"}
            String resultString = understanderResult.getResultString();
            SemanticComprehensionResult semanticComprehensionResult = new Gson().fromJson(resultString, SemanticComprehensionResult.class);

            if (semanticComprehensionResult.getRc() == 0) {
                //success
                String service = semanticComprehensionResult.getService();
                String operation = semanticComprehensionResult.getOperation();
                JSONObject jsonObject = new JSONObject(resultString);
                JSONObject data = jsonObject.optJSONObject("data");
                String result = "";
                if (data != null)
                    result = data.optString("result");

                if (APP.equalsIgnoreCase(service) && LAUNCH.equalsIgnoreCase(operation)) {
                    //打开应用
                    openAppByLauncher(semanticComprehensionResult.getSemantic());
                } else if (WEBSITE.equalsIgnoreCase(service) && OPEN.equalsIgnoreCase(operation)) {
                    //百度
                    String url = semanticComprehensionResult.getSemantic().getSlots().getUrl();
                    String name = semanticComprehensionResult.getSemantic().getSlots().getName();
//                    Intent intent = new Intent(MainApplication.getApplication(), OpenH5Activity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
//                    intent.putExtra("url",url);
//                    intent.putExtra("name",name);
//                    MainApplication.getApplication().startActivity(intent);
                    TalkBean talkBean = new TalkBean(url,
                            System.currentTimeMillis(), TalkListAdapter.VIEW_TYPE_ROBOT_WEB);
                    mTalkBeanList.add(talkBean);
                    mIMainView.updateList(mTalkBeanList);

                }else if (ANSWER.equalsIgnoreCase(operation)) {
                    //聊天
                    responseAnswer(semanticComprehensionResult.getAnswer().getText());
                }else if (TELEPHONE.equalsIgnoreCase(service) && CALL.equalsIgnoreCase(operation)) {
                    String name = "";
                    String code = "";
                    if(semanticComprehensionResult.getSemantic().getSlots()!=null){
                        //打电话
                        name = semanticComprehensionResult.getSemantic().getSlots().getName();
                        code = semanticComprehensionResult.getSemantic().getSlots().getCode();
                    }

                    CallAction callAction = new CallAction(name, code, MainApplication.getApplication());//目前可根据名字或电话号码拨打电话
                    callAction.start();
                }else if (MESSAGE.equalsIgnoreCase(service) && VIEW.equalsIgnoreCase(operation)) {
                    //打电话界面
//                    CallView callview = new CallView(MainApplication.getApplication());
//                    callview.start();
                    //打开消息
                    responseAnswer("正在打开消息界面...");
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setType("vnd.android-dir/mms-sms");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                    MainApplication.getApplication().startActivity(intent);
                }else if (MUSIC.equalsIgnoreCase(service) && PLAY.equalsIgnoreCase(operation)) {
                    //播放音乐
                    ArrayList<MusicBean> musicBeenArrayList = new Gson().fromJson(result, new TypeToken<ArrayList<MusicBean>>() {
                    }.getType());
                    playMusic(musicBeenArrayList);
                } else if (WEATHER.equalsIgnoreCase(service) && QUERY.equalsIgnoreCase(operation)) {
                    //查询天气
                    ArrayList<WeatherBean> weatherBeanArrayList = new Gson().fromJson(result, new TypeToken<ArrayList<WeatherBean>>() {
                    }.getType());
                    queryWeather(semanticComprehensionResult.getSemantic(), weatherBeanArrayList);
                }else if (TVCONTROL.equalsIgnoreCase(service) && CLOSE.equalsIgnoreCase(operation)) {
                    //退出
                    VoiceActivity.instance.finish();
                } else {
                    //解析失败
                    String answerText = ((Activity) mIMainView).getResources().getString(R.string.default_voice_answer);
                    responseAnswer(answerText);
                }
            } else {
                //解析失败
                String answerText = ((Activity) mIMainView).getResources().getString(R.string.default_voice_answer);
                responseAnswer(answerText);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //解析失败
            String answerText = ((Activity) mIMainView).getResources().getString(R.string.default_voice_answer);
            responseAnswer(answerText);
        }

    }

    private void onTextUnderstanderError(SpeechError speechError) {
        LogUtils.e("语义理解失败，错误码=" + speechError.getErrorCode());
    }

    private void openAppByLauncher(SemanticBean semanticBean) {
        String answerText = "";
        try {
            MainApplication context = MainApplication.getApplication();
            SlotsBean slotsBean = semanticBean.getSlots();
            String appName = slotsBean.getName();
            List<String> list = DeviceUtils.getPackNameByAppName(appName);
            String packName = list.get(0);
            String realAppName = list.get(1);

            PackageManager pm = context.getPackageManager();
            //通过包名来查询应用
            Intent intent = pm.getLaunchIntentForPackage(packName);
            if (intent == null) {
                answerText = "你还没安装" + appName;
            } else {
                context.startActivity(intent);
                answerText = "正在打开" + realAppName;
            }
        } catch (Exception e) {
        }
        responseAnswer(answerText);
    }

    private void playMusic(ArrayList<MusicBean> musicBeenArrayList) {
        if (musicBeenArrayList == null) {
            String answerText = ((Activity) mIMainView).getResources().getString(R.string.dont_find_music);
            responseAnswer(answerText);
            return;
        }
        MusicBean musicBean = musicBeenArrayList.get(0);
        MusicService.startService((Activity) mIMainView, true, musicBean.getDownloadUrl());
        String answerText = "正在播放歌曲：" + musicBean.getName() + "，歌手：" + musicBean.getSinger();
        responseAnswer(answerText);
    }

    private void queryWeather(SemanticBean semanticBean, List<WeatherBean> list) {
        try {
            SlotsBean slotsBean = semanticBean.getSlots();
            String sightspot = slotsBean.getSightspot();
            LocationBean locationBean = slotsBean.getLocation();
            DateBean dateBean = slotsBean.getDatetime();
            String date = dateBean.getDate();
            String dateOrig = dateBean.getDateOrig();

            StringBuffer sb = new StringBuffer();

            if (!TextUtils.isEmpty(locationBean.getCityAddr())) {
                sb.append(locationBean.getCityAddr());
            }

            if (!TextUtils.isEmpty(sightspot)) {
                sb.append(sightspot);
            }

            if (!TextUtils.isEmpty(dateOrig)) {
                sb.append(dateOrig);
            }
            sb.append("：");
            for (WeatherBean weatherBean : list) {
                sb.append(weatherBean.getDate() + " , ");
                if (!StringUtils.isEmpty(weatherBean.getWeather()))
                    sb.append("天气：" + weatherBean.getWeather() + " , ");
                if (!StringUtils.isEmpty(weatherBean.getTempRange()))
                    sb.append("温度：" + weatherBean.getTempRange() + " , ");
                if (!StringUtils.isEmpty(weatherBean.getAirQuality()))
                    sb.append("空气质量：" + weatherBean.getAirQuality() + " , ");
                if (!StringUtils.isEmpty(weatherBean.getWind()))
                    sb.append("风向：" + weatherBean.getWind() + " , ");
                if (!StringUtils.isEmpty(weatherBean.getWindLevel()))
                    sb.append("风级：" + weatherBean.getWindLevel() + "级;\n");
            }

            responseAnswer(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
            responseAnswer("查询天气失败，请稍后再试~");
        }
    }

    public static void responseAnswer(final String answerText) {
        if (!TextUtils.isEmpty(answerText)) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    TalkBean talkBean = new TalkBean(answerText,
                            System.currentTimeMillis(), TalkListAdapter.VIEW_TYPE_ROBOT_CHAT);
                    mTalkBeanList.add(talkBean);
                    mIMainView.updateList(mTalkBeanList);
                }
            });

            //播放答案
            mIMainModel.synthesizeVoice((Activity) mIMainView, answerText, new SynthesizerListener() {

                @Override
                public void onSpeakBegin() {
                    LogUtils.d("开始播放答案");
                }

                @Override
                public void onSpeakPaused() {
                    LogUtils.d("暂停播放答案");
                }

                @Override
                public void onSpeakResumed() {
                    LogUtils.d("继续播放答案");
                }

                @Override
                public void onBufferProgress(int percent, int beginPos, int endPos,
                                             String info) {
                    // 合成进度
                    LogUtils.d("合成答案进度 = " + percent);
                }

                @Override
                public void onSpeakProgress(int percent, int beginPos, int endPos) {
                    // 播放进度
                    LogUtils.d("播放答案进度 = " + percent);
                }

                @Override
                public void onCompleted(SpeechError error) {
                    if (error == null) {
                        LogUtils.d("播放答案完成");
                    } else if (error != null) {
                        LogUtils.e(error.getPlainDescription(true));
                    }
                }

                @Override
                public void onEvent(int eventType, int arg1, int arg2, Bundle bundle) {
                    // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
                    // 若使用本地能力，会话id为null
                    //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
                    //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
                    //		Log.d(TAG, "session id =" + sid);
                    //	}
                }
            });
        }
    }
}
