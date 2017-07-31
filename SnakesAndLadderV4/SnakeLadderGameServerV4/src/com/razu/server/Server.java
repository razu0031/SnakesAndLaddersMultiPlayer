
package com.razu.server;

import static com.razu.server.ServerConsole.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {

    private static HashMap<Integer, ClientProcess> clientThreadList;
    
    public Server() throws IOException{
        clientThreadList = new HashMap<>(); 
    }

    private void sendAllTheAvailablePlayerList(){
        String message = "PlayerList@"+GET_ALL_CLIENTS_NAME();
        clientThreadList.values().stream().forEach((ClientProcess clientProcess) -> {
            clientProcess.WRITE_MESSAGE_TO_CLIENT(message, true);
        });
    }
    
    public synchronized void sendInvitation(String playersNameList, String invitationFrom){
        String invitationMessage = "InvitationData@"+playersNameList+"&"+invitationFrom;
        String[] playersName = playersNameList.split(",");
        for(String name : playersName){
            int port = Data.clientPortListByName.get(name);
            ClientProcess clientProcess = clientThreadList.get(port);            
            clientProcess.WRITE_MESSAGE_TO_CLIENT(invitationMessage, true);
        }
    }
    
    public final synchronized void ADD_CLIENT_NAME_TO_CLIENT_LIST(int port, String name) throws IOException{
        Data.clientNameListByPort.put(port, name);
        Data.clientPortListByName.put(name, port);
        System.out.println("\nClient "+name+" from port "+port+" joined");
        println("\nClient "+name+" from port "+port+" joined");
        sendAllTheAvailablePlayerList();
        printAllClients();
    }
    
    public final synchronized void REMOVE_CLIENT(int port) throws IOException{
        System.out.println("\nClient "+Data.clientNameListByPort.get(port)+" left");
        println("\nClient "+Data.clientNameListByPort.get(port)+" left");
        clientThreadList.remove(port);
        String clientName = Data.clientNameListByPort.remove(port);
        Data.clientPortListByName.remove(clientName);
        sendAllTheAvailablePlayerList();
        printAllClients();
    }
    
    public final String GET_ALL_CLIENTS_NAME(){
        String clientsName = "";
        if(!Data.clientNameListByPort.isEmpty()){
            //clientsName = Data.clientNameList.values().stream().map((name) -> 
            //                name).reduce(clientsName, String::concat);
            clientsName = String.join(",",Data.clientNameListByPort.values());
        }
        return clientsName;
    }
    
    private void printAllClients(){       
        System.out.println("Clients List\t: "+Data.clientNameListByPort);
        System.out.println("C.Threads\t: "+Server.clientThreadList);
        System.out.println("Clients Name\t: "+GET_ALL_CLIENTS_NAME());
        
        println("Clients List\t: "+Data.clientNameListByPort);
        println("C.Threads\t\t: "+Server.clientThreadList);
        println("Clients Name\t: "+GET_ALL_CLIENTS_NAME());
    }
    
    public void RUN_SERVER(Server server) throws IOException{
        System.out.println("TCP MultiClient Server Started");
        println("TCP MultiClient Server Started");
        
        //opening a sockets and allocating a distinct port
        ServerSocket serverSocket=new ServerSocket(Data.SERVER_PORT);     
        Socket clientSocket;
        
        int clientCounter = 0;
        
        /*
        .Accepting request from distinct client identified by its port number
        
            .Port number for client lies in clientSocket and the port is not the 
                SERVER_PORT. It can be got using clientSocket.getPort() after
                accepting that clientSocket
        
            .It is randomly genrated by the java socket not by the programmer.It
                is generated when client open a socket by 
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)
        
            .If a client send requst everytime form the same socket then the port 
                number for that client will be same and it will be identified as 
                an exisiting client 
        
            .If a client send requst everytime form new socket then the port 
                number for that client will be new and it will be identified as 
                a new client 
        
        .Then opening a thread for that distint client which will then 
            communicate with that distinct thread 
        */
        while(true){           
            clientSocket = serverSocket.accept();
            
            clientCounter++;
            System.out.println("\nMain Server: Client " + clientCounter
                    + " from " + clientSocket.getInetAddress()
                    + "\tPort: " + clientSocket.getPort() + "\n");
            
            println("\nMain Server: Client " + clientCounter
                    + " from " + clientSocket.getInetAddress()
                    + "\tPort: " + clientSocket.getPort() + "\n");
          
            //have to pair if player avaialable
            
            ClientProcess thread=new ClientProcess(server, clientSocket, clientCounter);
            clientThreadList.put(clientSocket.getPort(),thread);
            thread.start();               
        }
    }
    
    public static void main(String[] args) throws IOException {
        
        Server server = new Server();
        System.out.println("TCP MultiClient Server Started");
        
        //opening a sockets and allocating a distinct port
        ServerSocket serverSocket=new ServerSocket(Data.SERVER_PORT);     
        Socket clientSocket;
        
        int clientCounter = 0;
        
        /*
        .Accepting request from distinct client identified by its port number
        
            .Port number for client lies in clientSocket and the port is not the 
                SERVER_PORT. It can be got using clientSocket.getPort() after
                accepting that clientSocket
        
            .It is randomly genrated by the java socket not by the programmer.It
                is generated when client open a socket by 
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)
        
            .If a client send requst everytime form the same socket then the port 
                number for that client will be same and it will be identified as 
                an exisiting client 
        
            .If a client send requst everytime form new socket then the port 
                number for that client will be new and it will be identified as 
                a new client 
        
        .Then opening a thread for that distint client which will then 
            communicate with that distinct thread 
        */
        while(true){           
            clientSocket = serverSocket.accept();
            
            clientCounter++;
            System.out.println("\nMain Server: Client " + clientCounter
                    + " from " + clientSocket.getInetAddress()
                    + "\tPort: " + clientSocket.getPort() + "\n");
          
            //have to pair if player avaialable
            
            ClientProcess thread=new ClientProcess(server, clientSocket, clientCounter);
            clientThreadList.put(clientSocket.getPort(),thread);
            thread.start();               
        }
    }
    
}
