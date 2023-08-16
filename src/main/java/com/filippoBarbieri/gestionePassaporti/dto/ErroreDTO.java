package com.filippoBarbieri.gestionePassaporti.dto;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ErroreDTO {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-SSSS");
    private String tipo;
    private String datetime;
    private String messaggio;

    public ErroreDTO(String tipo, String messaggio) {
        this.tipo = tipo;
        datetime = LocalDateTime.now().format(formatter);
        this.messaggio = messaggio;
    }
}
