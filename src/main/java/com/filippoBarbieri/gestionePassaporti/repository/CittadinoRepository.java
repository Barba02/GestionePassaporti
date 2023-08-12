package com.filippoBarbieri.gestionePassaporti.repository;


import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.filippoBarbieri.gestionePassaporti.entity.Cittadino;

@Repository
public interface CittadinoRepository extends JpaRepository<Cittadino, String> {
}
