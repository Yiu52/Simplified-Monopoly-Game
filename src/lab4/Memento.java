package lab4;
/**
 *
 * @author Lam Ling Yiu, s216753    
 */
public class Memento {
    private int[][] board = new int[8][8]; 	// The logical board
    private int turn;               		// Who's turn to move (BLACK or WHITE)
    private int numPieces;					// How many pieces are there on the board
    private int blackScore, whiteScore; 	// How many BLACK and WHITE pieces are there currently
    private int status;						// 0:Game in progress, 1:Black Win, 2: White Win, 3:Draw
    private boolean nextPlayerMustPass;		// The next play has no moves. He must pass.
    
    public Memento(int[][] board, int turn, int numPieces, int blackScore, int whiteScore, int status, boolean nextPlayerMustPass){
        this.board = new int[8][8];
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    this.board[i][j] = board[i][j];
                }
            }       
        this.turn = turn;
        this.numPieces = numPieces;
        this.blackScore = blackScore;
        this.whiteScore = whiteScore;
        this.status = status;
        this.nextPlayerMustPass = nextPlayerMustPass;
    }
    
    public int[][] getBoard(){
        return board;
    }
    
    public int getTurn(){
        return turn;
    }
    
    public int getNumPieces(){
        return numPieces;
    }
    
    public int getBlackScore(){
        return blackScore;
    }
    
    public int getWhiteScore(){
        return whiteScore;
    }
    
    public int getStatus(){
        return status;
    }
    
    public boolean getNextPlayerMustPass(){
        return nextPlayerMustPass;
    }

}
