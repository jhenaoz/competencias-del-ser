package co.com.psl.evaluacionser.service;

import co.com.psl.evaluacionser.domain.AptitudeSurvey;
import co.com.psl.evaluacionser.domain.BehaviorSurvey;
import co.com.psl.evaluacionser.domain.Survey;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PdfService {

    public void createPdf(List<Survey> surveys) throws IOException, DocumentException {
        ReportGenerator reportGenerator = new ReportGenerator();
        String separator = File.separator;

        FileOutputStream fileOut = new FileOutputStream("src" + separator + "main" + separator
                + "resources" + separator + "Survey_Reports.pdf");
        Document document = new Document();

        PdfWriter.getInstance(document, fileOut);
        document.open();

        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{3, 2, 2, 2, 2.4f, 3, 4, 1.2f});


        Font smallFont = new Font(Font.FontFamily.HELVETICA, 9);
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD);


        Image img = Image.getInstance(ClassLoader.getSystemResource("psl logo.PNG"));
        img.setAlignment(Element.ALIGN_BOTTOM);

        Paragraph title = new Paragraph("Valoración competencias del ser\n\n", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);

        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidths(new float[]{1.2f, 4});
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
                    table.addCell(new Phrase(reportGenerator.roleTranslate(survey.getRole()), smallFont));
                    table.addCell(new Phrase(survey.getTimestamp(), smallFont));

                    table.addCell(new Phrase(aptitude.getAptitude().getEs(), smallFont));
                    table.addCell(new Phrase(aptitude.getObservation(), smallFont));

                    table.addCell(new PdfPCell(new Phrase(behavior.getBehavior().getEs(), smallFont)));
                    table.addCell(new Phrase(String.valueOf(behavior.getScore()), smallFont));
                }
            }
        }
        document.add(headerTable);
        document.add(table);
        document.close();
    }
}
