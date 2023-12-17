package com.rba1aji.examinationmanagementsystem.enums;

import com.rba1aji.examinationmanagementsystem.model.Marks;
import com.rba1aji.examinationmanagementsystem.utilities.ConfigurationMaps;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum ExamMarksReportEnum {

  STUDENT_REGISTER_NUMBER(1, "Student Register Number") {
    @Override
    public String getValue(Marks marks) {
      return marks.getStudent().getRegisterNumber();
    }
  },

  STUDENT_NAME(2, "Student Name") {
    @Override
    public String getValue(Marks marks) {
      return marks.getStudent().getFullName();
    }
  },

  ATTENDANCE(3, "Attendance") {
    @Override
    public String getValue(Marks marks) {
      return ConfigurationMaps.attendanceCharToString.get(marks.getAttendance());
    }
  },

  MARKS(4, "Marks") {
    @Override
    public Integer getValue(Marks marks) {
      return marks.getMarks();
    }
  },

  EXAM_NAME(5, "Exam Name") {
    @Override
    public String getValue(Marks marks) {
      return marks.getExam().getName();
    }
  },

  COURSE_CODE(6, "Course code") {
    @Override
    public String getValue(Marks marks) {
      return marks.getCourse().getCode();
    }
  },

  COURSE_NAME(7, "Course name") {
    @Override
    public String getValue(Marks marks) {
      return marks.getCourse().getName();
    }
  },

  FACULTY_IN_CHARGE(8, "Faculty InCharge") {
    @Override
    public String getValue(Marks marks) {
      return marks.getExamBatch().getFaculty().getFullName();
    }
  },
  EXAM_DATE(9, "Exam Date") {
    @Override
    public String getValue(Marks marks) {
      return marks.getExamBatch().getStartTime().toLocalDateTime().toLocalDate().toString();
    }
  };

  private final int id;
  private final String header;

  ExamMarksReportEnum(int id, String header) {
    this.id = id;
    this.header = header;
  }

  public abstract <T> T getValue(Marks marks);

  public static final Map<Integer, ExamMarksReportEnum> idToEnumMap = new HashMap<>();

  static {
    for (ExamMarksReportEnum en : ExamMarksReportEnum.values()) {
      idToEnumMap.put(en.id, en);
    }
  }

}
