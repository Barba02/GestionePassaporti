package com.filippoBarbieri.gestionePassaporti.repository;


import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.filippoBarbieri.gestionePassaporti.entity.Cittadino;
import com.filippoBarbieri.gestionePassaporti.id.IdPrenotazione;
import com.filippoBarbieri.gestionePassaporti.entity.Prenotazione;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, IdPrenotazione> {
    List<Prenotazione> findAllByIdContaining(Cittadino c);
}
