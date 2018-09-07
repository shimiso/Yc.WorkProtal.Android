package com.yuecheng.workprotal.module.contacts.bean;


import android.support.annotation.NonNull;

import com.yuecheng.workprotal.utils.PinYinUtil;

import java.util.List;

/**
 * 通讯录人员
 */
public class ContactBean {


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


			public void conversionpinyin(String name) {
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
