package com.company.project.deliver.processor.decorator;

import com.company.project.deliver.model.PayResultModel;
import com.company.project.deliver.processor.Processor;

public class OutsideNotifyProcessor extends ProcessorDecorator {

    public OutsideNotifyProcessor(Processor processor) {
        super(processor);
    }

    @Override
    protected void beforeProcess(PayResultModel payResultModel) {
        System.out.println("OutsideNotifyProcessor beforeProcess");
    }

    @Override
    protected void afterProcess(PayResultModel payResultModel) {
        System.out.println("OutsideNotifyProcessor afterProcess");
    }
}
