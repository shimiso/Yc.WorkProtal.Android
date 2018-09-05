package com.yuecheng.workprotal.module.robot.presenter;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.TextUnderstanderListener;
import com.iflytek.cloud.UnderstanderResult;
import com.yuecheng.workprotal.R;
import com.yuecheng.workprotal.module.robot.adapter.TalkListAdapter;
import com.yuecheng.workprotal.module.robot.bean.SemanticResult;
import com.yuecheng.workprotal.module.robot.bean.TalkBean;
import com.yuecheng.workprotal.module.robot.handler.AdminHandler;
import com.yuecheng.workprotal.module.robot.handler.ContactsHandler;
import com.yuecheng.workprotal.module.robot.handler.DefaultHandler;
import com.yuecheng.workprotal.module.robot.handler.FinanceHandler;
import com.yuecheng.workprotal.module.robot.handler.HRHandler;
import com.yuecheng.workprotal.module.robot.handler.HintHandler;
import com.yuecheng.workprotal.module.robot.handler.IntentHandler;
import com.yuecheng.workprotal.module.robot.handler.ItServiceHandler;
import com.yuecheng.workprotal.module.robot.handler.LeaveHandler;
import com.yuecheng.workprotal.module.robot.handler.MeetingHandler;
import com.yuecheng.workprotal.module.robot.handler.NoticeHandler;
import com.yuecheng.workprotal.module.robot.handler.ToDoHandler;
import com.yuecheng.workprotal.module.robot.model.IMainModel;
import com.yuecheng.workprotal.module.robot.model.MainModel;
import com.yuecheng.workprotal.module.robot.view.IMainView;
import com.yuecheng.workprotal.utils.LogUtils;
import com.yuecheng.workprotal.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainPresenter implements IMainPresenter {
    private static final String KEY_SEMANTIC = "semantic";
    private static final String KEY_OPERATION = "operation";
    private static final String SLOTS = "slots";
    private static Map<String, Class> handlerMap = new HashMap<>();

    static {
        handlerMap.put("unknown", HintHandler.class);
        //电话/短信
        handlerMap.put("YUECHENG.contacts_zh", ContactsHandler.class);
        handlerMap.put("YUECHENG.contacts_en", ContactsHandler.class);
        //会议
        handlerMap.put("YUECHENG.meeting_zh", MeetingHandler.class);
        handlerMap.put("YUECHENG.meeting_en", MeetingHandler.class);
        //新闻公告
        handlerMap.put("YUECHENG.notice_zh", NoticeHandler.class);
        handlerMap.put("YUECHENG.notice_en", NoticeHandler.class);
        //代办
        handlerMap.put("YUECHENG.todo_list_zh", ToDoHandler.class);
        handlerMap.put("YUECHENG.todo_list_en", ToDoHandler.class);
        //IT服务
        handlerMap.put("YUECHENG.it_service_zh", ItServiceHandler.class);
        handlerMap.put("YUECHENG.it_service_en", ItServiceHandler.class);
        //休假
        handlerMap.put("YUECHENG.leave_zh", LeaveHandler.class);
        handlerMap.put("YUECHENG.leave_en", LeaveHandler.class);
        //HR
        handlerMap.put("YUECHENG.hr_zh", HRHandler.class);
        handlerMap.put("YUECHENG.hr_en", HRHandler.class);
        //行政
        handlerMap.put("YUECHENG.admin_zh", AdminHandler.class);
        handlerMap.put("YUECHENG.abmin_en", AdminHandler.class);
        //财务
        handlerMap.put("YUECHENG.finance_zh", FinanceHandler.class);
        handlerMap.put("YUECHENG.finance_en", FinanceHandler.class);

    }
    private static IMainView mIMainView;
    private static IMainModel mIMainModel;
    private static Handler mHandler;
    private IntentHandler intentHandler;
    /**
     * 存储听写结果
     */
    private HashMap<String, String> mIatResults = new LinkedHashMap<>();
    private static List<TalkBean> mTalkBeanList = new ArrayList<>();
    private static String resultString = null;
    private SemanticResult parsedSemanticResult;

    public MainPresenter(IMainView IMainView) {
        mIMainView = IMainView;
        mHandler = new Handler(Looper.getMainLooper());
        mIMainModel = new MainModel();
        initData();
    }
    private void initData() {
        //show default data
        TalkBean talkBean = new TalkBean(((Activity) mIMainView).getResources().getString(R.string.talk_first),"这个Json是空的！！！",
                System.currentTimeMillis(), TalkListAdapter.VIEW_TYPE_ROBOT_CHAT);
        mTalkBeanList.add(talkBean);
        mIMainView.updateList(mTalkBeanList);
    }

    public void cancelVoiceRobot(){
        mIMainModel.cancelVoiceRobot();
    }

    public void stopVoiceRobot(){
        mIMainModel.stopVoiceRobot();
    }
    @Override
    public void startVoiceRobot() {
        //语音听写
        mIMainModel.startVoiceRobot((Activity) mIMainView, new RecognizerListener() {
            @Override
            public void onVolumeChanged(int i, byte[] bytes) {
                mIMainView.setVoiceChanged(i);
            }

            @Override
            public void onBeginOfSpeech() {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onResult(RecognizerResult recognizerResult, boolean isLast) {
                onRecognizerSuccess(recognizerResult, isLast);
            }

            @Override
            public void onError(SpeechError speechError) {
                LogUtils.e("语音听写失败，错误码=" + speechError.getErrorCode());
                ToastUtil.error((Activity) mIMainView,speechError.getErrorDescription());
                mIMainView.destroyTipView();
            }

            @Override
            public void onEvent(int i, int i1, int i2, Bundle bundle) {

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

                    //根据语义结果的service查找对应的IntentHandler，并实例化
                    Class handlerClass = handlerMap.get(parsedSemanticResult.service);
                    if (handlerClass == null) {
                        handlerClass = DefaultHandler.class;
                    }
                    try {
                        Constructor constructor = handlerClass.getConstructor(SemanticResult.class);
                        intentHandler = (IntentHandler) constructor.newInstance(parsedSemanticResult);
                        responseAnswer(intentHandler.getFormatContent());
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

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
                TalkBean talkBean = new TalkBean(question,resultString,
                        System.currentTimeMillis(), TalkListAdapter.VIEW_TYPE_USER);
                mTalkBeanList.add(talkBean);
                mIMainView.updateList(mTalkBeanList);
            }
        });
    }


    private void onTextUnderstanderSuccess(UnderstanderResult understanderResult) {
        if (understanderResult == null) return;
        JSONObject semanticResult;
        parsedSemanticResult = new SemanticResult();
        try {
            //根据语音生成一个json串，之后在获取其中的字段  打开本地应用{"semantic":{"slots":{"name":"qq"}},"rc":0,"operation":"LAUNCH","service":"app","text":"打开QQ"}
           // 提供外部链接{"semantic":{"slots":{"name":"百度","url":"http:\/\/www.baidu.com"}},"rc":0,"operation":"OPEN","service":"website","text":"打开百度"}
            //既有应用也提供外部链接{"semantic":{"slots":{"name":"淘宝"}},"rc":0,"operation":"LAUNCH","service":"app","moreResults":[{"semantic":{"slots":{"name":"淘宝","url":"http:\/\/www.taobao.com\/"}},"rc":0,"operation":"OPEN","service":"website","text":"打开淘宝"}],"text":"打开淘宝"}
            resultString = understanderResult.getResultString();
            semanticResult = new JSONObject(resultString);
            parsedSemanticResult.rc = semanticResult.optInt("rc");
            LogUtils.i(parsedSemanticResult);
            if (parsedSemanticResult.rc == 4) {

                parsedSemanticResult.service = "unknown";
            } else if(parsedSemanticResult.rc == 1) {

                parsedSemanticResult.service =  semanticResult.optString("service");
                parsedSemanticResult.answer = R.string.grammar_error+"";
            } else if(parsedSemanticResult.rc == 0) {
                parsedSemanticResult.service = semanticResult.optString("service");
                parsedSemanticResult.answer = semanticResult.optJSONObject("answer") == null ?
                        "已为您完成操作" : semanticResult.optJSONObject("answer").optString("text");
                parsedSemanticResult.semantic = semanticResult.optJSONArray(KEY_SEMANTIC) == null ?
                        semanticResult.optJSONObject(KEY_SEMANTIC) :
                        semanticResult.optJSONArray(KEY_SEMANTIC).optJSONObject(0);

                parsedSemanticResult.answer = parsedSemanticResult.answer.replaceAll("\\[[a-zA-Z0-9]{2}\\]", "");
                parsedSemanticResult.data = semanticResult.optJSONObject("data");
            }
        } catch (Exception e) {
            e.printStackTrace();
            //解析失败
//            String answerText = ((Activity) mIMainView).getResources().getString(R.string.default_voice_answer);
//            responseAnswer(answerText);
            parsedSemanticResult.rc = 4;
            parsedSemanticResult.service = "unknown";
        }

    }

    private void onTextUnderstanderError(SpeechError speechError) {
        LogUtils.e("语义理解失败，错误码=" + speechError.getErrorCode());
    }


    public static void responseAnswer(final String answerText ) {
        if (!TextUtils.isEmpty(answerText)) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    TalkBean talkBean = new TalkBean(answerText,resultString,
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
