package com.automationselenium;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Lucas
 */
public class SaveToExcel {
    private static String[] columns = {"CFCs", "JANEIRO", "FEVEREIRO", "MARÇO", "ABRIL", "MAIO", "jUNHO", "JULHO", "AGOSTO", "SETEMBRO", "OUTUBRO", "NOVEMBRO", "DEZEMBRO", "MÉDIA"};
    private YearData yearData[];
    
    public SaveToExcel(YearData yearData[]) {
        this.yearData = yearData;
    }
    
    public void saveFile(String fileName) throws FileNotFoundException, IOException {
        Workbook workbook = new XSSFWorkbook();
        
        for(YearData yd : yearData) {
            Sheet sheet = workbook.createSheet(yd.getYear());
            
            //header style
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 14);
            headerFont.setColor(IndexedColors.BLACK.getIndex());
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            
            //zero value style
            Font zeroFont = workbook.createFont();
            zeroFont.setColor(IndexedColors.RED.getIndex());
            CellStyle zeroStyle = workbook.createCellStyle();
            zeroStyle.setFont(zeroFont);
            
            Row headerRow1 = sheet.createRow(0);
            Cell h1 = headerRow1.createCell(0);
            h1.setCellValue(yd.getYear());
            h1.setCellStyle(headerCellStyle);
            
            Row headerRow2 = sheet.createRow(1);
            for(int i=0; i<columns.length; i++) {
                Cell cell = headerRow2.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerCellStyle);
            }
            
            int rowNum = 2;
            
            Set<String> schools = yd.getKeys();
            for(String school : schools) {
                Row row = sheet.createRow(rowNum++);
                int cellNum = 0;
                row.createCell(cellNum++).setCellValue(school);
                
                Float[] data = yd.getAllData(school);
                float average = 0;
                int cont = 0;
                for(Float dt : data) {
                    Cell cell = row.createCell(cellNum++);
                    cell.setCellValue(dt);
                    average += dt;
                    cont++;
                    
                    if(dt == 0)
                        cell.setCellStyle(zeroStyle);
                }
                row.createCell(cellNum++).setCellValue(average/cont);
            }
            
            for(int i=0; i<columns.length; i++) {
                sheet.autoSizeColumn(i);
            }
        }
        FileOutputStream fileOut = new FileOutputStream(fileName);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
    }
}