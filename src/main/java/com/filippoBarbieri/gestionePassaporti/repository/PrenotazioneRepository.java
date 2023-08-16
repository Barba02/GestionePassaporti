package com.filippoBarbieri.gestionePassaporti.repository;


import java.util.List;

import com.filippoBarbieri.gestionePassaporti.entity.Cittadino;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.filippoBarbieri.gestionePassaporti.entity.Prenotazione;
import com.filippoBarbieri.gestionePassaporti.entity.IdPrenotazione;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, IdPrenotazione> {
    List<Prenotazione> findAllByIdContaining(Cittadino c);
}
