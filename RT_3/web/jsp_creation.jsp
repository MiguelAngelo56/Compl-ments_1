<%-- 
    Document   : jsp_creation
    Created on : 03-nov.-2019, 15:33:34
    Author     : damien
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Creation d'un nouveau client</title>
    </head>
    <body>
        <h3>Client inconnu</h3>
        <h3>Creation d'un nouveau client :</h3>
        <form action="servlet_controller">
            Nom : <pre><input type="text" name="nom_client" value="" required/><p></pre>
            Prenom :<pre><input type="text" name="prenom_client" value="" required/><p></pre>
            Immatriculation : <pre><input type="text" name="immatriculation_client" value="" required/><p></pre>
            Addresse : <pre><input type="text" name="adresse_client" value="" required/><p></pre>
            email : <pre><input type="text" name="email_client" value="" required/><p></pre>
            <input type="submit" value="Creation" name="action" />
        </form>
    </body>
</html>
