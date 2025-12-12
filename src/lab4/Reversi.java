package lab4;
//
//  COM3101 Lab 3


//  Reversi game Undo using Memento Design Pattern 
//
public class Reversi {

	public static final int IN_PROGRESS = 0, BLACK_WIN = 1, WHITE_WIN = 2, DRAW=3;
	
    public static void main(String[] args) {
        ReversiModel model = new ReversiModel();
        ReversiView view = new ReversiView();
        ReversiController controller = new ReversiController();

        model.setController(controller);
        controller.setModel(model);
        controller.setView(view);
        view.setController(controller);

        controller.newGame();

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                view.setVisible(true);
            }
        }
        );
    }

}
