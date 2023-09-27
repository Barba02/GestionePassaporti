package com.filippoBarbieri.gestionePassaporti.entity;


import java.time.LocalDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.filippoBarbieri.gestionePassaporti.enums.Sede;
import com.filippoBarbieri.gestionePassaporti.enums.Tipo;

@Entity
public class Notifica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Sede sede;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate inizio;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate fine;
    @ManyToOne
    private Cittadino cittadino;

    public Notifica(Tipo tipo, Sede sede, LocalDate inizio, LocalDate fine, Cittadino cittadino) {
        this.tipo = tipo;
        this.sede = sede;
        this.fine = fine;
        this.inizio = inizio;
        this.cittadino = cittadino;
    }

    public Notifica() {}

    public Cittadino getCittadino() {
        return cittadino;
    }

    public void setCittadino(Cittadino cittadino) {
        this.cittadino = cittadino;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public LocalDate getInizio() {
        return inizio;
    }

    public void setInizio(LocalDate inizio) {
        this.inizio = inizio;
    }

    public LocalDate getFine() {
        return fine;
    }

    public void setFine(LocalDate fine) {
        this.fine = fine;
    }
}
