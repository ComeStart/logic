package cn.comestart.deliver;

import cn.comestart.deliver.deliver.MQConsumer;
import cn.comestart.deliver.deliver.model.PayResultModel;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static cn.comestart.deliver.deliver.model.PayResultType.*;
import static java.nio.charset.StandardCharsets.UTF_8;

public class ProcessorTest extends Tester {
    @Autowired
    private MQConsumer mqConsumer;

    @Test
    public void testTest() {
        System.out.println();
        byte[] data = VACCOUNT.name().getBytes(UTF_8);
        PayResultModel vaccountModel = mqConsumer.receivePayResult(data);
        System.out.println();
        data = BANKCARD.name().getBytes(UTF_8);
        PayResultModel bankcardModel = mqConsumer.receivePayResult(data);
        System.out.println();
        data = L1.name().getBytes(UTF_8);
        PayResultModel l1Model = mqConsumer.receivePayResult(data);
        System.out.println();
        data = FEE.name().getBytes(UTF_8);
        PayResultModel feeModel = mqConsumer.receivePayResult(data);
        System.out.println();
        System.out.println("VAccount resultType = " + vaccountModel.getPayResultType() +
                ", phase = " + vaccountModel.getPhase() +
                ", feeType = " + vaccountModel.getFeeType());
    }
}
