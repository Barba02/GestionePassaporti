package com.filippoBarbieri.gestionePassaporti.entity;


import java.io.Serializable;
import jakarta.persistence.OneToOne;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Embeddable;

@Embeddable
public class IdPrenotazione implements Serializable {
    @ManyToOne
    private Cittadino cittadino;
    @OneToOne
    private Slot slot;

    public IdPrenotazione() {
    }

    public IdPrenotazione(Cittadino cittadino, Slot slot) {
        this.cittadino = cittadino;
        this.slot = slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }
}
