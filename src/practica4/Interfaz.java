/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Interfaz.java
 *
 * Created on 13/11/2013, 10:56:10 PM
 */
package practica4;

import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jonathan
 */
public class Interfaz extends javax.swing.JFrame {

    /** Creates new form Interfaz */
    public Interfaz() {
        initComponents();
        llena();
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ddlTablas = new javax.swing.JComboBox();
        btnCSV = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblConocimiento = new javax.swing.JTable();
        btnCargar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ddlTablas.setActionCommand("");

        btnCSV.setText("Seleccione...");
        btnCSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCSVActionPerformed(evt);
            }
        });

        tblConocimiento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblConocimiento);

        btnCargar.setText("Cargar Tabla");
        btnCargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 667, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(btnCSV)
                        .addGap(109, 109, 109)
                        .addComponent(ddlTablas, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(84, 84, 84)
                        .addComponent(btnCargar)))
                .addGap(204, 204, 204))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ddlTablas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCSV)
                    .addComponent(btnCargar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(132, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void btnCSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCSVActionPerformed
    JFileChooser jf = new JFileChooser();
    jf.setCurrentDirectory(new File("C://Users//Jonathan//Documents//SemestreActual//IA//Practica4//"));
    if((jf.showOpenDialog(this)==JFileChooser.APPROVE_OPTION) && (jf.getSelectedFile().getAbsoluteFile().toString().endsWith("csv"))){
        try{
            //this.tblConocimiento.setModel(leerCSV.parse(new File(jf.getSelectedFile().getAbsolutePath())));
            SQLSentencia.insertar(jf.getSelectedFile().getAbsolutePath());
            llena();
        }catch(Exception e){
            System.out.println(e);
        }
    }else{
        //MOSTRAR MSJ DE ERROR
    }
}//GEN-LAST:event_btnCSVActionPerformed

private void btnCargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarActionPerformed
// TODO add your handling code here:
    this.tblConocimiento.setModel(SQLSentencia.llenaTabla(this.ddlTablas.getSelectedItem().toString()));
    SQLSentencia.tablaActualHash((DefaultTableModel)this.tblConocimiento.getModel());
}//GEN-LAST:event_btnCargarActionPerformed

private void llena(){
    ArrayList<String> comb=SQLSentencia.llenaCombo();
    this.ddlTablas.removeAllItems();
    if(!comb.isEmpty()){
        for(int k=0; k<comb.size(); k++){
            this.ddlTablas.addItem(comb.get(k));
        }
    }
}
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
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Interfaz().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCSV;
    private javax.swing.JButton btnCargar;
    private javax.swing.JComboBox ddlTablas;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblConocimiento;
    // End of variables declaration//GEN-END:variables
}
