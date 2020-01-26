package cn.comestart.trinity.io;


import cn.comestart.io.AbstractIO;
import cn.comestart.trinity.source.AssetSource;
import cn.comestart.trinity.vo.Asset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetIO extends AbstractIO<Asset> {
    @Autowired
    public AssetIO(AssetSource assetSource) {
        super(assetSource);
    }
}
