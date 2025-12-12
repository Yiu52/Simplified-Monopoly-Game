package Lab3;

/**
 *
 * @author s216753 Lam Ling Yiu
 */
public class VocabData {
    String entry;
    String meaning;
    String wordClass;
    
    public VocabData() {
        
    }
    
    public VocabData(String entry, String meaning, String wordClass) {
        this.entry = entry;
        this.meaning = meaning;
        this.wordClass = wordClass;
    }
    
    public String getEntry(){
        return this.entry;
    }
    public void setEntry(String value){
        this.entry = value;
    }
    public String getMeaning(){
        return this.meaning;
    }
    public void setMeaning(String value){
        this.meaning = value;
    }    
    public String getWordClass(){
        return this.wordClass;
    }
    public void  setwordClass(String value){
        this.wordClass = value;
    }
    
}
