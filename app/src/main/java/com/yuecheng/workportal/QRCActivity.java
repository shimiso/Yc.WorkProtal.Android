package com.yuecheng.workportal;

import android.os.Bundle;
import android.view.SurfaceView;

import com.zxing.activity.CaptureActivity;
import com.zxing.view.ViewfinderView;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class QRCActivity extends CaptureActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrc);
        ButterKnife.bind(this);
        init(this, (SurfaceView) findViewById(R.id.svCameraScan), (ViewfinderView) findViewById(R.id.vfvCameraScan));
    }

    @OnClick(R.id.back_iv)
    public void onViewClicked() {
        finish();
    }
}
