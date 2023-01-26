package com.learnnix.HelperClasses;

public class StudentInfos {
    private int studentId;
    private String studentUsername;
    private String studentEmail;
    private String studentPassword;

    public StudentInfos(int studentId, String studentUsername, String studentEmail, String studentPassword) {
        this.studentId = studentId;
        this.studentUsername = studentUsername;
        this.studentEmail = studentEmail;
        this.studentPassword = studentPassword;
    }

    public StudentInfos(int studentId, String studentUsername, String studentEmail) {
        this.studentId = studentId;
        this.studentUsername = studentUsername;
        this.studentEmail = studentEmail;
    }

    public StudentInfos(String studentUsername, String studentEmail) {
        this.studentUsername = studentUsername;
        this.studentEmail = studentEmail;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getStudentUsername() {
        return studentUsername;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public String getStudentPassword() {
        return studentPassword;
    }
}
