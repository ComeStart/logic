package cn.comestart.contract;

import cn.comestart.io.AbstractDBSource;
import cn.comestart.io.AbstractIO;
import cn.comestart.utils.SleepTools;
import javafx.util.Pair;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.*;

public class ContractIO extends AbstractIO<Contract>{

    private static ContractIO instance = new ContractIO();
    public static ContractIO getInstance() {return instance;}
    private static Random random = new Random();

    private ContractIO() {
        this(null);
    }

    protected ContractIO(AbstractDBSource<Contract> source) {
        super(source);
    }

    public Map<String, String> getParams(long contractId) {
        Map<String, String> result = new HashMap<>();
        for (int i = 0; i < 200; i++) {
            result.put("param" + i, "value" + contractId + i);
        }
        return result;
    }

    public String getTemplateContent(long templateId) {
        try(InputStream is = new FileInputStream("src/main/resources/ContractTemplate");){
            int iAvail = is.available();
            byte[] bytes = new byte[iAvail];
            int readRes = is.read(bytes);
            return new String(bytes);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public List<Contract> getEmptyContract(int num) {
        List<Contract> result = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            result.add(new Contract(random.nextInt(1_000_000), random.nextInt(1_000_000)));
        }
        SleepTools.ms(20);
        return result;
    }

    public List<Contract> getEmptyContractAsync(int num) throws InterruptedException, ExecutionException {
        List<Contract> result = new ArrayList<>();
        for (int i = 0; i < num; i+=AbstractIO.GROUP_SIZE) {
            AbstractIO.completionService.submit(new ContractReader());
        }
        for (int i = 0; i < num; i+=AbstractIO.GROUP_SIZE) {
            result.addAll((List<Contract>) AbstractIO.completionService.take().get());
        }
        return result;
    }

    public void updateContract(Contract contract) {
        SleepTools.ms(20);
    }

    public String uploadContract(String content) {
        SleepTools.ms(10);
        return "http://www.comestart.cn/fs/" + content.substring(0, 20);
    }

    public Future<String> uploadContractAsync(Contract contract) {
        return AbstractIO.completionService.submit(new ContractUploader(contract));
    }

    private static class ContractReader implements Callable {

        @Override
        public List<Contract> call() throws Exception {
            List<Contract> result = new ArrayList<>();
            for (int i = 0; i < AbstractIO.GROUP_SIZE; i++) {
                result.add(new Contract(random.nextInt(1_000_000), random.nextInt(1_000_000)));
            }
            SleepTools.ms(20);
            return result;
        }
    }

    private static class ContractUploader implements Callable {
        private Contract contract;

        private ContractUploader(Contract contract) {
            this.contract = contract;
        }


        @Override
        public String call() throws Exception {
            SleepTools.ms(10);
            String url = "http://www.comestart.cn/fs/" + contract.getContent().substring(0, 20);
            contract.setUrl(url);
            return url;
        }
    }
}
