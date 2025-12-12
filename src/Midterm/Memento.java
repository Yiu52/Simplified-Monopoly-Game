package Q2;
/**
 *
 * @author Lam Ling Yiu, s216753
 */
//COMPLETE THIS CLASS
public class Memento {

    // Add your attributes / variable here (for storing a grid)
    /* YOUR ANSWER HERE */
    char[][] grid = new char[8][8];
    int x;
    int y;

    public void setGrid(char[][] grid) {
        this.grid = grid;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public char[][] getGrid() {
        return grid;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Memento(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    
    //A constructor 
    public Memento(char[][] grid, int x, int y) {
        /* YOUR ANSWER HERE */
        
        

    }

    // You may add other methods, if you wish (optional)
}
