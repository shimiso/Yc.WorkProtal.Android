package com.yuecheng.workportal.module.robot.handler;

import android.text.TextUtils;


import com.yuecheng.workportal.module.robot.bean.SemanticResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 默认处理类，播报answer，播放data中符合格式的可播放内容
 *
 */

public class DefaultHandler extends IntentHandler {
    public DefaultHandler( SemanticResult result) {
        super(result);
    }

    @Override
    public String getFormatContent() {
        if(result.data != null) {
            JSONArray list = result.data.optJSONArray("result");
            if(list != null){
                for(int index = 0; index < list.length(); index++){
                    JSONObject item = list.optJSONObject(index);
                    int contentType = item.optInt("type", -1);
                    switch (contentType) {
                        //文本内容
                        case 0:{
                            //显示第一条结果
                            result.answer += NEWLINE_NO_HTML + NEWLINE_NO_HTML + item.optString("content");
                            break;
                        }
                        //音频内容(1) 视频内容(2)
                        case 1:
                        case 2: {
                            String audioPath = item.optString("url");
                            String songname = item.optString("title");
                            if(TextUtils.isEmpty(songname)) {
                                songname = item.optString("name");
                            }

                            break;
                        }
                    }
                }
            }

        }

        return result.answer;

    }
}
