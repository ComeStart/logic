package cn.enjoyedu.trinity.vo;

import java.util.Comparator;

public class Asset {
    private long id;
    private long amount;
    private int investedNum;
    private long investedAmt;

    public Asset(long id, long amount) {
        this.id = id;
        this.amount = amount;
        this.investedNum = 0;
        this.investedAmt = 0L;
    }

    public long getId() {
        return id;
    }

    public long getAmount() {
        return amount;
    }

    public int getInvestedNum() {
        return investedNum;
    }

    public void invest(long invest) {
        investedNum++;
        investedAmt += invest;
    }

    public boolean isFull() {
        return amount <= investedAmt;
    }

    public long restAmount() {
        return amount - investedAmt;
    }

    public static class AssetComparator implements Comparator<Asset> {

        @Override
        public int compare(Asset o1, Asset o2) {
            if (o1.investedNum != o2.investedNum) return o1.investedNum - o2.investedNum;
            if (o1.amount != o2.amount) return (int) (o2.amount - o1.amount);
            return (int) (o1.id - o2.id);
        }
    }
}
