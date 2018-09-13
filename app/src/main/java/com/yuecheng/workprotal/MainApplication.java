package com.yuecheng.workprotal;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

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
import com.yuecheng.workprotal.bean.LoginUser;
import com.yuecheng.workprotal.module.mycenter.presenter.UserPresenter;
import com.yuecheng.workprotal.module.mycenter.view.LoginActivity;
import com.yuecheng.workprotal.receive.MyReceiveMessageListener;
import com.yuecheng.workprotal.utils.AndroidUtil;
import com.yuecheng.workprotal.utils.SharePreferenceUtil;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import io.rong.imkit.RongIM;
import okhttp3.OkHttpClient;

public class MainApplication extends Application{
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

        RongIM.setOnReceiveMessageListener(new MyReceiveMessageListener());
    }

    public static MainApplication getApplication() {
        return app;
    }

    //获取当前登陆用户信息
    public LoginUser getLoginUser() {
        if(loginUser==null){
            userPresenter.getUser(spUtil.getCurrentUserName());
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
     * 遍历所有Activity并finish.
     *
     * @author 史明松
     * @update 2014年6月26日 上午10:55:28
     */
    public void exit() {
        spUtil.setCurrentUserName(null);
        for (Activity activity : activityList) {
            activity.finish();
        }
        startActivity(new Intent(app, LoginActivity.class));
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
}
