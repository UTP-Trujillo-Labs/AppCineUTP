/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package pe.edu.utp.poo.application.ui;

import pe.edu.utp.poo.application.common.Util;
import pe.edu.utp.poo.application.model.Paginacion;
import pe.edu.utp.poo.application.model.ReporteVenta;
import pe.edu.utp.poo.application.service.ReporteService;
import pe.edu.utp.poo.application.util.ExcelUtil;
import pe.edu.utp.poo.application.util.OSUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import static pe.edu.utp.poo.application.common.Constant.DEFAULT_LIMITE_POR_PAGINA;

/**
 * @author manuelguarniz
 */
public class UIReporteBoleteria extends javax.swing.JInternalFrame {

    private final int maxElements = DEFAULT_LIMITE_POR_PAGINA;
    private Paginacion<ReporteVenta> paginacion;

    private ReporteService reporteService;

    /**
     * Creates new form UIMantenimientoBoleteria
     */
    public UIReporteBoleteria() {
        initComponents();
        reporteService = new ReporteService();
        cargarDatos(0, maxElements);
    }

    private void cargarDatos(int offset, int limite) {
        try {
            if (offset < 0 || (paginacion != null && offset >= paginacion.getTotalElementos())) {
                return;
            }
            paginacion = reporteService.listaPaginada(offset, limite);
            cargarTabla(paginacion);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "No se cargaron los datos correctamente");
        }
    }

    private void cargarTabla(Paginacion<ReporteVenta> paginacionVenta) {
        DefaultTableModel tableModel = (DefaultTableModel) tbVentas.getModel();

        if (paginacionVenta == null) {
            return;
        } else if (paginacionVenta.getTotalElementos() == 0) {
            JOptionPane.showMessageDialog(this, "No hay mas elementos que mostrar");
        }
        int index = paginacionVenta.getOffset();

        tableModel.setRowCount(0);
        for (ReporteVenta e : paginacionVenta.getElementos()) {
            index++;
            tableModel.addRow(new Object[]{
                    e,
                    index,
                    Util.dateToString(e.getFechaVenta()),
                    e.getTituloPelicula(),
                    e.getHorario(),
                    e.getNombreCliente(),
                    e.getCantidadTickets(),
                    e.getSubtotal(),
            });
        }

        actualizarPaginacion(paginacionVenta);
    }

    private void actualizarPaginacion(Paginacion<ReporteVenta> paginacion) {
        lblTotalElementos.setText(String.valueOf(paginacion.getTotalElementos()));
        lblPaginaActual.setText(String.valueOf(paginacion.paginaActual()));
        lblTotalPaginas.setText(String.valueOf(paginacion.totalPaginas()));
    }

    private void paginaAnterior() {
        if (paginacion == null) {
            return;
        }
        int newOffset = paginacion.getOffset() - paginacion.getLimite();
        cargarDatos(newOffset, paginacion.getLimite());
    }

    private void paginaSiguiente() {
        if (paginacion == null) {
            return;
        }
        int newOffset = paginacion.getOffset() + paginacion.getLimite();
        cargarDatos(newOffset, paginacion.getLimite());
    }

    private void actualizarTabla() {
        if (paginacion == null) {
            paginacion = new Paginacion<>();
        }

        cargarDatos(paginacion.getOffset(), paginacion.getLimite());
    }

    private void exportar() {
        if (paginacion == null) {
            JOptionPane.showMessageDialog(this, "No hay elementos para exportar");
            return;
        }
        Integer totalElementos = paginacion.getTotalElementos();
        try {
            JFileChooser exploradorArchivos = new JFileChooser();
            exploradorArchivos.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int result = exploradorArchivos.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                Paginacion<ReporteVenta> listaCompleta = reporteService.listaPaginada(0, totalElementos);

                File selectedFolder = exploradorArchivos.getSelectedFile();
                String rutaArchivoFinal = ExcelUtil.export(selectedFolder.getAbsolutePath(), listaCompleta.getElementos());

                System.out.println(rutaArchivoFinal);

                JOptionPane.showMessageDialog(null, "Archivo exportado correctamente: " + rutaArchivoFinal);
                OSUtil.mostrarArchivo(rutaArchivoFinal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "No se cargaron los datos correctamente");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "No logramos crear el archivo excel");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btnVerDetalle = new javax.swing.JButton();
        btnVerDetalle1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbVentas = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        btnPaginaSiguiente = new javax.swing.JButton();
        btnPaginaAnterior = new javax.swing.JButton();
        lblTotalElementos = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblTotalPaginas = new javax.swing.JLabel();
        lblPaginaActual = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Selección de Butacas");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 100, Short.MAX_VALUE)
        );

        setClosable(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Reporte de Ventas");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        btnVerDetalle.setText("Exportar");
        btnVerDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerDetalleActionPerformed(evt);
            }
        });

        btnVerDetalle1.setText("Actualizar");
        btnVerDetalle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerDetalle1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnVerDetalle1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnVerDetalle)
                                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnVerDetalle)
                                        .addComponent(btnVerDetalle1))
                                .addContainerGap(10, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Ventas"));

        tbVentas.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "OBJ", "#", "Fecha", "Pelicula", "Horario", "Cliente", "Cantidad Tickets", "Total Pagado"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbVentas);
        if (tbVentas.getColumnModel().getColumnCount() > 0) {
            tbVentas.getColumnModel().getColumn(0).setMinWidth(0);
            tbVentas.getColumnModel().getColumn(0).setPreferredWidth(0);
            tbVentas.getColumnModel().getColumn(0).setMaxWidth(0);
            tbVentas.getColumnModel().getColumn(1).setMaxWidth(50);
        }

        btnPaginaSiguiente.setText(">");
        btnPaginaSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPaginaSiguienteActionPerformed(evt);
            }
        });

        btnPaginaAnterior.setText("<");
        btnPaginaAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPaginaAnteriorActionPerformed(evt);
            }
        });

        lblTotalElementos.setText("0");

        jLabel4.setText("de");

        lblTotalPaginas.setText("0");

        lblPaginaActual.setText("0");

        jLabel5.setText("Total Elementos:");

        jLabel6.setText("páginas");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTotalElementos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnPaginaAnterior)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblPaginaActual, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTotalPaginas, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPaginaSiguiente)
                                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addContainerGap(8, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnPaginaSiguiente)
                                        .addComponent(lblTotalElementos)
                                        .addComponent(jLabel4)
                                        .addComponent(lblTotalPaginas)
                                        .addComponent(btnPaginaAnterior)
                                        .addComponent(lblPaginaActual)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel6))
                                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 836, Short.MAX_VALUE)
                                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVerDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerDetalleActionPerformed
        exportar();
    }//GEN-LAST:event_btnVerDetalleActionPerformed

    private void btnPaginaAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaginaAnteriorActionPerformed
        paginaAnterior();
    }//GEN-LAST:event_btnPaginaAnteriorActionPerformed

    private void btnPaginaSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaginaSiguienteActionPerformed
        paginaSiguiente();
    }//GEN-LAST:event_btnPaginaSiguienteActionPerformed

    private void btnVerDetalle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerDetalle1ActionPerformed
        actualizarTabla();
    }//GEN-LAST:event_btnVerDetalle1ActionPerformed


//    private void poblarDB() {
//        try {
//            List<Venta> list = new ArrayList<>();
//            Double precio = 17.0;
//            list.add(new Venta(LocalDateTime.now(), "Coco", "8pm", 3, precio * 3));
//            TimeUnit.SECONDS.sleep(1);
//            list.add(new Venta(LocalDateTime.now(), "Coco", "8pm", 2, precio * 2));
//            TimeUnit.SECONDS.sleep(1);
//            list.add(new Venta(LocalDateTime.now(), "Coco", "8pm", 1, precio * 1));
//            TimeUnit.SECONDS.sleep(1);
//            list.add(new Venta(LocalDateTime.now(), "Coco", "8pm", 4, precio * 4));
//            TimeUnit.SECONDS.sleep(1);
//            list.add(new Venta(LocalDateTime.now(), "Coco", "6pm", 1, precio * 1));
//            TimeUnit.SECONDS.sleep(1);
//            list.add(new Venta(LocalDateTime.now(), "Coco", "6pm", 1, precio * 1));
//            TimeUnit.SECONDS.sleep(1);
//            list.add(new Venta(LocalDateTime.now(), "Coco", "8pm", 3, precio * 3));
//            TimeUnit.SECONDS.sleep(1);
//            list.add(new Venta(LocalDateTime.now(), "Coco", "6pm", 4, precio * 4));
//            TimeUnit.SECONDS.sleep(1);
//            list.add(new Venta(LocalDateTime.now(), "Coco", "8pm", 5, precio * 5));
//            TimeUnit.SECONDS.sleep(1);
//            list.add(new Venta(LocalDateTime.now(), "Intensamente", "6pm", 1, precio * 1));
//            TimeUnit.SECONDS.sleep(1);
//            list.add(new Venta(LocalDateTime.now(), "Intensamente", "6pm", 3, precio * 3));
//            TimeUnit.SECONDS.sleep(1);
//            list.add(new Venta(LocalDateTime.now(), "Intensamente", "8pm", 2, precio * 2));
//            TimeUnit.SECONDS.sleep(1);
//            list.add(new Venta(LocalDateTime.now(), "Intensamente", "8pm", 1, precio * 1));
//            TimeUnit.SECONDS.sleep(1);
//            list.add(new Venta(LocalDateTime.now(), "Intensamente", "6pm", 2, precio * 2));
//            TimeUnit.SECONDS.sleep(1);
//            list.add(new Venta(LocalDateTime.now(), "Intensamente", "6pm", 2, precio * 2));
//            TimeUnit.SECONDS.sleep(1);
//            list.add(new Venta(LocalDateTime.now(), "Intensamente", "6pm", 2, precio * 2));
//            TimeUnit.SECONDS.sleep(1);
//            list.add(new Venta(LocalDateTime.now(), "Intensamente", "8pm", 2, precio * 2));
//            TimeUnit.SECONDS.sleep(1);
//            list.add(new Venta(LocalDateTime.now(), "Vivo", "2pm", 2, precio * 2));
//            TimeUnit.SECONDS.sleep(1);
//            list.add(new Venta(LocalDateTime.now(), "Vivo", "2pm", 2, precio * 2));
//            TimeUnit.SECONDS.sleep(1);
//            list.add(new Venta(LocalDateTime.now(), "Vivo", "2pm", 3, precio * 3));
//            TimeUnit.SECONDS.sleep(1);
//            list.add(new Venta(LocalDateTime.now(), "Vivo", "4pm", 3, precio * 3));
//            TimeUnit.SECONDS.sleep(1);
//            list.add(new Venta(LocalDateTime.now(), "Vivo", "4pm", 3, precio * 3));
//            TimeUnit.SECONDS.sleep(1);
//            list.add(new Venta(LocalDateTime.now(), "Vivo", "4pm", 3, precio * 3));
//            TimeUnit.SECONDS.sleep(1);
//            list.add(new Venta(LocalDateTime.now(), "Vivo", "2pm", 3, precio * 3));
//            TimeUnit.SECONDS.sleep(1);
//            list.add(new Venta(LocalDateTime.now(), "Vivo", "2pm", 2, precio * 2));
//            TimeUnit.SECONDS.sleep(1);
//            list.add(new Venta(LocalDateTime.now(), "Vivo", "5pm", 2, precio * 2));
//            TimeUnit.SECONDS.sleep(1);
//            list.add(new Venta(LocalDateTime.now(), "Vivo", "5pm", 2, precio * 2));
//            TimeUnit.SECONDS.sleep(1);
//            list.add(new Venta(LocalDateTime.now(), "Vivo", "5pm", 2, precio * 2));
//            TimeUnit.SECONDS.sleep(1);
//            list.add(new Venta(LocalDateTime.now(), "Vivo", "2pm", 2, precio * 2));
//
//            for (Venta v : list) {
//                VentaRepositorio.getInstance().save(v);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (InterruptedException ex) {
//            ex.printStackTrace();
//        }
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPaginaAnterior;
    private javax.swing.JButton btnPaginaSiguiente;
    private javax.swing.JButton btnVerDetalle;
    private javax.swing.JButton btnVerDetalle1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblPaginaActual;
    private javax.swing.JLabel lblTotalElementos;
    private javax.swing.JLabel lblTotalPaginas;
    private javax.swing.JTable tbVentas;
    // End of variables declaration//GEN-END:variables
}
