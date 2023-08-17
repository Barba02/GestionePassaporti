package com.filippoBarbieri.gestionePassaporti.entity;


import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

@Entity
public class Cittadino extends Anagrafica {
    @NotNull
    private String ts;
    @NotNull
    private String password;
    @NotNull
    private Boolean figli_minori;
    @NotNull
    private Boolean diplomatico;
    @NotNull
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
