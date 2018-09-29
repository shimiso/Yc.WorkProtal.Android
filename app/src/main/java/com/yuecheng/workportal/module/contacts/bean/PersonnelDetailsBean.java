package com.yuecheng.workportal.module.contacts.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huochangsheng on 2018/9/5.
 * 人员详细信息
 */

public class PersonnelDetailsBean {


    /**
     * id : 6wewerwe
     * code : 15183
     * staffGrade : null
     * name : Karen Kang 康靖
     * email : kangjing@yuechenggroup.com
     * gender : 2
     * mobilePhone : 13911337569
     * telephone : 87967108
     * organizationName : 乐成集团>经营管理层
     * positionName : 集团CIO
     * directSupervisor : {"guid":"AFBA61BF-CFC0-4F43-A549-3FBE16B66919","name":"倪浩华"}
     * subordinates : [{"guid":"7B84BCB5-9EB0-470C-8593-FD8CCE3DA7B1","name":"高旭"},{"guid":"47A7E705-3ED0-41C5-969A-7E682E1CF100","name":"李孟强"},{"guid":"38513066-DAC0-4118-809E-D3DBA2C13FCB","name":"李哲"},{"guid":"03BF472C-8CFD-4156-89FB-4C929E8C5EE3","name":"刘高峰"},{"guid":"B0A5D479-1922-44F5-9481-E27089FB150B","name":"倪铁翔"},{"guid":"4978BC3B-EBF4-48BE-BF57-5FEB0D87FEA0","name":"王强"},{"guid":"A1BB7AA2-5A90-44FF-8336-5117C03852AC","name":"张松"},{"guid":"6A11B4AB-4CCE-4D85-8867-727D55BD5B72","name":"董晨"},{"guid":"6B20E4B6-2916-4415-AD9F-0D72FEBB6A7E","name":"郭喆"},{"guid":"C8FDFBE7-BFB9-4132-BEDF-BEED0F21FDE5","name":"马文秋"},{"guid":"01A21D05-82DE-4FEF-8832-84C5DC1A0C4D","name":"宋森"},{"guid":"88F26111-7A00-440F-BB4D-86586E4BA87F","name":"宋晓东"},{"guid":"CE64EAD2-7399-4417-AF61-A88E43DF07E9","name":"赵闯"},{"guid":"34C70D2C-0E59-4E78-B31F-9362C3ABF58F","name":"郑春晨"},{"guid":"33ABA518-ACDD-4955-9671-1A202881EB44","name":"霍长生"},{"guid":"2F0FE6E8-7801-43CA-92EE-9849F1EAF12F","name":"史明松"},{"guid":"CC0EC0F3-9790-43BC-9BAB-AF2F61C3C8FB","name":"王涛"},{"guid":"22FAB3B7-44B3-481D-94A5-B734AB179506","name":"王涛"},{"guid":"AA6699B6-46CB-4AC6-A3F9-13C175303F66","name":"赵志强"},{"guid":"C9BE9C97-7180-4FB2-AC83-9610A121C743","name":"安琳"},{"guid":"C3DED05F-E38D-4CF1-96F1-94F82E760DBF","name":"沈利峰"},{"guid":"90054DC9-2902-4081-870A-A1FDE98CF6E7","name":"苏秀艳"},{"guid":"0025640C-0099-42F7-B7C4-2FE6688CF2CA","name":"肖丹"},{"guid":"45FEA660-33E0-4483-BE1F-B8917D5F0C4F","name":"闫洪越"}]
     * partTimeJobs : 
     * rongCloudToken : Wfb+0+X02BLRK6SpyK8TFh7ejVA8RiPEVS0kvcsxneNyyi2l8Drtw5N4/QWBNrtquT9HBrqEK3jTBAWcEsRKuA==
     */

    private int id;
    private String code;
    private String guid;
    private String staffGrade;
    private String name;
    private String email;
    private int gender;
    private String mobilePhone;
    private String telephone;
    private String organizationName;
    private String deepOrgIds;
    private String positionName;
    private DirectSupervisorBean directSupervisor;
    private String partTimeJobs;
    private String rongCloudToken;
    private List<SubordinatesBean> subordinates;

    public String getDeepOrgIds() {
        return deepOrgIds;
    }

    public void setDeepOrgIds(String deepOrgIds) {
        this.deepOrgIds = deepOrgIds;
    }

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

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
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

    public DirectSupervisorBean getDirectSupervisor() {
        return directSupervisor;
    }

    public void setDirectSupervisor(DirectSupervisorBean directSupervisor) {
        this.directSupervisor = directSupervisor;
    }

    public String getPartTimeJobs() {
        return partTimeJobs;
    }

    public void setPartTimeJobs(String partTimeJobs) {
        this.partTimeJobs = partTimeJobs;
    }

    public String getRongCloudToken() {
        return rongCloudToken;
    }

    public void setRongCloudToken(String rongCloudToken) {
        this.rongCloudToken = rongCloudToken;
    }

    public List<SubordinatesBean> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(List<SubordinatesBean> subordinates) {
        this.subordinates = subordinates;
    }

    public static class DirectSupervisorBean {
        /**
         * guid : AFBA61BF-CFC0-4F43-A549-3FBE16B66919
         * name : 倪浩华
         */

        private String guid;
        private String name;
        private String positionName;
        private int gender;

        public void setPositionName(String positionName) {
            this.positionName = positionName;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getPositionName() {
            return positionName;
        }

        public int getGender() {
            return gender;
        }

        public String getGuid() {
            return guid;
        }

        public void setGuid(String guid) {
            this.guid = guid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class SubordinatesBean implements Serializable {
        /**
         * guid : 7B84BCB5-9EB0-470C-8593-FD8CCE3DA7B1
         * name : 高旭
         */

        private String guid;
        private String name;
        private String positionName;
        private int gender;

        public void setPositionName(String positionName) {
            this.positionName = positionName;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getPositionName() {
            return positionName;
        }

        public int getGender() {
            return gender;
        }

        public String getGuid() {
            return guid;
        }

        public void setGuid(String guid) {
            this.guid = guid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
