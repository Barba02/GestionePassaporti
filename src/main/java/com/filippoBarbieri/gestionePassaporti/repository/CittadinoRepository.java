package com.filippoBarbieri.gestionePassaporti.repository;


import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.filippoBarbieri.gestionePassaporti.entity.Cittadino;

import java.util.Optional;

@Repository
public interface CittadinoRepository extends JpaRepository<Cittadino, Long> {
    Optional<Cittadino> findByAnagrafica_Cf(String cf);
    boolean existsByAnagrafica_Cf(String cf);
}
