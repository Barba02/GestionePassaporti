package com.filippoBarbieri.gestionePassaporti.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

enum Sede {
    QUESTURA_VERONA,
    QUESTURA_PADOVA,
    QUESTURA_VENEZIA,
    CONSOLATO_LAS_PALMAS
}

@Entity
public class Slot {
    @Id
    private LocalDateTime datetime;
    // TODO: tenere così o come tabelle a parte
    @NotNull
    @Enumerated(EnumType.STRING)
    private Stato stato;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Sede sede;

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public void setStato(Stato stato) {
        this.stato = stato;
    }
}
