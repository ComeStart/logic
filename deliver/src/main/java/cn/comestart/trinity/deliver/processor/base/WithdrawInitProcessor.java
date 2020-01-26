package cn.comestart.trinity.deliver.processor.base;

import cn.comestart.trinity.deliver.model.PayResultModel;
import cn.comestart.trinity.deliver.processor.Processor;

public class WithdrawInitProcessor implements Processor {

    @Override
    public void process(PayResultModel payResultModel) {
        initWithdraw(payResultModel);
    }

    private void initWithdraw(PayResultModel payResultModel) {
        System.out.println("-- WithdrawInitProcessor initWithdraw");
    }
}
