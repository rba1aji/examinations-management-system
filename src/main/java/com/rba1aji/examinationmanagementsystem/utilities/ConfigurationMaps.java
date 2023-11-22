package com.rba1aji.examinationmanagementsystem.utilities;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ConfigurationMaps {
  public static Map<Character, String> attendanceCharToString = Map.of(
      'P', "Present",
      'A', "Absent",
      'O', "On Duty",
      'W', "Withdraw",
      '-', "Not Entered"
  );

}
