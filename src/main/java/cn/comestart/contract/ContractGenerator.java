package cn.comestart.contract;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ContractGenerator {
    private ContractService contractService = ContractService.getInstance();
    private ContractIO contractIO = ContractIO.getInstance();

    public static void main(String[] args) {
        ContractGenerator instance = new ContractGenerator();

        long startMillis = System.currentTimeMillis();

        try {
            List<Contract> contractList = instance.createContract();
            System.out.println("已生成" + contractList.size() + "个合同，用时 " + (System.currentTimeMillis() - startMillis) + " ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Contract> createContract() {
        List<Contract> contractList = contractIO.getEmptyContract(20,false);
        for (Contract contract : contractList) {
            String content = contractService.createContract(contract, false);
            contract.setContent(content);
            contractIO.uploadContract(contract);
            contractIO.updateContract(contract);
        }
        return contractList;
    }

    private List<Contract> createContractAsync() throws ExecutionException, InterruptedException {
        List<Contract> contractList = contractIO.getEmptyContract(1000,true);
        System.out.println("合同已获取，params初始化成功!");
        List<Future<Contract>> urlFutureList = new ArrayList<>();
        for (Contract contract : contractList) {
            String content = contractService.createContractAsync(contract, true);
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

}
