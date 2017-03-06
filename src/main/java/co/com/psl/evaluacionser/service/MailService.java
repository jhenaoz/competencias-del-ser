package co.com.psl.evaluacionser.service;

import co.com.psl.evaluacionser.domain.AptitudeSurvey;
import co.com.psl.evaluacionser.domain.Survey;
import org.apache.log4j.Logger;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

/**
 * This class allows the "send mail" service implementing a javax.mail dependency through Spring
 */
@Service
public class MailService {

    //TODO define the address who receive the mail to arodas@psl.com.co in the application properties files
    //@Value("${mailReceiver}")
    private String mailReceiver = "lusago19@gmail.com";

    //@Value("${mailUsername}")
    private String mailUsername = "competencias.del.ser@gmail.com";

    //@Value("${mailPassword}")
    private String pass = "competenciasdelser";

    static Logger logger = Logger.getLogger(MailService.class);

    /*public void newSurveyMail(SurveyDto surveyDto) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("username", "pass");
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

    /**
     * This Method create a new mail when a new survey is done
     * @param survey to extract the necessary information to make the new email
     */
    public void newSurveyMail(Survey survey) {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("smtp.gmail.com");
        sender.setPort(465);
        sender.setUsername(mailUsername);
        sender.setPassword(pass);
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth","true");
        properties.setProperty("mail.smtp.starttls.enable","true");
        sender.setJavaMailProperties(properties);
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

    /**
     * This method create the text for the new email with the survey information
     * @param survey
     * @return the new message
     */
    public String constructMessageText(Survey survey) {
        return "Cordial saludo, se acaba de realizar una valoración del ser \n"
                + "Persona valorada: " + survey.getEvaluated() + "\n"
                + "Persona que realizó la valoración: " + survey.getEvaluator() + "\n"
                + "Tipo de valoracón: "
                + evaluationType(survey.getAptitudes());
    }

    /**
     * This method control the type of the survey, one aptitude or all of them
     * @param aptitudeSurveyList
     * @return the part of the message with the type
     */
    public String evaluationType(List<AptitudeSurvey> aptitudeSurveyList) {
        if (aptitudeSurveyList.size() == 1) {
            return "una competencia de una persona \n" + "Competencia evaluada: "
                    + aptitudeSurveyList.get(0).getAptitude().getEs();
        } else {
            return "Todas las competencias de una persona";
        }
    }
}
