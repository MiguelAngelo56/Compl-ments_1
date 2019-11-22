<%-- 
    Document   : jsp_login
    Created on : 02-nov.-2019, 15:34:21
    Author     : damien
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Application Check In  ----   Dos Santos Miguel  ----  Wagemans Damien  ----</title>
    </head>
    <body>
        <h1>Bienvenu dans l'application de réservation pour Ferry</h1>
        <h3>Authentification</h3>
        <form name="login" action="servlet_controller">
            Numero du client : <input type="text" name="num_client" value="" />
            <input type="submit" value="Login" name="action" />       
            
        </form>
    </body>
</html>
