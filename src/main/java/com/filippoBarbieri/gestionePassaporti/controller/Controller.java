package com.filippoBarbieri.gestionePassaporti.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.filippoBarbieri.gestionePassaporti.dto.ErroreDTO;
import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class Controller {
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErroreDTO> gestioneErroreCentralizzata(Exception e) {
        ErroreDTO dto = new ErroreDTO(e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
