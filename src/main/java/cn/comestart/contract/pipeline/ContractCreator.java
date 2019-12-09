package cn.comestart.contract.pipeline;

import cn.comestart.contract.io.ParamsIO;
import cn.comestart.contract.service.ContractService;
import cn.comestart.contract.vo.Contract;
import cn.comestart.utils.Consts;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ContractCreator implements Runnable{
    private static BlockingQueue<Contract> queue = new ArrayBlockingQueue<>(1000);
    private static ContractService contractService = ContractService.getInstance();
    private static ParamsIO paramsIO = ParamsIO.getInstance();

    private ContractCreator() {
    }

    public static void createContractPipeline(Contract contract) throws InterruptedException {
//        System.out.println("开始创建合同，ID " + contract.getId());
        queue.put(contract);
    }

    public static void init() {
        new Thread(new ContractCreator()).start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Contract contract = queue.take();
                Map<String, String> params = paramsIO.getWarmedParams(contract.getAssetId());
                contract.setContent(contractService.makeContractFind(contract.getTemplateId(), params));
//                System.out.println("创建合同完成, id: " + contract.getId());
                ContractUploader.uploadContractPipeline(contract);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
