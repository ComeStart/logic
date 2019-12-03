package cn.comestart.matching.io;

import cn.comestart.io.AbstractIO;
import cn.comestart.matching.source.FundDBSource;
import cn.comestart.matching.vo.Fund;

public class FundIO extends AbstractIO<Fund> {

    private static FundIO instance = new FundIO();

    public static FundIO getInstance() {
        return instance;
    }

    private FundIO(FundDBSource source) {
        super(source);
    }

    private FundIO() {
        this(FundDBSource.getInstance());
    }


}
