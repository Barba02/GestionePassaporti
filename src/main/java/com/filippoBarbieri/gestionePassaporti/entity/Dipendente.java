package com.filippoBarbieri.gestionePassaporti.entity;


import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import com.filippoBarbieri.gestionePassaporti.enums.Sede;
import com.filippoBarbieri.gestionePassaporti.enums.Giorni;

@Entity
public class Dipendente {
    @Id
    @Column(length = 6)
    private String username;
    @NotNull
    private String nome;
    @NotNull
    private String cognome;
    @NotNull
    private Password password;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Sede sede;
    @NotNull
    private Set<Giorni> disponibilita;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public Set<Giorni> getDisponibilita() {
        return disponibilita;
    }

    public void setDisponibilita(Set<Giorni> disponibilita) {
        this.disponibilita = disponibilita;
    }
}
