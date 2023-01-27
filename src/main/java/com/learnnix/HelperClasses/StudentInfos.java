package com.learnnix.HelperClasses;

import java.io.Serializable;

public class StudentInfos implements Serializable {
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

    public StudentInfos(String studentPassword, String studentEmail) {
        this.studentPassword = studentPassword;
        this.studentEmail = studentEmail;
    }

    public StudentInfos(String studentUsername, String studentEmail, String studentPassword) {
        this.studentUsername = studentUsername;
        this.studentEmail = studentEmail;
        this.studentPassword = studentPassword;
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
