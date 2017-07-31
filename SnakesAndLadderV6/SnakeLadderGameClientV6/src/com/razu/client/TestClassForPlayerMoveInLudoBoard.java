
package com.razu.client;

import java.util.Scanner;
import javax.swing.JLabel;


public class TestClassForPlayerMoveInLudoBoard {
    
   private void processUserMovement(int moveTo, ClientBoard board){
        JLabel playerLabel = ClientBoard.playersNumberAndMovableJLabel.get(1);
        playerLabel.setVisible(true);
        board.UPDATE_UI.setTheUserAvatar(playerLabel, "user1");
        board.LUDO_BOARD_UPDATER.movePlayer(playerLabel, moveTo, playerLabel);
    }
   
   public static void main(String[] arg){
       ClientBoard board = new ClientBoard();
       board.setVisible(true);
       TestClassForPlayerMoveInLudoBoard t = new TestClassForPlayerMoveInLudoBoard();
       for(int i=1; i<30; i++ ){
           Scanner sc = new Scanner(System.in);
           int m = sc.nextInt();
           t.processUserMovement(m, board);
       }
   }
}
