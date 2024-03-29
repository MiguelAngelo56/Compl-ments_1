/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applic_mailing;

import ClassesMail.Agent;
import ClassesMail.Mail;
import static divers.Config_Applic.pathConfig;
import static divers.Config_Applic.pathLog;
import divers.FichierLog;
import divers.Persistance_Properties;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Folder;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Miguel
 */
public class Recevoir_Mail extends javax.swing.JFrame {

    /**
     * Creates new form Recevoir_Mail
     */
    //pour le fichier config
    private Properties myProperties;
    
    
    Agent agent_connecter;
    Vector<Mail> vect_mail = new Vector();
    Vector<Part> vect_part;
    String host;
    DefaultTableModel tm = new DefaultTableModel();
    Properties prop = System.getProperties();
    //System.out.println("Création d'une session mail");
    Session sess;
    Store st;
    Folder f;
    
    public Recevoir_Mail(Agent a) 
    {
        try {
            initComponents();
        
            myProperties = Persistance_Properties.LoadProp(pathConfig);
            host = myProperties.getProperty("host");
            
            
            TableauMail.setModel(tm);
            tm.addColumn("From");
            tm.addColumn("To");
            tm.addColumn("Subject");
            tm.addColumn("Text");
            tm.addColumn("Attachement");
            
            agent_connecter = a;
            jLabel4.setText(agent_connecter.getUser());
            jLabel5.setText(agent_connecter.getMail());
            
            
            prop.put("mail.pop3.host", host);
            prop.put("mail.disable.top", true);
            sess = Session.getDefaultInstance(prop, null);
            st = sess.getStore("pop3");
            st.connect(host, agent_connecter.getUser(), agent_connecter.getPassword());
            f = st.getFolder("INBOX");
            f.open(Folder.READ_ONLY);
            
            System.out.println("Obtention des messages");
            RemplirMessage();
            
        } catch (MessagingException ex) {
            Logger.getLogger(Recevoir_Mail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Recevoir_Mail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void RemplirMessage() throws MessagingException, IOException{
        String Text_Part = "";
        Message msg[] = f.getMessages();
        for(int i = 0; i < msg.length; i++)
        {
            vect_part = new Vector();
            if(msg[i].isMimeType("text/plain"))
            {
                Text_Part =(String)msg[i].getContent();
                tm.addRow(new Object[]{Arrays.toString(msg[i].getFrom()), agent_connecter.getMail(), msg[i].getSubject(), Text_Part, "false"});
                //tm.addRow(new Object[]{InternetAddress.toString(msg[i].getRecipients(Message.RecipientType.TO)), agent_connecter.getMail(), msg[i].getSubject(), Text_Part, "false"});
            }
            else
            {
                Multipart msgMP = (Multipart)msg[i].getContent();
                int np = msgMP.getCount();
                Part p = msgMP.getBodyPart(0);
                if (p.isMimeType("text/plain")){
                    Text_Part = (String)p.getContent(); 
                    }
                for (int j=1; j<np; j++)
                {
                    System.out.println("--Composante n° " + j);
                    p = msgMP.getBodyPart(j);
                    String d = p.getDisposition();                    
                    if (d!=null && d.equalsIgnoreCase(Part.ATTACHMENT))
                    {
                        vect_part.add(p);
                    }
                }
                tm.addRow(new Object[]{Arrays.toString(msg[i].getFrom()), agent_connecter.getMail(),msg[i].getSubject(), Text_Part, "true"});
            }
            Enumeration e = msg[i].getAllHeaders();
            Header h = (Header)e.nextElement();
            while(e.hasMoreElements()){
                FichierLog.Ecrire(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(new Date()) + "//" + h.getName() + "//" + h.getValue(), pathLog);
                h = (Header)e.nextElement();
                System.out.println(h.getName());
            }
            FichierLog.Ecrire("-----------------------------------------------------------------------------------------------------", pathLog);
            System.out.println(vect_part.size());
            Mail m = new Mail(Arrays.toString(msg[i].getFrom()), agent_connecter.getMail(), msg[i].getSubject(),Text_Part, vect_part);
            vect_mail.add(m);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        TableauMail = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        TableauMail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "exp", "dest", "sujet", "texte", "att"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TableauMail);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Received_Mail");

        jLabel2.setText("User: ");

        jLabel3.setText("E-Mail: ");

        jLabel4.setText("jLabel4");

        jLabel5.setText("jLabel5");

        jButton2.setText("Disconnect");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Show Details");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1))))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jButton3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        int nbr = TableauMail.getSelectedRow();
        Mail m = vect_mail.elementAt(nbr);
        MailDetailsDialog mdd = new MailDetailsDialog(this, false);
        mdd.setMail(m);
        mdd.setParamMail();
        mdd.show();
        
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(Recevoir_Mail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Recevoir_Mail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Recevoir_Mail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Recevoir_Mail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                    new Recevoir_Mail(null).setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TableauMail;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
