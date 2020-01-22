package cn.comestart.contract.io;

import cn.comestart.contract.vo.Contract;
import cn.comestart.io.AbstractDBSource;
import cn.comestart.io.AbstractIO;
import cn.comestart.utils.Consts;
import cn.comestart.utils.SleepTools;

import java.util.*;
import java.util.concurrent.*;

public class ContractIO extends AbstractIO<Contract> {


    private static ContractIO instance = new ContractIO();

    public static ContractIO getInstance() {
        return instance;
    }

    private static Random random = new Random();

    private ContractIO() {
        this(null);
    }

    protected ContractIO(AbstractDBSource<Contract> source) {
        super(source);
    }


    private ParamsIO paramsIO = ParamsIO.getInstance();

    public List<Contract> getEmptyContract(int assetNum, boolean warmParams) throws InterruptedException {
        return getEmptyContract(assetNum, warmParams, null);
    }

    /**
     * @param assetNum   资产数量
     * @param warmParams 是否预热参数
     * @param queue      阻塞队列，用于存放结果，生产者消费者模式
     * @return 合同列表
     * @throws InterruptedException 阻塞队列的put操作
     */
    public List<Contract> getEmptyContract(int assetNum, boolean warmParams, BlockingQueue<List<Contract>> queue) throws InterruptedException {
        // get assets
        List<Long> assetIdList = new ArrayList<>();
        for (int i = 0; i < assetNum; i++) {
            assetIdList.add((long) random.nextInt(1_000_000));
        }
        SleepTools.ms(20);

        if (warmParams) {
            for (Long assetId : assetIdList) {
                paramsIO.initParams(assetId);
            }
        }

        List<Contract> result = new ArrayList<>();
        List<Contract> subList = new ArrayList<>();
        int contractCount = 0;
        for (Long assetId : assetIdList) {
            int count = 8 + random.nextInt(4);
            for (int i = 0; i < count; i++) {
                if (contractCount++ % GROUP_SIZE == 0) SleepTools.ms(20);
                Contract contract = new Contract(random.nextInt(1_000_000), random.nextInt(300), assetId);
                result.add(contract);
                if (queue != null) {
                    subList.add(contract);
                    if (contractCount % GROUP_SIZE == 0) {
                        List<List<Contract>> contractList = new ArrayList<>();
                        for (int j = 0; j < Consts.THREAD_COUNT; j++) {
                            contractList.add(new ArrayList<>());
                        }
                        for (int j = 0; j < contractCount; j++) {
                            contractList.get(j % Consts.THREAD_COUNT).add(subList.get(j));
                        }
                        for (List<Contract> contractSubList : contractList) {
                            queue.put(contractSubList);
                        }
                        subList = new ArrayList<>();
                    }
                }
            }
        }
        if (queue != null && !subList.isEmpty()) {
            List<List<Contract>> contractList = new ArrayList<>();
            for (int j = 0; j < Consts.THREAD_COUNT; j++) {
                contractList.add(new ArrayList<>());
            }
            for (int j = 0; j < contractCount; j++) {
                contractList.get(j % Consts.THREAD_COUNT).add(subList.get(j));
            }
            for (List<Contract> contractSubList : contractList) {
                queue.put(contractSubList);
            }
            System.out.println("剩余合同数量" + subList.size());
        }

        return result;
    }


    public void updateContract(Contract contract) {
        SleepTools.ms(20);
    }

    public Future<List<Contract>> updateContractAsync(List<Contract> contractList) {
        return AbstractIO.completionService.submit(new ContractUpdater(contractList));
    }

    public String uploadContract(Contract contract) {
        SleepTools.ms(10);
        contract.setUrl("http://www.comestart.cn/fs/" + contract.getContent().substring(0, 20));
        return contract.getUrl();
    }

    public Future<Contract> uploadContractAsync(Contract contract) {
        return AbstractIO.completionService.submit(new ContractUploader(contract));
    }


    private static class ContractUploader implements Callable {
        private Contract contract;

        private ContractUploader(Contract contract) {
            this.contract = contract;
        }

        @Override
        public Contract call() throws Exception {
            SleepTools.ms(10);
            String url = "http://www.comestart.cn/fs/" + contract.getContent().substring(0, 20);
            contract.setUrl(url);
            return contract;
        }
    }

    private static class ContractUpdater implements Callable {
        private List<Contract> contractList;

        private ContractUpdater(List<Contract> contractList) {
            this.contractList = contractList;
        }

        @Override
        public List<Contract> call() throws Exception {
            SleepTools.ms(20);
            return contractList;
        }
    }
}
