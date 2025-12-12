package Lab3;

/**
 *
 * @author CHAN C.K.
 */
    public class Vocab3 {
        public static void main(String[] args) {
            VocabView view = new VocabView();
            VocabModel model = new VocabModel();
            VocabController control = new VocabController();
            
           view.setController(control);
           control.setModel(model);
           control.setView(view);
           model.setupDBConnection();
           
           view.setVisible(true);
           
        }
    }
