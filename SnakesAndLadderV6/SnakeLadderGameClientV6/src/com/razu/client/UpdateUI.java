
package com.razu.client;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Image;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class UpdateUI {
    public boolean diceRolling = false;
    public int random;
    
    private final ClientBoard CLIENT_BOARD;
    
    public UpdateUI(ClientBoard clientBoard){
        this.CLIENT_BOARD = clientBoard;
    }
    
    public void startPageAnimation(boolean continueAnim) throws IOException{
        
        JLabel Snake = CLIENT_BOARD.Snake;
        JLabel Snake2 = CLIENT_BOARD.Snake2;
        JLabel Ladder = CLIENT_BOARD.Ladder;
        
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
                        
                        Thread.sleep(7);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ClientBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }                  
                }               
            }
        });
        threadAnim.start();
    }
     
    public void setVisibility(JPanel panel, boolean trueFalse){
        panel.setVisible(trueFalse);
        Component[] components = panel.getComponents();
        for(Component component : components){
            if(component==CLIENT_BOARD.SettingsBasePanel)
                setVisibility(CLIENT_BOARD.SettingsBasePanel, false);
            else if(component==CLIENT_BOARD.InvitationPanel)
                setVisibility(CLIENT_BOARD.InvitationPanel, false);
            else if(component==CLIENT_BOARD.RightUpPanel){
                setVisibility(CLIENT_BOARD.SettingsBasePanel, trueFalse);
                CLIENT_BOARD.InviteButton.setVisible(false);
            }
            else if(component==CLIENT_BOARD.ChatBasePanel)
                setVisibility(CLIENT_BOARD.ChatBasePanel, false);
            else
                component.setVisible(trueFalse);
        }
    }
    
    public void makeButtonBackgroundTransparent(JButton button){
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
    }
    
    public void placeListElementAtCenter(JList list){
        DefaultListCellRenderer renderer = (DefaultListCellRenderer) list.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
    }
       
    public void rollDice(Client client) throws InterruptedException,IOException{
        
        Thread threadRoll = new Thread(new Runnable() {
            @Override
            public void run() {
                int rollingCount = 0;
                while (rollingCount < 10){
                    
                    int random = (int)(1+Math.random()*6);
                    rollingCount++;
                    
                    switch (random) {
                        case 1:moveTheDiceFront(CLIENT_BOARD.Dice1);
                               break;
                        case 2:moveTheDiceFront(CLIENT_BOARD.Dice2);
                               break;
                        case 3:moveTheDiceFront(CLIENT_BOARD.Dice3);
                               break;
                        case 4:moveTheDiceFront(CLIENT_BOARD.Dice4);
                               break;
                        case 5:moveTheDiceFront(CLIENT_BOARD.Dice5);
                               break;
                        case 6:moveTheDiceFront(CLIENT_BOARD.Dice6);
                               break;
                        default:break;
                    }

                    if(rollingCount==10){
                        client.SEND_MESSAGE_TO_SERVER("GameData@"+random+"", true);
                        CLIENT_BOARD.DiceRollButtton.setEnabled(true);
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
//        int rand = (int)(1+Math.random()*4);
//        makeTheUserActive(rand);
//        
//        //rough
//        setVisibility(CLIENT_BOARD.ChatBasePanel, true);
//        setVisibility(CLIENT_BOARD.ListBasePanel, false);
//        CLIENT_BOARD.AvailablePlayerListHeading.setText("Chat");
        
    } 
    
    private void moveTheDiceFront(JLabel dice){
        CLIENT_BOARD.Dice1.setVisible(false);
        CLIENT_BOARD.Dice2.setVisible(false);
        CLIENT_BOARD.Dice3.setVisible(false);
        CLIENT_BOARD.Dice4.setVisible(false);
        CLIENT_BOARD.Dice5.setVisible(false);
        CLIENT_BOARD.Dice6.setVisible(false);
        dice.setVisible(true);
        //Dice1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/razu/client/Images/dice/dicw+.png")));
    }
    
    public void makeTheUserActive(int userNumber){
        CLIENT_BOARD.ThisUserLabelActive.setVisible(false);
        CLIENT_BOARD.User2LabelActive.setVisible(false);
        CLIENT_BOARD.User3LabelActive.setVisible(false);
        CLIENT_BOARD.User4LabelActive.setVisible(false);
        
        switch (userNumber) {
            case 1:
                CLIENT_BOARD.ThisUserLabelActive.setVisible(true);
                break;
            case 2:
                CLIENT_BOARD.User2LabelActive.setVisible(true);
                break;
            case 3:
                CLIENT_BOARD.User3LabelActive.setVisible(true);
                break;
            case 4:
                CLIENT_BOARD.User4LabelActive.setVisible(true);
                break;
            default:
                break;
        }
    }
    
    public void setThisPlayer(String playerName, int playerNumber){
        switch (playerNumber) {
            case 2:
                ClientBoard.playersNameAndJLabel.put(playerName, CLIENT_BOARD.User2Label);
                ClientBoard.playersNameAndScoreJLabel.put(playerName, CLIENT_BOARD.User2ScoreLabel);
                CLIENT_BOARD.User2NameLabel.setText(playerName);
                CLIENT_BOARD.User2ScoreLabel.setText(0+"");
                showHidePlayer(playerNumber, true);
                break;
            case 3:
                ClientBoard.playersNameAndJLabel.put(playerName, CLIENT_BOARD.User3Label);
                ClientBoard.playersNameAndScoreJLabel.put(playerName, CLIENT_BOARD.User3ScoreLabel);
                CLIENT_BOARD.User3NameLabel.setText(playerName);
                CLIENT_BOARD.User3ScoreLabel.setText(0+"");
                showHidePlayer(playerNumber, true);
                break;
            case 4:
                ClientBoard.playersNameAndJLabel.put(playerName, CLIENT_BOARD.User4Label);
                ClientBoard.playersNameAndScoreJLabel.put(playerName, CLIENT_BOARD.User4ScoreLabel);
                CLIENT_BOARD.User4NameLabel.setText(playerName);
                CLIENT_BOARD.User4ScoreLabel.setText(0+"");
                showHidePlayer(playerNumber, true);
                break;
            default:
                break;
        }
    }
    
    public void hideAllPlayer(){
        CLIENT_BOARD.ThisUserLabelActive.setVisible(false);
        showHidePlayer(2, false);
        showHidePlayer(3, false);
        showHidePlayer(4, false);
    }
    
    public void showHidePlayer(int playerNumber, boolean trueFalse){
        switch (playerNumber) {
            case 2:
                CLIENT_BOARD.User2LabelActive.setVisible(false);
                CLIENT_BOARD.User2Label.setVisible(trueFalse);
                CLIENT_BOARD.User2NameLabel.setVisible(trueFalse);
                CLIENT_BOARD.User2ScoreLabel.setVisible(trueFalse);
                
                break;
            case 3:
                CLIENT_BOARD.User3LabelActive.setVisible(false);
                CLIENT_BOARD.User3Label.setVisible(trueFalse);
                CLIENT_BOARD.User3NameLabel.setVisible(trueFalse);
                CLIENT_BOARD.User3ScoreLabel.setVisible(trueFalse);
                break;
            case 4:
                CLIENT_BOARD.User4LabelActive.setVisible(false);
                CLIENT_BOARD.User4Label.setVisible(trueFalse);
                CLIENT_BOARD.User4NameLabel.setVisible(trueFalse);
                CLIENT_BOARD.User4ScoreLabel.setVisible(trueFalse);
                break;
            default:
                break;
        }
    }
    
    public List<String> controlInvitationButtonVisibility(){
        List<String> selectedPlayerList = CLIENT_BOARD.AvailablePlayerList.getSelectedValuesList();
        int selectedCount = selectedPlayerList.size();
        if(selectedPlayerList.isEmpty() || !CLIENT_BOARD.AvailablePlayerList.hasFocus() 
                            || CLIENT_BOARD.InvitationPanel.isVisible() || selectedCount>3){
            CLIENT_BOARD.InviteButton.setVisible(false);
        }
        else CLIENT_BOARD.InviteButton.setVisible(true);
        
        return selectedPlayerList;
    }
      
    public void setTheUserAvatar(JLabel userLabel, String avatarName){
        String avatarPath = "/com/razu/client/Images/avatar/"+avatarName+".png";
        ImageIcon avatarIcon = new ImageIcon(new ImageIcon(getClass().
                                                getResource(avatarPath))
                                            .getImage()
                                            .getScaledInstance(
                                                userLabel.getWidth(),
                                                userLabel.getHeight(), 
                                                Image.SCALE_SMOOTH)
                                            );
        userLabel.setIcon(avatarIcon);
    }
    
    public void showMessageDialog(Frame rootFrame, String message, String messageType) {
        JDialog frame = new JDialog(rootFrame, null, true);
        NotificationPane notificationPane = new NotificationPane(frame, message,
                                                messageType);
        frame.getContentPane().add(notificationPane);
        frame.setBounds(rootFrame.getX()+101, rootFrame.getY()+150, 
                        rootFrame.getWidth(), rootFrame.getHeight());
        frame.setUndecorated(true);
        frame.pack();
        frame.setVisible(true);
    }
    
}
//