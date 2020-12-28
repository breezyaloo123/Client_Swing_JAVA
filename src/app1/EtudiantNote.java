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
public class EtudiantNote {
    private String num_etudiant;
    private String nom_matiere;
    private int note;

    public EtudiantNote(String num_etudiant, String nom_matiere, int note) {
        this.num_etudiant = num_etudiant;
        this.nom_matiere = nom_matiere;
        this.note = note;
    }

    public String getNum_etudiant() {
        return num_etudiant;
    }

    public void setNum_etudiant(String num_etudiant) {
        this.num_etudiant = num_etudiant;
    }

    public String getNom_matiere() {
        return nom_matiere;
    }

    public void setNom_matiere(String nom_matiere) {
        this.nom_matiere = nom_matiere;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    
    
    
}
