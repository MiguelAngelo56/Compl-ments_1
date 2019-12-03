/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassesEBOOP;

import java.io.Serializable;

/**
 *
 * @author damien
 */
public class Facture implements Serializable{
    private String id_facture;
    private int montant;
    private String id_client;
    private String nom_client;
    private String prenom_client;
    private int nvr_reservation;

    public Facture(int montant, String id_client, String nom_client, String prenom_client, int nvr_reservation) {
        this.montant = montant;
        this.id_client = id_client;
        this.nom_client = nom_client;
        this.prenom_client = prenom_client;
        this.nvr_reservation = nvr_reservation;
    } 

    public String getId_facture() {
        return id_facture;
    }

    public void setId_facture(String id_facture) {
        this.id_facture = id_facture;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public String getId_client() {
        return id_client;
    }

    public void setId_client(String id_client) {
        this.id_client = id_client;
    }

    public String getNom_client() {
        return nom_client;
    }

    public void setNom_client(String nom_client) {
        this.nom_client = nom_client;
    }

    public String getPrenom_client() {
        return prenom_client;
    }

    public void setPrenom_client(String prenom_client) {
        this.prenom_client = prenom_client;
    }

    public int getNvr_reservation() {
        return nvr_reservation;
    }

    public void setNvr_reservation(int nvr_reservation) {
        this.nvr_reservation = nvr_reservation;
    }
    
    
}