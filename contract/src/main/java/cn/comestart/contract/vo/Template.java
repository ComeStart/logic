package cn.comestart.contract.vo;

import java.util.List;

public class Template {
    private long id;
    private List<TemplateElem> templateElemList;

    public Template(long id, List<TemplateElem> templateElemList) {
        this.id = id;
        this.templateElemList = templateElemList;
    }

    public long getId() {
        return id;
    }

    public List<TemplateElem> getTemplateElemList() {
        return templateElemList;
    }

    public static class TemplateElem {
        private String content;
        private String param;

        public TemplateElem(String content, String param) {
            this.content = content;
            this.param = param;
        }

        public String getContent() {
            return content;
        }

        public String getParam() {
            return param;
        }
    }
}
