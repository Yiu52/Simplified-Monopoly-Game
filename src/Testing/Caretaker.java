/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Testing;

import java.util.ArrayList;

/**
 *
 * @author jason
 */
public class Caretaker {
    private ArrayList<Memento> mementos = new ArrayList<>();
    
    public void AddMemento(Memento m){
        mementos.add(0,m);
    }
    
    public Memento RemoveMemento(){
        if(! mementos.isEmpty()){
            return mementos.remove(0);
        }else{
            return null;
        }
    }
}
