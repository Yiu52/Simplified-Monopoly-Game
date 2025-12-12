package Lab2ref;


import java.util.*;

/**
 *
 * @author CHAN C.K.
 */
public class DictionaryView {

    private DictionaryController controller;
    private Scanner scanner = new Scanner(System.in);

    public void setController(DictionaryController c) {
        this.controller = c;
    }

    public void processCommand() {
        boolean end = false;
        while (!end) {
            System.out.println("Please enter your command (add/lookup/delete/exit)");
            System.out.print(">");
            String command = scanner.next();
            switch (command) {
                case "add":
                    doAdd();
                    break;
                case "lookup": case "get":
                    doLookup();
                    break;
                case "delete":
                    doDelete();
                    break;
                case "exit":
                    end = true;
                    break;
                default:
                    System.out.println("Invalid command");
            }
        }
    }

    public void doAdd() {
        String eng = scanner.next();
        String spa = scanner.next();
        controller.addToModel(eng, spa);
    }

    public void doDelete() {
        String eng = scanner.next();
        controller.deleteFromModel(eng);
    }

    public void doLookup() {
        String eng = scanner.next();
        controller.lookupFromModel(eng);
    }

    public void showMessage(String s) {
        System.out.println(s);
    }
}
