package com.filippoBarbieri.gestionePassaporti.entity;


import java.io.Serializable;
import jakarta.persistence.OneToOne;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class IdPrenotazione implements Serializable {
    @NotNull
    @ManyToOne
    private Cittadino cittadino;
    @NotNull
    @OneToOne
    private Slot slot;

    public IdPrenotazione() {}

    public IdPrenotazione(Cittadino cittadino, Slot slot) {
        this.cittadino = cittadino;
        this.slot = slot;
    }
}
