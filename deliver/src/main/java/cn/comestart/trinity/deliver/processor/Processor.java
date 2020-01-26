package cn.comestart.trinity.deliver.processor;


import cn.comestart.trinity.deliver.model.PayResultModel;

public interface Processor {
    void process(PayResultModel payResultModel);
}
