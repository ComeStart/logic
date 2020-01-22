package com.conpany.project;

import com.company.project.Application;
import com.company.project.deliver.MQConsumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.company.project.deliver.model.PayResultType.*;
import static java.nio.charset.StandardCharsets.UTF_8;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
public class ProcessorTest {
    @Autowired
    private MQConsumer mqConsumer;

    @Test
    public void testTest() {
        System.out.println();
        byte[] data = VACCOUNT.name().getBytes(UTF_8);
        mqConsumer.receivePayResult(data);
        System.out.println();
        data = BANKCARD.name().getBytes(UTF_8);
        mqConsumer.receivePayResult(data);
        System.out.println();
        data = L1.name().getBytes(UTF_8);
        mqConsumer.receivePayResult(data);
        System.out.println();
        data = FEE.name().getBytes(UTF_8);
        mqConsumer.receivePayResult(data);
        System.out.println();
    }
}
