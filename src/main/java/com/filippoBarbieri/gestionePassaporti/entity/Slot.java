package com.filippoBarbieri.gestionePassaporti.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.filippoBarbieri.gestionePassaporti.id.IdSlot;
import com.filippoBarbieri.gestionePassaporti.enums.Tipo;
import com.filippoBarbieri.gestionePassaporti.enums.Sede;
import com.filippoBarbieri.gestionePassaporti.enums.Stato;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@IdClass(IdSlot.class)
public class Slot {
    @Id
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime datetime;
    @Id
    @Enumerated(EnumType.STRING)
    private Sede sede;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Stato stato;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

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

    @JsonIgnore
    public IdSlot getId() {
        return new IdSlot(datetime, sede);
    }
}
