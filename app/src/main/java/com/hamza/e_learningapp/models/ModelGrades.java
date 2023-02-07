package com.hamza.e_learningapp.models;

public class ModelGrades {
    private int assignmentGrade, attendanceGrade;

    public ModelGrades() {
    }

    public ModelGrades(int assignmentGrade, int attendanceGrade) {
        this.assignmentGrade = assignmentGrade;
        this.attendanceGrade = attendanceGrade;
    }

    public int getAssignmentGrade() {
        return assignmentGrade;
    }

    public void setAssignmentGrade(int assignmentGrade) {
        this.assignmentGrade = assignmentGrade;
    }

    public int getAttendanceGrade() {
        return attendanceGrade;
    }

    public void setAttendanceGrade(int attendanceGrade) {
        this.attendanceGrade = attendanceGrade;
    }
}
