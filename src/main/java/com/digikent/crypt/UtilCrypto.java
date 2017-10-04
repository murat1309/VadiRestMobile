package com.digikent.crypt;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

@Repository
public class UtilCrypto {

    private final Logger LOG = LoggerFactory.getLogger(UtilCrypto.class);

    public void testEncryptAndDecrypt() {

        try {
            String base64Str = encryptMessage("Selam naber", 123l);
            System.out.println(decryptMessage(base64Str, 123l));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
        Return encrypted base64String
     */
    public String encryptMessage(String messageText, Long userIdOrGroupId) throws Exception {
        LOG.info("mesaj encrypt edilecek. userId yada groupId = " + userIdOrGroupId);
        try {
            Key desKey = generateKey(userIdOrGroupId.toString());
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, desKey);
            byte[] textEncrypted = cipher.doFinal(messageText.getBytes());
            Base64 codec = new Base64();
            return codec.encodeBase64String(textEncrypted);
        } catch (Exception e) {
            LOG.error("encrypt edilirken hata oluştu message = " + e.getMessage());
            e.printStackTrace();
            throw new Exception();
        }
    }

    /*
        Return decrypted messageText
    */
    public String decryptMessage(String base64String, Long userIdOrGroupId) throws Exception {
        LOG.info("mesaj decrypt edilecek. userId yada groupId = " + userIdOrGroupId);
        try {
            Key desKey = generateKey(userIdOrGroupId.toString());
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, desKey);
            byte[] decodebyte = new Base64().decode(base64String);
            byte[] textDecrypted = cipher.doFinal(decodebyte);
            return new String(textDecrypted);

        } catch (Exception e) {
            LOG.error("decrypt edilirken hata oluştu message = " + e.getMessage());
            e.printStackTrace();
            throw new Exception();
        }
    }


    public Key generateKey(String userOrGroupId) {
        byte[] keyValue = userOrGroupId.getBytes();
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyValue = sha.digest(keyValue);
        keyValue = Arrays.copyOf(keyValue, 8);

        Key keys = new SecretKeySpec(keyValue, "DES");
        return keys;
    }
}