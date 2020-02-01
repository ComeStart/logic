package cn.comestart.deliver.deliver.processor.base;

import cn.comestart.deliver.deliver.model.PayResultModel;
import cn.comestart.deliver.deliver.processor.Processor;

public class WithdrawFinishProcessor implements Processor {

    @Override
    public void process(PayResultModel payResultModel) {
        finishWithdraw(payResultModel);
    }

    private void finishWithdraw(PayResultModel payResultModel) {
        System.out.println("-- WithdrawFinishProcessor finishWithdraw");
    }
}
