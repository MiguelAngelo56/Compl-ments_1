/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applic_checkin;

import ClassesCIA.Booking;
import ClassesCIA.Voyageur;
import ClassesCIA.Login;
import ClassesCIA.Reservation;
import ClassesCIA.Ticket;
import ClassesMail.Mail;
import ProtocolCIA.ReponseCIA;
import ProtocolCIA.RequeteCIA;
import SNMP.Ping;
import SNMP.SNMP_ADMIN;
import static divers.Config_Applic.pathConfig;
import divers.Persistance_Properties;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Dos Santos
 */
public class Login_Client extends javax.swing.JFrame {

    /**
     * Creates new form Login_Client
     */
    private Properties myProperties;
    private Socket cliSock = null;
    private Socket cliSockMail = null;
    public Ticket ticket_save = null;
    private Thread ThreadReiveiveMail = null;
    private RequeteCIA req;
    private RequeteCIA req_service;
    private ReponseCIA rep;
    private ReponseCIA rep_service;
    private Login_Client this_window = this;
    public Login_Client() throws IOException {
        initComponents();
        
        ticket_save = new Ticket();
        
        UsernameLogin.setText("Miguel");
        PasswordLogin.setText("1234");
        
        myProperties = Persistance_Properties.LoadProp(pathConfig);
        IpServeurTF.setText(myProperties.getProperty("ip"));
        PortTF.setText(myProperties.getProperty("port"));
        
        IpServiceTF.setText(myProperties.getProperty("ip_mail"));
        PortServiceTF1.setText(myProperties.getProperty("port_mail"));
        
        
        
        //test de ping vers Serveur Compagnie, service mailing !
        while((Ping.do_ping(IpServeurTF.getText())==-1) && (Ping.do_ping(IpServiceTF.getText())==-1))
        {
            JOptionPane.showMessageDialog(null, "Erreur de connectivité lors du ping", "Ping error", JOptionPane.ERROR_MESSAGE);
        }
        
        SNMP.SNMP_utility.SNMP_GET_synchrone("127.0.0.1/161", ".1.3.6.1.2.1.1.5", "2326DSMDW");
        
        
 
        this.setTitle("Login-User");
        UsernameLogin.setEnabled(false);
        PasswordLogin.setEnabled(false);
        jButton1.setEnabled(false);
        ThreadReiveiveMail = new Thread(){
            public void run(){
                System.out.println("Thread recevoir des e-mails");
                try {
                    while(!isInterrupted()){
                        req_service.RecevoirRequete(cliSockMail);
                        System.out.println("E-mail reçu");
                        Mail mail =(Mail)req_service.getObjectClasse();
                        MailDetailsDialog mdd = new MailDetailsDialog(this_window, false);
                        mdd.setMail(mail);
                        mdd.setParamMail();
                        mdd.setVisible(true);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Login_Client.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Login_Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        UsernameLogin = new javax.swing.JTextField();
        PasswordLogin = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        LabelException = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        ButtonConnect = new javax.swing.JButton();
        IpServeurTF = new javax.swing.JTextField();
        PortTF = new javax.swing.JTextField();
        ButtonBooking = new javax.swing.JButton();
        ButtonBuyTicket = new javax.swing.JButton();
        BookingEx = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton_quitter = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        IpServiceTF = new javax.swing.JTextField();
        PortServiceTF1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton_SNMP_ADMIN = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 102, 255));
        jLabel1.setText("Login");

        jLabel2.setForeground(new java.awt.Color(51, 102, 255));
        jLabel2.setText("Username:");

        jLabel3.setForeground(new java.awt.Color(51, 102, 255));
        jLabel3.setText("Password:");

        UsernameLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UsernameLoginActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(51, 51, 51));
        jButton1.setText("Sign in");
        jButton1.setMaximumSize(new java.awt.Dimension(69, 32));
        jButton1.setMinimumSize(new java.awt.Dimension(69, 32));
        jButton1.setPreferredSize(new java.awt.Dimension(69, 32));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        LabelException.setBackground(new java.awt.Color(255, 255, 255));
        LabelException.setForeground(new java.awt.Color(255, 0, 0));

        jLabel4.setForeground(new java.awt.Color(51, 102, 255));
        jLabel4.setText("Port :");

        jLabel5.setForeground(new java.awt.Color(51, 102, 255));
        jLabel5.setText("Ip Serveur:");

        ButtonConnect.setBackground(new java.awt.Color(51, 51, 51));
        ButtonConnect.setText("Connect");
        ButtonConnect.setPreferredSize(new java.awt.Dimension(94, 32));
        ButtonConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonConnectActionPerformed(evt);
            }
        });

        ButtonBooking.setBackground(new java.awt.Color(51, 51, 51));
        ButtonBooking.setText("Booking");
        ButtonBooking.setEnabled(false);
        ButtonBooking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonBookingActionPerformed(evt);
            }
        });

        ButtonBuyTicket.setBackground(new java.awt.Color(51, 51, 51));
        ButtonBuyTicket.setText("Buy Ticket");
        ButtonBuyTicket.setEnabled(false);
        ButtonBuyTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonBuyTicketActionPerformed(evt);
            }
        });

        jButton_quitter.setText("Quitter");
        jButton_quitter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_quitterActionPerformed(evt);
            }
        });

        jLabel7.setForeground(new java.awt.Color(51, 102, 255));
        jLabel7.setText("Ip Service:");

        jLabel8.setForeground(new java.awt.Color(51, 102, 255));
        jLabel8.setText("Port :");

        jButton2.setText("jButton2");

        jButton_SNMP_ADMIN.setText("SNMP ADMIN");
        jButton_SNMP_ADMIN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SNMP_ADMINActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(UsernameLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(PasswordLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(PortTF, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(IpServeurTF, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ButtonConnect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(IpServiceTF, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(PortServiceTF1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(122, 122, 122))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(BookingEx, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(252, 252, 252))
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(LabelException, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ButtonBooking, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ButtonBuyTicket, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(184, 184, 184)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addComponent(jButton_quitter))
                .addGap(35, 35, 35))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(172, 172, 172)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton_SNMP_ADMIN))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addComponent(jButton_SNMP_ADMIN))
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton_quitter)
                        .addGap(11, 11, 11))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(IpServeurTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(ButtonConnect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(PortTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(IpServiceTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(PortServiceTF1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addComponent(LabelException, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(UsernameLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(PasswordLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonBooking)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ButtonBuyTicket)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addComponent(BookingEx, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2)))
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String username = UsernameLogin.getText();
        String password = PasswordLogin.getText();
        Login log;
        try{
            if(username.equals("") || password.equals(""))
                throw new Exception("CV");
            log = new Login(username, password);
            req = new RequeteCIA(RequeteCIA.LOGIN, log);
            req.EnvoieRequete(cliSock);
            rep = new ReponseCIA();
            rep.RecevoirReponse(cliSock);
            if(rep.getTypeRequete() == ReponseCIA.LOGIN_OK){               
                req_service = new RequeteCIA(RequeteCIA.LOGIN, log);
                rep_service = new ReponseCIA();
                req_service.EnvoieRequete(cliSockMail);
                rep_service.RecevoirReponse(cliSockMail);
                if(rep_service.getTypeRequete() == ReponseCIA.LOGIN_OK){  
                    ButtonBooking.setEnabled(true);
                    ButtonBuyTicket.setEnabled(true);
                    UsernameLogin.setEnabled(false);
                    PasswordLogin.setEnabled(false);
                    jButton1.setEnabled(false);
                    ThreadReiveiveMail.start();
                }
                JOptionPane.showMessageDialog(null, "Login OK", "Login OK", JOptionPane.PLAIN_MESSAGE);
            }
            else
                JOptionPane.showMessageDialog(null, "Mauvais mot de pass", "Mot de pass incorrect", JOptionPane.ERROR_MESSAGE);
            
        }catch(Exception e){
            if(e.getMessage().equals("CV"))
                LabelException.setText("Veuillez remplir l'ensemble des champs");
            else
                LabelException.setText("Le nom d'utilisateur ou le mot de passe est incorrecte");   
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void ButtonConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonConnectActionPerformed
        // TODO add your handling code here:
        try
        {
            //gérer exception
            
            String adresse  = IpServeurTF.getText();
            int port = Integer.parseInt(PortTF.getText());
            cliSock = new Socket(adresse, port);
            
            String adresseMail = IpServiceTF.getText();
            int port_mail = Integer.parseInt(PortServiceTF1.getText());
            cliSockMail = new Socket(adresseMail, port_mail);
            //oos = new ObjectOutputStream(cliSock.getOutputStream());
            //ois = new ObjectInputStream(cliSock.getInputStream());
            
            System.out.println(cliSock.getInetAddress().toString());
            JOptionPane.showMessageDialog(null, "Connexion etablie avec succes", "Connexion", JOptionPane.PLAIN_MESSAGE);
            
            UsernameLogin.setEnabled(true);
            PasswordLogin.setEnabled(true);
            jButton1.setEnabled(true);
            PortTF.setEnabled(false);
            IpServeurTF.setEnabled(false);
            ButtonConnect.setEnabled(false);
        }
        catch (UnknownHostException e)
            { System.err.println("Erreur ! Host non trouvé [" + e + "]"); } 
        
        catch (IOException e)
            { System.err.println("Erreur ! Pas de connexion ? [" + e + "]");}
        
    }//GEN-LAST:event_ButtonConnectActionPerformed

    private void ButtonBookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonBookingActionPerformed
        // TODO add your handling code here:
        RequeteCIA req;
        ReponseCIA rep;
        Booking_Ticket_Window btw = new Booking_Ticket_Window(this, true);
        btw.pack();
        btw.show();
        Object [] retour = btw.getObject();
        String mot = (String)retour[0];
        String mot2 = (String)retour[1];
        try{
            if(mot.equals("") || mot2.equals(""))
               throw new Exception();
            Booking book = new Booking(mot2, mot);
                    
            req = new RequeteCIA(RequeteCIA.VERIF_BOOKING, book);
            req.EnvoieRequete(cliSock);
            
            rep = new ReponseCIA();
            rep.RecevoirReponse(cliSock);
            
            if(rep.getTypeRequete() == ReponseCIA.BOOKING_FAIL){
                BookingEx.setText("Aucune réservation, vous pouvez acheter un ticket");
                ButtonBuyTicket.setEnabled(true);
            }
            else{
                BookingEx.setText("Vous avez une réservation pour ce numéro de client");
                
            }
        }catch(IOException e){
            System.err.println("Erreur ? [" + e.getMessage() + "]"); 
        }catch(ClassNotFoundException e){
            System.out.println("--- erreur sur la classe = " + e.getMessage());
        }catch(Exception ex){
            BookingEx.setText("Attention de remplir l'ensemble des champs!");
        }  
    }//GEN-LAST:event_ButtonBookingActionPerformed

    private void ButtonBuyTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonBuyTicketActionPerformed
        RequeteCIA req;
        ReponseCIA rep;
        
        
        Buy_ticket b_t = new Buy_ticket(this, true);
        b_t.pack();
        b_t.show();
        Object [] retour = b_t.getObject();
        Ticket ticket = new Ticket((String)retour[0], (String)retour[1], (int)retour[2], (String)retour[3]);
        ticket_save = ticket;
        
        req = new RequeteCIA(RequeteCIA.BUY_TICKET, ticket_save);
        try
        {
            req.EnvoieRequete(cliSock);
            rep = new ReponseCIA();
            rep.RecevoirReponse(cliSock);
            if(rep.getTypeRequete() == ReponseCIA.VOYAGEUR_INTROUVABLE)
            {
                //creation d'un nouveau voyageur !
                JOptionPane.showMessageDialog(null, "Ce voyageur n'existe pas", "Voyageur inconnu", JOptionPane.ERROR_MESSAGE);
                Nouveau_Voyageur n_v = new Nouveau_Voyageur(this, true, ticket_save.getNom_conducteur(), ticket_save.getImmatriculation());
                n_v.pack();
                n_v.show();
                Object [] retour_nv_client = n_v.getObject();
                Voyageur vygr = new Voyageur((String)retour_nv_client[0], (String)retour_nv_client[1], (String)retour_nv_client[2], (String)retour_nv_client[3], (String)retour_nv_client[4]);
                req.setTypeRequete(RequeteCIA.CREATION_VOYAGEUR);
                req.setObjectClasse(vygr);
                req.EnvoieRequete(cliSock);
                
                //recuperation de la reponse du serveur !
                rep.RecevoirReponse(cliSock);
                if(rep.getTypeRequete() == ReponseCIA.VOYAGEUR_CREE_ACK)
                {
                    BookingEx.setText("Cliquez sur Buy Ticket pour continuer");
                    vygr = (Voyageur)rep.getObjectClasse();
                    this.ticket_save.setImmatriculation(vygr.getImmatriculation());
                    this.ticket_save.setNom_conducteur(vygr.getNom());
                    
                    JOptionPane.showMessageDialog(null, "Voyageur ajouté avec succes a la base de donnée! cliquez sur Buy Ticket","Insertion ok",  JOptionPane.PLAIN_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Erreur lors de l'insertion", "Erreur insertion", JOptionPane.ERROR_MESSAGE);
                }
                
                
            }
            else
            {
                if(rep.getTypeRequete() == ReponseCIA.TICKET_AUCUNE_TRAVERSEE)
                {
                    JOptionPane.showMessageDialog(null, "Aucune traversée disponible ", "Aucune traversée", JOptionPane.ERROR_MESSAGE);
                }
                else if(rep.getTypeRequete() == ReponseCIA.TICKET_CARTE_ERRONE)
                {
                    JOptionPane.showMessageDialog(null, "Le numéro de carte de crédit est incorrect", "Carte erronée", JOptionPane.ERROR_MESSAGE);
                }
                else if(rep.getTypeRequete() == ReponseCIA.TICKET_SOLDE_INSUFFISANT)
                {
                    JOptionPane.showMessageDialog(null, "Dommage, vous etes fauché!", "Solde insuffisant", JOptionPane.ERROR_MESSAGE);
                }
                else if(rep.getTypeRequete() == ReponseCIA.TICKET_ACK)
                {
                    JOptionPane.showMessageDialog(null, "Ticket acheté !!", "Felicitation !!", JOptionPane.ERROR_MESSAGE);
                    Reservation res = new Reservation ((Reservation)rep.getObjectClasse());
                    Reservation_details r_d = new Reservation_details(this, true, res);
                    
                    r_d.setVisible(true);
                    ticket_save.setImmatriculation("");
                    ticket_save.setNom_conducteur("");
                    ticket_save.setNombre_passager(0);
                    ticket_save.setNum_carte("");
                    
                    
                }
                
            }
            
        } catch (IOException ex) 
        {
            Logger.getLogger(Login_Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Login_Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }//GEN-LAST:event_ButtonBuyTicketActionPerformed

    private void jButton_quitterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_quitterActionPerformed
        try {
            cliSock.close();
            cliSockMail.close();
            ThreadReiveiveMail.stop();
        } catch (IOException ex) {
            Logger.getLogger(Login_Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setVisible(false);
    }//GEN-LAST:event_jButton_quitterActionPerformed

    private void UsernameLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UsernameLoginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UsernameLoginActionPerformed

    private void jButton_SNMP_ADMINActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SNMP_ADMINActionPerformed
        SNMP.SNMP_ADMIN snmp = new SNMP_ADMIN();
        if(UsernameLogin.getText()=="Admin" && PasswordLogin.getText()=="Admin")
        {
            snmp.Admin = true;
        }
        snmp.jComboBox_cible.addItem(IpServeurTF.getText());
        snmp.jComboBox_cible.addItem(IpServiceTF.getText());
        snmp.pack();
        snmp.show();
            
        
            
    }//GEN-LAST:event_jButton_SNMP_ADMINActionPerformed

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
            java.util.logging.Logger.getLogger(Login_Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login_Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login_Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login_Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Login_Client().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Login_Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BookingEx;
    private javax.swing.JButton ButtonBooking;
    private javax.swing.JButton ButtonBuyTicket;
    private javax.swing.JButton ButtonConnect;
    private javax.swing.JTextField IpServeurTF;
    private javax.swing.JTextField IpServiceTF;
    private javax.swing.JLabel LabelException;
    private javax.swing.JTextField PasswordLogin;
    private javax.swing.JTextField PortServiceTF1;
    private javax.swing.JTextField PortTF;
    private javax.swing.JTextField UsernameLogin;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton_SNMP_ADMIN;
    private javax.swing.JButton jButton_quitter;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
