package com.learnnix.ServerSide.Interfaces;

import com.learnnix.ClientSide.ChatIntefaces.ChatHandlerIF;

import java.awt.event.MouseEvent;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ChatServer extends Remote {
    public void broadcastMessage(String message, String sender) throws RemoteException;
    public void registerChatClient(String username) throws RemoteException;
    public void unregisterChatClient(ChatHandlerIF chatClient) throws RemoteException;
    public ArrayList<String> getClassMembers(int classID) throws RemoteException;
    public byte[] downloadFile(String fileName) throws RemoteException;
    public void broadCastMouseClick(MouseEvent e,String user) throws RemoteException;
    public void broadCastMouseDragged(MouseEvent e,String user) throws RemoteException;
    public void broadCastClear(String user) throws RemoteException;
}
