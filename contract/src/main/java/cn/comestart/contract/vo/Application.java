package cn.comestart.contract.vo;

import java.util.List;

public class Application {
    private long aid;
    private List<Contract> contractList;

    public static class Contract {
        private long contractId;
        private long templateId;
        private String url;
        private String content;

        public long getContractId() {
            return contractId;
        }

        public void setContractId(long contractId) {
            this.contractId = contractId;
        }

        public long getTemplateId() {
            return templateId;
        }

        public void setTemplateId(long templateId) {
            this.templateId = templateId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public long getAid() {
        return aid;
    }

    public void setAid(long aid) {
        this.aid = aid;
    }

    public List<Contract> getContractList() {
        return contractList;
    }

    public void setContractList(List<Contract> contractList) {
        this.contractList = contractList;
    }
}
