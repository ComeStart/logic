package com.company.project.deliver.processor.decorator;


import com.company.project.deliver.model.PayResultModel;
import com.company.project.deliver.processor.Processor;
import org.springframework.transaction.annotation.Transactional;

/**
 * 需要包在最外层，否则事务无法生效
 */
public class TransactionalProcessor extends ProcessorDecorator {

    public TransactionalProcessor(Processor processor) {
        super(processor);
    }

    @Override
    @Transactional
    public void process(PayResultModel payResultModel) {
        super.process(payResultModel);
    }

    @Override
    protected void beforeProcess(PayResultModel payResultModel) {
        System.out.println("TransactionalProcessor txn begin");
    }

    @Override
    protected void afterProcess(PayResultModel payResultModel) {
        System.out.println("TransactionalProcessor txn end");
    }
}
