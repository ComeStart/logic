package cn.comestart.deliver.deliver.processor.decorator.state;

import cn.comestart.deliver.deliver.model.PayResultModel;
import cn.comestart.deliver.deliver.processor.Processor;
import cn.comestart.deliver.deliver.processor.decorator.ProcessorDecorator;

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
