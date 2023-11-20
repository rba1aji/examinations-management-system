package com.rba1aji.examinationmanagementsystem.utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ExcelFileGenerator {

  private Workbook workbook;
  private Sheet sheet;
  private Row row;
  private String fileName;
  private int columnNumber;
  private int rowNumber;

  public ExcelFileGenerator(String fileName) {
    this.fileName = fileName;
    workbook = new XSSFWorkbook();
    sheet = workbook.createSheet(fileName);
  }

  public void addRow() {
    row = sheet.createRow(rowNumber++);
    columnNumber = 0;
  }

  public void addCell(Object value) {
    value = value == null ? "" : value;
    Cell cell = row.createCell(columnNumber++);
    if (value instanceof String) {
      cell.setCellValue((String) value);
    } else if (value instanceof Integer) {
      cell.setCellValue((Integer) value);
    }
//    else if (value instanceof LocalDate) {
//      cell.setCellValue((LocalDate) value);
//    }
  }

  public byte[] exportFileAsBytes() {
    try {
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      workbook.write(outputStream);
      return outputStream.toByteArray();
    } catch (Exception e) {
      return null;
    } finally {
      try {
        workbook.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
