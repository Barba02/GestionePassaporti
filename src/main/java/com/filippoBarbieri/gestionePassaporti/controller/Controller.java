package com.filippoBarbieri.gestionePassaporti.controller;


import com.filippoBarbieri.gestionePassaporti.service.CittadinoService;
import com.filippoBarbieri.gestionePassaporti.service.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.filippoBarbieri.gestionePassaporti.dto.ErroreDTO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.NoSuchElementException;

public abstract class Controller {
    @Autowired
    private CittadinoService cittadinoService;
    @Autowired
    private DipendenteService dipendenteService;

    static class ErroreNonGestitoDTO extends ErroreDTO {
        public ErroreNonGestitoDTO(String tipo, String messaggio, String path) {
            super(tipo, messaggio);
        }
    }
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErroreDTO> gestioneErroreCentralizzata(Exception e) {
        StackTraceElement ste = e.getStackTrace()[0];
        ErroreDTO dto = new ErroreNonGestitoDTO(e.getClass().getSimpleName(),
                e.getMessage(),
                ste.getClassName() + "." + ste.getMethodName());
        return new ResponseEntity<>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(path = "/login", produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml"})
    public ResponseEntity<Object> login(@RequestBody String[] cred) {
        String key = cred[0];
        String psw = cred[1];
        try {
            if (key.length() == 16)
                return new ResponseEntity<>(cittadinoService.login(key, psw), HttpStatus.OK);
            else if (key.length() == 6)
                return new ResponseEntity<>(dipendenteService.login(key, psw), HttpStatus.OK);
            else
                return new ResponseEntity<>("Metodo non permesso", HttpStatus.FORBIDDEN);
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(new ErroreDTO(e.getClass().getSimpleName(), e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ErroreDTO(e.getClass().getSimpleName(), e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
