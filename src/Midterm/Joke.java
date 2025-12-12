package Q1;

/**
 *
 * @author Lam Ling Yiu, s216753
 */
public class Joke {
    public static void main(String[] args) {
		
           JokeView view = new JokeView();
           JokeModel model = new JokeModel();
           JokeController control = new JokeController();

           view.setController(control);
           control.setModel(model);
           control.setView(view);
           model.setController(control);

           view.processCommand();
        }
    
}
