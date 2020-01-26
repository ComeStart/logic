package cn.comestart.trinity.deliver.processor.base;

import cn.comestart.trinity.deliver.model.PayResultModel;
import cn.comestart.trinity.deliver.processor.Processor;

public class FeeStatusProcessor implements Processor {

    @Override
    public void process(PayResultModel payResultModel) {
        updateFeeStatus(payResultModel);
    }

    private void updateFeeStatus(PayResultModel payResultModel) {
        System.out.println("-- FeeStatusProcessor updateFeeStatus");
    }
}
