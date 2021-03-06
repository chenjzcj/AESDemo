package com.felix.cipherlibrary.aes;

/**
 * Created by Felix.Zhong on 2018/9/29 15:20
 */


import com.blankj.utilcode.util.LogUtils;
import com.felix.cipherlibrary.encode.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 * AES tool class;
 * AES tool class used for encryption and decryption of information;
 * https://blog.csdn.net/Obelisk00/article/details/78248475
 *
 * @author MaLinQing
 */
public class AESUtils2 {

    private AESUtils2() {
    }//Static tool classes do not require new objects;


    /**
     * The password needed for encryption and decryption can be written to death directly by internal use, and the dynamic transmission of external calls can be defined directly in the parameters.
     */
    private static final String password = "gjh%^&(&  {}77";

    /**
     * AES encrypted string
     *
     * @param content String that needs to be encrypted
     * @return ciphertext
     */
    public static String encrypt(String content) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");//Construct a key generator, designated as the AES algorithm, not case sensitive.

            kgen.init(128, new SecureRandom(password.getBytes()));//A random source of 128 bits is generated based on the incoming byte array.

            SecretKey secretKey = kgen.generateKey();// Generate primitive symmetric key

            byte[] enCodeFormat = secretKey.getEncoded();// Gets the byte array of the original symmetric key.

            //Generate AES key based on byte array
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");

            Cipher cipher = Cipher.getInstance("AES");//Generate cipher based on the specified algorithm AES

            byte[] byteContent = content.getBytes("UTF-8"); //Convert packets to byte arrays
            //(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码

            //Initialization cipher，
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] ciphertext = cipher.doFinal(byteContent);// 加密

            return (Base64.encode(ciphertext));

        } catch (NoSuchPaddingException e) {
            LogUtils.e("生成cipher密码器异常！异常信息：{}", e);
        } catch (NoSuchAlgorithmException e) {
            LogUtils.e("无法通过AES加密算法生成cipher异常！异常信息：{}", e);
        } catch (UnsupportedEncodingException e) {
            LogUtils.e("byteContent-UTF-8字符转换异常！异常信息：{}", e);
        } catch (InvalidKeyException e) {
            LogUtils.e("cipher初始化失败异常！异常信息：{}", e);
        } catch (IllegalBlockSizeException e) {
            LogUtils.e("数据长度不正确导致报文加密失败！异常信息：{}", e);
        } catch (BadPaddingException e) {
            LogUtils.e("byteContent未正确转换-导致报文加密失败！异常信息：{}", e);
        }
        return null;
    }

    /**
     * Decrypt AES encrypted string
     *
     * @param content AES encrypted content
     * @return 明文
     */
    public static String decrypt(String content) {
        try {
            //Construct a key generator, designated as the AES algorithm, not case sensitive.
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            //A random source of 128 bits is generated based on the incoming byte array.
            kgen.init(128, new SecureRandom(password.getBytes()));
            //产生原始对称密钥
            SecretKey secretKey = kgen.generateKey();
            //获得原始对称密钥的字节数组
            byte[] enCodeFormat = secretKey.getEncoded();
            //根据字节数组生成AES密钥
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            // 创建密码器
            Cipher cipher = Cipher.getInstance("AES");
            // 初始化为解密模式的密码器
            cipher.init(Cipher.DECRYPT_MODE, key);

            //将密文解码成字节数组
            byte[] byteContent = Base64.decode(content);

            //解密；
            byte[] byteDecode = cipher.doFinal(byteContent);

            return new String(byteDecode, "UTF-8");

        } catch (NoSuchAlgorithmException e) {
            LogUtils.e("无法通过AES加密算法生成cipher异常！异常信息：{}", e);
        } catch (NoSuchPaddingException e) {
            LogUtils.e("生成cipher密码器异常！异常信息：{}", e);
        } catch (InvalidKeyException e) {
            LogUtils.e("cipher初始化失败异常！异常信息：{}", e);
        } catch (IllegalBlockSizeException e) {
            LogUtils.e("数据长度不正确导致报文加密失败！异常信息：{}", e);
        } catch (BadPaddingException e) {
            LogUtils.e("byteContent未正确转换-导致报文加密失败！异常信息：{}", e);
        } catch (UnsupportedEncodingException e) {
            LogUtils.e("byteDecode-UTF-8字符转换异常！异常信息：{}", e);
        }
        return null;
    }

}

