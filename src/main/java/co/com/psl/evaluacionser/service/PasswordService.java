package co.com.psl.evaluacionser.service;

import co.com.psl.evaluacionser.config.PasswordEncoderImpl;
import co.com.psl.evaluacionser.config.SecurityConfig;
import co.com.psl.evaluacionser.persistence.AdministratorRepository;
import co.com.psl.evaluacionser.persistence.ElasticsearchAdministratorRepository;
import co.com.psl.evaluacionser.service.dto.Administrator;
import co.com.psl.evaluacionser.service.dto.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Service
public class PasswordService {

    private PasswordEncoderImpl passwordEncoder;
    private SecurityConfig securityConfig;
    private AdministratorRepository administratorRepository;
    private EmailService emailService;

    @Autowired
    public PasswordService(final PasswordEncoderImpl passwordEncoder,
                           final SecurityConfig securityConfig,
                           final AdministratorRepository administratorRepository,
                           final EmailService emailService) {
        this.passwordEncoder = passwordEncoder;
        this.securityConfig = securityConfig;
        this.administratorRepository = administratorRepository;
        this.emailService = emailService;
    }

    public ResponseEntity updatePassword(Password password) {
        Administrator administrator = administratorRepository.findAdministrator();
        if (passwordEncoder.matches(password.getOldPassword(), administrator.getPassword())) {
            String encodePassword = passwordEncoder.encode(password.getNewPassword());
            administrator.setPassword(encodePassword);
            administratorRepository.updateAdministrator(administrator);
            //TODO update admin
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
        /**
         * TODO update admin
         */
        return new ResponseEntity(HttpStatus.OK);
    }

}
