package fr.adaming.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.ICategorieDaoSteven;
import fr.adaming.model.Categorie;

@Service("catServiceSteven")
@Transactional
public class CategorieServiceImplSteven implements ICategorieServiceSteven{
	
	@Autowired
	private ICategorieDaoSteven catDao;
	public ICategorieDaoSteven getCatDao() {
		return catDao;
	}
	public void setCatDao(ICategorieDaoSteven catDao) {
		this.catDao = catDao;
	}

	@Override
	public int ajoutCategorie(Categorie cat) {
		// TODO Auto-generated method stub
		return catDao.ajoutCategorie(cat);
	}

	@Override
	public int modifCategorie(Categorie cat) {
		// TODO Auto-generated method stub
		return catDao.modifCategorie(cat);
	}

	@Override
	public int supprCategorie(Categorie cat) {
		// TODO Auto-generated method stub
		return catDao.supprCategorie(cat);
	}

	@Override
	public Categorie rechCategorieById(Categorie cat) {
		// TODO Auto-generated method stub
		return catDao.rechCategorieById(cat);
	}

	@Override
	public List<Categorie> getAllCategorie() {
		// TODO Auto-generated method stub
		return catDao.getAllCategorie();
	}
	@Override
	public Categorie getCategorieByNom(Categorie cat) {
		// TODO Auto-generated method stub
		return catDao.rechCategorieByNom(cat);
	}
	@Override
	public List<Categorie> getCategorieByNomOrId(Categorie cat) {
		// TODO Auto-generated method stub
		return catDao.rechCategorieByIdOrNom(cat);
	}

}
