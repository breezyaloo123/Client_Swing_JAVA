/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app1;

/**
 *
 * @author LENOVO
 */
public class Etudiant {
    
    private String num_etudiant;
    private String prenom;
    private String nom;

    public Etudiant(String num_etudiant, String prenom, String nom) {
        this.num_etudiant = num_etudiant;
        this.prenom = prenom;
        this.nom = nom;
    }

    public String getNum_etudiant() {
        return num_etudiant;
    }

    public void setNum_etudiant(String num_etudiant) {
        this.num_etudiant = num_etudiant;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    
    
    
    
}
