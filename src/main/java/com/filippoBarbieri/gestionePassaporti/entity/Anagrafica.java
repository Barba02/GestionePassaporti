package com.filippoBarbieri.gestionePassaporti.entity;


import java.sql.Date;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Anagrafica {
    @Id
    protected String cf;
    protected String nome;
    protected String cognome;
    protected String nazione;
    protected Date data_nascita;
    protected String luogo_nascita;
}
