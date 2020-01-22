package com.company.project.deliver.processor.base;

import com.company.project.deliver.model.PayResultModel;
import com.company.project.deliver.processor.Processor;

public class WithdrawInitProcessor implements Processor {

    @Override
    public void process(PayResultModel payResultModel) {
        initWithdraw(payResultModel);
    }

    private void initWithdraw(PayResultModel payResultModel) {
        System.out.println("-- WithdrawInitProcessor initWithdraw");
    }
}
