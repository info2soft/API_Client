import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;


public class hmac {

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {

        String basStr = "Token=IswVAJrotlDLegFJ&mobile=18566668888&pwd=098f6bcd4621d373cade4e832627b4f6&role=8&service=User.createAccount&user.name=test";
        String keyStr = "bYDwcp9SC5iYgWFzvrmRazXzBNyUE4LC";
        SecretKey secretKey = null;

        String result;

        try {
            String data = basStr;
            String key = keyStr;
            // Get an hmac_sha1 key from the raw key bytes
            byte[] keyBytes = key.getBytes();
            SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA256");

            // Get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);

            // Compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(data.getBytes());

            // Convert raw bytes to Hex
            byte[] hexBytes = new Hex().encode(rawHmac);

            String sig = new String(Base64.encodeBase64(hexBytes)).trim();
            System.out.println("MAC : " + sig);
            //  Covert array of Hex bytes to a String
            result = new String(hexBytes, "ISO-8859-1");
            System.out.println("MAC : " + result);
        } catch (Exception e) {

        }

        byte[] keyBytes = keyStr.getBytes();
        secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");

        Mac mac = Mac.getInstance("HmacSHA256");

        mac.init(secretKey);

        byte[] text = basStr.getBytes();

        String sig = new String(Base64.encodeBase64(mac.doFinal(text))).trim();

        System.out.println(sig);


        result = HmacSHA256(basStr, keyStr);
        System.out.println(result);

    }

    public static String computeSignature(String baseString, String keyString) throws GeneralSecurityException, UnsupportedEncodingException {


        SecretKey secretKey = null;

        byte[] keyBytes = keyString.getBytes();
        secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");

        Mac mac = Mac.getInstance("HmacSHA256");

        mac.init(secretKey);

        byte[] text = baseString.getBytes();

        return new String(Base64.encodeBase64(mac.doFinal(text))).trim();

    }

    public static String HmacSHA256(String value, String key) {
        try {
            // Get an hmac_sha1 key from the raw key bytes
            byte[] keyBytes = key.getBytes();
            SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA256");

            // Get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);

            // Compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(value.getBytes());

            // Convert raw bytes to Hex
            byte[] hexBytes = new Hex().encode(rawHmac);

            //  Covert array of Hex bytes to a String
            return new String(hexBytes, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
