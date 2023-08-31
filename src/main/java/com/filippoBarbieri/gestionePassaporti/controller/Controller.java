package com.filippoBarbieri.gestionePassaporti.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.filippoBarbieri.gestionePassaporti.dto.ErroreDTO;
import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class Controller {
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
}
