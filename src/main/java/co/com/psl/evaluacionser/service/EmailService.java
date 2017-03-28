package co.com.psl.evaluacionser.service;

import co.com.psl.evaluacionser.domain.Survey;
import co.com.psl.evaluacionser.persistence.ElasticsearchAdministratorRepository;
import co.com.psl.evaluacionser.service.dto.Administrator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * This class is in charge of the mail sending service implementing the javax mail dependency through spring
 * and thymeleaf to generate the body through a html template
 */
@Service
@PropertySource("classpath:mail/emailconfig.properties")
public class EmailService {

    private String mailUsername = "mail.server.username";

    private static final Logger logger = Logger.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ElasticsearchAdministratorRepository administratorRepository;

    /**
     * It's necessary to define the qualifier in order to define which bean you want to use because spring
     * boot implements another one
     */
    @Autowired
    @Qualifier("emailTemplateEngine")
    private TemplateEngine htmlTemplateEngine;


    /**
     * Sends the Mail with the defined beans, this prepares the message using a Spring helper
     *
     * @throws MessagingException when the message can't be sent
     */
    private void sendSimpleMail(Context context, String mailTemplate, String subject) throws MessagingException {

        Administrator administrator = administratorRepository.findAdministrator();

        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject(subject);
        message.setFrom(mailUsername);
        message.setTo(administrator.getEmail());

        final String htmlContent = this.htmlTemplateEngine.process(mailTemplate, context);
        message.setText(htmlContent, true);

        this.mailSender.send(mimeMessage);
    }

    /**
     * Prepare the context with the survey parameters that should be changed in the html template
     * and calls the send mail method
     *
     * @param survey The new survey
     */
    public void sendSurveyMail(Survey survey) {
        final Context context = new Context();
        context.setVariable("evaluated", survey.getEvaluated());
        context.setVariable("evaluator", survey.getEvaluator());
        if (survey.getAptitudes().size() == 1) {
            context.setVariable("type", "Una competencia de una persona");
            context.setVariable("aptitude", survey.getAptitudes().get(0).getAptitude().getEs());
        } else {
            context.setVariable("type", "Todas las competencias de una persona");
            context.setVariable("aptitude", null);
        }
        try {
            sendSimpleMail(context, "html/mailtemplate", "Nueva valoracion del ser");
        } catch (MessagingException e) {
            logger.error("The survey mail can't be sent ", e);
        }
    }

    /**
     * Prepare the context with the new token that should be changed in the html template
     * and calls the send mail method
     *
     * @param newToken the new access token used to change the password
     */
    public void sendTokenEmail(String newToken) {
        final Context context = new Context();
        context.setVariable("password", newToken);
        try {
            sendSimpleMail(context, "html/passwordmailtemplate",
                    "Nueva contraseña valoración del ser");
        } catch (MessagingException e) {
            logger.error("The new password mail can't be sent ", e);
        }
    }
}

