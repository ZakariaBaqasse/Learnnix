package com.learnnix.ServerSide.Interfaces;

import com.learnnix.HelperClasses.ClassInfos;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ProfessorServer extends Remote,ChatServer{
    public boolean professorLogin(String username,String password) throws RemoteException;
    public ArrayList<ClassInfos> getAssignedClasses(String profName) throws RemoteException;
    public void uploadFile(String fileName, byte[] fileData, String profName, int classId) throws RemoteException;
    public ArrayList<String> getFilesByClass(int classId) throws RemoteException;
}
