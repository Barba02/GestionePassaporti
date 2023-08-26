package com.filippoBarbieri.gestionePassaporti;


import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import com.filippoBarbieri.gestionePassaporti.entity.Anagrafica;
import com.filippoBarbieri.gestionePassaporti.repository.AnagraficaRepository;

@SpringBootTest
class GestionePassaportiApplicationTests {
	/* @Autowired
	AnagraficaRepository anagraficaRepo;

	@Test
	void contextLoads() {
		String[] cfValidi = {"BRBFPP02R01E349J", "MRRCSR02C30E512F", "CNTDRD99M06A465H", "VRTNRC99T15E512P"};
		Arrays.stream(cfValidi).parallel().map(Anagrafica::new).forEach(cf -> anagraficaRepo.save(cf));
	} */
}
