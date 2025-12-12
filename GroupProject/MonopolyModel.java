/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GroupProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
/**
 *
 * @author jason & LISA
 */
public class MonopolyModel {
    private MonopolyController controller;
    
    private int[] landOwnerships; // 0= no one, 1-4= player 1-4
    private int turn; // 1-4, player 1-4
    private PlayerState[] playerStates; 

    //basic setting of the model
    public MonopolyModel() {
        
    }
    
    public void setController(MonopolyController c) {
        this.controller = c;
    }

    

    public void start(){
        //new game setting
        landOwnerships = new int[23];
        Arrays.fill(landOwnerships, 0); //the landOwnership of slot 0(GO position) is always 0
        turn = 1;
        PlayerState player1 = new PlayerState(2000, 0, false);
        PlayerState player2 = new PlayerState(2000, 0, false);
        PlayerState player3 = new PlayerState(2000, 0, false);
        PlayerState player4 = new PlayerState(2000, 0, false);
        PlayerState[] players = {player1, player2, player3, player4};
        playerStates = players;
    }

    public LandsInfo[] loadLands(){
        //load slot info + RETURN LandsInfo[] (slot 0-23)
        File file = new File("lands.txt");
        LandsInfo[] landsInfo = new LandsInfo[23];
        try {
            Scanner scanner = new Scanner(file);
            scanner.nextLine(); // Skip the header line
            int index = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                int slot = Integer.parseInt(parts[0]);
                String landName = parts[1];
                int price = Integer.parseInt(parts[2]);
                landsInfo[index] = new LandsInfo(slot, landName, price);
                index++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return landsInfo;
    }
    
    public int rollDice() {
        //random a number -> return 1 int
        Random random = new Random();
        int i = random.nextInt(10) + 1;
        return i;
    }
    
    
    public void updatePlayerPosition(String player, int positionToBeMoved){
        // which player -> change position
        // if position>23, case pass 0(reset by -23), balance +2000
        switch (player){
            case "Player 1":
                PlayerState player1 = playerStates[0];
                playerStates[0].setPosition(player1.getPosition()+positionToBeMoved);
                if(playerStates[0].getPosition()>=23){
                    playerStates[0].setPosition(playerStates[0].getPosition()-23);
                    playerStates[0].setBalance(playerStates[0].getBalance()+2000);
                    controller.showMessgae("Notice: You passed the slot GO and get $2000!");
                }
                break;
            case "Player 2":
                PlayerState player2 = playerStates[1];
                playerStates[1].setPosition(player2.getPosition()+positionToBeMoved);
                if(playerStates[1].getPosition()>=23){
                    playerStates[1].setPosition(playerStates[1].getPosition()-23);
                    playerStates[1].setBalance(playerStates[1].getBalance()+2000); 
                    controller.showMessgae("Notice: You passed the slot GO and get $2000!");
                }
                break;
            case "Player 3":
                PlayerState player3 = playerStates[2];
                playerStates[2].setPosition(player3.getPosition()+positionToBeMoved);
                if(playerStates[2].getPosition()>=23){
                    playerStates[2].setPosition(playerStates[2].getPosition()-23);
                    playerStates[2].setBalance(playerStates[2].getBalance()+2000);  
                    controller.showMessgae("Notice: You passed the slot GO and get $2000!");
                }
                break;
            case "Player 4":
                PlayerState player4 = playerStates[3];
                playerStates[3].setPosition(player4.getPosition()+positionToBeMoved);
                if(playerStates[3].getPosition()>=23){
                    playerStates[3].setPosition(playerStates[3].getPosition()-23);
                    playerStates[3].setBalance(playerStates[3].getBalance()+2000); 
                    controller.showMessgae("Notice: You passed the slot GO and get $2000!");
                }
                break;
        }
    }
    
    
    public Info update(){
        // create a PlayerState[] copy
        // create a Info ( landOwnerships+ PlayerState[] )
        // RETURN Info parameter
        PlayerState player1 = playerStates[0];
        PlayerState player2 = playerStates[1];
        PlayerState player3 = playerStates[2];
        PlayerState player4 = playerStates[3];
        PlayerState[] players = {player1, player2, player3, player4};
        Info infos = new Info(landOwnerships,players,turn);
        return infos;
    }

    
    public void passTurn(int oriTurn){
        //handle pass turn 
        switch(oriTurn){
            case 1:
                if (playerStates[1].getIsBankrupt() == false){
                    turn = 2;
                }else if (playerStates[2].getIsBankrupt() == false){
                    turn = 3;
                }else if (playerStates[3].getIsBankrupt() == false){
                    turn = 4;
                }else{
                    onePlayerLeft();
                    //turn = 1;
                }
                break;
            case 2:
                if (playerStates[2].getIsBankrupt() == false){
                    turn = 3;
                }else if (playerStates[3].getIsBankrupt() == false){
                    turn = 4;
                }else if (playerStates[0].getIsBankrupt() == false){
                    turn = 1;
                }else{
                    onePlayerLeft();
                    //turn = 2;
                }break;
            case 3:
                if (playerStates[3].getIsBankrupt() == false){
                    turn = 4;
                }else if (playerStates[0].getIsBankrupt() == false){
                    //turn = 1;
                }else if (playerStates[1].getIsBankrupt() == false){
                    turn = 2;
                }else{
                    onePlayerLeft();
                    //turn = 3;
                }break;
            case 4:
                if (playerStates[0].getIsBankrupt() == false){
                    turn = 1;
                }else if (playerStates[1].getIsBankrupt() == false){
                    turn = 2;
                }else if (playerStates[3].getIsBankrupt() == false){
                    turn = 4;
                }else{
                    onePlayerLeft();
                    //turn = 4;
                }break;
        }
    }

    
    
    public void buyLand(int turn, int position, int landPrice) {
        // if not enough money -> popup message
        // if enough money -> buy, balance-landprince, got ownership, popup message
        if(playerStates[turn-1].getBalance()-landPrice<0){
            controller.showMessgae("Do not have enough balance.");
            System.out.println("Do not have enough balance.");
        }else{
            playerStates[turn-1].setBalance(playerStates[turn-1].getBalance()-landPrice);
            landOwnerships[position] = turn;            
            controller.showMessgae("Player "+turn+" brought slot No."+position);
        }
    }

    public void payRentalFee(int payer, int rentalFee, int receiver) {
        // payer-rentalFee, receiver+rentalFee
        // if payer not enough money -> bankrupt
        if(playerStates[payer-1].getBalance() -rentalFee < 0){
            playerStates[payer-1].setIsBankrupt(true);
            for (int i = 1; i<23;i++){
                if (landOwnerships[i] == turn){
                    landOwnerships[i] = 0;
                }
            }
            playerStates[receiver-1].setBalance(playerStates[receiver-1].getBalance() + playerStates[payer-1].getBalance());
            playerStates[payer-1].setBalance(0);
        }else {
            playerStates[payer-1].setBalance(playerStates[payer-1].getBalance() -rentalFee);
            playerStates[receiver-1].setBalance(playerStates[receiver-1].getBalance() + rentalFee);
        }
    }
    
    
    public void isBankRupt(int turn){
        //check if the player is bankrupt
        if(playerStates[turn-1].getBalance() < 0){
            playerStates[turn-1].setIsBankrupt(true);
            for (int i = 1; i<23;i++){
                if (landOwnerships[i] == turn){
                    landOwnerships[i] = 0;
                }
            }
            playerStates[turn-1].setBalance(0);
        }
    }
    
    
    public boolean onePlayerLeft() {
        //check how many people are not yet bankrupt
        //if only one -> winner
        int i = 0;
        int winner;
        for (PlayerState s : playerStates){
           if (s.getIsBankrupt() == false){
               i++;
            }
        }
        if (i == 1){
            for (int j = 0; j<4; j++){
                if (playerStates[j].getIsBankrupt() ==false){
                    controller.showMessgae("Congrats! Player "+(j+1)+" wins!");
                    return true;
                }
            }
        }
        return false;
    }

    
    
    public void editTurn(int newTurn) {
        turn = newTurn;
    }
    
    public void editOnePlayerLandOwnership(int player, ArrayList<Integer> newLandOwnershipInt) {
        for (int i: newLandOwnershipInt){
            landOwnerships[i] = player;
        }
    }

    public void editOnePlayerBalance(int player, int changed) {
        playerStates[player-1].setBalance(changed);
    }

    public void editOnePlayerPosition(int player, int changed) {
        playerStates[player-1].setPosition(changed);
    }

    public void editOnePlayerStatus(int player, int changed) {
        //1 for Bankrupt, 0 for Active
        if (changed == 1){
            playerStates[player-1].setIsBankrupt(true);
            for (int i = 1; i<23;i++){
                if (landOwnerships[i] == player){
                    landOwnerships[i] = 0;
                }
            }
            playerStates[player-1].setBalance(0);
        }if (changed == 0)
            playerStates[player-1].setIsBankrupt(false);
    }
    
    
}
