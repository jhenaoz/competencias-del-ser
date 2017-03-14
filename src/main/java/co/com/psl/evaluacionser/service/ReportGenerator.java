package co.com.psl.evaluacionser.service;

import co.com.psl.evaluacionser.domain.AptitudeSurvey;
import co.com.psl.evaluacionser.domain.BehaviorSurvey;
import co.com.psl.evaluacionser.domain.Survey;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * This class generates the excel reports that can be download, the excel format is xlsx
 */
@Service
public class ReportGenerator {

    private Logger logger = Logger.getLogger(ReportGenerator.class);

    /**
     * This method is the main for the users report and orchestrates the call of all the methods.
     *
     * @param surveys List with the selected surveys to generated the report.
     */
    public void createUserExcelReport(List<Survey> surveys) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("User Reports");

        addUserColumnsHeaders(sheet, workbook);
        addUserSurveysToSheet(surveys, sheet);
        saveWorkbookToDisk(workbook);
    }

    /**
     * This method is the main for the relations report and orchestrates the call of all the methods.
     *
     * @param surveys List with the selected surveys to generated the report.
     */
    public void createRelationExcelReport(List<Survey> surveys) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Relation Reports");

        addRelationColumnsHeaders(sheet, workbook);
        addRelationSurveysToSheet(surveys, sheet);
        saveWorkbookToDisk(workbook);
    }

    /**
     * This method creates the header with the correct format for the users report.
     *
     * @param sheet    is the excel page to be modified.
     * @param workbook is the excel document to be modified.
     */
    private void addUserColumnsHeaders(Sheet sheet, Workbook workbook) {
        int cellNum = 0;
        Row row = sheet.createRow(sheet.getLastRowNum());
        row.createCell(cellNum++).setCellValue("Valorado");
        row.createCell(cellNum++).setCellValue("Categoría");
        row.createCell(cellNum++).setCellValue("Fecha de envío");
        row.createCell(cellNum++).setCellValue("Comportamiento");
        row.createCell(cellNum++).setCellValue("Valor");
        row.createCell(cellNum++).setCellValue("Evaluador");
        row.createCell(cellNum++).setCellValue("Tipo de relación");
        row.createCell(cellNum).setCellValue("Comentario");
        createCellStyle(workbook, row);
    }

    /**
     * This method creates the header with the correct format for relations report.
     *
     * @param sheet    is the excel page to be modified.
     * @param workbook is the excel document to be modified.
     */
    private void addRelationColumnsHeaders(Sheet sheet, Workbook workbook) {
        int cellNum = 0;
        Row row = sheet.createRow(sheet.getLastRowNum());
        row.createCell(cellNum++).setCellValue("Valorado");
        row.createCell(cellNum).setCellValue("Persona que hace la valoración");
        createCellStyle(workbook, row);
    }

    /**
     * This method creates the style for the cells in the header row
     *
     * @param workbook is the excel document to be modified.
     * @param row      is the header row of the sheet
     */
    private void createCellStyle(Workbook workbook, Row row) {
        Font font = workbook.createFont();
        CellStyle style = workbook.createCellStyle();
        font.setBold(true);
        style.setFont(font);
        for (int i = 0; i < row.getLastCellNum(); i++) {
            row.getCell(i).setCellStyle(style);
        }
    }

    /**
     * The method interpolates the data from a survey to the excel document.
     *
     * @param surveys List with the selected surveys to generate the report.
     * @param sheet   is the excel page to be modified.
     */
    private void addUserSurveysToSheet(List<Survey> surveys, Sheet sheet) {
        for (Survey survey : surveys) {
            for (AptitudeSurvey aptitude : survey.getAptitudes()) {
                for (BehaviorSurvey behavior : aptitude.getBehaviors()) {
                    int cellNum = 0;
                    Row row = sheet.createRow(sheet.getLastRowNum() + 1);
                    row.createCell(cellNum++).setCellValue(survey.getEvaluated());
                    row.createCell(cellNum++).setCellValue(aptitude.getAptitude().getEs());
                    row.createCell(cellNum++).setCellValue(survey.getTimestamp());
                    row.createCell(cellNum++).setCellValue(behavior.getBehavior().getEs());
                    row.createCell(cellNum++).setCellValue(behavior.getScore());
                    row.createCell(cellNum++).setCellValue(survey.getEvaluator());
                    row.createCell(cellNum++).setCellValue(this.roleTranslate(survey.getRole()));
                    row.createCell(cellNum).setCellValue(aptitude.getObservation());
                }
            }
        }

        sheetFormat(sheet);
    }

    /**
     * The method interpolates the data of the relations from a survey to the excel document.
     *
     * @param surveys List with the selected surveys to generated the report.
     * @param sheet   is the excel page to be modified.
     */
    private void addRelationSurveysToSheet(List<Survey> surveys, Sheet sheet) {
        for (Survey survey : surveys) {
            int cellNum = 0;
            Row row = sheet.createRow(sheet.getLastRowNum() + 1);
            row.createCell(cellNum++).setCellValue(survey.getEvaluated());
            row.createCell(cellNum).setCellValue(survey.getEvaluator());
        }

        sheetFormat(sheet);
    }

    /**
     * This method gives the correct format to the sheet, like the cells size and the filter
     *
     * @param sheet is the excel page to be modified.
     */
    private void sheetFormat(Sheet sheet) {
        for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
            sheet.autoSizeColumn(i);
        }

        sheet.setAutoFilter(new CellRangeAddress(sheet.getFirstRowNum(), sheet.getLastRowNum(),
                sheet.getRow(0).getFirstCellNum(), sheet.getRow(0).getLastCellNum() - 1));
    }

    /**
     * This method translate the role because the reporter is in spanish.
     *
     * @param role the role in english, from the data base.
     * @return the role in spanish.
     */
    public String roleTranslate(String role) {
        if (role == null) {
            return "N/A";
        }
        String roleSpanish;
        switch (role.toLowerCase()) {
            case "client":
                roleSpanish = "Cliente";
                break;
            case "self-assessment":
                roleSpanish = "Autoevaluación";
                break;
            case "teammate":
                roleSpanish = "Equipo de trabajo";
                break;
            default:
                roleSpanish = "N/A";
                break;
        }
        return roleSpanish;
    }

    /**
     * This method saves the excel document in the resources package, and overwrite the file if it exists.
     *
     * @param workbook is the excel document tha was modified.
     */
    private void saveWorkbookToDisk(Workbook workbook) {
        String separator = File.separator;
        try (FileOutputStream fileOut = new FileOutputStream("src" + separator + "main" + separator
                + "resources" + separator + "Survey_Reports.xlsx")) {
            workbook.write(fileOut);
        } catch (IOException e) {
            logger.error("The file can't be saved ", e);
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                logger.error("Can't close the workbook ", e);
            }
        }
    }
}
