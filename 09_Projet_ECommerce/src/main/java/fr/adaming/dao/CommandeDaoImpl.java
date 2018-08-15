package fr.adaming.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Commande;

@Repository
public class CommandeDaoImpl implements ICommandeDao{
	@Autowired
	private SessionFactory sf;

	@Override
	public int ajoutCommande(Commande com) {
		Session s = sf.getCurrentSession();
		s.save(com);
		if (s.get(Commande.class, com.getIdCommande()) != null) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public int modifCommande(Commande com) {
		Session s = sf.getCurrentSession();
		String req = "UPDATE Commande com SET com.dateCommande=:pDate WHERE com.idCommande =:pId";
		org.hibernate.Query query = s.createQuery(req);
		query.setParameter("pDate", com.getDateCommande());
		
		return query.executeUpdate();
	}

	@Override
	public int supprCommande(Commande com) {
		Session s = sf.getCurrentSession();
		com = (Commande) s.get(Commande.class,com.getIdCommande());
		s.delete(com);
		Commande verif = (Commande) s.get(Commande.class, com.getIdCommande());
		if(verif ==null){
			return 1; 
		}else{
		return 0;}
	}

	@Override
	public List<Commande> getAllCommande() {
		Session s = sf.getCurrentSession();
		String req = "FROM LigneCommande com";
		org.hibernate.Query queryListCommande = s.createQuery(req);
		
		return queryListCommande.list();
	}
	
	@Override
	public Commande rechCommande(Commande com) {
		Session s = sf.getCurrentSession();
		Commande comOut = (Commande) s.get(Commande.class, com.getIdCommande());
		if (comOut != null) {
			return comOut;
		} else {
			return null;
		}
	}

}
