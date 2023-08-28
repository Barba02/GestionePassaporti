package com.filippoBarbieri.gestionePassaporti.entity;


import java.sql.Date;
import java.io.IOException;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import com.filippoBarbieri.gestionePassaporti.cfGeneration.CodiceFiscale;
import com.filippoBarbieri.gestionePassaporti.cfGeneration.FormatException;
import com.filippoBarbieri.gestionePassaporti.cfGeneration.CityNotFoundException;

@Entity
public class Anagrafica {
    @Id
    @Column(length = 16)
    private String cf;
    @NotNull
    private String nome;
    @NotNull
    private String cognome;
    @NotNull
    private String nazionalita;
    @NotNull
    private Date data_nascita;
    @NotNull
    private String luogo_nascita;
    @NotNull
    @Column(length = 2)
    private String provincia_nascita;
    @NotNull
    private Boolean nato_maschio;

    public Anagrafica() {}

    public Anagrafica(String nome, String cognome, Boolean nato_maschio, String nazionalita, Date data_nascita, String luogo_nascita, String provincia_nascita) {
        this.nome = nome;
        this.cognome = cognome;
        this.nazionalita = nazionalita;
        this.data_nascita = data_nascita;
        this.luogo_nascita = luogo_nascita;
        this.nato_maschio = nato_maschio;
        this.provincia_nascita = provincia_nascita;
        try {
            cf = new CodiceFiscale(nome, cognome, nato_maschio, data_nascita, luogo_nascita, provincia_nascita).toString();
        }
        catch (IOException | FormatException | CityNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

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

    public Boolean getNato_maschio() {
        return nato_maschio;
    }

    public void setNato_maschio(Boolean male) {
        this.nato_maschio = male;
    }

    public String getProvincia_nascita() {
        return provincia_nascita;
    }

    public void setProvincia_nascita(String provincia_nascita) {
        this.provincia_nascita = provincia_nascita;
    }
}
