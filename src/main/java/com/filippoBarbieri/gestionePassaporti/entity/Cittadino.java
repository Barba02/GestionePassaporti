package com.filippoBarbieri.gestionePassaporti.entity;


import jakarta.persistence.Entity;

@Entity
public class Cittadino extends Anagrafica {
    private String ts;
    private String password;
    private Boolean figli_minori;
    private Boolean diplomatico;
    private Boolean di_servizio;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
