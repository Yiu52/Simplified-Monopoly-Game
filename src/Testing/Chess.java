/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Testing;

/**
 *
 * @author jason
 */
public class Chess {
    Caretaker caretaker = new Caretaker();
    String status1;
    String status2;
    public void save(){
        Memento memento = new Memento(status1, status2);
        caretaker.AddMemento(memento);
    }
    
    public void restore(){
        Memento m = caretaker.RemoveMemento();
        if(m!=null){
        status1 = m.getStatus1();
        status2 = m.getStatus2();
        }
        
    }
}
