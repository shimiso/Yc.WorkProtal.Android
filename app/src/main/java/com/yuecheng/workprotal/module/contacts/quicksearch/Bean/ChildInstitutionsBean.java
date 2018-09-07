package com.yuecheng.workprotal.module.contacts.quicksearch.Bean;

import java.util.List;
import com.yuecheng.workprotal.module.contacts.quicksearch.Bean.OrganizationBean.ResultBean.OrgsBean.StaffsBean;
import com.yuecheng.workprotal.module.contacts.quicksearch.Bean.OrganizationBean.ResultBean.OrgsBean.SubOrgsBean;
/**
 * Created by huochangsheng on 2018/9/6.
 */

public class ChildInstitutionsBean {

    /**
     * result : {"myselfTopOrgId":0,"deepOrgNames":"恭和苑>总部>人力资源部","orgs":[{"orgId":0,"orgName":null,"subOrgs":[{"orgId":74,"orgName":"人力资源部经理","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":75,"orgName":"人力资源部副经理","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":76,"orgName":"薪酬主管","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":77,"orgName":"培训主管","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":78,"orgName":"人事主管","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":79,"orgName":"劳动关系主管","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":80,"orgName":"招聘主管","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":81,"orgName":"绩效主管","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":82,"orgName":"人事专员","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":83,"orgName":"党务管理","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null}],"orgStaffCount":0,"deepCode":null,"staffs":[]}]}
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
         * myselfTopOrgId : 0
         * deepOrgNames : 恭和苑>总部>人力资源部
         * orgs : [{"orgId":0,"orgName":null,"subOrgs":[{"orgId":74,"orgName":"人力资源部经理","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":75,"orgName":"人力资源部副经理","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":76,"orgName":"薪酬主管","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":77,"orgName":"培训主管","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":78,"orgName":"人事主管","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":79,"orgName":"劳动关系主管","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":80,"orgName":"招聘主管","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":81,"orgName":"绩效主管","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":82,"orgName":"人事专员","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null},{"orgId":83,"orgName":"党务管理","subOrgs":null,"orgStaffCount":0,"deepCode":"@1@7@34@","staffs":null}],"orgStaffCount":0,"deepCode":null,"staffs":[]}]
         */

        private int myselfTopOrgId;
        private String deepOrgNames;
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

            public Object getDeepCode() {
                return deepCode;
            }

            public void setDeepCode(Object deepCode) {
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

//

        }
    }
}
