package com.learnnix.ServerSide.Interfaces;

import com.learnnix.ClientSide.ChatIntefaces.ChatHandlerIF;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ChatServer extends Remote {
    public void broadcastMessage(String message, String sender) throws RemoteException;
    public void registerChatClient(String username) throws RemoteException;
    public void unregisterChatClient(ChatHandlerIF chatClient) throws RemoteException;
    public ArrayList<String> getClassMembers(int classID) throws RemoteException;
}
