package com.yuecheng.workportal;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;

import com.yuecheng.workportal.utils.AndroidUtil;
import com.yuecheng.workportal.widget.ConfirmDialog;
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

        boolean lacksPermission = AndroidUtil.cameraIsCanUse();
        //当权限为开启时弹出提示框
        if (!lacksPermission) {
            ConfirmDialog dialog = ConfirmDialog.createDialog(this);
            dialog.setDialogTitle("提示");
            dialog.setCancelable(false);
            dialog.setDialogMessage("相机权限未开启！");

            dialog.setOnButton2ClickListener("知道了", null, new ConfirmDialog.OnButton2ClickListener() {
                @Override
                public void onClick(View view, DialogInterface dialog) {
                    dialog.cancel();
                }
            });
            dialog.show();
        }
    }

    @OnClick(R.id.back_iv)
    public void onViewClicked() {
        finish();
    }
}
