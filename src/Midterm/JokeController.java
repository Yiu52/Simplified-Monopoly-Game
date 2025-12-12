package Q1;

/**
 *
 * @author Lam Ling Yiu, s216753
 */
public class JokeController {
    
    private JokeView view;
    private JokeModel model;
    
    public void setView(JokeView v) {
        this.view = v;        
    }
    
    public void setModel(JokeModel m) {
        this.model = m;        
    }
  
  
    public void getJoke() {
	//First, obtain a joke from the model
	//Then call the appropriate method in the view to show it 
	//(or show "No joke available" if there is none)
	
        /* YOUR ANSWER HERE */
        String joke = model.getJoke();
        System.out.println(joke);
		
    }
    
    public void addJoke(String s) {
	//Add the joke (s) to the model
	//Then call the appropriate method in the view to show a "Ha Ha" messsage.
	
	/* YOUR ANSWER HERE */
        model.addJoke(s);
        view.showMessage("Ha Ha");

 
    }
	
    
}
