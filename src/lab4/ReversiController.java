package lab4;

public class ReversiController {

    ReversiModel model;
    ReversiView view;
    //
    // Methods for setting up the model and the view
    //	
    public void setModel(ReversiModel m) {
        this.model = m;
    }

    public void setView(ReversiView v) {
        this.view = v;
    }
    // Instruct the model to place a piece on the board:
    // - First check if it is a legal move or not. If it is legal:	
    // --- First save the current game status before we change it (so that it can be undo-ed)
    // --- Then place the piece on the board
    // --- Then indicate it is the next player's turn.
    //
    public void modelMove(int row, int col) {
        if (model.isValidMoveForCurrentPlayer(row, col)) {
            model.save(); 
            model.placeMove(row, col);
            model.swapTurns();
        }
    }

    // Instruct the model to reset the game to the initial status (i.e., a new game) 
    public void newGame() {
        model.resetBoard();
    }

    // Instruct the model that the current player indicates he need to pass 
    public void modelPass() {
        if (!model.currentPlayerHasMove()) {
            model.swapTurns();
        }
    }

    // Instruct the model to undo the last move
    public void modelUndo() {
        model.undo();
    }

    // This method is called by the PostUpdate() method of the model to update the view, based on the game status variables.
    public void viewUpdate(int[][] board2, int turn, int numPieces, int blackScore, int whiteScore, int status, boolean nextPlayerMustPass) {
        view.update(board2, turn, numPieces, blackScore, whiteScore, status, nextPlayerMustPass);
    }

    // This method is called by various methods to show a pop-up message on the view.
    public void viewShowMessage(String msg) {
        view.showMessage(msg);

    }
}
