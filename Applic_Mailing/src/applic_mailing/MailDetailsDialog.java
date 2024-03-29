/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applic_mailing;

import ClassesMail.Mail;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.swing.JFileChooser;

/**
 *
 * @author Miguel
 */
public class MailDetailsDialog extends javax.swing.JDialog {

    /**
     * Creates new form MailDetailsDialog
     */
    private Mail mail = new Mail();
    public Mail getMail(){
        return mail;
    }
    public void setMail(Mail m){
        mail = m;
    }
    
    public void setParamMail(){
        SujetTF.setText(mail.getSujet());
        ExpediteurTF.setText(mail.getExpediteur());
        TexteTA.setText(mail.getText());
        DestinataireTF.setText(mail.getDestinataire());
        for(int i =0; i < mail.getVectPart().size(); i++){
            try {
                AttachementCB.addItem(mail.getVectPart().get(i).getFileName());
            } catch (MessagingException ex) {
                Logger.getLogger(MailDetailsDialog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public MailDetailsDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
       
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Prenom = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        ExpediteurTF = new javax.swing.JTextField();
        DestinataireTF = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        SujetTF = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TexteTA = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        AttachementCB = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setForeground(new java.awt.Color(51, 102, 255));
        jLabel2.setText("Details de l'email reçu");

        jLabel3.setForeground(new java.awt.Color(51, 102, 255));
        jLabel3.setText("Expéditeur");

        ExpediteurTF.setEditable(false);
        ExpediteurTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExpediteurTFActionPerformed(evt);
            }
        });

        DestinataireTF.setEditable(false);

        jLabel4.setForeground(new java.awt.Color(51, 102, 255));
        jLabel4.setText("Destinataire");

        jLabel5.setForeground(new java.awt.Color(51, 102, 255));

        jLabel6.setForeground(new java.awt.Color(51, 102, 255));
        jLabel6.setText("Texte:");

        jLabel7.setForeground(new java.awt.Color(51, 102, 255));
        jLabel7.setText("Sujet");

        SujetTF.setEditable(false);

        TexteTA.setEditable(false);
        TexteTA.setColumns(20);
        TexteTA.setLineWrap(true);
        TexteTA.setRows(5);
        jScrollPane1.setViewportView(TexteTA);

        jLabel8.setForeground(new java.awt.Color(51, 102, 255));
        jLabel8.setText("Attachement: ");

        jButton1.setText("Download");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PrenomLayout = new javax.swing.GroupLayout(Prenom);
        Prenom.setLayout(PrenomLayout);
        PrenomLayout.setHorizontalGroup(
            PrenomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PrenomLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(PrenomLayout.createSequentialGroup()
                .addGroup(PrenomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(PrenomLayout.createSequentialGroup()
                        .addGroup(PrenomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel7)
                            .addGroup(PrenomLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(PrenomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel6))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PrenomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1)
                            .addGroup(PrenomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(DestinataireTF)
                                .addComponent(ExpediteurTF)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
                                .addComponent(SujetTF)
                                .addComponent(AttachementCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(0, 89, Short.MAX_VALUE))
        );
        PrenomLayout.setVerticalGroup(
            PrenomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PrenomLayout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(12, 12, 12)
                .addGroup(PrenomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(ExpediteurTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PrenomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DestinataireTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PrenomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SujetTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PrenomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PrenomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(AttachementCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Prenom, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Prenom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ExpediteurTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExpediteurTFActionPerformed
       
    }//GEN-LAST:event_ExpediteurTFActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        InputStream is = null;
        try {
            // TODO add your handling code here:
            int i = AttachementCB.getSelectedIndex();
            Part p = (Part)mail.getVectPart().get(i);
            is = p.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int c;
            while ((c = is.read()) != -1){ 
                baos.write(c);
            }
            baos.flush();
            String splitter = "\\\\"; 
            String split[] = p.getFileName().split(splitter);
            String nf = split[split.length-1];
            JFileChooser jfc = new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int ret = jfc.showOpenDialog(null); // ne te rend la main que si tu ferme
            if(ret == JFileChooser.APPROVE_OPTION) { // validation
                System.out.println("Selected dir : " + jfc.getSelectedFile() + nf);
                FileOutputStream fos =new FileOutputStream(jfc.getSelectedFile() + splitter + nf);
                baos.writeTo(fos);
                fos.close();
                System.out.println("Pièce attachée " + nf + " récupérée");
            }
            else
            {
                System.out.println("Annuler");
            }
        } catch (IOException ex) {
            Logger.getLogger(MailDetailsDialog.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(MailDetailsDialog.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(MailDetailsDialog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(MailDetailsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MailDetailsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MailDetailsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MailDetailsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MailDetailsDialog dialog = new MailDetailsDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> AttachementCB;
    private javax.swing.JTextField DestinataireTF;
    private javax.swing.JTextField ExpediteurTF;
    private javax.swing.JPanel Prenom;
    private javax.swing.JTextField SujetTF;
    private javax.swing.JTextArea TexteTA;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
