/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yuecheng.workportal.callback;

import android.content.Intent;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.StringConvert;
import com.yuecheng.workportal.LoginActivity;
import com.yuecheng.workportal.MainApplication;

import org.json.JSONObject;

import okhttp3.Response;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）
 * 版    本：1.0
 * 创建日期：2016/4/8
 * 描    述：我的Github地址  https://github.com/jeasonlzy
 * 修订历史：
 * ================================================
 */
public abstract class StringCustomCallback extends AbsCallback<String> {


    private StringConvert convert;

    public StringCustomCallback() {
        convert = new StringConvert();
    }

    @Override
    public String convertResponse(Response response) throws Throwable {
        String json = convert.convertResponse(response);
        JSONObject jsonObj = new JSONObject(json);
        //判断当前token是否过期
        boolean success = jsonObj.optBoolean("success");
        boolean unAuthorizedRequest = jsonObj.optBoolean("unAuthorizedRequest");
        if(!success && unAuthorizedRequest){
            MainApplication.getApplication().relogin();
            MainApplication.getApplication().startActivity(new Intent(MainApplication.getApplication(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
        response.close();
        return json;
    }
}
