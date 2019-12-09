package cn.comestart.contract.pipeline;

import cn.comestart.contract.vo.Contract;
import cn.comestart.utils.Consts;
import cn.comestart.utils.SleepTools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static cn.comestart.io.AbstractIO.GROUP_SIZE;

public class ContractUploader implements Runnable {
    private static BlockingQueue<List<Contract>> queue = new ArrayBlockingQueue<>(100);

    private ContractUploader() {
    }

    public static void uploadContractPipeline(List<Contract> contracts) throws InterruptedException {
//        System.out.println("开始上传合同，id " + contract.getId());
        queue.put(contracts);
    }

    public static void init() {
        new Thread(new ContractUploader()).start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                List<Contract> contractList = queue.take();

                // 1. set contract url
                int contentLength = 0;
                for (Contract contract : contractList) {
                    contract.setUrl("http://www.comestart.cn/fs/" + contract.getContent().substring(0, 20));
                    contentLength += contract.getContent().length();
                }
                // 2. upload contract list
//                System.out.println("上传合同完成，休眠 " + contentLength / (1024 * 1024));
                SleepTools.ms(contentLength / (1024 * 1024));

                // 3. update contract
                ContractUpdater.updateContractPipeline(contractList);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
