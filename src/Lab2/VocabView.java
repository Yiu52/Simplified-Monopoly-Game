package Lab2;


import Lab3.VocabController;
import Lab2ref.*;
import java.util.*;

/**
 *
 * @author CHAN C.K.
 */
public class VocabView {

    private VocabController controller;
    private Scanner scanner = new Scanner(System.in);

    public void setController(VocabController c) {
        this.controller = c;
    }

    public void processCommand() {
        boolean end = false;
        System.out.println("Enter a word in English, or a valid command (/add, /lookup, /delete, /exit)");
        
        while (!end) {
            System.out.print(">");
            String command = scanner.nextLine();
            switch (command) {
                case "/add":
                    doAdd();
                    break;
                case "/delete":
                    doDelete();
                    break;
                case "/exit":
                    end = true;
                    System.out.println("Record saved");
                    break;
                default:
                    if(command.charAt(0)=='/'){
                            System.out.println("Invalid command");
                       }else{
                            doLookup(command);
                            }
            }
        }
    }

    public void doAdd() {
        System.out.print("Enter a word or phrase to be added: ");
        String vocab = scanner.nextLine();
        System.out.print("Enter its meaning: ");        
        String meaning = scanner.nextLine();
        controller.addToModel(vocab, meaning);
    }

    public void doDelete() {
        System.out.print("Enter the word or phrase to be deleted:");
        String vocab = scanner.nextLine();        
        controller.deleteFromModel(vocab);
    }

    public void doLookup(String vocab) {
        controller.lookupFromModel(vocab);
    }

    public void showMessage(String s) {
        System.out.println(s);
    }

    void setController(Lab2.VocabController control) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
