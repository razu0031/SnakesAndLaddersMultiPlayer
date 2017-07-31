
package com.razu.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    final InetAddress SERVER_ADDRESS;
    final int SERVER_PORT;
    final Socket CLIENT_SOCKET;
    
    final DataInputStream DATA_FROM_SERVER;
    final DataOutputStream DATA_TO_SERVER;
    BufferedReader messageFromUser;
    String messageFromServer;
    
    boolean continueGame;
    
    //Getting address of the server
    private static InetAddress getServerAddress() throws UnknownHostException {
        return InetAddress.getLocalHost();        
    }
    
    //Getting the dwdicated port number of the server
    private static int getServerPortNumber(){
        return 8282;        
    }

    public final String READ_MESSAGE_FROM_SERVER() throws IOException{
        String message = "";
        try{
            message = DATA_FROM_SERVER.readUTF(); 
            System.out.println("Response\t: "+message);
            return message;
        } catch (Exception ex) {
            System.err.println("\nConnection Lost\n");
            return message;
        }
    }
    
    public final boolean CONTINUE_COMMUNICATION() throws IOException{
        boolean bool = DATA_FROM_SERVER.readBoolean();
        System.out.println("Continue Game : "+bool);
        return bool;
    }
    
    //Writting message for server including continue or close the comunication message
    public final void SEND_MESSAGE_TO_SERVER(String messageFromClient, boolean CONTINUE_COMMUNICATION) {           
        try {
            DATA_TO_SERVER.writeUTF(messageFromClient);
            DATA_TO_SERVER.writeBoolean(CONTINUE_COMMUNICATION);
            DATA_TO_SERVER.flush();
            System.out.println("\n-> Input : "+messageFromClient
                    +",\tCONTINUE_COMMUNICATION = " + CONTINUE_COMMUNICATION);
            
        } catch (Exception ex) {
            System.err.println("\nConnection Lost\n");
        }
    }
    
    //Closing the communication
    public void closeCommunication() throws IOException{
        DATA_FROM_SERVER.close();
        DATA_TO_SERVER.close();
        CLIENT_SOCKET.close();
    }
    
    public Client() throws UnknownHostException, IOException {
        this.SERVER_ADDRESS = getServerAddress();
        SERVER_PORT = getServerPortNumber();      
        CLIENT_SOCKET = new Socket(SERVER_ADDRESS, SERVER_PORT);
        
        DATA_FROM_SERVER = new DataInputStream(CLIENT_SOCKET.getInputStream());
        DATA_TO_SERVER = new DataOutputStream(CLIENT_SOCKET.getOutputStream());
        messageFromUser = new BufferedReader(new InputStreamReader(System.in));
        messageFromServer = null;
    }
    
}
