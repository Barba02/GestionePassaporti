package com.filippoBarbieri.gestionePassaporti.entity;


import java.sql.Date;
import java.sql.Time;
import jakarta.persistence.*;

enum Sede {
    QUESTURA_VERONA,
    QUESTURA_PADOVA,
    QUESTURA_VENEZIA,
    CONSOLATO_LAS_PALMAS
}

@Entity
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date data;
    private Time ora;
    // TODO: tenere così o come tabelle a parte
    @Enumerated(EnumType.STRING)
    private Stato stato;
    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    @Enumerated(EnumType.STRING)
    private Sede sede;

    public Integer getId() {
        return id;
    }

    public Date getData() {
        return data;
    }

    public Time getOra() {
        return ora;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public void setStato(Stato stato) {
        this.stato = stato;
    }
}
