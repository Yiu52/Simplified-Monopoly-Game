/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalTesting;

import java.util.ArrayList;
/**
 *
 * @author jason
 */
public class CareTaker {
    ArrayList mementos = new ArrayList();
    public void addMementos(Memento m){
        mementos.add(0,m);
    }
    public Memento removeMemento(){
        if(mementos.isEmpty()){
           mementos.remove(0);
        }
        return null;
    }
}
