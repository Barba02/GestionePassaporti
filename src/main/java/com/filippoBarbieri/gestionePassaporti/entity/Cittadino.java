package com.filippoBarbieri.gestionePassaporti.entity;


import jakarta.persistence.Entity;

@Entity
public class Cittadino extends Anagrafica {
    private String ts;
    private Boolean figli_minori;
    private Boolean diplomatico;
    private Boolean di_servizio;
}
