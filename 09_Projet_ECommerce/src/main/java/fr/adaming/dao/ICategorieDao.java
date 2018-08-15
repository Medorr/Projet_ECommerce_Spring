package fr.adaming.dao;

import java.util.List;

import fr.adaming.model.Categorie;

public interface ICategorieDao {
	
	public List<Categorie> getAllCategorieDao();
	public Categorie getCategorieByIdDao(Categorie cat);
	public Categorie ajouterCategorieDao(Categorie cat);
	public int modifCategorieDao(Categorie cat);
	public Categorie supprCategorieDao(Categorie cat);
	public Categorie rechCategorieByNomDao(Categorie cat);
	
	public Categorie getCategorieByNomOrIdDao(Categorie cat);

}
