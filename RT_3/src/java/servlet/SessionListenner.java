/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import ClassesEBOOP.Reservation;
import ClassesEBOOP.Traversee;
import ProtocolEBOOP.RequeteEBOOP;
import java.io.IOException;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author damien
 */
public class SessionListenner implements HttpSessionListener{
    private Socket CSocket;
    
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("Creation d'une nouvelle session");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("Fin de la session");
        HttpSession session = se.getSession();
        Vector<Reservation> vect_res_panier = new Vector<Reservation>();
        RequeteEBOOP req = new RequeteEBOOP();
        
        CSocket = (Socket)session.getAttribute("socket");
        
        if(session.getAttribute("reservation_pay")!=null)
        {
            try {
                vect_res_panier = (Vector<Reservation>) session.getAttribute("reservation_pay");
                req.setTypeRequete(RequeteEBOOP.ANNULER_RES);
                req.setObjectClasse(session.getAttribute("reservation_pay"));
                req.EnvoieRequete(CSocket);

            } catch (IOException ex) {
                Logger.getLogger(SessionListenner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
     
        
    }
    
}
