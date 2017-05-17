package com.des.cn;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * 
 * FileName��EncryptUtil.java                
 *             
 * Description��DES���ܽ��ܹ�����
 *
 */
public class Main {
    
    /**
     * ��־�������
     */
    
    /**
     * �����߼�����
     * @param message
     * @param key
     * @return
     * @throws Exception
     */
    private static byte[] encryptProcess(String message, String key) throws Exception { 
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding"); 
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8")); 
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES"); 
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec); 
        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8")); 
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv); 
        return cipher.doFinal(message.getBytes("UTF-8")); 
    }
    
    /**
     * �����߼�����
     * @param message
     * @param key
     * @return
     * @throws Exception
     */ 
    private static String decryptProcess(String message,String key) throws Exception { 
            byte[] bytesrc =convertHexString(message); 
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding"); 
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8")); 
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES"); 
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec); 
            IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8")); 
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv); 
            byte[] retByte = cipher.doFinal(bytesrc); 
            return new String(retByte); 
    }
    
    /**
     * 16����������ת��
     * @param ss
     * @return
     */
    private static byte[] convertHexString(String ss) throws Exception { 
        byte digest[] = new byte[ss.length() / 2]; 
        for(int i = 0; i < digest.length; i++) 
        { 
        String byteString = ss.substring(2 * i, 2 * i + 2); 
        int byteValue = Integer.parseInt(byteString, 16); 
        digest[i] = (byte)byteValue; 
        } 
        return digest; 
    }
    
    /**
     * ʮ��������ת��
     * @param b
     * @return
     * @throws Exception
     */
    private static String toHexString(byte b[]) throws Exception { 
        StringBuffer hexString = new StringBuffer(); 
        for (int i = 0; i < b.length; i++) { 
            String plainText = Integer.toHexString(0xff & b[i]); 
            if (plainText.length() < 2) 
                plainText = "0" + plainText; 
            hexString.append(plainText); 
        } 
           
        return hexString.toString(); 
    }
    
    /**
     * ���ܷ���
     */
    public static String encrypt(String message,String key){
      //  log.info("����ԭ��Ϊ��" + message);
        String enStr = null;
        try {
             String orignStr=java.net.URLEncoder.encode(message, "utf-8"); 
             enStr=toHexString(encryptProcess(orignStr, key)); 
        } catch (Exception e) {
       //     log.error("���������쳣��", e);
        }
        return enStr;
    }
    
    
    /**
     * ���ܷ���
     */
    public static String decrypt(String message,String key){
        String decStr = null;
        try {
       /*     log.debug("���ܴ���" + message);
            log.debug("����KEY:" + key);*/
            decStr = java.net.URLDecoder.decode(decryptProcess(message,key), "utf-8") ;
           /* log.info("�������ܽ����" + decStr);*/
        }catch (Exception e) {
//            log.error("���������쳣��", e);
        }
        return decStr;
    }
    
    /**
     * ����Main����
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception { 
         String key = " science"; //����ֻ��Ϊ8λ
         String message ="security"; 
         String enStr = encrypt(message,key);
         System.out.println("���ܺ�:" + enStr);
         String decStr = decrypt(enStr,key);
         System.out.println("���ܺ�:" + decStr);
    } 
}