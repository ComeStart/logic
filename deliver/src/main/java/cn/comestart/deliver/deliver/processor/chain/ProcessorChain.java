package cn.comestart.deliver.deliver.processor.chain;

import cn.comestart.deliver.deliver.model.PayResultModel;
import cn.comestart.deliver.deliver.processor.Processor;

import java.util.ArrayList;
import java.util.List;

public class ProcessorChain implements Processor {
    private final List<Processor> processors;

    public ProcessorChain() {
        processors = new ArrayList<>();
    }

    public ProcessorChain addProcessor(Processor processor) {
        processors.add(processor);
        return this;
    }

    public void process(PayResultModel payResultModel) {
        for(Processor processor : processors) {
            processor.process(payResultModel);
        }
    }

}
