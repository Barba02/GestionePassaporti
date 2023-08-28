package com.filippoBarbieri.gestionePassaporti;


import java.sql.Date;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import com.filippoBarbieri.gestionePassaporti.entity.Anagrafica;
import com.filippoBarbieri.gestionePassaporti.repository.AnagraficaRepository;

@SpringBootTest
public class GestionePassaportiApplicationTests {
	@Autowired
	private AnagraficaRepository anagraficaRepo;
	private static final Anagrafica barba = new Anagrafica("Filippo",
			"Barbieri",
			true,
			"Italiana",
			Date.valueOf(LocalDate.of(2002, 10, 1)),
			"Isola della Scala",
			"VR");
	private static final Anagrafica pier = new Anagrafica("Piergiorgio",
			"Rocchetto",
			true,
			"Italiana",
			Date.valueOf(LocalDate.of(1999, 10, 21)),
			"Verona",
			"VR");
	private static final Anagrafica dodi = new Anagrafica("Edoardo",
			"Cantiero",
			true,
			"Italiana",
			Date.valueOf(LocalDate.of(1999, 8, 6)),
			"Asiago",
			"VI");

	@Test
	void contextLoads() {
		if ("BRBFPP02R01E349J".equals(barba.getCf()))
			anagraficaRepo.save(barba);
		if ("RCCPGR99R21L781I".equals(pier.getCf()))
			anagraficaRepo.save(pier);
		if ("CNTDRD99M06A465H".equals(dodi.getCf()))
			anagraficaRepo.save(dodi);
		assertEquals(3, anagraficaRepo.count());
	}
}
