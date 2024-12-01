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
import static pe.edu.utp.poo.application.common.Constant.UI_BOLETERIA_BUTACAS;
import static pe.edu.utp.poo.application.common.Constant.UI_BOLETERIA_MANTENIMIENTO;
import static pe.edu.utp.poo.application.common.Constant.UI_BOLETERIA_NUEVO;
import static pe.edu.utp.poo.application.common.Constant.UI_MAIN_LOGOUT;
import static pe.edu.utp.poo.application.common.Constant.UI_MANTENIMIENTO_PELICULAS;
import static pe.edu.utp.poo.application.common.Constant.UI_MANTENIMIENTO_USUARIOS;

/**
 *
 * @author manuelguarniz
 */
public class MenuActionListener implements ActionListener {
    private UIMainMenu uiMain;
    private final JDesktopPane mainPanel;

    private UIAcercaDe uiAcercaDe;
    private UIUsuarios uiUsuarios;
    private UIPeliculas uiPelicula;
    private UISeleccionButacas uiSeleccionButacas;
    private UIMantenimientoBoleteria uiMantenimientoBoleteria;
    private UIBoleteria uiBoleteria;
    private Acceso uiAcceso;

    public MenuActionListener(UIMainMenu uiMain) {
        this.uiMain = uiMain;
        this.mainPanel = uiMain.getPanel();
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
            case UI_MANTENIMIENTO_PELICULAS -> {
                uiPelicula=new UIPeliculas();
                mainPanel.add(uiPelicula).setVisible(true);
            }
            case UI_BOLETERIA_BUTACAS -> {
                uiSeleccionButacas = new UISeleccionButacas();
                mainPanel.add(uiSeleccionButacas).setVisible(true);
            }
            case UI_BOLETERIA_MANTENIMIENTO -> {
                uiMantenimientoBoleteria = new UIMantenimientoBoleteria();
                mainPanel.add(uiMantenimientoBoleteria).setVisible(true);
            }
            case UI_BOLETERIA_NUEVO -> {
                uiBoleteria = new UIBoleteria(uiMain);
                mainPanel.add(uiBoleteria).setVisible(true);
            }
            case UI_MAIN_LOGOUT -> {
                uiAcceso = new Acceso();
                uiAcceso.setVisible(true);
                uiAcceso.setLocationRelativeTo(null);
                uiMain.dispose();
            }
            case UI_INICIO_SALIR -> System.exit(0);
            
            
            default -> System.out.println("No action");
        }
    }

}
