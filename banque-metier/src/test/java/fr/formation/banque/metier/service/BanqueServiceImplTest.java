package fr.formation.banque.metier.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import fr.formation.banque.metier.BanqueException;
import fr.formation.banque.metier.ConfigurationMetier;
import fr.formation.banque.metier.service.BanqueService;
import fr.formation.banque.persistance.ConfigurationPersistance;
import fr.formation.banque.persistance.to.Client;
import fr.formation.banque.persistance.to.Compte;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
	ConfigurationPersistance.class,
	ConfigurationMetier.class
})
@Transactional
public class BanqueServiceImplTest {

	@Autowired
	private BanqueService banqueService;
	
	@Test
	public void testAuthentifier() throws BanqueException {
		Client client = banqueService.authentifier(1, "password");
		assertEquals("DUPONT", client.getNom());
		assertEquals("Robert", client.getPrenom());
		// ...
	}
	
	@Test(expected = BanqueException.class)
	public void testAuthentifierEchec() throws BanqueException {
		Client client = banqueService.authentifier(1, "123456");
	}

	@Test
	public void testLesComptes() throws BanqueException {
		List<Compte> listeDesComptes = banqueService.lesComptes(1);
		assertEquals(2, listeDesComptes.size());
		for(Compte c : listeDesComptes) {
			if(c.getNumero() == 245646786)
				assertEquals(8400.00, c.getSolde(), 0);
			if(c.getNumero() == 263434345)
				assertEquals(20000.00, c.getSolde(), 0);
		}
	}

	@Test
	public void testVirementEntreComptes() throws BanqueException {
		banqueService.virementEntreComptes(245646786, 263434345, 1000);
		
		List<Compte> listeDesComptes = banqueService.lesComptes(1);
		for(Compte c : listeDesComptes) {
			if(c.getNumero() == 245646786)
				assertEquals(7400.00, c.getSolde(), 0);
			if(c.getNumero() == 263434345)
				assertEquals(21000.00, c.getSolde(), 0);
		}
	}

}
