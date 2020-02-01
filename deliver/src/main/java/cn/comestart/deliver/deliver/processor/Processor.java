package cn.comestart.deliver.deliver.processor;


import cn.comestart.deliver.deliver.model.PayResultModel;

public interface Processor {
    void process(PayResultModel payResultModel);
}
