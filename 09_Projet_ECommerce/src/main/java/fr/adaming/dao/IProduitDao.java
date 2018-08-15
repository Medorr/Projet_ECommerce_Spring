package fr.adaming.dao;

import java.util.List;


import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;


public interface IProduitDao {
	
	
	public int ajoutProduit(Produit pr);
	public int modifProduit(Produit pr);
	public int supprProduit(Produit pr);
	public Produit rechProduit(Produit pr);
	public List<Produit> getAllProduit();
	
	public List<Produit> getProduitByIdCat(Categorie cat);
	

}
