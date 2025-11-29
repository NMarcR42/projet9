package com.openclassroom.patient_back.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PatientDTO {

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 50, message = "Le nom doit faire moins de 50 caractères")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(max = 50, message = "Le prénom doit faire moins de 50 caractères")
    private String prenom;

    @NotBlank(message = "Le genre est obligatoire")
    @Pattern(regexp = "H|F", message = "Le genre doit être 'H' ou 'F'")
    private String genre;

    @NotBlank(message = "La date de naissance est obligatoire")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "La date doit être au format yyyy-MM-dd")
    private String dateNaissance;

    @NotBlank(message = "L'adresse est obligatoire")
    private String adresse;

    @NotBlank(message = "Le téléphone est obligatoire")
    @Pattern(regexp = "\\d{10}", message = "Le téléphone doit contenir 10 chiffres")
    private String telephone;

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

    public String getGenre() { 
        return genre; 
    }
    public void setGenre(String genre) { 
        this.genre = genre; 
    }

    public String getDateNaissance() { 
        return dateNaissance; 
    }
    public void setDateNaissance(String dateNaissance) { 
        this.dateNaissance = dateNaissance; 
    }

    public String getAdresse() { 
        return adresse; 
    }
    public void setAdresse(String adresse) { 
        this.adresse = adresse; 
    }

    public String getTelephone() { 
        return telephone; 
    }
    public void setTelephone(String telephone) { 
        this.telephone = telephone; 
    }
}
