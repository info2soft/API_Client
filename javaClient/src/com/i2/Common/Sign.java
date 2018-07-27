package com.i2.Common;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

//import sun.misc.BASE64Encoder;
//import org.apache.commons.codec.binary.Base64;
import com.i2.Utilities.Base64;

public class Sign {
    // 编码方式
    private static final String CONTENT_CHARSET = "UTF-8";

    // HMAC算法
    private static final String HMAC_ALGORITHM = "HmacSHA256";

    private static String byte2hex(final byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0xFF));
            if (stmp.length() == 1) hs = hs + "0" + stmp;
            else hs = hs + stmp;
        }
        return hs;
    }

    /**
     * @param signStr 被加密串
     * @param secret  加密密钥
     * @return
     * @brief 签名
     * @author gavinyao@tencent.com
     * @date 2014-08-13 21:07:27
     */
    public static String sign(String signStr, String secret)
            throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {

        String sig = null;
        Mac mac = Mac.getInstance(HMAC_ALGORITHM);
        System.out.println(secret);
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(CONTENT_CHARSET), mac.getAlgorithm());

        mac.init(secretKey);
        byte[] hash = mac.doFinal(signStr.getBytes(CONTENT_CHARSET));

//        for (byte b : hash) {
//            System.out.format("%02x", b);
//        }
//        System.out.println();

//        String hexBytes = byte2hex(hash);
//        System.out.println();
//        System.out.println(hexBytes);
//        System.out.println();

//        BigInteger hash = new BigInteger(1, digest);
//        String hmac = hash.toString(16);
//
//        if (hmac.length() % 2 != 0) {
//            hmac = "0" + hmac;
//        }
//        
//        System.out.println(hmac);

//        StringBuilder builder = new StringBuilder(rawHmac.length);
//        for (byte b : rawHmac) {
//            int i = b & 0xff;
//            if (i <= 0xf) {
//                builder.append("0");
//            }
//            builder.append(Integer.toHexString(i));
//        }

//        byte[] hexBytes = new Hex().encode(rawHmac);

//        System.out.println(new String(hexBytes, "UTF-8"));

        // base64
//        sig = new String(new BASE64Encoder().encode(hexBytes.getBytes()).getBytes());
//        sig = new String(Base64.encodeBase64(hash));

//        String macStr = builder.toString();

        sig = new String(Base64.encode(hash));
//        sig = new String(hash,CONTENT_CHARSET);
//        System.out.println(sig);

        return sig;
    }

    public static String makeSignPlainText(TreeMap<String, Object> requestParams, String requestMethod, String requestHost, String requestPath) {

        String retStr = "";
        retStr += buildParamStr1(requestParams, requestMethod);
        System.out.println(retStr);
        return retStr;
    }

    protected static String buildParamStr1(TreeMap<String, Object> requestParams, String requestMethod) {
        return buildParamStr(requestParams, requestMethod);
    }

    protected static String buildParamStr(TreeMap<String, Object> requestParams, String requestMethod) {

        String retStr = "";
        for (String key : requestParams.keySet()) {
            //排除上传文件的参数
            if (requestMethod == "POST" && requestParams.get(key).toString().substring(0, 1).equals("@")) {
                continue;
            }
            if (retStr.length() == 0) {
//                retStr += '?';
            } else {
                retStr += '&';
            }
            retStr += key + '=' + requestParams.get(key).toString();

        }
        return retStr;
    }
}
