package com.yuecheng.workprotal;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;
import com.yuecheng.workprotal.base.BaseActivity;
import com.yuecheng.workprotal.module.LoginActivity;
import com.yuecheng.workprotal.module.update.CheckVersionPresenter;
import com.yuecheng.workprotal.module.update.Version;
import com.yuecheng.workprotal.module.update.VersionUpdateDialog;
import com.yuecheng.workprotal.utils.AndroidUtil;
import com.yuecheng.workprotal.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类描述: 欢迎页
 * 创建人: shims
 * 创建时间: 2017/9/21 08:04
 */
public class WelcomeActivity extends BaseActivity {
    Context context;
    CheckVersionPresenter checkVersionPresenter;
    //版本更新返回码
    private int UpdateReqCode = 2014;
    @BindView(R.id.version_tv)
    TextView versionTv;

    /**
     * 需要android6.0以上处理的权限
     */
    private String[] PERMISSIONS = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setFullFullscreen();//全屏
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        context = this;
        ButterKnife.bind(this);
        versionTv.setText("版本：V" + androidUtil.getApkVersionName());
        checkVersionPresenter = new CheckVersionPresenter(this);
//        User currentLoginUser = MainApplication.getApplication().getCurrentLoginUser();
            runOnUiThread(() -> {
                // android系统大于等于6.0时需要处理时权限
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestNeedPermission();
                } else {
                    checkNetworkAndRequestData();//如果网络设置成功后，返回需再次检测网络，重复该流程，避免卡死在当前界面
                }
//                startActivity( new Intent(context, LoginActivity.class));
//                finish();
            });

    }
    /**
     * 判断是否需要进行权限的请求
     */
    private void requestNeedPermission() {
        boolean temp = false;
        for (String permission : PERMISSIONS) {
            if (!hasPermissionGranted(permission)) {
                temp = true;
                break;
            }
        }
        if (temp) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, 0);
        } else {
            checkNetworkAndRequestData();
        }
    }

    /**
     * 判断该权限是否已经授权
     *
     * @param permission
     * @return
     */
    private boolean hasPermissionGranted(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            boolean temp = false;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    ToastUtil.error(context, "拒绝" + grantResults[i] + "权限会导致定位失败！");
                    temp = true;
                    break;
                }
            }

            if (temp) {
                requestNeedPermission();
            } else {
                checkNetworkAndRequestData();
            }
        }
    }

    /**
     * 检查网络并请求数据
     */
    private void checkNetworkAndRequestData() {
        //验证存储和网络
        if (androidUtil.checkStoragePathAndSetBaseApp()) {
            //版本验证
            checkVersionPresenter.checkVersion(androidUtil.getApkVersionCode(), new CheckVersionPresenter.CheckVersionListener() {

                @Override
                public void toNext() {
                    startActivity( new Intent(context, MainActivity.class));
                    finish();
                }

                @Override
                public void updateNewVersion(Version version) {
                    if (!AndroidUtil.isActivityRunning(context, VersionUpdateDialog.class.getName())) {
                        Intent in = new Intent(context, VersionUpdateDialog.class);
                        in.putExtra("VERSION",version);
                        startActivityForResult(in, UpdateReqCode);
                    }
                }

                @Override
                public void onError(String errorMessage) {
                    startActivity( new Intent(context, LoginActivity.class));
                    finish();
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        startActivity( new Intent(context, LoginActivity.class));
        finish();
    }
}