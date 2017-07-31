
package com.razu.server;

import com.razu.game.Game;
import static com.razu.server.ServerConsole.println;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class ClientDataProcessor {
    private final ClientProcess CLIENT_PROCESS;
    private final Socket CLIENT_SOCKET;
    private final Server SERVER;
    private final Game GAME;
    
    
    
    public ClientDataProcessor(ClientProcess clientProcess, Socket socket){
        this.CLIENT_PROCESS = clientProcess;
        this.CLIENT_SOCKET = socket;
        this.SERVER = clientProcess.SERVER;
        this.GAME = clientProcess.game;       
    }
    
    public void processClientMessage(String messageFormClient) throws IOException{
        String[] messages = messageFormClient.split("@");
        String message = messages[1];
        
        switch (messages[0]) {
            case "UserName":
                processUserName(message);
                break;
                
            case "InvitationData":
                processInvitationData(message);
                break;
                
            case "InvitationReply":
                processInvitationReply(message);
                break;
                
            case "GameData":
                processGameData(message);
                break;
                
            case "Avatar":
                processAvatarData(message);
                break;
                
            default:
                break;
        }
    }
    
    private void processGameData(String data){
        //String senderName = Data.clientNameListByPort.get(CLIENT_SOCKET.getPort());
        //String teamName = Data.engagedClientAndTeamList.get(senderName);
       // try{
            String playerName = Data.clientNameListByPort.get(CLIENT_SOCKET.getPort());
            println("p10");
            String teamName = Data.engagedClientAndTeamList.get(playerName);
            println("p1");
            println("tpl1="+Data.teamList.get(teamName));
            Game game = Data.teamAndGameList.get(teamName);
            println("tpl2="+Data.teamList.get(teamName));
    //        
            int point = Integer.parseInt(data);
            println("gdp1");
            game.updateGame(playerName, point);
            println("gdp2");
            int score = game.getScore();
            println("gdp3="+score);
            String nextPlayer = game.getNextPlayer();
            println("gdp4="+nextPlayer);
            Data.teamAndGameList.put(teamName, game);
            println("tpl3="+Data.teamList.get(teamName));

            String message = "GameData@" + score + "@" + playerName + "@" + nextPlayer;
            println("tpl4="+Data.teamList.get(teamName));
             // CLIENT_PROCESS.WRITE_MESSAGE_TO_CLIENT(message, true);
    //        println("tpl="+Data.teamList.get(teamName));
    //        String message = "GameData@" + data + "@" + playerName + "@" + playerName;
            sendAllTeamMembers(message, teamName);
            println("tpl5="+Data.teamList.get(teamName));
        //}catch(Exception ex){
            //println("Exception in game data processing : "+ex);
       // }
    }
    
    private void processInvitationData(String data){
        String invitationFrom = Data.clientNameListByPort.get(CLIENT_SOCKET.getPort());
        
        if(!Data.engagedClientAndTeamList.containsKey(invitationFrom)){
            
            List<String> playersList = new ArrayList(Arrays.asList(data.split(",")));
            List<String> playersToRemove = new ArrayList();
            for(String name : playersList){
                if(Data.engagedClientAndTeamList.containsKey(name))
                    playersToRemove.add(name);
            }
            playersList.removeAll(playersToRemove);

            if(playersList.size()>=1){
                String teamName = SERVER.createTeamName();
                if(teamName.length()>0){
                    SERVER.sendInvitation(playersList, invitationFrom, teamName);
                }else{
                    String message = "InvitationReply@busy";
                    CLIENT_PROCESS.WRITE_MESSAGE_TO_CLIENT(message, true);
                }
            }else{
                String message = "InvitationReply@notAvailable";
                CLIENT_PROCESS.WRITE_MESSAGE_TO_CLIENT(message, true);
            }
            
        }else{
            String message = "InvitationReply@pending";
            CLIENT_PROCESS.WRITE_MESSAGE_TO_CLIENT(message, true);
        }
    }

    private void processUserName(String userName) throws IOException {
        String messageToClient = "";
        if(Data.clientNameListByPort.containsValue(userName)){
            messageToClient = "UserName@NotAvailable";
            CLIENT_PROCESS.WRITE_MESSAGE_TO_CLIENT(messageToClient, true);
        }
        else{
            messageToClient = "UserName@Available";
            CLIENT_PROCESS.WRITE_MESSAGE_TO_CLIENT(messageToClient, true);
            SERVER.ADD_CLIENT_NAME_TO_CLIENT_LIST(CLIENT_SOCKET.getPort(), userName);
        }   
    }

    private synchronized void processInvitationReply(String message) {
        System.out.println("send");
        println("enter");
        String replyFrom = Data.clientNameListByPort.get(CLIENT_SOCKET.getPort());
        println(replyFrom+"enter1");
        String teamName = Data.engagedClientAndTeamList.get(replyFrom);
        println(replyFrom+"enter2"+Data.engagedClientAndTeamList+" teamName ="+teamName);
        println(replyFrom+"enter2.1"+Data.pendingTeamList);
        ConcurrentHashMap<String, String> replyList = Data.pendingTeamList.get(teamName);
        println(replyFrom+"enter3:"+replyList);
        
        if(message.equals("yes")){
            println(replyFrom+"enter4:"+replyList);
            replyList.put(replyFrom, message);
            println(replyFrom+"enter5:"+replyList);
            Data.pendingTeamList.put(teamName, replyList);
            println(replyFrom+"enter6");
        }
        else if(message.equals("no")){
            println(replyFrom+"enter7"+replyList);
            replyList.remove(replyFrom);
            println(replyFrom+"enter8"+replyList);
            Data.pendingTeamList.put(teamName, replyList);
            println(replyFrom+"enter9"+Data.pendingTeamList);
            Data.engagedClientAndTeamList.remove(replyFrom);
            println(replyFrom+"enter10"+Data.engagedClientAndTeamList);
            
            Data.availableClientList.add(replyFrom);
            println(replyFrom+"enter11");
            Server.sendAllTheAvailablePlayerList();
            println(replyFrom+"enter12");
        }
        processTheTeam(teamName, replyFrom);
        println(replyFrom+"leaveInvReply");
    }

    private void processReplyForYes() {
        String replyFrom = Data.clientNameListByPort.get(CLIENT_SOCKET.getPort());
        String teamName = Data.engagedClientAndTeamList.get(replyFrom);
        ConcurrentHashMap<String, String> replyList = Data.pendingTeamList.get(teamName);
        replyList.put(replyFrom, "yes");
        //processThePendingTeam(teamName);
    }

    private void processReplyForNo() {
        String replyFrom = Data.clientNameListByPort.get(CLIENT_SOCKET.getPort());
        String teamName = Data.engagedClientAndTeamList.get(replyFrom);
        ConcurrentHashMap<String, String> replyList = Data.pendingTeamList.get(teamName);
        replyList.put(replyFrom, "no"); 
    }

    public static synchronized void processTheTeam(String teamName, String replyFrom) {
        if(Data.pendingTeamList.containsKey(teamName))
            processThePendingTeam(teamName, replyFrom);
        else if(Data.teamList.containsKey(teamName))
            processTheExistingTeam(teamName, replyFrom);
    }
    
    private static void processThePendingTeam(String teamName, String replyFrom) {
        println(replyFrom+"enter PT");
        ConcurrentHashMap<String, String> replyList = Data.pendingTeamList.get(teamName);
        println(replyFrom+"enter PT1");
        if(replyList.size() > 1 && !replyList.containsValue("pending")){
            println(replyFrom+"enter PT2");
            List<String> playerList = new ArrayList();
            println(replyFrom+"enter PT3");
            playerList.addAll(replyList.keySet());
            println(replyFrom+"enter PT4");
            Data.teamList.put(teamName, playerList);   
            println(replyFrom+"enter PT5");
            Data.teamAndGameList.put(teamName, new Game(playerList));
            println(replyFrom+"enter PT6");
            Data.pendingTeamList.remove(teamName);
            println(replyFrom+"enter PT7");
            
            String playersName = String.join(",", replyList.keySet());
            println(replyFrom+"enter PT8");
            String message = "InvitationReply@" + playersName + "@" + playerList.get(0);
            println(replyFrom+"enter PT9");
            sendAllTeamMembers(message, teamName);
            println(replyFrom+"leave enter PT10");                
        }
        else if(replyList.size() == 1){
            println(replyFrom+"enter PT11");
            String name = (String) replyList.keySet().toArray()[0];
            println(replyFrom+"enter PT12");
            int port = Data.clientPortListByName.get(name);
            //println(replyFrom+"enter PT13");
            ClientProcess clientProcess = Server.clientThreadList.get(port);
            //println(replyFrom+"enter PT14");
            Data.pendingTeamList.remove(teamName);
            //println(replyFrom+"enter PT15");
            Data.engagedClientAndTeamList.remove(name);
            //println(replyFrom+"enter PT916");
            Data.availableClientList.add(name);
            //println(replyFrom+"enter PT17");
            
            String message = "InvitationReply@none";
            //println(replyFrom+"enter PT18");
            clientProcess.WRITE_MESSAGE_TO_CLIENT(message, true);
            //println(replyFrom+"enter PT19");
            Server.sendAllTheAvailablePlayerList();
            //println(replyFrom+"leave enter PT20");
        }
        else if(replyList.isEmpty()) Data.pendingTeamList.remove(teamName);
    }

    private static void processTheExistingTeam(String teamName, String replyFrom) {
        if(Data.teamList.get(teamName).size()>1){
            println("p1");
            Game game = Data.teamAndGameList.get(teamName);
            println("p2");
            String nextPlayer = "";
            println("p3");
            if(game.getNextPlayer().equals(replyFrom)){
                nextPlayer = game.getNextNextPlayer();
            }else {
                nextPlayer = game.getNextPlayer();
            }

            game.removePlayer(replyFrom);
            Data.teamAndGameList.put(teamName, game);
            String message = "PlayerGone@" + replyFrom + "@" + nextPlayer;
            sendAllTeamMembers(message, teamName);
        }
        else if(Data.teamList.get(teamName).size()==1){
            println("p4");
            String name = Data.teamList.get(teamName).get(0);
            int port = Data.clientPortListByName.get(name);
            println("p5");
            ClientProcess clientProcess = Server.clientThreadList.get(port);           
            Data.availableClientList.add(name);
            println("p6");
            String message = "AllGone@none";
            clientProcess.WRITE_MESSAGE_TO_CLIENT(message, true);
            println("p7");
            Data.engagedClientAndTeamList.remove(name);
            Data.teamList.remove(teamName);
            Data.teamAndGameList.remove(teamName);
            println("p8");
            Server.sendAllTheAvailablePlayerList();
            println("p9");
        }
        else if(Data.teamList.get(teamName).isEmpty()){
            Data.teamList.remove(teamName);
            Data.teamAndGameList.remove(teamName);
        }
    }
    
    private static void sendAllTeamMembers(String message, String teamName){
        println("enter stm="+Data.teamList.get(teamName));
        for(String name : Data.teamList.get(teamName)){
            println("enter stm1");
            int port = Data.clientPortListByName.get(name);
            println("enter stm2");
            ClientProcess clientProcess = Server.clientThreadList.get(port);
            println("enter stm3");
            clientProcess.WRITE_MESSAGE_TO_CLIENT(message, true);
            println("leave enter stm4");
        }
    }

    private void processAvatarData(String avatarName) {
        String senderName = Data.clientNameListByPort.get(CLIENT_SOCKET.getPort());
        String teamName = Data.engagedClientAndTeamList.get(senderName);
        
        String message = "Avatar@" + avatarName + "@" + senderName;
        for(String name : Data.teamList.get(teamName)){
            int port = Data.clientPortListByName.get(name);
            ClientProcess clientProcess = Server.clientThreadList.get(port);
            clientProcess.WRITE_MESSAGE_TO_CLIENT(message, true);
        }
    }
    
}
