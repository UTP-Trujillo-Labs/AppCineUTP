/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package pe.edu.utp.poo.application;

import pe.edu.utp.poo.application.ui.UIMainMenu;

/**
 *
 * @author manuelguarniz
 */
public class AppCineUTP {
   
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            UIMainMenu uiMain = new UIMainMenu();
            uiMain.setVisible(true);
            uiMain.setLocationRelativeTo(null);
        });
    }
}
