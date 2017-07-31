
package com.razu.server;

import com.razu.game.Game;
import java.util.ArrayList;
import java.util.List;

public class TestClass {
    
    public static void main(String[] arg){
        List<String> listHere = new ArrayList();
        for(int i=1; i<=4; i++){
            listHere.add("e"+i);
        }
        
        Game game = new Game(listHere);
        game.updateGame("e1", 5);
        System.out.println("p1: e1="+game.getScore()+" np="+game.getNextPlayer());
        
        game.updateGame("e2", 3);
        System.out.println("p2: e2="+game.getScore()+" np="+game.getNextPlayer());
        
        game.updateGame("e1", 4);
        System.out.println("p3: e1="+game.getScore()+" np="+game.getNextPlayer());
        
        game.updateGame("e2", 2);
        System.out.println("p4: e2="+game.getScore()+" np="+game.getNextPlayer());
    }
    
}
