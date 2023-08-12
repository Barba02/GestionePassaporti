package com.filippoBarbieri.gestionePassaporti.entity;


import jakarta.persistence.*;

@Entity
public class Prenotazione {
    @EmbeddedId
    private IdPrenotazione id;

    public Prenotazione() {}

    public Prenotazione(IdPrenotazione id) {
        this.id = id;
    }
}
