package com.filippoBarbieri.gestionePassaporti.entity;


import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.Calendar;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import org.json.JSONObject;

@Entity
public class Anagrafica {
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
    @Column(length = 2)
    private String provincia_nascita;
    @NotNull
    private Boolean nato_maschio;
    @Transient
    private static final Calendar CAL = Calendar.getInstance();

    public Anagrafica() {}

    public Anagrafica(String nome, String cognome, Boolean nato_maschio, String nazionalita, Date data_nascita, String luogo_nascita, String provincia_nascita) {
        this.nome = nome;
        this.cognome = cognome;
        this.nazionalita = nazionalita;
        this.data_nascita = data_nascita;
        this.luogo_nascita = luogo_nascita;
        this.nato_maschio = nato_maschio;
        this.provincia_nascita = provincia_nascita;
        CAL.setTime(data_nascita);
        cf = retrieveCf();
    }

    private String retrieveCf() {
        try {
            String apiUrlWithParams = "https://api.miocodicefiscale.com/calculate" +
                    "?lname=" + URLEncoder.encode(cognome, StandardCharsets.UTF_8) +
                    "&fname=" + URLEncoder.encode(nome, StandardCharsets.UTF_8) +
                    "&gender=" + URLEncoder.encode((nato_maschio) ? "M" : "F", StandardCharsets.UTF_8) +
                    "&city=" + URLEncoder.encode(luogo_nascita, StandardCharsets.UTF_8) +
                    "&state=" + URLEncoder.encode(provincia_nascita, StandardCharsets.UTF_8) +
                    "&day=" + URLEncoder.encode(String.valueOf(CAL.get(Calendar.DAY_OF_MONTH)), StandardCharsets.UTF_8) +
                    "&month=" + URLEncoder.encode(String.valueOf(CAL.get(Calendar.MONTH)+1), StandardCharsets.UTF_8) +
                    "&year=" + URLEncoder.encode(String.valueOf(CAL.get(Calendar.YEAR)), StandardCharsets.UTF_8) +
                    "&omocodia_level=1&access_token=b8b3b094feb0088aa1d612e742690f1d3816e8a728ad034193a9c6c5c6a3e64d49e";
            HttpRequest req = HttpRequest.newBuilder().uri(URI.create(apiUrlWithParams)).GET().build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(req, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200)
                return "";
            return new JSONObject(response.body()).getJSONObject("data").getString("cf");
        }
        catch (InterruptedException | IOException e) {
            return "";
        }
    }

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

    public Boolean getNato_maschio() {
        return nato_maschio;
    }

    public void setNato_maschio(Boolean male) {
        this.nato_maschio = male;
    }

    public String getProvincia_nascita() {
        return provincia_nascita;
    }

    public void setProvincia_nascita(String provincia_nascita) {
        this.provincia_nascita = provincia_nascita;
    }
}
