package cn.comestart.contract.pipeline;

import cn.comestart.contract.io.ParamsIO;
import cn.comestart.contract.vo.Contract;
import cn.comestart.utils.CollectionTools;
import cn.comestart.utils.SleepTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static cn.comestart.io.AbstractIO.GROUP_SIZE;

public class ContractReader implements Runnable {
    private static Random random = new Random();

    private int assetNum;

    private ContractReader(int assetNum) {
        this.assetNum = assetNum;
    }

    public static void init(int assetNum) {
        new Thread(new ContractReader(assetNum)).start();
    }

    @Override
    public void run() {
        List<List<Long>> assetGroupList = CollectionTools.split(genAssetIdList(), GROUP_SIZE / 10);
        int contractCount = 0;
        for (List<Long> assetGroup : assetGroupList) {
            List<Contract> contractList = new ArrayList<>();
            for(Long assetId : assetGroup) {
                int count = 8 + random.nextInt(4);
                for (int i = 0; i < count; i++) {
                    contractList.add(new Contract(random.nextInt(1_000_000), random.nextInt(300), assetId));
                    contractCount++;
                }
            }
            try {
                ContractCreator.createContractPipeline(contractList);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SleepTools.ms(20);
        }
        System.out.println("读取合同完成，总数为" + contractCount);
    }

    private List<Long> genAssetIdList() {
        List<Long> assetIdList = new ArrayList<>();
        for (int i = 0; i < assetNum; i++) {
            assetIdList.add((long) random.nextInt(1_000_000));
        }
        SleepTools.ms(20);
        return assetIdList;
    }

}
