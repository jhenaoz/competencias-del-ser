package co.com.psl.evaluacionser.service;

import co.com.psl.evaluacionser.config.PasswordEncoderImpl;
import co.com.psl.evaluacionser.config.SecurityConfig;
import co.com.psl.evaluacionser.persistence.ElasticsearchAdministratorRepository;
import co.com.psl.evaluacionser.service.dto.Administrator;
import co.com.psl.evaluacionser.service.dto.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    private PasswordEncoderImpl passwordEncoder;
    private SecurityConfig securityConfig;
    private ElasticsearchAdministratorRepository administratorRepository;

    @Autowired
    public PasswordService(final PasswordEncoderImpl passwordEncoder,
                           final SecurityConfig securityConfig,
                           final ElasticsearchAdministratorRepository administratorRepository) {
        this.passwordEncoder = passwordEncoder;
        this.securityConfig = securityConfig;
        this.administratorRepository = administratorRepository;
    }

    public ResponseEntity updatePassword(Password password) {
        Administrator administrator = administratorRepository.findAdministrator();
        if (passwordEncoder.matches(password.getOldPassword(), administrator.getPassword())) {
            String encodePassword = passwordEncoder.encode(password.getNewPassword());
            administrator.setPassword(encodePassword);
            administratorRepository.updateAdministrator(administrator);
            securityConfig.refreshAdmin(administrator);

            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

}
