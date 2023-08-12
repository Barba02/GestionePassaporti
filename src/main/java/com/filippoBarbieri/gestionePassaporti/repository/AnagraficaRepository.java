package com.filippoBarbieri.gestionePassaporti.repository;


import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.filippoBarbieri.gestionePassaporti.entity.Anagrafica;

@Repository
public interface AnagraficaRepository extends JpaRepository<Anagrafica, String> {
}
