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
     * this is the method who takes the xlsx file and turns it into a response entity to be downloaded
     *
     * @param evaluated the person who was evaluated in the survey
     * @param startDate the start date from the date range
     * @param endDate   the end date of the range
     * @return a response entity with the xlsx file to be downloaded from the browser
     */
    public ResponseEntity getDownloadResponse(String evaluated, String startDate, String endDate) {
        String separator = File.separator;

        File file2Upload = new File("src" + separator + "main" + separator + "resources"
                + separator + "Survey_Reports.xlsx");

        Path path = Paths.get(file2Upload.getAbsolutePath());
        ByteArrayResource resource = null;
        try {
            resource = new ByteArrayResource(Files.readAllBytes(path));
        } catch (IOException e) {
            logger.error("there was an error getting the file bytes ", e);
        }


        return ResponseEntity.ok()
                .contentLength(file2Upload.length())
                .header("Content-disposition", "attachment;filename=" + getFileName(evaluated, startDate, endDate))
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(resource);
    }

    /**
     * this method builds the file name according to the received params
     * @param evaluated the name of the person evaluated in the survey
     * @param startDate the start date of the search range
     * @param endDate the end date of the search range
     * @return a String with the file name, .xlsx extension included
     */
    public String getFileName(String evaluated, String startDate, String endDate) {


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Valoración Competencias Ser " + evaluated);

        if (startDate != null) {
            stringBuilder.append(" del " + startDate);
        }
        if (endDate != null) {
            stringBuilder.append(" al " + endDate);
        }

        stringBuilder.append(".xlsx");
        return stringBuilder.toString();
    }
}
