package cn.comestart.contract.vo;

public class Contract {
    private long id;
    private long templateId;
    private String url;
    private String content;
    private long assetId;

    public Contract(long id, long templateId, long assetId) {
        this.id = id;
        this.templateId = templateId;
        this.assetId = assetId;
    }

    public long getId() {
        return id;
    }

    public long getTemplateId() {
        return templateId;
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

    public long getAssetId() {
        return assetId;
    }
}
