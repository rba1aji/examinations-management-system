package com.rba1aji.examinationmanagementsystem.utilities;

import org.apache.poi.ss.usermodel.Cell;

import java.util.Calendar;
import java.util.Date;

public class ExcelCellUtils {
  public static String getString(Cell cell) {
    try {
      return cell.getStringCellValue();
    } catch (Exception e) {
      return "";
    }
  }

  public static Date getDate(Cell cell) {
    try {
      return cell.getDateCellValue();
    } catch (Exception e) {
      return new Date();
    }
  }

  public static String getDateString(Cell cell) {
    // format: ddmmyyyy
    try {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(getDate(cell));
      return String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)) + String.format("%02d", calendar.get(Calendar.MONTH) + 1) + calendar.get(Calendar.YEAR);
    } catch (Exception e) {
      return new Date().toString();
    }
  }

}
