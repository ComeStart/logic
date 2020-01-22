package cn.comestart.contract.pipeline;

import cn.comestart.contract.vo.Contract;
import cn.comestart.utils.Consts;
import cn.comestart.utils.SleepTools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static cn.comestart.io.AbstractIO.GROUP_SIZE;

public class ContractUpdater implements Runnable {
    private static BlockingQueue<List<Contract>> queue = new ArrayBlockingQueue<>(100);
    private long startMillis;

    private ContractUpdater(long startMillis) {
        this.startMillis = startMillis;
    }

    public static void updateContractPipeline(List<Contract> contracts) throws InterruptedException {
//        System.out.println("开始更新合同, id " + contract.getId());
        queue.put(contracts);
    }

    public static void init(long startMillis) {
        new Thread(new ContractUpdater(startMillis)).start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                List<Contract> contractList = queue.take();

                // update contract list
                SleepTools.ms(20);
                System.out.println("合同更新完成，用时" + (System.currentTimeMillis() - startMillis));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
