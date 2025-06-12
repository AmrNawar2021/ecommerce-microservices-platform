package com.amrfawzi.ecommerce.user_service;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Base64;

public class Test {
    public static void main(String[] args) {
            byte[] key = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
            String base64Key = Base64.getEncoder().encodeToString(key);
            System.out.println(base64Key);
        }
    }
