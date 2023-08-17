package com.filippoBarbieri.gestionePassaporti.entity;


import java.sql.Date;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.validation.constraints.NotNull;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Anagrafica {
    @Id
    protected String cf;
    @NotNull
    protected String nome;
    @NotNull
    protected String cognome;
    @NotNull
    protected String nazionalita;
    @NotNull
    protected Date data_nascita;
    @NotNull
    protected String luogo_nascita;

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
}
