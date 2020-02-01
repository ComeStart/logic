package cn.comestart.deliver.deliver.processor.base;

import cn.comestart.deliver.deliver.model.PayResultModel;
import cn.comestart.deliver.deliver.processor.Processor;

public class RepayPlanProcessor implements Processor {

    @Override
    public void process(PayResultModel payResultModel) {
        createRepayPlan(payResultModel);
    }

    private void createRepayPlan(PayResultModel payResultModel) {
        System.out.println("-- RepayPlanProcessor createRepayPlan");
    }
}
