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
      if (marks.getStudent() != null) {
        return marks.getStudent().getRegisterNumber();
      }
      return "";
    }
  },

  STUDENT_NAME(2, "Student Name") {
    @Override
    public String getValue(Marks marks) {
      if (marks.getStudent() != null) {
        return marks.getStudent().getFullName();
      }
      return "";
    }
  },

  ATTENDANCE(3, "Attendance") {
    @Override
    public String getValue(Marks marks) {
      if (marks.getAttendance() != null) {
        return ConfigurationMaps.attendanceCharToString.get(marks.getAttendance());
      }
      return "";
    }
  },

  EVALUATION_PAPER_NUMBER(10, "Evaluation Paper Number") {
    @Override
    public String getValue(Marks marks) {
      if (marks.getEvaluationPaper() != null) {
        return "" + marks.getEvaluationPaper().getNumber();
      }
      return "";
    }
  },

  EVALUATION_PAPER_SPLIT_UP_MARKS(11, "SplitUp Marks") {
    @Override
    public String getValue(Marks marks) {
      if (marks.getEvaluationPaper() != null) {
        return marks.getEvaluationPaper().getSplitUpMarks().stream()
          .map(splitUpMarks -> splitUpMarks.getQuestion() + " = " + splitUpMarks.getMarks()).toList().toString();
      }
      return "";
    }
  },

  MARKS(4, "Marks") {
    @Override
    public Integer getValue(Marks marks) {
      return marks.getMarks();
    }
  },

  COURSE_CODE(5, "Course code") {
    @Override
    public String getValue(Marks marks) {
      return marks.getCourse().getCode();
    }
  },

  COURSE_NAME(6, "Course name") {
    @Override
    public String getValue(Marks marks) {
      return marks.getCourse().getName();
    }
  },

  EXAM_NAME(7, "Exam Name") {
    @Override
    public String getValue(Marks marks) {
      return marks.getExam().getName();
    }
  },

  FACULTY_IN_CHARGE(8, "Faculty InCharge") {
    @Override
    public String getValue(Marks marks) {
      if (marks.getExamBatch() != null) {
        return marks.getExamBatch().getFaculty().getFullName();
      }
      return "";
    }
  },
  EXAM_DATE(9, "Exam Date") {
    @Override
    public String getValue(Marks marks) {
      if (marks.getExamBatch() != null) {
        return marks.getExamBatch().getStartTime().toLocalDateTime().toLocalDate().toString();
      }
      return "";
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
