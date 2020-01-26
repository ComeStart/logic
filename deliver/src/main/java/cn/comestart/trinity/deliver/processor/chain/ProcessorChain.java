package cn.comestart.trinity.deliver.processor.chain;

import cn.comestart.trinity.deliver.model.PayResultModel;
import cn.comestart.trinity.deliver.processor.Processor;

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
