package co.com.psl.evaluacionser.service;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * this class will run unit test on the FileService class
 */
public class FileServiceTest {

    private FileService fileService = new FileService();

    @Test
    public void getFileNameWithBothDates() throws Exception {
        String fileName = fileService.getSurveysName("Josias Montoya",
                "1993-07-05", "2017-03-14");

        assertEquals("Valoración Competencias Ser Josias Montoya del 1993-07-05 al 2017-03-14.xlsx",
                fileName.toString());
    }

    @Test
    public void getFileNameWithoutDates() throws Exception {
        String fileName = fileService.getSurveysName("Josias Montoya", null, null);

        assertEquals("Valoración Competencias Ser Josias Montoya.xlsx", fileName.toString());
    }

    @Test
    public void getFileNameWithOneDates() throws Exception {
        String fileName = fileService.getSurveysName("Josias Montoya",
                null, "2017-03-14");

        assertEquals("Valoración Competencias Ser Josias Montoya al 2017-03-14.xlsx", fileName.toString());
    }

}
