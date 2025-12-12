/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Testing;

/**
 *
 * @author jason
 */
public class Memento {
    private String status1;
    private String status2;

    public Memento(String status1, String status2) {
        this.status1 = status1;
        this.status2 = status2;
    }

    public String getStatus1() {
        return status1;
    }

    public String getStatus2() {
        return status2;
    }

    public void setStatus1(String status1) {
        this.status1 = status1;
    }

    public void setStatus2(String status2) {
        this.status2 = status2;
    }
    
    
}
