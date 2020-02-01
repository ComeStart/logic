package cn.comestart.deliver.deliver.processor.decorator;

import cn.comestart.deliver.deliver.model.PayResultModel;
import cn.comestart.deliver.deliver.processor.Processor;

public abstract class ProcessorDecorator implements Processor {
    protected Processor processor;

    public ProcessorDecorator(Processor processor) {
        this.processor = processor;
    }

    @Override
    public void process(PayResultModel payResultModel) {
        beforeProcess(payResultModel);
        processor.process(payResultModel);
        afterProcess(payResultModel);
    }

    abstract protected void beforeProcess(PayResultModel payResultModel);
    abstract protected void afterProcess(PayResultModel payResultModel);
}
