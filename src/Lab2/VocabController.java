package Lab2;

import Lab2.VocabModel;
import Lab2.VocabView;


/**
 *
 * @author cchik_000
 */
public class VocabController {
    
    private VocabView view;
    private VocabModel model;
    
    public void setView(VocabView v) {
        this.view = v;        
    }
    
    public void setModel(VocabModel m) {
        this.model = m;        
    }
    
    public void addToModel(String voacb, String meaning){
        model.add(voacb, meaning);
        //view.showMessage("New entry added.");        
    }
    
    public void deleteFromModel(String vocab){
        boolean isOK = model.delete(vocab);
        if (isOK){
            //view.showMessage("Entry deleted.");            
        }
        else{
            view.showMessage("Record not found.");
            }
    }
    
    public void lookupFromModel(String vocab){
        String meaning = model.lookup(vocab);        
        if (meaning == null)
            view.showMessage("\'"+ vocab +"\' is not found.");        
        else{
            view.showMessage("\'"+ vocab + "\' means " + meaning);
        }
    }
    
    
}
