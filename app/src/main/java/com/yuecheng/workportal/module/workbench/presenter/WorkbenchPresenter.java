package com.yuecheng.workportal.module.workbench.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yuecheng.workportal.MainApplication;
import com.yuecheng.workportal.R;
import com.yuecheng.workportal.bean.LoginUser;
import com.yuecheng.workportal.bean.ResultInfo;
import com.yuecheng.workportal.callback.StringCustomCallback;
import com.yuecheng.workportal.common.CommonPostView;
import com.yuecheng.workportal.common.UrlConstant;
import com.yuecheng.workportal.module.workbench.bean.WorkbenchBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by huochangsheng on 2018/9/28.
 */

public class WorkbenchPresenter {
    private Context context;
    public WorkbenchPresenter(Context context) {
        this.context = context;
    }

    /**
     * 获取用户工作台
     * @param guid
     * @param commonPostView
     */
    public void getGetStaffPerms(String guid, final CommonPostView commonPostView){

        LoginUser loginUser = MainApplication.getApplication().getLoginUser();
        OkGo.<String>get(UrlConstant.GET_STAFF_PERMS)//
                .tag(this)//
                .params("Guid", guid)//
                .headers("Authorization", "Bearer "+loginUser.getAccess_token())
                .execute(new StringCustomCallback(){
                    @Override
                    public void onSuccess(Response<String> result) {
                        String stringresult = new String(result.body());

                        ResultInfo<List<WorkbenchBean>> resultInfo = null;
                        try {
                            resultInfo = new ResultInfo(stringresult);

                            if (resultInfo.isSuccess()) {
                                Gson gson = new Gson();
                                JSONObject jsonObj = new JSONObject(stringresult);
                                JSONArray result1 = jsonObj.getJSONArray("result");
                                //解析为集合
                                List<WorkbenchBean> checkAccount = gson.fromJson(result1.toString(), new TypeToken<List<WorkbenchBean>>(){}.getType());
                                resultInfo.result = checkAccount;
                                commonPostView.postSuccess(resultInfo);
                            } else {
                                commonPostView.postError(context.getString(R.string.server_net_error));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            commonPostView.postError(context.getString(R.string.server_net_error));
                        }
                    }

                    @Override
                    public void onError(Response<String> response){
                        commonPostView.postError(context.getString(R.string.server_net_error));
                    }
                });
    }
}
