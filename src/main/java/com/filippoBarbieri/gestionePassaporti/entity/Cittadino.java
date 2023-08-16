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

    public void setTs(String ts) {
        this.ts = ts;
    }

    public void setFigli_minori(Boolean figli_minori) {
        this.figli_minori = figli_minori;
    }

    public void setDiplomatico(Boolean diplomatico) {
        this.diplomatico = diplomatico;
    }

    public void setDi_servizio(Boolean di_servizio) {
        this.di_servizio = di_servizio;
    }
}
