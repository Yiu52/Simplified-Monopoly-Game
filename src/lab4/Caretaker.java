package lab4;
import java.util.*;
/**
 *
 * @author jason
 */
public class Caretaker {
    private ArrayList<Memento> mementos = new ArrayList<>();
    public void addMemento(Memento m) {
        mementos.add(0, m);
    }
    public Memento removeMemento() {
        if (! mementos.isEmpty())	
            return mementos.remove(0);
        else
            return null;
    }
    
    public void clearCaretaker(){
        mementos.clear();
    }
}
