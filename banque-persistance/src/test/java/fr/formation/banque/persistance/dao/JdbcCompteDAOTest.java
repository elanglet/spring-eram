package fr.formation.banque.persistance.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import fr.formation.banque.persistance.ConfigurationPersistance;
import fr.formation.banque.persistance.dao.ClientDAO;
import fr.formation.banque.persistance.dao.CompteDAO;
import fr.formation.banque.persistance.to.Client;
import fr.formation.banque.persistance.to.Compte;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConfigurationPersistance.class)
@Transactional
public class JdbcCompteDAOTest {

	@Autowired
	private CompteDAO compteDAO;
	
	@Autowired
	private ClientDAO clientDAO;
	
	@Test
	public void testAjouterCompte() {
		Compte compte = new Compte();
		Client client2 = clientDAO.rechercherClientParId(2);
		compte.setNumero(456236);
		compte.setSolde(22000.00);
		compte.setIdClient(1);
		
		compteDAO.ajouterCompte(compte);
		
		Compte compteRecupere = compteDAO.rechercherCompteParNumero(456236);
		assertEquals(22000.00, compteRecupere.getSolde(), 0);
		assertEquals(1, compte.getIdClient());
		// ...
	}

	@Test
	public void testModifierCompte() {
		Compte compte = compteDAO.rechercherCompteParNumero(245646786);
		assertEquals(8400.00, compte.getSolde(), 0);
		
		compte.setSolde(9999.00);
		compteDAO.modifierCompte(compte);
		
		compte = compteDAO.rechercherCompteParNumero(245646786);
		assertEquals(9999.00, compte.getSolde(), 0);
	}

	@Test
	public void testRechercherCompteParNumero() {
		Compte compte = compteDAO.rechercherCompteParNumero(245646786);
		assertEquals(8400.00, compte.getSolde(), 0);
	}

	@Test
	public void testRechercherComptesClient() {
		Client client = clientDAO.rechercherClientParId(1);
		List<Compte> listeDesComptes = compteDAO.rechercherComptesClient(client);
		assertEquals(2, listeDesComptes.size());
		// ... 
	}

	@Test
	public void testRechercherComptesDebiteurs() {
		List<Compte> listeDesComptes = compteDAO.rechercherComptesDebiteurs();
		assertEquals(1, listeDesComptes.size());
		// ...
	}

}
