package com.zik.ussd_application.report;

import com.zik.ussd_application.model.Accounts;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;




public class ReportExcelDownload {
    public static ByteArrayInputStream generateTransactionExcel(List<Accounts> list) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String extension = "file.xls";
        Workbook wb = getWorkbook(extension);
        Sheet sheet = wb.createSheet("accounts_file");
        createBody(sheet, list);
        wb.write(out);
        return new ByteArrayInputStream(out.toByteArray());
    }
    public static byte[] generateByte(List<Accounts> list) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String extension = "file.xls";
        Workbook wb = getWorkbook(extension);
        Sheet sheet = wb.createSheet("accounts_file");
        createBody(sheet, list);
        wb.write(out);
        return out.toByteArray();
    }

    public static Workbook getWorkbook(String excelFilePath)
            throws IOException {
        Workbook workbook = null;

        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }

    public static void createHeader(Row row) {
        ReportUtil.createContent(row, null, "Title", 0);
        ReportUtil.createContent(row, null, "First name", 1);
        ReportUtil.createContent(row, null, "Last name", 2);
        ReportUtil.createContent(row, null, "Phone Number", 3);
        ReportUtil.createContent(row, null, "Account Status", 4);
        ReportUtil.createContent(row, null, "Account Number", 5);
        ReportUtil.createContent(row, null, "Balance", 6);
        ReportUtil.createContent(row, null, "Date created", 7);
        ReportUtil.createContent(row, null, "Transaction date", 8);
    }

    private static void writeReport(Accounts rs, Row row) {
        String title ="";
        if("MALE".equalsIgnoreCase(rs.getGender())){
            title ="Mr.";
        }else{
            title ="Mrs.";
        }
        ReportUtil.createContent(row, null, title + "", 0);
        ReportUtil.createContent(row, null, rs.getFirstName(), 1);
        ReportUtil.createContent(row, null, rs.getLastName(), 2);
        ReportUtil.createContent(row, null, rs.getPhoneNumber(), 3);
        ReportUtil.createContent(row, null, rs.getAccountStatus(), 4);
          ReportUtil.createContent(row, null, rs.getAccountNumber(), 5);
        ReportUtil.createContent(row, null, ReportUtil.toString(rs.getBalance()), 6);
        ReportUtil.createContent(row, null, ReportUtil.toString(rs.getDatecreated()), 7);
        ReportUtil.createContent(row, null, ReportUtil.toString(rs.getLastTransactionDate()), 8);




    }

    public static void createBody(Sheet sheet, List<Accounts> list) {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        cellStyle.setFont(font);
        Row rw = sheet.createRow(0);
        createHeader(rw);
        int rowcount = 1;
        for (Accounts rs : list) {
            Row row = sheet.createRow(rowcount++);
            writeReport(rs, row);
        }

    }

}
