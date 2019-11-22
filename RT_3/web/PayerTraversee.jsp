<%-- 
    Document   : PayerTraversee
    Created on : 06-nov.-2019, 22:06:58
    Author     : Dos Santos
--%>

<%@page import="ClassesEBOOP.Reservation"%>
<%@page import="java.util.Vector"%>
<%@page import="ClassesEBOOP.Traversee"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Payement de vos réservations</title>
    </head>
    <body>
        <h1>Payement de votre réservation</h1>
        <h3>Payement pour <% out.println(request.getSession().getAttribute("prenom_client_pay") + " " + 
			request.getSession().getAttribute("nom_client_pay") + 
			" ("+ request.getSession().getAttribute("num_client_pay") + ")");%>
		</h3>
		<h4>
			Liste des traversees: 
					<%if(request.getSession().getAttribute("reservation_pay")!= null)
                    {
						Vector<Reservation> vec_res = (Vector<Reservation>) request.getSession().getAttribute("reservation_pay");
						for(int i = 0; i< vec_res.size(); i++ )
						{ 						
							out.println(vec_res.get(i).getID() + ":" + vec_res.get(i).getID_traversee());
						}
					}
					%>
		</h4>
		<h4>
			Prix total:<%out.println(session.getAttribute("montant"));%>euro
		</h4>
        <form name="payer_res" action="servlet_controller">
			<h4>
				Entrez le code de votre carte de crédit:
				<input type="text" name="Code_Carte" value="" />  
			</h4>
            <input type="submit" value="Payer" name="action" /> <input type="submit" value="Annuler" name="action" />      
        </form>
    </body>
</html>
