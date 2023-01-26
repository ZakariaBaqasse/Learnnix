package com.learnnix.ServerSide.Implementation;

import com.learnnix.HelperClasses.ClassInfos;
import com.learnnix.HelperClasses.ProfessorInfos;
import com.learnnix.HelperClasses.StudentInfos;
import com.learnnix.ServerSide.Interfaces.AdminServerInt;
import com.learnnix.ServerSide.ServerDB;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class Server extends UnicastRemoteObject implements AdminServerInt {
    private final ServerDB database;
    public Server(ServerDB database) throws RemoteException {
        super();
        this.database = database;
    }
    //fonction qui s'occupe du login de l'admin
    @Override
    public boolean adminLogin(String username, String password) throws RemoteException {
        return database.adminLogin(username,password);
    }

    @Override
    public List<ProfessorInfos> getAllProfessors() throws RemoteException {
        return database.getAllProfs();
    }

    @Override
    public boolean addProfessor(ProfessorInfos professor) throws RemoteException {
        return database.addProfessor(professor);
    }

    @Override
    public ProfessorInfos getProfessor(int id) throws RemoteException {
        return database.getProfessor(id);
    }

    @Override
    public boolean updateProfInfo(int id, String profName, String password, String profSpeciality) throws RemoteException {
        return database.updateProfInfo(id, profName, password, profSpeciality);
    }

    @Override
    public List<ClassInfos> getAllClasses() throws RemoteException {
        return database.getAllClasses();
    }

    @Override
    public boolean addClass(ClassInfos classInfos, String profName) throws RemoteException {
        return database.addClass(classInfos,profName);
    }

    @Override
    public boolean updateClassInfos(ClassInfos classInfos) throws RemoteException {
        return database.updateClassInfo(classInfos);
    }

    @Override
    public ClassInfos getClass(int id) throws RemoteException {
        return database.getClass(id);
    }

    @Override
    public List<StudentInfos> getAllStudents() throws RemoteException {
        return database.getAllStudents();
    }
}