package Lab2;

import Lab2.VocabModel;
import Lab2.VocabView;
import Lab2.VocabController;


/**
 *
 * @author CHAN C.K.
 */
    public class Vocab2 {
        public static void main(String[] args) {
            VocabView view = new VocabView();
            VocabModel model = new VocabModel();
            VocabController control = new VocabController();
            
           view.setController(control);
           control.setModel(model);
           control.setView(view);
           model.setupDBConnection();
           view.processCommand();
           model.closeDB();
        }
    }
