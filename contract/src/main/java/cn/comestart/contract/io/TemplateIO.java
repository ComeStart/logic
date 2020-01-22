package cn.comestart.contract.io;

import cn.comestart.contract.vo.Template;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TemplateIO {
    private static TemplateIO instance = new TemplateIO();
    public static TemplateIO getInstance() {return instance;}
    private TemplateIO() {}

    private static LoadingCache<Long, String> cache = CacheBuilder.newBuilder()
            .maximumSize(300)
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .build(new CacheLoader<Long, String>() {
                @Override
                public String load(@SuppressWarnings("NullableProblems") Long templateId) throws Exception {
                    return readFile(templateId);
                }
            });

    private static LoadingCache<Long, Template> templateCache = CacheBuilder.newBuilder()
            .maximumSize(300)
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .build(new CacheLoader<Long, Template>() {
                @Override
                public Template load(@SuppressWarnings("NullableProblems") Long templateId) throws Exception {
                    String templateContent = readFile(templateId);

                    List<Template.TemplateElem> elems = new ArrayList<>();
                    int ePos = 0;
                    do {
                        int sPos = templateContent.indexOf("${", ePos);
                        if(sPos == -1) break;
                        String content = templateContent.substring(ePos, sPos);
                        ePos = templateContent.indexOf("}", sPos) + 1;
                        String param = templateContent.substring(sPos+2, ePos-1);
                        elems.add(new Template.TemplateElem(content, param));
                    } while(true);
                    return new Template(templateId, elems);
                }
            });

    private static String readFile(Long templateId) throws Exception{
        try (InputStream is = new FileInputStream("src/main/resources/ContractTemplate")) {
            int iAvail = is.available();
            byte[] bytes = new byte[iAvail];
            int readRes = is.read(bytes);
            return new String(bytes);
        }
    }

    public String getTemplateContent(long templateId) {
        return cache.getUnchecked(templateId);
    }

    public Template getTemplate(long templateId) {
        return templateCache.getUnchecked(templateId);
    }

}
