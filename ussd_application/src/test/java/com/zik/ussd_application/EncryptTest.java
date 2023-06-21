package com.zik.ussd_application;

import ch.qos.logback.core.pattern.util.AsIsEscapeUtil;
import com.zik.ussd_application.config.SeceretKeySystem;
import com.zik.ussd_application.dto.Students;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EncryptTest {
    @Test
    void givenString_whenEncrypt_thenSuccess() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String input = "baeldung";
        SecretKey key = SeceretKeySystem.generateKey(128);
        IvParameterSpec ivParameterSpec = SeceretKeySystem.generateIv();
        String algorithm = "AES/CBC/PKCS5Padding";
        String cipherText = SeceretKeySystem.encrypt(algorithm,input,key,ivParameterSpec);
        String plainText = SeceretKeySystem.decrypt(algorithm,cipherText,key,ivParameterSpec);
        Assertions.assertEquals(input, plainText);

    }

    @Test
    void givenPassword_whenEncrypt_thenSuccess() throws InvalidKeySpecException,NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String plainText = "www.baeldung.com";
        String password = "baeldung";
        String salt = "123456789";
        SecretKey key = SeceretKeySystem.getKeyFromPassword(password, salt);
        IvParameterSpec ivParameterSpec = SeceretKeySystem.generateIv();
        String cipherText = SeceretKeySystem.encryptPasswordBased(plainText, key, ivParameterSpec);
        String decryptedCipherText = SeceretKeySystem.decryptPasswordBased(
                cipherText, key, ivParameterSpec);
        Assertions.assertEquals(plainText, decryptedCipherText);

    }

    @Test
    void givenFile_whenEncrypt_thenSuccess() throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String algorithm = "AES/CBC/PKCS5Padding";
        SecretKey key = SeceretKeySystem.generateKey(128);
        IvParameterSpec ivParameterSpec = SeceretKeySystem.generateIv();
        String resource = "C:\\Users\\OLASUNKANMI\\Documents\\ass.txt";
        File inputFile = new File(resource);
        File encryptedFile = new File("C:\\Users\\OLASUNKANMI\\Documents\\ass.encrypted");
        File decryptedFile = new File("C:\\Users\\OLASUNKANMI\\Documents\\ass.decrypted");
        SeceretKeySystem.encryptFile(algorithm,key,ivParameterSpec,inputFile,encryptedFile);
        SeceretKeySystem.decryptFile(algorithm,key,ivParameterSpec,encryptedFile,decryptedFile);
        assertThat(inputFile).hasSameTextualContentAs(decryptedFile);

    }

    @Test
    void givenObject_whenEncrypt_thenSuccess()
            throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
            InvalidAlgorithmParameterException, NoSuchPaddingException, IOException,
            BadPaddingException, ClassNotFoundException {

        Students student = new Students("Baeldung", 20);
        SecretKey key = SeceretKeySystem.generateKey(128);
        IvParameterSpec ivParameterSpec = SeceretKeySystem.generateIv();
        String algorithm = "AES/CBC/PKCS5Padding";
        SealedObject sealedObject = SeceretKeySystem.encryptObject(
                algorithm, student, key, ivParameterSpec);
        Students object = (Students) SeceretKeySystem.decryptObject(
                algorithm, sealedObject, key, ivParameterSpec);
        assertThat(student).isEqualToComparingFieldByField(object);
    }
}
