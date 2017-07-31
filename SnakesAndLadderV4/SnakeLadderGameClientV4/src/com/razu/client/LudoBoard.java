
package com.razu.client;

import static com.razu.client.ClientBoard.LudoBoardPanel;
import java.awt.Image;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public final class LudoBoard {
    
    public static HashMap<String,String> LADDER = new HashMap<>();
    public static HashMap<String,String> SNAKE = new HashMap<>();
    public static HashMap<String,String> boardLocationByValue = new HashMap<>();
    public static HashMap<String,String> boardValueByLocation = new HashMap<>();
    
    public LudoBoard(){
        DesignLudoBoard();
        SnakesAndLadders();       
    }
    
    public void DesignLudoBoard(){
        int imgSize = 43;
        int x=10, y=10;
        boolean leftToRight = true;
        for(int i=100; i>0; i--){
            
            ImageIcon icon = new ImageIcon(new ImageIcon(getClass().getResource("/com/razu/client/Images2/"+i+".jpg"))
                                    .getImage().getScaledInstance(imgSize, imgSize, Image
                                            .SCALE_SMOOTH));
            JLabel singleBox=new JLabel();
            singleBox.setBounds(x, y, imgSize, imgSize);
            singleBox.setIcon(icon);
            singleBox.setVisible(true);
            LudoBoardPanel.add(singleBox);
            
            boardLocationByValue.put(i+"", x+"@"+y);
            boardValueByLocation.put(x+"@"+y, i+"");
            
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
    
    private static void SnakesAndLadders() {
        LADDER.put("1", "38");
        LADDER.put("4", "14");
        LADDER.put("9", "31");
        LADDER.put("21", "42");
        LADDER.put("28", "84");
        LADDER.put("51", "67");
        LADDER.put("71", "91");
        LADDER.put("80", "100");
        
        SNAKE.put("17", "7");
        SNAKE.put("54", "34");
        SNAKE.put("62", "19");
        SNAKE.put("64", "60");
        SNAKE.put("87", "24");
        SNAKE.put("93", "73");
        SNAKE.put("95", "75");
        SNAKE.put("98", "79");
    }
}
