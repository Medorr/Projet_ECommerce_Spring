package fr.adaming.dao;

import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Categorie;

@Repository
public class CategorieDaoImplSteven implements ICategorieDaoSteven{
	@Autowired
	private SessionFactory sf;
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	@Override
	public int ajoutCategorie(Categorie cat) {
		Session s = sf.getCurrentSession();
		s.save(cat);
		if (s.get(Categorie.class, cat.getId()) != null) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public int modifCategorie(Categorie cat) {
		Session s = sf.getCurrentSession();
		String req = "UPDATE Categorie cat SET cat.nom=:pNom, cat.description=:pDescr, cat.photo=:pPhoto WHERE cat.id =:pId";
		org.hibernate.Query query = s.createQuery(req);
		query.setParameter("pNom", cat.getNom());
		query.setParameter("pDescr", cat.getDescription());
		query.setParameter("pPhoto", cat.getPhoto());
		query.setParameter("pId", cat.getId());
		
		return query.executeUpdate();
	}

	@Override
	public int supprCategorie(Categorie cat) {
		Session s = sf.getCurrentSession();
		s.delete(cat);
		Categorie verif = (Categorie) s.get(Categorie.class, cat.getId());
		if(verif !=null){
			return 0; 
		}else{
		return 1;}
	}

	@Override
	public Categorie rechCategorieById(Categorie cat) {
		Session s = sf.getCurrentSession();
		Categorie catOut = (Categorie) s.get(Categorie.class, cat.getId());
		if (catOut != null) {
			catOut.setImage("data:image/png;base64,"+Base64.encodeBase64String(catOut.getPhoto()));
			return catOut;
		} else {
			return null;
		}
	}

	@Override
	public List<Categorie> getAllCategorie() {
		Session s = sf.getCurrentSession();
		String req = "FROM Categorie";
		org.hibernate.Query queryListCategorie = s.createQuery(req);
		List<Categorie> listeCategorie = queryListCategorie.list();

		for (Categorie cat : listeCategorie) {
			cat.setImage("data:image/png);base64," + Base64.encodeBase64String(cat.getPhoto()));
		}

		return listeCategorie;
	}

	@Override
	public Categorie rechCategorieByNom(Categorie cat) {
		// recup de la session
		Session s=sf.getCurrentSession();
		Categorie catOut = (Categorie) s.get(Categorie.class, cat.getNom());
		catOut.setImage("data:image/png;base64,"+Base64.encodeBase64String(catOut.getPhoto()));
		return catOut;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Categorie> rechCategorieByIdOrNom(Categorie cat) {
		// recupp de la session
		Session s=sf.getCurrentSession();
		//la req
		String req="FROM Categorie cat where cat.id=:pId or cat.nom=:pNom";
		//Le query
		Query query=s.createQuery(req);
		//Passage des params
		query.setParameter("pId", cat.getId());
		query.setParameter("pNom", cat.getNom());
		
		// Ici : il manquait le categ.setImage() -->étape 4 du tuto, du coup il n'y avait rien dans l'attribut image des catégories de la liste
		List<Categorie> listeCategorie = query.list();
		for (Categorie categ : listeCategorie) {
			categ.setImage("data:image/png);base64," + Base64.encodeBase64String(categ.getPhoto()));
		}
		
		return listeCategorie;
	}
	
	

}
