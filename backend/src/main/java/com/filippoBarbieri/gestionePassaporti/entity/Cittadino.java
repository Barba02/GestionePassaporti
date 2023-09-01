package com.filippoBarbieri.gestionePassaporti.entity;


import java.util.List;
import java.time.LocalDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
public class Cittadino {
    @Id
    private String email;
    @OneToOne
    @JoinColumn(referencedColumnName = "cf")
    private Anagrafica anagrafica;
    @NotNull
    @Column(length = 9)
    private String cie;
    @NotNull
    private Password password;
    @NotNull
    private Boolean figli_minori;
    @NotNull
    private Boolean diplomatico;
    @Column(length = 9)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String passaporto;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate scadenza_passaporto;
    @OneToMany
    @JsonIgnore
    private List<Slot> slots;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Anagrafica getAnagrafica() {
        return anagrafica;
    }

    public void setAnagrafica(Anagrafica anagrafica) {
        this.anagrafica = anagrafica;
    }

    public Boolean getFigli_minori() {
        return figli_minori;
    }

    public void setFigli_minori(Boolean figli_minori) {
        this.figli_minori = figli_minori;
    }

    public Boolean getDiplomatico() {
        return diplomatico;
    }

    public void setDiplomatico(Boolean diplomatico) {
        this.diplomatico = diplomatico;
    }

    public String getCie() {
        return cie;
    }

    public void setCie(String cie) {
        this.cie = cie;
    }

    public String getPassaporto() {
        return passaporto;
    }

    public void setPassaporto(String passaporto) {
        this.passaporto = passaporto;
    }

    public LocalDate getScadenza_passaporto() {
        return scadenza_passaporto;
    }

    public void setScadenza_passaporto(LocalDate scadenza_passaporto) {
        this.scadenza_passaporto = scadenza_passaporto;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }
}
