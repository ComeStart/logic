package cn.comestart.trinity.io;


import cn.comestart.io.AbstractIO;
import cn.comestart.trinity.source.FundSource;
import cn.comestart.trinity.vo.Fund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FundIO extends AbstractIO<Fund> {

    @Autowired
    public FundIO(FundSource fundSource) {
        super(fundSource);
    }
}
