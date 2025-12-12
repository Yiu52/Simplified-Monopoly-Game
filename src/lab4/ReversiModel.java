package lab4;

public class ReversiModel {

    //Linking to a controller object
    ReversiController control;

    public void setController(ReversiController c) {
        this.control = c;
    }

    //************************************************************************************
    //  ADD YOUR CODE HERE TO DECLARE THE CareTaker for saving mementos.
    //************************************************************************************
    // YOUR CODE HERE
    //*************************************************************************************
    //The following are GAME STATUS Variables: (You need to use them, but don't need to modify this part).
    //  - They represent the status of the current game.
    //  - They will be updated (posted) to the view via the postUpdate() method after certain actions.
    //  - Needs to be saved to the caretaker inside a memento object before they are changed (See save() and  method)
    //  - They may be restored to the previous states during an undo-action (See undo() method)
    //*************************************************************************************
    private int[][] board = new int[8][8]; 	// The logical board
    private int turn;               		// Who's turn to move (BLACK or WHITE)
    private int numPieces;					// How many pieces are there on the board
    private int blackScore, whiteScore; 	// How many BLACK and WHITE pieces are there currently
    private int status;						// 0:Game in progress, 1:Black Win, 2: White Win, 3:Draw
    private boolean nextPlayerMustPass;		// The next play has no moves. He must pass.
    /////////////////////////////

    //Constants
    private final int EMPTY = 0, BLACK = 1, WHITE = 2;
    Caretaker Caretaker = new Caretaker();

    //*************************************************************************************
    // THE SAVE METHOD (INCOMPLETE)
    // Complete this part by adding code for saving the game status variables using mementos 
    // the user and restore them when he needs to undo an action later.
    // This method is called by modelMove() in the controller, before it make changes to the board.
    //*************************************************************************************
    public void save() {
        // Your code here.
        Memento m = new Memento(board, turn, numPieces, blackScore, whiteScore, status, nextPlayerMustPass);
        Caretaker.addMemento(m); 	
        //checkStatus();
        //postUpdate();
    }

    //*************************************************************************************
    // THE UNDO METHOD (INCOMPLETE)
    // Complete this part by adding code for restoring the previous game status variables
    // from the caretaker. After restoring, you need to inform the view of the changes.
    // This method is called by modelUndo() method in the controller.
    //*************************************************************************************
    public void undo() {
        // Your code here:
        // - Obtain the memento object from the caretaker
        // - Use the memento to restore the game status variables
        
        //Your codes here
        // = restore in lecture note
        Memento m = Caretaker.removeMemento();
        if(m!= null){
            board = m.getBoard();
            turn = m.getTurn();
            numPieces =m.getNumPieces();
            blackScore = m.getBlackScore();
            whiteScore = m.getWhiteScore();
            status = m.getStatus();
            nextPlayerMustPass = m.getNextPlayerMustPass();
        }
        //control.viewShowMessage("Please help CCK to implement the UNDO function !!"); //Remove after finishing
        postUpdate();
    }
    //****************************************************
    //
    // The remaining methods are related to board updating
    //
    //****************************************************
    //
    // postUpdate(): send the game status to the view via the controller
    // NOTE: we are sending the COPIES of the game status variables, especially for the board[][] array! (why?)
    //
    public void postUpdate() {
        int[][] board2 = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board2[i][j] = board[i][j];
            }
        }        
		control.viewUpdate(board2, turn, numPieces, blackScore, whiteScore, status, nextPlayerMustPass);

    }

    //Reset the game board to the starting state (for the new game option)
    public void resetBoard() {
        //Clear all the pieces first
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                board[row][col] = EMPTY;
            }
        }

        //Place four pieces in the centre
        placePiece(3, 3, WHITE);
        placePiece(3, 4, BLACK);
        placePiece(4, 4, WHITE);
        placePiece(4, 3, BLACK);

        //Reset the game status variables to the starting state
        turn = BLACK;
        numPieces = 4;
        blackScore = 2;
        whiteScore = 2;
		status = Reversi.IN_PROGRESS;
        
        //?
        //? (Hints: clear/reset the caretaker at new game!
        Caretaker.clearCaretaker();
        postUpdate();
    }

    //The next player's turn to move. Update the status accordingly.
    public void swapTurns() {    // swap turns
        turn = (turn == BLACK ? WHITE : BLACK);
        checkStatus();
        postUpdate();
    }

    //Update the game status variables.
    private void checkStatus() {     // counter the scores and return the winner
        blackScore = 0;
        whiteScore = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == WHITE) {
                    whiteScore++;
                } else if (board[i][j] == BLACK) {
                    blackScore++;
                }
            }
        }

	    if (numPieces == 64 || (!hasMove(BLACK) && !hasMove(WHITE))) {
            nextPlayerMustPass = false;
            if (blackScore > whiteScore) {
                status = Reversi.BLACK_WIN;
            } else if (whiteScore > blackScore) {
				status = Reversi.WHITE_WIN;                
            } else {
				status = Reversi.DRAW;                                
            }
        } else {
            if ((turn == BLACK && !hasMove(BLACK))
                    || (turn == WHITE && !hasMove(WHITE))) {
                nextPlayerMustPass = true;
            } else {
                nextPlayerMustPass = false;
            }
        }
    }

    //Check if the current player has any valid moves
    public boolean currentPlayerHasMove() {
        return (hasMove(turn));
    }

    //Check if the specified player has any valid moves
    private boolean hasMove(int color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (isValidMove(i, j, color)) {
                    return true;
                }
            }
        }
        return false;
    }

    //Check whether a certain move is a valid one for the current player
    public boolean isValidMoveForCurrentPlayer(int x, int y) {
        return isValidMove(x, y, turn);
    }

    //Check whether a certain move is a valid one for a specified player
    private boolean isValidMove(int x, int y, int color) {

        int oppColor = (color == BLACK) ? WHITE : BLACK;

        /* if already occupied, return false */
        if (board[x][y] != EMPTY) {
            return false;
        }

        /* check N */
        if (y + 2 < 8 && board[x][y + 1] == oppColor) {
            int j = y + 2;
            while (j < 8 && board[x][j] == oppColor) {
                j++;
            }
            if (j < 8 && board[x][j] == color) {
                return true;
            }
        }
        /* check NE */
        if (y + 2 < 8 && x + 2 < 8 && board[x + 1][y + 1] == oppColor) {
            int i = x + 2;
            int j = y + 2;
            while (i < 8 && j < 8 && board[i][j] == oppColor) {
                i++;
                j++;
            }
            if (i < 8 && j < 8 && board[i][j] == color) {
                return true;
            }
        }
        /* check E */
        if (x + 2 < 8 && board[x + 1][y] == oppColor) {
            int i = x + 2;
            while (i < 8 && board[i][y] == oppColor) {
                i++;
            }
            if (i < 8 && board[i][y] == color) {
                return true;
            }
        }
        /* check SE */
        if (y - 2 >= 0 && x + 2 < 8 && board[x + 1][y - 1] == oppColor) {
            int i = x + 2;
            int j = y - 2;
            while (i < 8 && j >= 0 && board[i][j] == oppColor) {
                i++;
                j--;
            }
            if (i < 8 && j >= 0 && board[i][j] == color) {
                return true;
            }
        }
        /* check S */
        if (y - 2 >= 0 && board[x][y - 1] == oppColor) {
            int j = y - 2;
            while (j >= 0 && board[x][j] == oppColor) {
                j--;
            }
            if (j >= 0 && board[x][j] == color) {
                return true;
            }
        }
        /* check SW */
        if (y - 2 >= 0 && x - 2 >= 0 && board[x - 1][y - 1] == oppColor) {
            int i = x - 2;
            int j = y - 2;
            while (i >= 0 && j >= 0 && board[i][j] == oppColor) {
                i--;
                j--;
            }
            if (i >= 0 && j >= 0 && board[i][j] == color) {
                return true;
            }
        }
        /* check W */
        if (x - 2 >= 0 && board[x - 1][y] == oppColor) {
            int i = x - 2;
            while (i >= 0 && board[i][y] == oppColor) {
                i--;
            }
            if (i >= 0 && board[i][y] == color) {
                return true;
            }
        }
        /* check NW */
        if (y + 2 < 8 && x - 2 >= 0 && board[x - 1][y + 1] == oppColor) {
            int i = x - 2;
            int j = y + 2;
            while (i >= 0 && j < 8 && board[i][j] == oppColor) {
                i--;
                j++;
            }
            if (i >= 0 && j < 8 && board[i][j] == color) {
                return true;
            }
        }
        return false;
    }

    //Place a piece on the board
    private void placePiece(int row, int col, int color) { // placing piece in the logical board
        if (board[row][col] == EMPTY) {
            board[row][col] = color;
        }
    }

    //Place a piece at a location on the board, and flip any opponent's piece that are sandwiched.
    public void placeMove(int x, int y) {

        int oppColor = (turn == BLACK) ? WHITE : BLACK;

        /* if already occupied, return false */
        if (board[x][y] != EMPTY) {
            return;
        }

        /* check N */
        if (y + 2 < 8 && board[x][y + 1] == oppColor) {
            int j = y + 2;
            while (j < 8 && board[x][j] == oppColor) {
                j++;
            }
            if (j < 8 && board[x][j] == turn) {
                overturn(x, y, x, j);
            }
        }
        /* check NE */
        if (y + 2 < 8 && x + 2 < 8 && board[x + 1][y + 1] == oppColor) {
            int i = x + 2;
            int j = y + 2;
            while (i < 8 && j < 8 && board[i][j] == oppColor) {
                i++;
                j++;
            }
            if (i < 8 && j < 8 && board[i][j] == turn) {
                overturn(x, y, i, j);
            }
        }
        /* check E */
        if (x + 2 < 8 && board[x + 1][y] == oppColor) {
            int i = x + 2;
            while (i < 8 && board[i][y] == oppColor) {
                i++;
            }
            if (i < 8 && board[i][y] == turn) {
                overturn(x, y, i, y);
            }
        }
        /* check SE */
        if (y - 2 >= 0 && x + 2 < 8 && board[x + 1][y - 1] == oppColor) {
            int i = x + 2;
            int j = y - 2;
            while (i < 8 && j >= 0 && board[i][j] == oppColor) {
                i++;
                j--;
            }
            if (i < 8 && j >= 0 && board[i][j] == turn) {
                overturn(x, y, i, j);
            }
        }
        /* check S */
        if (y - 2 >= 0 && board[x][y - 1] == oppColor) {
            int j = y - 2;
            while (j >= 0 && board[x][j] == oppColor) {
                j--;
            }
            if (j >= 0 && board[x][j] == turn) {
                overturn(x, y, x, j);
            }
        }
        /* check SW */
        if (y - 2 >= 0 && x - 2 >= 0 && board[x - 1][y - 1] == oppColor) {
            int i = x - 2;
            int j = y - 2;
            while (i >= 0 && j >= 0 && board[i][j] == oppColor) {
                i--;
                j--;
            }
            if (i >= 0 && j >= 0 && board[i][j] == turn) {
                overturn(x, y, i, j);
            }
        }
        /* check W */
        if (x - 2 >= 0 && board[x - 1][y] == oppColor) {
            int i = x - 2;
            while (i >= 0 && board[i][y] == oppColor) {
                i--;
            }
            if (i >= 0 && board[i][y] == turn) {
                overturn(x, y, i, y);
            }
        }
        /* check NW */
        if (y + 2 < 8 && x - 2 >= 0 && board[x - 1][y + 1] == oppColor) {
            int i = x - 2;
            int j = y + 2;
            while (i >= 0 && j < 8 && board[i][j] == oppColor) {
                i--;
                j++;
            }
            if (i >= 0 && j < 8 && board[i][j] == turn) {
                overturn(x, y, i, j);
            }
        }
        numPieces++;

    }

    private void overturn(int x1, int y1, int x2, int y2) {
        int i = x1;
        int j = y1;

        while (i != x2 || j != y2) {
            if (board[x2][y2] == BLACK) {
                board[i][j] = BLACK;
            } else {
                board[i][j] = WHITE;
            }

            if (i < x2) {
                i++;
            }
            if (i > x2) {
                i--;
            }
            if (j < y2) {
                j++;
            }
            if (j > y2) {
                j--;
            }
        }
    }
}
