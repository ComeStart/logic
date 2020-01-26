package cn.comestart.trinity.vo;

public class Trinity {
    private long assetId;
    private long fundId;
    private long amount;

    public Trinity(long assetId, long fundId, long amount) {
        this.assetId = assetId;
        this.fundId = fundId;
        this.amount = amount;
    }

    public long getAssetId() {
        return assetId;
    }

    public long getFundId() {
        return fundId;
    }

    public long getAmount() {
        return amount;
    }
}
