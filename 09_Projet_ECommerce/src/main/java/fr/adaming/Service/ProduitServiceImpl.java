package fr.adaming.Service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IProduitDao;
import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;

@Service("prService")
@Transactional
public class ProduitServiceImpl implements IProduitService {

	@Autowired
	private IProduitDao prDao;
	public void setPrDao(IProduitDao prDao) {
		this.prDao = prDao;
	}
	public IProduitDao getPrDao() {
		return prDao;
	}
	

//	@Autowired
//	private ICategorieDao catDao;
//	public void setCatDao(ICategorieDao catDao) {
//		this.catDao = catDao;
//	}

	


	@Override
	public int ajoutProduit(Produit pr) {

		// recuperer la cat avec son id
//		Categorie cat = catDao.getCategorieById(pr.getCategorie());
//		pr.setCategorie(cat);
		return prDao.ajoutProduit(pr);
	}

	@Override
	public int modifProduit(Produit pr) {

		return prDao.modifProduit(pr);
	}

	@Override
	public int supprProduit(Produit pr) {
		// TODO Auto-generated method stub
		return prDao.supprProduit(pr);
	}

	@Override
	public Produit rechProduit(Produit pr) {
		// TODO Auto-generated method stub
		return prDao.rechProduit(pr);
	}

	@Override
	public List<Produit> getAllProduit() {
		// TODO Auto-generated method stub
		return prDao.getAllProduit();
	}

	@Override
	public List<Produit> getProduitByIdCat(Categorie cat) {
		return prDao.getProduitByIdCat(cat);
	}
	@Override
	public List<Produit> getProduitByNom(Produit pr) {
		// TODO Auto-generated method stub
		return prDao.getProduitByNom(pr);
	}

}
