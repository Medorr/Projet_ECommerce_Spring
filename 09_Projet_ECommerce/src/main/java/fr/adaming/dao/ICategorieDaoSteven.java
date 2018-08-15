package fr.adaming.dao;

import java.util.List;

import fr.adaming.model.Categorie;

public interface ICategorieDaoSteven {
	public int ajoutCategorie(Categorie cat);
	public int modifCategorie(Categorie cat);
	public int supprCategorie(Categorie cat);
	public Categorie rechCategorie(Categorie cat);
	public List<Categorie> getAllCategorie();
	

}
