package com.company.project.deliver.processor.base;

import com.company.project.deliver.model.PayResultModel;
import com.company.project.deliver.processor.Processor;

public class LoanStatusProcessor implements Processor {

    @Override
    public void process(PayResultModel payResultModel) {
        updateLoanStatus(payResultModel);
    }

    private void updateLoanStatus(PayResultModel payResultModel) {
        System.out.println("-- LoanStatusProcessor updateLoanStatus");
    }
}
