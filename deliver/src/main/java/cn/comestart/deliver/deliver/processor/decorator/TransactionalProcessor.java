package cn.comestart.deliver.deliver.processor.decorator;


import cn.comestart.deliver.deliver.model.PayResultModel;
import cn.comestart.deliver.deliver.processor.Processor;
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
