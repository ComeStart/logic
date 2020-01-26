package cn.comestart.trinity.deliver.processor.decorator;

import cn.comestart.trinity.deliver.model.PayResultModel;
import cn.comestart.trinity.deliver.processor.Processor;

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
