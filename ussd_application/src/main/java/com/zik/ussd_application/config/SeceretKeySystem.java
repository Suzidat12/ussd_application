package com.zik.ussd_application.config;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class SeceretKeySystem {
public static SecretKey generateKey(int n) throws NoSuchAlgorithmException{
    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
    keyGenerator.init(n);
    SecretKey key = keyGenerator.generateKey();
    return key;
}
public static SecretKey getKeyFromPassword(String password,String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
    KeySpec spec = new PBEKeySpec(password.toCharArray(),salt.getBytes(),65536,256);
    SecretKey secretKey = new SecretKeySpec(factory.generateSecret(spec).getEncoded(),"AES");
    return secretKey;
}
public static IvParameterSpec generateIv(){
    byte[] iv = new byte[16];
    new SecureRandom().nextBytes(iv);
    return new IvParameterSpec(iv);
}
public static String encrypt(String algorithm,String input,SecretKey key,IvParameterSpec iv)
throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException,
        InvalidKeyException, BadPaddingException,IllegalBlockSizeException {
    Cipher cipher = Cipher.getInstance(algorithm);
    cipher.init(Cipher.ENCRYPT_MODE,key,iv);
    byte[] cipherText = cipher.doFinal(input.getBytes());
    return Base64.getEncoder().encodeToString(cipherText);

}
public static String decrypt(String algorithm,String cipherText,SecretKey key,IvParameterSpec iv)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException,
    InvalidKeyException, BadPaddingException,IllegalBlockSizeException{
    Cipher cipher = Cipher.getInstance(algorithm);
    cipher.init(Cipher.DECRYPT_MODE,key,iv);
    byte[] plaintext = cipher.doFinal(Base64.getDecoder().decode(cipherText));
    return new String(plaintext);
}
public static void encryptFile (String algorithm, SecretKey key, IvParameterSpec iv, File inputFile, File outputFile)
        throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException,
        InvalidKeyException, BadPaddingException,IllegalBlockSizeException{
    Cipher cipher = Cipher.getInstance(algorithm);
    cipher.init(Cipher.ENCRYPT_MODE,key,iv);
    FileInputStream inputStream = new FileInputStream(inputFile);
    FileOutputStream outputStream = new FileOutputStream(outputFile);
    byte[] buffer = new byte[64];
    int bytesRead;
    while ((bytesRead = inputStream.read(buffer))!=-1){
        byte[] output = cipher.update(buffer,0,bytesRead);
        if(output != null){
            outputStream.write(output);
        }
    }
    byte[] outputBytes = cipher.doFinal();
    if(outputBytes != null){
        outputStream.write(outputBytes);
    }
    inputStream.close();
    outputStream.close();
}

    public static void decryptFile (String algorithm, SecretKey key, IvParameterSpec iv, File inputFile, File outputFile)
            throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException,
            InvalidKeyException, BadPaddingException,IllegalBlockSizeException{
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE,key,iv);
        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        byte[] buffer = new byte[64];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer))!=-1){
            byte[] output = cipher.update(buffer,0,bytesRead);
            if(output != null){
                outputStream.write(output);
            }
        }
        byte[] outputBytes = cipher.doFinal();
        if(outputBytes != null){
            outputStream.write(outputBytes);
        }
        inputStream.close();
        outputStream.close();
    }

}
