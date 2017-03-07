package co.com.psl.evaluacionser.service;

import co.com.psl.evaluacionser.domain.AptitudeSurvey;
import co.com.psl.evaluacionser.domain.Survey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
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
     * To send a Email it's necessary to allow the gmail account to be acceded by a not secure application
     *
     * @param survey to extract the necessary information to make the new email
     */
    public void newSurveyMail(Survey survey) throws AddressException, MessagingException {
        /**
         * Setup Mail Server Properties
         */
        Properties mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");

        /**
         * get Mail Session
         */
        Session getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        MimeMessage generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(mailReceiver));
        generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(mailUsername));
        generateMailMessage.setSubject("Nueva valoracion del ser");
        String emailBody = constructHtml(survey);
        /**
         * generateMailMessage.setContent(emailBody, "text/html"); can send html text in this way
         */
        generateMailMessage.setContent(emailBody, "text/html; charset=utf-16");

        /**
         * Get Session and Send mail
         */
        Transport transport = getMailSession.getTransport("smtp");

        transport.connect("smtp.gmail.com", mailUsername, mailPassword);
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }

    /**
     * This method create the html text for the new email with the survey information
     *
     * @param survey the new survey
     * @return the new message
     */
    public String constructHtml(Survey survey) {
        return "<html>\n"
                + "<head>\n"
                + "<style>\n"
                + "* {\n"
                + "    font-family: Arial, Helvetica, sans-serif;\n"
                + "}\n"
                + "h2, h4{\n"
                + "    color: #134C96;\n"
                + "    font-weight: bold;\n"
                + "}\n"
                + "html {\n"
                + "  height: 100%;\n"
                + "  box-sizing: border-box;\n"
                + "}\n"
                + "*,\n"
                + "*:before,\n"
                + "*:after {\n"
                + "  box-sizing: inherit;\n"
                + "}\n"
                + "body {\n"
                + "  position: relative;\n"
                + "  margin: 0;\n"
                + "  padding-bottom: 6rem;\n"
                + "  min-height: 100%;\n"
                + "}\n"
                + ".box {\n"
                + "  margin: 0 auto;\n"
                + "  padding-top: 10%;\n"
                + "  max-width: 640px;\n"
                + "  width: 94%;\n"
                + "}\n"
                + ".box h1 {\n"
                + "  margin-top: 0;\n"
                + "}\n"
                + ".footer {\n"
                + "  position: absolute;\n"
                + "  right: 0;\n"
                + "  bottom: 0;\n"
                + "  left: 0;\n"
                + "  padding: 1rem;\n"
                + "  background-color: #efefef;\n"
                + "  text-align: center;\n"
                + "}\n"
                + "</style>\n"
                + "</head>\n"
                + "<body>\n"
                + "<div class=\"footer\"></div>\n"
                + "<div class=\"box\">\n"
                + "<h2>Cordial saludo, se ha realizado una valoración del ser</h2> \n"
                + " <h4>Persona valorada:</h4> \n"
                + " <p> " + survey.getEvaluated() + " </p> \n"
                + " <h4>Persona que realizó la valoración:</h4>\n"
                + " <p> " + survey.getEvaluator() + " </p> \n"
                + " <h4>Tipo de valoracón:</h4> \n"
                + evaluationType(survey.getAptitudes());
    }

    /**
     * This method control the type of the survey, one aptitude or all of them
     *
     * @param aptitudeSurveyList the List of aptitudes from survey, to get the size and name
     * @return the part of the message with the type
     */
    public String evaluationType(List<AptitudeSurvey> aptitudeSurveyList) {
        String type;
        if (aptitudeSurveyList.size() == 1) {
            type = " <p> Una competencia de una persona</p> \n"
                    + " <h4>Competencia evaluada:</h4> \n"
                    + " <p> " + aptitudeSurveyList.get(0).getAptitude().getEs() + "</p>\n";


        } else {
            type = " <p>Todas las competencias de una persona</p> \n";
        }
        type += "</div>\n"
                + "<div class=\"footer\">This message is auto-generated - "
                + "<strong>Competencias del ser PSL </strong> - </div>\n"
                + "</body>\n"
                + "</html>\n";
        return type;
    }
}
