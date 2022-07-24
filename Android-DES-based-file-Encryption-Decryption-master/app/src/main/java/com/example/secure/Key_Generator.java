package com.example.secure;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class Key_Generator {
    String password;
    Key_Generator(String password){
        this.password = password;
    }

    public Key generateKey() throws Exception {
        DESKeySpec desKeySpec = new DESKeySpec(this.password.getBytes(StandardCharsets.UTF_16));
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
        return secretKeyFactory.generateSecret(desKeySpec);
    }
 }
