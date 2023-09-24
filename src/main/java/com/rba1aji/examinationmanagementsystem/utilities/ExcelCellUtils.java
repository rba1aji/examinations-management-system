package com.rba1aji.examinationmanagementsystem.utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class ExcelCellUtils {

  private static final DataFormatter dataFormatter = new DataFormatter();
  private static final DecimalFormat decimalFormat = new DecimalFormat("0");

  public static String getString(Cell cell) {
    try {
      String value = dataFormatter.formatCellValue(cell);
      return value;
    } catch (Exception e) {
      return null;
    }
  }

  public static Date getDate(Cell cell) {
    try {
      return cell.getDateCellValue();
    } catch (Exception e) {
      return null;
    }
  }

  public static String getDateString(Cell cell) {
    // format: ddmmyyyy
    try {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(getDate(cell));
      return String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)) + String.format("%02d", calendar.get(Calendar.MONTH) + 1) + calendar.get(Calendar.YEAR);
    } catch (Exception e) {
      return null;
    }
  }

  public static String getValue(Cell cell) {
    try {
      if (CellType.STRING.equals(cell.getCellType())) {
        return cell.getStringCellValue();
      } else if (CellType.NUMERIC.equals(cell.getCellType())) {
        return cell.getNumericCellValue() + "";
      } else if (CellType.BOOLEAN.equals(cell.getCellType())) {
        return cell.getBooleanCellValue() + "";
      } else if (CellType.ERROR.equals(cell.getCellType())) {
        return "";
      }
    } catch (Exception e) {
    }
    return null;
  }

}
