package fr.adaming.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Client;

@Repository
public class ClientDaoImpl implements IClientDao {

	/**
	 * declaration de l'attribut sessionFactory
	 */
	@Autowired
	private SessionFactory sf;

	/**
	 * un setter pour injection de dependance
	 */
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	@Override
	public Client enregistrerClient(Client cl) {
		// Recuperer la session
		Session s = sf.getCurrentSession();

		// persist
		s.save(cl);

		return cl;
	}

	@Override
	public int modifClient(Client cl) {
		// Recuperer la session
		Session s = sf.getCurrentSession();

		// Req HQL
		String req = "UPDATE Client cl SET cl.nomClient=:pNom, cl.adresse=:pAdresse, cl.email=:pEmail, cl.tel=:pTel WHERE  cl.idClient=:pIdCl";

		// Query
		Query query = s.createQuery(req);

		// passage des parametres
		query.setParameter("pNom", cl.getNomClient());
		query.setParameter("pAdresse", cl.getAdresse());
		query.setParameter("pEmail", cl.getEmail());
		query.setParameter("pTel", cl.getTel());
		query.setParameter("pIdCl", cl.getIdClient());

		return query.executeUpdate();
	}

	@Override
	public Client supprClient(Client cl) {
		// Recuperer la session
		Session s = sf.getCurrentSession();

		// utilisation de delete
		s.delete(cl);

		return cl;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Client> getAllClient() {
		// Recuperer la session
		Session s = sf.getCurrentSession();

		// Req HQL
		String req = "FROM Client cl";

		// Query
		Query query = s.createQuery(req);

		return query.list();
	}

	@Override
	public Client getClientById(Client cl) {
		// Recuperer la session
		Session s = sf.getCurrentSession();

		// Req HQL
		String req = "FROM Client cl WHERE cl.idClient=:pIdCl";

		// Query
		Query query = s.createQuery(req);

		// passage des parametres
		query.setParameter("pIdCl", cl.getIdClient());

		return (Client) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Client> getClientByNomOrId(Client cl) {
		// Recuperer la session
		Session s = sf.getCurrentSession();

		// Req HQL
		String req = "FROM Client cl WHERE cl.idClient=:pIdCl OR cl.nomClient=:pNom";

		// Query
		Query query = s.createQuery(req);

		// passage des parametres
		query.setParameter("pIdCl", cl.getIdClient());
		query.setParameter("pNom", cl.getNomClient());

		return query.list();
	}

}
