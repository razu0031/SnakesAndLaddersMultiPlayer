
package com.razu.client;

import java.awt.Toolkit;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.jdesktop.swingx.prompt.PromptSupport;


public final class ClientBoard extends javax.swing.JFrame {
    
    Client client;
    Thread thread;
    
    public final LudoBoard LUDO_BOARD;
    public final UpdateUI UPDATE_UI;
    public final Action ACTION;
    public final LudoBoardUpdater LUDO_BOARD_UPDATER;
    private final ServerDataProcessor SERVER_DATA_PROCESSOR;
    
    public static HashMap<String, Integer> playersNameAndNumber = new HashMap();
    public static HashMap<String, JLabel> playersNameAndJLabel = new HashMap();
    public static HashMap<String, JLabel> playersNameAndScoreJLabel = new HashMap();
    public static HashMap<String, Integer> playersNameAndScore = new HashMap();
    public static HashMap<String, JLabel> playersNameAndMovableJLabel = new HashMap();
    public static HashMap<Integer, JLabel> playersNumberAndMovableJLabel = new HashMap();
    
    public static int playerWinPosition = 0;
    
    public static boolean joinedInATeam = false;
    public static String activeUser = "";
    
    boolean continueCommunication = false;
    boolean continueGame = false;
    boolean applicationRunning = true;
    boolean invitationHasCome = false;
    String invitationMessage = "";
    
    public String userNickName = "";
    public int userPort;
    //boolean diceRolling = false;
    
    JLabel guti = new JLabel();
    String serverAddress = "";
    String messageFromServer;
    List<String> selectedPlayerList;
    public boolean settingButtonClicked = false;

    public ClientBoard() {
        initComponents();
        
        this.LUDO_BOARD = new LudoBoard(this);
        this.UPDATE_UI = new UpdateUI(this);
        this.ACTION = new Action(this);
        this.LUDO_BOARD_UPDATER = new LudoBoardUpdater(this);
        this.SERVER_DATA_PROCESSOR = new ServerDataProcessor(this);
        initializeUserInterface();
        
        openNewConnection();
        
        try {
            UPDATE_UI.startPageAnimation(applicationRunning);
        } catch (IOException ex) {
            Logger.getLogger(ClientBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        System.out.println(RightUpPanel.getComponentCount());
        
        
    }

    
    
    private void initializeUserInterface(){
        UPDATE_UI.makeButtonBackgroundTransparent(PlayButton);
        UPDATE_UI.makeButtonBackgroundTransparent(SettingsButton);
        UPDATE_UI.makeButtonBackgroundTransparent(ExitButton);
        UPDATE_UI.makeButtonBackgroundTransparent(HomeButton);
        UPDATE_UI.makeButtonBackgroundTransparent(SettingExitButton);
        UPDATE_UI.makeButtonBackgroundTransparent(SettingsOkButton);
        UPDATE_UI.makeButtonBackgroundTransparent(AcceptRequestButton);
        UPDATE_UI.makeButtonBackgroundTransparent(DenyRequestButton);
        UPDATE_UI.makeButtonBackgroundTransparent(InviteButton);
        UPDATE_UI.placeListElementAtCenter(AvailablePlayerList);
        UPDATE_UI.setVisibility(StartPanel, true);
        UPDATE_UI.setVisibility(GamePanel, false);
        
        PromptSupport.setPrompt("Nick Name",UserNickNameTextField); 
        PromptSupport.setPrompt("Server Address",ServerAddressTextField);
        
    }
    
    public void openNewConnection(){
        try {
            client = new Client();
            //checking if the connection is establisded or not
            messageFromServer = client.READ_MESSAGE_FROM_SERVER();
            continueCommunication=client.CONTINUE_COMMUNICATION();
        
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Can not connect to the "
                                + "server.\nMay be the server is down",
                                "Server Connection Error",
                                JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
    
    public void startAndContinueGame() throws IOException{
        
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;        
                while (continueCommunication){
                    try {
                        messageFromServer = client.READ_MESSAGE_FROM_SERVER();
                        continueCommunication = client.CONTINUE_COMMUNICATION();
                        SERVER_DATA_PROCESSOR.UPDATE(messageFromServer);
                        
                        Thread.sleep(1);
                    } catch (IOException ex) {
                        System.out.println("scIO = "+ex);
                        continueCommunication = false;
                    } catch (InterruptedException ex) {
                        System.err.println("scIE="+ex);
                    }catch (ArrayIndexOutOfBoundsException ex){
                        AvailablePlayerList.removeAll();
                        System.out.println("scAIOBEX"+ex);   
                    }/*catch (Exception ex) {
                        System.err.println("scEX"+ex);
                    }*/
                }               
            }
        });
        thread.start();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BasePanel = new javax.swing.JPanel();
        GamePanel = new javax.swing.JPanel();
        InvitationPanel = new jpanelcustom.JPanelCz();
        AcceptRequestButton = new javax.swing.JButton();
        DenyRequestButton = new javax.swing.JButton();
        InvitationLabel = new org.jdesktop.swingx.JXLabel();
        LeftPanel = new jpanelcustom.JPanelC();
        LudoBoardPanel = new jpanelcustom.JPanelCz();
        PlayerBenchPanel = new jpanelcustom.JPanelCz();
        ThisUserScoreLabel = new org.jdesktop.swingx.JXLabel();
        ThisUserLabel = new javax.swing.JLabel();
        ThisUserLabelActive = new javax.swing.JLabel();
        ThisUserName = new javax.swing.JLabel();
        User2ScoreLabel = new javax.swing.JLabel();
        User2Label = new javax.swing.JLabel();
        User2LabelActive = new javax.swing.JLabel();
        User2NameLabel = new javax.swing.JLabel();
        User3ScoreLabel = new javax.swing.JLabel();
        User3Label = new javax.swing.JLabel();
        User3LabelActive = new javax.swing.JLabel();
        User3NameLabel = new javax.swing.JLabel();
        User4ScoreLabel = new javax.swing.JLabel();
        User4Label = new javax.swing.JLabel();
        User4LabelActive = new javax.swing.JLabel();
        User4NameLabel = new javax.swing.JLabel();
        RightUpPanel = new jpanelcustom.JPanelCz();
        AvailablePlayerListPanel = new jpanelcustom.JPanelC();
        ListBasePanel = new jpanelcustom.JPanelCz();
        jScrollPane1 = new javax.swing.JScrollPane();
        AvailablePlayerList = new javax.swing.JList<>();
        AvailablePlayerListHeadingBasePanel = new jpanelcustom.JPanelCz();
        AvailablePlayerListHeading = new javax.swing.JLabel();
        ChatBasePanel = new jpanelcustom.JPanelCz();
        jScrollPane3 = new javax.swing.JScrollPane();
        ChatBoxTextPane = new javax.swing.JTextPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        ChatWriteTextArea = new javax.swing.JTextArea();
        ChatSendButton = new javax.swing.JButton();
        InviteButton = new javax.swing.JButton();
        HomeButton = new javax.swing.JButton();
        DiceRollingPanel = new jpanelcustom.JPanelCz();
        Dice1 = new javax.swing.JLabel();
        Dice2 = new javax.swing.JLabel();
        Dice3 = new javax.swing.JLabel();
        Dice4 = new javax.swing.JLabel();
        Dice5 = new javax.swing.JLabel();
        Dice6 = new javax.swing.JLabel();
        DiceRollButtton = new javax.swing.JButton();
        GamePanelBackgroundLabel = new javax.swing.JLabel();
        StartPanel = new javax.swing.JPanel();
        PlayButton = new javax.swing.JButton();
        SettingsButton = new javax.swing.JButton();
        ExitButton = new javax.swing.JButton();
        SettingsBasePanel = new javax.swing.JPanel();
        SettingExitButton = new javax.swing.JButton();
        SettingsPanel = new jpanelcustom.JPanelCz();
        NickNameBasePanel = new jpanelcustom.JPanelC();
        UserNickNameTextField = new javax.swing.JTextField();
        SettingsOkButton = new javax.swing.JButton();
        ServerAddressBasePanel = new jpanelcustom.JPanelC();
        ServerAddressTextField = new javax.swing.JTextField();
        Divi = new javax.swing.JLabel();
        Snake2 = new javax.swing.JLabel();
        Ladder = new javax.swing.JLabel();
        Snake = new javax.swing.JLabel();
        StartPanelBackgroundLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/com/razu/client/Images/gIcon.png")));
        setResizable(false);

        BasePanel.setBackground(new java.awt.Color(255, 255, 153));
        BasePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        GamePanel.setAlignmentX(0.0F);
        GamePanel.setAlignmentY(0.0F);
        GamePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        InvitationPanel.setBackground(new java.awt.Color(204, 0, 0));
        org.jdesktop.swingx.border.DropShadowBorder dropShadowBorder1 = new org.jdesktop.swingx.border.DropShadowBorder();
        dropShadowBorder1.setCornerSize(20);
        dropShadowBorder1.setShadowColor(new java.awt.Color(204, 0, 0));
        dropShadowBorder1.setShadowSize(10);
        dropShadowBorder1.setShowLeftShadow(true);
        dropShadowBorder1.setShowTopShadow(true);
        InvitationPanel.setBorder(dropShadowBorder1);
        InvitationPanel.setPreferredSize(new java.awt.Dimension(100, 100));

        AcceptRequestButton.setFont(new java.awt.Font("Lucida Calligraphy", 1, 13)); // NOI18N
        AcceptRequestButton.setForeground(new java.awt.Color(0, 153, 77));
        AcceptRequestButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/razu/client/Images/raccept.png"))); // NOI18N
        AcceptRequestButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                AcceptRequestButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                AcceptRequestButtonMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                AcceptRequestButtonMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                AcceptRequestButtonMouseReleased(evt);
            }
        });
        AcceptRequestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AcceptRequestButtonActionPerformed(evt);
            }
        });

        DenyRequestButton.setFont(new java.awt.Font("Lucida Calligraphy", 1, 13)); // NOI18N
        DenyRequestButton.setForeground(new java.awt.Color(190, 23, 29));
        DenyRequestButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/razu/client/Images/rdeny.png"))); // NOI18N
        DenyRequestButton.setOpaque(false);
        DenyRequestButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                DenyRequestButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                DenyRequestButtonMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DenyRequestButtonMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                DenyRequestButtonMouseReleased(evt);
            }
        });
        DenyRequestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DenyRequestButtonActionPerformed(evt);
            }
        });

        InvitationLabel.setForeground(new java.awt.Color(255, 255, 255));
        InvitationLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        InvitationLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        InvitationLabel.setAlignmentY(0.0F);
        InvitationLabel.setAutoscrolls(true);
        InvitationLabel.setFont(new java.awt.Font("Lucida Calligraphy", 0, 18)); // NOI18N
        InvitationLabel.setIconTextGap(0);
        InvitationLabel.setLineWrap(true);
        InvitationLabel.setTextAlignment(org.jdesktop.swingx.JXLabel.TextAlignment.CENTER);

        javax.swing.GroupLayout InvitationPanelLayout = new javax.swing.GroupLayout(InvitationPanel);
        InvitationPanel.setLayout(InvitationPanelLayout);
        InvitationPanelLayout.setHorizontalGroup(
            InvitationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InvitationPanelLayout.createSequentialGroup()
                .addGroup(InvitationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(InvitationPanelLayout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(AcceptRequestButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(88, 88, 88)
                        .addComponent(DenyRequestButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(InvitationPanelLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(InvitationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        InvitationPanelLayout.setVerticalGroup(
            InvitationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, InvitationPanelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(InvitationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(InvitationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DenyRequestButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AcceptRequestButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35))
        );

        GamePanel.add(InvitationPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 130, 370, 250));

        LeftPanel.setBackground(new java.awt.Color(207, 240, 248));

        LudoBoardPanel.setBackground(new java.awt.Color(207, 240, 248));
        org.jdesktop.swingx.border.DropShadowBorder dropShadowBorder2 = new org.jdesktop.swingx.border.DropShadowBorder();
        dropShadowBorder2.setShadowSize(10);
        dropShadowBorder2.setShowLeftShadow(true);
        dropShadowBorder2.setShowTopShadow(true);
        LudoBoardPanel.setBorder(dropShadowBorder2);
        LudoBoardPanel.setPreferredSize(new java.awt.Dimension(450, 450));

        javax.swing.GroupLayout LudoBoardPanelLayout = new javax.swing.GroupLayout(LudoBoardPanel);
        LudoBoardPanel.setLayout(LudoBoardPanelLayout);
        LudoBoardPanelLayout.setHorizontalGroup(
            LudoBoardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );
        LudoBoardPanelLayout.setVerticalGroup(
            LudoBoardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );

        PlayerBenchPanel.setBackground(new java.awt.Color(1, 218, 127));
        org.jdesktop.swingx.border.DropShadowBorder dropShadowBorder3 = new org.jdesktop.swingx.border.DropShadowBorder();
        dropShadowBorder3.setShadowOpacity(0.3F);
        dropShadowBorder3.setShowLeftShadow(true);
        dropShadowBorder3.setShowTopShadow(true);
        PlayerBenchPanel.setBorder(dropShadowBorder3);
        PlayerBenchPanel.setPreferredSize(new java.awt.Dimension(100, 100));
        PlayerBenchPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ThisUserScoreLabel.setForeground(new java.awt.Color(255, 255, 255));
        ThisUserScoreLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ThisUserScoreLabel.setText("100");
        ThisUserScoreLabel.setFont(new java.awt.Font("Tahoma", 1, 19)); // NOI18N
        ThisUserScoreLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ThisUserScoreLabel.setIconTextGap(0);
        ThisUserScoreLabel.setLineWrap(true);
        ThisUserScoreLabel.setTextAlignment(org.jdesktop.swingx.JXLabel.TextAlignment.CENTER);
        ThisUserScoreLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ThisUserScoreLabelMouseClicked(evt);
            }
        });
        PlayerBenchPanel.add(ThisUserScoreLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 15, 54, 54));

        ThisUserLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/razu/client/Images/user1.png"))); // NOI18N
        PlayerBenchPanel.add(ThisUserLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 15, 54, 54));

        ThisUserLabelActive.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/razu/client/Images/userActive.png"))); // NOI18N
        PlayerBenchPanel.add(ThisUserLabelActive, new org.netbeans.lib.awtextra.AbsoluteConstraints(47, 12, 60, 60));

        ThisUserName.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        ThisUserName.setForeground(new java.awt.Color(183, 0, 0));
        ThisUserName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ThisUserName.setText("name");
        PlayerBenchPanel.add(ThisUserName, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 71, 54, -1));

        User2ScoreLabel.setFont(new java.awt.Font("Tahoma", 1, 19)); // NOI18N
        User2ScoreLabel.setForeground(new java.awt.Color(255, 255, 255));
        User2ScoreLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        User2ScoreLabel.setText("100");
        PlayerBenchPanel.add(User2ScoreLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(148, 15, 54, 54));

        User2Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/razu/client/Images/user2.png"))); // NOI18N
        PlayerBenchPanel.add(User2Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(148, 15, 54, 54));

        User2LabelActive.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/razu/client/Images/userActive.png"))); // NOI18N
        PlayerBenchPanel.add(User2LabelActive, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 12, 60, 60));

        User2NameLabel.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        User2NameLabel.setForeground(new java.awt.Color(255, 255, 255));
        User2NameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        User2NameLabel.setText("name");
        PlayerBenchPanel.add(User2NameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(148, 71, 54, -1));

        User3ScoreLabel.setFont(new java.awt.Font("Tahoma", 1, 19)); // NOI18N
        User3ScoreLabel.setForeground(new java.awt.Color(255, 255, 255));
        User3ScoreLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        User3ScoreLabel.setText("100");
        PlayerBenchPanel.add(User3ScoreLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(246, 15, 54, 54));

        User3Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/razu/client/Images/user3.png"))); // NOI18N
        PlayerBenchPanel.add(User3Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(246, 15, 54, 54));

        User3LabelActive.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/razu/client/Images/userActive.png"))); // NOI18N
        PlayerBenchPanel.add(User3LabelActive, new org.netbeans.lib.awtextra.AbsoluteConstraints(243, 12, 60, 60));

        User3NameLabel.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        User3NameLabel.setForeground(new java.awt.Color(255, 255, 255));
        User3NameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        User3NameLabel.setText("name");
        PlayerBenchPanel.add(User3NameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(246, 71, 54, -1));

        User4ScoreLabel.setFont(new java.awt.Font("Tahoma", 1, 19)); // NOI18N
        User4ScoreLabel.setForeground(new java.awt.Color(255, 255, 255));
        User4ScoreLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        User4ScoreLabel.setText("100");
        PlayerBenchPanel.add(User4ScoreLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(344, 15, 54, 54));

        User4Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/razu/client/Images/user4.png"))); // NOI18N
        PlayerBenchPanel.add(User4Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(344, 15, 54, 54));

        User4LabelActive.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/razu/client/Images/userActive.png"))); // NOI18N
        PlayerBenchPanel.add(User4LabelActive, new org.netbeans.lib.awtextra.AbsoluteConstraints(341, 12, 60, 60));

        User4NameLabel.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        User4NameLabel.setForeground(new java.awt.Color(255, 255, 255));
        User4NameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        User4NameLabel.setText("name");
        PlayerBenchPanel.add(User4NameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(344, 71, 54, -1));

        javax.swing.GroupLayout LeftPanelLayout = new javax.swing.GroupLayout(LeftPanel);
        LeftPanel.setLayout(LeftPanelLayout);
        LeftPanelLayout.setHorizontalGroup(
            LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LeftPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(LudoBoardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                    .addComponent(PlayerBenchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        LeftPanelLayout.setVerticalGroup(
            LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LeftPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(LudoBoardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PlayerBenchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        LudoBoardPanel.getAccessibleContext().setAccessibleName("");

        GamePanel.add(LeftPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 490, 600));

        RightUpPanel.setBackground(new java.awt.Color(207, 240, 248));
        org.jdesktop.swingx.border.DropShadowBorder dropShadowBorder4 = new org.jdesktop.swingx.border.DropShadowBorder();
        dropShadowBorder4.setShowLeftShadow(true);
        dropShadowBorder4.setShowTopShadow(true);
        RightUpPanel.setBorder(dropShadowBorder4);
        RightUpPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        AvailablePlayerListPanel.setBackground(new java.awt.Color(1, 218, 127));
        AvailablePlayerListPanel.setAlignmentX(0.0F);
        AvailablePlayerListPanel.setAlignmentY(0.0F);
        AvailablePlayerListPanel.setMinimumSize(new java.awt.Dimension(100, 100));
        AvailablePlayerListPanel.setPreferredSize(new java.awt.Dimension(100, 100));
        AvailablePlayerListPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        org.jdesktop.swingx.border.DropShadowBorder dropShadowBorder5 = new org.jdesktop.swingx.border.DropShadowBorder();
        dropShadowBorder5.setShadowOpacity(0.2F);
        dropShadowBorder5.setShowLeftShadow(true);
        dropShadowBorder5.setShowTopShadow(true);
        ListBasePanel.setBorder(dropShadowBorder5);
        ListBasePanel.setMinimumSize(new java.awt.Dimension(100, 100));
        ListBasePanel.setPreferredSize(new java.awt.Dimension(100, 100));

        jScrollPane1.setAutoscrolls(true);

        AvailablePlayerList.setBackground(new java.awt.Color(240, 240, 240));
        AvailablePlayerList.setFont(new java.awt.Font("Lucida Calligraphy", 0, 13)); // NOI18N
        AvailablePlayerList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7", "Item 8", "Item 9", "Item 10", "Item 11", "Item 12", "Item 13", "Item 14", "Item 15", "Item 16", "Item 17", "Item 19", "Item 20", "Item 21" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        AvailablePlayerList.setAlignmentX(0.0F);
        AvailablePlayerList.setAlignmentY(0.0F);
        AvailablePlayerList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AvailablePlayerList.setName("Name"); // NOI18N
        AvailablePlayerList.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                AvailablePlayerListMouseMoved(evt);
            }
        });
        AvailablePlayerList.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                AvailablePlayerListFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                AvailablePlayerListFocusLost(evt);
            }
        });
        AvailablePlayerList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                AvailablePlayerListMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(AvailablePlayerList);

        javax.swing.GroupLayout ListBasePanelLayout = new javax.swing.GroupLayout(ListBasePanel);
        ListBasePanel.setLayout(ListBasePanelLayout);
        ListBasePanelLayout.setHorizontalGroup(
            ListBasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ListBasePanelLayout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jScrollPane1)
                .addGap(1, 1, 1))
        );
        ListBasePanelLayout.setVerticalGroup(
            ListBasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ListBasePanelLayout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                .addGap(1, 1, 1))
        );

        AvailablePlayerListPanel.add(ListBasePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 55, 205, 314));

        org.jdesktop.swingx.border.DropShadowBorder dropShadowBorder6 = new org.jdesktop.swingx.border.DropShadowBorder();
        dropShadowBorder6.setShadowOpacity(0.2F);
        dropShadowBorder6.setShowLeftShadow(true);
        dropShadowBorder6.setShowTopShadow(true);
        AvailablePlayerListHeadingBasePanel.setBorder(dropShadowBorder6);
        AvailablePlayerListHeadingBasePanel.setMinimumSize(new java.awt.Dimension(100, 100));
        AvailablePlayerListHeadingBasePanel.setPreferredSize(new java.awt.Dimension(100, 100));

        AvailablePlayerListHeading.setFont(new java.awt.Font("Curlz MT", 1, 19)); // NOI18N
        AvailablePlayerListHeading.setForeground(new java.awt.Color(0, 187, 127));
        AvailablePlayerListHeading.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AvailablePlayerListHeading.setText("Available Players");
        AvailablePlayerListHeading.setAlignmentY(0.0F);
        AvailablePlayerListHeading.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        AvailablePlayerListHeading.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout AvailablePlayerListHeadingBasePanelLayout = new javax.swing.GroupLayout(AvailablePlayerListHeadingBasePanel);
        AvailablePlayerListHeadingBasePanel.setLayout(AvailablePlayerListHeadingBasePanelLayout);
        AvailablePlayerListHeadingBasePanelLayout.setHorizontalGroup(
            AvailablePlayerListHeadingBasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(AvailablePlayerListHeading, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
        );
        AvailablePlayerListHeadingBasePanelLayout.setVerticalGroup(
            AvailablePlayerListHeadingBasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AvailablePlayerListHeadingBasePanelLayout.createSequentialGroup()
                .addComponent(AvailablePlayerListHeading, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        AvailablePlayerListPanel.add(AvailablePlayerListHeadingBasePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 14, 205, 35));

        org.jdesktop.swingx.border.DropShadowBorder dropShadowBorder7 = new org.jdesktop.swingx.border.DropShadowBorder();
        dropShadowBorder7.setShadowOpacity(0.2F);
        dropShadowBorder7.setShowLeftShadow(true);
        dropShadowBorder7.setShowTopShadow(true);
        ChatBasePanel.setBorder(dropShadowBorder7);
        ChatBasePanel.setMinimumSize(new java.awt.Dimension(100, 100));
        ChatBasePanel.setPreferredSize(new java.awt.Dimension(100, 100));
        ChatBasePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane3.setViewportView(ChatBoxTextPane);

        ChatBasePanel.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 5, 195, 180));

        ChatWriteTextArea.setColumns(20);
        ChatWriteTextArea.setLineWrap(true);
        ChatWriteTextArea.setRows(5);
        ChatWriteTextArea.setMinimumSize(new java.awt.Dimension(50, 12));
        ChatWriteTextArea.setPreferredSize(new java.awt.Dimension(50, 14));
        jScrollPane4.setViewportView(ChatWriteTextArea);

        ChatBasePanel.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 190, 195, 60));

        ChatSendButton.setFont(new java.awt.Font("Lucida Calligraphy", 1, 15)); // NOI18N
        ChatSendButton.setForeground(new java.awt.Color(190, 23, 29));
        ChatSendButton.setText("Send");
        ChatSendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChatSendButtonActionPerformed(evt);
            }
        });
        ChatBasePanel.add(ChatSendButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 262, 155, -1));

        AvailablePlayerListPanel.add(ChatBasePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 55, 205, 314));

        RightUpPanel.add(AvailablePlayerListPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 77, 231, 379));

        InviteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/razu/client/Images/invite3.png"))); // NOI18N
        InviteButton.setAlignmentY(0.0F);
        InviteButton.setIconTextGap(0);
        InviteButton.setOpaque(false);
        InviteButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                InviteButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                InviteButtonMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                InviteButtonMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                InviteButtonMouseReleased(evt);
            }
        });
        InviteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InviteButtonActionPerformed(evt);
            }
        });
        RightUpPanel.add(InviteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 22, 42, 42));

        HomeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/razu/client/Images/Home-icon2.png"))); // NOI18N
        HomeButton.setOpaque(false);
        HomeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                HomeButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                HomeButtonMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                HomeButtonMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                HomeButtonMouseReleased(evt);
            }
        });
        HomeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HomeButtonActionPerformed(evt);
            }
        });
        RightUpPanel.add(HomeButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 15, 63, 56));

        GamePanel.add(RightUpPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 23, 255, 470));

        DiceRollingPanel.setBackground(new java.awt.Color(1, 218, 127));
        org.jdesktop.swingx.border.DropShadowBorder dropShadowBorder8 = new org.jdesktop.swingx.border.DropShadowBorder();
        dropShadowBorder8.setShowLeftShadow(true);
        dropShadowBorder8.setShowTopShadow(true);
        DiceRollingPanel.setBorder(dropShadowBorder8);
        DiceRollingPanel.setPreferredSize(new java.awt.Dimension(100, 100));
        DiceRollingPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Dice1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/razu/client/Images/dice/dicw1.png"))); // NOI18N
        DiceRollingPanel.add(Dice1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        Dice2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/razu/client/Images/dice/dicw2.png"))); // NOI18N
        DiceRollingPanel.add(Dice2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        Dice3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/razu/client/Images/dice/dicw3.png"))); // NOI18N
        DiceRollingPanel.add(Dice3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        Dice4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/razu/client/Images/dice/dicw4.png"))); // NOI18N
        DiceRollingPanel.add(Dice4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        Dice5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/razu/client/Images/dice/dicw5.png"))); // NOI18N
        DiceRollingPanel.add(Dice5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        Dice6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/razu/client/Images/dice/dicw6.png"))); // NOI18N
        DiceRollingPanel.add(Dice6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        DiceRollButtton.setFont(new java.awt.Font("Lucida Calligraphy", 1, 15)); // NOI18N
        DiceRollButtton.setForeground(new java.awt.Color(190, 23, 29));
        DiceRollButtton.setAlignmentY(0.0F);
        DiceRollButtton.setLabel("Roll");
        DiceRollButtton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DiceRollButttonActionPerformed(evt);
            }
        });
        DiceRollingPanel.add(DiceRollButtton, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, 80, 40));

        GamePanel.add(DiceRollingPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 500, 255, 120));

        GamePanelBackgroundLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/razu/client/Images/startPageBg2_1.png"))); // NOI18N
        GamePanelBackgroundLabel.setText("Go to another panel");
        GamePanelBackgroundLabel.setAlignmentY(0.0F);
        GamePanelBackgroundLabel.setMaximumSize(new java.awt.Dimension(800, 642));
        GamePanelBackgroundLabel.setMinimumSize(new java.awt.Dimension(800, 641));
        GamePanelBackgroundLabel.setPreferredSize(new java.awt.Dimension(800, 642));
        GamePanel.add(GamePanelBackgroundLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 641));

        BasePanel.add(GamePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 641));

        StartPanel.setBackground(new java.awt.Color(0, 153, 153));
        StartPanel.setPreferredSize(new java.awt.Dimension(800, 641));
        StartPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PlayButton.setBackground(new java.awt.Color(255, 255, 255));
        PlayButton.setFont(new java.awt.Font("Ravie", 0, 19)); // NOI18N
        PlayButton.setForeground(new java.awt.Color(0, 153, 51));
        PlayButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/razu/client/Images/playGame.png"))); // NOI18N
        PlayButton.setBorder(null);
        PlayButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PlayButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PlayButtonMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                PlayButtonMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                PlayButtonMouseReleased(evt);
            }
        });
        PlayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PlayButtonActionPerformed(evt);
            }
        });
        StartPanel.add(PlayButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 120, 150, 40));

        SettingsButton.setBackground(new java.awt.Color(255, 255, 255));
        SettingsButton.setFont(new java.awt.Font("Ravie", 0, 19)); // NOI18N
        SettingsButton.setForeground(new java.awt.Color(0, 153, 51));
        SettingsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/razu/client/Images/settings.png"))); // NOI18N
        SettingsButton.setBorder(null);
        SettingsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                SettingsButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                SettingsButtonMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                SettingsButtonMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                SettingsButtonMouseReleased(evt);
            }
        });
        SettingsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SettingsButtonActionPerformed(evt);
            }
        });
        StartPanel.add(SettingsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 200, 150, 40));

        ExitButton.setBackground(new java.awt.Color(255, 255, 255));
        ExitButton.setFont(new java.awt.Font("Ravie", 0, 19)); // NOI18N
        ExitButton.setForeground(new java.awt.Color(0, 153, 51));
        ExitButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/razu/client/Images/exit.png"))); // NOI18N
        ExitButton.setBorder(null);
        ExitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ExitButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ExitButtonMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ExitButtonMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ExitButtonMouseReleased(evt);
            }
        });
        ExitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitButtonActionPerformed(evt);
            }
        });
        StartPanel.add(ExitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 280, 150, 40));

        SettingsBasePanel.setOpaque(false);
        SettingsBasePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SettingExitButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/razu/client/Images/cross.png"))); // NOI18N
        SettingExitButton.setOpaque(false);
        SettingExitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                SettingExitButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                SettingExitButtonMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                SettingExitButtonMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                SettingExitButtonMouseReleased(evt);
            }
        });
        SettingExitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SettingExitButtonActionPerformed(evt);
            }
        });
        SettingsBasePanel.add(SettingExitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(312, 9, 42, 42));

        SettingsPanel.setBackground(new java.awt.Color(88, 203, 233));
        org.jdesktop.swingx.border.DropShadowBorder dropShadowBorder9 = new org.jdesktop.swingx.border.DropShadowBorder();
        dropShadowBorder9.setCornerSize(30);
        dropShadowBorder9.setShadowColor(new java.awt.Color(88, 203, 233));
        dropShadowBorder9.setShadowSize(10);
        dropShadowBorder9.setShowLeftShadow(true);
        dropShadowBorder9.setShowTopShadow(true);
        SettingsPanel.setBorder(dropShadowBorder9);

        NickNameBasePanel.setPreferredSize(new java.awt.Dimension(100, 100));

        UserNickNameTextField.setFont(new java.awt.Font("Lucida Calligraphy", 0, 15)); // NOI18N
        UserNickNameTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        UserNickNameTextField.setToolTipText("");
        org.jdesktop.swingx.border.DropShadowBorder dropShadowBorder10 = new org.jdesktop.swingx.border.DropShadowBorder();
        dropShadowBorder10.setShadowSize(2);
        dropShadowBorder10.setShowLeftShadow(true);
        dropShadowBorder10.setShowTopShadow(true);
        UserNickNameTextField.setBorder(dropShadowBorder10);
        UserNickNameTextField.setMargin(new java.awt.Insets(2, 10, 2, 10));

        javax.swing.GroupLayout NickNameBasePanelLayout = new javax.swing.GroupLayout(NickNameBasePanel);
        NickNameBasePanel.setLayout(NickNameBasePanelLayout);
        NickNameBasePanelLayout.setHorizontalGroup(
            NickNameBasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(UserNickNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        NickNameBasePanelLayout.setVerticalGroup(
            NickNameBasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NickNameBasePanelLayout.createSequentialGroup()
                .addComponent(UserNickNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );

        SettingsOkButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/razu/client/Images/ok2.png"))); // NOI18N
        SettingsOkButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                SettingsOkButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                SettingsOkButtonMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                SettingsOkButtonMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                SettingsOkButtonMouseReleased(evt);
            }
        });
        SettingsOkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SettingsOkButtonActionPerformed(evt);
            }
        });

        ServerAddressBasePanel.setPreferredSize(new java.awt.Dimension(100, 100));

        ServerAddressTextField.setFont(new java.awt.Font("Lucida Calligraphy", 0, 15)); // NOI18N
        ServerAddressTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ServerAddressTextField.setToolTipText("");
        org.jdesktop.swingx.border.DropShadowBorder dropShadowBorder11 = new org.jdesktop.swingx.border.DropShadowBorder();
        dropShadowBorder11.setShadowSize(2);
        dropShadowBorder11.setShowLeftShadow(true);
        dropShadowBorder11.setShowTopShadow(true);
        ServerAddressTextField.setBorder(dropShadowBorder11);
        ServerAddressTextField.setMargin(new java.awt.Insets(2, 10, 2, 10));

        javax.swing.GroupLayout ServerAddressBasePanelLayout = new javax.swing.GroupLayout(ServerAddressBasePanel);
        ServerAddressBasePanel.setLayout(ServerAddressBasePanelLayout);
        ServerAddressBasePanelLayout.setHorizontalGroup(
            ServerAddressBasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ServerAddressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        ServerAddressBasePanelLayout.setVerticalGroup(
            ServerAddressBasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ServerAddressBasePanelLayout.createSequentialGroup()
                .addComponent(ServerAddressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout SettingsPanelLayout = new javax.swing.GroupLayout(SettingsPanel);
        SettingsPanel.setLayout(SettingsPanelLayout);
        SettingsPanelLayout.setHorizontalGroup(
            SettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SettingsPanelLayout.createSequentialGroup()
                .addGroup(SettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SettingsPanelLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(SettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ServerAddressBasePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(NickNameBasePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(SettingsPanelLayout.createSequentialGroup()
                        .addGap(142, 142, 142)
                        .addComponent(SettingsOkButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        SettingsPanelLayout.setVerticalGroup(
            SettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SettingsPanelLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(NickNameBasePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ServerAddressBasePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(SettingsOkButton, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        SettingsBasePanel.add(SettingsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 340, 220));

        StartPanel.add(SettingsBasePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 140, 360, 250));

        Divi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/razu/client/Images/divi_trans.png"))); // NOI18N
        Divi.setAlignmentY(0.0F);
        StartPanel.add(Divi, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 366, 530, 275));

        Snake2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/razu/client/Images/snake2.png"))); // NOI18N
        StartPanel.add(Snake2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 590, 140, -1));

        Ladder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/razu/client/Images/ladder_trans5.png"))); // NOI18N
        StartPanel.add(Ladder, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 480, 50, -1));

        Snake.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/razu/client/Images/snake.png"))); // NOI18N
        StartPanel.add(Snake, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 570, 90, -1));

        StartPanelBackgroundLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/razu/client/Images/startPageBg2.png"))); // NOI18N
        StartPanelBackgroundLabel.setText("Go to another panel");
        StartPanelBackgroundLabel.setAlignmentY(0.0F);
        StartPanelBackgroundLabel.setMaximumSize(new java.awt.Dimension(800, 642));
        StartPanelBackgroundLabel.setMinimumSize(new java.awt.Dimension(800, 641));
        StartPanelBackgroundLabel.setPreferredSize(new java.awt.Dimension(800, 642));
        StartPanel.add(StartPanelBackgroundLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 641));

        BasePanel.add(StartPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BasePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BasePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
 
    private void PlayButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PlayButtonActionPerformed
        if(!SettingsBasePanel.isVisible())
            ACTION.PlayButtonAction();
    }//GEN-LAST:event_PlayButtonActionPerformed
    
    private void HomeButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HomeButtonMouseEntered
        if(!InvitationPanel.isVisible())
            HomeButton.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/com/razu/client/Images/Home-icon3.png")));      
    }//GEN-LAST:event_HomeButtonMouseEntered

    private void HomeButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HomeButtonMouseExited
        if(!InvitationPanel.isVisible())
            HomeButton.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/com/razu/client/Images/Home-icon2.png"))); 
    }//GEN-LAST:event_HomeButtonMouseExited

    private void HomeButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HomeButtonMousePressed
        if(!InvitationPanel.isVisible())
            HomeButton.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/com/razu/client/Images/Home-icon2.png"))); 
    }//GEN-LAST:event_HomeButtonMousePressed

    private void HomeButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HomeButtonMouseReleased
        if(!InvitationPanel.isVisible())
            HomeButton.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/com/razu/client/Images/Home-icon3.png"))); 
    }//GEN-LAST:event_HomeButtonMouseReleased

    private void HomeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HomeButtonActionPerformed
        if(!InvitationPanel.isVisible())
            ACTION.HomeButtonAction();
    }//GEN-LAST:event_HomeButtonActionPerformed

    private void PlayButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PlayButtonMouseEntered
        if(!SettingsBasePanel.isVisible())
            PlayButton.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/com/razu/client/Images/playGame2.png")));      
    }//GEN-LAST:event_PlayButtonMouseEntered

    private void PlayButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PlayButtonMouseExited
        if(!SettingsBasePanel.isVisible())
            PlayButton.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/com/razu/client/Images/playGame.png")));      
    }//GEN-LAST:event_PlayButtonMouseExited

    private void PlayButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PlayButtonMousePressed
        if(!SettingsBasePanel.isVisible())
            PlayButton.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/com/razu/client/Images/playGame.png")));      
    }//GEN-LAST:event_PlayButtonMousePressed

    private void PlayButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PlayButtonMouseReleased
        if(!SettingsBasePanel.isVisible())
            PlayButton.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/com/razu/client/Images/playGame2.png")));      
    }//GEN-LAST:event_PlayButtonMouseReleased

    private void ExitButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExitButtonMouseEntered
        if(!SettingsBasePanel.isVisible())
            ExitButton.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/com/razu/client/Images/exit2.png")));      
    }//GEN-LAST:event_ExitButtonMouseEntered

    private void ExitButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExitButtonMouseExited
        if(!SettingsBasePanel.isVisible())
            ExitButton.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/com/razu/client/Images/exit.png")));      
    }//GEN-LAST:event_ExitButtonMouseExited

    private void ExitButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExitButtonMousePressed
        if(!SettingsBasePanel.isVisible())
            ExitButton.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/com/razu/client/Images/exit.png")));      
    }//GEN-LAST:event_ExitButtonMousePressed

    private void ExitButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExitButtonMouseReleased
        if(!SettingsBasePanel.isVisible())
            ExitButton.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/com/razu/client/Images/exit2.png")));      
    }//GEN-LAST:event_ExitButtonMouseReleased

    private void ExitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitButtonActionPerformed
        if(!SettingsBasePanel.isVisible()){
            applicationRunning = false;
            System.exit(0);
        }
    }//GEN-LAST:event_ExitButtonActionPerformed

    private void SettingsButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SettingsButtonMouseEntered
        if(!SettingsBasePanel.isVisible())
            SettingsButton.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/com/razu/client/Images/settings2.png")));      
    }//GEN-LAST:event_SettingsButtonMouseEntered

    private void SettingsButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SettingsButtonMouseExited
        if(!SettingsBasePanel.isVisible())
            SettingsButton.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/com/razu/client/Images/settings.png")));      
    }//GEN-LAST:event_SettingsButtonMouseExited

    private void SettingsButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SettingsButtonMousePressed
        if(!SettingsBasePanel.isVisible())
            SettingsButton.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/com/razu/client/Images/settings.png")));      
    }//GEN-LAST:event_SettingsButtonMousePressed

    private void SettingsButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SettingsButtonMouseReleased
        if(!SettingsBasePanel.isVisible())
            SettingsButton.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/com/razu/client/Images/settings2.png")));      
    }//GEN-LAST:event_SettingsButtonMouseReleased

    private void SettingsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SettingsButtonActionPerformed
        if(!SettingsBasePanel.isVisible()){
            UserNickNameTextField.setText(userNickName);
            ServerAddressTextField.setText(serverAddress);
            UPDATE_UI.setVisibility(SettingsBasePanel, true);
            settingButtonClicked = true;
        }
    }//GEN-LAST:event_SettingsButtonActionPerformed

    private void DiceRollButttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DiceRollButttonActionPerformed
        if(!InvitationPanel.isVisible() && joinedInATeam){
            ACTION.DiceRollButttonAction();
        }
    }//GEN-LAST:event_DiceRollButttonActionPerformed

    private void SettingExitButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SettingExitButtonMouseEntered
        SettingExitButton.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/com/razu/client/Images/cross2.png")));      
    }//GEN-LAST:event_SettingExitButtonMouseEntered

    private void SettingExitButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SettingExitButtonMouseExited
        SettingExitButton.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/com/razu/client/Images/cross.png")));      
    }//GEN-LAST:event_SettingExitButtonMouseExited

    private void SettingExitButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SettingExitButtonMousePressed
        SettingExitButton.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/com/razu/client/Images/cross.png")));      
    }//GEN-LAST:event_SettingExitButtonMousePressed

    private void SettingExitButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SettingExitButtonMouseReleased
        SettingExitButton.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/com/razu/client/Images/cross2.png")));      
    }//GEN-LAST:event_SettingExitButtonMouseReleased

    private void SettingExitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SettingExitButtonActionPerformed
        UPDATE_UI.setVisibility(SettingsBasePanel, false);
    }//GEN-LAST:event_SettingExitButtonActionPerformed

    private void SettingsOkButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SettingsOkButtonMouseEntered
        SettingsOkButton.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/com/razu/client/Images/ok3.png")));      
    }//GEN-LAST:event_SettingsOkButtonMouseEntered

    private void SettingsOkButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SettingsOkButtonMouseExited
        SettingsOkButton.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/com/razu/client/Images/ok2.png")));      
    }//GEN-LAST:event_SettingsOkButtonMouseExited

    private void SettingsOkButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SettingsOkButtonMousePressed
        SettingsOkButton.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/com/razu/client/Images/ok2.png")));      
    }//GEN-LAST:event_SettingsOkButtonMousePressed

    private void SettingsOkButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SettingsOkButtonMouseReleased
        SettingsOkButton.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/com/razu/client/Images/ok3.png")));      
    }//GEN-LAST:event_SettingsOkButtonMouseReleased

    private void SettingsOkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SettingsOkButtonActionPerformed
        if(UserNickNameTextField.getText().trim().length()!=0)
            ACTION.SettingsOkButtonAction();
    }//GEN-LAST:event_SettingsOkButtonActionPerformed

    private void AcceptRequestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AcceptRequestButtonActionPerformed
        SERVER_DATA_PROCESSOR.wait = false;
        ACTION.InvitationAcceptDeny("yes");
    }//GEN-LAST:event_AcceptRequestButtonActionPerformed

    private void DenyRequestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DenyRequestButtonActionPerformed
        SERVER_DATA_PROCESSOR.wait = false;
        ACTION.InvitationAcceptDeny("no");
    }//GEN-LAST:event_DenyRequestButtonActionPerformed

    private void AcceptRequestButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AcceptRequestButtonMouseEntered
        AcceptRequestButton.setIcon(new javax.swing.ImageIcon(getClass()
                .getResource("/com/razu/client/Images/raccept2.png")));      
    }//GEN-LAST:event_AcceptRequestButtonMouseEntered

    private void AcceptRequestButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AcceptRequestButtonMouseExited
        AcceptRequestButton.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/com/razu/client/Images/raccept.png")));      
    }//GEN-LAST:event_AcceptRequestButtonMouseExited

    private void AcceptRequestButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AcceptRequestButtonMousePressed
        AcceptRequestButton.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/com/razu/client/Images/raccept.png")));      
    }//GEN-LAST:event_AcceptRequestButtonMousePressed

    private void AcceptRequestButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AcceptRequestButtonMouseReleased
        AcceptRequestButton.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/com/razu/client/Images/raccept2.png")));      
    }//GEN-LAST:event_AcceptRequestButtonMouseReleased

    private void DenyRequestButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DenyRequestButtonMouseEntered
        DenyRequestButton.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/com/razu/client/Images/rdeny2.png")));      
    }//GEN-LAST:event_DenyRequestButtonMouseEntered

    private void DenyRequestButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DenyRequestButtonMouseExited
        DenyRequestButton.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/com/razu/client/Images/rdeny.png")));      
    }//GEN-LAST:event_DenyRequestButtonMouseExited

    private void DenyRequestButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DenyRequestButtonMousePressed
        DenyRequestButton.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/com/razu/client/Images/rdeny.png")));      
    }//GEN-LAST:event_DenyRequestButtonMousePressed

    private void DenyRequestButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DenyRequestButtonMouseReleased
        DenyRequestButton.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/com/razu/client/Images/rdeny2.png")));      
    }//GEN-LAST:event_DenyRequestButtonMouseReleased

    private void InviteButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_InviteButtonMouseEntered
        if(!InvitationPanel.isVisible())
            InviteButton.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/com/razu/client/Images/invite3.png")));  
    }//GEN-LAST:event_InviteButtonMouseEntered

    private void InviteButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_InviteButtonMouseExited
        if(!InvitationPanel.isVisible())
            InviteButton.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/com/razu/client/Images/invite4.png")));      
    }//GEN-LAST:event_InviteButtonMouseExited

    private void InviteButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_InviteButtonMousePressed
        if(!InvitationPanel.isVisible())
            InviteButton.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/com/razu/client/Images/invite4.png")));      
    }//GEN-LAST:event_InviteButtonMousePressed

    private void InviteButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_InviteButtonMouseReleased
        if(!InvitationPanel.isVisible())
            InviteButton.setIcon(new javax.swing.ImageIcon(getClass().
                    getResource("/com/razu/client/Images/invite3.png")));      
    }//GEN-LAST:event_InviteButtonMouseReleased

    private void InviteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InviteButtonActionPerformed
        try {
            ACTION.sendInvite(client);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }//GEN-LAST:event_InviteButtonActionPerformed

    private void AvailablePlayerListFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_AvailablePlayerListFocusGained
        selectedPlayerList = UPDATE_UI.controlInvitationButtonVisibility();
    }//GEN-LAST:event_AvailablePlayerListFocusGained

    private void AvailablePlayerListFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_AvailablePlayerListFocusLost
        selectedPlayerList = UPDATE_UI.controlInvitationButtonVisibility();
    }//GEN-LAST:event_AvailablePlayerListFocusLost

    private void AvailablePlayerListMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AvailablePlayerListMouseMoved
        UPDATE_UI.controlInvitationButtonVisibility();
    }//GEN-LAST:event_AvailablePlayerListMouseMoved

    private void AvailablePlayerListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AvailablePlayerListMouseReleased
         UPDATE_UI.controlInvitationButtonVisibility();
    }//GEN-LAST:event_AvailablePlayerListMouseReleased

    private void ThisUserScoreLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ThisUserScoreLabelMouseClicked
        ACTION.getAndSetSelectedAvatar();
//        String avatarName = ACTION.getSelectedAvatarName();
//        UPDATE_UI.setTheUserAvatar(ThisUserLabel, avatarName);
//        if(joinedInATeam)
//            ACTION.sendAllTheAvatarChanged(avatarName);
    }//GEN-LAST:event_ThisUserScoreLabelMouseClicked

    private void ChatSendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChatSendButtonActionPerformed
        if(ChatWriteTextArea.getText().trim().length() > 1)
            ACTION.ChatSendButtonAction(ChatWriteTextArea.getText().trim());
    }//GEN-LAST:event_ChatSendButtonActionPerformed
    
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(ClientBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientBoard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton AcceptRequestButton;
    public javax.swing.JList<String> AvailablePlayerList;
    public javax.swing.JLabel AvailablePlayerListHeading;
    private jpanelcustom.JPanelCz AvailablePlayerListHeadingBasePanel;
    private jpanelcustom.JPanelC AvailablePlayerListPanel;
    private javax.swing.JPanel BasePanel;
    public jpanelcustom.JPanelCz ChatBasePanel;
    public javax.swing.JTextPane ChatBoxTextPane;
    public javax.swing.JButton ChatSendButton;
    private javax.swing.JTextArea ChatWriteTextArea;
    public javax.swing.JButton DenyRequestButton;
    public javax.swing.JLabel Dice1;
    public javax.swing.JLabel Dice2;
    public javax.swing.JLabel Dice3;
    public javax.swing.JLabel Dice4;
    public javax.swing.JLabel Dice5;
    public javax.swing.JLabel Dice6;
    public javax.swing.JButton DiceRollButtton;
    private jpanelcustom.JPanelCz DiceRollingPanel;
    private javax.swing.JLabel Divi;
    private javax.swing.JButton ExitButton;
    public javax.swing.JPanel GamePanel;
    private javax.swing.JLabel GamePanelBackgroundLabel;
    public javax.swing.JButton HomeButton;
    public org.jdesktop.swingx.JXLabel InvitationLabel;
    public jpanelcustom.JPanelCz InvitationPanel;
    public javax.swing.JButton InviteButton;
    public javax.swing.JLabel Ladder;
    private jpanelcustom.JPanelC LeftPanel;
    public jpanelcustom.JPanelCz ListBasePanel;
    public jpanelcustom.JPanelCz LudoBoardPanel;
    public jpanelcustom.JPanelC NickNameBasePanel;
    private javax.swing.JButton PlayButton;
    private jpanelcustom.JPanelCz PlayerBenchPanel;
    public jpanelcustom.JPanelCz RightUpPanel;
    public jpanelcustom.JPanelC ServerAddressBasePanel;
    public javax.swing.JTextField ServerAddressTextField;
    private javax.swing.JButton SettingExitButton;
    public javax.swing.JPanel SettingsBasePanel;
    private javax.swing.JButton SettingsButton;
    private javax.swing.JButton SettingsOkButton;
    private jpanelcustom.JPanelCz SettingsPanel;
    public javax.swing.JLabel Snake;
    public javax.swing.JLabel Snake2;
    public javax.swing.JPanel StartPanel;
    public javax.swing.JLabel StartPanelBackgroundLabel;
    public javax.swing.JLabel ThisUserLabel;
    public javax.swing.JLabel ThisUserLabelActive;
    public javax.swing.JLabel ThisUserName;
    public org.jdesktop.swingx.JXLabel ThisUserScoreLabel;
    public javax.swing.JLabel User2Label;
    public javax.swing.JLabel User2LabelActive;
    public javax.swing.JLabel User2NameLabel;
    public javax.swing.JLabel User2ScoreLabel;
    public javax.swing.JLabel User3Label;
    public javax.swing.JLabel User3LabelActive;
    public javax.swing.JLabel User3NameLabel;
    public javax.swing.JLabel User3ScoreLabel;
    public javax.swing.JLabel User4Label;
    public javax.swing.JLabel User4LabelActive;
    public javax.swing.JLabel User4NameLabel;
    public javax.swing.JLabel User4ScoreLabel;
    public javax.swing.JTextField UserNickNameTextField;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JScrollPane jScrollPane4;
    // End of variables declaration//GEN-END:variables
}

