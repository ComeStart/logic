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
    private static BlockingQueue<Contract> queue = new ArrayBlockingQueue<>(1000);
    private long startMillis;

    private ContractUpdater(long startMillis) {
        this.startMillis = startMillis;
    }

    public static void updateContractPipeline(Contract contract) throws InterruptedException {
//        System.out.println("开始更新合同, id " + contract.getId());
        queue.put(contract);
    }

    public static void init(long startMillis) {
        new Thread(new ContractUpdater(startMillis)).start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                List<Contract> contractList = new ArrayList<>();
                for (int i = 0; i < GROUP_SIZE; i++) {
                    Contract contract = queue.poll(10, TimeUnit.MILLISECONDS);
                    if(contract == null) break;
                    contractList.add(contract);
                }
                if (!contractList.isEmpty()) {
                    // update contract list
                    SleepTools.ms(20);
                    System.out.println("合同更新完成，用时" + (System.currentTimeMillis() - startMillis));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
