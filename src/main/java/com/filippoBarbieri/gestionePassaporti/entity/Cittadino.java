package com.filippoBarbieri.gestionePassaporti.entity;


import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.codec.digest.DigestUtils;

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

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public Boolean getFigli_minori() {
        return figli_minori;
    }

    public void setFigli_minori(Boolean figli_minori) {
        this.figli_minori = figli_minori;
    }

    public Boolean getDiplomatico() {
        return diplomatico;
    }

    public void setDiplomatico(Boolean diplomatico) {
        this.diplomatico = diplomatico;
    }

    public Boolean getDi_servizio() {
        return di_servizio;
    }

    public void setDi_servizio(Boolean di_servizio) {
        this.di_servizio = di_servizio;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = DigestUtils.sha256Hex(password);
    }
}
