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
public class Agent implements Serializable{
    private int Id;
    private String User;
    private String Password;
    private String EMail;
    
    public int getId(){
        return Id;
    }
    
    public void setId(int id){
        Id = id;
    }
    
    public String getUser(){
        return User;
    }
    
    public void setUser(String nom){
        User = nom;
    }
    
    public String getPassword(){
        return Password;
    }
    
    public void setPassword(String prenom){
        Password = prenom;
    }
    
    public String getMail(){
        return EMail;
    }
    
    public void setMail(String mail){
        EMail = mail;
    }
    
    public Agent(){
        Id = 0;
        User = "";
        Password = "";
        EMail = "";
    }
    
    public Agent(int id, String nom, String prenom, String mail){
        Id = id;
        User = nom;
        Password = prenom;
        EMail = mail;
    }
}
