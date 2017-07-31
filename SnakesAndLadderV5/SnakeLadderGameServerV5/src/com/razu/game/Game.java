
package com.razu.game;

import java.util.HashMap;
import java.util.List;

public class Game {
    
    public int gameData1;
    public int gameData2;
    private int gutiValue;
    
    private List<String> playerList;
    private HashMap<String, String> playersMap;
    private HashMap<String, Integer> playersSocre;
    private final CircularList circularList;
    private int score;
    private String nextPlayer;
    
    public Game(List<String> playerList) {
        this.playerList = playerList;
        this.circularList = new CircularList(playerList);
        this.playersMap = this.circularList.getMap();
        this.playersSocre = new HashMap();
        score = 0;
        nextPlayer = "";
        initializePlayersScore();
        
    }
    
    private void initializePlayersScore(){
        for(String name : playerList){
            this.playersSocre.put(name, 0);
        }
    }
       
    private int addAndGetScore(String name, int value){
        int scoreHere = playersSocre.get(name);
        playersSocre.put(name, scoreHere+value);
        return scoreHere+value;
    }
    
    private String nextPlayer(String name){
        //playerList.remove(name);
        return playersMap.get(name);
    }
    
    public void updateGame(String name, int value){
        //have to expand it
        score = addAndGetScore(name, value);
        nextPlayer = nextPlayer(name);
    }
    
    public void removePlayer(String name){
        playersMap = circularList.remove(name);
        playerList.remove(name);
    }
    
    public String getNextPlayer(){
        return nextPlayer;
    }
    
    public String getNextNextPlayer(){
        return playersMap.get(nextPlayer);
    }
    
    public int getScore(){
        return score;
    }
    
    
    //rough from here
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
