package com.felix.cipherlibrary.des;


import com.felix.cipherlibrary.Base64;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Created by Felix.Zhong on 2018/10/9 10:39
 * Android 3DES���ܹ�����
 * https://www.cnblogs.com/oc-bowen/p/5622914.html
 * ���Լӽ��ܳɹ�
 */
public class AndroidDes3Util {

    // ��Կ ���Ȳ���С��24
    private final static String secretKey = "123456789012345678901234";
    // ���� ���п��� �ն˺�̨ҲҪԼ��
    private final static String iv = "01234567";
    // �ӽ���ͳһʹ�õı��뷽ʽ
    private final static String encoding = "utf-8";

    /**
     * 3DES����
     *
     * @param plainText ��ͨ�ı�
     * @return String
     * @throws Exception Exception
     */
    public static String encode(String plainText) throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);

        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
        return Base64.encodeToString(encryptData, Base64.DEFAULT);
    }

    /**
     * 3DES����
     *
     * @param encryptText �����ı�
     * @return String
     * @throws Exception Exception
     */
    public static String decode(String encryptText) throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

        byte[] decryptData = cipher.doFinal(Base64.decode(encryptText, Base64.DEFAULT));

        return new String(decryptData, encoding);
    }

    public static String decode(String encryptText, String secretKey) {
        Key deskey;
        byte[] decryptData;
        try {
            DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

            decryptData = cipher.doFinal(Base64.decode(encryptText, Base64.DEFAULT));
            return new String(decryptData, encoding);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
