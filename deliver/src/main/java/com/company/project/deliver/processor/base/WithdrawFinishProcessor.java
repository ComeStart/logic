package com.company.project.deliver.processor.base;

import com.company.project.deliver.model.PayResultModel;
import com.company.project.deliver.processor.Processor;

public class WithdrawFinishProcessor implements Processor {

    @Override
    public void process(PayResultModel payResultModel) {
        finishWithdraw(payResultModel);
    }

    private void finishWithdraw(PayResultModel payResultModel) {
        System.out.println("-- WithdrawFinishProcessor finishWithdraw");
    }
}
