<%-- 
    Document   : jsp_traversee
    Created on : 03-nov.-2019, 16:28:39
    Author     : damien
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="ClassesEBOOP.Traversee"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.Hashtable"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Traversée(s) disponible(s)</title>
        <style>
            table, th, td
            {
                border: 1px solid black;
                color : black;
                border-collapse: collapse;
                padding: 10px;
            }
        </style>
    </head>
   
    <header>
        <h3>Bonjour <% out.println(request.getSession().getAttribute("prenom_client") + " " + request.getSession().getAttribute("nom_client") + " ("+ request.getSession().getAttribute("num_client") + ")");%></h3>
        
    </header>
    </header>
    <body>
        <form action="servlet_controller">
            <br>
            <h3>Voici la liste des traversées :</h3>
            <br>
            
            <table>
                <%
                    if(request.getSession().getAttribute("traversees")!= null)
                    {
                        Vector<Traversee> vec_trav = (Vector<Traversee>) request.getSession().getAttribute("traversees");
                    
                %>
                <thead>
                    <tr>
                    <%
                        out.println("<th>" + "ID traversée" + "</th>");
                        out.println("<th>" + "Depart" + "</th>");
                        out.println("<th>" + "Port de départ" + "</th>");
                        out.println("<th>" + "Port de destination" + "</th>");
                        out.println("<th>" + "Nombre de passager" + "</th>");
                    %>              
                    </tr>
                </thead>
                <%
                    Hashtable hash = new Hashtable();
                    hash = (Hashtable)request.getSession().getAttribute("port_id_nom");
                    
                    for(int i = 0; i< vec_trav.size(); i++ )
                    { %>
                    <tr>
                        <%
                                Traversee t = vec_trav.get(i);
                                out.println("<td>" +t.getId()+ "</td>");
                                out.println("<td>" +t.getDepart()+ "</td>");
                                out.println("<td>" +(String)hash.get(t.getPort_depart())+ "</td>");
                                out.println("<td>" +(String)hash.get(t.getPort_destination())+ "</td>");
                        %>
						<td>
                            <p>                            
                                <input type="text" name="<%out.println(t.getId());%>" value="0" />                            
                            </p>
                        </td>
                        <td>
                            <p>
                                    <%String name = t.getId();
                                    String tmp = name.substring(0, t.getId().length());
                                    name = ("type_paiement"+tmp);%>
                                    <select name="<%out.println(name);%>">
                                            <option>Ajout au panier</option>
                                            <option>Paiement direct</option>
                                    </select>
                                    <button input type="submit" value="<%out.println(t.getId());%>" name="num_traversee"> Valider</button>                                    
                            </p>
                        </td>
                    </tr>
                     
                 <% }
                }    
                %>
                
            </table>
			<h4>
			Liste des traversées au panier: 
                            <%if(request.getSession().getAttribute("traversee_panier")!= null){
                                    Vector<Traversee> vec_trav_panier = (Vector<Traversee>) request.getSession().getAttribute("traversee_panier");
                                    for(int i = 0; i< vec_trav_panier.size(); i++ )
                                    { 						
                                            out.println(vec_trav_panier.get(i).getId());
                                    }
                            }
                            %>
			</h4>
			<h4><%
				if(session.getAttribute("montant_panier")!=null){
				%>
				Prix total:<%out.println(session.getAttribute("montant_panier"));%>euro
				<%
					}
				%>
			</h4>
			<input type="submit" value="Payer Panier" name="action" />
			<input type="submit" value="Annuler Panier" name="action" />
        </form>
        
    </body>
</html>
