package com.company.project.deliver.processor.decorator.state;

import com.company.project.deliver.model.PayResultModel;
import com.company.project.deliver.processor.Processor;
import com.company.project.deliver.processor.decorator.ProcessorDecorator;

public class L1StateMachineProcessor extends ProcessorDecorator {
    public L1StateMachineProcessor(Processor processor) {
        super(processor);
    }

    @Override
    protected void beforeProcess(PayResultModel payResultModel) {
        System.out.println("L1StateMachineProcessor beforeProcess");
    }

    @Override
    protected void afterProcess(PayResultModel payResultModel) {
        System.out.println("L1StateMachineProcessor afterProcess");
    }
}
