package Lab2ref;


/**
 *
 * @author CHAN C.K.
 */
    public class Dictionary {
        public static void main(String[] args) {
            DictionaryView view = new DictionaryView();
            DictionaryModel model = new DictionaryModel();
            DictionaryController control = new DictionaryController();

           view.setController(control);
           control.setModel(model);
           control.setView(view);

           view.processCommand();
        }
    }
