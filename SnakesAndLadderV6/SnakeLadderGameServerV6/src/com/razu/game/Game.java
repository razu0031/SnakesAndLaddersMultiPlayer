
package com.razu.game;

import com.razu.server.Data;
import static com.razu.server.ServerConsole.println;
import java.util.HashMap;
import java.util.List;

public class Game {
    
    public int gameData1;
    public int gameData2;
    private int gutiValue;
    
    private List<String> playerList;
    private HashMap<String, String> playersMap;
    private HashMap<String, Integer> playersSocre;
    private HashMap<String, Boolean> playersSocreCountable;
    private final CircularList circularList;
    private int score;
    private String nextPlayer;
    
    public Game(List<String> playerList) {
        this.playerList = playerList;
        this.circularList = new CircularList(playerList);
        this.playersMap = this.circularList.getMap();
        this.playersSocre = new HashMap();
        this.playersSocreCountable = new HashMap();
        score = 0;
        nextPlayer = "";
        initializePlayersScore();
        initializePlayersForCountingScore();
        
    }
    
    private void initializePlayersScore(){
        for(String name : playerList){
            this.playersSocre.put(name, 0);
        }
    }
    
    private void initializePlayersForCountingScore(){
        for(String name : playerList){
            this.playersSocreCountable.put(name, Boolean.TRUE);
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
        println("pg0");
        if(!playersSocreCountable.get(name)){
            if(value == 1){
                println("pg1");
                playersSocreCountable.put(name, Boolean.TRUE); 
                println("pg2");
                score = 1;
            }
            else score = 0;
        }
        else if(playersSocreCountable.get(name)){
            println("pg3");
            int currentScore = playersSocre.get(name);
            int remainingToFinish = 100 - currentScore;
            
            if(value<=remainingToFinish){
                println("pg5");
                score = addAndGetScore(name, value);
                
                if(Data.SNAKE_LADDER.containsKey(score)){
                   int finalScore = Data.SNAKE_LADDER.get(score);
                   playersSocre.put(name, finalScore);
                }
                else playersSocre.put(name, score);
                println("pg6");
            }
            else score = currentScore;
            println("pg7");
        }
        
        println("pg8");
        if(value == 1)nextPlayer = name;
        else nextPlayer = nextPlayer(name);
        println("pg9");
        
        println("pg10");
        if(playersSocre.get(name) == 100){
            println("pg101="+playersSocre.get(name));
            println("pg102="+playerList);
            println("pg103="+playersSocre);
            println("pg104="+playersMap);
            println("pg105="+playersSocreCountable);
            removePlayer(name);
            println("pg106="+playerList);
            println("pg107="+playersSocre);
            println("pg108="+playersMap);
            println("pg109="+playersSocreCountable);
        }
        println("pg11");
        //score = addAndGetScore(name, value);
        //nextPlayer = nextPlayer(name);
    }
    
    public void removePlayer(String name){
        println("pg12");
        playersMap = circularList.remove(name);
        println("pg13"+playersMap);
        playerList.remove(name);
        println("pg14"+playerList);
        playersSocre.remove(name);
        println("pg15"+playersSocre);
        playersSocreCountable.remove(name);
        println("pg16"+playersSocreCountable);
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
