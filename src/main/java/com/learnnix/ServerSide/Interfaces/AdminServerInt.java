package com.learnnix.ServerSide.Interfaces;

import com.learnnix.HelperClasses.ClassInfos;
import com.learnnix.HelperClasses.ProfessorInfos;
import com.learnnix.HelperClasses.StudentInfos;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface AdminServerInt extends Remote {
    public boolean adminLogin(String username,String password) throws RemoteException;
    public List<ProfessorInfos> getAllProfessors() throws RemoteException;
    public boolean addProfessor(ProfessorInfos professor) throws RemoteException;
    public ProfessorInfos getProfessor(int id) throws RemoteException;
    public boolean updateProfInfo(int id,String profName,String password,String profSpeciality) throws RemoteException;
    public List<ClassInfos> getAllClasses() throws RemoteException;
    public boolean addClass(ClassInfos classInfos,String profName) throws RemoteException;
    public boolean updateClassInfos(ClassInfos classInfos) throws RemoteException;
    public ClassInfos getClass(int id) throws RemoteException;
    public List<StudentInfos> getAllStudents() throws RemoteException;
}
