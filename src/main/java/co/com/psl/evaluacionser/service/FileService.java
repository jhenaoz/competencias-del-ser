package co.com.psl.evaluacionser.service;

import org.apache.log4j.Logger;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    private static final Logger logger = Logger.getLogger(FileService.class);

    /**
     * This method converts the survey report into a Response entity.
     *
     * @param fileName the name the file will have when downloaded
     * @return response entity OK containing the file to be downloaded
     */
    public ResponseEntity getDownloadResponse(String fileName) {
        String separator = File.separator;

        File fileToUpload = new File("src" + separator + "main" + separator + "resources"
                + separator + "Survey_Reports.xlsx");

        Path path = Paths.get(fileToUpload.getAbsolutePath());
        ByteArrayResource resource = null;
        try {
            resource = new ByteArrayResource(Files.readAllBytes(path));
        } catch (IOException e) {
            logger.error("there was an error getting the file bytes ", e);
        }

        return ResponseEntity.ok()
                .contentLength(fileToUpload.length())
                .header("Content-disposition", "attachment;filename=" + fileName)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(resource);
    }

    /**
     * This method builds the file name according to the received params.
     *
     * @param evaluated the name of the person evaluated in the survey
     * @param startDate the start date of the search range
     * @param endDate   the end date of the search range
     * @return a String with the file name, .xlsx extension included
     */
    public String getSurveysName(String evaluated, String startDate, String endDate) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Valoración Competencias Ser");

        if (evaluated != null) {
            stringBuilder.append(" " + evaluated);
        }
        if (startDate != null) {
            stringBuilder.append(" del " + startDate);
        }
        if (endDate != null) {
            stringBuilder.append(" al " + endDate);
        }

        stringBuilder.append(".xlsx");
        return stringBuilder.toString();
    }

    /**
     * This method builds the file name according to the received params.
     *
     * @param startDate the start date of the search range
     * @param endDate   the end date of the search range
     * @return a String with the file name, .xlsx extension included
     */
    public String getRelationSurveysName(String startDate, String endDate) {


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Personas que han sido valoradas");

        if (startDate != null) {
            stringBuilder.append(" del " + startDate);
        }
        if (endDate != null) {
            stringBuilder.append(" al " + endDate);
        }

        stringBuilder.append(".xlsx");
        return stringBuilder.toString();
    }

    /**
     * This method builds the report name and returns the response with the download.
     *
     * @param startDate the start date used to search for surveys
     * @param endDate   the end date used to search for surveys
     * @return response entity containing the file to be downloaded
     */
    public ResponseEntity getRelationDownloadResponse(String startDate, String endDate) {

        String relationFileName = getRelationSurveysName(startDate, endDate);
        return getDownloadResponse(relationFileName);

    }

    /**
     * This method builds the report name and returns the response with the download.
     *
     * @param evaluated the name of the person who was evaluated
     * @param startDate the start date used to search for surveys
     * @param endDate   the end date used to search for surveys
     * @return response entity containing the file to be downloaded
     */
    public ResponseEntity getSurveyDownloadResponse(String evaluated, String startDate, String endDate) {

        String surveyFileName = getSurveysName(evaluated, startDate, endDate);
        return getDownloadResponse(surveyFileName);
    }
}
