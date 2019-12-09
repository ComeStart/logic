package cn.comestart.contract.io;

import cn.comestart.utils.Consts;
import cn.comestart.utils.SleepTools;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class ParamsIO {
    private static ParamsIO instance = new ParamsIO();
    public static ParamsIO getInstance() {return instance;}
    private ParamsIO() {}

    public Map<String, String> getParams(long contractId) {
        Map<String, String> result = new HashMap<>();
        for (int i = 0; i < 200; i++) {
            result.put("param" + i, "value" + contractId + i);
            if (i % 20 == 0) SleepTools.ms(20);
        }
        return result;
    }

    public Map<String, String> getParamsParallel(long contractId) {
        Map<String, String> result = new HashMap<>();
        for (int i = 0; i < 200; i++) {
            result.put("param" + i, "value" + contractId + i);
        }
        SleepTools.ms(20);
        return result;
    }

    public Map<String, String> getWarmedParams(long contractId) {
        Map<String, String> result = new HashMap<>();
        for (int i = 0; i < 200; i++) {
            result.put("param" + i, "value" + contractId + i);
        }
        return result;
    }

    private static ExecutorService executorService = Executors.newFixedThreadPool(1);
    private static ExecutorService paramsSubService = Executors.newFixedThreadPool(1);

    private LoadingCache<Long, Map<String, String>> cache = CacheBuilder.newBuilder()
            .maximumSize(200)
            .expireAfterAccess(30, TimeUnit.SECONDS)
            .build(new CacheLoader<Long, Map<String, String>>() {
                @Override
                public Map<String, String> load(@SuppressWarnings("NullableProblems") Long assetId) throws Exception {
                    return paramFutures.remove(assetId).get();
                }
            });

    private Map<Long, Future<Map<String, String>>> paramFutures = new ConcurrentHashMap<>();
    public void initParams(long assetId) {
        paramFutures.put(assetId, executorService.submit(new ParamReader(assetId)));
    }

    public Map<String, String> getParamsFromFuture(long assetId) {
        return cache.getUnchecked(assetId);
    }

    public Future<Map<String, String>> getParamsFuture(long assetId) {
        return paramFutures.get(assetId);
    }

    private static class ParamReader implements Callable<Map<String, String>> {

        private long assetId;

        public ParamReader(long assetId) {
            this.assetId = assetId;
        }

        @Override
        public Map<String, String> call() throws Exception {
            Map<String, String> result = new HashMap<>();
            List<Future<Map<String, String>>> futureList = new ArrayList<>();
            for (int i=0;i<10;i++) {
                futureList.add(paramsSubService.submit(new ParamSubReader(assetId, i)));
            }
            for (Future<Map<String, String>> future : futureList) {
                result.putAll(future.get());
            }
            return result;

        }
    }

    private static class ParamSubReader implements Callable<Map<String, String>> {
        private long assetId;
        private int groupNum;

        private ParamSubReader(long assetId, int groupNum) {
            this.assetId = assetId;
            this.groupNum = groupNum;
        }

        @Override
        public Map<String, String> call() throws Exception {
            Map<String, String> result = new HashMap<>();
            for (int i = groupNum*20; i < (groupNum+1)*20; i++) {
                result.put("param" + i, "value" + assetId + i);
            }
            SleepTools.ms(20);
            return result;
        }
    }
}
