package com.hamza.e_learningapp.models;

public class ModelMembers {



    private String studentEmail ;
    private String courseId ;
     private String courseName;
     private double attendanceGrade ;
     private double quizGrade ;

    public double getAttendanceGrade() {
        return attendanceGrade;
    }

    public void setAttendanceGrade(double attendanceGrade) {
        this.attendanceGrade = attendanceGrade;
    }

    public double getQuizGrade() {
        return quizGrade;
    }

    public void setQuizGrade(double quizGrade) {
        this.quizGrade = quizGrade;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }
}
