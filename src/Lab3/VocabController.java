package Lab3;

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
    
    public void addToModel(String voacb, String meaning, String wordClass){
        model.add(voacb, meaning, wordClass);
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
        VocabData vd = model.lookup(vocab);
        String meaning = vd.meaning;
        String wordClass = vd.wordClass;
        if (meaning.isEmpty() || wordClass.isEmpty())
            view.showMessage("\'"+ vocab +"\' is not found.");        
        else{
            view.showMessage(""+ vocab + " ("+wordClass+") means " + meaning);
        }
    }

    public void addToModel(String vocab, String meaning) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
