/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.utp.poo.application.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDesktopPane;
import static pe.edu.utp.poo.application.common.Constant.UI_INICIO_SALIR;
import static pe.edu.utp.poo.application.common.Constant.UI_AYUDA_ACERCA_DE;
import static pe.edu.utp.poo.application.common.Constant.UI_MANTENIMIENTO_USUARIOS;

/**
 *
 * @author manuelguarniz
 */
public class MenuActionListener implements ActionListener {
    private final JDesktopPane mainPanel;
    private UIAcercaDe uiAcercaDe;
    private UIUsuarios uiUsuarios;
    
    public MenuActionListener(JDesktopPane panel) {
        this.mainPanel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case UI_AYUDA_ACERCA_DE -> {
                uiAcercaDe = new UIAcercaDe();
                mainPanel.add(uiAcercaDe).setVisible(true);
            }
            case UI_MANTENIMIENTO_USUARIOS -> {
                uiUsuarios = new UIUsuarios();
                mainPanel.add(uiUsuarios).setVisible(true);
            }
            case UI_INICIO_SALIR -> System.exit(0);
            default -> System.out.println("No action");
        }
    }
    
}
