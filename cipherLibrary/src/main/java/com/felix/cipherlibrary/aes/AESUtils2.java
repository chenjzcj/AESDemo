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
 * AES������;
 * ���ڶ���Ϣ���мӽ��ܵ�AES�����ࣻ
 * https://blog.csdn.net/Obelisk00/article/details/78248475
 *
 * @author MaLinQing
 */
public class AESUtils2 {

    private AESUtils2() {
    }//��̬�����࣬����Ҫnew����


    /**
     * �ӽ���ʱ��������룬�ڲ�ʹ�ÿ���ֱ��д�����ⲿ���ö�̬�������ֱ�����β��ж���
     */
    private static final String password = "gjh%^&(&  {}77";

    /**
     * AES�����ַ���
     *
     * @param content ��Ҫ�����ܵ��ַ���
     * @return ����
     */
    public static String encrypt(String content) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");//������Կ��������ָ��ΪAES�㷨,�����ִ�Сд

            kgen.init(128, new SecureRandom(password.getBytes()));//���ݴ�����ֽ�����,����һ��128λ�����Դ

            SecretKey secretKey = kgen.generateKey();// ����ԭʼ�Գ���Կ

            byte[] enCodeFormat = secretKey.getEncoded();// ���ԭʼ�Գ���Կ���ֽ�����

            //Generate AES key based on byte array
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");

            Cipher cipher = Cipher.getInstance("AES");//����ָ���㷨AES����������

            byte[] byteContent = content.getBytes("UTF-8"); //������ת���ֽ�����
            //(����Ҫ����Ϊutf-8)��Ȼ��������������ĺ�Ӣ�Ļ�����ľͻ����Ϊ����

            //Initialization cipher��
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] ciphertext = cipher.doFinal(byteContent);// ����

            return (Base64.encode(ciphertext));

        } catch (NoSuchPaddingException e) {
            LogUtils.e("����cipher�������쳣���쳣��Ϣ��{}", e);
        } catch (NoSuchAlgorithmException e) {
            LogUtils.e("�޷�ͨ��AES�����㷨����cipher�쳣���쳣��Ϣ��{}", e);
        } catch (UnsupportedEncodingException e) {
            LogUtils.e("byteContent-UTF-8�ַ�ת���쳣���쳣��Ϣ��{}", e);
        } catch (InvalidKeyException e) {
            LogUtils.e("cipher��ʼ��ʧ���쳣���쳣��Ϣ��{}", e);
        } catch (IllegalBlockSizeException e) {
            LogUtils.e("���ݳ��Ȳ���ȷ���±��ļ���ʧ�ܣ��쳣��Ϣ��{}", e);
        } catch (BadPaddingException e) {
            LogUtils.e("byteContentδ��ȷת��-���±��ļ���ʧ�ܣ��쳣��Ϣ��{}", e);
        }
        return null;
    }

    /**
     * Decrypt AES encrypted string
     *
     * @param content AES encrypted content
     * @return ����
     */
    public static String decrypt(String content) {
        try {
            //Construct a key generator, designated as the AES algorithm, not case sensitive.
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            //A random source of 128 bits is generated based on the incoming byte array.
            kgen.init(128, new SecureRandom(password.getBytes()));
            //����ԭʼ�Գ���Կ
            SecretKey secretKey = kgen.generateKey();
            //���ԭʼ�Գ���Կ���ֽ�����
            byte[] enCodeFormat = secretKey.getEncoded();
            //�����ֽ���������AES��Կ
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            // ����������
            Cipher cipher = Cipher.getInstance("AES");
            // ��ʼ��Ϊ����ģʽ��������
            cipher.init(Cipher.DECRYPT_MODE, key);

            //�����Ľ�����ֽ�����
            byte[] byteContent = Base64.decode(content);

            //���ܣ�
            byte[] byteDecode = cipher.doFinal(byteContent);

            return new String(byteDecode, "UTF-8");

        } catch (NoSuchAlgorithmException e) {
            LogUtils.e("�޷�ͨ��AES�����㷨����cipher�쳣���쳣��Ϣ��{}", e);
        } catch (NoSuchPaddingException e) {
            LogUtils.e("����cipher�������쳣���쳣��Ϣ��{}", e);
        } catch (InvalidKeyException e) {
            LogUtils.e("cipher��ʼ��ʧ���쳣���쳣��Ϣ��{}", e);
        } catch (IllegalBlockSizeException e) {
            LogUtils.e("���ݳ��Ȳ���ȷ���±��ļ���ʧ�ܣ��쳣��Ϣ��{}", e);
        } catch (BadPaddingException e) {
            LogUtils.e("byteContentδ��ȷת��-���±��ļ���ʧ�ܣ��쳣��Ϣ��{}", e);
        } catch (UnsupportedEncodingException e) {
            LogUtils.e("byteDecode-UTF-8�ַ�ת���쳣���쳣��Ϣ��{}", e);
        }
        return null;
    }

}

