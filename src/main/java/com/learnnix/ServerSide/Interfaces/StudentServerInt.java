package com.learnnix.ServerSide.Interfaces;

import com.learnnix.HelperClasses.ClassInfos;
import com.learnnix.HelperClasses.StudentInfos;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface StudentServerInt extends Remote, ChatServer {
    public boolean studentLogin(StudentInfos student) throws RemoteException;
    public String registerNewStudent(StudentInfos student) throws RemoteException;
    public List<ClassInfos> getJoinedClasses(String studentEmail) throws RemoteException;
    public List<ClassInfos> getNonJoinedClasses(String studentEmail) throws RemoteException;
    public boolean joinClass(String studentEmail,int classId) throws RemoteException;
    public ArrayList<String> getUploadedFiles(int classId) throws RemoteException;
}
