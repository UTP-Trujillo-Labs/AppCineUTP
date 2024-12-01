/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package pe.edu.utp.poo.application.ui;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.swing.JOptionPane;
import pe.edu.utp.poo.application.enums.EstadoBustacaEnum;
import pe.edu.utp.poo.application.ui.dto.VentaButacasDTO;

import static pe.edu.utp.poo.application.enums.EstadoBustacaEnum.Disponible;
import static pe.edu.utp.poo.application.enums.EstadoBustacaEnum.NULL;
import static pe.edu.utp.poo.application.enums.EstadoBustacaEnum.Ocupado;

/**
 *
 * @author manuelguarniz
 */
public class UISeleccionButacas extends javax.swing.JInternalFrame {
    private Consumer<VentaButacasDTO> outputOnClose;
    private VentaButacasDTO dto;

    private Map<String, EstadoBustacaEnum[]> butacas = new HashMap<>();
    private List<String> butacasSeleccionadas = new ArrayList<>();

    public UISeleccionButacas() {
        this(null, null);
    }
    public UISeleccionButacas(VentaButacasDTO dto, Consumer<VentaButacasDTO> outputOnClose) {
        this.outputOnClose = outputOnClose == null ? (VentaButacasDTO data) -> {} : outputOnClose;
        this.dto = outputOnClose == null
                ? new VentaButacasDTO("", "", 0, 0)
                : dto;
        this.initComponents();
        this.setControles();
        // TODO: Cambiar por defecto a lectura desde base de datos
        this.defaultButacas();
        this.reloadButacas();
    }

    private void setControles() {
        txtPelicula.setText(dto.getPelicula());
        txtHorario.setText(dto.getHorario());
        txtButacasAdulto.setValue(dto.getCantidadAsientosAdulto());
        txtButacasNino.setValue(dto.getCantidadAsientosNinio());
    }
    
    private void defaultButacas() {
        butacas.clear();
        butacas.put("A", new EstadoBustacaEnum[] {Disponible, Disponible, NULL, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, NULL, Disponible});
        butacas.put("B", new EstadoBustacaEnum[] {Disponible, Disponible, NULL, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, NULL, Disponible});
        butacas.put("C", new EstadoBustacaEnum[] {Disponible, Disponible, NULL, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, NULL, Disponible});
        butacas.put("D", new EstadoBustacaEnum[] {Disponible, Disponible, NULL, Ocupado, Ocupado, Ocupado, Ocupado, Ocupado, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, NULL, Disponible});
        butacas.put("E", new EstadoBustacaEnum[] {Disponible, Disponible, NULL, Ocupado, Ocupado, Ocupado, Ocupado, Ocupado, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, NULL, Disponible});
        butacas.put("F", new EstadoBustacaEnum[] {Ocupado, Ocupado, NULL, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, NULL, Disponible});
        butacas.put("G", new EstadoBustacaEnum[] {Disponible, Disponible, NULL, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, NULL, Disponible});
        butacas.put("H", new EstadoBustacaEnum[] {Ocupado, Ocupado, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, Disponible, Ocupado, Ocupado});

    }
    
    private void reloadButacas() {
        Component[] component = jplButacas.getComponents();
        for (Component c : component) {
            if (c instanceof javax.swing.JLabel) {
                javax.swing.JLabel label = (javax.swing.JLabel) c;
                
                String butaca = label.getText();
                
                String fila = butaca.substring(0, 1);
                int columna = Integer.parseInt(butaca.substring(1, butaca.length())) - 1;
                           
                EstadoBustacaEnum estadoButaca = butacas.get(fila)[columna];

                if (EstadoBustacaEnum.NULL.equals(estadoButaca)) {
                    label.setVisible(false);
                } else {
                    label.setBackground(estadoButaca.getColor());
                    label.setForeground(estadoButaca.getColor());
                }
                
            }
        }
        
    }
    
    private void seleccionarButaca(java.awt.event.MouseEvent evt) {
        javax.swing.JLabel label = (javax.swing.JLabel) evt.getSource();
        EstadoBustacaEnum estadoActual = EstadoBustacaEnum.parseEnum(label.getBackground());
        String numeroButaca = label.getText();
        Integer butacasAdulto = (int) txtButacasAdulto.getValue();
        Integer butacasNino = (int) txtButacasNino.getValue();
        
        if (butacasSeleccionadas.size() >= (butacasAdulto + butacasNino)) {
            JOptionPane.showMessageDialog(this, "Ya seleccionaste todas las butacas compradas");
            return;
        }
        
        switch (estadoActual) {
            case Disponible -> {
                label.setBackground(EstadoBustacaEnum.Seleccion.getColor());
                label.setForeground(EstadoBustacaEnum.Seleccion.getColor());
                this.butacasSeleccionadas.add(numeroButaca);
            }
            case Seleccion -> {
                label.setBackground(EstadoBustacaEnum.Disponible.getColor());
                label.setForeground(EstadoBustacaEnum.Disponible.getColor());
                this.butacasSeleccionadas.remove(numeroButaca);
            }
            default -> JOptionPane.showMessageDialog(this, "La butaca está ocupada");
        }
    }
    
    private void continuar() {
        this.butacasSeleccionadas.forEach(e -> System.out.println("Butaca seleccionada: " + e));

        dto.setCantidadAsientosAdulto(Integer.parseInt(txtButacasAdulto.getValue().toString()));
        dto.setCantidadAsientosNinio(Integer.parseInt(txtButacasNino.getValue().toString()));
        dto.setButacasReservadas(this.butacasSeleccionadas);

        outputOnClose.accept(dto);
        this.dispose();
//        Component[] component = jplButacas.getComponents();
//        for (Component c : component) {
//            if (c instanceof javax.swing.JLabel) {
//                javax.swing.JLabel label = (javax.swing.JLabel) c;
//                EstadoBustacaEnum estadoActual = EstadoBustacaEnum.parseEnum(label.getBackground());
//                
//                if (EstadoBustacaEnum.Seleccion.equals(estadoActual)) {
//                    System.out.println("Butaca seleccionada: " + label.getText());
//                }
//            }
//        }
    }
    
    private void reiniciar() {
        reloadButacas();
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
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtHorario = new javax.swing.JTextPane();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtPelicula = new javax.swing.JTextPane();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtButacasAdulto = new javax.swing.JSpinner();
        txtButacasNino = new javax.swing.JSpinner();
        jPanel5 = new javax.swing.JPanel();
        btnContinuar = new javax.swing.JButton();
        btnReiniciar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jplButacas = new javax.swing.JPanel();
        lblH1 = new javax.swing.JLabel();
        lblH6 = new javax.swing.JLabel();
        lblB1 = new javax.swing.JLabel();
        lblC1 = new javax.swing.JLabel();
        lblD1 = new javax.swing.JLabel();
        lblE1 = new javax.swing.JLabel();
        lblF1 = new javax.swing.JLabel();
        lblG1 = new javax.swing.JLabel();
        lblA1 = new javax.swing.JLabel();
        lblA2 = new javax.swing.JLabel();
        lblB2 = new javax.swing.JLabel();
        lblC2 = new javax.swing.JLabel();
        lblD2 = new javax.swing.JLabel();
        lblE2 = new javax.swing.JLabel();
        lblF2 = new javax.swing.JLabel();
        lblG2 = new javax.swing.JLabel();
        lblH2 = new javax.swing.JLabel();
        lblA3 = new javax.swing.JLabel();
        lblB3 = new javax.swing.JLabel();
        lblC3 = new javax.swing.JLabel();
        lblD3 = new javax.swing.JLabel();
        lblE3 = new javax.swing.JLabel();
        lblF3 = new javax.swing.JLabel();
        lblG3 = new javax.swing.JLabel();
        lblH3 = new javax.swing.JLabel();
        lblA4 = new javax.swing.JLabel();
        lblB4 = new javax.swing.JLabel();
        lblC4 = new javax.swing.JLabel();
        lblD4 = new javax.swing.JLabel();
        lblE4 = new javax.swing.JLabel();
        lblF4 = new javax.swing.JLabel();
        lblG4 = new javax.swing.JLabel();
        lblH4 = new javax.swing.JLabel();
        lblA5 = new javax.swing.JLabel();
        lblB5 = new javax.swing.JLabel();
        lblC5 = new javax.swing.JLabel();
        lblD5 = new javax.swing.JLabel();
        lblE5 = new javax.swing.JLabel();
        lblF5 = new javax.swing.JLabel();
        lblG5 = new javax.swing.JLabel();
        lblH5 = new javax.swing.JLabel();
        lblA9 = new javax.swing.JLabel();
        lblB6 = new javax.swing.JLabel();
        lblC6 = new javax.swing.JLabel();
        lblD6 = new javax.swing.JLabel();
        lblE6 = new javax.swing.JLabel();
        lblF6 = new javax.swing.JLabel();
        lblG6 = new javax.swing.JLabel();
        lblA6 = new javax.swing.JLabel();
        lblA7 = new javax.swing.JLabel();
        lblB7 = new javax.swing.JLabel();
        lblC7 = new javax.swing.JLabel();
        lblD7 = new javax.swing.JLabel();
        lblE7 = new javax.swing.JLabel();
        lblF7 = new javax.swing.JLabel();
        lblG7 = new javax.swing.JLabel();
        lblH7 = new javax.swing.JLabel();
        lblA8 = new javax.swing.JLabel();
        lblB8 = new javax.swing.JLabel();
        lblC8 = new javax.swing.JLabel();
        lblD8 = new javax.swing.JLabel();
        lblE8 = new javax.swing.JLabel();
        lblF8 = new javax.swing.JLabel();
        lblG8 = new javax.swing.JLabel();
        lblG10 = new javax.swing.JLabel();
        lblH8 = new javax.swing.JLabel();
        lblB9 = new javax.swing.JLabel();
        lblC9 = new javax.swing.JLabel();
        lblD9 = new javax.swing.JLabel();
        lblE9 = new javax.swing.JLabel();
        lblF9 = new javax.swing.JLabel();
        lblG9 = new javax.swing.JLabel();
        lblH9 = new javax.swing.JLabel();
        lblA10 = new javax.swing.JLabel();
        lblB10 = new javax.swing.JLabel();
        lblC10 = new javax.swing.JLabel();
        lblD10 = new javax.swing.JLabel();
        lblE10 = new javax.swing.JLabel();
        lblF10 = new javax.swing.JLabel();
        lblH16 = new javax.swing.JLabel();
        lblH10 = new javax.swing.JLabel();
        lblA11 = new javax.swing.JLabel();
        lblB11 = new javax.swing.JLabel();
        lblC11 = new javax.swing.JLabel();
        lblD11 = new javax.swing.JLabel();
        lblE11 = new javax.swing.JLabel();
        lblF11 = new javax.swing.JLabel();
        lblG11 = new javax.swing.JLabel();
        lblH11 = new javax.swing.JLabel();
        lblA12 = new javax.swing.JLabel();
        lblB12 = new javax.swing.JLabel();
        lblC12 = new javax.swing.JLabel();
        lblD12 = new javax.swing.JLabel();
        lblE12 = new javax.swing.JLabel();
        lblF12 = new javax.swing.JLabel();
        lblG12 = new javax.swing.JLabel();
        lblH12 = new javax.swing.JLabel();
        lblA13 = new javax.swing.JLabel();
        lblB13 = new javax.swing.JLabel();
        lblC13 = new javax.swing.JLabel();
        lblD13 = new javax.swing.JLabel();
        lblE13 = new javax.swing.JLabel();
        lblF13 = new javax.swing.JLabel();
        lblG13 = new javax.swing.JLabel();
        lblH13 = new javax.swing.JLabel();
        lblA14 = new javax.swing.JLabel();
        lblB14 = new javax.swing.JLabel();
        lblC14 = new javax.swing.JLabel();
        lblD14 = new javax.swing.JLabel();
        lblE14 = new javax.swing.JLabel();
        lblF14 = new javax.swing.JLabel();
        lblG14 = new javax.swing.JLabel();
        lblH14 = new javax.swing.JLabel();
        lblA15 = new javax.swing.JLabel();
        lblB15 = new javax.swing.JLabel();
        lblC15 = new javax.swing.JLabel();
        lblD15 = new javax.swing.JLabel();
        lblE15 = new javax.swing.JLabel();
        lblF15 = new javax.swing.JLabel();
        lblG15 = new javax.swing.JLabel();
        lblH15 = new javax.swing.JLabel();
        lblA16 = new javax.swing.JLabel();
        lblB16 = new javax.swing.JLabel();
        lblC16 = new javax.swing.JLabel();
        lblD16 = new javax.swing.JLabel();
        lblE16 = new javax.swing.JLabel();
        lblF16 = new javax.swing.JLabel();
        lblG16 = new javax.swing.JLabel();

        setClosable(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Selección de Butacas");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalles Pelicula"));

        jLabel2.setText("Pelicula:");

        jScrollPane1.setViewportView(txtHorario);

        jLabel3.setText("Horario:");

        jScrollPane2.setViewportView(txtPelicula);

        jLabel4.setText("Niño:");

        jLabel5.setText("Adulto:");

        txtButacasAdulto.setModel(new javax.swing.SpinnerNumberModel(0, null, 20, 1));

        txtButacasNino.setModel(new javax.swing.SpinnerNumberModel(0, null, 20, 1));
        txtButacasNino.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtButacasAdulto, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtButacasNino, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(txtButacasAdulto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(txtButacasNino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 10, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        btnContinuar.setText("Continuar");
        btnContinuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContinuarActionPerformed(evt);
            }
        });

        btnReiniciar.setText("Reiniciar");
        btnReiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReiniciarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnReiniciar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnContinuar)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnContinuar)
                    .addComponent(btnReiniciar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Butacas"));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("A");
        jLabel17.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jLabel17.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel4.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("B");
        jLabel18.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jLabel18.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel4.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, -1, -1));

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("C");
        jLabel19.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jLabel19.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel4.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, -1, -1));

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("D");
        jLabel20.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jLabel20.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel4.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, -1, -1));

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("E");
        jLabel21.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jLabel21.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel4.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, -1, -1));

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("F");
        jLabel22.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jLabel22.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel4.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, -1, -1));

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("G");
        jLabel23.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jLabel23.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel4.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, -1, -1));

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("H");
        jLabel16.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jLabel16.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel4.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, -1, -1));

        jPanel6.setBackground(new java.awt.Color(255, 204, 204));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel29.setBackground(new java.awt.Color(255, 204, 204));
        jLabel29.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("PANTALLA");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Informativo"));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setText("Pantalla");
        jPanel7.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 70, -1));

        jLabel7.setText("Disponible");
        jPanel7.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, 70, -1));

        jLabel8.setText("Ocupado");
        jPanel7.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, 70, -1));

        jLabel9.setText("Selección");
        jPanel7.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, 70, -1));

        jLabel24.setBackground(new java.awt.Color(153, 204, 255));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jLabel24.setOpaque(true);
        jLabel24.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel7.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        jLabel25.setBackground(new java.awt.Color(255, 204, 204));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setToolTipText("");
        jLabel25.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Helvetica Neue", 0, 13), new java.awt.Color(255, 255, 255))); // NOI18N
        jLabel25.setOpaque(true);
        jLabel25.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel7.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jLabel26.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel7.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        jLabel27.setBackground(new java.awt.Color(204, 204, 204));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jLabel27.setOpaque(true);
        jLabel27.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel7.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, -1, -1));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("16");
        jLabel28.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jLabel28.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel10.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 0, -1, -1));

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("1");
        jLabel30.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jLabel30.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel10.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("2");
        jLabel31.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jLabel31.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel10.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, -1, -1));

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("3");
        jLabel32.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jLabel32.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel10.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 0, -1, -1));

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("4");
        jLabel33.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jLabel33.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel10.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 0, -1, -1));

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("5");
        jLabel34.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jLabel34.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel10.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 0, -1, -1));

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("6");
        jLabel35.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jLabel35.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel10.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 0, -1, -1));

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("7");
        jLabel36.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jLabel36.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel10.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, -1, -1));

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("8");
        jLabel37.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jLabel37.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel10.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 0, -1, -1));

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setText("9");
        jLabel38.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jLabel38.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel10.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 0, -1, -1));

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("10");
        jLabel39.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jLabel39.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel10.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 0, -1, -1));

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setText("11");
        jLabel40.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jLabel40.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel10.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 0, -1, -1));

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setText("12");
        jLabel41.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jLabel41.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel10.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, -1, -1));

        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel42.setText("13");
        jLabel42.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jLabel42.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel10.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 0, -1, -1));

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setText("14");
        jLabel43.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jLabel43.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel10.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 0, -1, -1));

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setText("15");
        jLabel44.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jLabel44.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel10.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 0, -1, -1));

        jplButacas.setBackground(new java.awt.Color(255, 255, 255));
        jplButacas.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jplButacas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblH1.setBackground(new java.awt.Color(255, 255, 255));
        lblH1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblH1.setText("H1");
        lblH1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblH1.setOpaque(true);
        lblH1.setPreferredSize(new java.awt.Dimension(32, 32));
        lblH1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblH1MousePressed(evt);
            }
        });
        jplButacas.add(lblH1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, -1, -1));

        lblH6.setBackground(new java.awt.Color(255, 255, 255));
        lblH6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblH6.setText("H6");
        lblH6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblH6.setOpaque(true);
        lblH6.setPreferredSize(new java.awt.Dimension(32, 32));
        lblH6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblH6MousePressed(evt);
            }
        });
        jplButacas.add(lblH6, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 290, -1, -1));

        lblB1.setBackground(new java.awt.Color(255, 255, 255));
        lblB1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblB1.setText("B1");
        lblB1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblB1.setOpaque(true);
        lblB1.setPreferredSize(new java.awt.Dimension(32, 32));
        lblB1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblB1MousePressed(evt);
            }
        });
        jplButacas.add(lblB1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        lblC1.setBackground(new java.awt.Color(255, 255, 255));
        lblC1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblC1.setText("C1");
        lblC1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblC1.setOpaque(true);
        lblC1.setPreferredSize(new java.awt.Dimension(32, 32));
        lblC1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblC1MousePressed(evt);
            }
        });
        jplButacas.add(lblC1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        lblD1.setBackground(new java.awt.Color(255, 255, 255));
        lblD1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblD1.setText("D1");
        lblD1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblD1.setOpaque(true);
        lblD1.setPreferredSize(new java.awt.Dimension(32, 32));
        lblD1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblD1MousePressed(evt);
            }
        });
        jplButacas.add(lblD1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        lblE1.setBackground(new java.awt.Color(255, 255, 255));
        lblE1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblE1.setText("E1");
        lblE1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblE1.setOpaque(true);
        lblE1.setPreferredSize(new java.awt.Dimension(32, 32));
        lblE1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblE1MousePressed(evt);
            }
        });
        jplButacas.add(lblE1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, -1));

        lblF1.setBackground(new java.awt.Color(255, 255, 255));
        lblF1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblF1.setText("F1");
        lblF1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblF1.setOpaque(true);
        lblF1.setPreferredSize(new java.awt.Dimension(32, 32));
        lblF1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblF1MousePressed(evt);
            }
        });
        jplButacas.add(lblF1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, -1));

        lblG1.setBackground(new java.awt.Color(255, 255, 255));
        lblG1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblG1.setText("G1");
        lblG1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblG1.setOpaque(true);
        lblG1.setPreferredSize(new java.awt.Dimension(32, 32));
        lblG1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblG1MousePressed(evt);
            }
        });
        jplButacas.add(lblG1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, -1, -1));

        lblA1.setBackground(new java.awt.Color(255, 255, 255));
        lblA1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblA1.setText("A1");
        lblA1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblA1.setOpaque(true);
        lblA1.setPreferredSize(new java.awt.Dimension(32, 32));
        lblA1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblA1MousePressed(evt);
            }
        });
        jplButacas.add(lblA1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        lblA2.setBackground(new java.awt.Color(255, 255, 255));
        lblA2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblA2.setText("A2");
        lblA2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblA2.setOpaque(true);
        lblA2.setPreferredSize(new java.awt.Dimension(32, 32));
        lblA2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblA2MousePressed(evt);
            }
        });
        jplButacas.add(lblA2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, -1));

        lblB2.setBackground(new java.awt.Color(255, 255, 255));
        lblB2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblB2.setText("B2");
        lblB2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblB2.setOpaque(true);
        lblB2.setPreferredSize(new java.awt.Dimension(32, 32));
        lblB2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblB2MousePressed(evt);
            }
        });
        jplButacas.add(lblB2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, -1, -1));

        lblC2.setBackground(new java.awt.Color(255, 255, 255));
        lblC2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblC2.setText("C2");
        lblC2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblC2.setOpaque(true);
        lblC2.setPreferredSize(new java.awt.Dimension(32, 32));
        lblC2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblC2MousePressed(evt);
            }
        });
        jplButacas.add(lblC2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, -1, -1));

        lblD2.setBackground(new java.awt.Color(255, 255, 255));
        lblD2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblD2.setText("D2");
        lblD2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblD2.setOpaque(true);
        lblD2.setPreferredSize(new java.awt.Dimension(32, 32));
        lblD2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblD2MousePressed(evt);
            }
        });
        jplButacas.add(lblD2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, -1, -1));

        lblE2.setBackground(new java.awt.Color(255, 255, 255));
        lblE2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblE2.setText("E2");
        lblE2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblE2.setOpaque(true);
        lblE2.setPreferredSize(new java.awt.Dimension(32, 32));
        lblE2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblE2MousePressed(evt);
            }
        });
        jplButacas.add(lblE2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, -1, -1));

        lblF2.setBackground(new java.awt.Color(255, 255, 255));
        lblF2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblF2.setText("F2");
        lblF2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblF2.setOpaque(true);
        lblF2.setPreferredSize(new java.awt.Dimension(32, 32));
        lblF2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblF2MousePressed(evt);
            }
        });
        jplButacas.add(lblF2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, -1, -1));

        lblG2.setBackground(new java.awt.Color(255, 255, 255));
        lblG2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblG2.setText("G2");
        lblG2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblG2.setOpaque(true);
        lblG2.setPreferredSize(new java.awt.Dimension(32, 32));
        lblG2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblG2MousePressed(evt);
            }
        });
        jplButacas.add(lblG2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, -1, -1));

        lblH2.setBackground(new java.awt.Color(255, 255, 255));
        lblH2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblH2.setText("H2");
        lblH2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblH2.setOpaque(true);
        lblH2.setPreferredSize(new java.awt.Dimension(32, 32));
        lblH2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblH2MousePressed(evt);
            }
        });
        jplButacas.add(lblH2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 290, -1, -1));

        lblA3.setBackground(new java.awt.Color(255, 255, 255));
        lblA3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblA3.setText("A3");
        lblA3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblA3.setOpaque(true);
        lblA3.setPreferredSize(new java.awt.Dimension(32, 32));
        lblA3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblA3MousePressed(evt);
            }
        });
        jplButacas.add(lblA3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, -1, -1));

        lblB3.setBackground(new java.awt.Color(255, 255, 255));
        lblB3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblB3.setText("B3");
        lblB3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblB3.setOpaque(true);
        lblB3.setPreferredSize(new java.awt.Dimension(32, 32));
        lblB3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblB3MousePressed(evt);
            }
        });
        jplButacas.add(lblB3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, -1, -1));

        lblC3.setBackground(new java.awt.Color(255, 255, 255));
        lblC3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblC3.setText("C3");
        lblC3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblC3.setOpaque(true);
        lblC3.setPreferredSize(new java.awt.Dimension(32, 32));
        lblC3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblC3MousePressed(evt);
            }
        });
        jplButacas.add(lblC3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, -1, -1));

        lblD3.setBackground(new java.awt.Color(255, 255, 255));
        lblD3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblD3.setText("D3");
        lblD3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblD3.setOpaque(true);
        lblD3.setPreferredSize(new java.awt.Dimension(32, 32));
        lblD3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblD3MousePressed(evt);
            }
        });
        jplButacas.add(lblD3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, -1, -1));

        lblE3.setBackground(new java.awt.Color(255, 255, 255));
        lblE3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblE3.setText("E3");
        lblE3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblE3.setOpaque(true);
        lblE3.setPreferredSize(new java.awt.Dimension(32, 32));
        lblE3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblE3MousePressed(evt);
            }
        });
        jplButacas.add(lblE3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, -1, -1));

        lblF3.setBackground(new java.awt.Color(255, 255, 255));
        lblF3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblF3.setText("F3");
        lblF3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblF3.setOpaque(true);
        lblF3.setPreferredSize(new java.awt.Dimension(32, 32));
        lblF3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblF3MouseClicked(evt);
            }
        });
        jplButacas.add(lblF3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, -1, -1));

        lblG3.setBackground(new java.awt.Color(255, 255, 255));
        lblG3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblG3.setText("G3");
        lblG3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblG3.setOpaque(true);
        lblG3.setPreferredSize(new java.awt.Dimension(32, 32));
        lblG3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblG3MouseClicked(evt);
            }
        });
        jplButacas.add(lblG3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 250, -1, -1));

        lblH3.setBackground(new java.awt.Color(255, 255, 255));
        lblH3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblH3.setText("H3");
        lblH3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblH3.setOpaque(true);
        lblH3.setPreferredSize(new java.awt.Dimension(32, 32));
        lblH3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblH3MouseClicked(evt);
            }
        });
        jplButacas.add(lblH3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 290, -1, -1));

        lblA4.setBackground(new java.awt.Color(255, 255, 255));
        lblA4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblA4.setText("A4");
        lblA4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblA4.setOpaque(true);
        lblA4.setPreferredSize(new java.awt.Dimension(32, 32));
        lblA4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblA4MousePressed(evt);
            }
        });
        jplButacas.add(lblA4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, -1, -1));

        lblB4.setBackground(new java.awt.Color(255, 255, 255));
        lblB4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblB4.setText("B4");
        lblB4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblB4.setOpaque(true);
        lblB4.setPreferredSize(new java.awt.Dimension(32, 32));
        lblB4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblB4MousePressed(evt);
            }
        });
        jplButacas.add(lblB4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, -1, -1));

        lblC4.setBackground(new java.awt.Color(255, 255, 255));
        lblC4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblC4.setText("C4");
        lblC4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblC4.setOpaque(true);
        lblC4.setPreferredSize(new java.awt.Dimension(32, 32));
        lblC4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblC4MousePressed(evt);
            }
        });
        jplButacas.add(lblC4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, -1, -1));

        lblD4.setBackground(new java.awt.Color(255, 255, 255));
        lblD4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblD4.setText("D4");
        lblD4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblD4.setOpaque(true);
        lblD4.setPreferredSize(new java.awt.Dimension(32, 32));
        lblD4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblD4MousePressed(evt);
            }
        });
        jplButacas.add(lblD4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, -1, -1));

        lblE4.setBackground(new java.awt.Color(255, 255, 255));
        lblE4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblE4.setText("E4");
        lblE4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblE4.setOpaque(true);
        lblE4.setPreferredSize(new java.awt.Dimension(32, 32));
        lblE4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblE4MousePressed(evt);
            }
        });
        jplButacas.add(lblE4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, -1, -1));

        lblF4.setBackground(new java.awt.Color(255, 255, 255));
        lblF4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblF4.setText("F4");
        lblF4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblF4.setOpaque(true);
        lblF4.setPreferredSize(new java.awt.Dimension(32, 32));
        lblF4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblF4MousePressed(evt);
            }
        });
        jplButacas.add(lblF4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 210, -1, -1));

        lblG4.setBackground(new java.awt.Color(255, 255, 255));
        lblG4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblG4.setText("G4");
        lblG4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblG4.setOpaque(true);
        lblG4.setPreferredSize(new java.awt.Dimension(32, 32));
        lblG4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblG4MousePressed(evt);
            }
        });
        jplButacas.add(lblG4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 250, -1, -1));

        lblH4.setBackground(new java.awt.Color(255, 255, 255));
        lblH4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblH4.setText("H4");
        lblH4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblH4.setOpaque(true);
        lblH4.setPreferredSize(new java.awt.Dimension(32, 32));
        lblH4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblH4MousePressed(evt);
            }
        });
        jplButacas.add(lblH4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 290, -1, -1));

        lblA5.setBackground(new java.awt.Color(255, 255, 255));
        lblA5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblA5.setText("A5");
        lblA5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblA5.setOpaque(true);
        lblA5.setPreferredSize(new java.awt.Dimension(32, 32));
        lblA5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblA5MousePressed(evt);
            }
        });
        jplButacas.add(lblA5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, -1, -1));

        lblB5.setBackground(new java.awt.Color(255, 255, 255));
        lblB5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblB5.setText("B5");
        lblB5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblB5.setOpaque(true);
        lblB5.setPreferredSize(new java.awt.Dimension(32, 32));
        lblB5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblB5MousePressed(evt);
            }
        });
        jplButacas.add(lblB5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, -1, -1));

        lblC5.setBackground(new java.awt.Color(255, 255, 255));
        lblC5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblC5.setText("C5");
        lblC5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblC5.setOpaque(true);
        lblC5.setPreferredSize(new java.awt.Dimension(32, 32));
        lblC5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblC5MousePressed(evt);
            }
        });
        jplButacas.add(lblC5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 90, -1, -1));

        lblD5.setBackground(new java.awt.Color(255, 255, 255));
        lblD5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblD5.setText("D5");
        lblD5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblD5.setOpaque(true);
        lblD5.setPreferredSize(new java.awt.Dimension(32, 32));
        lblD5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblD5MousePressed(evt);
            }
        });
        jplButacas.add(lblD5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 130, -1, -1));

        lblE5.setBackground(new java.awt.Color(255, 255, 255));
        lblE5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblE5.setText("E5");
        lblE5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblE5.setOpaque(true);
        lblE5.setPreferredSize(new java.awt.Dimension(32, 32));
        lblE5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblE5MousePressed(evt);
            }
        });
        jplButacas.add(lblE5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 170, -1, -1));

        lblF5.setBackground(new java.awt.Color(255, 255, 255));
        lblF5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblF5.setText("F5");
        lblF5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblF5.setOpaque(true);
        lblF5.setPreferredSize(new java.awt.Dimension(32, 32));
        lblF5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblF5MousePressed(evt);
            }
        });
        jplButacas.add(lblF5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 210, -1, -1));

        lblG5.setBackground(new java.awt.Color(255, 255, 255));
        lblG5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblG5.setText("G5");
        lblG5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblG5.setOpaque(true);
        lblG5.setPreferredSize(new java.awt.Dimension(32, 32));
        lblG5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblG5MousePressed(evt);
            }
        });
        jplButacas.add(lblG5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 250, -1, -1));

        lblH5.setBackground(new java.awt.Color(255, 255, 255));
        lblH5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblH5.setText("H5");
        lblH5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblH5.setOpaque(true);
        lblH5.setPreferredSize(new java.awt.Dimension(32, 32));
        lblH5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblH5MousePressed(evt);
            }
        });
        jplButacas.add(lblH5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 290, -1, -1));

        lblA9.setBackground(new java.awt.Color(255, 255, 255));
        lblA9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblA9.setText("A9");
        lblA9.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblA9.setOpaque(true);
        lblA9.setPreferredSize(new java.awt.Dimension(32, 32));
        lblA9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblA9MousePressed(evt);
            }
        });
        jplButacas.add(lblA9, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, -1, -1));

        lblB6.setBackground(new java.awt.Color(255, 255, 255));
        lblB6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblB6.setText("B6");
        lblB6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblB6.setOpaque(true);
        lblB6.setPreferredSize(new java.awt.Dimension(32, 32));
        lblB6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblB6MousePressed(evt);
            }
        });
        jplButacas.add(lblB6, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 50, -1, -1));

        lblC6.setBackground(new java.awt.Color(255, 255, 255));
        lblC6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblC6.setText("C6");
        lblC6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblC6.setOpaque(true);
        lblC6.setPreferredSize(new java.awt.Dimension(32, 32));
        lblC6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblC6MousePressed(evt);
            }
        });
        jplButacas.add(lblC6, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 90, -1, -1));

        lblD6.setBackground(new java.awt.Color(255, 255, 255));
        lblD6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblD6.setText("D6");
        lblD6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblD6.setOpaque(true);
        lblD6.setPreferredSize(new java.awt.Dimension(32, 32));
        lblD6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblD6MousePressed(evt);
            }
        });
        jplButacas.add(lblD6, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 130, -1, -1));

        lblE6.setBackground(new java.awt.Color(255, 255, 255));
        lblE6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblE6.setText("E6");
        lblE6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblE6.setOpaque(true);
        lblE6.setPreferredSize(new java.awt.Dimension(32, 32));
        lblE6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblE6MousePressed(evt);
            }
        });
        jplButacas.add(lblE6, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 170, -1, -1));

        lblF6.setBackground(new java.awt.Color(255, 255, 255));
        lblF6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblF6.setText("F6");
        lblF6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblF6.setOpaque(true);
        lblF6.setPreferredSize(new java.awt.Dimension(32, 32));
        lblF6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblF6MousePressed(evt);
            }
        });
        jplButacas.add(lblF6, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 210, -1, -1));

        lblG6.setBackground(new java.awt.Color(255, 255, 255));
        lblG6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblG6.setText("G6");
        lblG6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblG6.setOpaque(true);
        lblG6.setPreferredSize(new java.awt.Dimension(32, 32));
        lblG6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblG6MousePressed(evt);
            }
        });
        jplButacas.add(lblG6, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 250, -1, -1));

        lblA6.setBackground(new java.awt.Color(255, 255, 255));
        lblA6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblA6.setText("A6");
        lblA6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblA6.setOpaque(true);
        lblA6.setPreferredSize(new java.awt.Dimension(32, 32));
        lblA6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblA6MousePressed(evt);
            }
        });
        jplButacas.add(lblA6, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, -1, -1));

        lblA7.setBackground(new java.awt.Color(255, 255, 255));
        lblA7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblA7.setText("A7");
        lblA7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblA7.setOpaque(true);
        lblA7.setPreferredSize(new java.awt.Dimension(32, 32));
        lblA7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblA7MousePressed(evt);
            }
        });
        jplButacas.add(lblA7, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, -1, -1));

        lblB7.setBackground(new java.awt.Color(255, 255, 255));
        lblB7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblB7.setText("B7");
        lblB7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblB7.setOpaque(true);
        lblB7.setPreferredSize(new java.awt.Dimension(32, 32));
        lblB7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblB7MousePressed(evt);
            }
        });
        jplButacas.add(lblB7, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 50, -1, -1));

        lblC7.setBackground(new java.awt.Color(255, 255, 255));
        lblC7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblC7.setText("C7");
        lblC7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblC7.setOpaque(true);
        lblC7.setPreferredSize(new java.awt.Dimension(32, 32));
        lblC7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblC7MousePressed(evt);
            }
        });
        jplButacas.add(lblC7, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 90, -1, -1));

        lblD7.setBackground(new java.awt.Color(255, 255, 255));
        lblD7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblD7.setText("D7");
        lblD7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblD7.setOpaque(true);
        lblD7.setPreferredSize(new java.awt.Dimension(32, 32));
        lblD7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblD7MousePressed(evt);
            }
        });
        jplButacas.add(lblD7, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 130, -1, -1));

        lblE7.setBackground(new java.awt.Color(255, 255, 255));
        lblE7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblE7.setText("E7");
        lblE7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblE7.setOpaque(true);
        lblE7.setPreferredSize(new java.awt.Dimension(32, 32));
        lblE7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblE7MousePressed(evt);
            }
        });
        jplButacas.add(lblE7, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 170, -1, -1));

        lblF7.setBackground(new java.awt.Color(255, 255, 255));
        lblF7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblF7.setText("F7");
        lblF7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblF7.setOpaque(true);
        lblF7.setPreferredSize(new java.awt.Dimension(32, 32));
        lblF7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblF7MousePressed(evt);
            }
        });
        jplButacas.add(lblF7, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 210, -1, -1));

        lblG7.setBackground(new java.awt.Color(255, 255, 255));
        lblG7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblG7.setText("G7");
        lblG7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblG7.setOpaque(true);
        lblG7.setPreferredSize(new java.awt.Dimension(32, 32));
        lblG7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblG7MousePressed(evt);
            }
        });
        jplButacas.add(lblG7, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 250, -1, -1));

        lblH7.setBackground(new java.awt.Color(255, 255, 255));
        lblH7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblH7.setText("H7");
        lblH7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblH7.setOpaque(true);
        lblH7.setPreferredSize(new java.awt.Dimension(32, 32));
        lblH7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblH7MousePressed(evt);
            }
        });
        jplButacas.add(lblH7, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 290, -1, -1));

        lblA8.setBackground(new java.awt.Color(255, 255, 255));
        lblA8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblA8.setText("A8");
        lblA8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblA8.setOpaque(true);
        lblA8.setPreferredSize(new java.awt.Dimension(32, 32));
        lblA8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblA8MousePressed(evt);
            }
        });
        jplButacas.add(lblA8, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, -1, -1));

        lblB8.setBackground(new java.awt.Color(255, 255, 255));
        lblB8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblB8.setText("B8");
        lblB8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblB8.setOpaque(true);
        lblB8.setPreferredSize(new java.awt.Dimension(32, 32));
        lblB8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblB8MousePressed(evt);
            }
        });
        jplButacas.add(lblB8, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, -1, -1));

        lblC8.setBackground(new java.awt.Color(255, 255, 255));
        lblC8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblC8.setText("C8");
        lblC8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblC8.setOpaque(true);
        lblC8.setPreferredSize(new java.awt.Dimension(32, 32));
        lblC8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblC8MousePressed(evt);
            }
        });
        jplButacas.add(lblC8, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 90, -1, -1));

        lblD8.setBackground(new java.awt.Color(255, 255, 255));
        lblD8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblD8.setText("D8");
        lblD8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblD8.setOpaque(true);
        lblD8.setPreferredSize(new java.awt.Dimension(32, 32));
        lblD8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblD8MousePressed(evt);
            }
        });
        jplButacas.add(lblD8, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 130, -1, -1));

        lblE8.setBackground(new java.awt.Color(255, 255, 255));
        lblE8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblE8.setText("E8");
        lblE8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblE8.setOpaque(true);
        lblE8.setPreferredSize(new java.awt.Dimension(32, 32));
        lblE8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblE8MousePressed(evt);
            }
        });
        jplButacas.add(lblE8, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 170, -1, -1));

        lblF8.setBackground(new java.awt.Color(255, 255, 255));
        lblF8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblF8.setText("F8");
        lblF8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblF8.setOpaque(true);
        lblF8.setPreferredSize(new java.awt.Dimension(32, 32));
        lblF8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblF8MousePressed(evt);
            }
        });
        jplButacas.add(lblF8, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 210, -1, -1));

        lblG8.setBackground(new java.awt.Color(255, 255, 255));
        lblG8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblG8.setText("G8");
        lblG8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblG8.setOpaque(true);
        lblG8.setPreferredSize(new java.awt.Dimension(32, 32));
        lblG8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblG8MousePressed(evt);
            }
        });
        jplButacas.add(lblG8, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 250, -1, -1));

        lblG10.setBackground(new java.awt.Color(255, 255, 255));
        lblG10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblG10.setText("G10");
        lblG10.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblG10.setOpaque(true);
        lblG10.setPreferredSize(new java.awt.Dimension(32, 32));
        lblG10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblG10MousePressed(evt);
            }
        });
        jplButacas.add(lblG10, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 250, -1, -1));

        lblH8.setBackground(new java.awt.Color(255, 255, 255));
        lblH8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblH8.setText("H8");
        lblH8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblH8.setOpaque(true);
        lblH8.setPreferredSize(new java.awt.Dimension(32, 32));
        lblH8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblH8MousePressed(evt);
            }
        });
        jplButacas.add(lblH8, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 290, -1, -1));

        lblB9.setBackground(new java.awt.Color(255, 255, 255));
        lblB9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblB9.setText("B9");
        lblB9.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblB9.setOpaque(true);
        lblB9.setPreferredSize(new java.awt.Dimension(32, 32));
        lblB9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblB9MousePressed(evt);
            }
        });
        jplButacas.add(lblB9, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 50, -1, -1));

        lblC9.setBackground(new java.awt.Color(255, 255, 255));
        lblC9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblC9.setText("C9");
        lblC9.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblC9.setOpaque(true);
        lblC9.setPreferredSize(new java.awt.Dimension(32, 32));
        lblC9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblC9MousePressed(evt);
            }
        });
        jplButacas.add(lblC9, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 90, -1, -1));

        lblD9.setBackground(new java.awt.Color(255, 255, 255));
        lblD9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblD9.setText("D9");
        lblD9.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblD9.setOpaque(true);
        lblD9.setPreferredSize(new java.awt.Dimension(32, 32));
        lblD9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblD9MousePressed(evt);
            }
        });
        jplButacas.add(lblD9, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 130, -1, -1));

        lblE9.setBackground(new java.awt.Color(255, 255, 255));
        lblE9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblE9.setText("E9");
        lblE9.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblE9.setOpaque(true);
        lblE9.setPreferredSize(new java.awt.Dimension(32, 32));
        lblE9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblE9MousePressed(evt);
            }
        });
        jplButacas.add(lblE9, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 170, -1, -1));

        lblF9.setBackground(new java.awt.Color(255, 255, 255));
        lblF9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblF9.setText("F9");
        lblF9.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblF9.setOpaque(true);
        lblF9.setPreferredSize(new java.awt.Dimension(32, 32));
        lblF9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblF9MousePressed(evt);
            }
        });
        jplButacas.add(lblF9, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 210, -1, -1));

        lblG9.setBackground(new java.awt.Color(255, 255, 255));
        lblG9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblG9.setText("G9");
        lblG9.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblG9.setOpaque(true);
        lblG9.setPreferredSize(new java.awt.Dimension(32, 32));
        lblG9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblG9MousePressed(evt);
            }
        });
        jplButacas.add(lblG9, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 250, -1, -1));

        lblH9.setBackground(new java.awt.Color(255, 255, 255));
        lblH9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblH9.setText("H9");
        lblH9.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblH9.setOpaque(true);
        lblH9.setPreferredSize(new java.awt.Dimension(32, 32));
        lblH9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblH9MousePressed(evt);
            }
        });
        jplButacas.add(lblH9, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 290, -1, -1));

        lblA10.setBackground(new java.awt.Color(255, 255, 255));
        lblA10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblA10.setText("A10");
        lblA10.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblA10.setOpaque(true);
        lblA10.setPreferredSize(new java.awt.Dimension(32, 32));
        lblA10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblA10MousePressed(evt);
            }
        });
        jplButacas.add(lblA10, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 10, -1, -1));

        lblB10.setBackground(new java.awt.Color(255, 255, 255));
        lblB10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblB10.setText("B10");
        lblB10.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblB10.setOpaque(true);
        lblB10.setPreferredSize(new java.awt.Dimension(32, 32));
        lblB10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblB10MousePressed(evt);
            }
        });
        jplButacas.add(lblB10, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 50, -1, -1));

        lblC10.setBackground(new java.awt.Color(255, 255, 255));
        lblC10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblC10.setText("C10");
        lblC10.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblC10.setOpaque(true);
        lblC10.setPreferredSize(new java.awt.Dimension(32, 32));
        lblC10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblC10MousePressed(evt);
            }
        });
        jplButacas.add(lblC10, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 90, -1, -1));

        lblD10.setBackground(new java.awt.Color(255, 255, 255));
        lblD10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblD10.setText("D10");
        lblD10.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblD10.setOpaque(true);
        lblD10.setPreferredSize(new java.awt.Dimension(32, 32));
        lblD10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblD10MousePressed(evt);
            }
        });
        jplButacas.add(lblD10, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 130, -1, -1));

        lblE10.setBackground(new java.awt.Color(255, 255, 255));
        lblE10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblE10.setText("E10");
        lblE10.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblE10.setOpaque(true);
        lblE10.setPreferredSize(new java.awt.Dimension(32, 32));
        lblE10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblE10MousePressed(evt);
            }
        });
        jplButacas.add(lblE10, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 170, -1, -1));

        lblF10.setBackground(new java.awt.Color(255, 255, 255));
        lblF10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblF10.setText("F10");
        lblF10.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblF10.setOpaque(true);
        lblF10.setPreferredSize(new java.awt.Dimension(32, 32));
        lblF10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblF10MousePressed(evt);
            }
        });
        jplButacas.add(lblF10, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 210, -1, -1));

        lblH16.setBackground(new java.awt.Color(255, 255, 255));
        lblH16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblH16.setText("H16");
        lblH16.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblH16.setOpaque(true);
        lblH16.setPreferredSize(new java.awt.Dimension(32, 32));
        lblH16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblH16MousePressed(evt);
            }
        });
        jplButacas.add(lblH16, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 290, -1, -1));

        lblH10.setBackground(new java.awt.Color(255, 255, 255));
        lblH10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblH10.setText("H10");
        lblH10.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblH10.setOpaque(true);
        lblH10.setPreferredSize(new java.awt.Dimension(32, 32));
        lblH10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblH10MousePressed(evt);
            }
        });
        jplButacas.add(lblH10, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 290, -1, -1));

        lblA11.setBackground(new java.awt.Color(255, 255, 255));
        lblA11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblA11.setText("A11");
        lblA11.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblA11.setOpaque(true);
        lblA11.setPreferredSize(new java.awt.Dimension(32, 32));
        lblA11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblA11MousePressed(evt);
            }
        });
        jplButacas.add(lblA11, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, -1, -1));

        lblB11.setBackground(new java.awt.Color(255, 255, 255));
        lblB11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblB11.setText("B11");
        lblB11.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblB11.setOpaque(true);
        lblB11.setPreferredSize(new java.awt.Dimension(32, 32));
        lblB11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblB11MousePressed(evt);
            }
        });
        jplButacas.add(lblB11, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 50, -1, -1));

        lblC11.setBackground(new java.awt.Color(255, 255, 255));
        lblC11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblC11.setText("C11");
        lblC11.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblC11.setOpaque(true);
        lblC11.setPreferredSize(new java.awt.Dimension(32, 32));
        lblC11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblC11MousePressed(evt);
            }
        });
        jplButacas.add(lblC11, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 90, -1, -1));

        lblD11.setBackground(new java.awt.Color(255, 255, 255));
        lblD11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblD11.setText("D11");
        lblD11.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblD11.setOpaque(true);
        lblD11.setPreferredSize(new java.awt.Dimension(32, 32));
        lblD11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblD11MousePressed(evt);
            }
        });
        jplButacas.add(lblD11, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 130, -1, -1));

        lblE11.setBackground(new java.awt.Color(255, 255, 255));
        lblE11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblE11.setText("E11");
        lblE11.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblE11.setOpaque(true);
        lblE11.setPreferredSize(new java.awt.Dimension(32, 32));
        lblE11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblE11MousePressed(evt);
            }
        });
        jplButacas.add(lblE11, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 170, -1, -1));

        lblF11.setBackground(new java.awt.Color(255, 255, 255));
        lblF11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblF11.setText("F11");
        lblF11.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblF11.setOpaque(true);
        lblF11.setPreferredSize(new java.awt.Dimension(32, 32));
        lblF11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblF11MousePressed(evt);
            }
        });
        jplButacas.add(lblF11, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 210, -1, -1));

        lblG11.setBackground(new java.awt.Color(255, 255, 255));
        lblG11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblG11.setText("G11");
        lblG11.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblG11.setOpaque(true);
        lblG11.setPreferredSize(new java.awt.Dimension(32, 32));
        lblG11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblG11MousePressed(evt);
            }
        });
        jplButacas.add(lblG11, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 250, -1, -1));

        lblH11.setBackground(new java.awt.Color(255, 255, 255));
        lblH11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblH11.setText("H11");
        lblH11.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblH11.setOpaque(true);
        lblH11.setPreferredSize(new java.awt.Dimension(32, 32));
        lblH11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblH11MousePressed(evt);
            }
        });
        jplButacas.add(lblH11, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 290, -1, -1));

        lblA12.setBackground(new java.awt.Color(255, 255, 255));
        lblA12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblA12.setText("A12");
        lblA12.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblA12.setOpaque(true);
        lblA12.setPreferredSize(new java.awt.Dimension(32, 32));
        lblA12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblA12MousePressed(evt);
            }
        });
        jplButacas.add(lblA12, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, -1, -1));

        lblB12.setBackground(new java.awt.Color(255, 255, 255));
        lblB12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblB12.setText("B12");
        lblB12.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblB12.setOpaque(true);
        lblB12.setPreferredSize(new java.awt.Dimension(32, 32));
        lblB12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblB12MousePressed(evt);
            }
        });
        jplButacas.add(lblB12, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 50, -1, -1));

        lblC12.setBackground(new java.awt.Color(255, 255, 255));
        lblC12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblC12.setText("C12");
        lblC12.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblC12.setOpaque(true);
        lblC12.setPreferredSize(new java.awt.Dimension(32, 32));
        lblC12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblC12MousePressed(evt);
            }
        });
        jplButacas.add(lblC12, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 90, -1, -1));

        lblD12.setBackground(new java.awt.Color(255, 255, 255));
        lblD12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblD12.setText("D12");
        lblD12.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblD12.setOpaque(true);
        lblD12.setPreferredSize(new java.awt.Dimension(32, 32));
        lblD12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblD12MousePressed(evt);
            }
        });
        jplButacas.add(lblD12, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 130, -1, -1));

        lblE12.setBackground(new java.awt.Color(255, 255, 255));
        lblE12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblE12.setText("E12");
        lblE12.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblE12.setOpaque(true);
        lblE12.setPreferredSize(new java.awt.Dimension(32, 32));
        lblE12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblE12MousePressed(evt);
            }
        });
        jplButacas.add(lblE12, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 170, -1, -1));

        lblF12.setBackground(new java.awt.Color(255, 255, 255));
        lblF12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblF12.setText("F12");
        lblF12.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblF12.setOpaque(true);
        lblF12.setPreferredSize(new java.awt.Dimension(32, 32));
        lblF12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblF12MousePressed(evt);
            }
        });
        jplButacas.add(lblF12, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 210, -1, -1));

        lblG12.setBackground(new java.awt.Color(255, 255, 255));
        lblG12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblG12.setText("G12");
        lblG12.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblG12.setOpaque(true);
        lblG12.setPreferredSize(new java.awt.Dimension(32, 32));
        lblG12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblG12MousePressed(evt);
            }
        });
        jplButacas.add(lblG12, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 250, -1, -1));

        lblH12.setBackground(new java.awt.Color(255, 255, 255));
        lblH12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblH12.setText("H12");
        lblH12.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblH12.setOpaque(true);
        lblH12.setPreferredSize(new java.awt.Dimension(32, 32));
        lblH12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblH12MousePressed(evt);
            }
        });
        jplButacas.add(lblH12, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 290, -1, -1));

        lblA13.setBackground(new java.awt.Color(255, 255, 255));
        lblA13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblA13.setText("A13");
        lblA13.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblA13.setOpaque(true);
        lblA13.setPreferredSize(new java.awt.Dimension(32, 32));
        lblA13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblA13MousePressed(evt);
            }
        });
        jplButacas.add(lblA13, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 10, -1, -1));

        lblB13.setBackground(new java.awt.Color(255, 255, 255));
        lblB13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblB13.setText("B13");
        lblB13.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblB13.setOpaque(true);
        lblB13.setPreferredSize(new java.awt.Dimension(32, 32));
        lblB13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblB13MousePressed(evt);
            }
        });
        jplButacas.add(lblB13, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 50, -1, -1));

        lblC13.setBackground(new java.awt.Color(255, 255, 255));
        lblC13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblC13.setText("C13");
        lblC13.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblC13.setOpaque(true);
        lblC13.setPreferredSize(new java.awt.Dimension(32, 32));
        lblC13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblC13MousePressed(evt);
            }
        });
        jplButacas.add(lblC13, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 90, -1, -1));

        lblD13.setBackground(new java.awt.Color(255, 255, 255));
        lblD13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblD13.setText("D13");
        lblD13.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblD13.setOpaque(true);
        lblD13.setPreferredSize(new java.awt.Dimension(32, 32));
        lblD13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblD13MousePressed(evt);
            }
        });
        jplButacas.add(lblD13, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 130, -1, -1));

        lblE13.setBackground(new java.awt.Color(255, 255, 255));
        lblE13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblE13.setText("E13");
        lblE13.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblE13.setOpaque(true);
        lblE13.setPreferredSize(new java.awt.Dimension(32, 32));
        lblE13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblE13MousePressed(evt);
            }
        });
        jplButacas.add(lblE13, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 170, -1, -1));

        lblF13.setBackground(new java.awt.Color(255, 255, 255));
        lblF13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblF13.setText("F13");
        lblF13.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblF13.setOpaque(true);
        lblF13.setPreferredSize(new java.awt.Dimension(32, 32));
        lblF13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblF13MousePressed(evt);
            }
        });
        jplButacas.add(lblF13, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 210, -1, -1));

        lblG13.setBackground(new java.awt.Color(255, 255, 255));
        lblG13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblG13.setText("G13");
        lblG13.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblG13.setOpaque(true);
        lblG13.setPreferredSize(new java.awt.Dimension(32, 32));
        lblG13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblG13MousePressed(evt);
            }
        });
        jplButacas.add(lblG13, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 250, -1, -1));

        lblH13.setBackground(new java.awt.Color(255, 255, 255));
        lblH13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblH13.setText("H13");
        lblH13.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblH13.setOpaque(true);
        lblH13.setPreferredSize(new java.awt.Dimension(32, 32));
        lblH13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblH13MousePressed(evt);
            }
        });
        jplButacas.add(lblH13, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 290, -1, -1));

        lblA14.setBackground(new java.awt.Color(255, 255, 255));
        lblA14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblA14.setText("A14");
        lblA14.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblA14.setOpaque(true);
        lblA14.setPreferredSize(new java.awt.Dimension(32, 32));
        lblA14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblA14MousePressed(evt);
            }
        });
        jplButacas.add(lblA14, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, -1, -1));

        lblB14.setBackground(new java.awt.Color(255, 255, 255));
        lblB14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblB14.setText("B14");
        lblB14.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblB14.setOpaque(true);
        lblB14.setPreferredSize(new java.awt.Dimension(32, 32));
        lblB14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblB14MousePressed(evt);
            }
        });
        jplButacas.add(lblB14, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 50, -1, -1));

        lblC14.setBackground(new java.awt.Color(255, 255, 255));
        lblC14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblC14.setText("C14");
        lblC14.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblC14.setOpaque(true);
        lblC14.setPreferredSize(new java.awt.Dimension(32, 32));
        lblC14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblC14MousePressed(evt);
            }
        });
        jplButacas.add(lblC14, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 90, -1, -1));

        lblD14.setBackground(new java.awt.Color(255, 255, 255));
        lblD14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblD14.setText("D14");
        lblD14.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblD14.setOpaque(true);
        lblD14.setPreferredSize(new java.awt.Dimension(32, 32));
        lblD14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblD14MousePressed(evt);
            }
        });
        jplButacas.add(lblD14, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 130, -1, -1));

        lblE14.setBackground(new java.awt.Color(255, 255, 255));
        lblE14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblE14.setText("E14");
        lblE14.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblE14.setOpaque(true);
        lblE14.setPreferredSize(new java.awt.Dimension(32, 32));
        lblE14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblE14MousePressed(evt);
            }
        });
        jplButacas.add(lblE14, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 170, -1, -1));

        lblF14.setBackground(new java.awt.Color(255, 255, 255));
        lblF14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblF14.setText("F14");
        lblF14.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblF14.setOpaque(true);
        lblF14.setPreferredSize(new java.awt.Dimension(32, 32));
        lblF14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblF14MousePressed(evt);
            }
        });
        jplButacas.add(lblF14, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 210, -1, -1));

        lblG14.setBackground(new java.awt.Color(255, 255, 255));
        lblG14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblG14.setText("G14");
        lblG14.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblG14.setOpaque(true);
        lblG14.setPreferredSize(new java.awt.Dimension(32, 32));
        lblG14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblG14MousePressed(evt);
            }
        });
        jplButacas.add(lblG14, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 250, -1, -1));

        lblH14.setBackground(new java.awt.Color(255, 255, 255));
        lblH14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblH14.setText("H14");
        lblH14.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblH14.setOpaque(true);
        lblH14.setPreferredSize(new java.awt.Dimension(32, 32));
        lblH14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblH14MousePressed(evt);
            }
        });
        jplButacas.add(lblH14, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 290, -1, -1));

        lblA15.setBackground(new java.awt.Color(255, 255, 255));
        lblA15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblA15.setText("A15");
        lblA15.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblA15.setOpaque(true);
        lblA15.setPreferredSize(new java.awt.Dimension(32, 32));
        lblA15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblA15MousePressed(evt);
            }
        });
        jplButacas.add(lblA15, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 10, -1, -1));

        lblB15.setBackground(new java.awt.Color(255, 255, 255));
        lblB15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblB15.setText("B15");
        lblB15.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblB15.setOpaque(true);
        lblB15.setPreferredSize(new java.awt.Dimension(32, 32));
        lblB15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblB15MousePressed(evt);
            }
        });
        jplButacas.add(lblB15, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 50, -1, -1));

        lblC15.setBackground(new java.awt.Color(255, 255, 255));
        lblC15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblC15.setText("C15");
        lblC15.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblC15.setOpaque(true);
        lblC15.setPreferredSize(new java.awt.Dimension(32, 32));
        lblC15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblC15MousePressed(evt);
            }
        });
        jplButacas.add(lblC15, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 90, -1, -1));

        lblD15.setBackground(new java.awt.Color(255, 255, 255));
        lblD15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblD15.setText("D15");
        lblD15.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblD15.setOpaque(true);
        lblD15.setPreferredSize(new java.awt.Dimension(32, 32));
        lblD15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblD15MousePressed(evt);
            }
        });
        jplButacas.add(lblD15, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 130, -1, -1));

        lblE15.setBackground(new java.awt.Color(255, 255, 255));
        lblE15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblE15.setText("E15");
        lblE15.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblE15.setOpaque(true);
        lblE15.setPreferredSize(new java.awt.Dimension(32, 32));
        lblE15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblE15MousePressed(evt);
            }
        });
        jplButacas.add(lblE15, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 170, -1, -1));

        lblF15.setBackground(new java.awt.Color(255, 255, 255));
        lblF15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblF15.setText("F15");
        lblF15.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblF15.setOpaque(true);
        lblF15.setPreferredSize(new java.awt.Dimension(32, 32));
        lblF15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblF15MousePressed(evt);
            }
        });
        jplButacas.add(lblF15, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 210, -1, -1));

        lblG15.setBackground(new java.awt.Color(255, 255, 255));
        lblG15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblG15.setText("G15");
        lblG15.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblG15.setOpaque(true);
        lblG15.setPreferredSize(new java.awt.Dimension(32, 32));
        lblG15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblG15MousePressed(evt);
            }
        });
        jplButacas.add(lblG15, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 250, -1, -1));

        lblH15.setBackground(new java.awt.Color(255, 255, 255));
        lblH15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblH15.setText("H15");
        lblH15.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblH15.setOpaque(true);
        lblH15.setPreferredSize(new java.awt.Dimension(32, 32));
        lblH15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblH15MousePressed(evt);
            }
        });
        jplButacas.add(lblH15, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 290, -1, -1));

        lblA16.setBackground(new java.awt.Color(255, 255, 255));
        lblA16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblA16.setText("A16");
        lblA16.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblA16.setOpaque(true);
        lblA16.setPreferredSize(new java.awt.Dimension(32, 32));
        lblA16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblA16MousePressed(evt);
            }
        });
        jplButacas.add(lblA16, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 10, -1, -1));

        lblB16.setBackground(new java.awt.Color(255, 255, 255));
        lblB16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblB16.setText("B16");
        lblB16.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblB16.setOpaque(true);
        lblB16.setPreferredSize(new java.awt.Dimension(32, 32));
        lblB16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblB16MousePressed(evt);
            }
        });
        jplButacas.add(lblB16, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 50, -1, -1));

        lblC16.setBackground(new java.awt.Color(255, 255, 255));
        lblC16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblC16.setText("C16");
        lblC16.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblC16.setOpaque(true);
        lblC16.setPreferredSize(new java.awt.Dimension(32, 32));
        lblC16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblC16MousePressed(evt);
            }
        });
        jplButacas.add(lblC16, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 90, -1, -1));

        lblD16.setBackground(new java.awt.Color(255, 255, 255));
        lblD16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblD16.setText("D16");
        lblD16.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblD16.setOpaque(true);
        lblD16.setPreferredSize(new java.awt.Dimension(32, 32));
        lblD16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblD16MousePressed(evt);
            }
        });
        jplButacas.add(lblD16, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 130, -1, -1));

        lblE16.setBackground(new java.awt.Color(255, 255, 255));
        lblE16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblE16.setText("E16");
        lblE16.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblE16.setOpaque(true);
        lblE16.setPreferredSize(new java.awt.Dimension(32, 32));
        lblE16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblE16MousePressed(evt);
            }
        });
        jplButacas.add(lblE16, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 170, -1, -1));

        lblF16.setBackground(new java.awt.Color(255, 255, 255));
        lblF16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblF16.setText("F16");
        lblF16.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblF16.setOpaque(true);
        lblF16.setPreferredSize(new java.awt.Dimension(32, 32));
        lblF16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblF16MousePressed(evt);
            }
        });
        jplButacas.add(lblF16, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 210, -1, -1));

        lblG16.setBackground(new java.awt.Color(255, 255, 255));
        lblG16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblG16.setText("G16");
        lblG16.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        lblG16.setOpaque(true);
        lblG16.setPreferredSize(new java.awt.Dimension(32, 32));
        lblG16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblG16MousePressed(evt);
            }
        });
        jplButacas.add(lblG16, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 250, -1, -1));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jplButacas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jplButacas, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnContinuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContinuarActionPerformed
        continuar();
    }//GEN-LAST:event_btnContinuarActionPerformed

    private void btnReiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReiniciarActionPerformed
        reiniciar();
    }//GEN-LAST:event_btnReiniciarActionPerformed

    private void lblA1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblA1MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblA1MousePressed

    private void lblB1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblB1MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblB1MousePressed

    private void lblC1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblC1MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblC1MousePressed

    private void lblD1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblD1MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblD1MousePressed

    private void lblE1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblE1MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblE1MousePressed

    private void lblF1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblF1MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblF1MousePressed

    private void lblG1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblG1MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblG1MousePressed

    private void lblH1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblH1MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblH1MousePressed

    private void lblA2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblA2MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblA2MousePressed

    private void lblB2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblB2MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblB2MousePressed

    private void lblC2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblC2MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblC2MousePressed

    private void lblD2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblD2MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblD2MousePressed

    private void lblE2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblE2MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblE2MousePressed

    private void lblF2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblF2MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblF2MousePressed

    private void lblG2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblG2MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblG2MousePressed

    private void lblH2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblH2MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblH2MousePressed

    private void lblA3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblA3MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblA3MousePressed

    private void lblB3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblB3MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblB3MousePressed

    private void lblC3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblC3MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblC3MousePressed

    private void lblD3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblD3MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblD3MousePressed

    private void lblE3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblE3MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblE3MousePressed

    private void lblF3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblF3MouseClicked
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblF3MouseClicked

    private void lblG3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblG3MouseClicked
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblG3MouseClicked

    private void lblH3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblH3MouseClicked
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblH3MouseClicked

    private void lblA4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblA4MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblA4MousePressed

    private void lblB4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblB4MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblB4MousePressed

    private void lblC4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblC4MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblC4MousePressed

    private void lblD4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblD4MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblD4MousePressed

    private void lblE4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblE4MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblE4MousePressed

    private void lblF4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblF4MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblF4MousePressed

    private void lblG4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblG4MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblG4MousePressed

    private void lblH4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblH4MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblH4MousePressed

    private void lblA5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblA5MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblA5MousePressed

    private void lblB5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblB5MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblB5MousePressed

    private void lblC5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblC5MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblC5MousePressed

    private void lblD5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblD5MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblD5MousePressed

    private void lblE5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblE5MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblE5MousePressed

    private void lblF5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblF5MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblF5MousePressed

    private void lblG5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblG5MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblG5MousePressed

    private void lblH5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblH5MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblH5MousePressed

    private void lblA6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblA6MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblA6MousePressed

    private void lblB6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblB6MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblB6MousePressed

    private void lblC6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblC6MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblC6MousePressed

    private void lblD6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblD6MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblD6MousePressed

    private void lblE6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblE6MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblE6MousePressed

    private void lblF6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblF6MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblF6MousePressed

    private void lblG6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblG6MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblG6MousePressed

    private void lblH6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblH6MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblH6MousePressed

    private void lblA7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblA7MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblA7MousePressed

    private void lblB7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblB7MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblB7MousePressed

    private void lblC7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblC7MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblC7MousePressed

    private void lblD7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblD7MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblD7MousePressed

    private void lblE7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblE7MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblE7MousePressed

    private void lblF7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblF7MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblF7MousePressed

    private void lblG7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblG7MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblG7MousePressed

    private void lblH7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblH7MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblH7MousePressed

    private void lblA8MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblA8MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblA8MousePressed

    private void lblB8MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblB8MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblB8MousePressed

    private void lblC8MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblC8MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblC8MousePressed

    private void lblD8MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblD8MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblD8MousePressed

    private void lblE8MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblE8MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblE8MousePressed

    private void lblF8MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblF8MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblF8MousePressed

    private void lblG8MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblG8MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblG8MousePressed

    private void lblH8MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblH8MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblH8MousePressed

    private void lblA9MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblA9MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblA9MousePressed

    private void lblB9MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblB9MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblB9MousePressed

    private void lblC9MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblC9MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblC9MousePressed

    private void lblD9MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblD9MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblD9MousePressed

    private void lblE9MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblE9MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblE9MousePressed

    private void lblF9MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblF9MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblF9MousePressed

    private void lblG9MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblG9MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblG9MousePressed

    private void lblH9MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblH9MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblH9MousePressed

    private void lblA10MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblA10MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblA10MousePressed

    private void lblB10MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblB10MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblB10MousePressed

    private void lblC10MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblC10MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblC10MousePressed

    private void lblD10MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblD10MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblD10MousePressed

    private void lblE10MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblE10MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblE10MousePressed

    private void lblF10MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblF10MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblF10MousePressed

    private void lblG10MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblG10MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblG10MousePressed

    private void lblH10MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblH10MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblH10MousePressed

    private void lblA11MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblA11MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblA11MousePressed

    private void lblB11MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblB11MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblB11MousePressed

    private void lblC11MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblC11MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblC11MousePressed

    private void lblD11MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblD11MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblD11MousePressed

    private void lblE11MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblE11MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblE11MousePressed

    private void lblF11MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblF11MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblF11MousePressed

    private void lblG11MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblG11MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblG11MousePressed

    private void lblH11MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblH11MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblH11MousePressed

    private void lblA12MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblA12MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblA12MousePressed

    private void lblB12MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblB12MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblB12MousePressed

    private void lblC12MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblC12MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblC12MousePressed

    private void lblD12MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblD12MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblD12MousePressed

    private void lblE12MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblE12MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblE12MousePressed

    private void lblF12MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblF12MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblF12MousePressed

    private void lblG12MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblG12MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblG12MousePressed

    private void lblH12MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblH12MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblH12MousePressed

    private void lblA13MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblA13MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblA13MousePressed

    private void lblB13MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblB13MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblB13MousePressed

    private void lblC13MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblC13MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblC13MousePressed

    private void lblD13MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblD13MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblD13MousePressed

    private void lblE13MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblE13MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblE13MousePressed

    private void lblF13MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblF13MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblF13MousePressed

    private void lblG13MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblG13MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblG13MousePressed

    private void lblH13MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblH13MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblH13MousePressed

    private void lblA14MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblA14MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblA14MousePressed

    private void lblB14MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblB14MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblB14MousePressed

    private void lblC14MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblC14MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblC14MousePressed

    private void lblD14MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblD14MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblD14MousePressed

    private void lblE14MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblE14MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblE14MousePressed

    private void lblF14MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblF14MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblF14MousePressed

    private void lblG14MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblG14MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblG14MousePressed

    private void lblH14MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblH14MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblH14MousePressed

    private void lblA15MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblA15MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblA15MousePressed

    private void lblB15MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblB15MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblB15MousePressed

    private void lblC15MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblC15MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblC15MousePressed

    private void lblD15MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblD15MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblD15MousePressed

    private void lblE15MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblE15MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblE15MousePressed

    private void lblF15MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblF15MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblF15MousePressed

    private void lblG15MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblG15MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblG15MousePressed

    private void lblH15MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblH15MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblH15MousePressed

    private void lblA16MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblA16MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblA16MousePressed

    private void lblB16MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblB16MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblB16MousePressed

    private void lblC16MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblC16MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblC16MousePressed

    private void lblD16MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblD16MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblD16MousePressed

    private void lblE16MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblE16MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblE16MousePressed

    private void lblF16MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblF16MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblF16MousePressed

    private void lblG16MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblG16MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblG16MousePressed

    private void lblH16MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblH16MousePressed
        seleccionarButaca(evt);
    }//GEN-LAST:event_lblH16MousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnContinuar;
    private javax.swing.JButton btnReiniciar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel jplButacas;
    private javax.swing.JLabel lblA1;
    private javax.swing.JLabel lblA10;
    private javax.swing.JLabel lblA11;
    private javax.swing.JLabel lblA12;
    private javax.swing.JLabel lblA13;
    private javax.swing.JLabel lblA14;
    private javax.swing.JLabel lblA15;
    private javax.swing.JLabel lblA16;
    private javax.swing.JLabel lblA2;
    private javax.swing.JLabel lblA3;
    private javax.swing.JLabel lblA4;
    private javax.swing.JLabel lblA5;
    private javax.swing.JLabel lblA6;
    private javax.swing.JLabel lblA7;
    private javax.swing.JLabel lblA8;
    private javax.swing.JLabel lblA9;
    private javax.swing.JLabel lblB1;
    private javax.swing.JLabel lblB10;
    private javax.swing.JLabel lblB11;
    private javax.swing.JLabel lblB12;
    private javax.swing.JLabel lblB13;
    private javax.swing.JLabel lblB14;
    private javax.swing.JLabel lblB15;
    private javax.swing.JLabel lblB16;
    private javax.swing.JLabel lblB2;
    private javax.swing.JLabel lblB3;
    private javax.swing.JLabel lblB4;
    private javax.swing.JLabel lblB5;
    private javax.swing.JLabel lblB6;
    private javax.swing.JLabel lblB7;
    private javax.swing.JLabel lblB8;
    private javax.swing.JLabel lblB9;
    private javax.swing.JLabel lblC1;
    private javax.swing.JLabel lblC10;
    private javax.swing.JLabel lblC11;
    private javax.swing.JLabel lblC12;
    private javax.swing.JLabel lblC13;
    private javax.swing.JLabel lblC14;
    private javax.swing.JLabel lblC15;
    private javax.swing.JLabel lblC16;
    private javax.swing.JLabel lblC2;
    private javax.swing.JLabel lblC3;
    private javax.swing.JLabel lblC4;
    private javax.swing.JLabel lblC5;
    private javax.swing.JLabel lblC6;
    private javax.swing.JLabel lblC7;
    private javax.swing.JLabel lblC8;
    private javax.swing.JLabel lblC9;
    private javax.swing.JLabel lblD1;
    private javax.swing.JLabel lblD10;
    private javax.swing.JLabel lblD11;
    private javax.swing.JLabel lblD12;
    private javax.swing.JLabel lblD13;
    private javax.swing.JLabel lblD14;
    private javax.swing.JLabel lblD15;
    private javax.swing.JLabel lblD16;
    private javax.swing.JLabel lblD2;
    private javax.swing.JLabel lblD3;
    private javax.swing.JLabel lblD4;
    private javax.swing.JLabel lblD5;
    private javax.swing.JLabel lblD6;
    private javax.swing.JLabel lblD7;
    private javax.swing.JLabel lblD8;
    private javax.swing.JLabel lblD9;
    private javax.swing.JLabel lblE1;
    private javax.swing.JLabel lblE10;
    private javax.swing.JLabel lblE11;
    private javax.swing.JLabel lblE12;
    private javax.swing.JLabel lblE13;
    private javax.swing.JLabel lblE14;
    private javax.swing.JLabel lblE15;
    private javax.swing.JLabel lblE16;
    private javax.swing.JLabel lblE2;
    private javax.swing.JLabel lblE3;
    private javax.swing.JLabel lblE4;
    private javax.swing.JLabel lblE5;
    private javax.swing.JLabel lblE6;
    private javax.swing.JLabel lblE7;
    private javax.swing.JLabel lblE8;
    private javax.swing.JLabel lblE9;
    private javax.swing.JLabel lblF1;
    private javax.swing.JLabel lblF10;
    private javax.swing.JLabel lblF11;
    private javax.swing.JLabel lblF12;
    private javax.swing.JLabel lblF13;
    private javax.swing.JLabel lblF14;
    private javax.swing.JLabel lblF15;
    private javax.swing.JLabel lblF16;
    private javax.swing.JLabel lblF2;
    private javax.swing.JLabel lblF3;
    private javax.swing.JLabel lblF4;
    private javax.swing.JLabel lblF5;
    private javax.swing.JLabel lblF6;
    private javax.swing.JLabel lblF7;
    private javax.swing.JLabel lblF8;
    private javax.swing.JLabel lblF9;
    private javax.swing.JLabel lblG1;
    private javax.swing.JLabel lblG10;
    private javax.swing.JLabel lblG11;
    private javax.swing.JLabel lblG12;
    private javax.swing.JLabel lblG13;
    private javax.swing.JLabel lblG14;
    private javax.swing.JLabel lblG15;
    private javax.swing.JLabel lblG16;
    private javax.swing.JLabel lblG2;
    private javax.swing.JLabel lblG3;
    private javax.swing.JLabel lblG4;
    private javax.swing.JLabel lblG5;
    private javax.swing.JLabel lblG6;
    private javax.swing.JLabel lblG7;
    private javax.swing.JLabel lblG8;
    private javax.swing.JLabel lblG9;
    private javax.swing.JLabel lblH1;
    private javax.swing.JLabel lblH10;
    private javax.swing.JLabel lblH11;
    private javax.swing.JLabel lblH12;
    private javax.swing.JLabel lblH13;
    private javax.swing.JLabel lblH14;
    private javax.swing.JLabel lblH15;
    private javax.swing.JLabel lblH16;
    private javax.swing.JLabel lblH2;
    private javax.swing.JLabel lblH3;
    private javax.swing.JLabel lblH4;
    private javax.swing.JLabel lblH5;
    private javax.swing.JLabel lblH6;
    private javax.swing.JLabel lblH7;
    private javax.swing.JLabel lblH8;
    private javax.swing.JLabel lblH9;
    private javax.swing.JSpinner txtButacasAdulto;
    private javax.swing.JSpinner txtButacasNino;
    private javax.swing.JTextPane txtHorario;
    private javax.swing.JTextPane txtPelicula;
    // End of variables declaration//GEN-END:variables
}
