
package com.razu.client;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JLabel;

public class ServerDataProcessor{
    
    private final ClientBoard CLIENT_BOARD;
    private final UpdateUI UPDATE_UI;
    
    public Thread threadWait;
    public boolean wait = false;    
    private final int WaitTimeInSecond = 10;
    
    public ServerDataProcessor(ClientBoard clientBoard){
        this.CLIENT_BOARD = clientBoard;
        this.UPDATE_UI = clientBoard.UPDATE_UI;
    } 
    
    public void UPDATE(String messageFromServer){
        try{
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
                        
                    case "InvitationData":
                        String teamName = message;
                        String invitationData = messages[2];
                        showInvitation(teamName, invitationData);
                        break;
                        
                    case "InvitationReply":
                        processInvitationReply(message, messages);
                        break;
                        
                    case "GameData":
                        String score = message;
                        String playerName = messages[2];
                        String nextPlayer = messages[3];
                        updateLudoBoard(score, playerName, nextPlayer);
                        break;
                    
                    case "PlayerGone":
                        String gonePlayer = message;
                        String next = messages[2];
                        processPlayerGone(gonePlayer, next);
                        break;
                    
                    case "AllGone":
                        processAllPlayerGone();
                        break;
                        
                    case "Avatar":
                        String sender = messages[2];
                        processAvatarData(message, sender);
                        break;
                        
                    default:
                        System.out.println("default break:"
                                + "\ntype:"+messages[0]
                                + "\nmessaage:"+message);
                        break;
                }
            }
        }catch(ArrayIndexOutOfBoundsException ex){
            CLIENT_BOARD.AvailablePlayerList.setListData(new String[0]);
            System.out.println("scAIOBEX in processor"+ex);
        }
    }
    
    private void updateLudoBoard(String score, String playerName, String nextPlayer){
        //have to split the game value and the name/number of player whose 
        //  turn to play
        
        //rough
        //CLIENT_BOARD.GameDataLabel.setText("Game Data : "+gameData);
        
        JLabel userScoreJLabel = ClientBoard.playersNameAndScoreJLabel.get(playerName);
        UPDATE_UI.makeTheUserActive(ClientBoard.playersNameAndNumber.get(nextPlayer)); 
        userScoreJLabel.setText(score);
        ClientBoard.activeUser = nextPlayer;
        
        //int sc
        
//        if(Integer.parseInt(score) > 15){
//            UPDATE_UI.setVisibility(CLIENT_BOARD.ListBasePanel, true);
//            UPDATE_UI.setVisibility(CLIENT_BOARD.ChatBasePanel, false);
//            CLIENT_BOARD.AvailablePlayerListHeading.setText("Available Players");
//        }
    }
    
     private void showInvitation(String teamName, String invitationData){
        //have to split the invitationData to get the player intro who will play 
        String userName = CLIENT_BOARD.userNickName;
        String[] data = invitationData.split("&");
        String playersName = data[0];
        String invitationFrom = data[1]; 
        System.out.println("teamName="+teamName);
        List<String> playerList = new ArrayList(Arrays.asList(playersName.split(",")));
        playerList.remove(userName);
        
        String playerListWithoutThisUser = String.join(", ", playerList);
        String invaitationMessage = "";
        if(playerListWithoutThisUser.length()>1){
            invaitationMessage = "You along with "+playerListWithoutThisUser
                +" are invited to play by "+invitationFrom;
        }
        else{
            invaitationMessage = "You are invited to play by "+invitationFrom;
        }
        CLIENT_BOARD.InvitationLabel.setText(invaitationMessage);
        UPDATE_UI.setVisibility(CLIENT_BOARD.InvitationPanel, true);
        
        wait = true;
        try {
            waitForUserAction(teamName);
        } catch (InterruptedException | IOException ex) {
            System.out.println("Wait error : "+ex);
        }
    }
     
    public void waitForUserAction(String teamName) throws InterruptedException,IOException{
        
        threadWait = new Thread(new Runnable() {
            @Override
            public void run() {
                int second = 0;
                String finalMessage = "InvitationReply@" + teamName;
                while (wait){
                    second++;
                    if(second==WaitTimeInSecond+1){
                        wait = false;
                        CLIENT_BOARD.ACTION.InvitationAcceptDeny("no");
                        System.out.println("wait_teamname="+teamName+" replied as no");
                    }
                    
                    try {       
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        System.err.println(ex);
                    }
                } 
            }
        });
        threadWait.start();        
    }
    
    public void updateAvailablePlayerList(String playersName){
        CLIENT_BOARD.AvailablePlayerList.removeAll();
        if(playersName.length()!=0){
            String userName = CLIENT_BOARD.userNickName;
            List<String> playerList = new ArrayList(Arrays.asList(playersName.split(",")));
            playerList.remove(userName);
            CLIENT_BOARD.AvailablePlayerList.setListData(playerList.toArray(new String[playerList.size()]));
        }        
    }

    private void processUserName(String message) {
        if(message.equals("NotAvailable")){
            UPDATE_UI.setVisibility(CLIENT_BOARD.SettingsBasePanel, true);
            CLIENT_BOARD.NickNameBasePanel.setBackground(Color.red);
        }
        else if(message.equals("Available")){
            UPDATE_UI.setVisibility(CLIENT_BOARD.GamePanel, true);
            UPDATE_UI.setVisibility(CLIENT_BOARD.StartPanel, false);
            CLIENT_BOARD.userNickName = CLIENT_BOARD.UserNickNameTextField.getText();
            CLIENT_BOARD.NickNameBasePanel.setBackground(new Color(240,240,240));
            CLIENT_BOARD.ThisUserScoreLabel.setText("0");
            CLIENT_BOARD.ThisUserName.setText(CLIENT_BOARD.userNickName);
        }
    }

    private void processInvitationReply(String message, String[] messages) {
        switch (message) {
            case "notAvailable":
                {
                    String dialogMessage = "All your selected players are not available now !!";
                    UPDATE_UI.showMessageDialog(CLIENT_BOARD, dialogMessage, NotificationPane.WARNING);
                    break;
                }
            case "busy":
                {
                    String dialogMessage = "!! Server is Crowed !!\nTry sometime later.";
                    UPDATE_UI.showMessageDialog(CLIENT_BOARD, dialogMessage, NotificationPane.ERRORR);
                    break;
                }
            case "pending":
                {
                    String dialogMessage = "You have already engaged in a Queue.\nTry again when that finishes.";           
                    UPDATE_UI.showMessageDialog(CLIENT_BOARD, dialogMessage, NotificationPane.WARNING);
                    break;
                }
            case "none":
                {
                    String dialogMessage = "!! Sorry !!\nNo one else to play this time.";
                    UPDATE_UI.showMessageDialog(CLIENT_BOARD, dialogMessage, NotificationPane.WARNING);
                    break;
                }
            default:
                {
                    String dialogMessage = "New game created with the players "+message;
                    UPDATE_UI.showMessageDialog(CLIENT_BOARD, dialogMessage, NotificationPane.INFORMATION);
                    ClientBoard.activeUser = messages[2];
                    processStartGame(message, ClientBoard.activeUser);
                    break;
                }
        }
    }

    private void processStartGame(String playerList, String activeUser){
        ClientBoard.joinedInATeam = true;
        processThesePlayers(playerList);
        UPDATE_UI.makeTheUserActive(ClientBoard.playersNameAndNumber.get(activeUser));
        CLIENT_BOARD.ACTION.getAndSetSelectedAvatar();                               
    }
    
    private void processThesePlayers(String message) {
        List<String> playerList = new ArrayList(Arrays.asList(message.split(",")));
        String thisUserName = CLIENT_BOARD.userNickName;
        playerList.remove(thisUserName);
        ClientBoard.playersNameAndNumber.put(thisUserName, 1);
        ClientBoard.playersNameAndJLabel.put(thisUserName, CLIENT_BOARD.ThisUserLabel);
        ClientBoard.playersNameAndScoreJLabel.put(thisUserName, CLIENT_BOARD.ThisUserScoreLabel);
                
        UPDATE_UI.hideAllPlayer();
                
        int playerNumber = 2;
        for(String name : playerList){
            ClientBoard.playersNameAndNumber.put(name, playerNumber);
            UPDATE_UI.setThisPlayer(name, playerNumber);
            playerNumber++;
        }
        
    }

    private void processAvatarData(String avatarName, String sender) {
        JLabel userJLabel = ClientBoard.playersNameAndJLabel.get(sender);
        System.out.println("an="+avatarName+"  ul="+userJLabel);
        UPDATE_UI.setTheUserAvatar(userJLabel, avatarName);
    }

    private void processPlayerGone(String gonePlayer, String nextPlayer) {
        ClientBoard.activeUser = nextPlayer;
        UPDATE_UI.makeTheUserActive(ClientBoard.playersNameAndNumber.get(nextPlayer)); 
        ClientBoard.playersNameAndJLabel.remove(gonePlayer);
        int playerNumber = ClientBoard.playersNameAndNumber.remove(gonePlayer);
        ClientBoard.playersNameAndScoreJLabel.remove(gonePlayer);
        
        String message = "The player " + gonePlayer + " has left."; 
        UPDATE_UI.showMessageDialog(CLIENT_BOARD, message, NotificationPane.WARNING);
        UPDATE_UI.showHidePlayer(playerNumber, false);
    }

    private void processAllPlayerGone() {
        String message = "All other players have left. The Game has now closed."; 
        UPDATE_UI.showMessageDialog(CLIENT_BOARD, message, NotificationPane.WARNING);
        ClientBoard.activeUser = "";
        UPDATE_UI.showHidePlayer(2, false);
        UPDATE_UI.showHidePlayer(3, false);
        UPDATE_UI.showHidePlayer(4, false);
        
        processReInitializeThisUser();
    }

    private void processReInitializeThisUser() {

    }

    
    
}
