package co.com.psl.evaluacionser.config;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class PasswordEncryptor {

    /**
     * Generate a random String, based on the seed provided
     * 
     * @return Random String;
     */
    public static String generateSalt(int saltSeed) {
        Random rgn = new Random(saltSeed);
        String salt = "";

        char randomChar;
        for (int i = 0; i < 16; i++) {
            int randomNum = (int) (rgn.nextDouble() * 93) + 33;
            randomChar = (char) randomNum;
            salt = salt + randomChar;
        }

        return salt;
    }

    /**
     * Generate a hash String from the password provided.
     * 
     * @param passwordToHash password to change
     * @param salt the salt to use
     * @return a String
     */
    public static String getSHA512SecurePassword(String passwordToHash, String salt) {
        String generatedPassword = null;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes("UTF-8"));
            byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return generatedPassword;
    }

}
