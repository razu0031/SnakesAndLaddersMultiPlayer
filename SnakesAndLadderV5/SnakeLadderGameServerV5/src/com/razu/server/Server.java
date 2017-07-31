
package com.razu.server;

import static com.razu.server.ServerConsole.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    public static ConcurrentHashMap<Integer, ClientProcess> clientThreadList;
    private int lastTeamNumber;
    private final int MAXIMUM_TEAM = 3;
    
    public Server(){
        clientThreadList = new ConcurrentHashMap<>();       
        this.lastTeamNumber = 1;
    }

    public static synchronized void sendAllTheAvailablePlayerList(){
//        String message = "PlayerList@"+GET_ALL_CLIENTS_NAME();
        String message = "PlayerList@"+GET_ALL_AVAILABLE_CLIENTS_NAME();
        clientThreadList.values().stream().forEach((ClientProcess clientProcess) -> {
            clientProcess.WRITE_MESSAGE_TO_CLIENT(message, true);
        });
    }
    
//    private void sendAllTheAvailablePlayerList(){
////        String message = "PlayerList@"+GET_ALL_CLIENTS_NAME();
//        String message = "PlayerList@"+GET_ALL_AVAILABLE_CLIENTS_NAME();
//        clientThreadList.values().stream().forEach((ClientProcess clientProcess) -> {
//            clientProcess.WRITE_MESSAGE_TO_CLIENT(message, true);
//        });
//    }
    
    public synchronized String createTeamName(){
        int teamNumber = lastTeamNumber;
        String teamName = teamNumber + "";
        int counter = 0;
        while(Data.engagedClientAndTeamList.containsValue(teamName)){
            System.out.println(counter+". ectlist"+Data.engagedClientAndTeamList);
            teamNumber ++;
            if(teamNumber==MAXIMUM_TEAM+1)teamNumber = 1;
            teamName = teamNumber + "";
            
            counter++;
            if(counter == 2*MAXIMUM_TEAM){
                teamName = "";
                break;
            }
            
        }
        lastTeamNumber = teamNumber;
        return teamName;
    }
    
    public synchronized void sendInvitation(List<String> playersList, String invitationFrom, String teamName){
        String players = String.join(",", playersList) + "&" + invitationFrom;
        String invitationMessage = "InvitationData@" + teamName + "@" +players;

        Data.engagedClientAndTeamList.put(invitationFrom, teamName);
        ConcurrentHashMap<String,String> replyList = new ConcurrentHashMap();
        replyList.put(invitationFrom, "yes");
        Data.availableClientList.remove(invitationFrom);

        for(String name : playersList){
            Data.engagedClientAndTeamList.put(name, teamName);
            replyList.put(name, "pending");

            int port = Data.clientPortListByName.get(name);
            ClientProcess clientProcess = clientThreadList.get(port);            
            clientProcess.WRITE_MESSAGE_TO_CLIENT(invitationMessage, true);
            
            Data.availableClientList.remove(name);
        }

        Data.pendingTeamList.put(teamName, replyList);
        sendAllTheAvailablePlayerList();
    }
    
    public final synchronized void ADD_CLIENT_NAME_TO_CLIENT_LIST(int port, String name) throws IOException{
        Data.clientNameListByPort.put(port, name);
        Data.clientPortListByName.put(name, port);
        Data.availableClientList.add(name);
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
        Data.availableClientList.remove(clientName);
        
        if(Data.engagedClientAndTeamList.containsKey(clientName)){
            String teamName = Data.engagedClientAndTeamList.get(clientName);
            Data.engagedClientAndTeamList.remove(clientName);
            
            if(Data.pendingTeamList.containsKey(teamName)){
                ConcurrentHashMap<String, String> replyList = Data.pendingTeamList.get(teamName);
                replyList.remove(clientName);
                Data.pendingTeamList.put(teamName, replyList); 
                ClientDataProcessor.processTheTeam(teamName, clientName);
                /*
                processThePendingTeam();
                1.  have to check the number of reamining members of the pending team
                2.  if the number greater than 1 then check all of them replies
                        2.1 if all replies are no then send the invitor that news
                                and delete the team and members info        
                            else wait untill all replies come when a reply will come 
                                have to do the peocess from 2.1
                    else send the invitor the news that all others are gone
                        and and delete the team and members info
                */
            }
            else if(Data.teamList.containsKey(teamName)){
                List<String> memberList = Data.teamList.get(teamName);
                memberList.remove(clientName);
                Data.teamList.put(teamName, memberList);  
                ClientDataProcessor.processTheTeam(teamName, clientName);
                /*
                remaining : have to notify all others member that a member has left
                1.  have to check how many member remaining in the team
                2.  if the number is greater than 1 then notify all other members
                        that one player is gone
                    else send the remaining the news that all others are gone
                        and and delete the team and members info
                */
            }
        }
        
        System.out.println("remove_ectlist"+Data.engagedClientAndTeamList);
        sendAllTheAvailablePlayerList();
        printAllClients();
    }
    
    public String GET_ALL_CLIENTS_NAME(){
        String clientsName = "";
        if(!Data.clientNameListByPort.isEmpty()){
            //clientsName = Data.clientNameList.values().stream().map((name) -> 
            //                name).reduce(clientsName, String::concat);
            clientsName = String.join(",",Data.clientNameListByPort.values());
        }
        return clientsName;
    }
    
    public static String GET_ALL_AVAILABLE_CLIENTS_NAME(){
        String clientsName = "";
        if(!Data.availableClientList.isEmpty()){
            clientsName = String.join(",",Data.availableClientList);
        }
        return clientsName;
    }
    
    private void printAllClients(){       
        System.out.println("Clients List\t: "+Data.clientNameListByPort);
        System.out.println("C.Threads\t: "+Server.clientThreadList);
        System.out.println("Clients Name\t: "+GET_ALL_CLIENTS_NAME());
        System.out.println("AvailableClients Name: "+GET_ALL_AVAILABLE_CLIENTS_NAME());
        
        println("Clients List\t: "+Data.clientNameListByPort);
        println("C.Threads\t\t: "+Server.clientThreadList);
        println("Clients Name\t: "+GET_ALL_CLIENTS_NAME());
        println("AvailableClients Name: "+GET_ALL_AVAILABLE_CLIENTS_NAME());
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
