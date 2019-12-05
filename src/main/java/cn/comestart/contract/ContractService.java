package cn.comestart.contract;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ContractService {
    private ContractService() {
    }

    private static ContractService instance = new ContractService();

    public static ContractService getInstance() {
        return instance;
    }

    private ParamsIO paramsIO = ParamsIO.getInstance();
    private TemplateIO templateIO = TemplateIO.getInstance();

    public String createContract(Contract contract, boolean useFind) {
        Map<String, String> params = paramsIO.getParams(contract.getId());
        String templateContent = templateIO.getTemplateContent(contract.getTemplateId());
        return useFind ? makeContractFind(templateContent, params) : makeContract(templateContent, params);
    }

    public String createContractAsync(Contract contract, boolean useFind) {
        Map<String, String> params = paramsIO.getParamsFromFuture(contract.getAssetId());
        String templateContent = templateIO.getTemplateContent(contract.getTemplateId());
        return useFind ? makeContractFind(templateContent, params) : makeContract(templateContent, params);
    }

    private String makeContract(String templateContent, Map<String, String> params) {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String param = paramPattern(entry.getKey());
            templateContent = templateContent.replaceAll(param, entry.getValue());
        }
        return templateContent;
    }

    private String makeContractFind(String templateContent, Map<String, String> params) {
        StringBuilder builder = new StringBuilder();
        int ePos = 0;
        do {
            int sPos = templateContent.indexOf("${", ePos);
            if(sPos == -1) break;
            builder.append(templateContent, ePos, sPos);
            ePos = templateContent.indexOf("}", sPos) + 1;
            builder.append(params.get(templateContent.substring(sPos+2, ePos-1)));
        } while(true);
        return builder.toString();
    }

    private String paramPattern(String param) {
        return "\\$\\{" + param + "}";
    }
}
