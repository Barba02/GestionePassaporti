package com.filippoBarbieri.gestionePassaporti.repository;


import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.filippoBarbieri.gestionePassaporti.entity.Notifica;
import com.filippoBarbieri.gestionePassaporti.entity.Cittadino;

@Repository
public interface NotificaRepository extends JpaRepository<Notifica, Long> {
    List<Notifica> findAllByCittadino(Cittadino c);
}
