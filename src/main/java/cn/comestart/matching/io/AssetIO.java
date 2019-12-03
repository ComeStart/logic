package cn.comestart.matching.io;

import cn.comestart.io.AbstractIO;
import cn.comestart.matching.source.AssetDBSource;
import cn.enjoyedu.trinity.vo.Asset;


public class AssetIO extends AbstractIO<Asset> {

    private static AssetIO instance = new AssetIO();

    public static AssetIO getInstance() {
        return instance;
    }

    private AssetIO(AssetDBSource source) {
        super(source);
    }

    private AssetIO() {
        this(AssetDBSource.getInstance());
    }


}
