package com.learnnix.ClientSide.ChatImplementations;

import com.learnnix.ClientSide.ChatIntefaces.ChatClientINF;
import com.learnnix.ClientSide.ChatIntefaces.ChatGUI;
import com.learnnix.ClientSide.ChatIntefaces.ChatHandlerIF;
import com.learnnix.ServerSide.Interfaces.ChatServer;

import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ChatHandlerIMP extends UnicastRemoteObject implements ChatHandlerIF{
    private ChatClientINF chatClient;
    private int classID;
    private ArrayList<String> classMembers;
    private ChatServer chatServer;
    private ChatGUI gui;

    public ChatHandlerIMP(ChatClientINF chatClient, int classID, ChatServer chatServer) throws RemoteException, MalformedURLException {
        super();
        this.chatClient = chatClient;
        this.classID = classID;
        this.chatServer = chatServer;
        classMembers = chatServer.getClassMembers(classID);
        Registry registry = LocateRegistry.getRegistry();
        registry.rebind(chatClient.getUsername(),this);
        chatServer.registerChatClient(this.chatClient.getUsername());
        System.out.println("Handler created");
    }
    //cette methode sert a recuperer le gui pour pouvoir afficher les messages
    public void bindWithGui(ChatGUI gui){
        this.gui = gui;
    }
    @Override
    public void retrieveMessage(String message, String sender) throws RemoteException {
        //ici on affiche le message dans le gui
        String newMessage = new String(sender+" : "+message);
        gui.addNewMessage(newMessage);
    }

    @Override
    public String getUsername() throws RemoteException {
        return chatClient.getUsername();
    }

    @Override
    public ArrayList<String> getClassMembers() throws RemoteException {
        return classMembers;
    }

    @Override
    public void retrieveFile(String fileName) throws RemoteException {
        gui.addNewFile(fileName);
    }



    public byte[] downloadFile(String fileName) throws RemoteException{
        return chatServer.downloadFile(fileName);
    }
    @Override
    public void notifyMouseClick(MouseEvent e) throws RemoteException {
       gui.mouseClicked(e);
    }

    @Override
    public void notifyMouseDrag(MouseEvent e) throws RemoteException {
       gui.mouseDragged(e);
    }


    public void broadCastMouseClick(MouseEvent e,String user){
        try {
            chatServer.broadCastMouseClick(e,user);
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void broadCastMouseDragged(MouseEvent e,String user){
        try {
            chatServer.broadCastMouseDragged(e,user);
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }
    @Override
    public void notifyClear() throws RemoteException {
      gui.clear();
    }

    public void broadCastClear(String user){
        try {
            chatServer.broadCastClear(user);
        } catch (RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }
}
