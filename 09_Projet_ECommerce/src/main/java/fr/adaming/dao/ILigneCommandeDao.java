package fr.adaming.dao;

import java.util.List;


import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;

public interface ILigneCommandeDao {
	
	public int ajoutLigneCommande(LigneCommande lc);
	public int modifLigneCommande(LigneCommande lc);
	public int supprLigneCommande(LigneCommande lc);
	public List<LigneCommande> getAllLigneCommande();
	
	public List<LigneCommande> getListeLigneCommandeByComId(Commande com);

}
