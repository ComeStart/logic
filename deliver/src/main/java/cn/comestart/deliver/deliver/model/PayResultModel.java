package cn.comestart.deliver.deliver.model;

public class PayResultModel {
    private PayResultType payResultType;

    public PayResultType getPayResultType() {
        return payResultType;
    }

    public void setPayResultType(PayResultType payResultType) {
        this.payResultType = payResultType;
    }

    public Integer getPhase() {
        throw new UnsupportedOperationException();
    }

    public String  getFeeType() {
        throw new UnsupportedOperationException();
    }
}
