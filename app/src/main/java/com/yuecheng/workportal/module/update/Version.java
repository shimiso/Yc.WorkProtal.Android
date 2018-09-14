package com.yuecheng.workportal.module.update;

import java.io.Serializable;

/**
 * 版本信息
 */
public class Version implements Serializable {
	private static final long serialVersionUID = 1L;
	private int versionCode;
	private String versionName;
	private String apkUrl;
	private boolean success;
	private String apkSize;
	private boolean isForcedUpdate;
	private String updateInfo;

	public Integer getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getApkUrl() {
		return apkUrl;
	}

	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getApkSize() {
		return apkSize;
	}

	public void setApkSize(String apkSize) {
		this.apkSize = apkSize;
	}


	public String getUpdateInfo() {
		return updateInfo;
	}

	public void setUpdateInfo(String updateInfo) {
		this.updateInfo = updateInfo;
	}

	public boolean isForcedUpdate() {
		return isForcedUpdate;
	}

	public void setForcedUpdate(boolean forcedUpdate) {
		isForcedUpdate = forcedUpdate;
	}
}
