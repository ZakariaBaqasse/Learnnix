package com.learnnix.ClientSide.ChatIntefaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ChatHandlerIF extends Remote {
    public void retrieveMessage(String message, String sender) throws RemoteException;
    public byte[] retrieveFile(String fileName,String sender) throws RemoteException;
    public String getUsername() throws RemoteException;
    public ArrayList<String> getClassMembers() throws RemoteException;
}
