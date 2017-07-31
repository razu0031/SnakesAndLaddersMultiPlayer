
package com.razu.client;

import static com.razu.client.ClientBoard.*;
import java.awt.Component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class UpdateUI {
    public static boolean diceRolling = false;
    static int random;
    
    public static void startPageAnimation(boolean continueAnim) throws IOException{
        
        Thread threadAnim = new Thread(new Runnable() {
            @Override
            public void run() {
                int startPont = -160;
                int endPoint = 560;
                int xSnake = -100;
                int ySnake = Snake.getY();
                int xSnake2 = Snake2.getX();
                int ySnake2 = Snake2.getY();
                int xLadder  = Ladder.getX();
                int yLadder  = Ladder.getY();
                boolean leftToRightSnake = true;
                boolean leftToRightSnake2 = false;
                boolean leftToRightLadder = true;
                
                while (continueAnim){
                    try {
                        
                        if(xSnake <endPoint && leftToRightSnake){
                            xSnake++;
                            Snake.setLocation(xSnake, ySnake);
                            if(xSnake == endPoint) leftToRightSnake = false;
                            Snake.setIcon(new ImageIcon(getClass().getResource("/com/razu/client/Images/snake.png")));
                        }else{
                            xSnake--;
                            Snake.setLocation(xSnake, ySnake);
                            if(xSnake == startPont) leftToRightSnake = true;
                            Snake.setIcon(new ImageIcon(getClass().getResource("/com/razu/client/Images/snake1Flip.png")));
                        }
                        
                        if(xSnake2 <endPoint-120 && leftToRightSnake2){
                            xSnake2++;
                            Snake2.setLocation(xSnake2, ySnake2);
                            if(xSnake2 == endPoint-120) leftToRightSnake2 = false;
                            Snake2.setIcon(new ImageIcon(getClass().getResource("/com/razu/client/Images/snake2Flip.png")));
                        }else{
                            xSnake2--;
                            Snake2.setLocation(xSnake2, ySnake2);
                            if(xSnake2 == startPont+10) leftToRightSnake2 = true;
                            Snake2.setIcon(new ImageIcon(getClass().getResource("/com/razu/client/Images/snake2.png")));
                        }
                        
                        /*if(xLadder <endPoint-10 && leftToRightLadder){
                            xLadder++;
                            Ladder.setLocation(xLadder, yLadder);
                            if(xLadder == endPoint-10) leftToRightLadder = false;
                        }else{
                            xLadder--;
                            Ladder.setLocation(xLadder, yLadder);
                            if(xLadder == startPont+20) leftToRightLadder = true;
                        }*/
                        
                        Thread.sleep(7);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ClientBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }                  
                }               
            }
        });
        threadAnim.start();
    }
     
    public static void setVisibility(JPanel panel, boolean trueFalse){
        panel.setVisible(trueFalse);
        Component[] components = panel.getComponents();
        for(Component component : components){
            if(component==SettingsBasePanel)
                setVisibility(SettingsBasePanel, false);
            else if(component==InvitationPanel)
                setVisibility(InvitationPanel, false);
            else if(component==RightUpPanel){
                setVisibility(SettingsBasePanel, trueFalse);
                InviteButton.setVisible(false);
            }
            else
                component.setVisible(trueFalse);
        }
    }
    
    public static void makeButtonBackgroundTransparent(JButton button){
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
    }
    
    public static void placeListElementAtCenter(JList list){
        DefaultListCellRenderer renderer = (DefaultListCellRenderer) list.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
    }
    
/*
    public static void rollDice() throws InterruptedException{
        
        Thread threadRoll = new Thread(new Runnable() {
            @Override
            public void run() {
                
                int rollingCount = 0;
                while (rollingCount < 10){
                    random = (int)(1+Math.random()*6);
                    rollingCount++;
                    
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {

                            System.out.println("hi="+random);
                            switch (random) {
                                case 1:moveTheDiceFront(Dice1);
                                       break;
                                case 2:moveTheDiceFront(Dice2);
                                       break;
                                case 3:moveTheDiceFront(Dice3);
                                       break;
                                case 4:moveTheDiceFront(Dice4);
                                       break;
                                case 5:moveTheDiceFront(Dice5);
                                       break;
                                case 6:moveTheDiceFront(Dice6);
                                       break;
                                default:break;
                            }                       
                        }
                    });
                    
                    if(rollingCount==10)DiceRollButtton.setEnabled(true);
                    
                    try {
                        Thread.sleep(200);
                    }catch (InterruptedException ex) {
                        System.err.println(ex);
                    }                                   
                } 
            }
        });
        threadRoll.start();
    } 
    */
    
    public static void rollDice(Client client) throws InterruptedException,IOException{
        
        Thread threadRoll = new Thread(new Runnable() {
            @Override
            public void run() {
                int rollingCount = 0;
                while (rollingCount < 10){
                    
                    int random = (int)(1+Math.random()*6);
                    rollingCount++;
                    
                    switch (random) {
                        case 1:moveTheDiceFront(Dice1);
                               break;
                        case 2:moveTheDiceFront(Dice2);
                               break;
                        case 3:moveTheDiceFront(Dice3);
                               break;
                        case 4:moveTheDiceFront(Dice4);
                               break;
                        case 5:moveTheDiceFront(Dice5);
                               break;
                        case 6:moveTheDiceFront(Dice6);
                               break;
                        default:break;
                    }

                    if(rollingCount==10){
                        client.SEND_MESSAGE_TO_SERVER("GameData@"+random+"", true);
                        DiceRollButtton.setEnabled(true);
                    }
                        
                    try {       
                        Thread.sleep(200);
                    } catch (InterruptedException ex) {
                        System.err.println(ex);
                    }                  
                } 
            }
        });
        threadRoll.start();
        
        //rough
        int rand = (int)(1+Math.random()*3);
        makeTheUserActive(rand);
        
    } 
    
    private static void moveTheDiceFront(JLabel dice){
        Dice1.setVisible(false);
        Dice2.setVisible(false);
        Dice3.setVisible(false);
        Dice4.setVisible(false);
        Dice5.setVisible(false);
        Dice6.setVisible(false);
        dice.setVisible(true);
        //Dice1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/razu/client/Images/dice/dicw+.png")));
    }
    
    private static void makeTheUserActive(int userNumber){
        User1LabelActive.setVisible(false);
        User2LabelActive.setVisible(false);
        User3LabelActive.setVisible(false);
        
        switch (userNumber) {
            case 1:
                User1LabelActive.setVisible(true);
                break;
            case 2:
                User2LabelActive.setVisible(true);
                break;
            case 3:
                User3LabelActive.setVisible(true);
                break;
            default:
                break;
        }
    }

//    public static final void UPDATE(String messageFromServer){
//        if(messageFromServer.length()!=0){
//            String[] messages = messageFromServer.split("@");
//            switch (messages[0]) {
//                case "PlayerList":
//                    updateAvailablePlayerList(messages[1]);
//                    break;
//                case "GameData":
//                    updateLudoBoard(messages[1]);
//                    break;
//                case "InvitationData":
//                    showInvitation(messages[1]);
//                default:
//                    AvailablePlayerList.removeAll();
//                    break;
//            }
//        }
//    }
    
//    private static void updateAvailablePlayerList(String playersName){
//        AvailablePlayerList.removeAll();
//        if(playersName.length()!=0){
//            String userName = userNickName+userPort;
//            List<String> playerList = new ArrayList(Arrays.asList(playersName.split(",")));
//            playerList.remove(userName);
//            AvailablePlayerList.setListData(playerList.toArray(new String[playerList.size()]));
//        }        
//    }
//    
//    private static void updateLudoBoard(String gameData){
//        //have to split the game value and the name/number of player whose 
//        //  turn to play
//        
//        //rough
//        GameDataLabel.setText("Game Data : "+gameData);
//    }
    
//    private static void showInvitation(String invitationData){
//        //have to split the invitationData to get the player intro who will play 
//        String userName = userNickName+userPort;
//        String[] data = invitationData.split("&");
//        String playersName = data[0];
//        String invitationFrom = data[1]; 
//        
//        List<String> playerList = new ArrayList(Arrays.asList(playersName.split(",")));
//        playerList.remove(userName);
//        
//        String playerListWithoutThisUser = String.join(", ", playerList);
//        String invaitationMessage;
//        if(playerListWithoutThisUser.length()>1){
//            invaitationMessage = "You along with "+playerListWithoutThisUser
//                +" are invited to play by "+invitationFrom;
//        }
//        else{
//            invaitationMessage = "You are invited to play by "+invitationFrom;
//        }
//        InvitationLabel.setText(invaitationMessage);
//        setVisibility(InvitationPanel, true);
//    }
    
    public static void sendInvite(Client client) throws IOException{
        List<String> selectedPlayerList = AvailablePlayerList.getSelectedValuesList();
        String playersName = String.join(",",selectedPlayerList);
        client.SEND_MESSAGE_TO_SERVER("InvitationData@"+playersName+"", true);       
    }
    
    public static List<String> controlInvitationButtonVisibility(){
        List<String> selectedPlayerList = AvailablePlayerList.getSelectedValuesList();
        int selectedCount = selectedPlayerList.size();
        if(selectedPlayerList.isEmpty() || !AvailablePlayerList.hasFocus() 
                            || InvitationPanel.isVisible() || selectedCount>3){
            InviteButton.setVisible(false);
        }
        else InviteButton.setVisible(true);
        
        return selectedPlayerList;
    }
      
}
