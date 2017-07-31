
package com.razu.client;

import java.awt.Image;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class LudoBoard {
    private final ClientBoard CLIENT_BOARD;
    public static HashMap<Integer,Integer> SNAKE_LADDER = new HashMap<>();
    public HashMap<Integer,String> boardLocationByValue = new HashMap<>();
    public HashMap<String,Integer> boardValueByLocation = new HashMap<>();
    
    public LudoBoard(ClientBoard clientBoard){
        this.CLIENT_BOARD = clientBoard;
        DesignLudoBoard();
        initializeSnakesAndLadders();       
    }
    
    private void DesignLudoBoard(){
        int imgSize = 43;
        int x=10, y=10;
        boolean leftToRight = true;
        
        for(int i=1; i<=4; i++){
            JLabel playerLabel=new JLabel();
            playerLabel.setBounds(0, 0, imgSize-10, imgSize-10);
            playerLabel.setVisible(true);
            CLIENT_BOARD.LudoBoardPanel.add(playerLabel);
            CLIENT_BOARD.playersNumberAndMovableJLabel.put(i, playerLabel);
        }
        
        for(int i=100; i>0; i--){
            
            ImageIcon icon = new ImageIcon(new ImageIcon(getClass().getResource("/com/razu/client/Images2/"+i+".jpg"))
                                    .getImage().getScaledInstance(imgSize, imgSize, Image
                                            .SCALE_SMOOTH));
            JLabel singleBox=new JLabel();
            singleBox.setBounds(x, y, imgSize, imgSize);
            singleBox.setIcon(icon);
            singleBox.setVisible(true);
            CLIENT_BOARD.LudoBoardPanel.add(singleBox);
            
            boardLocationByValue.put(i, x+"@"+y);
            boardValueByLocation.put(x+"@"+y, i);
            
            if(leftToRight)
            {
                if(x==(9*imgSize+10)){
                    y = y + imgSize;
                    leftToRight = false;
                }
                else x = x + imgSize;   
            }
            else{
                if(x==10){
                    y = y + imgSize;
                    leftToRight = true;
                }
                else x = x - imgSize;               
            }
        }
        
        
    }
    
    private void initializeSnakesAndLadders() {
        SNAKE_LADDER.put(3, 39);
        SNAKE_LADDER.put(13, 10);
        SNAKE_LADDER.put(16, 48);
        SNAKE_LADDER.put(35, 58);
        SNAKE_LADDER.put(38, 5);
        SNAKE_LADDER.put(50, 52);
        SNAKE_LADDER.put(61, 82);
        SNAKE_LADDER.put(63, 21);
        SNAKE_LADDER.put(71, 54);
        SNAKE_LADDER.put(73, 90);
        SNAKE_LADDER.put(94, 75);
        SNAKE_LADDER.put(98, 46);
    }
}
