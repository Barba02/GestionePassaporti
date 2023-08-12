package com.filippoBarbieri.gestionePassaporti.entity;


import java.io.Serializable;
import jakarta.persistence.*;

@Embeddable
class IdPrenotazione implements Serializable {
    @ManyToOne
    private Cittadino cittadino;
    @OneToOne
    private Slot slot;

    public IdPrenotazione() {}

    public IdPrenotazione(Cittadino cittadino, Slot slot) {
        this.cittadino = cittadino;
        this.slot = slot;
    }
}

@Entity
public class Prenotazione {
    @EmbeddedId
    private IdPrenotazione id;
}
