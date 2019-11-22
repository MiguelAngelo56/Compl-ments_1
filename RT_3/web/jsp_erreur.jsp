<%-- 
    Document   : jsp_erreur
    Created on : 12-nov.-2019, 16:00:49
    Author     : Dos Santos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Erreur</title>
    </head>
    <body>
        <form action="jsp_traversee.jsp">
           
            <h1>Erreur sur la page <% out.println(request.getSession().getAttribute("name_page_err"));%></h1>
                    <h2>----------------------------------------------------------------------------------------</h2>
                    <h2><% out.println(request.getSession().getAttribute("des_err"));%></h2>
                    <h2>----------------------------------------------------------------------------------------</h2>
                    <input type="submit" value="Retour aux traversées" />
        </form>        
    </body>
</html>
