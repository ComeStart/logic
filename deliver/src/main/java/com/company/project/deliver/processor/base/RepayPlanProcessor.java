package com.company.project.deliver.processor.base;

import com.company.project.deliver.model.PayResultModel;
import com.company.project.deliver.processor.Processor;

public class RepayPlanProcessor implements Processor {

    @Override
    public void process(PayResultModel payResultModel) {
        createRepayPlan(payResultModel);
    }

    private void createRepayPlan(PayResultModel payResultModel) {
        System.out.println("-- RepayPlanProcessor createRepayPlan");
    }
}
