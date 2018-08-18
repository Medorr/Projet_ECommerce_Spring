package fr.adaming.Service;

import java.util.List;

import fr.adaming.model.Categorie;

public interface ICategorieServiceSteven {
	
	public int ajoutCategorie(Categorie cat);
	public int modifCategorie(Categorie cat);
	public int supprCategorie(Categorie cat);
	public Categorie rechCategorieById(Categorie cat);
	public Categorie getCategorieByNom(Categorie cat);	
	public List<Categorie> getCategorieByNomOrId(Categorie cat);
	public List<Categorie> getAllCategorie();

}
