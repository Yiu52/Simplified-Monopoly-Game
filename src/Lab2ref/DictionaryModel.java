package Lab2ref;


import java.util.*;
/**
 *
 * @author CHAN C.K.
 */
public class DictionaryModel {
    
    private Hashtable<String, String> dict = new Hashtable<String, String>();    
       
    public void add(String eng, String spa) {
        dict.put(eng, spa);
    }

    public boolean delete(String eng) {
        String result = dict.remove(eng);
        if (result == null) {
            return false;
        }
        return true;
    }

    public String lookup(String eng) {
        return dict.get(eng);
    }

}
