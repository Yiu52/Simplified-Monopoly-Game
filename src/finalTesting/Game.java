/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalTesting;

/**
 *
 * @author jason
 */
public class Game {
    private String states;
    private CareTaker careTaker = new CareTaker();
    
   public void save(){
       Memento m = new Memento(states);
       careTaker.addMementos(m);
   }
   
   public void restore(){
       Memento m = careTaker.removeMemento();
       String states = m.getStates();
       if(states !=null){
            System.out.println(states);
       }
   }
}
