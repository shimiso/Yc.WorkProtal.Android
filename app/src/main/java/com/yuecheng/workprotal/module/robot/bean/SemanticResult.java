package com.yuecheng.workprotal.module.robot.bean;

import org.json.JSONObject;

import java.util.List;

/**
 * AIUI语义结果
 */

public class SemanticResult {

    /**
     * category : TEST123111.music_demo
     * intentType : custom
     * rc : 0
     * semanticType : 1
     * service : TEST123111.music_demo
     * uuid : atn004d5ec6@ch00070ec8319f6f2401
     * vendor : TEST123111
     * version : 2.0
     * semantic : [{"entrypoint":"ent","intent":"query_express_without_company","score":0.9655572175979614,"slots":[{"begin":3,"end":6,"name":"number","normValue":"123","value":"123"}],"template":"查一下{number}到哪里"}]
     * state : null
     * sessionIsEnd : false
     * shouldEndSession : true
     * answer : {"text":"请问您的快递公司是什么","type":"T"}
     * data : {"result":null}
     * sid : atn004d5ec6@ch00070ec8319f6f2401
     * text : 查一下123到哪了
     */

    public String category;
    public String intentType;
    public int rc;
    public int semanticType;
    public String service;
    public String operation;
    public String uuid;
    public String vendor;
    public String version;
    public Object state;
    public String sessionIsEnd;
    public boolean shouldEndSession;
    public String answer;
    public JSONObject data;
    public String sid;
    public String text;
    public JSONObject semantic;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIntentType() {
        return intentType;
    }

    public void setIntentType(String intentType) {
        this.intentType = intentType;
    }

    public int getRc() {
        return rc;
    }

    public void setRc(int rc) {
        this.rc = rc;
    }

    public int getSemanticType() {
        return semanticType;
    }

    public void setSemanticType(int semanticType) {
        this.semanticType = semanticType;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public String getSessionIsEnd() {
        return sessionIsEnd;
    }

    public void setSessionIsEnd(String sessionIsEnd) {
        this.sessionIsEnd = sessionIsEnd;
    }

    public boolean isShouldEndSession() {
        return shouldEndSession;
    }

    public void setShouldEndSession(boolean shouldEndSession) {
        this.shouldEndSession = shouldEndSession;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public JSONObject getSemantic() {
        return semantic;
    }

    public void setSemantic(JSONObject semantic) {
        this.semantic = semantic;
    }

    public static class AnswerBean {
        /**
         * text : 请问您的快递公司是什么
         * type : T
         */

        private String text;
        private String type;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class DataBean {
        /**
         * result : null
         */

        private Object result;

        public Object getResult() {
            return result;
        }

        public void setResult(Object result) {
            this.result = result;
        }
    }

    public static class SemanticBean {
        /**
         * entrypoint : ent
         * intent : query_express_without_company
         * score : 0.9655572175979614
         * slots : [{"begin":3,"end":6,"name":"number","normValue":"123","value":"123"}]
         * template : 查一下{number}到哪里
         */

        private String entrypoint;
        private String intent;
        private double score;
        private String template;
        private List<SlotsBean> slots;

        public String getEntrypoint() {
            return entrypoint;
        }

        public void setEntrypoint(String entrypoint) {
            this.entrypoint = entrypoint;
        }

        public String getIntent() {
            return intent;
        }

        public void setIntent(String intent) {
            this.intent = intent;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }

        public List<SlotsBean> getSlots() {
            return slots;
        }

        public void setSlots(List<SlotsBean> slots) {
            this.slots = slots;
        }

        public static class SlotsBean {
            /**
             * begin : 3
             * end : 6
             * name : number
             * normValue : 123
             * value : 123
             */

            private int begin;
            private int end;
            private String name;
            private String normValue;
            private String value;

            public int getBegin() {
                return begin;
            }

            public void setBegin(int begin) {
                this.begin = begin;
            }

            public int getEnd() {
                return end;
            }

            public void setEnd(int end) {
                this.end = end;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNormValue() {
                return normValue;
            }

            public void setNormValue(String normValue) {
                this.normValue = normValue;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }
}
