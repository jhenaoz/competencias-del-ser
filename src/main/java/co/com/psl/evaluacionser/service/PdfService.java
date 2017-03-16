package co.com.psl.evaluacionser.service;

import co.com.psl.evaluacionser.domain.AptitudeSurvey;
import co.com.psl.evaluacionser.domain.BehaviorSurvey;
import co.com.psl.evaluacionser.domain.Survey;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class PdfService {

    private ExcelReportGenerator excelReportGenerator = new ExcelReportGenerator();
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(PdfService.class);

    /**
     * This method receives the params from the controller, builds the pdf and triggers the download.
     *
     * @param surveys  the list of all the surveys used to extract the data
     * @param response the HttpServletResponse used to trigger the download
     * @param fileName the name the file will have when downloaded
     */
    public void getUserPdf(List<Survey> surveys, HttpServletResponse response, String fileName) {
        String pdfName = "Valoración competencias del ser";
        Element headerTable = getHeaderTable(pdfName);
        Element bodyTable = getBodyUserTable(surveys);
        response.addHeader("Content-disposition", "attachment;filename=" + fileName + ".pdf");
        createPdf(headerTable, bodyTable, response);
    }

    /**
     * This method receives the params from the controller, builds the pdf and triggers the download.
     *
     * @param surveys  the list of all the surveys used to extract the data
     * @param response the HttpServletResponse used to trigger the download
     * @param fileName the name the file will have when downloaded
     */
    public void getRelationPdf(List<Survey> surveys, HttpServletResponse response, String fileName) {
        String pdfName = "Personas que han sido valoradas";
        Element headerTable = getHeaderTable(pdfName);
        Element bodyTable = getBodyRelationTable(surveys);
        response.addHeader("Content-disposition", "attachment;filename=" + fileName + ".pdf");
        createPdf(headerTable, bodyTable, response);
    }

    /**
     * This method uses the survey list to build the body table of the relation pdf.
     *
     * @param surveys the list of all the surveys required in the report
     * @return returns the body table as an Element ready to be added to the pdf document
     */
    private Element getBodyRelationTable(List<Survey> surveys) {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);

        Font smallFont = new Font(Font.FontFamily.HELVETICA, 9);
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
        GrayColor gray = new GrayColor(0.8f);

        PdfPCell headerCell = new PdfPCell();
        headerCell.setBackgroundColor(gray);

        headerCell.setPhrase(new Phrase("Evaluado", headerFont));
        table.addCell(headerCell);

        headerCell.setPhrase(new Phrase("Evaluador", headerFont));
        table.addCell(headerCell);

        for (Survey surveyInList : surveys) {
            table.addCell(new Phrase(surveyInList.getEvaluated(), smallFont));
            table.addCell(new Phrase(surveyInList.getEvaluator(), smallFont));
        }
        return table;
    }

    /**
     * This method uses the survey list to build the body table of the user pdf.
     *
     * @param surveys the list of all the surveys required in the report
     * @return returns the body table as an Element ready to be added to the pdf document
     */
    private Element getBodyUserTable(List<Survey> surveys) {
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);
        try {
            table.setWidths(new float[]{3, 2, 2, 2, 2.4f, 3, 4, 1.2f});
        } catch (DocumentException e) {
            logger.error("The table widths were not edited. ", e);
        }

        Font smallFont = new Font(Font.FontFamily.HELVETICA, 9);
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
        GrayColor gray = new GrayColor(0.8f);

        PdfPCell cell = new PdfPCell(new Phrase("Evaluado", headerFont));
        cell.setBackgroundColor(gray);
        table.addCell(cell);

        cell.setPhrase(new Phrase("Evaluador", headerFont));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Rol", headerFont));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Fecha", headerFont));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Aptitud", headerFont));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Observación", headerFont));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Comportamiento", headerFont));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Valor", headerFont));
        table.addCell(cell);


        for (Survey survey : surveys) {
            for (AptitudeSurvey aptitude : survey.getAptitudes()) {
                for (BehaviorSurvey behavior : aptitude.getBehaviors()) {

                    table.addCell(new Phrase(survey.getEvaluated(), smallFont));
                    table.addCell(new Phrase(survey.getEvaluator(), smallFont));
                    table.addCell(new Phrase(excelReportGenerator.roleTranslate(survey.getRole()), smallFont));
                    table.addCell(new Phrase(survey.getTimestamp(), smallFont));

                    table.addCell(new Phrase(aptitude.getAptitude().getEs(), smallFont));
                    table.addCell(new Phrase(aptitude.getObservation(), smallFont));

                    table.addCell(new PdfPCell(new Phrase(behavior.getBehavior().getEs(), smallFont)));
                    table.addCell(new Phrase(String.valueOf(behavior.getScore()), smallFont));
                }
            }
        }
        return table;
    }

    /**
     * This body builds the header table of the pdf, the header table contains 2 columns and 2 cells, one with the logo.
     * and one with the title of the title of the pdf
     *
     * @param pdfName the name to be included in the header table
     * @return returns the header table as an Element ready to be added to the pdf document
     */
    private Element getHeaderTable(String pdfName) {
        Image img = null;
        try {
            img = Image.getInstance(ClassLoader.getSystemResource("psl logo.PNG"));
        } catch (BadElementException e) {
            logger.error("There was a problem getting the instance of the logo image. ", e);
        } catch (IOException e) {
            logger.error("There was a problem getting the instance of the logo image. ", e);
        }
        img.setAlignment(Element.ALIGN_BOTTOM);

        Font titleFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD);
        Paragraph title = new Paragraph(pdfName + "\n\n", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);

        PdfPTable headerTable = new PdfPTable(2);
        try {
            headerTable.setWidths(new float[]{1.2f, 4});
        } catch (DocumentException e) {
            logger.error("There was a problem while modifiying the header table width. ", e);
        }
        headerTable.setWidthPercentage(100);

        PdfPCell headerCell = new PdfPCell();
        headerCell.setBorder(Rectangle.NO_BORDER);
        headerCell.addElement(img);
        headerCell.setFixedHeight(10f);
        headerTable.addCell(headerCell);

        headerCell = new PdfPCell();
        headerCell.addElement(title);
        headerCell.setBorder(Rectangle.NO_BORDER);
        headerCell.setFixedHeight(47f);
        headerTable.addCell(headerCell);
        return headerTable;
    }

    /**
     * This method gathers the tables to be included in the pdf and builds the file so it can be downloaded.
     *
     * @param headerTable the header table of the pdf
     * @param bodyTable   the body table of the pdf
     * @param response    the HttpServletResponse used to trigger the download
     */
    private void createPdf(Element headerTable, Element bodyTable, HttpServletResponse response) {


        Document document = new Document();

        try {
            try {
                PdfWriter.getInstance(document, response.getOutputStream());
            } catch (IOException e) {
                logger.error("The PdfWriter instance could not be gotten. ", e);
            }
        } catch (DocumentException e) {
            logger.error("There was a problem getting the instance of the document. ", e);
        }
        document.open();

        try {
            document.add(headerTable);
        } catch (DocumentException e) {
            logger.error("There was a problem adding the header table. ", e);
        }
        try {
            document.add(bodyTable);
        } catch (DocumentException e) {
            logger.error("There was a problem while adding the table ", e);
        }
        document.close();
    }
}
