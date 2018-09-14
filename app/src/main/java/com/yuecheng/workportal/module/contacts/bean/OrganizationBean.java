package com.yuecheng.workportal.module.contacts.bean;

import java.util.List;

/**
 * Created by huochangsheng on 2018/9/6.
 * 通讯录组织机构顶级机构数据
 */

public class OrganizationBean {


    /**
     * myselfTopOrgId : 1
     * deepOrgNames : null
     * orgs : [{"orgId":1,"orgName":"恭和苑","subOrgs":[{"orgId":47,"orgName":"我所在的部门","subOrgs":null,"orgStaffCount":3,"deepCode":"@1@6@","staffs":null},{"orgId":4,"orgName":"测试机构","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@","staffs":null},{"orgId":5,"orgName":"参股单位","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@","staffs":null},{"orgId":6,"orgName":"各公司","subOrgs":null,"orgStaffCount":12,"deepCode":"@1@","staffs":null},{"orgId":7,"orgName":"总部","subOrgs":null,"orgStaffCount":68,"deepCode":"@1@","staffs":null}],"orgStaffCount":0,"deepCode":"@","staffs":[{"staffId":75,"name":"杨柳","positionName":"","headPortraitUrl":""}]},{"orgId":2,"orgName":"BCIS","subOrgs":null,"orgStaffCount":0,"deepCode":"@","staffs":null}]
     */

    private int myselfTopOrgId;
    private Object deepOrgNames;
    private List<OrgsBean> orgs;

    public int getMyselfTopOrgId() {
        return myselfTopOrgId;
    }

    public void setMyselfTopOrgId(int myselfTopOrgId) {
        this.myselfTopOrgId = myselfTopOrgId;
    }

    public Object getDeepOrgNames() {
        return deepOrgNames;
    }

    public void setDeepOrgNames(Object deepOrgNames) {
        this.deepOrgNames = deepOrgNames;
    }

    public List<OrgsBean> getOrgs() {
        return orgs;
    }

    public void setOrgs(List<OrgsBean> orgs) {
        this.orgs = orgs;
    }

    public static class OrgsBean {
        /**
         * orgId : 1
         * orgName : 恭和苑
         * subOrgs : [{"orgId":47,"orgName":"我所在的部门","subOrgs":null,"orgStaffCount":3,"deepCode":"@1@6@","staffs":null},{"orgId":4,"orgName":"测试机构","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@","staffs":null},{"orgId":5,"orgName":"参股单位","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@","staffs":null},{"orgId":6,"orgName":"各公司","subOrgs":null,"orgStaffCount":12,"deepCode":"@1@","staffs":null},{"orgId":7,"orgName":"总部","subOrgs":null,"orgStaffCount":68,"deepCode":"@1@","staffs":null}]
         * orgStaffCount : 0
         * deepCode : @
         * staffs : [{"staffId":75,"name":"杨柳","positionName":"","headPortraitUrl":""}]
         */

        private int orgId;
        private String orgName;
        private int orgStaffCount;
        private String deepCode;
        private List<SubOrgsBean> subOrgs;
        private List<StaffsBean> staffs;

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

        public String getDeepCode() {
            return deepCode;
        }

        public void setDeepCode(String deepCode) {
            this.deepCode = deepCode;
        }

        public List<SubOrgsBean> getSubOrgs() {
            return subOrgs;
        }

        public void setSubOrgs(List<SubOrgsBean> subOrgs) {
            this.subOrgs = subOrgs;
        }

        public List<StaffsBean> getStaffs() {
            return staffs;
        }

        public void setStaffs(List<StaffsBean> staffs) {
            this.staffs = staffs;
        }

        public static class SubOrgsBean {
            /**
             * orgId : 47
             * orgName : 我所在的部门
             * subOrgs : null
             * orgStaffCount : 3
             * deepCode : @1@6@
             * staffs : null
             */

            private int orgId;
            private String orgName;
            private Object subOrgs;
            private int orgStaffCount;
            private String deepCode;
            private Object staffs;

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

            public Object getSubOrgs() {
                return subOrgs;
            }

            public void setSubOrgs(Object subOrgs) {
                this.subOrgs = subOrgs;
            }

            public int getOrgStaffCount() {
                return orgStaffCount;
            }

            public void setOrgStaffCount(int orgStaffCount) {
                this.orgStaffCount = orgStaffCount;
            }

            public String getDeepCode() {
                return deepCode;
            }

            public void setDeepCode(String deepCode) {
                this.deepCode = deepCode;
            }

            public Object getStaffs() {
                return staffs;
            }

            public void setStaffs(Object staffs) {
                this.staffs = staffs;
            }
        }

        public static class StaffsBean {
            /**
             * staffId : 75
             * name : 杨柳
             * positionName :
             * headPortraitUrl :
             */

            private int staffId;
            private String name;
            private String positionName;
            private String headPortraitUrl;

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
        }
    }
}
