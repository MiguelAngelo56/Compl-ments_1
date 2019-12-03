/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassesMail;

import java.io.Serializable;
import java.util.*; 
import java.io.File;
import javax.mail.Part;
/**
 *
 * @author Miguel
 */
public class Mail implements Serializable{
    private String Expediteur;
    private String Destinataire;
    private String Sujet;
    private String Text;
    private Vector<Part> vect_part = new Vector();
    
    public void setVectPart(Vector<Part> e){
        vect_part = e;
    }
    
    public Vector<Part> getVectPart(){
        return vect_part;
    }
    
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
        vect_part = null;
    }
    
    public Mail(String e, String d, String s, String t){
        Expediteur = e;
        Destinataire = d;
        Sujet = s;
        Text = t;
        vect_part = null;
    }
    
    public Mail(String e, String d, String s, String t, Vector<Part> vect){
        Expediteur = e;
        Destinataire = d;
        Sujet = s;
        Text = t;
        vect_part = vect;
    }
}
