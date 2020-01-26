package cn.comestart.trinity.io;


import cn.comestart.io.AbstractIO;
import cn.comestart.trinity.source.TrinitySource;
import cn.comestart.trinity.vo.Trinity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrinityIO extends AbstractIO<Trinity> {
    @Autowired
    public TrinityIO(TrinitySource trinitySource) {
        super(trinitySource);
    }
}
