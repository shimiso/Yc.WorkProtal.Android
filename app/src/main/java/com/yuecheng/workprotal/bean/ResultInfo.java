package com.yuecheng.workprotal.bean;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * 类的说明：网络请求返回JavaBean
 */
public class ResultInfo<T>  implements Serializable {
    private static final long serialVersionUID = -5173718782598185482L;
    public boolean success = false; //是否成功
    public String error = "";//错误信息
    public T result;// 返回数据,字符串格式
    public String json;//返回的json字符串

    public ResultInfo() {

    }

    /**
     * 初始化ResultInfo
     * @param json
     * @param dataType data数据类型
     */
    public ResultInfo(String json, Type dataType) {
        this.json = json;
        Gson gson = new Gson();
        try {
            JSONObject jsonObj = new JSONObject(json);
            this.success = jsonObj.getBoolean("success");
            this.error = jsonObj.getString("error");
            if(json!=null&&!json.isEmpty()){
                this.result = gson.fromJson(json, dataType);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /**
     * 初始化ResultInfo
     * @param json
     */
    public ResultInfo(String json) {
        this.json = json;
        try {
            JSONObject jsonObj = new JSONObject(json);
            this.success = jsonObj.getBoolean("success");
            this.error = jsonObj.getString("error");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
