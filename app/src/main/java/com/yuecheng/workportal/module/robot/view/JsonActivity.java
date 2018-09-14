package com.yuecheng.workportal.module.robot.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pddstudio.highlightjs.HighlightJsView;
import com.pddstudio.highlightjs.models.Language;
import com.pddstudio.highlightjs.models.Theme;
import com.yuecheng.workportal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonActivity extends AppCompatActivity {

    private HighlightJsView mDetailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.json_activity_layout);
        String content = getIntent().getStringExtra("json");
        mDetailView = (HighlightJsView)findViewById(R.id.detail_js_view);
        //设置高亮语言和样式
        mDetailView.setHighlightLanguage(Language.JSON);
        mDetailView.setTheme(Theme.ARDUINO_LIGHT);
        try {
            mDetailView.setSource(new JSONObject(content).toString(2));
        } catch (JSONException e) {
            try {
                mDetailView.setSource(new JSONArray(content).toString(2));
            }catch (JSONException j) {
                mDetailView.setSource(content);
            }
        }
    }
}
