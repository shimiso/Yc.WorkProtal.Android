package com.yuecheng.workprotal.module.contacts.presenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yuecheng.workprotal.bean.ResultInfo;
import com.yuecheng.workprotal.common.CommonPostView;
import com.yuecheng.workprotal.common.UrlConstant;
import com.yuecheng.workprotal.module.contacts.bean.ChildInstitutionsBean;
import com.yuecheng.workprotal.module.contacts.bean.ContactBean;
import com.yuecheng.workprotal.module.contacts.bean.OrganizationBean;
import com.yuecheng.workprotal.module.contacts.bean.PersonnelDetailsBean;

import org.json.JSONObject;

/**
 * Created by huochangsheng on 2018/9/5.
 */

public class ContactsPresenter {

    private Activity activity;
    public ContactsPresenter(Activity activity) {
        this.activity = activity;
    }

    /**
     * 获取通讯录人员
     * @param commonPostView
     */
    public void getContact(final CommonPostView commonPostView){

        OkGo.<String>post(UrlConstant.ADDRESSSTAFFQUEY)//
                .tag(this)//
                .params("staffId", 11)//
                .execute(new StringCallback(){
                    @Override
                    public void onSuccess(Response<String> result) {
                        String stringresult = new String(result.body());

                        ResultInfo<ContactBean> resultInfo = null;
                        try {
                            resultInfo = new ResultInfo(stringresult);

                            if (resultInfo.isSuccess()) {
                                Gson gson = new Gson();
                                JSONObject jsonObj = new JSONObject(stringresult);
                                JSONObject result1 = jsonObj.getJSONObject("result");
                                ContactBean checkAccount = gson.fromJson(result1.toString(), ContactBean.class);
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

    /**
     * 获取个人详细信息
     * @param StaffId
     * @param commonPostView
     */
    public void getContactInformation(String StaffId, final CommonPostView commonPostView){

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
                                JSONObject jsonObj = new JSONObject(stringresult);
                                JSONObject result1 = jsonObj.getJSONObject("result");
                                PersonnelDetailsBean checkAccount = gson.fromJson(result1.toString(), PersonnelDetailsBean.class);
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

    /**
     * 获取顶级组织机构
     * @param staffId
     * @param commonPostView
     */
    public void getAddressTopOrgQuery(int staffId, final CommonPostView commonPostView){

        OkGo.<String>post(UrlConstant.ADDRESSTOPORGQUERY)
                .tag(this)
                .params("staffId", staffId)
                .execute(new StringCallback(){
                    @Override
                    public void onSuccess(Response<String> result) {
                        String stringresult = new String(result.body());

                        ResultInfo<OrganizationBean> resultInfo = null;
                        try {
                            resultInfo = new ResultInfo(stringresult);

                            if (resultInfo.isSuccess()) {
                                Gson gson = new Gson();
                                JSONObject jsonObj = new JSONObject(stringresult);
                                JSONObject result1 = jsonObj.getJSONObject("result");
                                OrganizationBean checkAccount = gson.fromJson(result1.toString(), OrganizationBean.class);
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

    /**
     * 获取子级组织机构
     * @param orgId
     * @param commonPostView
     */
    public void getAddressOrgQuery(int orgId, final CommonPostView commonPostView){

        OkGo.<String>post(UrlConstant.ADDRESSORGQUERY)
                .tag(this)
                .params("orgId", orgId)
                .execute(new StringCallback(){
                    @Override
                    public void onSuccess(Response<String> result) {
                        String stringresult = new String(result.body());

                        ResultInfo<ChildInstitutionsBean> resultInfo = null;
                        try {
                            resultInfo = new ResultInfo(stringresult);

                            if (resultInfo.isSuccess()) {
                                Gson gson = new Gson();
                                JSONObject jsonObj = new JSONObject(stringresult);
                                JSONObject result1 = jsonObj.getJSONObject("result");
                                ChildInstitutionsBean checkAccount = gson.fromJson(result1.toString(), ChildInstitutionsBean.class);
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
