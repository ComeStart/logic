package cn.comestart.deliver.deliver.processor.decorator.state;

import cn.comestart.deliver.deliver.model.PayResultModel;
import cn.comestart.deliver.deliver.processor.Processor;
import cn.comestart.deliver.deliver.processor.decorator.ProcessorDecorator;

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
