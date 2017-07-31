
package com.razu.server;

import java.awt.Color;
import java.io.IOException;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class ServerConsole extends javax.swing.JFrame {

    public ServerConsole() {
        initComponents();
    }
    
    public static synchronized void print(String message){
        ConsoleTexPane.setEditable(true);
        StyleContext styleContext = StyleContext.getDefaultStyleContext();
        AttributeSet attributeSet = styleContext.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.BLACK);

        int len = ConsoleTexPane.getDocument().getLength();
        ConsoleTexPane.setCaretPosition(len);
        ConsoleTexPane.setCharacterAttributes(attributeSet, false);
        ConsoleTexPane.replaceSelection(message);
        ConsoleTexPane.setEditable(false);
    }
    
    public static synchronized void println(String message){
        ConsoleTexPane.setEditable(true);
        StyleContext styleContext = StyleContext.getDefaultStyleContext();
        AttributeSet attributeSet = styleContext.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.BLACK);

        int len = ConsoleTexPane.getDocument().getLength();
        ConsoleTexPane.setCaretPosition(len);
        ConsoleTexPane.setCharacterAttributes(attributeSet, false);
        ConsoleTexPane.replaceSelection(message+"\n");
        ConsoleTexPane.setEditable(false);
    }
    
    public static synchronized void print(String message, Color color){
        ConsoleTexPane.setEditable(true);
        StyleContext styleContext = StyleContext.getDefaultStyleContext();
        AttributeSet attributeSet = styleContext.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color );

        int len = ConsoleTexPane.getDocument().getLength();
        ConsoleTexPane.setCaretPosition(len);
        ConsoleTexPane.setCharacterAttributes(attributeSet, false);
        ConsoleTexPane.replaceSelection(message);
        ConsoleTexPane.setEditable(false);
    }
    
    public static synchronized void println(String message, Color color){
        ConsoleTexPane.setEditable(true);
        StyleContext styleContext = StyleContext.getDefaultStyleContext();
        AttributeSet attributeSet = styleContext.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color );

        int len = ConsoleTexPane.getDocument().getLength();
        ConsoleTexPane.setCaretPosition(len);
        ConsoleTexPane.setCharacterAttributes(attributeSet, false);
        ConsoleTexPane.replaceSelection(message+"\n");
        ConsoleTexPane.setEditable(false);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BasePanel = new jpanelcustom.JPanelCz();
        jScrollPane1 = new javax.swing.JScrollPane();
        ConsoleTexPane = new javax.swing.JTextPane();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        org.jdesktop.swingx.border.DropShadowBorder dropShadowBorder1 = new org.jdesktop.swingx.border.DropShadowBorder();
        dropShadowBorder1.setShadowSize(9);
        dropShadowBorder1.setShowLeftShadow(true);
        dropShadowBorder1.setShowTopShadow(true);
        BasePanel.setBorder(dropShadowBorder1);
        BasePanel.setAlignmentX(0.0F);
        BasePanel.setAlignmentY(0.0F);
        BasePanel.setPreferredSize(new java.awt.Dimension(793, 511));

        jScrollPane1.setAlignmentX(0.0F);
        jScrollPane1.setAlignmentY(0.0F);
        jScrollPane1.setAutoscrolls(true);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(200, 200));

        ConsoleTexPane.setEditable(false);
        ConsoleTexPane.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
        jScrollPane1.setViewportView(ConsoleTexPane);

        javax.swing.GroupLayout BasePanelLayout = new javax.swing.GroupLayout(BasePanel);
        BasePanel.setLayout(BasePanelLayout);
        BasePanelLayout.setHorizontalGroup(
            BasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BasePanelLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE)
                .addGap(7, 7, 7))
        );
        BasePanelLayout.setVerticalGroup(
            BasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BasePanelLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
                .addGap(7, 7, 7))
        );

        jLabel1.setFont(new java.awt.Font("Courier New", 0, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 0));
        jLabel1.setText("TCP Server (Snakes And Ladders Game)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BasePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 775, Short.MAX_VALUE)
                        .addGap(21, 21, 21))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BasePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) throws IOException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ServerConsole.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServerConsole.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServerConsole.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServerConsole.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //Server server = new Server();
        //server.RUN_SERVER(server);
        /* Create and display the form */
        /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServerConsole().setVisible(true);            
            }
        });
        */
        new ServerConsole().setVisible(true);
        Server server = new Server();
        server.RUN_SERVER(server);
        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private jpanelcustom.JPanelCz BasePanel;
    private static javax.swing.JTextPane ConsoleTexPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
