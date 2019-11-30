/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import ClassesEBOOP.Client;
import ClassesEBOOP.Reservation;
import ClassesEBOOP.Traversee;
import ProtocolEBOOP.ReponseEBOOP;
import ProtocolEBOOP.RequeteEBOOP;
import static divers.Config_Applic.pathConfig;
import divers.Persistance_Properties;
import java.io.IOException;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 *
 * @author damien
 */
public class servlet_controller extends HttpServlet  {

    private Socket CSocket;
    private Socket Socket_carte;
    private Properties myProperties;
    
    //pour la connexion vers serveur compagnie
    private int port, port_carte;
    private String IP, IP_carte;
	
	//Vecteur
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    
    @Override
    public void init (ServletConfig config)
    {
        try {
            //connexion au serveur compagnie
            myProperties = Persistance_Properties.LoadProp(pathConfig);
            
            //recuperation du port/ip dans l'objet properties
            port = Integer.parseInt(myProperties.getProperty("port_compagnie"));
            IP = myProperties.getProperty("ip_compagnie");
            
            System.out.println("Port : " + port + " IP : " + IP);
            
            CSocket = new Socket(IP, port);
            System.out.println("Connexion ok"); 
			
        } catch (IOException ex) 
        {
            System.out.println("Connexion au Serveur compagnie ou Serveur carte impossible");
        } 
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String adresseDistante = "";
        Vector<Traversee> vect_trav_panier = new Vector();
        Vector<Reservation> vect_res_panier = new Vector();
        int montant_panier = 0;
        try {
            RequeteEBOOP req = new RequeteEBOOP();
            ReponseEBOOP rep = new ReponseEBOOP();
            HttpSession session = request.getSession();
            String action = request.getParameter("action");
            System.out.println("Type de l'action : " + action);
            if(action == null)
            {
                action = "default";
            }
            if(session.getAttribute("port_id_nom") == null)
            {
                
                req.setTypeRequete(RequeteEBOOP.NOM_PORT);
                req.setObjectClasse(null);
                req.EnvoieRequete(CSocket);
                rep.RecevoirReponse(CSocket);
                Hashtable hash = new Hashtable();
                hash=(Hashtable)rep.getObjectClasse();
                session.setAttribute("port_id_nom", hash);
                session.setMaxInactiveInterval(15);
                session.setAttribute("socket", CSocket);
            }
            
            switch(action)
            {
                case "Login":
                    String num_client = request.getParameter("num_client");
                    Client c = new Client(num_client);
                    req.setTypeRequete(RequeteEBOOP.VERIF_NUM_CLIENT);
                    req.setObjectClasse(c);
                    req.EnvoieRequete(CSocket);
                    rep.RecevoirReponse(CSocket);
                    c=(Client)rep.getObjectClasse();
                    switch(rep.getCode())
                    {
                        case 201:
                            System.out.println("Client ok");
                            session.setAttribute("nom_client",c.getNom());
                            session.setAttribute("prenom_client", c.getPrenom());
                            session.setAttribute("num_client", c.getId_client());
                            //il faut mtn recuperer toute les traversee postérieure a la date d'ajd
                            req.setTypeRequete(RequeteEBOOP.GET_TRAVERSEE);
                            req.setObjectClasse(null);
                            req.EnvoieRequete(CSocket);
                            rep.RecevoirReponse(CSocket) ;
                            Vector<Traversee> vect_trav= (Vector<Traversee>)rep.getObjectClasse();
                            session.setAttribute("traversees", vect_trav);
                            session.setAttribute("traversee_panier", vect_trav_panier);
                            session.setAttribute("montant_panier", montant_panier);
                            request.getRequestDispatcher("/jsp_traversee.jsp").forward(request, response);
                            break;
                        case 501:
                            System.out.println("Nouveau client");
                            request.getRequestDispatcher("/jsp_creation.jsp").forward(request, response);
                            break;
                    }
                    break;
                case "Creation":
                    System.out.println("Dans creation");
                    Client c_add = new Client();
                    c_add.setAdresse(request.getParameter("adresse_client"));
                    c_add.setEmail(request.getParameter("email_client"));
                    c_add.setImmatriculation(request.getParameter("immatriculation_client"));
                    c_add.setPrenom(request.getParameter("prenom_client"));
                    c_add.setNom(request.getParameter("nom_client"));
                    req.setTypeRequete(RequeteEBOOP.CREATION_CLIENT);
                    req.setObjectClasse(c_add);
                    req.EnvoieRequete(CSocket);
                    rep.RecevoirReponse(CSocket);
                    switch(rep.getTypeRequete())
                    {
                        case 502:
                            System.out.println("creation impossible");
                            break;
                        case 202:
                            c_add = (Client)rep.getObjectClasse();
                            session.setAttribute("nom_client",c_add.getNom());
                            session.setAttribute("prenom_client", c_add.getPrenom());
                            session.setAttribute("num_client", c_add.getId_client());
                            //il faut mtn recuperer toute les traversee postérieure a la date d'ajd
                            req.setTypeRequete(RequeteEBOOP.GET_TRAVERSEE);
                            req.setObjectClasse(null);
                            req.EnvoieRequete(CSocket);
                            rep.RecevoirReponse(CSocket) ;
                            Vector<Traversee> vect_trav= (Vector<Traversee>)rep.getObjectClasse();
                            session.setAttribute("traversees", vect_trav);
                            session.setAttribute("traversee_panier", vect_trav_panier);
                            session.setAttribute("montant_panier", montant_panier);		
                            request.getRequestDispatcher("/jsp_traversee.jsp").forward(request, response);
                            break;
                    }
                    
                
                case "Payer Panier":
                    if(session.getAttribute("num_client") != null)
                    {
                        session.setAttribute("montant", session.getAttribute("montant_panier"));
                        session.setAttribute("num_client_pay", session.getAttribute("num_client"));
                        session.setAttribute("nom_client_pay", session.getAttribute("nom_client"));
                        session.setAttribute("prenom_client_pay", session.getAttribute("prenom_client"));
                        request.getRequestDispatcher("/PayerTraversee.jsp").forward(request, response);
                    }
                    else
                    {
                        request.getRequestDispatcher("/jsp_login.jsp").forward(request, response);
                    } 
                    break;
                case "Annuler Panier":
                        if(session.getAttribute("reservation_pay")!=null)
                        {
                            vect_res_panier = (Vector<Reservation>) session.getAttribute("reservation_pay");
                            req.setTypeRequete(RequeteEBOOP.ANNULER_RES);
                            req.setObjectClasse(session.getAttribute("reservation_pay"));
                            req.EnvoieRequete(CSocket);
                            rep.RecevoirReponse(CSocket);
                            vect_res_panier.removeAllElements();
                            session.setAttribute("reservation_pay", vect_res_panier);
                        }
                        if(session.getAttribute("traversee_panier")!=null)
                        {
                            vect_trav_panier = (Vector<Traversee>)session.getAttribute("traversee_panier");
                            vect_trav_panier.removeAllElements();
                            session.setAttribute("traversee_panier", vect_trav_panier);
                        }
                        session.setAttribute("montant_panier", montant_panier);
                        req.setTypeRequete(RequeteEBOOP.GET_TRAVERSEE);
                        req.setObjectClasse(null);
                        req.EnvoieRequete(CSocket);
                        rep.RecevoirReponse(CSocket) ;
                        session.setAttribute("traversees", (Vector<Traversee>)rep.getObjectClasse());
                        request.getRequestDispatcher("/jsp_traversee.jsp").forward(request, response);
                        break;
                case "Payer":
                        if(session.getAttribute("num_client") != null)
                        {
                            System.out.println(request.getParameter("Code_Carte"));
                            Vector<Object> v = new Vector();
                            int montant2 = (int)session.getAttribute("montant");
                            v.add(montant2);
                            v.add(request.getParameter("Code_Carte"));
                            req.setObjectClasse(v);
                            req.setTypeRequete(RequeteEBOOP.PAIEMENT);
                            req.EnvoieRequete(CSocket);
                            rep.RecevoirReponse(CSocket);
                            int rep_type = (int)rep.getObjectClasse();
                            if(rep_type == -1)
                            {
                                    System.out.println("Requete recue du serveur card : carte eronnee");
                                    System.out.println(adresseDistante +" / Carte invalide / " + Thread.currentThread().getName());
                                    session.setAttribute("name_page_err","PayerTraversee");
                                    session.setAttribute("des_err","Carte Erronée");
                                    request.getRequestDispatcher("/jsp_erreur.jsp").forward(request, response);
                            }
                            else if(rep_type == -2)
                            {
                                    System.out.println("Requete recue du serveur card : solde insuffisant");
                                    System.out.println(adresseDistante+" / Solde insuffisant / " +Thread.currentThread().getName());
                                    session.setAttribute("name_page_err","PayerTraversee");
                                    session.setAttribute("des_err","Solde insuffisant");
                                    request.getRequestDispatcher("/jsp_erreur.jsp").forward(request, response);
                            }
                            else if(rep_type == 1)
                            {
                                    System.out.println("Requete recue du serveur card : paiement ok");
                                    req.setTypeRequete(RequeteEBOOP.PAIEMENT_OK);
                                    req.setObjectClasse((Vector<Reservation>)session.getAttribute("reservation_pay"));
                                    req.EnvoieRequete(CSocket);
                                    request.getRequestDispatcher("/jsp_confirmation_paye.jsp").forward(request, response);
                            }
                            
                        }
                        else
                        {
                            request.getRequestDispatcher("/jsp_login.jsp").forward(request, response);
                        }
                        break;
                            
                case "Annuler":
                        req.setTypeRequete(RequeteEBOOP.ANNULER_RES);
                        req.setObjectClasse(session.getAttribute("reservation_pay"));
                        req.EnvoieRequete(CSocket);
                        rep.RecevoirReponse(CSocket);
                        req.setTypeRequete(RequeteEBOOP.GET_TRAVERSEE);
                        req.setObjectClasse(null);
                        req.EnvoieRequete(CSocket);
                        rep.RecevoirReponse(CSocket) ;
                        Vector<Traversee> vect_trav= (Vector<Traversee>)rep.getObjectClasse();
                        session.setAttribute("traversees", vect_trav);
                        request.getRequestDispatcher("/jsp_traversee.jsp").forward(request, response);
                        break;
                default:
                    if(session.getAttribute("num_client") != null)
                    {
                        String name = request.getParameter("num_traversee");
                        String tmp = name.substring(0,request.getParameter("num_traversee").length());
                        name = ("type_paiement"+tmp);
                        tmp = name.substring(0,26);

                        if(request.getParameter(name).equals("Paiement direct"))
                        {
                            String information = request.getParameter("num_traversee") + ";" + session.getAttribute("nom_client");
                            int montant = (random(1, 3) * Integer.parseInt(request.getParameter(request.getParameter("num_traversee"))) * 1000);
                            req.setTypeRequete(RequeteEBOOP.CREATION_RES);
                            req.setObjectClasse(information);
                            req.EnvoieRequete(CSocket);
                            rep.RecevoirReponse(CSocket);
                            //j'envoi un requete de création au serveur. Le serveur vérifie que la place est toujours disponible
                            // si, elle l'est, alors il crée ajoute un tuple réservation avec champs payé = N

                            if(rep.getTypeRequete() == ReponseEBOOP.CREATION_ACK){
                                    session.setAttribute("montant", montant);
                                    session.setAttribute("num_client_pay", session.getAttribute("num_client"));
                                    session.setAttribute("nom_client_pay", session.getAttribute("nom_client"));
                                    session.setAttribute("prenom_client_pay", session.getAttribute("prenom_client"));
                                    session.setAttribute("reservation_pay", (Vector<Reservation>)rep.getObjectClasse());
                                    request.getRequestDispatcher("/PayerTraversee.jsp").forward(request, response);
                            }	
                            else{
                                req.setTypeRequete(RequeteEBOOP.GET_TRAVERSEE);
                                req.setObjectClasse(null);
                                req.EnvoieRequete(CSocket);
                                rep.RecevoirReponse(CSocket) ;
                                vect_trav= (Vector<Traversee>)rep.getObjectClasse();
                                session.setAttribute("traversees", vect_trav);
                                session.setAttribute("name_page_err", "Erreur de disponibilités");
                                session.setAttribute("des_err", "La place n'est malheuresement plus disponible, vous n'avez pas été assez vite !!!!");
                                request.getRequestDispatcher("/jsp_erreur.jsp").forward(request, response);
                            }
                        }
                        if(request.getParameter(name).equals("Ajout au panier"))
                        {
                            System.out.println("Dans ajout au panier");
                            Vector<Traversee> vect_trav_actuelle =(Vector<Traversee>) session.getAttribute("traversees");
                            if(session.getAttribute("reservation_pay")!=null)
                                    vect_res_panier = (Vector<Reservation>) session.getAttribute("reservation_pay");
                            if(session.getAttribute("traversee_panier")!=null)
                                    vect_trav_panier = (Vector<Traversee>)session.getAttribute("traversee_panier");
                            montant_panier = (random(1, 3) * Integer.parseInt(request.getParameter(request.getParameter("num_traversee"))) * 1000);
                            String information_panier = request.getParameter("num_traversee") + ";" + session.getAttribute("nom_client");
                            req.setTypeRequete(RequeteEBOOP.CREATION_RES);
                            req.setObjectClasse(information_panier);
                            req.EnvoieRequete(CSocket);
                            rep.RecevoirReponse(CSocket);
                            Vector<Reservation> vect_res = (Vector<Reservation>)rep.getObjectClasse();
                            Traversee trav = new Traversee();
                            if(rep.getTypeRequete() == ReponseEBOOP.CREATION_ACK){
                                    for(int i = 0; i < vect_trav_actuelle.size(); i++){
                                            if(vect_res.get(0).getID_traversee().equals(vect_trav_actuelle.get(i).getId())){
                                                    trav = vect_trav_actuelle.get(i);
                                                    vect_trav_panier.add(trav);
                                                    vect_res_panier.add(vect_res.get(0));
                                            }
                                    }
                                    vect_trav_actuelle.removeElement(trav);
                                    session.setAttribute("traversee_panier", vect_trav_panier);
                                    session.setAttribute("montant_panier", montant_panier + (int)session.getAttribute("montant_panier"));
                                    session.setAttribute("traversees", vect_trav_actuelle);
                                    session.setAttribute("reservation_pay", vect_res_panier);
                                    request.getRequestDispatcher("/jsp_traversee.jsp").forward(request, response);				
                            }	
                            else{
                                    req.setTypeRequete(RequeteEBOOP.GET_TRAVERSEE);
                                    req.setObjectClasse(null);
                                    req.EnvoieRequete(CSocket);
                                    rep.RecevoirReponse(CSocket) ;
                                    session.setAttribute("traversees", (Vector<Traversee>)rep.getObjectClasse());
                                    session.setAttribute("name_page_err", "Erreur de disponibilités");
                                    session.setAttribute("des_err", "La place n'est malheuresement plus disponible, vous n'avez pas été assez vite !!!!");
                                    request.getRequestDispatcher("/jsp_erreur.jsp").forward(request, response);
                            }
                        }
                    }
                    else
                    {
                        request.getRequestDispatcher("/jsp_login.jsp").forward(request, response);
                    }
            }

        }catch (ClassNotFoundException ex) {
            Logger.getLogger(servlet_controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	
	public int random(int high, int low) {
            return((int)(Math.random() * (high+1-low)) + low);
    }
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
		//request.getSession().setMaxInactiveInterval(10);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>


    
}
