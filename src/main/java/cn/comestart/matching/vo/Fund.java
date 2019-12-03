package cn.enjoyedu.trinity.vo;

import java.util.Comparator;

public class Fund {
    private long id;
    private long amount;
    private long investedAmount;

    public Fund(long id, long amount) {
        this.id = id;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public long getAmount() {
        return amount;
    }

    public long getInvestedAmount() {return investedAmount;}

    public boolean isFull() {return investedAmount >= amount;}

    public long restAmount() {return amount - investedAmount;}

    public void invest(long invest) {investedAmount += invest;}

    public static class FundComparator implements Comparator<Fund> {
        @Override
        public int compare(Fund o1, Fund o2) {
            return (int)(o1.id - o2.id);
        }
    }
}
