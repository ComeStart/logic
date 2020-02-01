package cn.comestart.deliver.deliver.processor.decorator;

import cn.comestart.deliver.deliver.model.PayResultModel;
import cn.comestart.deliver.deliver.processor.Processor;

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
