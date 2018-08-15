package fr.adaming.dao;

import java.util.List;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;

@Repository
public class LigneCommandeDaoImpl implements ILigneCommandeDao{
	@Autowired
	private SessionFactory sf;
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	@Override
	public int ajoutLigneCommande(LigneCommande lc) {
		Session s = sf.getCurrentSession();
		s.save(lc);
		if (s.get(LigneCommande.class, lc.getId()) != null) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public int modifLigneCommande(LigneCommande lc) {
		Session s = sf.getCurrentSession();
		String req = "UPDATE LigneCommande lc SET lc.quantite=:pQuantite, lc.prix=:pPrix WHERE lc.id =:pId";
		org.hibernate.Query query = s.createQuery(req);
		query.setParameter("pQuantite", lc.getQuantite());
		query.setParameter("pPrix", lc.getPrix());
		query.setParameter("pId", lc.getId());
		
		return query.executeUpdate();
	}

	@Override
	public int supprLigneCommande(LigneCommande lc) {
		Session s = sf.getCurrentSession();
		lc = (LigneCommande) s.merge(lc);
		s.delete(lc);
		LigneCommande verif = (LigneCommande) s.get(LigneCommande.class, lc.getId());
		if(verif ==null){
			return 1; 
		}else{
		return 0;}
	}

	@Override
	public List<LigneCommande> getAllLigneCommande() {
		Session s = sf.getCurrentSession();
		String req = "FROM LigneCommande lc";
		org.hibernate.Query queryListLigneCommande = s.createQuery(req);
		
		return queryListLigneCommande.list();
	}
	
	@Override
	public List<LigneCommande> getListeLigneCommandeByComId(Commande com) {
		Session s = sf.getCurrentSession();
		String req = "FROM LigneCommande lc WHERE co_id=:pId";
		org.hibernate.Query queryListLigneCommande = s.createQuery(req);
		queryListLigneCommande.setParameter("pId", com.getIdCommande());
		
		return queryListLigneCommande.list();
	}
	
	

}
