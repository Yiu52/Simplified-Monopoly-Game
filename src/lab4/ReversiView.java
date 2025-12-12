package lab4;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class ReversiView extends JFrame {
    //
    // Variables and method for linking up with a controller
    //

    // Add your name to the variable below
    private String sname = "Lam Ling Yiu (S216753)"; //Enter your name and Student ID here
    //
    private ReversiController control;

    public void setController(ReversiController cntl) {
        this.control = cntl;
    }

    //Some constants
    private final int EMPTY = 0, BLACK = 1, WHITE = 2;

    // GUI elements
    JLabel blackScoreLbl, whiteScoreLbl, currentPlayerLbl;
    JButton passBtn;
    JButton undoBtn;
    JButton redoBtn;
    JPanel boardPanel;

    //
    // The contructor method.
    //    It creates the GUI elements (such as Panels, Buttons, Labels)
    //    as well as adding listeners for specifying what to do when the user performs some actions
    //
    public ReversiView() {
        super("HSUHK COM3101 Software Engineering");
        setLayout(new BorderLayout());

        setBounds(200, 50, 580, 700);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        mainPanel.setBorder(new TitledBorder(sname));

        JPanel topPanel = new JPanel(new FlowLayout());

        JButton btnNewGame = new JButton("Start New Game");
        btnNewGame.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                control.newGame();
            }
        });

        currentPlayerLbl = new JLabel("BLACK to move", JLabel.RIGHT);
        currentPlayerLbl.setFont(new Font("Lucida Calligraphy", Font.BOLD, 18));

        topPanel.add(btnNewGame);
        topPanel.add(currentPlayerLbl);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        //Board Panel
        boardPanel = new JPanel(new GridLayout(8, 8));
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JPanel cell = new JPanel(new BorderLayout());
                cell.setSize(70, 70);
                cell.setBackground(new Color(250, 250, 90));
                cell.setBorder(BorderFactory.createLineBorder(Color.gray));
                boardPanel.add(cell);
            }
        }
        mainPanel.add(boardPanel, BorderLayout.CENTER);

        // bottom score panel
        JPanel scorePanel = new JPanel(new FlowLayout());

        // Creating score Labels
        blackScoreLbl = new JLabel();
        whiteScoreLbl = new JLabel();

        // The Pass Button
        // with a listener that will call the modelPass() method of the controller when the button is clicked.		
        passBtn = new JButton("Pass Turn");
        passBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                control.modelPass();
            }
        });

        //Undo Button
        // with a listener that will call the modelUndo() method of the controller when the button is clicked.		
        undoBtn = new JButton("Undo");
        undoBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                control.modelUndo();
            }
        });

        //The score panel which is at the bottom of the main Panel 
        //  it holds the Pass Button, the Undo Button, the Redo Button as well as the score labels.
        scorePanel.add(undoBtn);    
        scorePanel.add(passBtn);
        scorePanel.add(blackScoreLbl);
        scorePanel.add(whiteScoreLbl);

        mainPanel.add(scorePanel, BorderLayout.SOUTH);

        //The board panel is the main part of the game board GUI 
        //  It shows a 8*8 grid for holding the playing pieces 
        //  When clicked, it use the coordinate of the event to determines which slot has been clicked.
        boardPanel.addMouseListener(new MouseListener() {
            public void mousePressed(MouseEvent e) {
                int row = e.getX() / 70;
                int col = e.getY() / 70;
                control.modelMove(row, col);
            }

            public void mouseClicked(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        }
        );
    }

    //
    // Update the game board shown to the user
    //
    private void updateBoard(int[][] board) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JPanel cell = (JPanel) boardPanel.getComponent(coordToindex(row, col));
                cell.removeAll();
            }
        }
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {

                if (board[row][col] == BLACK) {
                    addRespectivePics(row, col, "black");
                } else if (board[row][col] == WHITE) {
                    addRespectivePics(row, col, "white");
                }

            }
        }
    }
    //
    // The following methods change the picture of a piece (Black piece or white piece) according to its colour
    //

    private void addRespectivePics(int row, int col, String colorName) {
        ImageIcon picture = createImageIcon("images/" + colorName + ".png");
        JLabel picLbl = new JLabel(picture);
        JPanel cell = (JPanel) boardPanel.getComponent(coordToindex(row, col));
        cell.removeAll();
        cell.add(picLbl);
        boardPanel.updateUI();
    }

    private int coordToindex(int row, int col) {     // convert 2D array to 1D array index
        return (col * 8) + row;
    }

    private ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find the file: " + path);
            return null;
        }
    }

    // Show a pop-up message to the user.
    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Display", JOptionPane.INFORMATION_MESSAGE);
    }

    //
    // This method is called by the controller when the model has some changes that it needs to inform the view.
    //	The arguments are copies of the game status variables of the model.
    //	(i.e., changing their values here will not affect the model)
    //
    
	public void update(int[][] board, int turn, int numPieces, int blackScore, int whiteScore, int status, boolean nextPlayerMustPass) {

        updateBoard(board);
        if (turn == WHITE) {
            currentPlayerLbl.setText("WHITE to move");
        } else {
            currentPlayerLbl.setText("BLACK to move");
        }

        blackScoreLbl.setText("Black: " + blackScore);
        whiteScoreLbl.setText("White: " + whiteScore);

        if (status == Reversi.BLACK_WIN) {
            showMessage("BLACK WIN by " + blackScore + ":" + whiteScore);
        } else if (status == Reversi.WHITE_WIN) {
            showMessage("WHITE WIN by " + whiteScore + ":" + blackScore);
        } else if (status == Reversi.DRAW) {
            showMessage("DRAW!");
        }

        if (status == Reversi.IN_PROGRESS && nextPlayerMustPass) {
            passBtn.setEnabled(true);
            passBtn.setBackground(new Color(255, 150, 150));
        } else {
            passBtn.setEnabled(false);
            passBtn.setBackground(java.awt.Color.lightGray);
        }

    }
}