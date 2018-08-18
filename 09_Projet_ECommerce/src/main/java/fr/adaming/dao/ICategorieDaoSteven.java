package fr.adaming.dao;

import java.util.List;

import fr.adaming.model.Categorie;

public interface ICategorieDaoSteven {
	public int ajoutCategorie(Categorie cat);
	public int modifCategorie(Categorie cat);
	public int supprCategorie(Categorie cat);
	public Categorie rechCategorieById(Categorie cat);
	public Categorie rechCategorieByNom(Categorie cat);
	public List<Categorie> rechCategorieByIdOrNom(Categorie cat);
	public List<Categorie> getAllCategorie();
	

}
