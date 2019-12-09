package cn.comestart.contract.pipeline;

import cn.comestart.contract.io.ParamsIO;
import cn.comestart.contract.service.ContractService;
import cn.comestart.contract.vo.Contract;
import cn.comestart.utils.Consts;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ContractCreator implements Runnable{
    private static BlockingQueue<List<Contract>> queue = new ArrayBlockingQueue<>(100);
    private static ParamsIO paramsIO = ParamsIO.getInstance();
    private static ContractService contractService = ContractService.getInstance();

    private ContractCreator() {
    }

    public static void createContractPipeline(List<Contract> contracts) throws InterruptedException {
//        System.out.println("开始创建合同，ID " + contract.getId());
        queue.put(contracts);
    }

    public static void init() {
        for (int i = 0; i < Consts.THREAD_COUNT+1; i++) {
            new Thread(new ContractCreator()).start();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                List<Contract> contracts = queue.take();
                for (Contract contract : contracts) {
                    Map<String, String> params = paramsIO.getWarmedParams(contract.getAssetId());
                    contract.setContent(contractService.makeContractFind(contract.getTemplateId(), params));
                }
//                System.out.println("创建合同完成, size: " + contracts.size());
                ContractUploader.uploadContractPipeline(contracts);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
