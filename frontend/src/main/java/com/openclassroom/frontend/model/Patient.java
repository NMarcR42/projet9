package com.openclassroom.frontend.model;

public class Patient {
    private Long id;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String genre;
    private String adresse;
    private String telephone;

    public Long getId() { 
        return id; 
        }
    public void setId(Long id) { 
        this.id = id; 
        }
    public String getNom() { 
        return nom; 
        }
    public void setNom(String nom) { 
        this.nom = nom; 
        }
    public String getPrenom() { 
        return prenom; 
        }
    public void setPrenom(String prenom) { 
        this.prenom = prenom; 
        }
    public String getDateNaissance() { 
        return dateNaissance; }
    public void setDateNaissance(String dateNaissance) { 
        this.dateNaissance = dateNaissance; }
    public String getGenre() { 
        return genre; }
    public void setGenre(String genre) { 
        this.genre = genre; }
    public String getAdresse() { 
        return adresse; }
    public void setAdresse(String adresse) { 
        this.adresse = adresse; }
    public String getTelephone() { 
        return telephone; }
    public void setTelephone(String telephone) { 
        this.telephone = telephone; }
}
