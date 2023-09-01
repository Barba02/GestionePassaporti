package com.filippoBarbieri.gestionePassaporti;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import com.filippoBarbieri.gestionePassaporti.repository.AnagraficaRepository;
import com.filippoBarbieri.gestionePassaporti.repository.DipendenteRepository;

@SpringBootTest
public class LoadTests {
    @Autowired
    private AnagraficaRepository anagraficaRepo;
    @Autowired
    private DipendenteRepository dipendenteRepo;

    @Test
    public void checkLoadDipendenti() {
        assertEquals(4, dipendenteRepo.count());
    }

    @Test
    public void checkLoadAnagrafiche() {
        assertEquals(4, anagraficaRepo.count());
    }
}
