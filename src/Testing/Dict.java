/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Testing;

/**
 *
 * @author jason
 */
public class Dict {
    public static void main(String args[]){
        DictControl control = new DictControl();
        DictModel model = new DictModel();
        DictView view = new DictView();
        
        view.setControl(control);
        control.setView(view);
        control.setModel(model);
        
        view.processCommand();
    }
}
