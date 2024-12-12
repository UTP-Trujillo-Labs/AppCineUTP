/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pe.edu.utp.poo.application.ui;

import java.sql.SQLException;

import pe.edu.utp.poo.application.model.*;
import pe.edu.utp.poo.application.service.SeguridadService;
import pe.edu.utp.poo.application.service.VentaService;
import pe.edu.utp.poo.application.ui.dto.VentaButacasDTO;

import javax.swing.table.DefaultTableModel;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import pe.edu.utp.poo.application.service.ClienteService;
import pe.edu.utp.poo.application.service.PeliculaService;

/**
 *
 * @author axcel
 */
public class UIBoleteria extends javax.swing.JInternalFrame {
    private Consumer<VentaButacasDTO> outputOnClose = this::fnOutputOnClose;
    private UIMainMenu uiMain;
    private UISeleccionButacas uiSeleccionButacas;
    private ClienteService clienteService;
    private PeliculaService peliculaService;
    private List<Pelicula> listaPeliculas = new ArrayList<>();
    private VentaService ventaService;
    private List<String> butacasAdulto;
    private List<String> butacasNinio;

    /**
     * Creates new form UIBoleteria
     */
    
    public UIBoleteria(UIMainMenu uiMain) {
        initComponents();
        this.uiMain = uiMain;
        this.clienteService=new ClienteService();
        this.peliculaService=new PeliculaService();
        this.ventaService=new VentaService();
        this.mostrarPelicula(); 
    }
    
    private void abrirSeleccionButacas() {
        Integer cantidadTicketsAdulto = (Integer) txtTicketsAdulto.getValue();
        Integer cantidadTicketsNinio = (Integer) txtTicketsNinio.getValue();

        VentaButacasDTO dto = new VentaButacasDTO();

        dto.setCantidadAsientosAdulto(cantidadTicketsAdulto);
        dto.setCantidadAsientosNinio(cantidadTicketsNinio);
        dto.setPelicula((String) cboPelicula.getSelectedItem());
        dto.setHorario((String) cboHorario.getSelectedItem());

        uiSeleccionButacas = new UISeleccionButacas(dto, outputOnClose);
        this.uiMain.getPanel().add(uiSeleccionButacas).setVisible(true);
    }

    private void fnOutputOnClose(VentaButacasDTO dto) {
        System.out.println(dto);
        DefaultTableModel model = (DefaultTableModel) jtDetalleVenta.getModel();
        double totalVenta = 0.0;

        model.setRowCount(0);

        butacasAdulto = dto.getButacasReservadas().subList(0, dto.getCantidadAsientosAdulto());
        if (!butacasAdulto.isEmpty()) {
            double precioAdulto = 34.9;
            double subTotalVenta = precioAdulto * butacasAdulto.size();
            totalVenta += subTotalVenta;
            model.addRow(new Object[]{
                    1,
                    String.format("Tickets adulto: %d - Asiento(s): %s", butacasAdulto.size(), String.join(", ", butacasAdulto)),
                    String.format("S/. %.2f", precioAdulto),
                    butacasAdulto.size(),
                    String.format("S/. %.2f", subTotalVenta),
            });
        }

        butacasNinio = dto.getButacasReservadas().subList(dto.getCantidadAsientosAdulto(), dto.getButacasReservadas().size());
        if (!butacasNinio.isEmpty()) {
            double precioNinio = 29.9;
            double subTotalVenta = precioNinio * butacasNinio.size();
            totalVenta += subTotalVenta;
            model.addRow(new Object[]{
                    2,
                    String.format("Tickets niño: %d - Asiento(s): %s", butacasNinio.size(), String.join(", ", butacasNinio)),
                    String.format("S/. %.2f", precioNinio),
                    butacasNinio.size(),
                    String.format("S/. %.2f", subTotalVenta),
            });
        }

        lblTotalVenta.setText(String.format("%.2f", totalVenta));
    }
    
    private void limpiarCampos() {
        txtNombreCliente.setText("");
        txtApellidosCliente.setText("");
        txtNumeroDocumento.setText("");

        cboPelicula.setSelectedIndex(0);
        cboHorario.setSelectedIndex(0);
        txtTicketsAdulto.setValue(0);
        txtTicketsNinio.setValue(0);
    }
    
    private void vender(){
        try {
            String nombres=txtNombreCliente.getText();
            String apellidos=txtApellidosCliente.getText();
            String numeroDocumento=txtNumeroDocumento.getText();

            Cliente cliente=new Cliente();

            cliente.setNombres(nombres);
            cliente.setApellidos(apellidos);
            cliente.setNumeroDocumento(numeroDocumento);

            Integer clienteId = this.clienteService.insertar(cliente);
            Integer usuarioId = SeguridadService.instancia().getUsuarioSession().getUsuarioId();
            Integer peliculaId = listaPeliculas.get(cboPelicula.getSelectedIndex() - 1).getPeliculaId();
            String horario = (String) cboHorario.getSelectedItem();
            LocalDateTime fechaVenta = LocalDateTime.now();

            Integer ticketsAdulto = (Integer) txtTicketsAdulto.getValue();
            Integer ticketsNinio = (Integer) txtTicketsNinio.getValue();

            Venta venta = new Venta();
            venta.setClienteId(clienteId);
            venta.setUsuarioId(usuarioId);
            venta.setPeliculaId(peliculaId);
            venta.setHorario(horario);
            venta.setFechaVenta(fechaVenta);

            Integer ventaId = this.ventaService.insertar(venta);


            if (ticketsAdulto > 0 && butacasAdulto != null && !butacasAdulto.isEmpty()) {
                DetalleVenta detalleVenta = new DetalleVenta();
                detalleVenta.setVentaId(ventaId);
                detalleVenta.setTipoTickets("A");
                detalleVenta.setPrecio(34.9);
                detalleVenta.setDescripcion(String.format("Tickets adulto: %d - Asiento(s): %s", butacasAdulto.size(), String.join(", ", butacasAdulto)));
                detalleVenta.setCantidad(ticketsAdulto);

                Integer detalleVentaId = this.ventaService.insertarDetalle(detalleVenta);

                for (String numeroButaca : butacasAdulto) {
                    DetalleButaca detalleButaca = new DetalleButaca();
                    detalleButaca.setVentaId(ventaId);
                    detalleButaca.setDetalleVentaId(detalleVentaId);
                    detalleButaca.setNumeroButaca(numeroButaca);
                    this.ventaService.insertarDetalleButacas(detalleButaca);
                }
            }

            if (ticketsNinio > 0 && butacasNinio != null && !butacasNinio.isEmpty()) {
                DetalleVenta detalleVenta = new DetalleVenta();
                detalleVenta.setVentaId(ventaId);
                detalleVenta.setTipoTickets("N");
                detalleVenta.setPrecio(29.9);
                detalleVenta.setDescripcion(String.format("Tickets niño: %d - Asiento(s): %s", butacasNinio.size(), String.join(", ", butacasNinio)));
                detalleVenta.setCantidad(ticketsNinio);

                Integer detalleVentaId = this.ventaService.insertarDetalle(detalleVenta);

                for (String numeroButaca : butacasNinio) {
                    DetalleButaca detalleButaca = new DetalleButaca();
                    detalleButaca.setVentaId(ventaId);
                    detalleButaca.setDetalleVentaId(detalleVentaId);
                    detalleButaca.setNumeroButaca(numeroButaca);
                    this.ventaService.insertarDetalleButacas(detalleButaca);
                }
            }

            JOptionPane.showMessageDialog(this, "La venta se registro con éxito!");
        } catch (SQLException ex) {
            Logger.getLogger(UIBoleteria.class.getName()).log(Level.SEVERE, null, ex);
            
            JOptionPane.showMessageDialog(this, "Se encontró error en base de datos");
        }
        
        limpiarCampos();
        

        
    }
    
    private void mostrarPelicula(){
        try {
            listaPeliculas = this.peliculaService.findAll();
            for(Pelicula peli : listaPeliculas){
               cboPelicula.addItem(peli.getTitulo());
            }
        } catch (SQLException ex) {
            Logger.getLogger(UIBoleteria.class.getName()).log(Level.SEVERE, null, ex);
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtTicketsAdulto = new javax.swing.JSpinner();
        txtTicketsNinio = new javax.swing.JSpinner();
        cboPelicula = new javax.swing.JComboBox<>();
        cboHorario = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNombreCliente = new javax.swing.JTextField();
        txtApellidosCliente = new javax.swing.JTextField();
        txtNumeroDocumento = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        btnLimpiar = new javax.swing.JToggleButton();
        btnSeleccionarButacas = new javax.swing.JToggleButton();
        btnImprimir = new javax.swing.JToggleButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtDetalleVenta = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        lblTotalVenta = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Cant. Tickets"));

        jLabel4.setText("Pelicula:");

        jLabel5.setText("Horario:");

        jLabel6.setText("Adulto:");

        jLabel7.setText("Niño:");

        cboPelicula.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Seleccionar --" }));
        cboPelicula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPeliculaActionPerformed(evt);
            }
        });

        cboHorario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Seleccionar --", "3pm", "5pm", "7pm", "10pm" }));
        cboHorario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboHorarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cboPelicula, 0, 183, Short.MAX_VALUE)
                    .addComponent(cboHorario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTicketsNinio, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                            .addComponent(txtTicketsAdulto))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboPelicula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cboHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtTicketsAdulto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtTicketsNinio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Cliente"));

        jLabel1.setText("Nombres:");

        jLabel2.setText("Apellidos:");

        jLabel3.setText("DNI:");

        txtNombreCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtApellidosCliente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                    .addComponent(txtNumeroDocumento)
                    .addComponent(txtNombreCliente))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNombreCliente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtApellidosCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumeroDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnSeleccionarButacas.setText("Seleccionar Butacas");
        btnSeleccionarButacas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarButacasActionPerformed(evt);
            }
        });

        btnImprimir.setForeground(new java.awt.Color(51, 153, 255));
        btnImprimir.setText("Vender");
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnLimpiar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSeleccionarButacas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnImprimir)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLimpiar)
                    .addComponent(btnSeleccionarButacas)
                    .addComponent(btnImprimir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Vista Factura"));

        jtDetalleVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item", "Descripción", "Precio Unit.", "Cantidad", "Subtotal"
            }
        ));
        jtDetalleVenta.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(jtDetalleVenta);
        jtDetalleVenta.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (jtDetalleVenta.getColumnModel().getColumnCount() > 0) {
            jtDetalleVenta.getColumnModel().getColumn(0).setMaxWidth(40);
            jtDetalleVenta.getColumnModel().getColumn(2).setMaxWidth(90);
            jtDetalleVenta.getColumnModel().getColumn(3).setMaxWidth(60);
            jtDetalleVenta.getColumnModel().getColumn(4).setMaxWidth(85);
        }

        jLabel9.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel9.setText("Total: S/.");

        lblTotalVenta.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        lblTotalVenta.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotalVenta.setText("0.00");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotalVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lblTotalVenta)))
        );

        jLabel8.setFont(new java.awt.Font("Calibri Light", 1, 18)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Boleteria");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreClienteActionPerformed

    private void cboPeliculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPeliculaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboPeliculaActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnSeleccionarButacasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarButacasActionPerformed
        abrirSeleccionButacas();
    }//GEN-LAST:event_btnSeleccionarButacasActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        vender();
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void cboHorarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboHorarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboHorarioActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnImprimir;
    private javax.swing.JToggleButton btnLimpiar;
    private javax.swing.JToggleButton btnSeleccionarButacas;
    private javax.swing.JComboBox<String> cboHorario;
    private javax.swing.JComboBox<String> cboPelicula;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtDetalleVenta;
    private javax.swing.JLabel lblTotalVenta;
    private javax.swing.JTextField txtApellidosCliente;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtNumeroDocumento;
    private javax.swing.JSpinner txtTicketsAdulto;
    private javax.swing.JSpinner txtTicketsNinio;
    // End of variables declaration//GEN-END:variables
}
