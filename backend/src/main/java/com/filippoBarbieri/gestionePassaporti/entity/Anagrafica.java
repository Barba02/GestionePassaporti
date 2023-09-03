package com.filippoBarbieri.gestionePassaporti.entity;


import java.net.URI;
import org.json.JSONObject;
import java.time.LocalDate;
import java.io.IOException;
import java.net.URLEncoder;
import jakarta.persistence.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import jakarta.validation.constraints.NotNull;
import com.filippoBarbieri.gestionePassaporti.enums.Sesso;

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
    private LocalDate data_nascita;
    @NotNull
    private String luogo_nascita;
    @NotNull
    @Column(length = 2)
    private String provincia_nascita;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Sesso sesso;

    public Anagrafica() {}

    public Anagrafica(String nome, String cognome, Sesso sesso, LocalDate data_nascita, String luogo_nascita, String provincia_nascita) {
        this.nome = nome;
        this.cognome = cognome;
        this.data_nascita = data_nascita;
        this.luogo_nascita = luogo_nascita;
        this.sesso = sesso;
        this.provincia_nascita = provincia_nascita;
        try {
            cf = retrieveCf();
        }
        catch (IOException | InterruptedException e) {
            cf = "";
        }
    }

    private String retrieveCf() throws IOException, InterruptedException {
        String apiUrlWithParams = "https://api.miocodicefiscale.com/calculate" +
                "?lname=" + URLEncoder.encode(cognome, StandardCharsets.UTF_8) +
                "&fname=" + URLEncoder.encode(nome, StandardCharsets.UTF_8) +
                "&gender=" + URLEncoder.encode(String.valueOf(sesso), StandardCharsets.UTF_8) +
                "&city=" + URLEncoder.encode(luogo_nascita, StandardCharsets.UTF_8) +
                "&state=" + URLEncoder.encode(provincia_nascita, StandardCharsets.UTF_8) +
                "&day=" + URLEncoder.encode(String.valueOf(data_nascita.getDayOfMonth()), StandardCharsets.UTF_8) +
                "&month=" + URLEncoder.encode(String.valueOf(data_nascita.getMonthValue()), StandardCharsets.UTF_8) +
                "&year=" + URLEncoder.encode(String.valueOf(data_nascita.getYear()), StandardCharsets.UTF_8) +
                "&access_token=b8b3b094feb0088aa1d612e742690f1d3816e8a728ad034193a9c6c5c6a3e64d49e";
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create(apiUrlWithParams)).GET().build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(req, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200)
            throw new InterruptedException("Response code not valid");
        return new JSONObject(response.body()).getJSONObject("data").getString("cf");
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

    public LocalDate getData_nascita() {
        return data_nascita;
    }

    public void setData_nascita(LocalDate data_nascita) {
        this.data_nascita = data_nascita;
    }

    public String getLuogo_nascita() {
        return luogo_nascita;
    }

    public void setLuogo_nascita(String luogo_nascita) {
        this.luogo_nascita = luogo_nascita;
    }

    public Sesso getSesso() {
        return sesso;
    }

    public void setSesso(Sesso sesso) {
        this.sesso = sesso;
    }

    public String getProvincia_nascita() {
        return provincia_nascita;
    }

    public void setProvincia_nascita(String provincia_nascita) {
        this.provincia_nascita = provincia_nascita;
    }
}
