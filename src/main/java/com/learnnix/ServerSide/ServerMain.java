package com.learnnix.ServerSide;

import com.learnnix.ServerSide.Implementation.Server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;

public class ServerMain {
    public static void main(String[] args) {
      String DB_USERNAME = "root";
      String DB_PASSWORD = "root";
      String CONNECTION_STRING = "jdbc:mysql://localhost:3306/learnnix_app";
      int PORT = 1099;
        try {
            ServerDB database = new ServerDB(DB_USERNAME,DB_PASSWORD,CONNECTION_STRING);
            Server server = new Server(database);
            LocateRegistry.createRegistry(PORT);
            Naming.rebind("server",server);
            System.out.println("Server Up and Ready...");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
