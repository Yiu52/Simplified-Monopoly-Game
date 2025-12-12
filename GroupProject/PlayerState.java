/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GroupProject;

/**
 *
 * @author jason
 */
public class PlayerState {
    private int balance;
    private int position;
    private boolean isBankrupt;

    public PlayerState(int balance, int position, boolean isBankrupt) {
        this.balance = balance;
        this.position = position;
        this.isBankrupt = isBankrupt;
    }

    public int getBalance() {
        return balance;
    }

    public int getPosition() {
        return position;
    }

    public void setIsBankrupt(boolean isBankrupt) {
        this.isBankrupt = isBankrupt;
    }

    public boolean getIsBankrupt() {
        return isBankrupt;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    
}
