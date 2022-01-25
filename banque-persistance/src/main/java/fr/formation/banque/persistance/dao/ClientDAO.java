package fr.formation.banque.persistance.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import fr.formation.banque.persistance.to.Client;

public interface ClientDAO {

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public abstract void ajouterClient(Client client);
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public abstract void modifierClient(Client client);
	
	@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public abstract Client rechercherClientParId(long id);
	
	@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public abstract List<Client> rechercherTousLesClients();
	
	@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public abstract List<Client> rechercherClientParNomEtPrenom(String nom, String prenom);
	
}
