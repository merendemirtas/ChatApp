package org.example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHasher {

    //Sifreyi hash ledigim metod.Bu metod bir sifre alir onu harflere ayirir ve her harfa ozel şifreler atar
    // sonra harfleri birlestirir ve güvenli ,kolay kirilamayacak sifre haline cevirir ve sifreyi dondurur
    public static String hashPassword(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = messageDigest.digest(password.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedPassword) {
                //Her byte i 2 haneli sifrelere cevirir
                hexString.append(String.format("%02X", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
