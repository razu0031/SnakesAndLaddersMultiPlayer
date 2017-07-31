
package com.razu.server;

import com.razu.game.Game;
import static com.razu.server.ServerConsole.println;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class Data {
    
    static final int SERVER_PORT = 8282;
    static InetAddress SERVER_ADDRESS ;
    
    static {
        try {          
            SERVER_ADDRESS = InetAddress.getLocalHost();
            
        }catch(Exception e){
        }
    }
    
    static ConcurrentHashMap<Integer, String> clientNameListByPort = new ConcurrentHashMap();
    static ConcurrentHashMap<String, Integer> clientPortListByName = new ConcurrentHashMap();
    static List<String> availableClientList = new ArrayList();
    
    //TeamName, ClientList
    static ConcurrentHashMap<String, List<String>> teamList = new ConcurrentHashMap();
    
    /*
    - A pending TeamName will be enterd when a invitation will come
    - by default the reply type of all client in the invitation list
        will be set as pending but the invitor as yes
    - the original reply from clients will be set as yes/no
    - when all clients reply will be reached then if more than one reply is yes
        then the team name and teamClient list will be saved in teamList and the 
        teamClientList will be sent to all the clients of teamClientList
    */
    //TeamName, <ClientName,Reply>
    static ConcurrentHashMap<String, ConcurrentHashMap<String, String>> pendingTeamList = new ConcurrentHashMap();
    
    
    /*
    - if a invitation came for a client then the clientName will be entered here
    **(reamining)- and the client will be out of available list untill the client reply
    - if the reply is yes then the client will only be available for his team
    - if the reply is no then the client will be available for all
    - if a client is in engagedClientList he must have exists in pendingTeamList
        or teamList but not in both at a time
    */
    //clientName, TeamName
    static ConcurrentHashMap<String, String> engagedClientAndTeamList = new ConcurrentHashMap();
    
    //clientName, GameObject
    static ConcurrentHashMap<String, Game> teamAndGameList = new ConcurrentHashMap();
   
    //currentState, jumpState
    public static HashMap<Integer,Integer> SNAKE_LADDER = new HashMap();
    
    static void initializeSnakeLadder(){
        try {          
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
            
        }catch(Exception ex){
            println("Exception in initializeSnakeLadder : "+ex);
        }
    }
    
}
