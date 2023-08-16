package com.filippoBarbieri.gestionePassaporti.controller;


import jakarta.persistence.Persistence;
import jakarta.persistence.EntityManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.filippoBarbieri.gestionePassaporti.dto.ErroreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import com.filippoBarbieri.gestionePassaporti.entity.Cittadino;
import com.filippoBarbieri.gestionePassaporti.service.CittadinoService;

@RequestMapping("/cittadino")
public class CittadinoController extends Controller {
    @Autowired
    private CittadinoService service;

    @PostMapping(path = "/registra", produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml"})
    public ResponseEntity<Object> registraCittadino(@RequestBody Cittadino c) {
        try {
            service.registraCittadino(c);
            return new ResponseEntity<>("Cittadino registrato", HttpStatus.CREATED);
        }
        catch(Exception e) {
            String exceptionName = e.getClass().getSimpleName();
            ErroreDTO dto = new ErroreDTO(exceptionName, e.getMessage());
            HttpStatus status = (exceptionName.equals("NoSuchElementException")) ?
                    HttpStatus.NOT_ACCEPTABLE :
                    HttpStatus.CONFLICT;
            return new ResponseEntity<>(dto, status);
        }
    }

    @GetMapping(path = "/{cf}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Object> getCittadino(@PathVariable String cf) {
        try {
            return new ResponseEntity<>(service.getCittadino(cf), HttpStatus.OK);
        }
        catch(Exception e) {
            ErroreDTO dto = new ErroreDTO(e.getClass().getSimpleName(), e.getMessage());
            return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "/{cf}", produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml"})
    public ResponseEntity<Object> modificaCittadino(@PathVariable String cf, @RequestBody Cittadino c) {
        ResponseEntity<Object> res;
        EntityManager em = Persistence.createEntityManagerFactory("updateCittadino").createEntityManager();
        try {
            em.getTransaction().begin();
            /*TODO: verificare non si tolgano a cascata*/
            service.eliminaCittadino(service.getCittadino(cf));
            registraCittadino(c);
            em.getTransaction().commit();
            res = new ResponseEntity<>("Modifica eseguita", HttpStatus.OK);
        } catch (Exception e) {
            em.getTransaction().rollback();
            String exceptionName = e.getClass().getSimpleName();
            ErroreDTO dto = new ErroreDTO(exceptionName, e.getMessage());
            HttpStatus status = (exceptionName.equals("NoSuchElementException")) ?
                    HttpStatus.NOT_FOUND :
                    HttpStatus.INTERNAL_SERVER_ERROR;
            res = new ResponseEntity<>(dto, status);
        }
        finally {
            em.close();
        }
        return res;
    }
}
