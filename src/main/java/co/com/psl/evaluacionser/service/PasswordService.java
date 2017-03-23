package co.com.psl.evaluacionser.service;

import co.com.psl.evaluacionser.config.PasswordEncoderImpl;
import co.com.psl.evaluacionser.persistence.AdministratorRepository;
import co.com.psl.evaluacionser.service.dto.Administrator;
import co.com.psl.evaluacionser.service.dto.Password;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

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

    public ResponseEntity updatePassword(Password password) {
        Administrator administrator = administratorRepository.findAdministrator();
        boolean oldPassMatchAdminPass = passwordEncoder.matches(password.getOldPassword(), administrator.getPassword());
        boolean timeAllowed = allowedTimestamp(administrator.getTimeStamp());
        boolean oldPassMatchAdminToken = passwordEncoder.matches(password.getOldPassword(), administrator.getToken());
        if (oldPassMatchAdminPass || (oldPassMatchAdminToken && timeAllowed)) {
            String encodePassword = passwordEncoder.encode(password.getNewPassword());
            administrator.setPassword(encodePassword);
            administratorRepository.updateAdministrator(administrator);
            if (oldPassMatchAdminToken && timeAllowed) {
                deleteToken(administrator);
            }
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity forgotPassword() {
        Random random = new Random();
        int seed = random.nextInt();
        String newPassword = passwordEncoder.generateSalt(seed);

        Administrator administrator = administratorRepository.findAdministrator();
        administrator.setToken(passwordEncoder.encode(newPassword));
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        administrator.setTimeStamp(dateFormat.format(date));
        administratorRepository.updateAdministrator(administrator);
        emailService.sendNewPassword(newPassword);
        return new ResponseEntity(HttpStatus.OK);
    }

    private boolean allowedTimestamp(String timestamp) {
        if (timestamp != null && !"".equals(timestamp)) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date timestampDate = dateFormat.parse(timestamp);
                long timestampToMills = timestampDate.getTime();
                Date actualDate = new Date();
                long actualDateToMills = actualDate.getTime();
                timestampToMills += 1800000L;
                return timestampToMills >= actualDateToMills;
            } catch (ParseException e) {
                logger.error("The date from the base date cannot be parsed");
            }
        }
        return false;
    }

    private void deleteToken(Administrator administrator) {
        administrator.setToken(null);
        administrator.setTimeStamp(null);
        administratorRepository.updateAdministrator(administrator);
    }
}
