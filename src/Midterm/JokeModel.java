package Q1;


import java.util.*;

/**
 *
 * @author Lam Ling Yiu, s216753
 */
public class JokeModel {

    JokeController control;

    
    public void setController(JokeController c) {
        this.control = c;
    }

    //You may add your data structure (e.g., array or ArrayList) here for storing the jokes
    // You are NOT recommended to use a Hashtable or a file here.
    /* YOUR ANSWER HERE */
    ArrayList<String> jokes = new ArrayList<>();
    
    
    
    // Add your method here for adding a joke to the data structure
    public void addJoke(String s) {
        /* YOUR ANSWER HERE */
        jokes.add(s);
    }

    // Add your method here for getting and returning a random joke from the data structure
    public String getJoke() {
        /* YOUR ANSWER HERE */
        String joke;
        String noJoke = "Sorry I have no jokes";
        if(!jokes.isEmpty()){
            Random rand = new Random();
            int idx = rand.nextInt(jokes.size());
            joke = jokes.get(idx);
            return joke;
        }else{
            return noJoke;
        }
        
}
}
