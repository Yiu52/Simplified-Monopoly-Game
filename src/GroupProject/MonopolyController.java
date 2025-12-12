/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GroupProject;

import java.util.ArrayList;

/**
 *
 * @author jason
 */
public class MonopolyController {
    private MonopolyModel model;
    private MonopolyView view;

    //basic setting
    public void setModel(MonopolyModel m) {
        this.model = m;
    }

    public void setView(MonopolyView v) {
        this.view = v;
    }

    //functions
    public int rollDice() {
        //called by view, call model to get and RETURN a random int to view
        return model.rollDice();
    }
    
    public void updatePlayerPosition(String player, int positionToBeMoved){
        //called by view, calling model to handel movement of player
        model.updatePlayerPosition(player, positionToBeMoved);
    }

    public Info update(){
        //called by view, call model to update info in Info format to RETURN to view
        return model.update();
    }
    
    public void passTurn(int turn){
        //call the model to handle passing String turn to String turn
        model.passTurn(turn);
    }
            
    public LandsInfo[] loadLands(){
        //call model to load slot/land info
        return model.loadLands();
    }
    
    public void buyLand(int turn, int position, int landPrice) {
        //call model to handle buy land function
        model.buyLand(turn, position, landPrice);
    }

    public void payRentalFee(int payer, int rentalFee, int receiver) {
        //call model to handle pay rental fee function
        model.payRentalFee(payer, rentalFee, receiver);
    }

    public void newGame() {
        //new game function
        model.start();
        view.update();
    }
    
    public void showMessgae(String s){
        view.showMessage(s);
    }
    
    public void checkBankrupt(){
        for (int i = 1; i<=4; i++){
            model.isBankRupt(i);
        }
    }
    
    public boolean checkWin(){
        return model.onePlayerLeft();
    }

    public void viewUpdate() {

        view.update();
    }
    
    public void editTurn(int newTurn){
        model.editTurn(newTurn);
    }
    
    public void editOnePlayerByNum(int choice, int player, int changed){
        // choice: 2 balance, 3 position, 4 status
        switch(choice){
            case 2:
                model.editOnePlayerBalance(player, changed);
                break;
            case 3:
                model.editOnePlayerPosition(player, changed);
                break;
            case 4:
                //1 for Bankrupt, 0 for Active
                model.editOnePlayerStatus(player, changed);
                break;
        }
    }
    
    public void editOnePlayerOwnership(int player, ArrayList<Integer> newLandOwnershipInt){
        model.editOnePlayerLandOwnership(player, newLandOwnershipInt);
    }
}
