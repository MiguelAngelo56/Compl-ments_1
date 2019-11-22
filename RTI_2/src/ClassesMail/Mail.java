/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassesMail;

import java.io.Serializable;

/**
 *
 * @author Miguel
 */
public class Mail implements Serializable{
    private String Expediteur;
    private String Destinataire;
    private String Sujet;
    private String Text;
    
    public void setExpediteur(String e){
        Expediteur = e;
    }
    
    public String getExpediteur(){
        return Expediteur;
    }
    
    public void setDestinataire(String e){
        Destinataire = e;
    }
    
    public String getDestinataire(){
        return Destinataire;
    }
    
    public void setSujet(String e){
        Sujet = e;
    }
    
    public String getSujet(){
        return Sujet;
    }
    
    public void setText(String e){
        Text = e;
    }
    
    public String getText(){
        return Text;
    }
    
    public Mail(){
        Expediteur = "";
        Destinataire = "";
        Sujet = "";
        Text = "";
    }
    
    public Mail(String e, String d, String s, String t){
        Expediteur = e;
        Destinataire = d;
        Sujet = s;
        Text = t;
    }
}
