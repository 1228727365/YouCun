package com.api.you_cun.tool;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AES128CBC {
    // 算法模式：AES-128-CBC（带向量）
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String ALGORITHM = "AES";
    private static final String iv="you_cunapp200464";
    /**
     * AES-128-CBC加密（需要向量）
     * @param key 密钥（必须16字节）
     * @param data 待加密数据
     * @return 加密后Base64编码字符串
     * @throws Exception 加密异常
     */
    public static String encrypt(String key, String data){

        try {
            if (key.getBytes().length != 16) {
                throw new IllegalArgumentException("密钥必须是16字节");
            }
            if (iv.getBytes().length != 16) {
                throw new IllegalArgumentException("向量必须是16字节");
            }

            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

            byte[] encrypted = cipher.doFinal(data.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encrypted);
        }catch (Exception e){
            e.printStackTrace();
            return "加密失败";
        }
    }

    /**
     * AES-128-CBC解密（需要相同向量）
     * @param key 密钥（必须16字节）
     * @param encryptedData 加密后Base64字符串
     * @return 解密后的原始数据
     * @throws Exception 解密异常
     */
    public static String decrypt(String key, String encryptedData){
        try{
        if (key.getBytes().length != 16 || iv.getBytes().length != 16) {
            throw new IllegalArgumentException("密钥和向量必须都是16字节");
        }

        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

        byte[] decoded = Base64.getDecoder().decode(encryptedData);
        byte[] decrypted = cipher.doFinal(decoded);
        return new String(decrypted, "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
            return "解密失败";
        }
    }
}
