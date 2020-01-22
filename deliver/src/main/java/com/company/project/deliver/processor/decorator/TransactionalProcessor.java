package com.company.project.deliver.processor.decorator;


import com.company.project.deliver.model.PayResultModel;
import com.company.project.deliver.processor.Processor;
import org.springframework.transaction.annotation.Transactional;

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
