
package com.razu.server;

import com.razu.game.Game;
import java.io.IOException;
import java.net.Socket;


public class ClientDataProcessor {
    private final ClientProcess CLIENT_PROCESS;
    private final Socket CLIENT_SOCKET;
    private final Server SERVER;
    private final Game GAME;
    
    public ClientDataProcessor(ClientProcess clientProcess, Socket socket){
        this.CLIENT_PROCESS = clientProcess;
        this.CLIENT_SOCKET = socket;
        this.SERVER = clientProcess.SERVER;
        this.GAME = clientProcess.game;       
    }
    
    public void processClientMessage(String messageFormClient) throws IOException{
        String[] messages = messageFormClient.split("@");
        String message = messages[1];
        
        switch (messages[0]) {
            case "UserName":
                processUserName(message);
                break;
                
            case "GameData":
                processGameData(message);
                break;
                
            case "InvitationData":
                processInvitationData(message);
                break;
                
            default:
                break;
        }
    }
    
    private void processGameData(String data){
        String messageToClient = "GameData@"+GAME.continueGame(data);
        //have to add the clients name whose turn to roll dice with the message.
        
        CLIENT_PROCESS.WRITE_MESSAGE_TO_CLIENT(messageToClient, true);
    }
    
    private void processInvitationData(String data){
        String invitationFrom = Data.clientNameListByPort.get(CLIENT_SOCKET.getPort());
        SERVER.sendInvitation(data, invitationFrom);
    }

    private void processUserName(String userName) throws IOException {
        String messageToClient = "";
        if(Data.clientNameListByPort.containsValue(userName)){
            messageToClient = "UserName@NotAvailable";
            CLIENT_PROCESS.WRITE_MESSAGE_TO_CLIENT(messageToClient, true);
        }
        else{
            messageToClient = "UserName@Available";
            CLIENT_PROCESS.WRITE_MESSAGE_TO_CLIENT(messageToClient, true);
            SERVER.ADD_CLIENT_NAME_TO_CLIENT_LIST(CLIENT_SOCKET.getPort(), userName);
        }
        
        
        
        
    }
}
