package com.yuecheng.workprotal.module.contacts.presenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yuecheng.workprotal.bean.ResultInfo;
import com.yuecheng.workprotal.callback.DialogCallback;
import com.yuecheng.workprotal.common.CommonPostView;
import com.yuecheng.workprotal.common.UrlConstant;
import com.yuecheng.workprotal.module.contacts.quicksearch.Bean.ContactBean;
import com.yuecheng.workprotal.module.contacts.quicksearch.Bean.PersonnelDetailsBean;
import com.yuecheng.workprotal.module.update.Version;

/**
 * Created by huochangsheng on 2018/9/5.
 */

public class ContactsPresenter {

    private Activity activity;
    public ContactsPresenter(Activity activity) {
        this.activity = activity;
    }

    public void getContact(final CommonPostView<ContactBean> commonPostView){

        OkGo.<String>post(UrlConstant.ADDRESSSTAFFQUEY)//
                .tag(this)//
                .params("staffId", 11)//
                .execute(new DialogCallback<String>(activity) {
                    @Override
                    public void onSuccess(Response<String> result) {
                        String stringresult = new String(result.body());

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
                        ResultInfo<ContactBean> resultInfo = null;
                        try {
                            resultInfo = new ResultInfo(stringresult);

                            if (resultInfo.isSuccess()) {
                                Gson gson = new Gson();
                                ContactBean checkAccount = gson.fromJson(stringresult, ContactBean.class);
                                resultInfo.result = checkAccount;
                                commonPostView.postSuccess(resultInfo);
                            } else {
                                commonPostView.postError("服务器发生未知异常");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            commonPostView.postError("服务器发生未知异常");
                        }
                    }

                    @Override
                    public void onError(Response<String> response){
                        commonPostView.postError("发生未知异常");
                    }
                });
    }
    public void getContactInformation(String StaffId, final CommonPostView<PersonnelDetailsBean> commonPostView){

        OkGo.<String>post(UrlConstant.STAFFBASICINFOGET)//
                .tag(this)//
                .params("StaffId", StaffId)//
                .execute(new StringCallback(){
                    @Override
                    public void onSuccess(Response<String> result) {
                        String stringresult = new String(result.body());

                        ResultInfo<PersonnelDetailsBean> resultInfo = null;
                        try {
                            resultInfo = new ResultInfo(stringresult);

                            if (resultInfo.isSuccess()) {
                                Gson gson = new Gson();
                                PersonnelDetailsBean checkAccount = gson.fromJson(stringresult, PersonnelDetailsBean.class);
                                resultInfo.result = checkAccount;
                                commonPostView.postSuccess(resultInfo);
                            } else {
                                commonPostView.postError("服务器发生未知异常");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            commonPostView.postError("服务器发生未知异常");
                        }
                    }

                    @Override
                    public void onError(Response<String> response){
                        commonPostView.postError("发生未知异常");
                    }
                });
    }
}
