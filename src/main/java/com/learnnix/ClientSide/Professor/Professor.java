package com.learnnix.ClientSide.Professor;

import com.learnnix.ClientSide.ChatIntefaces.ChatClientINF;
import com.learnnix.HelperClasses.ClassInfos;
import com.learnnix.ServerSide.Interfaces.ProfessorServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Professor implements ChatClientINF {
    private String username;
    private String password;
    private static String SERVER_URL = "rmi://localhost/server";
    private ProfessorServer server ;
    public Professor(String username,String password) throws MalformedURLException, NotBoundException, RemoteException {
        this.username = username;
        this.password = password;
        this.server = (ProfessorServer) Naming.lookup(SERVER_URL);
    }
    @Override
    public String getUsername() {
        return username;
    }
    public boolean professorLogin(){
        try {
            return server.professorLogin(this.username,this.password);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public ProfessorServer getServer() {
        return server;
    }

    public ArrayList<ClassInfos> getAssignedClasses(){
        try {
            return server.getAssignedClasses(this.username);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void uploadFile(String fileName, byte[] fileContent,int classId){
        try {
            server.uploadFile(fileName,fileContent,this.username,classId);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> getFilesByClass(int classId){
        try {
            return server.getFilesByClass(classId);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
