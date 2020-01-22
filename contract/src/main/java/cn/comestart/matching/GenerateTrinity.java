package cn.comestart.matching;

import cn.comestart.matching.io.AssetIO;
import cn.comestart.matching.io.FundIO;
import cn.comestart.matching.io.TrinityIO;
import cn.comestart.matching.vo.Asset;
import cn.comestart.matching.vo.Fund;
import cn.comestart.matching.vo.Trinity;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.*;

public class GenerateTrinity {
    private static final long MAX_INVEST_AMOUNT = 250L;

    public static void main(String[] args) {
        GenerateTrinity instance = new GenerateTrinity();
        instance.makeInvest();
    }

    private AssetIO assetIO = AssetIO.getInstance();
    private FundIO fundIO = FundIO.getInstance();
    private TrinityIO trinityIO = TrinityIO.getInstance();

    private void makeInvest() {
        try {
            long totalStart = System.currentTimeMillis();

            long startMillis = System.currentTimeMillis();
            List<Asset> assetList = assetIO.readEntities();
            System.out.println("已获取资产列表，size = " + assetList.size() + "，用时 "
                    + (System.currentTimeMillis() - startMillis) + " ms");

            startMillis = System.currentTimeMillis();
            List<Fund> fundList = fundIO.readEntities();
            fundList.add(new Fund(1, 1_000_000)); // 超级账户
            System.out.println("已获取资金列表，size = " + fundList.size() + "，用时 "
                    + (System.currentTimeMillis() - startMillis) + " ms");

            startMillis = System.currentTimeMillis();
            List<Trinity> trinityList = invest(assetList, fundList);
            System.out.println("匹配成功，size = " + trinityList.size() + "，用时 "
                    + (System.currentTimeMillis() - startMillis) + " ms");

//            verifyTrinity(trinityList, assetList, fundList);

            startMillis = System.currentTimeMillis();
            insertTrinity(trinityList);
            System.out.println("插入trinity完成，用时 "
                    + (System.currentTimeMillis() - startMillis) + " ms");

            startMillis = System.currentTimeMillis();
            updateAsset(assetList);
            System.out.println("更新asset完成，用时 "
                    + (System.currentTimeMillis() - startMillis) + " ms");

            startMillis = System.currentTimeMillis();
            updateFund(fundList);
            System.out.println("更新fund完成，用时 "
                    + (System.currentTimeMillis() - startMillis) + " ms");

            System.out.println("总用时 " + (System.currentTimeMillis() - totalStart) + " ms");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void updateFund(List<Fund> fundList) throws ExecutionException, InterruptedException {
        fundIO.updateEntities(fundList);
    }

    private void updateAsset(List<Asset> assetList) throws ExecutionException, InterruptedException {
        assetIO.updateEntities(assetList);
    }


    private void insertTrinity(List<Trinity> trinityList) throws ExecutionException, InterruptedException {
        trinityIO.updateEntities(trinityList);
    }

    private void verifyTrinity(List<Trinity> trinityList, List<Asset> assetList, List<Fund> fundList) {
        for (Asset asset : assetList) {
            long amount = 0L;
            for (Trinity trinity : trinityList) {
                if (trinity.getAssetId() == asset.getId()) {
                    amount += trinity.getAmount();
                }
            }
            if (amount != asset.getAmount())
                System.out.println("资产id " + asset.getId() + " 金额 " + asset.getAmount() + "，匹配总金额 = " + amount);
        }
        for (Fund fund : fundList) {
            long amount = 0L;
            for (Trinity trinity : trinityList) {
                if (trinity.getFundId() == fund.getId()) {
                    amount += trinity.getAmount();
                }
            }
            if (amount != fund.getAmount())
                System.out.println("资金id " + fund.getId() + " 金额 " + fund.getAmount() + "，匹配总金额 = " + amount);
        }
    }

    private List<Trinity> invest(List<Asset> assetList, List<Fund> fundList) {
        List<Trinity> result = new ArrayList<>();
        Queue<Asset> assetQueue = new PriorityQueue<>(new Asset.AssetComparator());
        assetQueue.addAll(assetList);

        for (Fund fund : fundList) {
            while (!fund.isFull()) {
                Asset asset = assetQueue.poll();
                if (asset == null) break;

                long investAmount;
                if (fund.restAmount() <= MAX_INVEST_AMOUNT) {
                    if (asset.restAmount() > fund.restAmount()) {
                        investAmount = fund.restAmount();
                    } else {
                        investAmount = asset.restAmount();
                    }
                } else {
                    if (asset.restAmount() > MAX_INVEST_AMOUNT) {
                        investAmount = MAX_INVEST_AMOUNT;
                    } else {
                        investAmount = asset.restAmount();
                    }
                }
                asset.invest(investAmount);
                if (!asset.isFull()) assetQueue.add(asset);
                fund.invest(investAmount);
                result.add(new Trinity(asset.getId(), fund.getId(), investAmount));

//                System.out.println("资产id " + asset.getId() + " 被资金id " + fund.getId() + " 投资 " + investAmount + "元");
            }
//            if (assetQueue.isEmpty()) System.out.println("资产没了，资金id " + fund.getId() + "无处可投，剩余资金" + fund.restAmount());
        }

        return result;
    }
}











