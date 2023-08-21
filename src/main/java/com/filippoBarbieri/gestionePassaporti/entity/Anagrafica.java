package com.filippoBarbieri.gestionePassaporti.entity;


import jakarta.persistence.Id;
import jakarta.persistence.Entity;

@Entity
public class Anagrafica {
    @Id
    private String cf;

    public Anagrafica() {}

    public Anagrafica(String cf) {
        this.cf = cf;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }
}
