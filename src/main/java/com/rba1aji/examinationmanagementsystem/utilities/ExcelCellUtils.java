package com.rba1aji.examinationmanagementsystem.utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class ExcelCellUtils {

  private static final DataFormatter dataFormatter = new DataFormatter();

  public static String getString(Cell cell) {
    return dataFormatter.formatCellValue(cell);
  }

  public static Date getDate(Cell cell) {
    try {
      return cell.getDateCellValue();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static String getDateString(Cell cell) {
    // format: ddmmyyyy
    try {
      Calendar calendar = Calendar.getInstance();
      Date date = getDate(cell);
      if (date != null) {
        calendar.setTime(date);
        return String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)) + String.format("%02d", calendar.get(Calendar.MONTH) + 1) + calendar.get(Calendar.YEAR);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static int getInt(Cell cell) {
    String value = getString(cell);
    if (ValidationUtils.areNotNullAndNotEmpty(value)) {
      return ValidationUtils.getInt(value);
    }
    return 0;
  }

  public static String getValue(Cell cell) {
    try {
      if (CellType.STRING.equals(cell.getCellType())) {
        return cell.getStringCellValue();
      } else if (CellType.NUMERIC.equals(cell.getCellType())) {
        return String.valueOf(cell.getNumericCellValue());
      } else if (CellType.BOOLEAN.equals(cell.getCellType())) {
        return String.valueOf(cell.getBooleanCellValue());
      } else if (CellType.ERROR.equals(cell.getCellType())) {
        return "";
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

}
