package co.com.psl.evaluacionser.config;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncryptor {

    private static final Logger logger = Logger.getLogger(PasswordEncryptor.class);

    /**
     * Generate a random String, based on the seed provided
     *
     * @param saltSeed the seed to use for the random generator
     *
     * @return Random String;
     */
    private String generateSalt(int saltSeed) {
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
     * @param passwordToHash password to hash
     *
     * @return a String
     */
    public String getSecurePassword(String passwordToHash) {
        String salt = generateSalt(passwordToHash.hashCode());
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
            logger.error("Couldn't find the algorithm to encrypt the password" + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            logger.error("Couldn't encode the String to UTF-8" + e.getMessage());
        }

        return generatedPassword;
    }

}


}
