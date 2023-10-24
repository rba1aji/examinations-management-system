package com.rba1aji.examinationmanagementsystem.utilities;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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

  public static long getLong(String str) {
    try {
      return Long.parseLong(str);
    } catch (Exception e) {
      return 0;
    }
  }

  public static boolean validStartTimeEndTime(Timestamp startTime, Timestamp endTime) {
    if (startTime == null || endTime == null) {
      return false;
    }
    return startTime.compareTo(endTime) < 0;
  }

  public static boolean isNotNullAndNotEmpty(Collection<?> batch) {
    return batch != null && !batch.isEmpty();
  }

  public static boolean isNullOrEmpty(String token) {
    return token == null || token.isEmpty();
  }
}
