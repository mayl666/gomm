package com.gome.upm.common.des;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.gome.upm.common.util.SecretUtils;


/**
 * DES 加密--工具类
 */
public class DES {

    private static byte[] eniv = { 1, 2, 3, 4, 5, 6, 7, 8 };

    /**
     * des加密
     * 
     * @param encryptString
     * @param encryptKey
     * @return
     * @throws Exception
     */
    public static String encryptDES(String encryptString, String encryptKey) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(eniv);
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
        return Base64.encode(encryptedData);
    }

    private static byte[] deiv = { 1, 2, 3, 4, 5, 6, 7, 8 };

    /**
     * des 解密
     * 
     * @param decryptString
     * @param decryptKey
     * @return
     * @throws Exception
     */
    public static String decryptDES(String decryptString, String decryptKey) throws Exception {
        byte[] byteMi = Base64.decode(decryptString);
        IvParameterSpec zeroIv = new IvParameterSpec(deiv);
        SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte decryptedData[] = cipher.doFinal(byteMi);
        return new String(decryptedData);
    }

     public static void main(String[] args) {
     String plaintext = "liyujian";
     try {
     String secretKey = SecretUtils.generateWithLength(20);
     System.out.println(secretKey);
	System.out.println(DES.encryptDES(plaintext, secretKey ));
     String decryptDES = DES.decryptDES("24L6JCENcr94H3RnSFAR7w==", secretKey);
     System.out.println(decryptDES);
     } catch (Exception e) {
     e.printStackTrace();
     }
     }
}

