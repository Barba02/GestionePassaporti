package com.filippoBarbieri.gestionePassaporti.entity;


import java.sql.Date;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.codec.digest.DigestUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Cittadino {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(referencedColumnName = "cf")
    private Anagrafica anagrafica;
    @NotNull
    @Column(length = 20)
    private String ts;
    @NotNull
    @Column(length = 9)
    private String cie;
    @NotNull
    private String password;
    @NotNull
    private Boolean figli_minori;
    @NotNull
    private Boolean diplomatico;
    @NotNull
    private Boolean di_servizio;
    @Column(length = 9)
    private String passaporto;
    private Date scadenza_passaporto;

    public String getPassword() {
        return password;
    }

    public void hidePassword() {
        password = "*".repeat(16);
    }

    static public String hashPassword(String password) {
        return DigestUtils.sha256Hex(password);
    }

    public static boolean isValid(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()-[{}]:;',?/*~$^+=<>]).{8,}$");
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Anagrafica getAnagrafica() {
        return anagrafica;
    }

    public void setAnagrafica(Anagrafica anagrafica) {
        this.anagrafica = anagrafica;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
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

    public Boolean getDi_servizio() {
        return di_servizio;
    }

    public void setDi_servizio(Boolean di_servizio) {
        this.di_servizio = di_servizio;
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

    public Date getScadenza_passaporto() {
        return scadenza_passaporto;
    }

    public void setScadenza_passaporto(Date scadenza_passaporto) {
        this.scadenza_passaporto = scadenza_passaporto;
    }
}
