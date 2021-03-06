/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sesint3.vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import javafx.scene.chart.NumberAxis;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;



/**
 *
 * @author carlos
 */
public class FrmRobotMonitor extends StandardForm {
    
    //codigo al renderizar la pantalla
    private void init(){       
    }
    
    private void conectarARobots(){        
        String ip= this.txtNombreArchivo.getText();
        String puerto= this.txtPuerto.getText();
                    
        Thread thread=new Thread(new Runnable(){
            @Override
            public void run() {
                try{                    
                    
                    
                }catch(Exception err){
                    JOptionPane.showMessageDialog(null, "Ingrese IP y puerto valido", "Error del usuario", JOptionPane.ERROR_MESSAGE);
                }
            }
        
        });
        
        thread.start();
    }
    
    /**
     * Creates new form robotMonitor
     */
    public FrmRobotMonitor() {
        initComponents();
        init();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator2 = new javax.swing.JSeparator();
        canvasPanel = new javax.swing.JPanel();
        btnConectar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNombreArchivo = new javax.swing.JTextField();
        txtPuerto = new javax.swing.JTextField();
        btnMoverRobotArriba = new javax.swing.JButton();
        btnMoverRobotAbajo = new javax.swing.JButton();
        btnMoverRobotDerecha = new javax.swing.JButton();
        btnMoverRobotIzquierda = new javax.swing.JButton();
        cmbRadar = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        txtIP = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnGuardarArchivo = new javax.swing.JButton();
        btnCargarArchivo = new javax.swing.JButton();
        canvasMappingPanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        canvasPanel.setBackground(java.awt.Color.white);
        canvasPanel.setLayout(new java.awt.BorderLayout());
        getContentPane().add(canvasPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 460, 210));

        btnConectar.setText("Conectar");
        btnConectar.setName("btnConectar"); // NOI18N
        getContentPane().add(btnConectar, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, 130, 40));

        jLabel1.setText("IP:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 20));

        jLabel2.setText("Nombre de archivo:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, -1, 10));
        getContentPane().add(txtNombreArchivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 150, 23));

        txtPuerto.setText("6665");
        getContentPane().add(txtPuerto, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, 102, 23));

        btnMoverRobotArriba.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sesint3/assets/imagenes/up.png"))); // NOI18N
        btnMoverRobotArriba.setName("btnMoverRobotArriba"); // NOI18N
        getContentPane().add(btnMoverRobotArriba, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 150, 70, 30));

        btnMoverRobotAbajo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sesint3/assets/imagenes/down.png"))); // NOI18N
        btnMoverRobotAbajo.setName("btnMoverRobotAbajo"); // NOI18N
        getContentPane().add(btnMoverRobotAbajo, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 190, 70, 30));

        btnMoverRobotDerecha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sesint3/assets/imagenes/right.png"))); // NOI18N
        btnMoverRobotDerecha.setName("btnMoverRobotDerecha"); // NOI18N
        getContentPane().add(btnMoverRobotDerecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 160, 30, -1));

        btnMoverRobotIzquierda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sesint3/assets/imagenes/left.png"))); // NOI18N
        btnMoverRobotIzquierda.setName("btnMoverRobotIzquierda"); // NOI18N
        getContentPane().add(btnMoverRobotIzquierda, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 160, 30, -1));

        cmbRadar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sonar", "Laser" }));
        getContentPane().add(cmbRadar, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 180, 90, 20));

        jLabel3.setText("Ranger");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, -1, -1));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 430, 20));
        getContentPane().add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 430, 20));

        txtIP.setText("localhost");
        getContentPane().add(txtIP, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 102, 23));

        jLabel4.setText("Puerto:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, -1, 40));

        btnGuardarArchivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sesint3/assets/imagenes/save-file-option.png"))); // NOI18N
        btnGuardarArchivo.setName("btnGuardarArchivo"); // NOI18N
        getContentPane().add(btnGuardarArchivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 80, -1, -1));

        btnCargarArchivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sesint3/assets/imagenes/upload.png"))); // NOI18N
        btnCargarArchivo.setName("btnCargarArchivo"); // NOI18N
        getContentPane().add(btnCargarArchivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 80, -1, -1));

        canvasMappingPanel.setBackground(new java.awt.Color(102, 255, 204));
        getContentPane().add(canvasMappingPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 40, 400, 400));

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel5.setText("Mapping");
        jLabel5.setToolTipText("");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmRobotMonitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmRobotMonitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmRobotMonitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmRobotMonitor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmRobotMonitor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCargarArchivo;
    public javax.swing.JButton btnConectar;
    private javax.swing.JButton btnGuardarArchivo;
    private javax.swing.JButton btnMoverRobotAbajo;
    public javax.swing.JButton btnMoverRobotArriba;
    private javax.swing.JButton btnMoverRobotDerecha;
    private javax.swing.JButton btnMoverRobotIzquierda;
    public javax.swing.JPanel canvasMappingPanel;
    private javax.swing.JPanel canvasPanel;
    public javax.swing.JComboBox<String> cmbRadar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    public javax.swing.JTextField txtIP;
    public javax.swing.JTextField txtNombreArchivo;
    public javax.swing.JTextField txtPuerto;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setOnActionListenerToComponents(ActionListener actionListener) {
        //vista-conexion con el simulador del robot
        this.btnConectar.addActionListener(actionListener);        
        
        //botones para cargar/guardar archivo de recorrido
        this.btnCargarArchivo.addActionListener(actionListener);
        this.btnGuardarArchivo.addActionListener(actionListener);
        
        //vista-flechas movimiento del robot
        this.btnMoverRobotArriba.addActionListener(actionListener);
        this.btnMoverRobotDerecha.addActionListener(actionListener);
        this.btnMoverRobotIzquierda.addActionListener(actionListener);
        this.btnMoverRobotAbajo.addActionListener(actionListener);
        
        
    }
    
    public JPanel getGraficoCanvasPanel(){
        return this.canvasPanel;        
    }
}
