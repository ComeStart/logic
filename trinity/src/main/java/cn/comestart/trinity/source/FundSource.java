package cn.comestart.trinity.source;


import cn.comestart.io.AbstractSource;
import cn.comestart.trinity.vo.Fund;
import org.springframework.stereotype.Service;

@Service
public class FundSource extends AbstractSource<Fund> {
    public FundSource() {
        this.init(10000, 350000, 9000, 2000, 180, 40, 300_000_000L);
    }

    @Override
    protected Fund newEntity(long id, long amount) {
        return new Fund(id, amount);
    }
}
