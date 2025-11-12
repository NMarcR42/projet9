package com.openclassroom.diabetes_risk.model;

import lombok.Data;

@Data
public class Patient {
    private Integer id;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String genre;

    public Integer getId() { 
        return id; 
    }
    public void setId(Integer id) { 
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
        return dateNaissance; 
    }
    public void setDateNaissance(String dateNaissance) { 
        this.dateNaissance = dateNaissance; 
    }
    public String getGenre() { 
        return genre; 
    }
    public void setGenre(String genre) { 
        this.genre = genre; 
    }
}
