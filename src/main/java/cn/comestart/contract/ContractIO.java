package cn.comestart.contract;

import cn.comestart.io.AbstractDBSource;
import cn.comestart.io.AbstractIO;
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

    public List<Contract> getEmptyContract(int assetNum, boolean warmParams) {
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
        for (Long assetId : assetIdList) {
            int count = 8 + random.nextInt(4);
            for (int i = 0; i < count; i++) {
                result.add(new Contract(random.nextInt(1_000_000), random.nextInt(300), assetId));
            }
        }
        SleepTools.ms(20);

        return result;
    }


    public void updateContract(Contract contract) {
        SleepTools.ms(20);
    }

    public Future<Contract> updateContractAsync(Contract contract) {
        return AbstractIO.completionService.submit(new ContractUpdater(contract));
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
        private Contract contract;

        private ContractUpdater(Contract contract) {
            this.contract = contract;
        }

        @Override
        public Contract call() throws Exception {
            SleepTools.ms(20);
            return contract;
        }
    }
}
