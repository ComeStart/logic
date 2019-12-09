package cn.comestart.contract.pipeline;

import cn.comestart.contract.vo.Contract;
import cn.comestart.utils.Consts;
import cn.comestart.utils.SleepTools;

import java.util.concurrent.*;

public class ContractUploader implements Runnable {
    private static BlockingQueue<Contract> queue = new ArrayBlockingQueue<>(1000);
    private static int THREAD_COUNT = Consts.THREAD_COUNT * 13;

    private ContractUploader() {
    }

    public static void uploadContractPipeline(Contract contract) throws InterruptedException {
//        System.out.println("开始上传合同，id " + contract.getId());
        queue.put(contract);
    }

    public static void init() {
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(new ContractUploader()).start();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
//                System.out.println("初始化uploader");
                Contract contract = queue.take();
                SleepTools.ms(10);
                contract.setUrl("http://www.comestart.cn/fs/" + contract.getContent().substring(0, 20));
//                System.out.println("上传合同完成,id " + contract.getId());
                ContractUpdater.updateContractPipeline(contract);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
