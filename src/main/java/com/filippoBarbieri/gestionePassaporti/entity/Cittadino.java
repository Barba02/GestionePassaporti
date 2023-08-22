package com.filippoBarbieri.gestionePassaporti.entity;


import java.sql.Date;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.codec.digest.DigestUtils;

@Entity
public class Cittadino {
    @Id
    @Column(length = 16)
    private String cf;
    @NotNull
    private String nome;
    @NotNull
    private String cognome;
    @NotNull
    private String nazionalita;
    @NotNull
    private Date data_nascita;
    @NotNull
    private String luogo_nascita;
    @NotNull
    @Column(length = 20)
    private String ts;
    @NotNull
    private String password;
    @NotNull
    private Boolean figli_minori;
    @NotNull
    private Boolean diplomatico;
    @NotNull
    private Boolean di_servizio;

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNazionalita() {
        return nazionalita;
    }

    public void setNazionalita(String nazionalita) {
        this.nazionalita = nazionalita;
    }

    public Date getData_nascita() {
        return data_nascita;
    }

    public void setData_nascita(Date data_nascita) {
        this.data_nascita = data_nascita;
    }

    public String getLuogo_nascita() {
        return luogo_nascita;
    }

    public void setLuogo_nascita(String luogo_nascita) {
        this.luogo_nascita = luogo_nascita;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

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
}
