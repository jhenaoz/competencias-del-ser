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

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Valoración Competencias Ser " + evaluated);

        if (startDate != null) {
            stringBuilder.append(" del " + startDate);
        }
        if (endDate != null) {
            stringBuilder.append(" al " + endDate);
        }

        stringBuilder.append(".xlsx");
        return ResponseEntity.ok()
                .contentLength(file2Upload.length())
                .header("Content-disposition", "attachment;filename=" + stringBuilder)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(resource);
    }
}
