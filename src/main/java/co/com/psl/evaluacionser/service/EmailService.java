package co.com.psl.evaluacionser.service;

import co.com.psl.evaluacionser.domain.Survey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
public class EmailService {

    @Value("${mailReceiver}")
    private String mailReceiver;

    @Value("${mailUsername}")
    private String mailUsername;

    private String mailTemplate = "html/mailtemplate";

    @Autowired
    private JavaMailSender mailSender;

    /**
     * It's necessary to define de qualifier in order to define which bean you want to use because spring
     * boot implements another one
     */
    @Autowired
    @Qualifier("emailTemplateEngine")
    private TemplateEngine htmlTemplateEngine;

    /**
     * Send the Mail with the defined beans
     * @param survey
     * @throws MessagingException when the message can't be sent
     */
    public void sendSimpleMail(Survey survey) throws MessagingException {
        /**
         * Prepare the context with the parameters that should be change in the html template
         */
        final Context ctx = new Context();
        ctx.setVariable("evaluated", survey.getEvaluated());
        ctx.setVariable("evaluator", survey.getEvaluator());
        if (survey.getAptitudes().size() == 1) {
            ctx.setVariable("type", "Una competencia de una persona");
            ctx.setVariable("aptitude", survey.getAptitudes().get(0).getAptitude().getEs());
        } else {
            ctx.setVariable("type", "Todas las competencias de una persona");
            ctx.setVariable("aptitude", null);
        }

        /**
         * Prepare message using a Spring helper
         */
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject("Nueva valoracion del ser");
        message.setFrom(mailUsername);
        message.setTo(mailReceiver);

        /**
         * Create the HTML body using Thymeleaf
         */
        final String htmlContent = this.htmlTemplateEngine.process(mailTemplate, ctx);
        message.setText(htmlContent, true /* isHtml */);

        this.mailSender.send(mimeMessage);
    }
}

