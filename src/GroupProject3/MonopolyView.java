/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GroupProject3;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author jason
 */
public class MonopolyView extends javax.swing.JFrame {
    private MonopolyController controller;
    private String sequence = "";
    private String cheatCode = "UP UP DOWN DOWN LEFT LEFT RIGHT RIGHT A B A B";

    public MonopolyView() {
        initComponents();
        buyLandBT.setEnabled(false);
        payRentBT.setEnabled(false);
        passTurnBT.setEnabled(false);
        editGameBT.setEnabled(false);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("Key pressed: " + e.getKeyCode()); // Print the key code of the pressed key

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        sequence += "UP ";
                        break;
                    case KeyEvent.VK_DOWN:
                        sequence += "DOWN ";
                        break;
                    case KeyEvent.VK_LEFT:
                        sequence += "LEFT ";
                        break;
                    case KeyEvent.VK_RIGHT:
                        sequence += "RIGHT ";
                        break;
                    case KeyEvent.VK_A:
                        sequence += "A ";
                        break;
                    case KeyEvent.VK_B:
                        sequence += "B ";
                        break;
                }
                System.out.println("Current sequence: " + sequence);
                if (sequence.contains(cheatCode)) {
                    editGameBT.setEnabled(true);
                    System.out.println("Cheat code entered, Editor mode enabled");
                }
            }
        });
        this.setFocusable(true);
        this.requestFocusInWindow();
        
    }
    public void setController(MonopolyController cntl) {
    this.controller = cntl;
    }
public void update() {
    Info infos = controller.update();
    PlayerState[] players = infos.getPlayerStates();
    p1PositionNumLabel.setText(String.valueOf(players[0].getPosition()));
    p1BalanceAmountLabel.setText(String.valueOf(players[0].getBalance()));
    p2PositionNumLabel.setText(String.valueOf(players[1].getPosition()));
    p2BalanceAmountLabel.setText(String.valueOf(players[1].getBalance()));
    p3PositionNumLabel.setText(String.valueOf(players[2].getPosition()));
    p3BalanceAmountLabel.setText(String.valueOf(players[2].getBalance()));
    p4PositionNumLabel.setText(String.valueOf(players[3].getPosition()));
    p4BalanceAmountLabel.setText(String.valueOf(players[3].getBalance()));
    
    int landOwnership [] = infos.ownerships;

    StringBuilder p1Lands = new StringBuilder();
    StringBuilder p2Lands = new StringBuilder();
    StringBuilder p3Lands = new StringBuilder();
    StringBuilder p4Lands = new StringBuilder();

    for (int i = 1; i < landOwnership.length; i++) {
        switch (landOwnership[i]) {
            case 1:
                if (p1Lands.length() > 0) {
                    p1Lands.append(", ");
                }
                p1Lands.append(i);
                break;
            case 2:
                if (p2Lands.length() > 0) {
                    p2Lands.append(", ");
                }
                p2Lands.append(i);
                break;
            case 3:
                if (p3Lands.length() > 0) {
                    p3Lands.append(", ");
                }
                p3Lands.append(i);
                break;
            case 4:
                if (p4Lands.length() > 0) {
                    p4Lands.append(", ");
                }
                p4Lands.append(i);
                break;
        }
    }

    p1LandsOwnershipDisplayLabel.setText(p1Lands.toString());
    p2LandsOwnershipDisplayLabel.setText(p2Lands.toString());
    p3LandsOwnershipDisplayLabel.setText(p3Lands.toString());
    p4LandsOwnershipDisplayLabel.setText(p4Lands.toString());
    
    //check if game end conditon be reached
/*  for(int i=0;i<3; i++ ){
        if(players[i].getBalance() < 0){
            showMessage("One of the player is bankrupted, game ended!");
            rollDiceBT.setEnabled(false);
            buyLandBT.setEnabled(false);
            payRentBT.setEnabled(false);
            passTurnBT.setEnabled(false);
            break;
        }
    }*/
}


    public void rollDice() {        
        DiceDisplayLabel.setText(String.valueOf(controller.rollDice()));
    }
    
    public void updatePlayerPosition(){
        controller.updatePlayerPosition(turnDisplayLabel.getText(),Integer.parseInt(DiceDisplayLabel.getText()));
    }
    
    public void passTurn(){
        turnDisplayLabel.setText(controller.passTurn(turnDisplayLabel.getText()));
    }
    
    public void loadLands(){
        LandsInfo[] landsInfo = controller.loadLands();

        JLabel[] slotLabels = {
            slot1Label, slot2Label, slot3Label, slot4Label, slot5Label,
            slot6Label, slot7Label, slot8Label, slot9Label, slot10Label,
            slot11Label, slot12Label, slot13Label, slot14Label, slot15Label,
            slot16Label, slot17Label, slot18Label, slot19Label, slot20Label,
            slot21Label, slot22Label
        };

        JLabel[] priceLabels = {
            slot1PLabel, slot2PLabel, slot3PLabel, slot4PLabel, slot5PLabel,
            slot6PLabel, slot7PLabel, slot8PLabel, slot9PLabel, slot10PLabel,
            slot11PLabel, slot12PLabel, slot13PLabel, slot14PLabel, slot15PLabel,
            slot16PLabel, slot17PLabel, slot18PLabel, slot19PLabel, slot20PLabel,
            slot21PLabel, slot22PLabel
        };


    for (int i = 0; i < 22; i++) {
        LandsInfo landInfo = landsInfo[i+1];
        slotLabels[i].setText(landInfo.getLandName());
        priceLabels[i].setText("$"+String.valueOf(landInfo.getPrice()));
    }
}

    public void checkPositionToBeMoved(){
        String player = turnDisplayLabel.getText();
        switch(player){
            case"Player 1":
                if(!p1PositionNumLabel.getText().equals("0")){
                    Info infos = controller.update();
                    int landOwnerships[] = infos.getLandOwnerships();
                    if(landOwnerships[Integer.parseInt(p1PositionNumLabel.getText())] == 0){
                        buyLandBT.setEnabled(true);
                        passTurnBT.setEnabled(true);                        
                    }else{
                        if(landOwnerships[Integer.parseInt(p1PositionNumLabel.getText())] == 1){
                            passTurnBT.setEnabled(true);
                        }else{
                            if(landOwnerships[Integer.parseInt(p1PositionNumLabel.getText())] != 1){
                                payRentBT.setEnabled(true);
                            }   
                        }
                    }
                }else{
                    passTurnBT.setEnabled(true);
                }
                break;
            case"Player 2":
                if(!p2PositionNumLabel.getText().equals("0")){
                    Info infos = controller.update();
                    int landOwnerships[] = infos.getLandOwnerships();
                    if(landOwnerships[Integer.parseInt(p2PositionNumLabel.getText())] == 0){
                        buyLandBT.setEnabled(true);
                        passTurnBT.setEnabled(true);                        
                    }else{
                        if(landOwnerships[Integer.parseInt(p2PositionNumLabel.getText())] == 2){
                            passTurnBT.setEnabled(true);
                        }else{
                            if(landOwnerships[Integer.parseInt(p2PositionNumLabel.getText())] != 2){
                                payRentBT.setEnabled(true);
                            }   
                        }
                    }
                }else{
                    passTurnBT.setEnabled(true);
                }
                break;                
            case"Player 3":
                if(!p3PositionNumLabel.getText().equals("0")){
                    Info infos = controller.update();
                    int landOwnerships[] = infos.getLandOwnerships();
                    if(landOwnerships[Integer.parseInt(p3PositionNumLabel.getText())] == 0){
                        buyLandBT.setEnabled(true);
                        passTurnBT.setEnabled(true);                        
                    }else{
                        if(landOwnerships[Integer.parseInt(p3PositionNumLabel.getText())] == 3){
                            passTurnBT.setEnabled(true);
                        }else{
                            if(landOwnerships[Integer.parseInt(p3PositionNumLabel.getText())] != 3){
                                payRentBT.setEnabled(true);
                            }   
                        }
                    }
                }else{
                    passTurnBT.setEnabled(true);
                }
                break;
            case"Player 4":
                if(!p4PositionNumLabel.getText().equals("0")){
                    Info infos = controller.update();
                    int landOwnerships[] = infos.getLandOwnerships();
                    if(landOwnerships[Integer.parseInt(p4PositionNumLabel.getText())] == 0){
                        buyLandBT.setEnabled(true);
                        passTurnBT.setEnabled(true);                        
                    }else{
                        if(landOwnerships[Integer.parseInt(p4PositionNumLabel.getText())] == 4){
                            passTurnBT.setEnabled(true);
                        }else{
                            if(landOwnerships[Integer.parseInt(p4PositionNumLabel.getText())] != 4){
                                payRentBT.setEnabled(true);
                            }   
                        }
                    }
                }else{
                    passTurnBT.setEnabled(true);
                }
                break;
        }
    }

    public void buyLand() {
        Info infos = controller.update();
        String player = turnDisplayLabel.getText();
        int landOwnerships [] = infos.getLandOwnerships();
        LandsInfo[] landsInfo = controller.loadLands();
        
        PlayerState players[] = infos.getPlayerStates();

        JLabel[] slotLabels = {
            slot1Label, slot2Label, slot3Label, slot4Label, slot5Label,
            slot6Label, slot7Label, slot8Label, slot9Label, slot10Label,
            slot11Label, slot12Label, slot13Label, slot14Label, slot15Label,
            slot16Label, slot17Label, slot18Label, slot19Label, slot20Label,
            slot21Label, slot22Label
        };

        JLabel[] priceLabels = {
            slot1PLabel, slot2PLabel, slot3PLabel, slot4PLabel, slot5PLabel,
            slot6PLabel, slot7PLabel, slot8PLabel, slot9PLabel, slot10PLabel,
            slot11PLabel, slot12PLabel, slot13PLabel, slot14PLabel, slot15PLabel,
            slot16PLabel, slot17PLabel, slot18PLabel, slot19PLabel, slot20PLabel,
            slot21PLabel, slot22PLabel
        };
        
        switch (player){
            case "Player 1":
                int position = Integer.parseInt(p1PositionNumLabel.getText());
                int landPrice = landsInfo[position].getPrice();
                if(landPrice > players[0].getBalance()){
                    buyLandBT.setEnabled(false);
                }else{
                controller.buyLand(1, position, landPrice);                  
                }
                break;
            case "Player 2":
                position = Integer.parseInt(p2PositionNumLabel.getText());
                landPrice = landsInfo[position].getPrice();
                if(landPrice > players[1].getBalance()){
                    buyLandBT.setEnabled(false);
                }else{
                controller.buyLand(2, position, landPrice);                  
                }
                break;
            case "Player 3":
                position = Integer.parseInt(p3PositionNumLabel.getText());
                landPrice = landsInfo[position].getPrice();
                if(landPrice > players[2].getBalance()){
                    buyLandBT.setEnabled(false);
                }else{
                controller.buyLand(3, position, landPrice);                  
                }
                break;
            case "Player 4":
                position =Integer.parseInt(p4PositionNumLabel.getText());
                landPrice = landsInfo[position].getPrice();
                if(landPrice > players[3].getBalance()){
                    buyLandBT.setEnabled(false);
                }else{
                controller.buyLand(4, position, landPrice);                  
                }
                break;
        }
    }

    public void payRentalFee() {
        Info infos = controller.update();
        String player = turnDisplayLabel.getText();
        int landOwnerships [] = infos.getLandOwnerships();
        LandsInfo[] landsInfo = controller.loadLands();
        
        PlayerState players[] = infos.getPlayerStates();

        JLabel[] slotLabels = {
            slot1Label, slot2Label, slot3Label, slot4Label, slot5Label,
            slot6Label, slot7Label, slot8Label, slot9Label, slot10Label,
            slot11Label, slot12Label, slot13Label, slot14Label, slot15Label,
            slot16Label, slot17Label, slot18Label, slot19Label, slot20Label,
            slot21Label, slot22Label
        };

        JLabel[] priceLabels = {
            slot1PLabel, slot2PLabel, slot3PLabel, slot4PLabel, slot5PLabel,
            slot6PLabel, slot7PLabel, slot8PLabel, slot9PLabel, slot10PLabel,
            slot11PLabel, slot12PLabel, slot13PLabel, slot14PLabel, slot15PLabel,
            slot16PLabel, slot17PLabel, slot18PLabel, slot19PLabel, slot20PLabel,
            slot21PLabel, slot22PLabel
        };
        
        switch (player){
            case "Player 1":
                int position = Integer.parseInt(p1PositionNumLabel.getText());
                int landPrice = landsInfo[position].getPrice();
                controller.payRentalFee(1, (int) (landPrice*0.1), landOwnerships[position]);
                break;
            case "Player 2":
                position = Integer.parseInt(p2PositionNumLabel.getText());
                landPrice = landsInfo[position].getPrice();
                controller.payRentalFee(2, (int) (landPrice*0.1), landOwnerships[position]);
                break;
            case "Player 3":
                position = Integer.parseInt(p3PositionNumLabel.getText());
                landPrice = landsInfo[position].getPrice();
                controller.payRentalFee(3, (int) (landPrice*0.1), landOwnerships[position]);
                break;
            case "Player 4":
                position =Integer.parseInt(p4PositionNumLabel.getText());
                landPrice = landsInfo[position].getPrice();
                controller.payRentalFee(4, (int) (landPrice*0.1), landOwnerships[position]);
                break;
        }
    }
    
    public void edit(int choice, int player, int number) {

    }
    
    public void newGame(){
        controller.newGame();
        rollDiceBT.setEnabled(true);
        buyLandBT.setEnabled(false);
        payRentBT.setEnabled(false);
        passTurnBT.setEnabled(false);
        turnDisplayLabel.setText("Player 1");
    }
    
    public void showMessage(String str){
        JOptionPane.showMessageDialog(null, str);
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        board = new javax.swing.JPanel();
        cornerLHPanel = new javax.swing.JPanel();
        cornerLBPanel = new javax.swing.JPanel();
        cornerRHPanel = new javax.swing.JPanel();
        slot0Panel = new javax.swing.JPanel();
        slot0Label = new javax.swing.JLabel();
        collect2000Label = new javax.swing.JLabel();
        slot1Panel = new javax.swing.JPanel();
        slot1Label = new javax.swing.JLabel();
        slot1ColorTone = new javax.swing.JPanel();
        slot1PLabel = new javax.swing.JLabel();
        slotTag1 = new javax.swing.JLabel();
        slot2Panel = new javax.swing.JPanel();
        slot2Label = new javax.swing.JLabel();
        slot2ColorTone = new javax.swing.JPanel();
        slot2PLabel = new javax.swing.JLabel();
        slotTag2 = new javax.swing.JLabel();
        emptySlotPanel0 = new javax.swing.JPanel();
        emptySlotLabel0 = new javax.swing.JLabel();
        emptySlot0ColorTone = new javax.swing.JPanel();
        slot3Panel = new javax.swing.JPanel();
        slot3Label = new javax.swing.JLabel();
        slot3ColorTone = new javax.swing.JPanel();
        slot3PLabel = new javax.swing.JLabel();
        slotTag3 = new javax.swing.JLabel();
        slot4Panel = new javax.swing.JPanel();
        slot4Label = new javax.swing.JLabel();
        slot4ColorTone = new javax.swing.JPanel();
        slot4PLabel = new javax.swing.JLabel();
        slotTag4 = new javax.swing.JLabel();
        slot5Panel = new javax.swing.JPanel();
        slot5Label = new javax.swing.JLabel();
        slot5ColorTone = new javax.swing.JPanel();
        slot5PLabel = new javax.swing.JLabel();
        slotTag5 = new javax.swing.JLabel();
        slot6Panel = new javax.swing.JPanel();
        slot6Label = new javax.swing.JLabel();
        slot6ColorTone = new javax.swing.JPanel();
        slot6PLabel = new javax.swing.JLabel();
        slotTag6 = new javax.swing.JLabel();
        slot7Panel = new javax.swing.JPanel();
        slot7ColorTone = new javax.swing.JPanel();
        slot7Label = new javax.swing.JLabel();
        slot7PLabel = new javax.swing.JLabel();
        slotTag7 = new javax.swing.JLabel();
        slot8Panel = new javax.swing.JPanel();
        slot8ColorTone = new javax.swing.JPanel();
        slot8Label = new javax.swing.JLabel();
        slot8PLabel = new javax.swing.JLabel();
        slotTag8 = new javax.swing.JLabel();
        slot9Panel = new javax.swing.JPanel();
        slot9ColorTone = new javax.swing.JPanel();
        slot9Label = new javax.swing.JLabel();
        slot9PLabel = new javax.swing.JLabel();
        slotTag9 = new javax.swing.JLabel();
        slot10Panel = new javax.swing.JPanel();
        Slot10ColorTone = new javax.swing.JPanel();
        slot10Label = new javax.swing.JLabel();
        slot10PLabel = new javax.swing.JLabel();
        slotTag10 = new javax.swing.JLabel();
        slot11Panel = new javax.swing.JPanel();
        slot11ColorTone = new javax.swing.JPanel();
        slot11Label = new javax.swing.JLabel();
        slot11PLabel = new javax.swing.JLabel();
        slotTag11 = new javax.swing.JLabel();
        slot12Panel = new javax.swing.JPanel();
        slot12ColorTone = new javax.swing.JPanel();
        slot12Label = new javax.swing.JLabel();
        slot12PLabel = new javax.swing.JLabel();
        slotTag12 = new javax.swing.JLabel();
        slot13Panel = new javax.swing.JPanel();
        slot13ColorTone = new javax.swing.JPanel();
        slot13Label = new javax.swing.JLabel();
        slot13PLabel = new javax.swing.JLabel();
        slotTag13 = new javax.swing.JLabel();
        slot14Panel = new javax.swing.JPanel();
        slot14ColorTone = new javax.swing.JPanel();
        slot14Label = new javax.swing.JLabel();
        slot14PLabel = new javax.swing.JLabel();
        slotTag14 = new javax.swing.JLabel();
        slot15Panel = new javax.swing.JPanel();
        slot15ColorTone = new javax.swing.JPanel();
        slot15Label = new javax.swing.JLabel();
        slot15PLabel = new javax.swing.JLabel();
        slotTag15 = new javax.swing.JLabel();
        slot16Panel = new javax.swing.JPanel();
        slot16ColorTone = new javax.swing.JPanel();
        slot16Label = new javax.swing.JLabel();
        slot16PLabel = new javax.swing.JLabel();
        slotTag16 = new javax.swing.JLabel();
        slot17Panel = new javax.swing.JPanel();
        slot17ColorTone = new javax.swing.JPanel();
        slot17Label = new javax.swing.JLabel();
        slot17PLabel = new javax.swing.JLabel();
        slotTag17 = new javax.swing.JLabel();
        slot18Panel = new javax.swing.JPanel();
        slot18ColorTone = new javax.swing.JPanel();
        slot18Label = new javax.swing.JLabel();
        slot18PLabel = new javax.swing.JLabel();
        slotTag18 = new javax.swing.JLabel();
        slot19Panel = new javax.swing.JPanel();
        slot19ColorTone = new javax.swing.JPanel();
        slot19Label = new javax.swing.JLabel();
        slot19PLabel = new javax.swing.JLabel();
        slotTag19 = new javax.swing.JLabel();
        slot20Panel = new javax.swing.JPanel();
        slot20ColorTone = new javax.swing.JPanel();
        slot20Label = new javax.swing.JLabel();
        slot20PLabel = new javax.swing.JLabel();
        slotTag20 = new javax.swing.JLabel();
        emptySlotPanel1 = new javax.swing.JPanel();
        emptySlot1ColorTone = new javax.swing.JPanel();
        emptySlotLabel1 = new javax.swing.JLabel();
        slot21Panel = new javax.swing.JPanel();
        slot21ColorTone = new javax.swing.JPanel();
        slot21Label = new javax.swing.JLabel();
        slot21PLabel = new javax.swing.JLabel();
        slotTag21 = new javax.swing.JLabel();
        slot22Panel = new javax.swing.JPanel();
        slot22ColorTone = new javax.swing.JPanel();
        slot22Label = new javax.swing.JLabel();
        slot22PLabel = new javax.swing.JLabel();
        slotTag22 = new javax.swing.JLabel();
        informationPanel = new javax.swing.JPanel();
        turnLabel = new javax.swing.JLabel();
        turnDisplayLabel = new javax.swing.JLabel();
        rollDiceBT = new javax.swing.JButton();
        DiceLabel = new javax.swing.JLabel();
        DiceDisplayLabel = new javax.swing.JLabel();
        buyLandBT = new javax.swing.JButton();
        payRentBT = new javax.swing.JButton();
        passTurnBT = new javax.swing.JButton();
        newGameBT = new javax.swing.JButton();
        editGameBT = new javax.swing.JButton();
        playersInfoPanel = new javax.swing.JPanel();
        player1InfoPanel = new javax.swing.JPanel();
        player1Label = new javax.swing.JLabel();
        p1BalanceLabel = new javax.swing.JLabel();
        p1PositionLabel = new javax.swing.JLabel();
        p1StatusLabel = new javax.swing.JLabel();
        p1BalanceAmountLabel = new javax.swing.JLabel();
        p1PositionNumLabel = new javax.swing.JLabel();
        p1StausStatueLabel = new javax.swing.JLabel();
        p1LandsOwnershipLabel = new javax.swing.JLabel();
        p1LandsOwnershipDisplayLabel = new javax.swing.JLabel();
        player2InfoPanel = new javax.swing.JPanel();
        p2LandsOwnershipDisplayLabel = new javax.swing.JLabel();
        p2LandsOwnershipLabel = new javax.swing.JLabel();
        p2BalanceLabel = new javax.swing.JLabel();
        p2BalanceAmountLabel = new javax.swing.JLabel();
        p2PositionLabel = new javax.swing.JLabel();
        p2PositionNumLabel = new javax.swing.JLabel();
        p2StatusLabel = new javax.swing.JLabel();
        p2StausStatueLabel = new javax.swing.JLabel();
        player2Label = new javax.swing.JLabel();
        player3InfoPanel = new javax.swing.JPanel();
        p3LandsOwnershipDisplayLabel = new javax.swing.JLabel();
        p3LandsOwnershipLabel = new javax.swing.JLabel();
        p3BalanceLabel = new javax.swing.JLabel();
        p3BalanceAmountLabel = new javax.swing.JLabel();
        p3PositionLabel = new javax.swing.JLabel();
        p3PositionNumLabel = new javax.swing.JLabel();
        p3StatusLabel = new javax.swing.JLabel();
        p3StausStatueLabel = new javax.swing.JLabel();
        player3Label = new javax.swing.JLabel();
        player4InfoPanel = new javax.swing.JPanel();
        p4LandsOwnershipDisplayLabel = new javax.swing.JLabel();
        p4LandsOwnershipLabel = new javax.swing.JLabel();
        p4BalanceLabel = new javax.swing.JLabel();
        p4BalanceAmountLabel = new javax.swing.JLabel();
        p4PositionLabel = new javax.swing.JLabel();
        p4PositionNumLabel = new javax.swing.JLabel();
        p4StatusLabel = new javax.swing.JLabel();
        p4StausStatueLabel = new javax.swing.JLabel();
        player4Label = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        board.setBackground(new java.awt.Color(191, 219, 174));

        cornerLHPanel.setBackground(new java.awt.Color(191, 219, 174));
        cornerLHPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout cornerLHPanelLayout = new javax.swing.GroupLayout(cornerLHPanel);
        cornerLHPanel.setLayout(cornerLHPanelLayout);
        cornerLHPanelLayout.setHorizontalGroup(
            cornerLHPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        cornerLHPanelLayout.setVerticalGroup(
            cornerLHPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        cornerLBPanel.setBackground(new java.awt.Color(191, 219, 174));
        cornerLBPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cornerLBPanel.setPreferredSize(new java.awt.Dimension(150, 150));

        javax.swing.GroupLayout cornerLBPanelLayout = new javax.swing.GroupLayout(cornerLBPanel);
        cornerLBPanel.setLayout(cornerLBPanelLayout);
        cornerLBPanelLayout.setHorizontalGroup(
            cornerLBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        cornerLBPanelLayout.setVerticalGroup(
            cornerLBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        cornerRHPanel.setBackground(new java.awt.Color(191, 219, 174));
        cornerRHPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cornerRHPanel.setPreferredSize(new java.awt.Dimension(150, 150));

        javax.swing.GroupLayout cornerRHPanelLayout = new javax.swing.GroupLayout(cornerRHPanel);
        cornerRHPanel.setLayout(cornerRHPanelLayout);
        cornerRHPanelLayout.setHorizontalGroup(
            cornerRHPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        cornerRHPanelLayout.setVerticalGroup(
            cornerRHPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        slot0Panel.setBackground(new java.awt.Color(191, 219, 174));
        slot0Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        slot0Label.setFont(new java.awt.Font("Calibri", 0, 36)); // NOI18N
        slot0Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot0Label.setText("GO");

        collect2000Label.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        collect2000Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        collect2000Label.setText("Collect $2000");

        javax.swing.GroupLayout slot0PanelLayout = new javax.swing.GroupLayout(slot0Panel);
        slot0Panel.setLayout(slot0PanelLayout);
        slot0PanelLayout.setHorizontalGroup(
            slot0PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(slot0PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(slot0PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(collect2000Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot0Label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                .addContainerGap())
        );
        slot0PanelLayout.setVerticalGroup(
            slot0PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(slot0PanelLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(slot0Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(collect2000Label, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        slot1Panel.setBackground(new java.awt.Color(191, 219, 174));
        slot1Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        slot1Label.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot1Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot1Label.setText("Slot1");

        slot1ColorTone.setBackground(new java.awt.Color(150, 85, 57));

        javax.swing.GroupLayout slot1ColorToneLayout = new javax.swing.GroupLayout(slot1ColorTone);
        slot1ColorTone.setLayout(slot1ColorToneLayout);
        slot1ColorToneLayout.setHorizontalGroup(
            slot1ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        slot1ColorToneLayout.setVerticalGroup(
            slot1ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        slot1PLabel.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot1PLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot1PLabel.setText("$Price1");

        slotTag1.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        slotTag1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slotTag1.setText("1");

        javax.swing.GroupLayout slot1PanelLayout = new javax.swing.GroupLayout(slot1Panel);
        slot1Panel.setLayout(slot1PanelLayout);
        slot1PanelLayout.setHorizontalGroup(
            slot1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(slot1ColorTone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(slot1PanelLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(slot1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(slot1Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot1PLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)))
            .addComponent(slotTag1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        slot1PanelLayout.setVerticalGroup(
            slot1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, slot1PanelLayout.createSequentialGroup()
                .addComponent(slot1ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slot1Label, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slotTag1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(slot1PLabel))
        );

        slot2Panel.setBackground(new java.awt.Color(191, 219, 174));
        slot2Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        slot2Label.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot2Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot2Label.setText("Slot2");

        slot2ColorTone.setBackground(new java.awt.Color(150, 85, 57));

        javax.swing.GroupLayout slot2ColorToneLayout = new javax.swing.GroupLayout(slot2ColorTone);
        slot2ColorTone.setLayout(slot2ColorToneLayout);
        slot2ColorToneLayout.setHorizontalGroup(
            slot2ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 110, Short.MAX_VALUE)
        );
        slot2ColorToneLayout.setVerticalGroup(
            slot2ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        slot2PLabel.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot2PLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot2PLabel.setText("$Price2");

        slotTag2.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        slotTag2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slotTag2.setText("2");

        javax.swing.GroupLayout slot2PanelLayout = new javax.swing.GroupLayout(slot2Panel);
        slot2Panel.setLayout(slot2PanelLayout);
        slot2PanelLayout.setHorizontalGroup(
            slot2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(slot2ColorTone, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(slot2PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(slot2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(slot2Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot2PLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(slotTag2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        slot2PanelLayout.setVerticalGroup(
            slot2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, slot2PanelLayout.createSequentialGroup()
                .addComponent(slot2ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slot2Label, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slotTag2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addComponent(slot2PLabel))
        );

        emptySlotPanel0.setBackground(new java.awt.Color(191, 219, 174));
        emptySlotPanel0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        emptySlotLabel0.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        emptySlotLabel0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        emptySlot0ColorTone.setBackground(new java.awt.Color(191, 219, 174));

        javax.swing.GroupLayout emptySlot0ColorToneLayout = new javax.swing.GroupLayout(emptySlot0ColorTone);
        emptySlot0ColorTone.setLayout(emptySlot0ColorToneLayout);
        emptySlot0ColorToneLayout.setHorizontalGroup(
            emptySlot0ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        emptySlot0ColorToneLayout.setVerticalGroup(
            emptySlot0ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout emptySlotPanel0Layout = new javax.swing.GroupLayout(emptySlotPanel0);
        emptySlotPanel0.setLayout(emptySlotPanel0Layout);
        emptySlotPanel0Layout.setHorizontalGroup(
            emptySlotPanel0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(emptySlotPanel0Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(emptySlotLabel0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, emptySlotPanel0Layout.createSequentialGroup()
                .addGap(0, 19, Short.MAX_VALUE)
                .addComponent(emptySlot0ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        emptySlotPanel0Layout.setVerticalGroup(
            emptySlotPanel0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, emptySlotPanel0Layout.createSequentialGroup()
                .addComponent(emptySlot0ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(emptySlotLabel0, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        slot3Panel.setBackground(new java.awt.Color(191, 219, 174));
        slot3Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        slot3Label.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot3Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot3Label.setText("Slot3");

        slot3ColorTone.setBackground(new java.awt.Color(172, 223, 252));

        javax.swing.GroupLayout slot3ColorToneLayout = new javax.swing.GroupLayout(slot3ColorTone);
        slot3ColorTone.setLayout(slot3ColorToneLayout);
        slot3ColorToneLayout.setHorizontalGroup(
            slot3ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        slot3ColorToneLayout.setVerticalGroup(
            slot3ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        slot3PLabel.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot3PLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot3PLabel.setText("$Price3");

        slotTag3.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        slotTag3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slotTag3.setText("3");

        javax.swing.GroupLayout slot3PanelLayout = new javax.swing.GroupLayout(slot3Panel);
        slot3Panel.setLayout(slot3PanelLayout);
        slot3PanelLayout.setHorizontalGroup(
            slot3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(slot3ColorTone, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(slot3PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(slot3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(slot3Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot3PLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(slotTag3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        slot3PanelLayout.setVerticalGroup(
            slot3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, slot3PanelLayout.createSequentialGroup()
                .addComponent(slot3ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slot3Label, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slotTag3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(slot3PLabel))
        );

        slot4Panel.setBackground(new java.awt.Color(191, 219, 174));
        slot4Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        slot4Label.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot4Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot4Label.setText("Slot4");

        slot4ColorTone.setBackground(new java.awt.Color(172, 223, 252));

        javax.swing.GroupLayout slot4ColorToneLayout = new javax.swing.GroupLayout(slot4ColorTone);
        slot4ColorTone.setLayout(slot4ColorToneLayout);
        slot4ColorToneLayout.setHorizontalGroup(
            slot4ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        slot4ColorToneLayout.setVerticalGroup(
            slot4ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        slot4PLabel.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot4PLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot4PLabel.setText("$Price4");

        slotTag4.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        slotTag4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slotTag4.setText("4");

        javax.swing.GroupLayout slot4PanelLayout = new javax.swing.GroupLayout(slot4Panel);
        slot4Panel.setLayout(slot4PanelLayout);
        slot4PanelLayout.setHorizontalGroup(
            slot4PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(slot4ColorTone, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(slot4PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(slot4PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(slot4Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot4PLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(slotTag4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        slot4PanelLayout.setVerticalGroup(
            slot4PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, slot4PanelLayout.createSequentialGroup()
                .addComponent(slot4ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slot4Label, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slotTag4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(slot4PLabel))
        );

        slot5Panel.setBackground(new java.awt.Color(191, 219, 174));
        slot5Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        slot5Label.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot5Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot5Label.setText("Slot5");

        slot5ColorTone.setBackground(new java.awt.Color(172, 223, 252));

        javax.swing.GroupLayout slot5ColorToneLayout = new javax.swing.GroupLayout(slot5ColorTone);
        slot5ColorTone.setLayout(slot5ColorToneLayout);
        slot5ColorToneLayout.setHorizontalGroup(
            slot5ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        slot5ColorToneLayout.setVerticalGroup(
            slot5ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        slot5PLabel.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot5PLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot5PLabel.setText("$Price5");

        slotTag5.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        slotTag5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slotTag5.setText("5");

        javax.swing.GroupLayout slot5PanelLayout = new javax.swing.GroupLayout(slot5Panel);
        slot5Panel.setLayout(slot5PanelLayout);
        slot5PanelLayout.setHorizontalGroup(
            slot5PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(slot5ColorTone, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(slot5PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(slot5PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(slot5Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot5PLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(slotTag5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        slot5PanelLayout.setVerticalGroup(
            slot5PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, slot5PanelLayout.createSequentialGroup()
                .addComponent(slot5ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slot5Label, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slotTag5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(slot5PLabel))
        );

        slot6Panel.setBackground(new java.awt.Color(191, 219, 174));
        slot6Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        slot6Label.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot6Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot6Label.setText("Slot6");

        slot6ColorTone.setBackground(new java.awt.Color(217, 58, 151));

        javax.swing.GroupLayout slot6ColorToneLayout = new javax.swing.GroupLayout(slot6ColorTone);
        slot6ColorTone.setLayout(slot6ColorToneLayout);
        slot6ColorToneLayout.setHorizontalGroup(
            slot6ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        slot6ColorToneLayout.setVerticalGroup(
            slot6ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        slot6PLabel.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot6PLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot6PLabel.setText("$Price6");

        slotTag6.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        slotTag6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slotTag6.setText("6");

        javax.swing.GroupLayout slot6PanelLayout = new javax.swing.GroupLayout(slot6Panel);
        slot6Panel.setLayout(slot6PanelLayout);
        slot6PanelLayout.setHorizontalGroup(
            slot6PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(slot6PanelLayout.createSequentialGroup()
                .addGroup(slot6PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(slot6PanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(slot6PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(slot6Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(slot6PLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)))
                    .addComponent(slotTag6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slot6ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        slot6PanelLayout.setVerticalGroup(
            slot6PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(slot6PanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(slot6ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(slot6PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(slot6Label, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slotTag6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slot6PLabel))
        );

        slot7Panel.setBackground(new java.awt.Color(191, 219, 174));
        slot7Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        slot7ColorTone.setBackground(new java.awt.Color(217, 58, 151));
        slot7ColorTone.setToolTipText("");

        javax.swing.GroupLayout slot7ColorToneLayout = new javax.swing.GroupLayout(slot7ColorTone);
        slot7ColorTone.setLayout(slot7ColorToneLayout);
        slot7ColorToneLayout.setHorizontalGroup(
            slot7ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        slot7ColorToneLayout.setVerticalGroup(
            slot7ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        slot7Label.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot7Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot7Label.setText("Slot7");

        slot7PLabel.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot7PLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot7PLabel.setText("$Price7");

        slotTag7.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        slotTag7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slotTag7.setText("7");

        javax.swing.GroupLayout slot7PanelLayout = new javax.swing.GroupLayout(slot7Panel);
        slot7Panel.setLayout(slot7PanelLayout);
        slot7PanelLayout.setHorizontalGroup(
            slot7PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, slot7PanelLayout.createSequentialGroup()
                .addGroup(slot7PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(slot7PanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(slot7PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(slot7Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(slot7PLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)))
                    .addComponent(slotTag7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slot7ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        slot7PanelLayout.setVerticalGroup(
            slot7PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(slot7PanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(slot7ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(slot7PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(slot7Label, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slotTag7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slot7PLabel))
        );

        slot8Panel.setBackground(new java.awt.Color(191, 219, 174));
        slot8Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        slot8ColorTone.setBackground(new java.awt.Color(217, 58, 151));

        javax.swing.GroupLayout slot8ColorToneLayout = new javax.swing.GroupLayout(slot8ColorTone);
        slot8ColorTone.setLayout(slot8ColorToneLayout);
        slot8ColorToneLayout.setHorizontalGroup(
            slot8ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        slot8ColorToneLayout.setVerticalGroup(
            slot8ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        slot8Label.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot8Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot8Label.setText("Slot8");

        slot8PLabel.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot8PLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot8PLabel.setText("$Price8");

        slotTag8.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        slotTag8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slotTag8.setText("8");

        javax.swing.GroupLayout slot8PanelLayout = new javax.swing.GroupLayout(slot8Panel);
        slot8Panel.setLayout(slot8PanelLayout);
        slot8PanelLayout.setHorizontalGroup(
            slot8PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, slot8PanelLayout.createSequentialGroup()
                .addGroup(slot8PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(slot8PanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(slot8PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(slot8Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(slot8PLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)))
                    .addComponent(slotTag8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slot8ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        slot8PanelLayout.setVerticalGroup(
            slot8PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(slot8PanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(slot8ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(slot8PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(slot8Label, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slotTag8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slot8PLabel))
        );

        slot9Panel.setBackground(new java.awt.Color(191, 219, 174));
        slot9Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        slot9ColorTone.setBackground(new java.awt.Color(247, 148, 29));

        javax.swing.GroupLayout slot9ColorToneLayout = new javax.swing.GroupLayout(slot9ColorTone);
        slot9ColorTone.setLayout(slot9ColorToneLayout);
        slot9ColorToneLayout.setHorizontalGroup(
            slot9ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        slot9ColorToneLayout.setVerticalGroup(
            slot9ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        slot9Label.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot9Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot9Label.setText("Slot9");

        slot9PLabel.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot9PLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot9PLabel.setText("$Price9");

        slotTag9.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        slotTag9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slotTag9.setText("9");

        javax.swing.GroupLayout slot9PanelLayout = new javax.swing.GroupLayout(slot9Panel);
        slot9Panel.setLayout(slot9PanelLayout);
        slot9PanelLayout.setHorizontalGroup(
            slot9PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, slot9PanelLayout.createSequentialGroup()
                .addGroup(slot9PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(slot9PanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(slot9PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(slot9Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(slot9PLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)))
                    .addComponent(slotTag9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slot9ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        slot9PanelLayout.setVerticalGroup(
            slot9PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(slot9PanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(slot9ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(slot9PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(slot9Label, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slotTag9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slot9PLabel))
        );

        slot10Panel.setBackground(new java.awt.Color(191, 219, 174));
        slot10Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Slot10ColorTone.setBackground(new java.awt.Color(247, 148, 29));
        Slot10ColorTone.setPreferredSize(new java.awt.Dimension(50, 60));

        javax.swing.GroupLayout Slot10ColorToneLayout = new javax.swing.GroupLayout(Slot10ColorTone);
        Slot10ColorTone.setLayout(Slot10ColorToneLayout);
        Slot10ColorToneLayout.setHorizontalGroup(
            Slot10ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        Slot10ColorToneLayout.setVerticalGroup(
            Slot10ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        slot10Label.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot10Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot10Label.setText("Slot10");

        slot10PLabel.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot10PLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot10PLabel.setText("$Price10");
        slot10PLabel.setPreferredSize(new java.awt.Dimension(50, 20));

        slotTag10.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        slotTag10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slotTag10.setText("10");

        javax.swing.GroupLayout slot10PanelLayout = new javax.swing.GroupLayout(slot10Panel);
        slot10Panel.setLayout(slot10PanelLayout);
        slot10PanelLayout.setHorizontalGroup(
            slot10PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, slot10PanelLayout.createSequentialGroup()
                .addGroup(slot10PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(slotTag10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(slot10PanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(slot10Label, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
                    .addComponent(slot10PLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Slot10ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        slot10PanelLayout.setVerticalGroup(
            slot10PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(slot10PanelLayout.createSequentialGroup()
                .addComponent(slot10Label, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(slotTag10, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slot10PLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(Slot10ColorTone, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
        );

        slot11Panel.setBackground(new java.awt.Color(191, 219, 174));
        slot11Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        slot11ColorTone.setBackground(new java.awt.Color(247, 148, 29));

        javax.swing.GroupLayout slot11ColorToneLayout = new javax.swing.GroupLayout(slot11ColorTone);
        slot11ColorTone.setLayout(slot11ColorToneLayout);
        slot11ColorToneLayout.setHorizontalGroup(
            slot11ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        slot11ColorToneLayout.setVerticalGroup(
            slot11ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        slot11Label.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot11Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot11Label.setText("Slot11");

        slot11PLabel.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot11PLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot11PLabel.setText("$Price11");

        slotTag11.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        slotTag11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slotTag11.setText("11");

        javax.swing.GroupLayout slot11PanelLayout = new javax.swing.GroupLayout(slot11Panel);
        slot11Panel.setLayout(slot11PanelLayout);
        slot11PanelLayout.setHorizontalGroup(
            slot11PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, slot11PanelLayout.createSequentialGroup()
                .addGroup(slot11PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(slot11PanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(slot11PLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
                    .addComponent(slotTag11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot11Label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slot11ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        slot11PanelLayout.setVerticalGroup(
            slot11PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(slot11PanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(slot11ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(slot11PanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(slot11Label, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slotTag11)
                .addGap(1, 1, 1)
                .addComponent(slot11PLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        slot12Panel.setBackground(new java.awt.Color(191, 219, 174));
        slot12Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        slot12ColorTone.setBackground(new java.awt.Color(237, 27, 36));

        javax.swing.GroupLayout slot12ColorToneLayout = new javax.swing.GroupLayout(slot12ColorTone);
        slot12ColorTone.setLayout(slot12ColorToneLayout);
        slot12ColorToneLayout.setHorizontalGroup(
            slot12ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        slot12ColorToneLayout.setVerticalGroup(
            slot12ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        slot12Label.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot12Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot12Label.setText("Slot12");

        slot12PLabel.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot12PLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot12PLabel.setText("$Price12");

        slotTag12.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        slotTag12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slotTag12.setText("12");

        javax.swing.GroupLayout slot12PanelLayout = new javax.swing.GroupLayout(slot12Panel);
        slot12Panel.setLayout(slot12PanelLayout);
        slot12PanelLayout.setHorizontalGroup(
            slot12PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(slot12ColorTone, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(slot12Label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(slot12PLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
            .addComponent(slotTag12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        slot12PanelLayout.setVerticalGroup(
            slot12PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, slot12PanelLayout.createSequentialGroup()
                .addComponent(slot12PLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(slotTag12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addComponent(slot12Label, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slot12ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        slot13Panel.setBackground(new java.awt.Color(191, 219, 174));
        slot13Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        slot13ColorTone.setBackground(new java.awt.Color(237, 27, 36));

        javax.swing.GroupLayout slot13ColorToneLayout = new javax.swing.GroupLayout(slot13ColorTone);
        slot13ColorTone.setLayout(slot13ColorToneLayout);
        slot13ColorToneLayout.setHorizontalGroup(
            slot13ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        slot13ColorToneLayout.setVerticalGroup(
            slot13ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 49, Short.MAX_VALUE)
        );

        slot13Label.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot13Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot13Label.setText("Slot13");

        slot13PLabel.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot13PLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot13PLabel.setText("$Price13");

        slotTag13.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        slotTag13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slotTag13.setText("13");

        javax.swing.GroupLayout slot13PanelLayout = new javax.swing.GroupLayout(slot13Panel);
        slot13Panel.setLayout(slot13PanelLayout);
        slot13PanelLayout.setHorizontalGroup(
            slot13PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(slot13ColorTone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(slot13PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(slot13PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(slot13Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot13PLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(slotTag13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        slot13PanelLayout.setVerticalGroup(
            slot13PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, slot13PanelLayout.createSequentialGroup()
                .addComponent(slot13PLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(slotTag13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(slot13Label, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slot13ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        slot14Panel.setBackground(new java.awt.Color(191, 219, 174));
        slot14Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        slot14ColorTone.setBackground(new java.awt.Color(237, 27, 36));

        javax.swing.GroupLayout slot14ColorToneLayout = new javax.swing.GroupLayout(slot14ColorTone);
        slot14ColorTone.setLayout(slot14ColorToneLayout);
        slot14ColorToneLayout.setHorizontalGroup(
            slot14ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        slot14ColorToneLayout.setVerticalGroup(
            slot14ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        slot14Label.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot14Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot14Label.setText("Slot14");

        slot14PLabel.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot14PLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot14PLabel.setText("$Price14");

        slotTag14.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        slotTag14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slotTag14.setText("14");

        javax.swing.GroupLayout slot14PanelLayout = new javax.swing.GroupLayout(slot14Panel);
        slot14Panel.setLayout(slot14PanelLayout);
        slot14PanelLayout.setHorizontalGroup(
            slot14PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(slot14ColorTone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(slot14PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(slot14PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(slot14Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot14PLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(slotTag14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        slot14PanelLayout.setVerticalGroup(
            slot14PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, slot14PanelLayout.createSequentialGroup()
                .addComponent(slot14PLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(slotTag14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(slot14Label, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slot14ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        slot15Panel.setBackground(new java.awt.Color(191, 219, 174));
        slot15Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        slot15ColorTone.setBackground(new java.awt.Color(253, 241, 0));

        javax.swing.GroupLayout slot15ColorToneLayout = new javax.swing.GroupLayout(slot15ColorTone);
        slot15ColorTone.setLayout(slot15ColorToneLayout);
        slot15ColorToneLayout.setHorizontalGroup(
            slot15ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );
        slot15ColorToneLayout.setVerticalGroup(
            slot15ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        slot15Label.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot15Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot15Label.setText("Slot15");

        slot15PLabel.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot15PLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot15PLabel.setText("$Price15");

        slotTag15.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        slotTag15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slotTag15.setText("15");

        javax.swing.GroupLayout slot15PanelLayout = new javax.swing.GroupLayout(slot15Panel);
        slot15Panel.setLayout(slot15PanelLayout);
        slot15PanelLayout.setHorizontalGroup(
            slot15PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(slot15ColorTone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(slot15PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(slot15PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(slot15Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot15PLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(slotTag15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        slot15PanelLayout.setVerticalGroup(
            slot15PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, slot15PanelLayout.createSequentialGroup()
                .addComponent(slot15PLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slotTag15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(slot15Label, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slot15ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        slot16Panel.setBackground(new java.awt.Color(191, 219, 174));
        slot16Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        slot16ColorTone.setBackground(new java.awt.Color(253, 241, 0));

        javax.swing.GroupLayout slot16ColorToneLayout = new javax.swing.GroupLayout(slot16ColorTone);
        slot16ColorTone.setLayout(slot16ColorToneLayout);
        slot16ColorToneLayout.setHorizontalGroup(
            slot16ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        slot16ColorToneLayout.setVerticalGroup(
            slot16ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        slot16Label.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot16Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot16Label.setText("Slot16");

        slot16PLabel.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot16PLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot16PLabel.setText("$Price16");

        slotTag16.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        slotTag16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slotTag16.setText("16");

        javax.swing.GroupLayout slot16PanelLayout = new javax.swing.GroupLayout(slot16Panel);
        slot16Panel.setLayout(slot16PanelLayout);
        slot16PanelLayout.setHorizontalGroup(
            slot16PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(slot16ColorTone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(slot16PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(slot16PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(slot16Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot16PLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(slotTag16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        slot16PanelLayout.setVerticalGroup(
            slot16PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, slot16PanelLayout.createSequentialGroup()
                .addComponent(slot16PLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slotTag16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(slot16Label, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slot16ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        slot17Panel.setBackground(new java.awt.Color(191, 219, 174));
        slot17Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        slot17ColorTone.setBackground(new java.awt.Color(253, 241, 0));

        javax.swing.GroupLayout slot17ColorToneLayout = new javax.swing.GroupLayout(slot17ColorTone);
        slot17ColorTone.setLayout(slot17ColorToneLayout);
        slot17ColorToneLayout.setHorizontalGroup(
            slot17ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        slot17ColorToneLayout.setVerticalGroup(
            slot17ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        slot17Label.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot17Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot17Label.setText("Slot17");

        slot17PLabel.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot17PLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot17PLabel.setText("$Price17");

        slotTag17.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        slotTag17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slotTag17.setText("17");

        javax.swing.GroupLayout slot17PanelLayout = new javax.swing.GroupLayout(slot17Panel);
        slot17Panel.setLayout(slot17PanelLayout);
        slot17PanelLayout.setHorizontalGroup(
            slot17PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(slot17ColorTone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(slot17Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(slot17PLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
            .addComponent(slotTag17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        slot17PanelLayout.setVerticalGroup(
            slot17PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, slot17PanelLayout.createSequentialGroup()
                .addComponent(slot17PLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slotTag17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(slot17Label, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slot17ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        slot18Panel.setBackground(new java.awt.Color(191, 219, 174));
        slot18Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        slot18ColorTone.setBackground(new java.awt.Color(31, 176, 90));

        javax.swing.GroupLayout slot18ColorToneLayout = new javax.swing.GroupLayout(slot18ColorTone);
        slot18ColorTone.setLayout(slot18ColorToneLayout);
        slot18ColorToneLayout.setHorizontalGroup(
            slot18ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        slot18ColorToneLayout.setVerticalGroup(
            slot18ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        slot18Label.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot18Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot18Label.setText("Slot18");

        slot18PLabel.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot18PLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot18PLabel.setText("$Price18");

        slotTag18.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        slotTag18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slotTag18.setText("18");

        javax.swing.GroupLayout slot18PanelLayout = new javax.swing.GroupLayout(slot18Panel);
        slot18Panel.setLayout(slot18PanelLayout);
        slot18PanelLayout.setHorizontalGroup(
            slot18PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(slot18PanelLayout.createSequentialGroup()
                .addComponent(slot18ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(slot18PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(slot18PanelLayout.createSequentialGroup()
                        .addGroup(slot18PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(slot18Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(slot18PLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE))
                        .addContainerGap())
                    .addComponent(slotTag18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        slot18PanelLayout.setVerticalGroup(
            slot18PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, slot18PanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(slot18PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(slot18PanelLayout.createSequentialGroup()
                        .addComponent(slot18Label, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(slotTag18, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(slot18PLabel))
                    .addComponent(slot18ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        slot19Panel.setBackground(new java.awt.Color(191, 219, 174));
        slot19Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        slot19ColorTone.setBackground(new java.awt.Color(31, 176, 90));

        javax.swing.GroupLayout slot19ColorToneLayout = new javax.swing.GroupLayout(slot19ColorTone);
        slot19ColorTone.setLayout(slot19ColorToneLayout);
        slot19ColorToneLayout.setHorizontalGroup(
            slot19ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        slot19ColorToneLayout.setVerticalGroup(
            slot19ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        slot19Label.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot19Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot19Label.setText("Slot19");

        slot19PLabel.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot19PLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot19PLabel.setText("$Price19");

        slotTag19.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        slotTag19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slotTag19.setText("19");

        javax.swing.GroupLayout slot19PanelLayout = new javax.swing.GroupLayout(slot19Panel);
        slot19Panel.setLayout(slot19PanelLayout);
        slot19PanelLayout.setHorizontalGroup(
            slot19PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(slot19PanelLayout.createSequentialGroup()
                .addComponent(slot19ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(slot19PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(slot19Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot19PLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                    .addComponent(slotTag19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        slot19PanelLayout.setVerticalGroup(
            slot19PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, slot19PanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(slot19PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(slot19PanelLayout.createSequentialGroup()
                        .addComponent(slot19Label, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(slotTag19, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(slot19PLabel)
                        .addContainerGap())
                    .addComponent(slot19ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        slot20Panel.setBackground(new java.awt.Color(191, 219, 174));
        slot20Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        slot20ColorTone.setBackground(new java.awt.Color(31, 176, 90));

        javax.swing.GroupLayout slot20ColorToneLayout = new javax.swing.GroupLayout(slot20ColorTone);
        slot20ColorTone.setLayout(slot20ColorToneLayout);
        slot20ColorToneLayout.setHorizontalGroup(
            slot20ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        slot20ColorToneLayout.setVerticalGroup(
            slot20ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        slot20Label.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot20Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot20Label.setText("Slot20");

        slot20PLabel.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot20PLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot20PLabel.setText("$Price20");

        slotTag20.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        slotTag20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slotTag20.setText("20");

        javax.swing.GroupLayout slot20PanelLayout = new javax.swing.GroupLayout(slot20Panel);
        slot20Panel.setLayout(slot20PanelLayout);
        slot20PanelLayout.setHorizontalGroup(
            slot20PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(slot20PanelLayout.createSequentialGroup()
                .addComponent(slot20ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(slot20PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(slot20Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot20PLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                    .addComponent(slotTag20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        slot20PanelLayout.setVerticalGroup(
            slot20PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(slot20PanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(slot20ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(slot20PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(slot20Label, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slotTag20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slot20PLabel))
        );

        emptySlotPanel1.setBackground(new java.awt.Color(191, 219, 174));
        emptySlotPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        emptySlot1ColorTone.setBackground(new java.awt.Color(191, 219, 174));

        javax.swing.GroupLayout emptySlot1ColorToneLayout = new javax.swing.GroupLayout(emptySlot1ColorTone);
        emptySlot1ColorTone.setLayout(emptySlot1ColorToneLayout);
        emptySlot1ColorToneLayout.setHorizontalGroup(
            emptySlot1ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        emptySlot1ColorToneLayout.setVerticalGroup(
            emptySlot1ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        emptySlotLabel1.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        emptySlotLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout emptySlotPanel1Layout = new javax.swing.GroupLayout(emptySlotPanel1);
        emptySlotPanel1.setLayout(emptySlotPanel1Layout);
        emptySlotPanel1Layout.setHorizontalGroup(
            emptySlotPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(emptySlotPanel1Layout.createSequentialGroup()
                .addComponent(emptySlot1ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(emptySlotLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        emptySlotPanel1Layout.setVerticalGroup(
            emptySlotPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, emptySlotPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(emptySlot1ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(emptySlotPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(emptySlotLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        slot21Panel.setBackground(new java.awt.Color(191, 219, 174));
        slot21Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        slot21ColorTone.setBackground(new java.awt.Color(0, 114, 187));

        javax.swing.GroupLayout slot21ColorToneLayout = new javax.swing.GroupLayout(slot21ColorTone);
        slot21ColorTone.setLayout(slot21ColorToneLayout);
        slot21ColorToneLayout.setHorizontalGroup(
            slot21ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        slot21ColorToneLayout.setVerticalGroup(
            slot21ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        slot21Label.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot21Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot21Label.setText("Slot21");

        slot21PLabel.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot21PLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot21PLabel.setText("$Price21");

        slotTag21.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        slotTag21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slotTag21.setText("21");

        javax.swing.GroupLayout slot21PanelLayout = new javax.swing.GroupLayout(slot21Panel);
        slot21Panel.setLayout(slot21PanelLayout);
        slot21PanelLayout.setHorizontalGroup(
            slot21PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(slot21PanelLayout.createSequentialGroup()
                .addComponent(slot21ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(slot21PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(slot21Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot21PLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                    .addComponent(slotTag21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        slot21PanelLayout.setVerticalGroup(
            slot21PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(slot21PanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(slot21ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(slot21PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(slot21Label, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slotTag21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slot21PLabel))
        );

        slot22Panel.setBackground(new java.awt.Color(191, 219, 174));
        slot22Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        slot22ColorTone.setBackground(new java.awt.Color(0, 114, 187));

        javax.swing.GroupLayout slot22ColorToneLayout = new javax.swing.GroupLayout(slot22ColorTone);
        slot22ColorTone.setLayout(slot22ColorToneLayout);
        slot22ColorToneLayout.setHorizontalGroup(
            slot22ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        slot22ColorToneLayout.setVerticalGroup(
            slot22ColorToneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        slot22Label.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot22Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot22Label.setText("Slot22");

        slot22PLabel.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        slot22PLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slot22PLabel.setText("$Price22");

        slotTag22.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        slotTag22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slotTag22.setText("22");

        javax.swing.GroupLayout slot22PanelLayout = new javax.swing.GroupLayout(slot22Panel);
        slot22Panel.setLayout(slot22PanelLayout);
        slot22PanelLayout.setHorizontalGroup(
            slot22PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(slot22PanelLayout.createSequentialGroup()
                .addComponent(slot22ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(slot22PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(slot22Label, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                    .addGroup(slot22PanelLayout.createSequentialGroup()
                        .addComponent(slot22PLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(slotTag22, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        slot22PanelLayout.setVerticalGroup(
            slot22PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(slot22PanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(slot22ColorTone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(slot22PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(slot22Label, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slotTag22, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(slot22PLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        informationPanel.setBackground(new java.awt.Color(191, 219, 174));

        turnLabel.setFont(new java.awt.Font("新細明體", 1, 18)); // NOI18N
        turnLabel.setText("Turn:");

        turnDisplayLabel.setFont(new java.awt.Font("新細明體", 1, 18)); // NOI18N
        turnDisplayLabel.setText("Player 1");

        rollDiceBT.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        rollDiceBT.setText("Roll Dice");
        rollDiceBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rollDiceBTActionPerformed(evt);
            }
        });

        DiceLabel.setFont(new java.awt.Font("新細明體", 1, 18)); // NOI18N
        DiceLabel.setText("Dice:");

        DiceDisplayLabel.setFont(new java.awt.Font("新細明體", 1, 18)); // NOI18N
        DiceDisplayLabel.setText("0");

        buyLandBT.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        buyLandBT.setText("Buy Land");
        buyLandBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buyLandBTActionPerformed(evt);
            }
        });

        payRentBT.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        payRentBT.setText("Pay Rent");
        payRentBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                payRentBTActionPerformed(evt);
            }
        });

        passTurnBT.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        passTurnBT.setText("Pass Turn");
        passTurnBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passTurnBTActionPerformed(evt);
            }
        });

        newGameBT.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        newGameBT.setText("New Game");
        newGameBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGameBTActionPerformed(evt);
            }
        });

        editGameBT.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        editGameBT.setText("Edit Game");

        javax.swing.GroupLayout informationPanelLayout = new javax.swing.GroupLayout(informationPanel);
        informationPanel.setLayout(informationPanelLayout);
        informationPanelLayout.setHorizontalGroup(
            informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(passTurnBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rollDiceBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(informationPanelLayout.createSequentialGroup()
                                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(DiceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                                    .addComponent(turnLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(turnDisplayLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(DiceDisplayLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(informationPanelLayout.createSequentialGroup()
                                .addComponent(buyLandBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(payRentBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addComponent(editGameBT, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(newGameBT, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE))))
        );
        informationPanelLayout.setVerticalGroup(
            informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, informationPanelLayout.createSequentialGroup()
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(newGameBT, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(editGameBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(turnLabel)
                    .addComponent(turnDisplayLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DiceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DiceDisplayLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rollDiceBT, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(payRentBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buyLandBT, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passTurnBT, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        playersInfoPanel.setBackground(new java.awt.Color(191, 219, 174));

        player1InfoPanel.setBackground(new java.awt.Color(191, 219, 174));
        player1InfoPanel.setPreferredSize(new java.awt.Dimension(0, 150));

        player1Label.setFont(new java.awt.Font("新細明體", 1, 18)); // NOI18N
        player1Label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        player1Label.setText("Player 1:");
        player1Label.setPreferredSize(new java.awt.Dimension(80, 30));

        p1BalanceLabel.setFont(new java.awt.Font("新細明體", 1, 12)); // NOI18N
        p1BalanceLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        p1BalanceLabel.setText("Balance:");
        p1BalanceLabel.setPreferredSize(new java.awt.Dimension(50, 30));

        p1PositionLabel.setFont(new java.awt.Font("新細明體", 1, 12)); // NOI18N
        p1PositionLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        p1PositionLabel.setText("Position:");
        p1PositionLabel.setPreferredSize(new java.awt.Dimension(80, 30));

        p1StatusLabel.setFont(new java.awt.Font("新細明體", 1, 12)); // NOI18N
        p1StatusLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        p1StatusLabel.setText("Status:");
        p1StatusLabel.setPreferredSize(new java.awt.Dimension(80, 30));

        p1BalanceAmountLabel.setFont(new java.awt.Font("新細明體", 1, 12)); // NOI18N
        p1BalanceAmountLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        p1BalanceAmountLabel.setText("2000");

        p1PositionNumLabel.setFont(new java.awt.Font("新細明體", 1, 12)); // NOI18N
        p1PositionNumLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        p1PositionNumLabel.setText("0");

        p1StausStatueLabel.setFont(new java.awt.Font("新細明體", 1, 12)); // NOI18N
        p1StausStatueLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        p1StausStatueLabel.setText("Active");

        p1LandsOwnershipLabel.setFont(new java.awt.Font("新細明體", 1, 12)); // NOI18N
        p1LandsOwnershipLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        p1LandsOwnershipLabel.setText("Land(s) Owned:");

        p1LandsOwnershipDisplayLabel.setFont(new java.awt.Font("新細明體", 1, 12)); // NOI18N

        javax.swing.GroupLayout player1InfoPanelLayout = new javax.swing.GroupLayout(player1InfoPanel);
        player1InfoPanel.setLayout(player1InfoPanelLayout);
        player1InfoPanelLayout.setHorizontalGroup(
            player1InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(player1InfoPanelLayout.createSequentialGroup()
                .addComponent(p1BalanceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(p1BalanceAmountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(p1PositionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(p1PositionNumLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(p1StatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(p1StausStatueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(player1Label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(p1LandsOwnershipLabel)
            .addComponent(p1LandsOwnershipDisplayLabel)
        );
        player1InfoPanelLayout.setVerticalGroup(
            player1InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(player1InfoPanelLayout.createSequentialGroup()
                .addComponent(player1Label, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(player1InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(p1BalanceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(p1BalanceAmountLabel)
                    .addComponent(p1PositionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(p1PositionNumLabel)
                    .addComponent(p1StatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(p1StausStatueLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(p1LandsOwnershipLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(p1LandsOwnershipDisplayLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addGap(22, 22, 22))
        );

        player2InfoPanel.setBackground(new java.awt.Color(191, 219, 174));
        player2InfoPanel.setPreferredSize(new java.awt.Dimension(0, 150));
        player2InfoPanel.setRequestFocusEnabled(false);

        p2LandsOwnershipDisplayLabel.setFont(new java.awt.Font("新細明體", 1, 12)); // NOI18N

        p2LandsOwnershipLabel.setFont(new java.awt.Font("新細明體", 1, 12)); // NOI18N
        p2LandsOwnershipLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        p2LandsOwnershipLabel.setText("Land(s) Owned:");

        p2BalanceLabel.setFont(new java.awt.Font("新細明體", 1, 12)); // NOI18N
        p2BalanceLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        p2BalanceLabel.setText("Balance:");
        p2BalanceLabel.setPreferredSize(new java.awt.Dimension(50, 30));

        p2BalanceAmountLabel.setFont(new java.awt.Font("新細明體", 1, 12)); // NOI18N
        p2BalanceAmountLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        p2BalanceAmountLabel.setText("2000");

        p2PositionLabel.setFont(new java.awt.Font("新細明體", 1, 12)); // NOI18N
        p2PositionLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        p2PositionLabel.setText("Position:");
        p2PositionLabel.setPreferredSize(new java.awt.Dimension(80, 30));

        p2PositionNumLabel.setFont(new java.awt.Font("新細明體", 1, 12)); // NOI18N
        p2PositionNumLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        p2PositionNumLabel.setText("0");

        p2StatusLabel.setFont(new java.awt.Font("新細明體", 1, 12)); // NOI18N
        p2StatusLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        p2StatusLabel.setText("Status:");
        p2StatusLabel.setPreferredSize(new java.awt.Dimension(80, 30));

        p2StausStatueLabel.setFont(new java.awt.Font("新細明體", 1, 12)); // NOI18N
        p2StausStatueLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        p2StausStatueLabel.setText("Active");

        player2Label.setFont(new java.awt.Font("新細明體", 1, 18)); // NOI18N
        player2Label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        player2Label.setText("Player 2:");
        player2Label.setPreferredSize(new java.awt.Dimension(80, 30));

        javax.swing.GroupLayout player2InfoPanelLayout = new javax.swing.GroupLayout(player2InfoPanel);
        player2InfoPanel.setLayout(player2InfoPanelLayout);
        player2InfoPanelLayout.setHorizontalGroup(
            player2InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(player2InfoPanelLayout.createSequentialGroup()
                .addGroup(player2InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(player2InfoPanelLayout.createSequentialGroup()
                        .addComponent(p2BalanceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(p2BalanceAmountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(p2PositionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(p2PositionNumLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(p2StatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(p2StausStatueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(player2Label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(p2LandsOwnershipLabel)
                    .addComponent(p2LandsOwnershipDisplayLabel))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        player2InfoPanelLayout.setVerticalGroup(
            player2InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(player2InfoPanelLayout.createSequentialGroup()
                .addComponent(player2Label, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(player2InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(p2BalanceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(p2BalanceAmountLabel)
                    .addComponent(p2PositionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(p2PositionNumLabel)
                    .addComponent(p2StatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(p2StausStatueLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(p2LandsOwnershipLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(p2LandsOwnershipDisplayLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addGap(22, 22, 22))
        );

        player3InfoPanel.setBackground(new java.awt.Color(191, 219, 174));
        player3InfoPanel.setPreferredSize(new java.awt.Dimension(0, 150));

        p3LandsOwnershipDisplayLabel.setFont(new java.awt.Font("新細明體", 1, 12)); // NOI18N

        p3LandsOwnershipLabel.setFont(new java.awt.Font("新細明體", 1, 12)); // NOI18N
        p3LandsOwnershipLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        p3LandsOwnershipLabel.setText("Land(s) Owned:");

        p3BalanceLabel.setFont(new java.awt.Font("新細明體", 1, 12)); // NOI18N
        p3BalanceLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        p3BalanceLabel.setText("Balance:");
        p3BalanceLabel.setPreferredSize(new java.awt.Dimension(50, 30));

        p3BalanceAmountLabel.setFont(new java.awt.Font("新細明體", 1, 12)); // NOI18N
        p3BalanceAmountLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        p3BalanceAmountLabel.setText("2000");

        p3PositionLabel.setFont(new java.awt.Font("新細明體", 1, 12)); // NOI18N
        p3PositionLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        p3PositionLabel.setText("Position:");
        p3PositionLabel.setPreferredSize(new java.awt.Dimension(80, 30));

        p3PositionNumLabel.setFont(new java.awt.Font("新細明體", 1, 12)); // NOI18N
        p3PositionNumLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        p3PositionNumLabel.setText("0");

        p3StatusLabel.setFont(new java.awt.Font("新細明體", 1, 12)); // NOI18N
        p3StatusLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        p3StatusLabel.setText("Status:");
        p3StatusLabel.setPreferredSize(new java.awt.Dimension(80, 30));

        p3StausStatueLabel.setFont(new java.awt.Font("新細明體", 1, 12)); // NOI18N
        p3StausStatueLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        p3StausStatueLabel.setText("Active");

        player3Label.setFont(new java.awt.Font("新細明體", 1, 18)); // NOI18N
        player3Label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        player3Label.setText("Player 3:");
        player3Label.setPreferredSize(new java.awt.Dimension(80, 30));

        javax.swing.GroupLayout player3InfoPanelLayout = new javax.swing.GroupLayout(player3InfoPanel);
        player3InfoPanel.setLayout(player3InfoPanelLayout);
        player3InfoPanelLayout.setHorizontalGroup(
            player3InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(player3InfoPanelLayout.createSequentialGroup()
                .addGroup(player3InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(player3InfoPanelLayout.createSequentialGroup()
                        .addComponent(p3BalanceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(p3BalanceAmountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(p3PositionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(p3PositionNumLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(p3StatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(p3StausStatueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(player3Label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(p3LandsOwnershipLabel)
                    .addComponent(p3LandsOwnershipDisplayLabel))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        player3InfoPanelLayout.setVerticalGroup(
            player3InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(player3InfoPanelLayout.createSequentialGroup()
                .addComponent(player3Label, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(player3InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(p3BalanceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(p3BalanceAmountLabel)
                    .addComponent(p3PositionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(p3PositionNumLabel)
                    .addComponent(p3StatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(p3StausStatueLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(p3LandsOwnershipLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(p3LandsOwnershipDisplayLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addGap(22, 22, 22))
        );

        player4InfoPanel.setBackground(new java.awt.Color(191, 219, 174));
        player4InfoPanel.setPreferredSize(new java.awt.Dimension(0, 150));

        p4LandsOwnershipDisplayLabel.setFont(new java.awt.Font("新細明體", 1, 12)); // NOI18N

        p4LandsOwnershipLabel.setFont(new java.awt.Font("新細明體", 1, 12)); // NOI18N
        p4LandsOwnershipLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        p4LandsOwnershipLabel.setText("Land(s) Owned:");

        p4BalanceLabel.setFont(new java.awt.Font("新細明體", 1, 12)); // NOI18N
        p4BalanceLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        p4BalanceLabel.setText("Balance:");
        p4BalanceLabel.setPreferredSize(new java.awt.Dimension(50, 30));

        p4BalanceAmountLabel.setFont(new java.awt.Font("新細明體", 1, 12)); // NOI18N
        p4BalanceAmountLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        p4BalanceAmountLabel.setText("2000");

        p4PositionLabel.setFont(new java.awt.Font("新細明體", 1, 12)); // NOI18N
        p4PositionLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        p4PositionLabel.setText("Position:");
        p4PositionLabel.setPreferredSize(new java.awt.Dimension(80, 30));

        p4PositionNumLabel.setFont(new java.awt.Font("新細明體", 1, 12)); // NOI18N
        p4PositionNumLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        p4PositionNumLabel.setText("0");

        p4StatusLabel.setFont(new java.awt.Font("新細明體", 1, 12)); // NOI18N
        p4StatusLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        p4StatusLabel.setText("Status:");
        p4StatusLabel.setPreferredSize(new java.awt.Dimension(80, 30));

        p4StausStatueLabel.setFont(new java.awt.Font("新細明體", 1, 12)); // NOI18N
        p4StausStatueLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        p4StausStatueLabel.setText("Active");

        player4Label.setFont(new java.awt.Font("新細明體", 1, 18)); // NOI18N
        player4Label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        player4Label.setText("Player 4:");
        player4Label.setPreferredSize(new java.awt.Dimension(80, 30));

        javax.swing.GroupLayout player4InfoPanelLayout = new javax.swing.GroupLayout(player4InfoPanel);
        player4InfoPanel.setLayout(player4InfoPanelLayout);
        player4InfoPanelLayout.setHorizontalGroup(
            player4InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(player4InfoPanelLayout.createSequentialGroup()
                .addGroup(player4InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(player4InfoPanelLayout.createSequentialGroup()
                        .addComponent(p4BalanceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(p4BalanceAmountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(p4PositionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(p4PositionNumLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(p4StatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(p4StausStatueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(player4Label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(p4LandsOwnershipLabel)
                    .addComponent(p4LandsOwnershipDisplayLabel))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        player4InfoPanelLayout.setVerticalGroup(
            player4InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(player4InfoPanelLayout.createSequentialGroup()
                .addComponent(player4Label, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(player4InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(p4BalanceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(p4BalanceAmountLabel)
                    .addComponent(p4PositionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(p4PositionNumLabel)
                    .addComponent(p4StatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(p4StausStatueLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(p4LandsOwnershipLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(p4LandsOwnershipDisplayLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout playersInfoPanelLayout = new javax.swing.GroupLayout(playersInfoPanel);
        playersInfoPanel.setLayout(playersInfoPanelLayout);
        playersInfoPanelLayout.setHorizontalGroup(
            playersInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(player2InfoPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
            .addComponent(player3InfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
            .addComponent(player4InfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
            .addGroup(playersInfoPanelLayout.createSequentialGroup()
                .addComponent(player1InfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        playersInfoPanelLayout.setVerticalGroup(
            playersInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(playersInfoPanelLayout.createSequentialGroup()
                .addComponent(player1InfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(player2InfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(player3InfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(player4InfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout boardLayout = new javax.swing.GroupLayout(board);
        board.setLayout(boardLayout);
        boardLayout.setHorizontalGroup(
            boardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(boardLayout.createSequentialGroup()
                .addGroup(boardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(slot11Panel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot10Panel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot9Panel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot8Panel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot7Panel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot6Panel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cornerLBPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                    .addComponent(cornerLHPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(boardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(boardLayout.createSequentialGroup()
                        .addGroup(boardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(slot5Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(slot12Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(boardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(boardLayout.createSequentialGroup()
                                .addComponent(slot4Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(slot3Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(emptySlotPanel0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(slot2Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(slot1Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(boardLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(slot13Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(slot14Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(slot15Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(slot16Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(slot17Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(boardLayout.createSequentialGroup()
                        .addComponent(playersInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(informationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(6, 6, 6)
                .addGroup(boardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cornerRHPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                    .addComponent(slot18Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot20Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(emptySlotPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot21Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot22Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot0Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot19Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        boardLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {slot12Panel, slot14Panel});

        boardLayout.setVerticalGroup(
            boardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, boardLayout.createSequentialGroup()
                .addGroup(boardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cornerRHPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                    .addComponent(cornerLHPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot12Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot13Panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot14Panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot15Panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot16Panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot17Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(boardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(boardLayout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(slot10Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(slot9Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(slot8Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(slot7Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(slot6Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(boardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(informationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(playersInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(slot11Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(boardLayout.createSequentialGroup()
                        .addComponent(slot18Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(slot19Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(slot20Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(emptySlotPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(slot21Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(slot22Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(boardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(slot5Panel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot4Panel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot3Panel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(emptySlotPanel0, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot2Panel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot1Panel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slot0Panel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cornerLBPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(board, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(board, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rollDiceBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rollDiceBTActionPerformed
        // TODO add your handling code here:
        rollDice();
        updatePlayerPosition();
        update();
        rollDiceBT.setEnabled(false);
        checkPositionToBeMoved();

    }//GEN-LAST:event_rollDiceBTActionPerformed

    private void passTurnBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passTurnBTActionPerformed
        // TODO add your handling code here:
        passTurn();
        rollDiceBT.setEnabled(true);
        buyLandBT.setEnabled(false);
        payRentBT.setEnabled(false);
        passTurnBT.setEnabled(false);
    }//GEN-LAST:event_passTurnBTActionPerformed

    private void buyLandBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buyLandBTActionPerformed
        buyLand();
        update();
        buyLandBT.setEnabled(false);
    }//GEN-LAST:event_buyLandBTActionPerformed

    private void payRentBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payRentBTActionPerformed
        // TODO add your handling code here:
        payRentalFee();
        update();
        payRentBT.setEnabled(false);
        passTurnBT.setEnabled(true);
    }//GEN-LAST:event_payRentBTActionPerformed

    private void newGameBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGameBTActionPerformed
        // TODO add your handling code here:
        newGame();
    }//GEN-LAST:event_newGameBTActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(MonopolyView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MonopolyView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MonopolyView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MonopolyView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MonopolyView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel DiceDisplayLabel;
    private javax.swing.JLabel DiceLabel;
    private javax.swing.JPanel Slot10ColorTone;
    private javax.swing.JPanel board;
    private javax.swing.JButton buyLandBT;
    private javax.swing.JLabel collect2000Label;
    private javax.swing.JPanel cornerLBPanel;
    private javax.swing.JPanel cornerLHPanel;
    private javax.swing.JPanel cornerRHPanel;
    private javax.swing.JButton editGameBT;
    private javax.swing.JPanel emptySlot0ColorTone;
    private javax.swing.JPanel emptySlot1ColorTone;
    private javax.swing.JLabel emptySlotLabel0;
    private javax.swing.JLabel emptySlotLabel1;
    private javax.swing.JPanel emptySlotPanel0;
    private javax.swing.JPanel emptySlotPanel1;
    private javax.swing.JPanel informationPanel;
    private javax.swing.JButton newGameBT;
    private javax.swing.JLabel p1BalanceAmountLabel;
    private javax.swing.JLabel p1BalanceLabel;
    private javax.swing.JLabel p1LandsOwnershipDisplayLabel;
    private javax.swing.JLabel p1LandsOwnershipLabel;
    private javax.swing.JLabel p1PositionLabel;
    private javax.swing.JLabel p1PositionNumLabel;
    private javax.swing.JLabel p1StatusLabel;
    private javax.swing.JLabel p1StausStatueLabel;
    private javax.swing.JLabel p2BalanceAmountLabel;
    private javax.swing.JLabel p2BalanceLabel;
    private javax.swing.JLabel p2LandsOwnershipDisplayLabel;
    private javax.swing.JLabel p2LandsOwnershipLabel;
    private javax.swing.JLabel p2PositionLabel;
    private javax.swing.JLabel p2PositionNumLabel;
    private javax.swing.JLabel p2StatusLabel;
    private javax.swing.JLabel p2StausStatueLabel;
    private javax.swing.JLabel p3BalanceAmountLabel;
    private javax.swing.JLabel p3BalanceLabel;
    private javax.swing.JLabel p3LandsOwnershipDisplayLabel;
    private javax.swing.JLabel p3LandsOwnershipLabel;
    private javax.swing.JLabel p3PositionLabel;
    private javax.swing.JLabel p3PositionNumLabel;
    private javax.swing.JLabel p3StatusLabel;
    private javax.swing.JLabel p3StausStatueLabel;
    private javax.swing.JLabel p4BalanceAmountLabel;
    private javax.swing.JLabel p4BalanceLabel;
    private javax.swing.JLabel p4LandsOwnershipDisplayLabel;
    private javax.swing.JLabel p4LandsOwnershipLabel;
    private javax.swing.JLabel p4PositionLabel;
    private javax.swing.JLabel p4PositionNumLabel;
    private javax.swing.JLabel p4StatusLabel;
    private javax.swing.JLabel p4StausStatueLabel;
    private javax.swing.JButton passTurnBT;
    private javax.swing.JButton payRentBT;
    private javax.swing.JPanel player1InfoPanel;
    private javax.swing.JLabel player1Label;
    private javax.swing.JPanel player2InfoPanel;
    private javax.swing.JLabel player2Label;
    private javax.swing.JPanel player3InfoPanel;
    private javax.swing.JLabel player3Label;
    private javax.swing.JPanel player4InfoPanel;
    private javax.swing.JLabel player4Label;
    private javax.swing.JPanel playersInfoPanel;
    private javax.swing.JButton rollDiceBT;
    private javax.swing.JLabel slot0Label;
    private javax.swing.JPanel slot0Panel;
    private javax.swing.JLabel slot10Label;
    private javax.swing.JLabel slot10PLabel;
    private javax.swing.JPanel slot10Panel;
    private javax.swing.JPanel slot11ColorTone;
    private javax.swing.JLabel slot11Label;
    private javax.swing.JLabel slot11PLabel;
    private javax.swing.JPanel slot11Panel;
    private javax.swing.JPanel slot12ColorTone;
    private javax.swing.JLabel slot12Label;
    private javax.swing.JLabel slot12PLabel;
    private javax.swing.JPanel slot12Panel;
    private javax.swing.JPanel slot13ColorTone;
    private javax.swing.JLabel slot13Label;
    private javax.swing.JLabel slot13PLabel;
    private javax.swing.JPanel slot13Panel;
    private javax.swing.JPanel slot14ColorTone;
    private javax.swing.JLabel slot14Label;
    private javax.swing.JLabel slot14PLabel;
    private javax.swing.JPanel slot14Panel;
    private javax.swing.JPanel slot15ColorTone;
    private javax.swing.JLabel slot15Label;
    private javax.swing.JLabel slot15PLabel;
    private javax.swing.JPanel slot15Panel;
    private javax.swing.JPanel slot16ColorTone;
    private javax.swing.JLabel slot16Label;
    private javax.swing.JLabel slot16PLabel;
    private javax.swing.JPanel slot16Panel;
    private javax.swing.JPanel slot17ColorTone;
    private javax.swing.JLabel slot17Label;
    private javax.swing.JLabel slot17PLabel;
    private javax.swing.JPanel slot17Panel;
    private javax.swing.JPanel slot18ColorTone;
    private javax.swing.JLabel slot18Label;
    private javax.swing.JLabel slot18PLabel;
    private javax.swing.JPanel slot18Panel;
    private javax.swing.JPanel slot19ColorTone;
    private javax.swing.JLabel slot19Label;
    private javax.swing.JLabel slot19PLabel;
    private javax.swing.JPanel slot19Panel;
    private javax.swing.JPanel slot1ColorTone;
    private javax.swing.JLabel slot1Label;
    private javax.swing.JLabel slot1PLabel;
    private javax.swing.JPanel slot1Panel;
    private javax.swing.JPanel slot20ColorTone;
    private javax.swing.JLabel slot20Label;
    private javax.swing.JLabel slot20PLabel;
    private javax.swing.JPanel slot20Panel;
    private javax.swing.JPanel slot21ColorTone;
    private javax.swing.JLabel slot21Label;
    private javax.swing.JLabel slot21PLabel;
    private javax.swing.JPanel slot21Panel;
    private javax.swing.JPanel slot22ColorTone;
    private javax.swing.JLabel slot22Label;
    private javax.swing.JLabel slot22PLabel;
    private javax.swing.JPanel slot22Panel;
    private javax.swing.JPanel slot2ColorTone;
    private javax.swing.JLabel slot2Label;
    private javax.swing.JLabel slot2PLabel;
    private javax.swing.JPanel slot2Panel;
    private javax.swing.JPanel slot3ColorTone;
    private javax.swing.JLabel slot3Label;
    private javax.swing.JLabel slot3PLabel;
    private javax.swing.JPanel slot3Panel;
    private javax.swing.JPanel slot4ColorTone;
    private javax.swing.JLabel slot4Label;
    private javax.swing.JLabel slot4PLabel;
    private javax.swing.JPanel slot4Panel;
    private javax.swing.JPanel slot5ColorTone;
    private javax.swing.JLabel slot5Label;
    private javax.swing.JLabel slot5PLabel;
    private javax.swing.JPanel slot5Panel;
    private javax.swing.JPanel slot6ColorTone;
    private javax.swing.JLabel slot6Label;
    private javax.swing.JLabel slot6PLabel;
    private javax.swing.JPanel slot6Panel;
    private javax.swing.JPanel slot7ColorTone;
    private javax.swing.JLabel slot7Label;
    private javax.swing.JLabel slot7PLabel;
    private javax.swing.JPanel slot7Panel;
    private javax.swing.JPanel slot8ColorTone;
    private javax.swing.JLabel slot8Label;
    private javax.swing.JLabel slot8PLabel;
    private javax.swing.JPanel slot8Panel;
    private javax.swing.JPanel slot9ColorTone;
    private javax.swing.JLabel slot9Label;
    private javax.swing.JLabel slot9PLabel;
    private javax.swing.JPanel slot9Panel;
    private javax.swing.JLabel slotTag1;
    private javax.swing.JLabel slotTag10;
    private javax.swing.JLabel slotTag11;
    private javax.swing.JLabel slotTag12;
    private javax.swing.JLabel slotTag13;
    private javax.swing.JLabel slotTag14;
    private javax.swing.JLabel slotTag15;
    private javax.swing.JLabel slotTag16;
    private javax.swing.JLabel slotTag17;
    private javax.swing.JLabel slotTag18;
    private javax.swing.JLabel slotTag19;
    private javax.swing.JLabel slotTag2;
    private javax.swing.JLabel slotTag20;
    private javax.swing.JLabel slotTag21;
    private javax.swing.JLabel slotTag22;
    private javax.swing.JLabel slotTag3;
    private javax.swing.JLabel slotTag4;
    private javax.swing.JLabel slotTag5;
    private javax.swing.JLabel slotTag6;
    private javax.swing.JLabel slotTag7;
    private javax.swing.JLabel slotTag8;
    private javax.swing.JLabel slotTag9;
    private javax.swing.JLabel turnDisplayLabel;
    private javax.swing.JLabel turnLabel;
    // End of variables declaration//GEN-END:variables
}
