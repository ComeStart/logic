package cn.comestart.contract;

import java.util.Map;
import java.util.regex.Matcher;

public class ContractService {
    private ContractService() {
    }

    private static ContractService instance = new ContractService();

    public static ContractService getInstance() {
        return instance;
    }

    private ContractIO contractIO = ContractIO.getInstance();
    public String createContract(Contract contract) {
        Map<String, String> params = contractIO.getParams(contract.getId());
        String templateContent = contractIO.getTemplateContent(contract.getTemplateId());
        return makeContractFind(templateContent, params);
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
