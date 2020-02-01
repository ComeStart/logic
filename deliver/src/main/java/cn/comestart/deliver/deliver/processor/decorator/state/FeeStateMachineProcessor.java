package cn.comestart.deliver.deliver.processor.decorator.state;

import cn.comestart.deliver.deliver.model.PayResultModel;
import cn.comestart.deliver.deliver.processor.Processor;
import cn.comestart.deliver.deliver.processor.decorator.ProcessorDecorator;

public class FeeStateMachineProcessor extends ProcessorDecorator {
    public FeeStateMachineProcessor(Processor processor) {
        super(processor);
    }

    @Override
    protected void beforeProcess(PayResultModel payResultModel) {
        System.out.println("FeeStateMachineProcessor beforeProcess");
    }

    @Override
    protected void afterProcess(PayResultModel payResultModel) {
        System.out.println("FeeStateMachineProcessor afterProcess");
    }
}
