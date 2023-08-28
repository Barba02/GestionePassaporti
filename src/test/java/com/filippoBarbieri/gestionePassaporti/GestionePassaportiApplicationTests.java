package com.filippoBarbieri.gestionePassaporti;


import java.sql.Date;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import com.filippoBarbieri.gestionePassaporti.entity.Anagrafica;
import com.filippoBarbieri.gestionePassaporti.repository.AnagraficaRepository;

@SpringBootTest
class GestionePassaportiApplicationTests {
	@Autowired
	AnagraficaRepository anagraficaRepo;

	@Test
	void contextLoads() {
		Anagrafica barba = new Anagrafica("Filippo",
				"Barbieri",
				true,
				"Italiana",
				Date.valueOf(LocalDate.of(2002, 10, 1)),
				"Isola della Scala",
				"VR");
		Anagrafica pier = new Anagrafica("Piergiorgio",
				"Rocchetto",
				true,
				"Italiana",
				Date.valueOf(LocalDate.of(1999, 10, 21)),
				"Verona",
				"VR");
		if ("BRBFPP02R01E349J".equals(barba.getCf()))
			anagraficaRepo.save(barba);
		if ("RCCPGR99R21L781I".equals(pier.getCf()))
			anagraficaRepo.save(pier);
	}
}
