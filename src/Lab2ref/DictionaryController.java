package Lab2ref;


/**
 *
 * @author cchik_000
 */
public class DictionaryController {
    
    private DictionaryView view;
    private DictionaryModel model;
    
    public void setView(DictionaryView v) {
        this.view = v;        
    }
    
    public void setModel(DictionaryModel m) {
        this.model = m;        
    }
    
    public void addToModel(String eng, String spa){
        model.add(eng, spa);
        view.showMessage("New entry added.");        
    }
    
    public void deleteFromModel(String eng){
        boolean isOK = model.delete(eng);
        if (isOK)
            view.showMessage("Entry deleted.");        
        else
            view.showMessage("Record not found.");        
    }
    
    public void lookupFromModel(String eng){
        String spa = model.lookup(eng);        
        if (spa == null)
            view.showMessage("Entry not found.");        
        else
            view.showMessage(eng + " is " + spa + " in Spanish.");        
    }
    
    
}
