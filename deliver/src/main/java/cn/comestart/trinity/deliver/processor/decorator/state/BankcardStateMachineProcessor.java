package cn.comestart.trinity.deliver.processor.decorator.state;

import cn.comestart.trinity.deliver.model.PayResultModel;
import cn.comestart.trinity.deliver.processor.Processor;
import cn.comestart.trinity.deliver.processor.decorator.ProcessorDecorator;

public class BankcardStateMachineProcessor extends ProcessorDecorator {
    public BankcardStateMachineProcessor(Processor processor) {
        super(processor);
    }

    @Override
    protected void beforeProcess(PayResultModel payResultModel) {
        System.out.println("BankcardStateMachineProcessor beforeProcess");
    }

    @Override
    protected void afterProcess(PayResultModel payResultModel) {
        System.out.println("BankcardStateMachineProcessor afterProcess");
    }
}
