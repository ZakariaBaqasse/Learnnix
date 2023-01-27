package com.learnnix.ClientSide.Student;

import com.learnnix.HelperClasses.ClassInfos;
import com.learnnix.HelperClasses.StudentInfos;
import com.learnnix.ServerSide.Interfaces.StudentServerInt;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class Student {
    private String email;
    private String password;
    private final StudentServerInt studentServer;
    private static String SERVER_URL = "rmi://localhost/server";

    public Student(String email, String password) throws MalformedURLException, NotBoundException, RemoteException {
        this.email = email;
        this.password = password;
        this.studentServer  = (StudentServerInt) Naming.lookup(SERVER_URL);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean studentLogin(StudentInfos student){
        try {
            return studentServer.studentLogin(student);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public String studentRegister(StudentInfos student){
        try {
            return studentServer.registerNewStudent(student);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ClassInfos> getJoinedClasses(String studentEmail){
        try {
            return  studentServer.getJoinedClasses(studentEmail);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ClassInfos> getNonJoinedClasses(String studentEmail){
        try {
            return studentServer.getNonJoinedClasses(studentEmail);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean joinClass(String studentEmail,int classId){
        try {
            return studentServer.joinClass(studentEmail,classId);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
