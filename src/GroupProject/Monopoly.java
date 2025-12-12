/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GroupProject;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 *
 * @author jason
 */
public class Monopoly {
	
    public static void main(String[] args) {
        MonopolyModel model = new MonopolyModel();
        MonopolyView view = new MonopolyView();
        MonopolyController controller = new MonopolyController();

        model.setController(controller);
        controller.setModel(model);
        controller.setView(view);
        view.setController(controller);
        view.loadLands();
        controller.newGame();
        

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                view.setSize( Toolkit.getDefaultToolkit().getScreenSize() );
                view.setVisible(true);
            }
        }
        );
    }
}
