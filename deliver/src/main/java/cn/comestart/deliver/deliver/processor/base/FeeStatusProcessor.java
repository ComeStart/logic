package cn.comestart.deliver.deliver.processor.base;

import cn.comestart.deliver.deliver.model.PayResultModel;
import cn.comestart.deliver.deliver.processor.Processor;

public class FeeStatusProcessor implements Processor {

    @Override
    public void process(PayResultModel payResultModel) {
        updateFeeStatus(payResultModel);
    }

    private void updateFeeStatus(PayResultModel payResultModel) {
        System.out.println("-- FeeStatusProcessor updateFeeStatus");
    }
}
