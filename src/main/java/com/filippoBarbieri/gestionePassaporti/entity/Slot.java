package com.filippoBarbieri.gestionePassaporti.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.filippoBarbieri.gestionePassaporti.enums.Tipo;
import com.filippoBarbieri.gestionePassaporti.enums.Sede;
import com.filippoBarbieri.gestionePassaporti.enums.Stato;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Slot {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd-HH-mm")
    private LocalDateTime datetime;
    @Enumerated(EnumType.STRING)
    private Sede sede;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Stato stato;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    @OneToOne
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Cittadino cittadino;
    @OneToOne
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Dipendente dipendente;

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public Stato getStato() {
        return stato;
    }

    public void setStato(Stato stato) {
        this.stato = stato;
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

    public Cittadino getCittadino() {
        if (cittadino != null && cittadino.getPassword() != null)
            cittadino.getPassword().hide();
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

    public Dipendente getDipendente() {
        return dipendente;
    }

    public void setDipendente(Dipendente dipendente) {
        this.dipendente = dipendente;
    }
}
