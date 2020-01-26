package cn.comestart.trinity.deliver.processor.base;

import cn.comestart.trinity.deliver.model.PayResultModel;
import cn.comestart.trinity.deliver.processor.Processor;

public class LoanStatusProcessor implements Processor {

    @Override
    public void process(PayResultModel payResultModel) {
        updateLoanStatus(payResultModel);
    }

    private void updateLoanStatus(PayResultModel payResultModel) {
        System.out.println("-- LoanStatusProcessor updateLoanStatus");
    }
}
