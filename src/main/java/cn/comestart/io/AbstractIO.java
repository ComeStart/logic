package cn.comestart.io;

import cn.comestart.utils.Consts;
import cn.comestart.utils.SleepTools;

import java.util.*;
import java.util.concurrent.*;

public class AbstractIO<T>  {

    public static ExecutorService executorService = Executors.newFixedThreadPool(Consts.THREAD_COUNT * 4);
    public static CompletionService<?> completionService = new ExecutorCompletionService<>(executorService);

    public static final int GROUP_SIZE = 100;

    private final AbstractDBSource<T> source;

    protected AbstractIO(AbstractDBSource<T> source) {
        this.source = source;
    }

    @SuppressWarnings("unchecked")
    public List<T> readEntities() throws InterruptedException, ExecutionException {
        List<T> entityList = new ArrayList<>();
        long startId = source.getStartId();
        long endId = source.getEndId();
        int count = source.getTotalCount();
        int groupCnt = (count - 1) / GROUP_SIZE + 1;

        // 分割每个线程的大小
        long[] froms = new long[groupCnt + 1];
        froms[0] = startId;
        for (int i = 1; i < groupCnt; i++) {
            froms[i] = startId + (endId - startId) * i / groupCnt;
        }
        froms[groupCnt] = endId;

        // 多线程读取
        for (int i = 0; i < groupCnt; i++) {
            completionService.submit(new EntityReader(froms[i], froms[i + 1]));
        }

        // 异步获取结果
        for (int i = 0; i < groupCnt; i++) {
            entityList.addAll((List<T>) completionService.take().get());
        }

        source.freeEntities(); // help gc
        return entityList;
    }

    @SuppressWarnings("unchecked")
    public void updateEntities(List<T> entityList) throws InterruptedException, ExecutionException {
        List<T> insertList = new ArrayList<>();
        for(T entity : entityList) {
            insertList.add(entity);
            if(insertList.size() >= GROUP_SIZE) {
                completionService.submit(new EntityWriter(insertList));
                insertList = new ArrayList<>();
            }
        }
        List<Future<T>> futureList = new ArrayList<>();
        if(!insertList.isEmpty()) {
            futureList.add(completionService.submit(new EntityWriter(insertList)));
        }

        for(Future future : futureList) future.get();
    }

    private class EntityReader implements Callable {
        private long fromId;
        private long toId;

        EntityReader(long fromId, long toId) {
            this.fromId = fromId;
            this.toId = toId;
        }

        @Override
        public List<T> call() {
            List<T> result = source.getEntities(fromId, toId);
//            System.out.println("获取entity，from " + fromId + " to " + toId + ", size = " + result.size());
            return result;
        }
    }

    private class EntityWriter implements Callable {
        private List<T> entity;

        EntityWriter(List<T> entity) {
            this.entity = entity;
        }

        @Override
        public Void call() throws Exception {
            SleepTools.ms(20);
            return null;
        }
    }
}
