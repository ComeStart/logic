package cn.comestart.deliver.deliver;

import cn.comestart.deliver.deliver.model.PayResultModel;
import cn.comestart.deliver.deliver.model.PayResultType;
import cn.comestart.deliver.deliver.processor.Processor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import static cn.comestart.deliver.deliver.model.PayResultType.*;

@Service
public class MQConsumer {
    @Resource(name = "vaccountProcessor")
    private Processor vaccountProcessor;
    @Resource(name = "bankcardProcessor")
    private Processor bankcardProcessor;
    @Resource(name = "l1Processor")
    private Processor l1Processor;
    @Resource(name = "feeProcessor")
    private Processor feeProcessor;

    private Map<PayResultType, Processor> processorMap = new HashMap<>();

    @PostConstruct
    public void init() {
        processorMap.put(VACCOUNT, vaccountProcessor);
        processorMap.put(BANKCARD, bankcardProcessor);
        processorMap.put(L1, l1Processor);
        processorMap.put(FEE, feeProcessor);
    }

    public void receivePayResult(byte[] data) {
        PayResultModel payResultModel = MsgFactory.genPayResult(data);
        processPayResult(payResultModel);
    }

    private void processPayResult(PayResultModel payResultModel) {
        PayResultType payResultType = payResultModel.getPayResultType();
        processorMap.get(payResultType).process(payResultModel);
    }
}
