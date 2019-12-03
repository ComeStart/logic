package cn.comestart.matching.source;

import cn.comestart.io.AbstractDBSource;
import cn.enjoyedu.trinity.vo.Fund;

public class FundDBSource extends AbstractDBSource<Fund> {
    private static FundDBSource fundSource = new FundDBSource();

    private FundDBSource() {
        this.init(10000, 350000, 9000, 2000, 180, 40, 300_000_000L);
    }

    public static FundDBSource getInstance() {
        return fundSource;
    }

    @Override
    protected Fund newEntity(long id, long amount) {
        return new Fund(id, amount);
    }
}
