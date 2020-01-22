package cn.comestart.matching.io;


import cn.comestart.io.AbstractDBSource;
import cn.comestart.io.AbstractIO;
import cn.comestart.matching.vo.Trinity;

public class TrinityIO extends AbstractIO<Trinity> {
    private static TrinityIO instance = new TrinityIO();
    public static TrinityIO getInstance() {return instance;}

    private TrinityIO(AbstractDBSource<Trinity> source) {
        super(source);
    }

    private TrinityIO() {
        this(null);
    }
}
