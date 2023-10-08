package com.rba1aji.examinationmanagementsystem.utilities;

import org.springframework.stereotype.Component;

@Component
public class CommonUtils {

  public static String commaSeperatedSqlString(String str) {
    if (ValidationUtils.isNotNullAndNotEmpty(str)) {
      return "'" + str.trim().replace(",", "','") + "'";
    }
    return "";
  }

}
