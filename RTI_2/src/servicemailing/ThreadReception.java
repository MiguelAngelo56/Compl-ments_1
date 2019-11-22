/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicemailing;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import java.io.*; 


/**
 *
 * @author Miguel
 */
public class ThreadReception extends Thread{
    static String host = "10.59.26.134";
    public ThreadReception(){   
    }
    public void run(){
        Properties prop = System.getProperties();
        System.out.println("Création d'une session mail");
        prop.put("mail.pop3.host", host);
        prop.put("mail.disable.top", true);
        Session sess = Session.getDefaultInstance(prop, null);
        //prop.list(System.out);
        try
        {
            String user = "dossantos";
            String pwd = "unizuniz1999";
            System.out.println("Obtention d'un objet store");
            Store st = sess.getStore("pop3");
            st.connect(host,user, pwd);

            System.out.println("Obtention d'un objet folder");
            Folder f = st.getFolder("INBOX");
            f.open(Folder.READ_ONLY);
            System.out.println("Obtention des messages");
            while(!isInterrupted())
            {       
                //while(f.hasNewMessage()==false);
                Message msg[] = f.getMessages();
                System.out.println("Nombre de messages : " + f.getMessageCount());
                System.out.println("Nombre de nouveaux messages : " + f.getNewMessageCount());
                System.out.println("Liste des messages : ");
                for (int i=0; i<msg.length; i++)
                {
                    if (msg[i].isMimeType("text/plain"))
                    {
                        //System.out.println("Destinataire : " + msg[i].getReplyTo()[0]);
                        //System.out.println("Expéditeur : " + msg[i].getFrom()[0]);
                        System.out.println("Sujet = " + (String)msg[i].getSubject());
                        System.out.println("Texte : " + (String)msg[i].getContent());
                    }
                }
            }
        }
        catch (NoSuchProviderException e)
        {
            System.out.println("Errreur sur provider : " + e.getMessage());
        }
        catch (MessagingException e)
        {
            System.out.println("Errreur sur message : " + e.getMessage());
        }
        catch (IOException e)
        {
            System.out.println("Errreur sur I/O : " + e.getMessage());
        }
        catch (Exception e)
        {
            System.out.println("Errreur indéterminée : " + e.getMessage());
        } 
    }
}
