package cn.comestart.deliver.deliver;

import cn.comestart.deliver.deliver.model.PayResultModel;
import cn.comestart.deliver.deliver.model.PayResultType;

import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

public class MsgFactory {
    public static PayResultModel genPayResult(byte[] data) {
        return parsePayResult(parseParams(decode(data)));
    }

    private static String decode(byte[] data) {
        return new String(data, UTF_8);
    }

    private static Map<String, Object> parseParams(String msg) {
        Map<String, Object> result = new HashMap<>();
        result.put("payResultType", PayResultType.valueOf(msg));
        return result;
    }

    private static PayResultModel parsePayResult(Map<String, Object> params) {
        PayResultType payResultType = (PayResultType) params.get("payResultType");
        PayResultModel payResultModel = payResultType.createPayResultModelProxy();
        payResultModel.setPayResultType(payResultType);

        System.out.println("PayResultModel, phase = " + payResultModel.getPhase() + ", feeType = " + payResultModel.getFeeType());
        return payResultModel;
    }
}
