package cn.comestart.trinity.source;

import cn.comestart.io.AbstractSource;
import cn.comestart.trinity.vo.Trinity;
import org.springframework.stereotype.Service;

@Service
public class TrinitySource extends AbstractSource<Trinity> {
    @Override
    protected Trinity newEntity(long id, long amount) {
        return null;
    }
}
