package cn.comestart.io;

import cn.comestart.utils.SleepTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

public abstract class AbstractSource<T> {
    private long startId;
    private long endId;
    private int totalCount;
    private TreeMap<Long, T> entities = new TreeMap<>();

    protected void init(int newCount, int oldCount, int newBase, int newRandom, int oldBase, int oldRandom, long initId) {
        System.out.println("开始生成数据……");

        Random random = new Random();
        long startMillis = System.currentTimeMillis();
        startId = initId + random.nextInt(1_000_000);
        totalCount = newCount + oldCount;

        long id = startId;
        for (int i = 0; i < newCount; i++) {
            entities.put(id, newEntity(id, newBase + random.nextInt(newRandom)));
            id += 1 + random.nextInt(2);
        }
        for (int i = 0; i < oldCount; i++) {
            entities.put(id, newEntity(id, oldBase + random.nextInt(oldRandom)));
            id += 1 + random.nextInt(2);
        }

        endId = id;
        System.out.println("生成数据完成。startId=" + startId + ", endId=" + endId
                + "，总共消耗时间：" + (System.currentTimeMillis() - startMillis) + " ms");
    }

    abstract protected T newEntity(long id, long amount);

    public long getStartId() {
        return startId;
    }

    public long getEndId() {
        return endId;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public List<T> getEntities(long from, long to) {
        List<T> results = new ArrayList<>(entities.subMap(from, to).values());
        SleepTools.ms(20);
        return results;
    }

    public void freeEntities() {
        entities = null;
    }
}
