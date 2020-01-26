package cn.comestart.trinity.deliver.processor.base;

import cn.comestart.trinity.deliver.model.PayResultModel;
import cn.comestart.trinity.deliver.processor.Processor;

public class RepayPlanProcessor implements Processor {

    @Override
    public void process(PayResultModel payResultModel) {
        createRepayPlan(payResultModel);
    }

    private void createRepayPlan(PayResultModel payResultModel) {
        System.out.println("-- RepayPlanProcessor createRepayPlan");
    }
}
