package com.zik.ussd_application;

import ch.qos.logback.core.pattern.util.AsIsEscapeUtil;
import com.zik.ussd_application.config.SeceretKeySystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

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
    void givenFile_whenEncrypt_thenSuccess() throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String algorithm = "AES/CBC/PKCS5Padding";
        SecretKey key = SeceretKeySystem.generateKey(128);
        IvParameterSpec ivParameterSpec = SeceretKeySystem.generateIv();
        String resource = "C:\\Users\\OLASUNKANMI\\Documents\\ass.txt";
        File inputFile = new File(resource);
        File encryptedFile = new File("classpath:baeldung.encrypted");
        File decryptedFile = new File("document.decrypted");
        SeceretKeySystem.encryptFile(algorithm,key,ivParameterSpec,inputFile,encryptedFile);
        SeceretKeySystem.decryptFile(algorithm,key,ivParameterSpec,encryptedFile,decryptedFile);
        assertThat(inputFile).hasSameTextualContentAs(decryptedFile);

    }
}
