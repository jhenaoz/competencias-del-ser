package co.com.psl.evaluacionser.service;

import org.springframework.stereotype.Service;

/**
 * As both the excelReportGenerator and the PdfReportGenerator build their respective file names in a similar way
 * this class is used to build their names, doing so will prevent some duplicate code on both classes, it will also
 * avoid the need to send a lot of additional parameters to the classes report builders.
 */
@Service
public class NameService {

    /**
     * This method builds the file name according to the received params.
     *
     * @param evaluated the name of the person evaluated in the survey
     * @param startDate the start date of the search range
     * @param endDate   the end date of the search range
     * @return a String with the file name, .xlsx extension included
     */
    private String getSurveysName(String evaluated, String startDate, String endDate) {

        StringBuilder stringBuilder = new StringBuilder();

        if (evaluated != null) {
            stringBuilder.append(" ").append(evaluated);
        }
        if (startDate != null) {
            stringBuilder.append(" del ").append(startDate);
        }
        if (endDate != null) {
            stringBuilder.append(" al ").append(endDate);
        }


        return stringBuilder.toString();
    }

    /**
     * This method builds the  relation report name and returns the string.
     *
     * @param startDate the start date used to search for surveys
     * @param endDate   the end date used to search for surveys
     * @return name string
     */
    public String getRelationFileName(String startDate, String endDate) {

        return "Personas que han sido valoradas"
                + getSurveysName(null, startDate, endDate);

    }

    /**
     * This method builds the user report name and returns the string.
     *
     * @param evaluated the name of the person who was evaluated
     * @param startDate the start date used to search for surveys
     * @param endDate   the end date used to search for surveys
     * @return name string
     */
    public String getUserFileName(String evaluated, String startDate, String endDate) {

        return "Valoración Competencias Ser"
                + getSurveysName(evaluated, startDate, endDate);
    }
}
