package cn.comestart.trinity.configurer;

import cn.comestart.trinity.deliver.processor.Processor;
import cn.comestart.trinity.deliver.processor.base.*;
import cn.comestart.trinity.deliver.processor.decorator.InsideNotifyProcessor;
import cn.comestart.trinity.deliver.processor.decorator.OutsideNotifyProcessor;
import cn.comestart.trinity.deliver.processor.decorator.TransactionalProcessor;
import cn.comestart.trinity.deliver.processor.decorator.state.BankcardStateMachineProcessor;
import cn.comestart.trinity.deliver.processor.decorator.state.FeeStateMachineProcessor;
import cn.comestart.trinity.deliver.processor.decorator.state.L1StateMachineProcessor;
import cn.comestart.trinity.deliver.processor.decorator.state.VAccountStateMachineProcessor;
import com.company.project.deliver.processor.base.*;
import cn.comestart.trinity.deliver.processor.chain.ProcessorChain;
import com.company.project.deliver.processor.decorator.*;
import com.company.project.deliver.processor.decorator.state.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BeanConfiguration {
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

}
