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
}
