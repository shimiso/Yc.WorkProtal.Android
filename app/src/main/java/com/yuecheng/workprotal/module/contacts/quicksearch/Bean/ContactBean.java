package com.yuecheng.workprotal.module.contacts.quicksearch.Bean;


import android.support.annotation.NonNull;

import com.yuecheng.workprotal.module.contacts.quicksearch.utils.PinYinUtil;

import java.util.List;

public class ContactBean {

	/**
	 * result : {"count":7,"staffs":[{"staffId":21,"name":"李忠诚","positionName":"人事主管","headPortraitUrl":""},{"staffId":30,"name":"李明路","positionName":"人事专员","headPortraitUrl":""},{"staffId":34,"name":"李平华","positionName":"文员","headPortraitUrl":""},{"staffId":37,"name":"李英英","positionName":"销售助理","headPortraitUrl":""},{"staffId":53,"name":"李萍","positionName":"绩效主管","headPortraitUrl":""},{"staffId":80,"name":"李杏子","positionName":"中级销售","headPortraitUrl":""},{"staffId":82,"name":"李晓龙","positionName":"人事主管","headPortraitUrl":""}]}
	 * targetUrl : null
	 * success : true
	 * error : null
	 * unAuthorizedRequest : false
	 * __abp : true
	 */

	private ResultBean result;
	private Object targetUrl;
	private boolean success;
	private Object error;
	private boolean unAuthorizedRequest;
	private boolean __abp;

	//使用成员变量生成构造方法：alt+shift+s->o


//	@Override
//	public int compareTo(ContactBean another) {
//		return getPinyin().compareTo(another.getPinyin());
//	}
//
//	public String getPinyin() {
//		return pinyin;
//	}
//
//	public void setPinyin(String pinyin) {
//		this.pinyin = pinyin;
//	}


	public ResultBean getResult() {
		return result;
	}

	public void setResult(ResultBean result) {
		this.result = result;
	}

	public Object getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(Object targetUrl) {
		this.targetUrl = targetUrl;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getError() {
		return error;
	}

	public void setError(Object error) {
		this.error = error;
	}

	public boolean isUnAuthorizedRequest() {
		return unAuthorizedRequest;
	}

	public void setUnAuthorizedRequest(boolean unAuthorizedRequest) {
		this.unAuthorizedRequest = unAuthorizedRequest;
	}

	public boolean is__abp() {
		return __abp;
	}

	public void set__abp(boolean __abp) {
		this.__abp = __abp;
	}

	public static class ResultBean {
		/**
		 * count : 7
		 * staffs : [{"staffId":21,"name":"李忠诚","positionName":"人事主管","headPortraitUrl":""},{"staffId":30,"name":"李明路","positionName":"人事专员","headPortraitUrl":""},{"staffId":34,"name":"李平华","positionName":"文员","headPortraitUrl":""},{"staffId":37,"name":"李英英","positionName":"销售助理","headPortraitUrl":""},{"staffId":53,"name":"李萍","positionName":"绩效主管","headPortraitUrl":""},{"staffId":80,"name":"李杏子","positionName":"中级销售","headPortraitUrl":""},{"staffId":82,"name":"李晓龙","positionName":"人事主管","headPortraitUrl":""}]
		 */

		private int count;
		private List<StaffsBean> staffs;

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public List<StaffsBean> getStaffs() {
			return staffs;
		}

		public void setStaffs(List<StaffsBean> staffs) {
			this.staffs = staffs;
		}

		public static class StaffsBean implements Comparable<StaffsBean> {
			/**
			 * staffId : 21
			 * name : 李忠诚
			 * positionName : 人事主管
			 * headPortraitUrl :
			 */

			private int staffId;
			private String name;
			private String positionName;
			private String headPortraitUrl;
			private String pinyin;
			public PinYinStyle pinYinStyle=new PinYinStyle();


			public StaffsBean(String name) {
				this.name = name;
				//一开始就转化好拼音
				setPinyin(PinYinUtil.getPinyin(name));
			}


			public String getPinyin() {
				return pinyin;
			}

			public void setPinyin(String pinyin) {
				this.pinyin = pinyin;
			}
			public int getStaffId() {
				return staffId;
			}

			public void setStaffId(int staffId) {
				this.staffId = staffId;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getPositionName() {
				return positionName;
			}

			public void setPositionName(String positionName) {
				this.positionName = positionName;
			}

			public String getHeadPortraitUrl() {
				return headPortraitUrl;
			}

			public void setHeadPortraitUrl(String headPortraitUrl) {
				this.headPortraitUrl = headPortraitUrl;
			}

			@Override
			public int compareTo(@NonNull StaffsBean another) {
				return getPinyin().compareTo(another.getPinyin());
			}
		}
	}

	@Override
	public String toString() {
		return "ContactBean{" +
				"result=" + result +
				", targetUrl=" + targetUrl +
				", success=" + success +
				", error=" + error +
				", unAuthorizedRequest=" + unAuthorizedRequest +
				", __abp=" + __abp +
				'}';
	}
}
