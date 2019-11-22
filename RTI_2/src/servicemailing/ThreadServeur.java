/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicemailing;

import ClassesMail.SocketAgent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author damien
 */
public class ThreadServeur extends Thread{
    private int port;
    private int nbr_client;
    private ServerSocket SSocket = null;
    private Statement instruc;
    private static Vector<SocketAgent>  Vect_SA = new Vector();

    public Statement getInstruc() {
        return instruc;
    }

    public void setInstruc(Statement instruc) {
        this.instruc = instruc;
    }
    
    
    public ServerSocket getSSocket() {
        return SSocket;
    }

    public void setSSocket(ServerSocket SSocket) {
        this.SSocket = SSocket;
    }

    public Vector<SocketAgent> getVect_SA() {
        return Vect_SA;
    }
    
    public void setVect_SA(Vector<SocketAgent> vsa) {
        Vect_SA = vsa;
    }
    
    public ThreadServeur(int p, int nbr_c) 
    {
        nbr_client = nbr_c;
        port = p; 
    }
    
    public ThreadServeur(){
        
    }

    public void run() 
    {
        try 
        {
            SSocket = new ServerSocket(port); 
        }
        catch (IOException e) 
        {
            System.err.println("Erreur de port d'écoute ! ? [" + e + "]"); 
            System.exit(1); 
        }
        try
        {
            //Connexion à la base de donnée
            System.out.println("Essai de connexion JDBC");
            Class leDriver= Class.forName("oracle.jdbc.driver.OracleDriver");
            //Connection con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/ORCL","BD_FERRIES","Damien");
            //Connection con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","RTI","unizuniz1999");
            Connection con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","RTI2","unizuniz1999");
            con.setAutoCommit(false);
            System.out.println("Connexion à la BDD RTI réalisée");
            instruc= con.createStatement();
            System.out.println("Création d'une instance d'instruction pour cette connexion");
            Socket CSocket = null;
            ThreadReception tr = new ThreadReception();
            //tr.start();
            while (!isInterrupted()) 
            {
       
                System.out.println("************ Serveur en attente");
                
                CSocket = SSocket.accept();                 
                System.out.println("Etablissement d'une connexion");
                
                ThreadClient thr = new ThreadClient(CSocket, instruc);
                thr.start();

            }
        }catch (SQLException ex) {
            Logger.getLogger(ThreadServeur.class.getName()).log(Level.SEVERE, null, ex);
        }catch (ClassNotFoundException ex) {
            Logger.getLogger(ThreadServeur.class.getName()).log(Level.SEVERE, null, ex);
        }catch (IOException e) 
        {
            System.err.println("Erreur d'accept ! ? [" + e.getMessage() + "]"); 
            System.exit(1); 
        }
        

    } 
}
