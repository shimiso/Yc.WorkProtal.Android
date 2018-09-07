package com.yuecheng.workprotal.module.contacts.bean;

/**
 * Created by huochangsheng on 2018/9/5.
 * 人员详细信息
 */

public class PersonnelDetailsBean {


        /**
         * id : 10
         * code : qh1989
         * staffGrade : null
         * name : 刘启蒙
         * email : lqm@hjsoft.com.cn
         * gender : 1
         * mobilePhone : 13801246166
         * telephone : null
         * organizationName : 恭和苑>总部>人力资源部
         * positionName : 人力资源部经理
         * directSupervisor : null
         */

        private int id;
        private String code;
        private String staffGrade;
        private String name;
        private String email;
        private int gender;
        private String mobilePhone;
        private String telephone;
        private String organizationName;
        private String positionName;
        private String directSupervisor;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getStaffGrade() {
            return staffGrade;
        }

        public void setStaffGrade(String staffGrade) {
            this.staffGrade = staffGrade;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getMobilePhone() {
            return mobilePhone;
        }

        public void setMobilePhone(String mobilePhone) {
            this.mobilePhone = mobilePhone;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getOrganizationName() {
            return organizationName;
        }

        public void setOrganizationName(String organizationName) {
            this.organizationName = organizationName;
        }

        public String getPositionName() {
            return positionName;
        }

        public void setPositionName(String positionName) {
            this.positionName = positionName;
        }

        public String getDirectSupervisor() {
            return directSupervisor;
        }

        public void setDirectSupervisor(String directSupervisor) {
            this.directSupervisor = directSupervisor;
        }

}
