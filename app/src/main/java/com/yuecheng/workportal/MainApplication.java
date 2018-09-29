package com.yuecheng.workportal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.widget.Toast;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.yuecheng.workportal.bean.LoginUser;
import com.yuecheng.workportal.module.contacts.view.ShareDialog;
import com.yuecheng.workportal.module.mycenter.presenter.UserPresenter;
import com.yuecheng.workportal.receive.MyConnectionStatusListener;
import com.yuecheng.workportal.receive.MyReceiveMessageListener;
import com.yuecheng.workportal.utils.AndroidUtil;
import com.yuecheng.workportal.utils.SharePreferenceUtil;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import io.rong.imkit.RongIM;
import okhttp3.OkHttpClient;

import static com.yuecheng.workportal.InvalidateActivity.INVALIDATE_MESSAGE;

public class MainApplication extends MultiDexApplication {
    private static MainApplication app;
    public List<Activity> activityList = new LinkedList<Activity>();

    //当天登陆用户信息
    private LoginUser loginUser;
    SharePreferenceUtil spUtil = null;
    UserPresenter userPresenter = null;
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        spUtil = new SharePreferenceUtil(this);
        userPresenter =  new UserPresenter(app);
        initXunfei();
        initOkGo();
        initRongIM();
        UMConfigure.init(this,"5bad9649f1f556df24000098"
                ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0
    }


    /**
     * 解决Android studio中方法数超过65536
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    private void initRongIM() {
        /**
         *
         * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
        if (getApplicationInfo().packageName.equals(AndroidUtil.getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(AndroidUtil.getCurProcessName(getApplicationContext()))) {
            /**
             * IMKit SDK调用第一步 初始化
             */
            RongIM.init(this);
        }
        //消息接受监听
        RongIM.setOnReceiveMessageListener(new MyReceiveMessageListener(this));
        //接状态监听器
        RongIM.setConnectionStatusListener(new MyConnectionStatusListener(this));
    }

    public static MainApplication getApplication() {
        return app;
    }

    //获取当前登陆用户信息
    public LoginUser getLoginUser() {
        if(loginUser==null){
            loginUser = userPresenter.getUser(spUtil.getCurrentUserName());
        }
        return loginUser;
    }

    /**
     * 添加Activity到容器中.
     *
     * @param activity
     * @author 史明松
     * @update 2014年6月26日 上午10:55:40
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }
    /**
     * 重新登陆
     *
     * @author 史明松
     * @update 2014年6月26日 上午10:55:28
     */
    public void relogin() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        spUtil.setCurrentUserName("");
        loginUser = null;//把当前登陆用户置空
        RongIM.getInstance().logout();//断开与融云服务器的连接，并且不再接收 Push 消息
        startActivity(new Intent(app, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
    /**
     * 退出
     *
     * @author 史明松
     * @update 2014年6月26日 上午10:55:28
     */
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        spUtil.setCurrentUserName("");
        loginUser = null;//把当前登陆用户置空
        RongIM.getInstance().logout();//断开与融云服务器的连接，并且不再接收 Push 消息
        android.os.Process.killProcess(android.os.Process.myPid());
    }
    /**
     * 重启进入首页
     */
    public void toIndex() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        startActivity(new Intent(app, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    /**
     * 登陆失效
     */
    public void toInvalidate(String message) {
        startActivity(new Intent(app, InvalidateActivity.class).putExtra(INVALIDATE_MESSAGE,message).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
    /**
     * 登陆失效
     */
    public void toInvalidate(Integer id) {
        startActivity(new Intent(app, InvalidateActivity.class).putExtra(INVALIDATE_MESSAGE,getString(id)).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
    private void initXunfei() {
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5b7287ac");
    }

    private void initOkGo() {
        //---------这里给出的是示例代码,告诉你可以这么传,实际使用的时候,根据需要传,不需要就不传-------------//
        HttpHeaders headers = new HttpHeaders();
        headers.put("commonHeaderKey1", "commonHeaderValue1");    //header不支持中文，不允许有特殊字符
        HttpParams params = new HttpParams();
        params.put("commonParamsKey1", "commonParamsValue1");     //param支持中文,直接传,不要自己编码
        //----------------------------------------------------------------------------------------//

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.INFO);                               //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor);                                 //添加OkGo默认debug日志
        //第三方的开源库，使用通知显示当前请求的log，不过在做文件下载的时候，这个库好像有问题，对文件判断不准确
        //builder.addInterceptor(new ChuckInterceptor(this));

        //超时时间设置，默认60秒
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);   //全局的连接超时时间

        //自动管理cookie（或者叫session的保持），以下几种任选其一就行
        //builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));            //使用sp保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));              //使用数据库保持cookie，如果cookie不过期，则一直有效
        //builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));            //使用内存保持cookie，app退出后，cookie消失

        //https相关设置，使用预埋证书，校验服务端证书（自签名证书）
        HttpsUtils.SSLParams sslParams = null;
        try {
            sslParams = HttpsUtils.getSslSocketFactory(getAssets().open("srca.cer"));
            builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其他统一的配置
        // 详细说明看GitHub文档：https://github.com/jeasonlzy/
        OkGo.getInstance().init(this)                           //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置会使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3) ;                              //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
//                .addCommonHeaders(headers)                      //全局公共头
//                .addCommonParams(params);                       //全局公共参数
    }

    {
        PlatformConfig.setWeixin("wx75af2356bc439f25", "a2840cc69014df6c1d0c33a8145d2c6a");
        PlatformConfig.setQQZone("1107803171", "C9dxemWiTlf7EeaA");
    }

    //分享
    public void myShare(Activity activity) {
        ShareDialog shareDialog = new ShareDialog(activity);
        shareDialog.setClicklistener(new ShareDialog.ClickListenerInterface() {

            @Override
            public void onWXClick() {
                new ShareAction(activity)
                        .setPlatform(SHARE_MEDIA.WEIXIN.toSnsPlatform().mPlatform)//传入平台
                        .withText("hello")//分享内容
                        .setCallback(shareListener)//回调监听器
                        .share();
                shareDialog.dismissDialog();
            }

            @Override
            public void onPYQClick() {
                new ShareAction(activity)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE.toSnsPlatform().mPlatform)//传入平台
                        .withText("hello")//分享内容
                        .setCallback(shareListener)//回调监听器
                        .share();
                shareDialog.dismissDialog();
            }

            @Override
            public void onQQClick() {
                UMImage imagelocal = new UMImage(app, R.mipmap.dbgz);
                imagelocal.setThumb(new UMImage(app, R.mipmap.dbgz));
                new ShareAction(activity)
                        .withMedia(imagelocal)
                        .setPlatform(SHARE_MEDIA.QQ.toSnsPlatform().mPlatform)
                        .setCallback(shareListener).share();
                shareDialog.dismissDialog();
            }
        });
    }
    //分享回调
    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(app, "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(app, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(app, "取消了", Toast.LENGTH_LONG).show();

        }
    };
}
