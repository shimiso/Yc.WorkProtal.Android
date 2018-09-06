package com.yuecheng.workprotal.module.update;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yuecheng.workprotal.callback.DialogCallback;
import com.yuecheng.workprotal.common.UrlConstant;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 类的说明：版本更新
 * 作者：shims
 * 创建时间：2016/2/26 0026 11:11
 */
public class CheckVersionPresenter {

    private Activity activity;

    public CheckVersionPresenter(Activity activity) {
        this.activity = activity;
    }

    public void checkVersion(final int versionCode, final CheckVersionListener checkVersionListener) {
//        OkGo.<String>get(UrlConstant.GET_NEW_VERSION)//
        OkGo.<String>post(UrlConstant.ADDRESSSTAFFQUEY)//
                .tag(this)//
//                .headers("header1", "headerValue1")//
//                .params("param1", "paramValue1")//
                .params("staffId", 11)//
               .execute(new DialogCallback<String>(activity) {
                   @Override
                   public void onSuccess(Response<String> result) {

                       Version version = new Version();
                       version.setApkUrl("http://knowapp.b0.upaiyun.com/hz/knowbox_teacher_3550.apk");
                       version.setUpdateInfo("发现一个新版本");
                       version.setForcedUpdate(false);
                       checkVersionListener.updateNewVersion(version);
                       /*JSONObject json = null;
                       JSONObject data = null;
                       boolean success = false;
                       try {
                           json = new JSONObject(result.body());
                           data = json.getJSONObject("result");
                           success = json.getBoolean("success");
                           if (success) {
                               Gson gson = new Gson();
                               Version version = gson.fromJson(data.toString(), new TypeToken<Version>() {}.getType());
                               //如果当前版本不是最新就打开更新
                               if (version.getVersionCode() > versionCode) {
                                   checkVersionListener.updateNewVersion(version);
                               } else {//如果已经是最新就跳转到向导页或自动登录进主页
                                   checkVersionListener.toNext();
                               }
                           } else {
                               checkVersionListener.toNext();
                           }
                       } catch (JSONException e) {
                           e.printStackTrace();
                           checkVersionListener.toNext();
                       }*/
                   }

                   @Override
                   public void onError(Response<String> response){
                       checkVersionListener.onError("发生未知异常");
                   }
               });
    }

    /**
     * 校验版本监听.
     *
     * @author 史明松
     */
    public interface CheckVersionListener {
        /**
         * 跳转到引导或登录界面.
         *
         * @author 史明松
         * @update 2014年6月23日 下午12:52:49
         */
        public void toNext();

        /**
         * 立刻更新.
         *
         * @author 史明松
         * @update 2014年6月20日 下午5:34:23
         */
        public void updateNewVersion(Version version);

        public void onError(String errorMessage);

    }
}
