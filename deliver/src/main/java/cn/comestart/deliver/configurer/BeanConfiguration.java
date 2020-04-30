package cn.comestart.deliver.configurer;

import cn.comestart.deliver.deliver.processor.Processor;
import cn.comestart.deliver.deliver.processor.base.*;
import cn.comestart.deliver.deliver.processor.decorator.InsideNotifyProcessor;
import cn.comestart.deliver.deliver.processor.decorator.OutsideNotifyProcessor;
import cn.comestart.deliver.deliver.processor.decorator.TransactionalProcessor;
import cn.comestart.deliver.deliver.processor.decorator.state.BankcardStateMachineProcessor;
import cn.comestart.deliver.deliver.processor.decorator.state.FeeStateMachineProcessor;
import cn.comestart.deliver.deliver.processor.decorator.state.L1StateMachineProcessor;
import cn.comestart.deliver.deliver.processor.decorator.state.VAccountStateMachineProcessor;
import cn.comestart.deliver.deliver.processor.chain.ProcessorChain;
import cn.comestart.deliver.proxy.TestService;
import cn.comestart.deliver.proxy.TestServiceInvocationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.lang.reflect.Proxy;


@Configuration
public class BeanConfiguration {
    @Resource(name = "testService")
    private TestService testService;

    @Bean
    public Processor vaccountProcessor() {
        ProcessorChain processorChain = new ProcessorChain();
        processorChain.addProcessor(new WithdrawInitProcessor()).addProcessor(new LoanStatusProcessor());
        return new TransactionalProcessor(new VAccountStateMachineProcessor(new InsideNotifyProcessor(processorChain)));
    }

    @Bean
    public Processor bankcardProcessor() {
        ProcessorChain processorChain = new ProcessorChain();
        processorChain.addProcessor(new WithdrawFinishProcessor()).addProcessor(new RepayPlanProcessor());
        return new TransactionalProcessor(new BankcardStateMachineProcessor(new OutsideNotifyProcessor(processorChain)));
    }

    @Bean
    public Processor l1Processor() {
        ProcessorChain processorChain = new ProcessorChain();
        processorChain.addProcessor(new LoanStatusProcessor()).addProcessor(new L1RepayPlanProcessor());
        return new TransactionalProcessor(new L1StateMachineProcessor(processorChain));
    }

    @Bean
    public Processor feeProcessor() {
        return new TransactionalProcessor(new FeeStateMachineProcessor(new FeeStatusProcessor()));
    }

    @Bean
    public TestService testServiceProxy() {
        return (TestService) Proxy.newProxyInstance(testService.getClass().getClassLoader(),
                testService.getClass().getInterfaces(), new TestServiceInvocationHandler(testService));
    }

}
