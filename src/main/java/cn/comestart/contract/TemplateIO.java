package cn.comestart.contract;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public class TemplateIO {
    private static TemplateIO instance = new TemplateIO();
    public static TemplateIO getInstance() {return instance;}
    private TemplateIO() {}

    private static LoadingCache<Long, String> cache = CacheBuilder.newBuilder()
            .maximumSize(200)
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .build(new CacheLoader<Long, String>() {
                @Override
                public String load(@SuppressWarnings("NullableProblems") Long templateId) throws Exception {
                    try (InputStream is = new FileInputStream("src/main/resources/ContractTemplate")) {
                        int iAvail = is.available();
                        byte[] bytes = new byte[iAvail];
                        int readRes = is.read(bytes);
                        return new String(bytes);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return "";
                }
            });

    public String getTemplateContent(long templateId) {
        return cache.getUnchecked(templateId);
    }
}
