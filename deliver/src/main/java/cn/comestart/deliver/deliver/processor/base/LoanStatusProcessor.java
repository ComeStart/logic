package cn.comestart.deliver.deliver.processor.base;

import cn.comestart.deliver.deliver.model.PayResultModel;
import cn.comestart.deliver.deliver.processor.Processor;

public class LoanStatusProcessor implements Processor {

    @Override
    public void process(PayResultModel payResultModel) {
        updateLoanStatus(payResultModel);
    }

    private void updateLoanStatus(PayResultModel payResultModel) {
        System.out.println("-- LoanStatusProcessor updateLoanStatus");
    }
}
