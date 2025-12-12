/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GroupProject3;

/**
 *
 * @author jason
 */
public class MonopolyController {
    private MonopolyModel model;
    private MonopolyView view;

    public void setModel(MonopolyModel m) {
        this.model = m;
    }

    public void setView(MonopolyView v) {
        this.view = v;
    }

    public int rollDice() {
        return model.rollDice();
    }
    
    public void updatePlayerPosition(String player, int positionToBeMoved){
        model.updatePlayerPosition(player, positionToBeMoved);
    }

    public Info update(){
        return model.update();
    }
    
    public String passTurn(String turn){
        return model.passTurn(turn);
    }
            
    public LandsInfo[] loadLands(){
        return model.loadLands();
    }
    
    public void buyLand(int turn, int position, int landPrice) {
        model.buyLand(turn, position, landPrice);
    }

    public void payRentalFee(int payer, int rentalFee, int receiver) {
        model.payRentalFee(payer, rentalFee, receiver);
    }

    public void newGame() {
        model.start();
        model.loadLands();
        view.update();
    }
    


    public void viewUpdate() {

        view.update();
    }
    
}
