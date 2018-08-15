package fr.adaming.dao;

import java.util.List;


import org.apache.commons.codec.binary.Base64;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;

@Repository
public class ProduitDaoImpl implements IProduitDao {
	@Autowired
	private SessionFactory sf;
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	@Override
	public int ajoutProduit(Produit pr) {
		Session s = sf.getCurrentSession();
		s.persist(pr);
		if (s.get(Produit.class, pr.getIdProduit()) != null) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public int modifProduit(Produit pr) {
		Session s = sf.getCurrentSession();
		String req = "UPDATE Produit pr SET pr.designation=:pDesign, pr.description=:pDescr, pr.prix=:pPrix,pr.quantite=:pQuant, pr.photo=:pPhoto WHERE pr.id =:pId";
		org.hibernate.Query query = s.createQuery(req);
		query.setParameter("pDesign", pr.getDesignation());
		query.setParameter("pDescr", pr.getDescription());
		query.setParameter("pPrix", pr.getPrix());
		query.setParameter("pQuant", pr.getQuantite());
		query.setParameter("pPhoto", pr.getPhoto());
		query.setParameter("pId", pr.getIdProduit());
		
		return query.executeUpdate();
	}

	@Override
	public int supprProduit(Produit pr) {
		Session s = sf.getCurrentSession();
		s.delete(pr);
		Produit verif = (Produit) s.get(Produit.class, pr.getIdProduit());
		if(verif !=null){
			return 0; 
		}else{
		return 1;}
	}

	@Override
	public Produit rechProduit(Produit pr) {
		Session s = sf.getCurrentSession();
		Produit prOut = (Produit) s.get(Produit.class, pr.getIdProduit());
		if (prOut != null) {
			return prOut;
		} else {
			return null;
		}
	}

	@Override
	public List<Produit> getAllProduit() {
		Session s = sf.getCurrentSession();
		String req = "FROM Produit";
		org.hibernate.Query queryListProduit = s.createQuery(req);
		List<Produit> listeProduit = queryListProduit.list();

		for (Produit pr : listeProduit) {
			pr.setImage("data:image/png);base64," + Base64.encodeBase64String(pr.getPhoto()));
		}

		return listeProduit;

	}

	@Override
	public List<Produit> getProduitByIdCat(Categorie cat) {
		Session s = sf.getCurrentSession();
		String req = "FROM Produit pr WHERE pr.categorie.id=:pId";
		org.hibernate.Query queryListProduitCat = s.createQuery(req);
		queryListProduitCat.setParameter("pId", cat.getId());
		List<Produit> listeProduitCat = queryListProduitCat.list();

		for (Produit pr : listeProduitCat) {
			pr.setImage("data:image/png);base64," + Base64.encodeBase64String(pr.getPhoto()));
		}

		return listeProduitCat;
	}

}
