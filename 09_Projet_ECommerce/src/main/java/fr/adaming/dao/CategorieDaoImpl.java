package fr.adaming.dao;

import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Categorie;

@Repository //Declarer le Dao comme un bean de SpringIoC
public class CategorieDaoImpl implements ICategorieDao{
	
	//********************************************************************
	//declaration de l'attribut sessionFactory
	//********************************************************************
	// Declaration de l'attribut session Factory. Attention a ne pas declarer la
	// session(s) Hibernate en attribut. sf est un collaborateur de
	// FormateurDaoImpl donc on utilise l'annotation @Autowired
	@Autowired
	private SessionFactory sf;	
	//setter pour l'injection de dependance de sf
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Categorie> getAllCategorieDao() {
		// recup de la sesssion 
		Session s=sf.getCurrentSession();
		
		//La req HQL
		String req="FROM Categorie";
		
		//Creer le sous bus query
		Query query =s.createQuery(req);
		
		//Recuperer la liste
		List<Categorie> listeCat= query.list();
		
		for(Categorie cat:listeCat){
			cat.setImage("data:image/png);base64," + Base64.encodeBase64String(cat.getPhoto()));
			System.out.println("coucou"+cat.toString());
		}
		
		return listeCat;
	}

	@Override
	public Categorie getCategorieByIdDao(Categorie cat) {
		//recup de la session
		Session s=sf.getCurrentSession();
		s.get(Categorie.class, cat.getId());
		cat.setImage("data:image/png);base64," + Base64.encodeBase64String(cat.getPhoto()));///Attention ajouter pour l'erreur de la photo
		return cat;
	}

	@Override
	public Categorie ajouterCategorieDao(Categorie cat) {
		// la session
		Session s=sf.getCurrentSession();
		
		s.save(cat);
		return cat;
	}

	@Override
	public int modifCategorieDao(Categorie cat) {
		
		// la session
		Session s=sf.getCurrentSession();
		
		//La req
		String req="UPDATE Categorie cat set cat.nom=:pNom, cat.photo=:pPhoto and cat.description=:pdescription where cat.id=:pId";
		
		//Query
		Query query=s.createQuery(req);
		
		//Passage des params
		query.setParameter("pNom", cat.getNom());
		query.setParameter("pPhoto", cat.getImage());
		query.setParameter("pDescription", cat.getDescription());
		query.setParameter("pId", cat.getId());
		
		return query.executeUpdate();
	}

	@Override
	public Categorie supprCategorieDao(Categorie cat) {
		// la session
		Session s=sf.getCurrentSession();
		s.delete(cat);
		return cat;
	}

	@Override
	public Categorie getCategorieByNomDao(Categorie cat) {
		// recup de la session
		Session s=sf.getCurrentSession();
		s.get(Categorie.class, cat.getNom());			
		return cat;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Categorie> getCategorieByNomOrIdDao(Categorie cat) {
		cat.setImage("data:image/png);base64," + Base64.encodeBase64String(cat.getPhoto()));///Attention ajouter pour l'erreur de la photo
		// recupp de la session
		Session s=sf.getCurrentSession();
		//la req
		String req="FROM Categorie cat where cat.id=:pId or cat.nom=:pNom";
		//Le query
		Query query=s.createQuery(req);
		//Passage des params
		query.setParameter("pId", cat.getId());
		query.setParameter("pNom", cat.getNom());
		return query.list();
	}

}
