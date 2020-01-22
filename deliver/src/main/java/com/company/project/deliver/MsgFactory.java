package com.company.project.deliver;

import com.company.project.deliver.model.PayResultModel;
import com.company.project.deliver.model.PayResultType;

import java.util.HashMap;
import java.util.Map;

import static com.company.project.deliver.model.PayResultType.*;
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
        PayResultModel payResultModel = new PayResultModel();
        payResultModel.setPayResultType((PayResultType) params.get("payResultType"));
        return payResultModel;
    }
}
