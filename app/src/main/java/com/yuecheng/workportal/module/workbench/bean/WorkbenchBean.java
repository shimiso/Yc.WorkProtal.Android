package com.yuecheng.workportal.module.workbench.bean;

import java.util.List;

/**
 * Created by huochangsheng on 2018/9/28.
 */

public class WorkbenchBean {


    /**
     * sectionName : 常用
     * perms : [{"permName":"待办工作", "enPermName": "英文名称","iconUrl":"dbgz.png","linkAddress":null},{"permName":"发起表单","iconUrl":"fqbd.png","linkAddress":null},{"permName":"协同","iconUrl":"xt.png","linkAddress":null},{"permName":"发起协同","iconUrl":"fqxt.png","linkAddress":null},{"permName":"新建会议","iconUrl":"xjhy.png","linkAddress":null},{"permName":"查询会议","iconUrl":"cxhy.png","linkAddress":null},{"permName":"时间安排","iconUrl":"sjap.png","linkAddress":null}]
     */

    private String sectionName;
    private List<PermsBean> perms;

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public List<PermsBean> getPerms() {
        return perms;
    }

    public void setPerms(List<PermsBean> perms) {
        this.perms = perms;
    }

    public static class PermsBean {
        /**
         * permName : 待办工作
         * iconUrl : dbgz.png
         * linkAddress : null
         */

        private String permName;
        private String iconUrl;
        private String enPermName;
        private String linkAddress;

        public String getPermName() {
            return permName;
        }

        public void setPermName(String permName) {
            this.permName = permName;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getEnPermName() {
            return enPermName;
        }

        public void setEnPermName(String enPermName) {
            this.enPermName = enPermName;
        }

        public String getLinkAddress() {
            return linkAddress;
        }

        public void setLinkAddress(String linkAddress) {
            this.linkAddress = linkAddress;
        }
    }
}
