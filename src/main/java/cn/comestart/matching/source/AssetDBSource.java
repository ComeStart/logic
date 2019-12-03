package cn.comestart.matching.source;

import cn.comestart.io.AbstractDBSource;
import cn.enjoyedu.trinity.vo.Asset;

public class AssetDBSource extends AbstractDBSource<Asset> {

    private static AssetDBSource instance = new AssetDBSource();

    public static AssetDBSource getInstance() {
        return instance;
    }

    private AssetDBSource() {
        this.init(30000, 350000, 3000, 666, 180, 40, 1_000_000_000L);
    }

    @Override
    protected Asset newEntity(long id, long amount) {
        return new Asset(id, amount);
    }
}
