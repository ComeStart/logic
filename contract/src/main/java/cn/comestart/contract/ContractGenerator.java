package cn.comestart.contract;

import cn.comestart.contract.io.ContractIO;
import cn.comestart.contract.pipeline.ContractReader;
import cn.comestart.contract.pipeline.ContractUpdater;
import cn.comestart.contract.pipeline.ContractUploader;
import cn.comestart.contract.service.ContractService;
import cn.comestart.contract.vo.Contract;
import cn.comestart.utils.Consts;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ContractGenerator {
    private static ContractService contractService = ContractService.getInstance();
    private static ContractIO contractIO = ContractIO.getInstance();

    private static BlockingQueue<List<Contract>> queue = new ArrayBlockingQueue<>(1000);

    private static ExecutorService executorService = Executors.newFixedThreadPool(1);

    public static void main(String[] args) {
        ContractGenerator instance = new ContractGenerator();

        long startMillis = System.currentTimeMillis();

        try {
            List<Contract> contractList = instance.createContractAsyncPipeline();
            if (contractList != null)
                System.out.println("已生成" + contractList.size() + "个合同，用时 " + (System.currentTimeMillis() - startMillis) + " ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Contract> createContract() throws InterruptedException {
        List<Contract> contractList = contractIO.getEmptyContract(1, false);
        for (Contract contract : contractList) {
            String content = contractService.createContract(contract, false, 0);
            contract.setContent(content);
            contractIO.uploadContract(contract);
            contractIO.updateContract(contract);
        }
        return contractList;
    }

    private List<Contract> createContractAsync() throws ExecutionException, InterruptedException {
        long startMillis =System.currentTimeMillis();
        for (int i = 0; i < Consts.THREAD_COUNT; i++) {
            executorService.execute(new ContractCreator(startMillis, queue));
        }
        List<Contract> contractList = contractIO.getEmptyContract(100, true, queue);
        System.out.println("合同总数" + contractList.size());
        return null;
    }

    private List<Contract> createContractAsyncNoInit() throws ExecutionException, InterruptedException {
        List<Contract> contractList = contractIO.getEmptyContract(100, false);
        System.out.println("合同已获取，params初始化成功!");
        List<Future<Contract>> urlFutureList = new ArrayList<>();
        for (Contract contract : contractList) {
            String content = contractService.createContract(contract, true, 2);
            contract.setContent(content);
            urlFutureList.add(contractIO.uploadContractAsync(contract));
        }
        System.out.println("合同已创建，正在上传...");
        for (Future<Contract> future : urlFutureList) {
            future.get();
        }
        contractIO.updateEntities(contractList);
        return contractList;
    }

    private List<Contract> createContractAsyncPipeline() {
        long startMillis = System.currentTimeMillis();
        ContractReader.init(100);
        cn.comestart.contract.pipeline.ContractCreator.init();
        ContractUploader.init();
        ContractUpdater.init(startMillis);
        return null;
    }

    private static class ContractCreator implements Runnable {
        private long startMillis;
        private BlockingQueue<List<Contract>> queue;

        public ContractCreator(long startMillis, BlockingQueue<List<Contract>> queue) {
            this.startMillis = startMillis;
            this.queue = queue;
        }

        @Override
        public void run() {
            do {
                try {
                    List<Contract> contractList = queue.take();
                    System.out.println("合同已获取，params初始化成功!");
                    List<Future<Contract>> urlFutureList = new ArrayList<>();
                    for (Contract contract : contractList) {
                        String content = contractService.createContract(contract, true, 1);
                        contract.setContent(content);
                        urlFutureList.add(contractIO.uploadContractAsync(contract));
                    }
                    System.out.println("合同已创建，正在上传...");
                    for (Future<Contract> future : urlFutureList) {
                        future.get();
                    }
                    contractIO.updateContractAsync(contractList);
                    System.out.println("合同已上传，总用时" + (System.currentTimeMillis() - startMillis));
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
    }


}
