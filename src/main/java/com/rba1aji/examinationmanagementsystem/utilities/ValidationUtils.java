package com.rba1aji.examinationmanagementsystem.utilities;

import java.util.Arrays;

public class ValidationUtils {

  public static boolean areNotNullAndNotEmpty(String... values) {
    if (values == null) {
      return false;
    }
    return Arrays.stream(values).noneMatch(val -> val == null || val.isEmpty());
  }

  public static int getInt(String string) {
    try {
      return Integer.parseInt(string);
    } catch (Exception e) {
      return 0;
    }
  }

  public static boolean isNotNullAndNotEmpty(String str) {
    return str != null && !str.trim().isEmpty();
  }
}
