package com.yuecheng.workprotal.module.contacts.bean;

import java.util.List;

/**
 * Created by huochangsheng on 2018/9/6.
 * 通讯录组织机构子机构数据
 */

public class ChildInstitutionsBean {

    /**
     * myselfTopOrgId : 0
     * deepOrgNames : 恭和苑>总部>人力资源部
     * orgs : [{"orgId":0,"orgName":null,"subOrgs":[{"orgId":74,"orgName":"人力资源部经理","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":75,"orgName":"人力资源部副经理","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":76,"orgName":"薪酬主管","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":77,"orgName":"培训主管","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":78,"orgName":"人事主管","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":79,"orgName":"劳动关系主管","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":80,"orgName":"招聘主管","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":81,"orgName":"绩效主管","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":82,"orgName":"人事专员","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":83,"orgName":"党务管理","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null}],"orgStaffCount":0,"deepCode":null,"staffs":[]}]
     */

    private int myselfTopOrgId;
    private String deepOrgNames;
    private String deepOrgIds;
    private List<OrgsBean> orgs;

    public int getMyselfTopOrgId() {
        return myselfTopOrgId;
    }

    public void setMyselfTopOrgId(int myselfTopOrgId) {
        this.myselfTopOrgId = myselfTopOrgId;
    }

    public String getDeepOrgNames() {
        return deepOrgNames;
    }

    public void setDeepOrgNames(String deepOrgNames) {
        this.deepOrgNames = deepOrgNames;
    }

    public String getDeepOrgIds() {
        return deepOrgIds;
    }

    public void setDeepOrgIds(String deepOrgIds) {
        this.deepOrgIds = deepOrgIds;
    }

    public List<OrgsBean> getOrgs() {
        return orgs;
    }

    public void setOrgs(List<OrgsBean> orgs) {
        this.orgs = orgs;
    }

    public static class OrgsBean {
        /**
         * orgId : 0
         * orgName : null
         * subOrgs : [{"orgId":74,"orgName":"人力资源部经理","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":75,"orgName":"人力资源部副经理","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":76,"orgName":"薪酬主管","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":77,"orgName":"培训主管","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":78,"orgName":"人事主管","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":79,"orgName":"劳动关系主管","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":80,"orgName":"招聘主管","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":81,"orgName":"绩效主管","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":82,"orgName":"人事专员","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":83,"orgName":"党务管理","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null}]
         * orgStaffCount : 0
         * deepCode : null
         * staffs : []
         */

        private int orgId;
        private String orgName;
        private int orgStaffCount;
        private Object deepCode;
        private List<OrganizationBean.OrgsBean.SubOrgsBean> subOrgs;
        private List<OrganizationBean.OrgsBean.StaffsBean> staffs;

        public int getOrgId() {
            return orgId;
        }

        public void setOrgId(int orgId) {
            this.orgId = orgId;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public int getOrgStaffCount() {
            return orgStaffCount;
        }

        public void setOrgStaffCount(int orgStaffCount) {
            this.orgStaffCount = orgStaffCount;
        }

        public Object getDeepCode() {
            return deepCode;
        }

        public void setDeepCode(Object deepCode) {
            this.deepCode = deepCode;
        }

        public List<OrganizationBean.OrgsBean.SubOrgsBean> getSubOrgs() {
            return subOrgs;
        }

        public void setSubOrgs(List<OrganizationBean.OrgsBean.SubOrgsBean> subOrgs) {
            this.subOrgs = subOrgs;
        }

        public List<OrganizationBean.OrgsBean.StaffsBean> getStaffs() {
            return staffs;
        }

        public void setStaffs(List<OrganizationBean.OrgsBean.StaffsBean> staffs) {
            this.staffs = staffs;
        }
    }
}
