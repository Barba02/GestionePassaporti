package com.filippoBarbieri.gestionePassaporti.dto;

import java.sql.Date;

public class CittadinoDTO {
    private String cf;
    private String nome;
    private String cognome;
    private String nazionalita;
    private Date data_nascita;
    private String luogo_nascita;
    private String ts;
    private String password;
    private Boolean figli_minori;
    private Boolean diplomatico;
    private Boolean di_servizio;

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNazionalita() {
        return nazionalita;
    }

    public void setNazionalita(String nazionalita) {
        this.nazionalita = nazionalita;
    }

    public Date getData_nascita() {
        return data_nascita;
    }

    public void setData_nascita(Date data_nascita) {
        this.data_nascita = data_nascita;
    }

    public String getLuogo_nascita() {
        return luogo_nascita;
    }

    public void setLuogo_nascita(String luogo_nascita) {
        this.luogo_nascita = luogo_nascita;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
