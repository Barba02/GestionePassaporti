package com.filippoBarbieri.gestionePassaporti.repository;


import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.filippoBarbieri.gestionePassaporti.entity.Dipendente;

@Repository
public interface DipendenteRepository extends JpaRepository<Dipendente, String> {}
