package com.company.project.configurer;

import com.company.project.deliver.processor.Processor;
import com.company.project.deliver.processor.base.*;
import com.company.project.deliver.processor.chain.ProcessorChain;
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
