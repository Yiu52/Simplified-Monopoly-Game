/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GroupProject3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
/**
 *
 * @author jason
 */
public class MonopolyModel {
    private String[][] landNames; 
    private int[] landOwnerships; // 0= no one, 1-4= player 1-4
    private int turn; // 1-4, player 1-4
    PlayerState[] playerStates; 

    public MonopolyModel() {

    }

    MonopolyController controller;

    public void setController(MonopolyController c) {
        this.controller = c;
    }
    
    private void loadSlotInfo() {

    }

    public void start(){
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


    public int rollDice() {
        Random random = new Random();
        int i = random.nextInt(10) + 1;
        return i;
    }
    
    public void updatePlayerPosition(String player, int positionToBeMoved){
        switch (player){
            case "Player 1":
                PlayerState player1 = playerStates[0];
                player1.setPosition(player1.getPosition()+positionToBeMoved);
                if(player1.getPosition()>=23){
                    player1.setPosition(player1.getPosition()-23);
                    player1.setBalance(player1.getBalance()+2000);
                }
                break;
            case "Player 2":
                PlayerState player2 = playerStates[1];
                player2.setPosition(player2.getPosition()+positionToBeMoved);
                if(player2.getPosition()>=23){
                    player2.setPosition(player2.getPosition()-23);
                    player2.setBalance(player2.getBalance()+2000);                    
                }
                break;
            case "Player 3":
                PlayerState player3 = playerStates[2];
                player3.setPosition(player3.getPosition()+positionToBeMoved);
                if(player3.getPosition()>=23){
                    player3.setPosition(player3.getPosition()-23);
                    player3.setBalance(player3.getBalance()+2000);                    
                }
                break;
            case "Player 4":
                PlayerState player4 = playerStates[3];
                player4.setPosition(player4.getPosition()+positionToBeMoved);
                if(player4.getPosition()>=23){
                    player4.setPosition(player4.getPosition()-23);
                    player4.setBalance(player4.getBalance()+2000);                    
                }
                break;
        }
    }
    
    public Info update(){
        PlayerState player1 = playerStates[0];
        PlayerState player2 = playerStates[1];
        PlayerState player3 = playerStates[2];
        PlayerState player4 = playerStates[3];
        PlayerState[] players = {player1, player2, player3, player4};
        Info infos = new Info(landOwnerships,players);
        return infos;
    }

    
    public String passTurn(String turn){
        switch(turn){
            case "Player 1":
                return "Player 2";
            case "Player 2":
                return "Player 3";
            case "Player 3":
                return "Player 4";
            case "Player 4":
                return "Player 1";
        }
        return "Player 0";
    }
    
public LandsInfo[] loadLands(){
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
    
    public void buyLand(int turn, int position, int landPrice) {
        landOwnerships[position] = turn;
        if(playerStates[turn-1].getBalance()-landPrice<0){
            System.out.println("Do not have enough balance.");
        }else{
            playerStates[turn-1].setBalance(playerStates[turn-1].getBalance()-landPrice);            
        }
    }

    public void payRentalFee(int payer, int rentalFee, int receiver) {
        playerStates[payer-1].setBalance(playerStates[payer-1].getBalance() -rentalFee);
        playerStates[receiver-1].setBalance(playerStates[receiver-1].getBalance() + rentalFee);
        if(playerStates[payer-1].getBalance() < 0){
            playerStates[payer-1].setIsBankrupt(true);
        }
    }

    public boolean onePlayerLeft() {

        return false;
    }


    public void editOwnership() {

    }

    public void editBalance(int player) {

    }

    public void editLocation(int player) {

    }

    public void editStatus() {

    }

    public void editTurn() {

    }
}
