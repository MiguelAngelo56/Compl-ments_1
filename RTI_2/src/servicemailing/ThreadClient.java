/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicemailing;

import ClassesCIA.Login;
import ClassesMail.Agent;
import ClassesMail.Mail;
import ProtocolCIA.ReponseCIA;
import ProtocolCIA.RequeteCIA;
import database.facility;
import static divers.Config_Applic.pathConfig;
import divers.Persistance_Properties;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import static servicemailing.ThreadReception.host;

/**
 *
 * @author damien
 */
public class ThreadClient extends Thread{ 
    private Socket Sock;
    private Statement instruc;
    private Properties myProperties;
    private ThreadServeur ts = new ThreadServeur();
    private Agent a = new Agent();
    static String host;
    private RequeteCIA req = new RequeteCIA();
    private ReponseCIA rep = new ReponseCIA();
    
    public Agent getAgent(){
        return a;
    }
    
    public void setAgent(Agent a){
        this.a = a;
    }
    
    public ThreadClient(Socket s, Statement st) 
    {
        Sock = s;
        instruc = st;
    }
    public void run() 
    {
        try 
        {
            //recuperation de l'host dans le fichier properties
            myProperties = Persistance_Properties.LoadProp(pathConfig);
            host = myProperties.getProperty("host");
            
            System.out.println("Service Mailing: thread crée : en attente d'un mail");
            Agent a = new Agent();
            
            System.out.println("Test de connexion");
            while((a=TestLogin()).getUser().equals(""));
            
            
            System.out.println("Connexion réussi");
          
            Properties prop = System.getProperties();
            System.out.println("Création d'une session mail");
            prop.put("mail.pop3.host", host);
            prop.put("mail.disable.top", true);
            Session sess = Session.getDefaultInstance(prop, null);
            //Remplacer par les paramètre agent
            String user = a.getUser();
            String pwd = a.getPassword();
            System.out.println("Obtention d'un objet store");
            Store st = sess.getStore("pop3");
            st.connect(host,user, pwd);
            System.out.println("Obtention d'un objet folder");
            Folder f = st.getFolder("INBOX");
            f.open(Folder.READ_ONLY);
            System.out.println("Obtention des messages");
            int countConnexionMessage = f.getMessageCount();
            int countNewMessage = 0;
            int MessageNumber;
            String reply = "";
            String Text = "";
            //Utiliser un listener: pour savoir si il ya des nouveaux messages, envoie de ces messages par socket. --> page 63 java(IV)
            //soit le store listener ou le count listener(je pense plus au store car celui là va envoyer un par un) --> moins chiant
            while(!isInterrupted())
            {
                System.out.println("WAIT");
                //Dès que l'on reçoit un message on lui envoie sous forme de requete
                
                while(countConnexionMessage==f.getMessageCount()){
                    f.close(true);
                    f.open(Folder.READ_ONLY);
                }
                System.out.println("Nombre de message: " + f.getMessageCount() + " " + countConnexionMessage);
                countNewMessage = f.getMessageCount() - countConnexionMessage;
                System.out.println("Nombre de nouveau message: " + countNewMessage);                
                Message msg[] = f.getMessages();
                //msg[0].setFlag(Flag.DELETED, true);               
                for(int i=0; i<countNewMessage; i++)
                {
                    //avec cette methode on recuperera le message du plus recent ou moins recent
                    //rajouter un if ici qui test si le mail de l'expediteur contient u2.tech.hepl.local
                    MessageNumber = f.getMessageCount()-(i+1);
                    System.out.println("Nouveau message");
                    if(msg[MessageNumber].isMimeType("text/plain"))
                    {
                        System.out.println("1");
                        System.out.println("Message en cours : " + MessageNumber);
                        System.out.println("Message total : " + f.getMessageCount());
                        Text =(String)msg[MessageNumber].getContent();
                        System.out.println("2");
                        reply = InternetAddress.toString(msg[MessageNumber].getRecipients(Message.RecipientType.TO));
                        System.out.println("3");        
                        //mail (expediateur, destinataire, sujet, texte)
                        Mail mail = new Mail(reply,a.getMail(),(String)msg[MessageNumber].getSubject(), Text, null);
                        req.setObjectClasse(mail);
                        req.EnvoieRequete(Sock);
                    }

                }
                countConnexionMessage = f.getMessageCount();
            }
        } catch (MessagingException ex) {
            Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException  | SQLException  ex) {
            Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);






        }
    }
    
    public Agent TestLogin() throws IOException, SQLException, ClassNotFoundException{
        ResultSet ret;
        RequeteCIA req = new RequeteCIA();
        ReponseCIA rep = new ReponseCIA();
        Agent a = new Agent();
        req.RecevoirRequete(Sock);
        Login log =(Login) req.getObjectClasse();
        boolean find = false;
        ret = facility.SelectAllRowFromTable("AGENTS", instruc);
        while(ret.next()){
            if(ret.getString("USER_AGENT").equals(log.getUsername()) && ret.getString("PASSWORD_AGENT").equals(log.getPassword())){
                rep.setTypeRequete(ReponseCIA.LOGIN_OK);
                rep.EnvoieReponse(Sock);
                a.setId(Integer.parseInt(ret.getString("ID_AGENT")));
                a.setMail(ret.getString("EMAIL"));
                a.setUser(ret.getString("USER_AGENT"));
                a.setPassword(ret.getString("PASSWORD_AGENT"));
                return a;
            }
        }
        return a;
    }
    
}
