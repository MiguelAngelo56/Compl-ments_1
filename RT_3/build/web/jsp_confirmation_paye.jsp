<%-- 
    Document   : jsp_confirmation_paye
    Created on : 12-nov.-2019, 16:18:29
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
        <title>Confirmation de payement</title>
    </head>
    <body>
        <h1>Votre payement confirmé</h1>
                    <%if(request.getSession().getAttribute("reservation_pay")!= null)
                    {
                            Vector<Reservation> vec_res = (Vector<Reservation>) request.getSession().getAttribute("reservation_pay");
                            for(int i = 0; i< vec_res.size(); i++ )
                            {%>
                            <h2><%out.println("Details de la reservation :");%></h2>
                            <h3><%out.println("Res n°"+ i+1 +" : \n");%></h3>
                            <h4><%out.println("ID reservation : " + vec_res.get(i).getID());%></h4>
                            <h4><%out.println("ID traversée : " + vec_res.get(i).getID_traversee());%></h4>
                            <h4><%out.println("Date de depart : " + vec_res.get(i).getDepart());%></h4>
                            <h4><%out.println("Nom du ferry : " + vec_res.get(i).getNom_ferry());%></h4>
                            <%                     
                            }
                    }
                    %>
    </body>
</html>
