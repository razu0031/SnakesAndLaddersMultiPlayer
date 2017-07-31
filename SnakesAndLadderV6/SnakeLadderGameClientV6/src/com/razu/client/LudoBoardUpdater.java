
package com.razu.client;

import javax.swing.JLabel;

public class LudoBoardUpdater {
    private final ClientBoard CLIENT_BOARD;
    private final LudoBoard LUDO_BOARD;
    private int finalMove = 0;
    
    public LudoBoardUpdater(ClientBoard clientBoard){
        this.CLIENT_BOARD = clientBoard;
        this.LUDO_BOARD = clientBoard.LUDO_BOARD;
    }
    
    private int getX(int value){
        String location = LUDO_BOARD.boardLocationByValue.get(value);
        String[] xy = location.split("@");
        return Integer.parseInt(xy[0]);
    }
    
    private int getY(int value){
        String location = LUDO_BOARD.boardLocationByValue.get(value);
        String[] xy = location.split("@");
        return Integer.parseInt(xy[1]);
    }
    
    private int getValue(int x, int y){
        String location = x+"@"+y;
        return LUDO_BOARD.boardValueByLocation.get(location);
    }
    
    public void setplayerLocation(JLabel playerJLabel, int value){
        int x = getX(value);
        int y = getY(value);
        playerJLabel.setLocation(x+5, y+5);
    }
 
    public void movePlayer(JLabel playerJLabel, int moveTo, JLabel playerScoreJLabel){
        
        if(LudoBoard.SNAKE_LADDER.containsKey(moveTo))
            finalMove = LudoBoard.SNAKE_LADDER.get(moveTo);
        else finalMove = moveTo;    

        CLIENT_BOARD.DiceRollButtton.setEnabled(false);
        Thread thread = new Thread(new Runnable() {
            
            boolean continueMove = true;
            @Override
            public void run() {
                int currentX = playerJLabel.getX()-5;
                int currentY = playerJLabel.getY()-5;          
                int currentValue = 0;
                //it means it is not the first movement if currentX < 0 then first movement
                if(currentX >= 0)
                    currentValue = getValue(currentX, currentY);
                
                for(int value=currentValue+1; value<=moveTo; value++){
                    setplayerLocation(playerJLabel, value);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ex) {
                        System.err.println(ex);
                    }
                } 
                setplayerLocation(playerJLabel, finalMove);
                
                if(finalMove == 100){
                    ClientBoard.playerWinPosition++;
                    switch (ClientBoard.playerWinPosition) {
                        case 1:
                            playerScoreJLabel.setText("1st");
                            break;
                        case 2:
                            playerScoreJLabel.setText("2nd");
                            break;
                        case 3:
                            playerScoreJLabel.setText("3rd");
                            break;
                        case 4:
                            playerScoreJLabel.setText("4th");
                            break;
                        default:
                            break;
                    }
                }
                
            }
        });
        thread.start();
        CLIENT_BOARD.DiceRollButtton.setEnabled(true);
    }
    
    
}
