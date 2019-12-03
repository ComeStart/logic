package cn.comestart.contract;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ContractGenerator {
    private static ContractService contractService = ContractService.getInstance();
    private static ContractIO contractIO = ContractIO.getInstance();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startMillis = System.currentTimeMillis();

        List<Contract> contractList = contractIO.getEmptyContract(100);
        List<Future<String>> urlFutureList = new ArrayList<>();
        for (Contract contract : contractList) {
            String content = contractService.createContract(contract);
            contract.setContent(content);
            urlFutureList.add(contractIO.uploadContractAsync(contract));
        }
        for(int i=0;i<contractList.size();i++) {
            urlFutureList.get(i).get();
        }
        contractIO.updateEntities(contractList);

        System.out.println("已生成合同，用时 " + (System.currentTimeMillis() - startMillis) + " ms");
    }


}
