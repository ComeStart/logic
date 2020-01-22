package com.company.project.deliver.processor.decorator;

import com.company.project.deliver.model.PayResultModel;
import com.company.project.deliver.processor.Processor;
import com.company.project.deliver.processor.decorator.ProcessorDecorator;

public class InsideNotifyProcessor extends ProcessorDecorator {
    public InsideNotifyProcessor(Processor processor) {
        super(processor);
    }

    @Override
    protected void beforeProcess(PayResultModel payResultModel) {
        System.out.println("InsideNotifyProcessor beforeProcess");
    }

    @Override
    protected void afterProcess(PayResultModel payResultModel) {
        System.out.println("InsideNotifyProcessor afterProcess");
    }

}
