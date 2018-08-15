package fr.adaming.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.ILigneCommandeDao;
import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;

@Repository("lcService")
@Transactional
public class LigneCommandeServiceImpl implements ILigneCommandeService{
	
	@Autowired
	private ILigneCommandeDao lcDao;
	public void setLcDao(ILigneCommandeDao lcDao) {
		this.lcDao = lcDao;
	}

	@Override
	public int ajoutLigneCommande(LigneCommande lc) {
		// TODO Auto-generated method stub
		return lcDao.ajoutLigneCommande(lc);
	}

	@Override
	public int modifLigneCommande(LigneCommande lc) {
		// TODO Auto-generated method stub
		return lcDao.modifLigneCommande(lc);
	}

	@Override
	public int supprLigneCommande(LigneCommande lc) {
		// TODO Auto-generated method stub
		return lcDao.supprLigneCommande(lc);
	}

	@Override
	public List<LigneCommande> getAllLigneCommande() {
		// TODO Auto-generated method stub
		return lcDao.getAllLigneCommande();
	}

	@Override
	public List<LigneCommande> getListeLigneCommandeByComId(Commande com) {
		// TODO Auto-generated method stub
		return lcDao.getListeLigneCommandeByComId(com);
	}

}
