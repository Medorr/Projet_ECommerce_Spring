package fr.adaming.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.ICategorieDao;
import fr.adaming.model.Categorie;

@Service("catService") // declaration de bean service
@Transactional
public class CategorieService implements ICategorieService{
	
	//Transfor de l'asso UML en JAVA
	@Autowired
	private ICategorieDao catDao;
	//le setter pour l'injection de dependance
	public void setCatDao(ICategorieDao catDao) {
		this.catDao = catDao;
	}

	@Override
	public List<Categorie> getAllCategorieService() {
		// TODO Auto-generated method stub
		return catDao.getAllCategorieDao();
	}

	@Override
	public Categorie getCategorieByIdService(Categorie cat) {
		// TODO Auto-generated method stub
		return catDao.getCategorieByIdDao(cat);
	}

	@Override
	public Categorie ajouterCategorieService(Categorie cat) {
		
		return catDao.ajouterCategorieDao(cat);
	}

	@Override
	public int modifCategorieService(Categorie cat) {
		// TODO Auto-generated method stub
		return catDao.modifCategorieDao(cat);
	}

	@Override
	public Categorie supprCategorieService(Categorie cat) {
		// TODO Auto-generated method stub
		return catDao.supprCategorieDao(cat);
	}

	@Override
	public Categorie rechCategorieByNomService(Categorie cat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Categorie getCategorieByNomOrIdService(Categorie cat) {
		// TODO Auto-generated method stub
		return null;
	}
	


}
