package com.learnnix.ClientSide.ChatIntefaces;

import java.awt.event.MouseEvent;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ChatHandlerIF extends Remote {
    public void retrieveMessage(String message, String sender) throws RemoteException;
    public String getUsername() throws RemoteException;
    public ArrayList<String> getClassMembers() throws RemoteException;
    public void retrieveFile(String fileName) throws RemoteException;
    public void notifyMouseClick(MouseEvent e) throws RemoteException;
    public void notifyMouseDrag(MouseEvent e) throws RemoteException;
    public void notifyClear() throws RemoteException;
}
