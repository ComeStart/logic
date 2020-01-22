package com.company.project.deliver.processor;


import com.company.project.deliver.model.PayResultModel;

public interface Processor {
    void process(PayResultModel payResultModel);
}
