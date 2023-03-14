package com.zik.ussd_application.report;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReportUtil {

public static void createHeader(Row row, String text, int i, Sheet sh) {
        Cell cell = row.createCell(i);
        cell.setCellValue(text);
        CellStyle cellStyle = sh.getWorkbook().createCellStyle();
        Font font = sh.getWorkbook().createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
    }
     public static void createContent(Row row, CellStyle style, String text, int i) {
        Cell cell = row.createCell(i);
        cell.setCellStyle(style);
        cell.setCellValue(text);

    }

    public static Map<String, Integer> getColumnName(Sheet sheet) {
        Map<String, Integer> maps = new HashMap<>();
        Row row = sheet.getRow(0); //Get first row
        short minColIx = row.getFirstCellNum(); //get the first column index for a row
        short maxColIx = row.getLastCellNum(); //get the last column index for a row
        for (short colIx = minColIx; colIx < maxColIx; colIx++) { //loop from first to last index
            Cell cell = row.getCell(colIx); //get the cell
            if (StringUtils.isEmpty(cell.getStringCellValue())) {
                System.out.println("Empty");
            } else {
                maps.put(cell.getStringCellValue().replaceAll("\\s+", "").toUpperCase(), cell.getColumnIndex()); //add the cell contents (name of column) and cell index to the map
            }
        }

        return maps;
    }

    public static Object getData(Row row, String key, Map<String, Integer> map, FormulaEvaluator fm) {
        int cell = 0;
        if (map.containsKey(key)) {
            cell = map.get(key);
            if (row.getCell(cell) == null || row.getCell(cell).getCellTypeEnum().equals(CellType.BLANK)
                    || row.getCell(cell).getCellTypeEnum().equals(CellType._NONE)
                    || row.getCell(cell).getCellTypeEnum().equals(CellType.ERROR)) {
                return "";
            } else if (row.getCell(cell).getCellTypeEnum().equals(CellType.FORMULA)) {
               CellValue c=fm.evaluate(row.getCell(cell));
               return c.getNumberValue();
            } else if (row.getCell(cell).getCellTypeEnum().equals(CellType.BOOLEAN)) {
                return row.getCell(cell).getBooleanCellValue();
            } else if (row.getCell(cell).getCellTypeEnum().equals(CellType.NUMERIC)) {
                return row.getCell(cell).getNumericCellValue();
            } else if (row.getCell(cell).getCellTypeEnum().equals(CellType.STRING)) {
                return row.getCell(cell).getStringCellValue();
            } else if (HSSFDateUtil.isCellDateFormatted(row.getCell(cell))) {
                return getDate(row.getCell(cell).getDateCellValue());
            }
        } else {
            System.out.println("Key........................." + key + " ..............Not Found");
            return "";
        }

        return "";
    }

    public static Workbook getWorkbook(FileInputStream inputStream,
            String excelFilePath) throws IOException {
        Workbook workbook = null;

        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException(
                    "The specified file is not Excel file");
        }

        return workbook;
    }

    public static String getMonthName(Integer id) {
        HashMap<Integer, String> maps = new HashMap<>();
        maps.put(1, "January");
        maps.put(2, "February");
        maps.put(3, "March");
        maps.put(4, "April");
        maps.put(5, "May");
        maps.put(6, "June");
        maps.put(7, "July");
        maps.put(8, "August");
        maps.put(9, "September");
        maps.put(10, "Octomber");
        maps.put(11, "November");
        maps.put(12, "December");
        return maps.get(id);

    }
    private static List<String> getExcelHeaders() {
        List<String> hd = new ArrayList<>();
        hd.add("surname".toUpperCase());
        hd.add("firstname");
        hd.add("Middle name".toUpperCase());
        hd.add("GSMNO".toUpperCase());
        
        return hd;
    }
    private static List<String> getLoanHeaders() {
        List<String> hd = new ArrayList<>();
        hd.add("ACCOUNTNO".toUpperCase());
        hd.add("ACCOUNTNAME");
        hd.add("BVN".toUpperCase());
        hd.add("PHONENO".toUpperCase());
        hd.add("HECTER".toUpperCase());
        hd.add("FARMSIZE".toUpperCase());
        hd.add("LANDSIZE".toUpperCase());
        hd.add("LONGITUDE".toUpperCase());
        hd.add("LATITUDE".toUpperCase());
        hd.add("STATE".toUpperCase());
        hd.add("LGA".toUpperCase());
        hd.add("LOANAMOUNT".toUpperCase());
        hd.add("CASH".toUpperCase());
        hd.add("FIRSTNAME".toUpperCase());
        hd.add("OTHERNAMES".toUpperCase());
        hd.add("GENDER".toUpperCase());
        hd.add("PRODUCETYPE".toUpperCase());
        hd.add("PRODUCENAME".toUpperCase());

        hd.add("SUPPORTINORNIZATION".toUpperCase());
        hd.add("COOPERATIVE_CODE".toUpperCase());
        hd.add("PROJECT_CODE".toUpperCase());

        return hd;
    }
    private static List<String> getLoanHome() {
        List<String> hd = new ArrayList<>();
        hd.add("Surname".toUpperCase());
        hd.add("Middle name");
        hd.add("First name".toUpperCase());
        hd.add("GSMNO".toUpperCase());
        hd.add("BVN".toUpperCase());
        hd.add("EOP".toUpperCase());
        hd.add("Hectares/Animals".toUpperCase());
        hd.add("Loan Amount".toUpperCase());
        hd.add("5% Equity Contribution".toUpperCase());
        hd.add("Interest".toUpperCase());
        hd.add("Loan Repayment".toUpperCase());
        hd.add("Curent Balance".toUpperCase());
        hd.add("Interest rate".toUpperCase());
        hd.add("Interest option".toUpperCase());
        hd.add("Date Granted".toUpperCase());
        hd.add("Duedate".toUpperCase());
        hd.add("GL Loan Reciveable Account No".toUpperCase());
        hd.add("GL Interest receiveable on Loan Account No".toUpperCase());

        hd.add("SUPPORTINORNIZATION".toUpperCase());
        hd.add("COOPERATIVE_CODE".toUpperCase());
        hd.add("PROJECT_CODE".toUpperCase());

        return hd;
    }
public static String getLoanHeader(Sheet sheet) {
        String response = "";
        int counter = 0;
        boolean found = false;
        String Colfound = "";
        Row row = sheet.getRow(0); //Get first row
        short minColIx = row.getFirstCellNum(); //get the first column index for a row
        short maxColIx = row.getLastCellNum(); //get the last column index for a row
        for (short colIx = minColIx; colIx < maxColIx; colIx++) { //loop from first to last index
            Cell cell = row.getCell(colIx); //get the cell
            if (cell.getStringCellValue().trim() != null && !cell.getStringCellValue().trim().equalsIgnoreCase("")) {
                for (String column : getLoanHeaders()) {
//                if (getHeaders().contains(cell.getStringCellValue()) == false) {
//                }
                    if (column.equalsIgnoreCase(cell.getStringCellValue().trim()) == true) {
                        found = true;
                        if (counter <= 0) {
                            Colfound = cell.getStringCellValue();
                        } else {
                            Colfound = Colfound + "," + cell.getStringCellValue();
                        }
                        counter++;
//                        System.out.println("cell.getStringCellValue():" + cell.getStringCellValue());
                    }
                }
                if (found == false) {
                    response += " INVALID FILE FORMAT " + cell.getStringCellValue();
                }
            }
            found = false;

//                } else {
//                   
//                }
        }
        if (counter < 4 || counter > 4) {
            response += " INVALID FILE FORMAT OR MISSING COLUMN(S) " + Colfound;
        }
        return response;

    }

public static String getExcelHeader(Sheet sheet) {
        String response = "";
        int counter = 0;
        boolean found = false;
        String Colfound = "";
        Row row = sheet.getRow(0); //Get first row
        short minColIx = row.getFirstCellNum(); //get the first column index for a row
        short maxColIx = row.getLastCellNum(); //get the last column index for a row
        for (short colIx = minColIx; colIx < maxColIx; colIx++) { //loop from first to last index
            Cell cell = row.getCell(colIx); //get the cell
            if (cell.getStringCellValue().trim() != null && !cell.getStringCellValue().trim().equalsIgnoreCase("")) {
                for (String column : getExcelHeaders()) {
//                if (getHeaders().contains(cell.getStringCellValue()) == false) {
//                }
                    if (column.equalsIgnoreCase(cell.getStringCellValue().trim()) == true) {
                        found = true;
                        if (counter <= 0) {
                            Colfound = cell.getStringCellValue();
                        } else {
                            Colfound = Colfound + "," + cell.getStringCellValue();
                        }
                        counter++;
//                        System.out.println("cell.getStringCellValue():" + cell.getStringCellValue());
                    }
                }
                if (found == false) {
                    response += " INVALID FILE FORMAT " + cell.getStringCellValue();
                }
            }
            found = false;

//                } else {
//                   
//                }
        }
        if (counter < 4 || counter > 4) {
            response += " INVALID FILE FORMAT OR MISSING COLUMN(S) " + Colfound;
        }
        return response;

    }
    public static Integer evaluate(Integer d) {
        return d == null ? 0 : d;
    }

    public static Double evaluate(Double d) {
        return d == null ? 0.0 : d;
    }

    public static String formatDate(Date d) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        return f.format(d);
    }

    public static Integer getYear(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int year = cal.get(Calendar.YEAR);
        return year;
    }

    public static String modifyValue(Double d) {
        return String.format("%,.2f", d);
    }

    public static String modifyValue(Integer d) {
        return NumberFormat.getNumberInstance(Locale.US).format(d);
    }

    public static Integer getDay(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static String getName(Date d) {
        SimpleDateFormat f = new SimpleDateFormat("MMM");
        return f.format(d);
    }

    public static String getDate(Date d) {
        SimpleDateFormat ft = new SimpleDateFormat("E");
        return ft.format(d);
    }


    public static String toString(Double d){
        return String.valueOf(d);
    }

    public static String toString(Integer i){
        return String.valueOf(i);
    }
    public static String toString(Long l){
        return String.valueOf(l);
    }

    public static String toString(Date d){
        return String.valueOf(d);
    }

}
