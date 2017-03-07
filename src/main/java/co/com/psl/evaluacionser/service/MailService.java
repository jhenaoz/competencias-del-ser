package co.com.psl.evaluacionser.service;

import co.com.psl.evaluacionser.domain.AptitudeSurvey;
import co.com.psl.evaluacionser.domain.Survey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.mail.*;
import javax.mail.internet.AddressException;
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
    @Value("${mailReceiver}")
    private String mailReceiver;

    @Value("${mailUsername}")
    private String mailUsername;

    @Value("${mailPassword}")
    private String mailPassword;

    /**
     * This Method create a new mail when a new survey is done
     * @param survey to extract the necessary information to make the new email
     */
    public void newSurveyMail(Survey survey) throws AddressException, MessagingException {

        Properties mailServerProperties;
        Session getMailSession;
        MimeMessage generateMailMessage;

        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");

        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(mailReceiver));
        generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(mailUsername));
        generateMailMessage.setSubject("Nueva valoracion del ser");
        String emailBody = constructMessageText(survey);
        //generateMailMessage.setContent(emailBody, "text/html");
        generateMailMessage.setContent(emailBody, "text/plain");

        Transport transport = getMailSession.getTransport("smtp");

        transport.connect("smtp.gmail.com", mailUsername, mailPassword);
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }

    /**
     * This method create the text for the new email with the survey information
     *
     * @param survey the new survey
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
     *
     * @param aptitudeSurveyList the List of aptitudes from survey, to get the size and name
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
