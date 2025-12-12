package Q2;
/**
 *
 * @author Lam Ling Yiu, s216753
 */
import java.util.*;

public class WordPuzzle {

    private Scanner scanner = new Scanner(System.in);

    CareTaker caretaker = new CareTaker();

    char[][] grid = new char[8][8]; // *an array for holding the game board data (the "grid")
    // *you need to save this in a memento before updating it*  

    public void process() {
        boolean end = false;
        System.out.println("Enter a command (put, undo, exit)");
        while (!end) {
            System.out.print(">");
            String command = scanner.next();
            switch (command) {
                case "put":
                    doPut();
                    break;
                case "undo":
                    doUndo();
                    break;
                case "exit":
                    end = true;
                    break;
                default:
                    System.out.println("Invalid command");
            }
        }

    }

    public void doPut() {
        int x = scanner.nextInt();
        int y = scanner.nextInt();

        //HINTS: ADD YOUR CODE HERE FOR SAVING THE grid to the a memento and then add to the caretaker
        /* YOUR ANSWER HERE */
        char[][] CopyGrid = new char[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                CopyGrid[i][j] = grid[i][j];
            }
        }
        Memento memento = new Memento(CopyGrid,x,y);
        caretaker.addMemento(memento);
        
        /* END OF ANSWER AREA */
        char c = scanner.next().charAt(0);
        grid[x][y] = c;
        showBoard();

    }

    public void showBoard() {
        System.out.println("0.1.2.3.4.5.6.7.");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(grid[i][j] + ".");
            }
            System.out.println();
        }
    }

    public void doUndo() {
        //HINTS: ADD YOUR CODE HERE FOR RESTORING the grid from a memento
        /* YOUR ANSWER HERE */
        
        Memento memento = caretaker.removeMemento();
        grid = memento.getGrid();
        

        /* END OF ANSWER AREA */
        showBoard();
    }

    public static void main(String[] args) {
        WordPuzzle wp = new WordPuzzle();
        wp.process();
    }

}
