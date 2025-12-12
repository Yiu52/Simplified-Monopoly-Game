package Q2;
//NOTE: THIS CLASS HAS BEEN COMPLETED. YOU DO NOT NEED TO MODIFY

import java.util.*;

class CareTaker {

    private ArrayList<Memento> undoList = new ArrayList<>();

    public void addMemento(Memento m) {
        undoList.add(0, m);
    }

    public Memento removeMemento() {

        if (!undoList.isEmpty()) {            
            Memento m = undoList.remove(0);
            return m;
        } else {
            return null;
        }
    }    
}
