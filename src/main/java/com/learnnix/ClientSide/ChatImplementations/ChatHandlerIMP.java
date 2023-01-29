package com.learnnix.ClientSide.ChatImplementations;

import com.learnnix.ClientSide.ChatIntefaces.ChatClientINF;
import com.learnnix.ClientSide.ChatIntefaces.ChatHandlerIF;
import com.learnnix.ClientSide.Student.Views.V_ClassLessons;
import com.learnnix.ServerSide.Interfaces.ChatServer;

import javax.swing.*;
import java.awt.*;
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
    private V_ClassLessons gui;

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
    public void bindWithGui(V_ClassLessons gui){
        this.gui = gui;
    }
    @Override
    public void retrieveMessage(String message, String sender) throws RemoteException {
        //ici on affiche le message dans le gui
        String newMessage = new String(sender+" : "+message);
        gui.addNewMessage(newMessage);
    }

    @Override
    public byte[] retrieveFile(String fileName, String sender) throws RemoteException {
        return new byte[0];
    }

    @Override
    public String getUsername() throws RemoteException {
        return chatClient.getUsername();
    }

    @Override
    public ArrayList<String> getClassMembers() throws RemoteException {
        return classMembers;
    }
}
