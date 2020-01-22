package com.company.project.deliver.processor.decorator.state;

import com.company.project.deliver.model.PayResultModel;
import com.company.project.deliver.processor.Processor;
import com.company.project.deliver.processor.decorator.ProcessorDecorator;

public class VAccountStateMachineProcessor extends ProcessorDecorator {
    public VAccountStateMachineProcessor(Processor processor) {
        super(processor);
    }

    @Override
    protected void beforeProcess(PayResultModel payResultModel) {
        System.out.println("VAccountStateMachineProcessor beforeProcess");
    }

    @Override
    protected void afterProcess(PayResultModel payResultModel) {
        System.out.println("VAccountStateMachineProcessor afterProcess");
    }
}
