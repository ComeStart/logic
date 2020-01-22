package com.company.project.deliver.processor.base;

import com.company.project.deliver.model.PayResultModel;
import com.company.project.deliver.processor.Processor;

public class FeeStatusProcessor implements Processor {

    @Override
    public void process(PayResultModel payResultModel) {
        updateFeeStatus(payResultModel);
    }

    private void updateFeeStatus(PayResultModel payResultModel) {
        System.out.println("-- FeeStatusProcessor updateFeeStatus");
    }
}
