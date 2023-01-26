package com.learnnix.ClientSide.Admin;

import com.learnnix.HelperClasses.ClassInfos;
import com.learnnix.HelperClasses.ProfessorInfos;
import com.learnnix.HelperClasses.StudentInfos;
import com.learnnix.ServerSide.Interfaces.AdminServerInt;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class Admin {
    private String username;
    private String password;
    private AdminServerInt adminServer;
    private static String SERVER_URL = "rmi://localhost/server";

    public Admin(String username, String password) throws MalformedURLException, NotBoundException, RemoteException {
        this.username = username;
        this.password = password;
        this.adminServer = (AdminServerInt) Naming.lookup(SERVER_URL);
    }

    //fonction qui s'occupe du login
    public boolean login(){
        try {
            return adminServer.adminLogin(this.username,this.password);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    //fonction qui recherche la liste des professeurs
    public List<ProfessorInfos> getAllProfs(){
        try {
            return adminServer.getAllProfessors();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addProfessor(ProfessorInfos professor){
        try {
            return adminServer.addProfessor(professor);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public ProfessorInfos getProfessor(int id){
        try {
            return adminServer.getProfessor(id);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateProfInfo(int id, String profName, String password, String profSpeciality){
        try {
            return adminServer.updateProfInfo(id, profName, password, profSpeciality);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ClassInfos> getAllClasses(){
        try {
            return adminServer.getAllClasses();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addClass(ClassInfos classInfos,String profName){
        try {
            return adminServer.addClass(classInfos,profName);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateClassInfo(ClassInfos classInfos){
        try {
            return  adminServer.updateClassInfos(classInfos);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public ClassInfos getClass(int id){
        try {
            return adminServer.getClass(id);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public List<StudentInfos> getAllStudents(){
        try {
            return adminServer.getAllStudents();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
