package Q1;


import java.util.*;

/**
 *
 * @author Lam Ling Yiu, s216753
 */
public class JokeView {

    private Scanner scanner = new Scanner(System.in); //Use this scanner for input. Do not add other scanners.

    private JokeController controller;

    public void setController(JokeController c) {
        this.controller = c;
    }

    public void processCommand() {

        boolean end = false;
        while (!end) {
            System.out.print(">");
            String command = scanner.nextLine();

            if (command.equals("bye")) {
                end = true;
            } else {

                //Else If the command is "joke", get a joke from the model via the controller (the getJoke() method)
                //Otherwise, add the command (which is a joke) to the model via the controller (the addJoke() method)
                /* YOUR ANSWER HERE */
                if(command.equals("joke")){
                    controller.getJoke();
                }else{
                controller.addJoke(command);                    
                }

            }

        }
    }

    //(optional) You may also add other methods here if needed 
    //The other classes may call this method to show a message to the user.
    public void showMessage(String s) {
        System.out.println(s);
    }
}
