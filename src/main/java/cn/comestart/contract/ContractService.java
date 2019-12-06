package cn.comestart.contract;

import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

public class ContractService {
    private ContractService() {
    }

    private static ContractService instance = new ContractService();

    public static ContractService getInstance() {
        return instance;
    }

    private ParamsIO paramsIO = ParamsIO.getInstance();
    private TemplateIO templateIO = TemplateIO.getInstance();

    public String createContract(Contract contract, boolean useFind, int flag) {
        Map<String, String> params;
        if (flag == 0) params = paramsIO.getParams(contract.getId());
        else if (flag == 1) params = paramsIO.getParamsFromFuture(contract.getAssetId());
        else params = paramsIO.getParamsParallel(contract.getAssetId());

        if (!useFind) makeContract(templateIO.getTemplateContent(contract.getTemplateId()), params);
        return makeContractFind(contract.getTemplateId(), params);
    }

    private String makeContract(String templateContent, Map<String, String> params) {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String param = paramPattern(entry.getKey());
            templateContent = templateContent.replaceAll(param, entry.getValue());
        }
        return templateContent;
    }

    private String makeContractFind(long templateId, Map<String, String> params) {
        StringBuilder builder = new StringBuilder();
        List<TemplateIO.Template> templateList = templateIO.getTemplate(templateId);
        for (TemplateIO.Template t : templateList) {
            if(StringUtils.isNotEmpty(t.getContent())) builder.append(t.getContent());
            if(StringUtils.isNotEmpty(t.getParam())) builder.append(params.get(t.getParam()));
        }
        return builder.toString();
    }

    private String paramPattern(String param) {
        return "\\$\\{" + param + "}";
    }
}
