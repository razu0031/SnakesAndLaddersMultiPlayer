
package com.razu.client;

import static com.razu.client.ClientBoard.*;
import static com.razu.client.UpdateUI.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerDataProcessor extends ClientBoard{
   
    public static void UPDATE(String messageFromServer){
        if(messageFromServer.length()!=0){
            String[] messages = messageFromServer.split("@");
            String message = messages[1];
            
            switch (messages[0]) {
                case "UserName":
                    processUserName(message);
                    break;
                case "PlayerList":
                    updateAvailablePlayerList(message);
                    break;
                case "GameData":
                    updateLudoBoard(message);
                    break;
                case "InvitationData":
                    showInvitation(message);
                default:
                    System.out.println("default break:"
                            + "\ntype:"+messages[0]
                            + "\nmessaage:"+message);
                    break;
            }
        }
    }
    
    private static void updateLudoBoard(String gameData){
        //have to split the game value and the name/number of player whose 
        //  turn to play
        
        //rough
        GameDataLabel.setText("Game Data : "+gameData);
    }
    
     private static void showInvitation(String invitationData){
        //have to split the invitationData to get the player intro who will play 
        String userName = userNickName;
        String[] data = invitationData.split("&");
        String playersName = data[0];
        String invitationFrom = data[1]; 
        
        List<String> playerList = new ArrayList(Arrays.asList(playersName.split(",")));
        playerList.remove(userName);
        
        String playerListWithoutThisUser = String.join(", ", playerList);
        String invaitationMessage;
        if(playerListWithoutThisUser.length()>1){
            invaitationMessage = "You along with "+playerListWithoutThisUser
                +" are invited to play by "+invitationFrom;
        }
        else{
            invaitationMessage = "You are invited to play by "+invitationFrom;
        }
        InvitationLabel.setText(invaitationMessage);
        setVisibility(InvitationPanel, true);
    }
    
    public static void updateAvailablePlayerList(String playersName){
        AvailablePlayerList.removeAll();
        if(playersName.length()!=0){
            String userName = userNickName;
            List<String> playerList = new ArrayList(Arrays.asList(playersName.split(",")));
            playerList.remove(userName);
            AvailablePlayerList.setListData(playerList.toArray(new String[playerList.size()]));
        }        
    }

    private static void processUserName(String message) {
        if(message.equals("NotAvailable")){
            setVisibility(SettingsBasePanel, true);
            NickNameBasePanel.setBackground(Color.red);
        }
        else if(message.equals("Available")){
            setVisibility(GamePanel, true);
            setVisibility(StartPanel, false);
            userNickName = UserNickNameTextField.getText();
            NickNameBasePanel.setBackground(new Color(240,240,240));
        }
    }

    
    
}
