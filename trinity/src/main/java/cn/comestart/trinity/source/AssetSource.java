package cn.comestart.trinity.source;


import cn.comestart.io.AbstractSource;
import cn.comestart.trinity.vo.Asset;
import org.springframework.stereotype.Service;

@Service
public class AssetSource extends AbstractSource<Asset> {
    public AssetSource() {
        this.init(30000, 350000, 3000, 666, 180, 40, 1_000_000_000L);
    }

    @Override
    protected Asset newEntity(long id, long amount) {
        return new Asset(id, amount);
    }
}
