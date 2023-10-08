package com.rba1aji.examinationmanagementsystem.utilities;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CommonUtils {

  public static String commaSeperatedSqlString(String str) {
    if (ValidationUtils.isNotNullAndNotEmpty(str)) {
      return "'" + str.trim().replace(",", "','") + "'";
    }
    return "";
  }

  public static List<String> getStringList(String batch) {
    if (ValidationUtils.isNotNullAndNotEmpty(batch)) {
      return Arrays.stream(batch.split(",")).toList();
    }
    return null;
  }

  public static List<Integer> getIntegerList(String semester) {
    if (ValidationUtils.isNotNullAndNotEmpty(semester)) {
      return Arrays.stream(semester.split(",")).map(Integer::parseInt).toList();
    }
    return null;
  }
}
