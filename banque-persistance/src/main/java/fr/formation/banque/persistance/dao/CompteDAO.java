package fr.formation.banque.persistance.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import fr.formation.banque.persistance.to.Client;
import fr.formation.banque.persistance.to.Compte;

public interface CompteDAO {

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public abstract void ajouterCompte(Compte compte);
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public abstract void modifierCompte(Compte compte);
	
	@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public abstract Compte rechercherCompteParNumero(long numero);
	
	@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public abstract List<Compte> rechercherComptesClient(Client client);
	
	@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
	public abstract List<Compte> rechercherComptesDebiteurs();
	
}
