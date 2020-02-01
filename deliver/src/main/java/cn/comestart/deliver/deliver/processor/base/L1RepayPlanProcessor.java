package cn.comestart.deliver.deliver.processor.base;

import cn.comestart.deliver.deliver.model.PayResultModel;
import cn.comestart.deliver.deliver.processor.Processor;

public class L1RepayPlanProcessor implements Processor {

    @Override
    public void process(PayResultModel payResultModel) {
        createL1RepayPlan(payResultModel);
    }

    private void createL1RepayPlan(PayResultModel payResultModel) {
        System.out.println("-- L1RepayPlanProcessor createL1RepayPlan");
    }
}
