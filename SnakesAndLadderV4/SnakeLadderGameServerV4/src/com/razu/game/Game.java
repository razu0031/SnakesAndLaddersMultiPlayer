
package com.razu.game;

public class Game {
    
    public int gameData1;
    public int gameData2;
    private int gutiValue;

    public Game() {
        
    }

    private void initializeGame(){
        this.gameData1 = 0;
        this.gameData2 = 0;
        this.gutiValue = 0;
    }
    
    public String continueGame(String input){
        
        gameData1++;
        gameData2--;
        String output = "gd1=" + gameData1 + ", gd2=" + gameData2 + ", inp=" 
                        + input.toUpperCase();
        //new
        gutiValue += Integer.parseInt(input);
        return gutiValue+"";
    }
    
    public int getGameData1() {
        return gameData1;
    }

    public void setGameData1(int gameData1) {
        this.gameData1 = gameData1;
    }

    public int getGameData2() {
        return gameData2;
    }

    public void setGameData2(int gameData2) {
        this.gameData2 = gameData2;
    }
    
}
