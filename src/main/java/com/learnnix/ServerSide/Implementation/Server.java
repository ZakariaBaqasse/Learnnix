package com.learnnix.ServerSide.Implementation;


import com.learnnix.ClientSide.ChatIntefaces.ChatHandlerIF;
import com.learnnix.HelperClasses.ClassInfos;
import com.learnnix.HelperClasses.ProfessorInfos;
import com.learnnix.HelperClasses.StudentInfos;
import com.learnnix.ServerSide.Interfaces.AdminServerInt;
import com.learnnix.ServerSide.Interfaces.StudentServerInt;
import com.learnnix.ServerSide.ServerDB;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Server extends UnicastRemoteObject implements AdminServerInt, StudentServerInt {
    private final ServerDB database;
    private static final String CLIENT_URL_START = "rmi://localhost/";
    private ArrayList<ChatHandlerIF> chatClients;
    public Server(ServerDB database) throws RemoteException {
        super();
        this.database = database;
        chatClients = new ArrayList<>();
    }
    /////////////////////////////////////////////////////Admin Functions///////////////////////////////////////
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

    ///////////////////////////////////////////////////////Student Functions/////////////////////////////
    @Override
    public boolean studentLogin(StudentInfos student) throws RemoteException {
        return database.studentLogin(student);
    }

    @Override
    public String registerNewStudent(StudentInfos student) throws RemoteException {
        return database.registerStudent(student);
    }

    @Override
    public List<ClassInfos> getJoinedClasses(String studentEmail) throws RemoteException {
        return database.getJoinedClasses(studentEmail);
    }

    @Override
    public List<ClassInfos> getNonJoinedClasses(String studentEmail) throws RemoteException {
        return database.getNonJoinedClasses(studentEmail);
    }

    @Override
    public boolean joinClass(String studentEmail, int classId) throws RemoteException {
        return database.joinClass(studentEmail,classId);
    }
    //////////fonctionnalites de chat de groupe///////////////////////////

    //cette fonction recoit un message puis le diffuse a tous les clients connectes
    @Override
    public void broadcastMessage(String message, String sender) throws RemoteException {
        //recuperer l'objet appartenenat a l'expediteur du message
        ChatHandlerIF chatHandler = getChatHandler(sender);
        //filtrer les clients connectes qui sont dans la meme classe que le client qui a envoye le message
        chatClients.stream().filter(chatClient-> {
            try {
                return chatHandler.getClassMembers().contains(chatClient.getUsername()) && !chatClient.getUsername().equals(chatHandler.getUsername());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }).forEach(chatClient -> {
            try {
                //envoyer le message a chaque client connecte qui est dans la meme classe que le client qui a envoye le message
                chatClient.retrieveMessage(message, sender);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void registerChatClient(String username) throws RemoteException {
        Registry registry = LocateRegistry.getRegistry();
        try {
            ChatHandlerIF chatHandler = (ChatHandlerIF) registry.lookup(username);
            chatClients.add(chatHandler);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void unregisterChatClient(ChatHandlerIF chatClient) throws RemoteException {
        chatClients.remove(chatClient);
    }

    @Override
    public ArrayList<String> getClassMembers(int classID) throws RemoteException {
        return database.getClassMembers(classID);
    }

    //fonction qui retourne les membres de classes online

    //fonction d'aide pour recuperer le chatHandler de l'expediteur
    private ChatHandlerIF getChatHandler(String username){
        return chatClients.stream().filter(chatClient-> {
            try {
                return chatClient.getUsername().equals(username);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }).findFirst().get();
    }
}
