
package com.razu.client;

import java.awt.Dialog;
import java.awt.Image;
import javax.swing.ImageIcon;

public class NotificationPane extends javax.swing.JPanel {

    public static String WARNING = "warning";
    public static String ERRORR = "error";
    public static String INFORMATION = "information";
    private final Dialog DIALOG_FRAME;
    private final String MESSAGE;
    private final String MESSAGE_TYPE;
    
    public NotificationPane(Dialog dialogFrame, String message, String messageType) {
        initComponents();
        this.DIALOG_FRAME = dialogFrame;
        this.MESSAGE = message;
        this.MESSAGE_TYPE = messageType;
        
        UpdateNotificationPanel();
    }

    private void UpdateNotificationPanel(){
        String iconPath = "/com/razu/client/Images/"+MESSAGE_TYPE+".png";
        ImageIcon icon = new ImageIcon(new ImageIcon(getClass().
                                                getResource(iconPath))
                                            .getImage()
                                            .getScaledInstance(66,66, 
                                                Image.SCALE_SMOOTH)
                                            );
        NotificationIcon.setIcon(icon);
        
        NotificationMessage.setText(MESSAGE);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        NotificationPanel = new jpanelcustom.JPanelCz();
        NotificationIcon = new javax.swing.JLabel();
        NotificationMessage = new org.jdesktop.swingx.JXLabel();
        OkButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(88, 203, 233));
        setAlignmentX(0.0F);
        setAlignmentY(0.0F);
        setOpaque(false);

        NotificationPanel.setBackground(new java.awt.Color(88, 203, 233));
        org.jdesktop.swingx.border.DropShadowBorder dropShadowBorder1 = new org.jdesktop.swingx.border.DropShadowBorder();
        dropShadowBorder1.setShadowColor(new java.awt.Color(88, 203, 233));
        dropShadowBorder1.setShadowSize(7);
        dropShadowBorder1.setShowLeftShadow(true);
        dropShadowBorder1.setShowTopShadow(true);
        NotificationPanel.setBorder(dropShadowBorder1);
        NotificationPanel.setAlignmentX(0.0F);
        NotificationPanel.setAlignmentY(0.0F);
        NotificationPanel.setName(""); // NOI18N
        NotificationPanel.setOpaque(true);
        NotificationPanel.setPreferredSize(new java.awt.Dimension(100, 100));

        NotificationIcon.setMaximumSize(new java.awt.Dimension(100, 100));
        NotificationIcon.setPreferredSize(new java.awt.Dimension(66, 66));

        NotificationMessage.setForeground(new java.awt.Color(122, 0, 0));
        NotificationMessage.setFont(new java.awt.Font("Lucida Calligraphy", 0, 13)); // NOI18N
        NotificationMessage.setLineWrap(true);
        NotificationMessage.setTextAlignment(org.jdesktop.swingx.JXLabel.TextAlignment.CENTER);

        OkButton.setFont(new java.awt.Font("Lucida Calligraphy", 1, 13)); // NOI18N
        OkButton.setForeground(new java.awt.Color(188, 0, 0));
        OkButton.setText("Ok");
        OkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OkButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout NotificationPanelLayout = new javax.swing.GroupLayout(NotificationPanel);
        NotificationPanel.setLayout(NotificationPanelLayout);
        NotificationPanelLayout.setHorizontalGroup(
            NotificationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NotificationPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(NotificationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(OkButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(NotificationPanelLayout.createSequentialGroup()
                        .addComponent(NotificationIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(NotificationMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        NotificationPanelLayout.setVerticalGroup(
            NotificationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NotificationPanelLayout.createSequentialGroup()
                .addGroup(NotificationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(NotificationPanelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(NotificationMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(NotificationPanelLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(NotificationIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(OkButton)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(NotificationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(NotificationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void OkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OkButtonActionPerformed
        DIALOG_FRAME.setVisible(false);
    }//GEN-LAST:event_OkButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel NotificationIcon;
    private org.jdesktop.swingx.JXLabel NotificationMessage;
    private jpanelcustom.JPanelCz NotificationPanel;
    private javax.swing.JButton OkButton;
    // End of variables declaration//GEN-END:variables
}
