
package com.razu.client;

import java.awt.Frame;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;

public class Action {

    private final ClientBoard CLIENT_BOARD;
    private final UpdateUI UPDATE_UI;
    
    public Action(ClientBoard clientBoard){
        this.CLIENT_BOARD = clientBoard;
        this.UPDATE_UI = clientBoard.UPDATE_UI;
    }
    
    public void sendInvite(Client client) throws IOException{
        List<String> selectedPlayerList = CLIENT_BOARD.AvailablePlayerList.getSelectedValuesList();
        String playersName = String.join(",",selectedPlayerList);
        client.SEND_MESSAGE_TO_SERVER("InvitationData@"+playersName+"", true); 
    }
    
    public void getAndSetSelectedAvatar(){
        //AvatarPane avatarPane = showAvatarListDialog(CLIENT_BOARD);       
        //return avatarPane.selectedAvatarName;
        AvatarPane avatarPane = showAvatarListDialog(CLIENT_BOARD);
        String avatarName = avatarPane.selectedAvatarName;;
        UPDATE_UI.setTheUserAvatar(CLIENT_BOARD.ThisUserLabel, avatarName);
        if(ClientBoard.joinedInATeam)
            sendAllTheAvatarChanged(avatarName);
    }
    
    private AvatarPane showAvatarListDialog(Frame rootFrame){
        JDialog frame = new JDialog(rootFrame, null, true);
        AvatarPane avatarPane = new AvatarPane(frame);
        frame.getContentPane().add(avatarPane);
        frame.setBounds(rootFrame.getX()+150, rootFrame.getY()+150, 
                        avatarPane.getWidth(), avatarPane.getHeight());
        frame.setUndecorated(true);
        frame.pack();
        frame.setVisible(true);
        
        return avatarPane;
    }

    public void PlayButtonAction() { 
        if(CLIENT_BOARD.userNickName.trim().length()==0  || 
                 CLIENT_BOARD.userNickName.contains(",") || 
                 CLIENT_BOARD.userNickName.contains("@")
           ){
            UPDATE_UI.setVisibility(CLIENT_BOARD.SettingsBasePanel, true);
            CLIENT_BOARD.UserNickNameTextField.setText(CLIENT_BOARD.userNickName);
        }
        else {
            try {
                initializeThisUser(CLIENT_BOARD.userNickName);
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
    
    public void SettingsOkButtonAction() {
        System.out.println("len="+CLIENT_BOARD.UserNickNameTextField.getText().trim().length());
        String userName = CLIENT_BOARD.UserNickNameTextField.getText().trim();
        CLIENT_BOARD.serverAddress = CLIENT_BOARD.ServerAddressTextField.getText().trim();

        if(!userName.contains(",") && !userName.contains("@")){
            if(!CLIENT_BOARD.settingButtonClicked){
                try {
                    initializeThisUser(userName);

                } catch (IOException ex) {
                    System.err.println(ex);
                }
            }

            CLIENT_BOARD.settingButtonClicked = false;
            UPDATE_UI.setVisibility(CLIENT_BOARD.SettingsBasePanel, false);
        }
    }
    
    public void initializeThisUser(String userName) throws IOException{  
        CLIENT_BOARD.userPort = CLIENT_BOARD.client.CLIENT_SOCKET.getLocalPort();     
        if(CLIENT_BOARD.continueCommunication){
            String messageToClient = "UserName@"+userName;
            CLIENT_BOARD.client.SEND_MESSAGE_TO_SERVER(messageToClient, true);
            if(CLIENT_BOARD.thread == null)
                CLIENT_BOARD.startAndContinueGame();
        }
    }

    public void DiceRollButttonAction() {
        try{  
            if(ClientBoard.activeUser.equals(CLIENT_BOARD.userNickName)){
                CLIENT_BOARD.DiceRollButtton.setEnabled(false);
                UPDATE_UI.rollDice(CLIENT_BOARD.client);
            }else{
                String message = "!! Sorry !!. Its not your time. Wait for your turn :)";
                UPDATE_UI.showMessageDialog(CLIENT_BOARD, message, NotificationPane.ERRORR);
            }
        
        }catch(InterruptedException | IOException ex){
            System.err.print(ex);
        }
    }

    public void HomeButtonAction() {
        try {
            CLIENT_BOARD.client.SEND_MESSAGE_TO_SERVER("Bye", false);
            CLIENT_BOARD.thread.stop();
            CLIENT_BOARD.client.DATA_FROM_SERVER.close();
            CLIENT_BOARD.client.DATA_TO_SERVER.close();
            CLIENT_BOARD.client.CLIENT_SOCKET.close();

            CLIENT_BOARD.thread = null;
            CLIENT_BOARD.userNickName = "";
            CLIENT_BOARD.UserNickNameTextField.setText(CLIENT_BOARD.userNickName);

            UPDATE_UI.setVisibility(CLIENT_BOARD.GamePanel, false);
            UPDATE_UI.setVisibility(CLIENT_BOARD.StartPanel, true);

            CLIENT_BOARD.openNewConnection();

        } catch (IOException ex) {
            Logger.getLogger(ClientBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ChatSendButtonAction(String message) {
    }

    public void InvitationAcceptDeny(String reply) {
        String message = "InvitationReply@" + reply;
        CLIENT_BOARD.client.SEND_MESSAGE_TO_SERVER(message, true);
        UPDATE_UI.setVisibility(CLIENT_BOARD.InvitationPanel, false);
        CLIENT_BOARD.InvitationLabel.setText("");
        System.out.println("team replied as " + reply);
    }

    public void sendAllTheAvatarChanged(String avatarName) {
        String message = "Avatar@"+avatarName;
        CLIENT_BOARD.client.SEND_MESSAGE_TO_SERVER(message, true);
    }
}
