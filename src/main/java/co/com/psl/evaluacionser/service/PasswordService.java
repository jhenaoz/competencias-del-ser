package co.com.psl.evaluacionser.service;

import co.com.psl.evaluacionser.config.PasswordEncoderImpl;
import co.com.psl.evaluacionser.persistence.AdministratorRepository;
import co.com.psl.evaluacionser.service.dto.Administrator;
import co.com.psl.evaluacionser.service.dto.Password;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * This class implements the business logic required to manage the administrator password
 */
@Service
public class PasswordService {

    private PasswordEncoderImpl passwordEncoder;

    private AdministratorRepository administratorRepository;
    private EmailService emailService;

    private static final Logger logger = Logger.getLogger(PasswordService.class);

    @Autowired
    public PasswordService(final PasswordEncoderImpl passwordEncoder,
                           final AdministratorRepository administratorRepository,
                           final EmailService emailService) {
        this.passwordEncoder = passwordEncoder;
        this.administratorRepository = administratorRepository;
        this.emailService = emailService;
    }

    /**
     * This method implements the logic to update a password and saves it in the database
     * The attribute oldPassword in the password object could be the actual password or the generated token
     * @param password the object with the old password or token and the new password
     * @return a response entity with the status of the operation
     */
    public boolean updatePassword(Password password) {
        Administrator administrator = administratorRepository.findAdministrator();
        boolean oldPassMatchAdminPass = passwordEncoder.matches(password.getOldPassword(), administrator.getPassword());
        boolean timeAllowed = allowedTimestamp(administrator.getTimestamp());
        boolean oldPassMatchAdminToken = passwordEncoder.matches(password.getOldPassword(), administrator.getToken());
        if (oldPassMatchAdminPass || (oldPassMatchAdminToken && timeAllowed)) {
            String encodePassword = passwordEncoder.encode(password.getNewPassword());
            administrator.setPassword(encodePassword);
            administratorRepository.updateAdministrator(administrator);
            if (oldPassMatchAdminToken && timeAllowed) {
                deleteToken(administrator);
            }
            return true;
        }
        return false;
    }

    /**
     * This method implements the logic to generate a new token, and sends it to the administrator
     * @return a response entity with the status of the operation
     */
    public void forgotPassword() {
        Random random = new Random();
        int seed = random.nextInt();
        String newToken = passwordEncoder.generateSalt(seed);

        Administrator administrator = administratorRepository.findAdministrator();
        administrator.setToken(passwordEncoder.encode(newToken));
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        administrator.setTimestamp(dateFormat.format(date));
        administratorRepository.updateAdministrator(administrator);
        emailService.sendTokenEmail(newToken);
    }

    /**
     * This method makes the time verification because the token can only be used 30 minutes after being solicited
     * @param timestamp is the date when the token was solicited
     * @return True if the time is right or false if the token expired
     */
    private boolean allowedTimestamp(String timestamp) {
        final long thirtyMinutesToMill = 1800000L;
        if (timestamp != null && !timestamp.isEmpty()) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date timestampDate = dateFormat.parse(timestamp);
                Date actualDate = new Date();
                //Date.getTime() returns the date in milliseconds
                return timestampDate.getTime() + thirtyMinutesToMill >= actualDate.getTime();
            } catch (ParseException e) {
                logger.error("The date from the database cannot be parsed");
            }
        }
        return false;
    }

    /**
     * This method updates the administrator after the token is used, deleting the token from the database
     * @param administrator to be updated
     */
    private void deleteToken(Administrator administrator) {
        administrator.setToken(null);
        administrator.setTimestamp(null);
        administratorRepository.updateAdministrator(administrator);
    }
}
