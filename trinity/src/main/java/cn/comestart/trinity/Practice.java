package cn.comestart.trinity;

import java.nio.charset.Charset;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Practice {
    public static void main(String[] args) {
        String ssr = "%s:%s:origin:%s:plain:%s/?obfsparam=&protoparam=&remarks=%s&group=";
        String server = "66.98.119.212";
        String server_port = "60314";
        String password = "NGE2NzViMT";
        String method = "aes-256-cfb";
        String remarks = "good";
        String format = String.format(ssr, server, server_port, method, base64Encode(password), base64Encode(remarks));
        System.out.println("ssr://" + base64Encode(format));
    }

    /**
     * 对本文进行base64解码，方法默认ISO_8859_1
     *
     * @param text
     * @return
     */
    public static String base64Decode(String text) {
        try {
            return new String(Base64.getDecoder().decode(text));
        } catch (Exception e) {
            System.out.println("base64解码失败！");
            return null;
        }
    }

    /**
     * 对本文进行base64转码，方法默认了utf8
     *
     * @param text
     * @return
     */
    public static String base64Encode(String text) {
        return base64Encode(text,UTF_8);
    }

    /**
     * 对本文进行base64转码，编码格式自定义
     *
     * @param text
     * @param charset
     * @return
     */
    public static String base64Encode(String text, Charset charset) {
        try {
            return new String(Base64.getEncoder().encode(text.getBytes(charset)));
        } catch (Exception e) {
            System.out.println("base64转码失败！");
            return null;
        }
    }
}