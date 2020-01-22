package com.company.project.deliver.processor.base;

import com.company.project.deliver.model.PayResultModel;
import com.company.project.deliver.processor.Processor;

public class L1RepayPlanProcessor implements Processor {

    @Override
    public void process(PayResultModel payResultModel) {
        createL1RepayPlan(payResultModel);
    }

    private void createL1RepayPlan(PayResultModel payResultModel) {
        System.out.println("-- L1RepayPlanProcessor createL1RepayPlan");
    }
}
