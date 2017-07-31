
package com.razu.server;

import com.razu.game.Game;
import static com.razu.server.ServerConsole.*;
import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientProcess extends Thread {
            
    //Things nedded to communicate with a client
    private final Socket CLIENT_SOCKET;
    private final DataInputStream DATA_FROM_CLIENT;
    private final DataOutputStream DATA_TO_CLIENT;
    
    final Server SERVER;
    Game game;
    
    //Trcaking a client
    final int CLIENT_NUMBER;
    String CLIENT_NAME = null;
    
    //Opening a ClientDataProcessor object for Handling this Client data
    ClientDataProcessor CLIENT_DATA_PROCESSOR;
    
    public ClientProcess(Server server, Socket socket, int clientNumber) throws IOException {
        
        //Initializing things nedded to communicate with a client
        this.CLIENT_SOCKET = socket;
        this.DATA_FROM_CLIENT = new DataInputStream(CLIENT_SOCKET.getInputStream());
        this.DATA_TO_CLIENT = new DataOutputStream(CLIENT_SOCKET.getOutputStream());
        this.SERVER = server;
        this.CLIENT_NUMBER = clientNumber;      
    }

    @Override
    public void run() {
        super.run();
        
        String messageFromClient;
        String messageToClient = null;
        boolean continueCommunication = true;
        
        //notifying client that the connection is established 
        messageToClient = "!! Welcome to SnakesAndLadders Game !!\tSelect available players to start the Game.";
        WRITE_MESSAGE_TO_CLIENT(messageToClient, true);
        
        //Opening a gameObject for the client
        //game= new Game();
        CLIENT_DATA_PROCESSOR = new ClientDataProcessor(this, CLIENT_SOCKET);
        
        try {
            while (continueCommunication) {
                
                messageFromClient = READ_MESSAGE_FROM_SERVER();
                continueCommunication = CONTINUE_COMMUNICATION();
                System.err.println("\n\t<- Client "+CLIENT_NUMBER+" ["+CLIENT_NAME+"] : "+messageFromClient);
                println("\n\t<- Client "+CLIENT_NUMBER+" ["+CLIENT_NAME+"] : "+messageFromClient, Color.BLUE);
                
                if(continueCommunication){
                    CLIENT_DATA_PROCESSOR.processClientMessage(messageFromClient);
                    //messageToClient = prepareMessageForClient(messageFromClient);
                    //WRITE_MESSAGE_TO_CLIENT(messageToClient, true);
                }
                /*if(!continueCommunication && messageFromClient.equals("START")){
                    continueCommunication = true;
                    //have to check if player avaialable
                    messageToClient = "Game Started.";
                    WRITE_MESSAGE_TO_CLIENT(messageToClient, true);
                }
                else if(continueCommunication){
                    messageToClient = game.continueGame(messageFromClient);
                    WRITE_MESSAGE_TO_CLIENT(messageToClient, true);
                }
                else {
                    messageToClient = "Please type START to start the Game.";
                    WRITE_MESSAGE_TO_CLIENT(messageToClient, true);
                }*/
                
                //System.out.println(CLIENT_SOCKET.getPort());
            }
            
                
            
        } catch (Exception e) {
            System.err.println(e);
            System.err.println("\nClient " + CLIENT_NUMBER +" ["+CLIENT_NAME+"] has disconnected due to an errror");
            println("\nClient " + CLIENT_NUMBER +" ["+CLIENT_NAME+"] has disconnected due to an errror", Color.RED);
        } 
        finally {            
            
            try {
                if(continueCommunication)WRITE_MESSAGE_TO_CLIENT("Bye..Bye..", false);
                CLOSE_COMMUNICATION();
                
            } catch (IOException e) {
                System.err.println("\nClient " + CLIENT_NUMBER +" ["+CLIENT_NAME+"] connection closing error");
                println("\nClient " + CLIENT_NUMBER +" ["+CLIENT_NAME+"] connection closing error", Color.RED);
            }           
        }
    }
    
    //Writting message for client including continue or close the comunication message
    public final void WRITE_MESSAGE_TO_CLIENT(String messageForClient, boolean continueCommunication) {      
        try{
            DATA_TO_CLIENT.writeUTF(messageForClient);              
            DATA_TO_CLIENT.writeBoolean(continueCommunication);
            DATA_TO_CLIENT.flush();
            System.out.println("\t-> Client " + CLIENT_NUMBER +" ["+CLIENT_NAME+"] : "+messageForClient
                    +" continueCommunication = " + continueCommunication);
            
            println("\t-> Client " + CLIENT_NUMBER +" ["+CLIENT_NAME+"] : "+messageForClient
                    +" continueCommunication = " + continueCommunication, new Color(0, 102, 0));
            
        }catch(Exception e){
            System.out.println("\t-> Client " + CLIENT_NUMBER +" ["+CLIENT_NAME+"] : "+messageForClient
                    +" continueCommunication = " + continueCommunication+"\tmessage writting error");
            
            println("\t-> Client " + CLIENT_NUMBER +" ["+CLIENT_NAME+"] : "+messageForClient
                    +" continueCommunication = " + continueCommunication+"\tmessage writting error", new Color(0, 102, 0));
        }
    }
    
    //Closing the communication
    private void CLOSE_COMMUNICATION() throws IOException{
        SERVER.REMOVE_CLIENT(CLIENT_SOCKET.getPort());
        DATA_FROM_CLIENT.close();
        DATA_TO_CLIENT.close();
        CLIENT_SOCKET.close();
    }
    
//    private void processClientMessage(String messageFormClient){
//        String[] messages = messageFormClient.split("@");
//        switch (messages[0]) {
//            case "GameData":
//                processGameData(messages[1]);
//                break;
//            case "InvitationData":
//                processInvitationData(messages[1]);
//            default:
//                break;
//        }
//    }
//    
//    private void processGameData(String data){
//        String messageToClient = "GameData@"+game.continueGame(data);
//        //have to add the clients name whose turn to roll dice with the message.
//        
//        WRITE_MESSAGE_TO_CLIENT(messageToClient, true);
//    }
//    
//    private void processInvitationData(String data){
//        String invitationFrom = CLIENT_NAME;
//        SERVER.sendInvitation(data, invitationFrom);
//    }
    
    private String prepareMessageForClient(String messageFromClient){
        String message = "GameData@"+game.continueGame(messageFromClient);
        //have to add the clients name whose turn to roll dice with the message.
        
        return message;
    }
    
    private String READ_MESSAGE_FROM_SERVER() throws IOException{
        String message = "";
        try{
            message = DATA_FROM_CLIENT.readUTF(); 
            return message;
        } catch (Exception ex) {
            return message;
        }
    }
    
    private boolean CONTINUE_COMMUNICATION() throws IOException{
        return DATA_FROM_CLIENT.readBoolean();
    }
    
}
