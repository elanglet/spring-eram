package fr.formation.banque.persistance.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import fr.formation.banque.config.ConfigurationPersistance;
import fr.formation.banque.persistance.dao.ClientDAO;
import fr.formation.banque.persistance.to.Client;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConfigurationPersistance.class)
@Transactional
public class JdbcClientDAOTest {

	// Injection du bean à tester
	@Autowired
	private ClientDAO clientDAO;
	
	@Test
	public void testAjouterClient() {
		Client client = new Client();
		client.setPrenom("Gérard");
		client.setNom("LEPIC");
		client.setAdresse("56 rue de Nantes");
		client.setCodePostal("35000");
		client.setVille("Rennes");
		client.setMotDePasse("123456");
		
		clientDAO.ajouterClient(client);
		
		List<Client> listeDesClients = clientDAO.rechercherTousLesClients();
		assertEquals(5, listeDesClients.size());
	}

	@Test
	public void testModifierClient() {
		Client client = clientDAO.rechercherClientParId(1);
		assertEquals("Paris", client.getVille());
		
		client.setVille("Nantes");
		
		clientDAO.modifierClient(client);
		
		client = clientDAO.rechercherClientParId(1);
		assertEquals("Nantes", client.getVille());
	}

	@Test
	public void testRechercherClientParId() {
		Client client = clientDAO.rechercherClientParId(2);
		assertEquals("DRUAND", client.getNom());
		assertEquals("Bruno", client.getPrenom());
		assertEquals("Place de Bretagne", client.getAdresse());
		assertEquals("35000", client.getCodePostal());
		assertEquals("Rennes", client.getVille());
		assertEquals("password", client.getMotDePasse());
	}

	@Test
	public void testRechercherTousLesClients() {
		List<Client> listeDesClients = clientDAO.rechercherTousLesClients();
		assertEquals(4, listeDesClients.size());
	}

	@Test
	public void testRechercherClientParNomEtPrenom() {
		List<Client> liste1 = clientDAO.rechercherClientParNomEtPrenom("DUPONT", "Robert");
		assertEquals(1, liste1.size());
		List<Client> liste2 = clientDAO.rechercherClientParNomEtPrenom("DRUAND", "Bruno");
		assertEquals(1, liste2.size());
	}

}
