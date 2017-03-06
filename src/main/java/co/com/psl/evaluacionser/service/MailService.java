package co.com.psl.evaluacionser.service;

import co.com.psl.evaluacionser.domain.AptitudeSurvey;
import co.com.psl.evaluacionser.domain.Survey;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;

/**
 * This class allows the "send mails" service implementing a javax.mail dependency through Spring
 */
@Service
public class MailService {

    //TODO define the address who receive the mail to arodas@psl.com.co in the application properties files
    @Value("${mailReceiver}")
    private String mailReceiver;

    @Value("${mailUsername}")
    private String mailUsername;

    @Value("${mailPassword}")
    private String password;

    static Logger logger = Logger.getLogger(MailService.class);

    /*public void placeOrder(SurveyDto surveyDto) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("username", "password");
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from@no-spam.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("to@no-spam.com"));
            message.setSubject("Testing Subject");
            message.setText("Dear Mail Crawler," +
                    "\n No spam to my email, please!");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }*/

    public void placeOrder(Survey survey) {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("smtp.gmail.com");
        sender.setPort(465);
        sender.setUsername(mailUsername);
        sender.setPassword(password);
        //Properties properties = new Properties();
        //properties.setProperty("mail.smtp.auth","true");
        //properties.setProperty("mail.smtp.starttls.enable","true");
        //sender.setJavaMailProperties(properties);
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            InternetAddress internetEmailReceiver = new InternetAddress();
            internetEmailReceiver.setAddress(mailReceiver);
            helper.setTo(internetEmailReceiver);
            helper.setSubject("Nueva calificación del ser");
            helper.setText(constructMessageText(survey));
            sender.send(message);
        } catch (MailException e) {
            logger.error("Mail can't be sent " + e);
        } catch (MessagingException me) {
            logger.error("Message can't be built " + me);
        }
    }

    public String constructMessageText(Survey survey) {
        return "Cordial saludo, se acaba de realizar una valoración del ser \n"
                + "Persona valorada: " + survey.getEvaluated() + "\n"
                + "Persona que realizó la valoración: " + survey.getEvaluator() + "\n"
                + "Tipo de valoracón: "
                + evaluationType(survey.getAptitudes());
    }

    public String evaluationType(List<AptitudeSurvey> aptitudeSurveyList) {
        if (aptitudeSurveyList.size() == 1) {
            return "una competencia de una persona \n" + "Competencia evaluada: "
                    + aptitudeSurveyList.get(0).getAptitude().getEs();
        } else {
            return "Todas las competencias de una persona";
        }
    }
}
