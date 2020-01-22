package cn.comestart.contract.service;

import cn.comestart.contract.io.ParamsIO;
import cn.comestart.contract.io.TemplateIO;
import cn.comestart.contract.vo.Application;
import cn.comestart.utils.Consts;
import cn.comestart.utils.RandomTools;
import cn.comestart.utils.SleepTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.*;

public class ContractCreateService {

    private static BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(1000);
    private static ExecutorService executorService = new ThreadPoolExecutor(50, 50,
            1000L, TimeUnit.SECONDS, workQueue);
    private static CompletionService<?> completionService = new ExecutorCompletionService<>(executorService);

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();

        int N = 100;
        int count = 0;
        for (int i = 0; i < N; i++) {
            ContractDealer contractDealer = new ContractDealer();
            completionService.submit(contractDealer);
        }
        for (int i = 0; i < N; i++) {
            Application application = (Application) completionService.take().get();
            count += application.getContractList().size();
        }

        System.out.println("生成" + count + "个合同耗时" + (System.currentTimeMillis() - start) + " ms");


        TreeMap<Integer, String> map = new TreeMap<>();

    }



    private static class ContractDealer implements Callable {

        @Override
        public Object call() throws Exception {
            Application application = receive();
            Map<String, String> params = findContractParams(application.getAid());
            for(Application.Contract contract : application.getContractList()) {
                contract.setContent(generateContract(contract.getTemplateId(), params));
                uploadContract(contract);
                updateContract(contract);
            }
            return application;
        }


        private static ContractService contractService = ContractService.getInstance();
        private static ParamsIO paramsIO = ParamsIO.getInstance();
        private static TemplateIO templateIO = TemplateIO.getInstance();

        /**
         * 收到MQ消息生成合同，包含aid, contractIds
         */
        public Application receive() {
            Application application = new Application();

            // gen aid
            application.setAid(10_000_000L + RandomTools.nextInt(1_000_000));

            // gen contracts;
            List<Application.Contract> contractList = new ArrayList<>();
            int n = 8 + RandomTools.nextInt(4);
            for (int i = 0; i < n; i++) {
                Application.Contract contract = new Application.Contract();
                contract.setContractId(1_000_000L + RandomTools.nextInt(1_000_000));
                contract.setTemplateId(RandomTools.nextInt(300));
                contractList.add(contract);
            }
            application.setContractList(contractList);

            return application;
        }

        /**
         * 获取合同参数
         */
        public Map<String, String> findContractParams(long aid) {
            return paramsIO.getParams(aid);
        }

        /**
         * 生成合同
         */
        public String generateContract(long templateId, Map<String, String> params) {
            return contractService.makeContractFind(templateId, params);
        }

        /**
         * 上传合同
         *
         * @param contract
         * @return 合同地址
         */
        public Application.Contract uploadContract(Application.Contract contract) {
            SleepTools.ms(10);
            String url = "http://www.comestart.cn/fs/" + contract.getContent().substring(0, 20);
            contract.setUrl(url);
            return contract;
        }

        /**
         * 更新合同元数据
         */
        public void updateContract(Application.Contract contract) {
            SleepTools.ms(20);
        }
    }
}
