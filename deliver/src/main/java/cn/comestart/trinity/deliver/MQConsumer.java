package cn.comestart.trinity.deliver;

import cn.comestart.trinity.deliver.model.PayResultModel;
import cn.comestart.trinity.deliver.model.PayResultType;
import cn.comestart.trinity.deliver.processor.Processor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static cn.comestart.trinity.deliver.model.PayResultType.*;

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

    public void receivePayResult(byte[] data) {
        PayResultModel payResultModel = MsgFactory.genPayResult(data);
        processPayResult(payResultModel);
    }

    private void processPayResult(PayResultModel payResultModel) {
        PayResultType payResultType = payResultModel.getPayResultType();
        if (VACCOUNT == payResultType) {
            vaccountProcessor.process(payResultModel);
        } else if (BANKCARD == payResultType) {
            bankcardProcessor.process(payResultModel);
        } else if (L1 == payResultType) {
            l1Processor.process(payResultModel);
        } else if (FEE == payResultType) {
            feeProcessor.process(payResultModel);
        }
    }
}
