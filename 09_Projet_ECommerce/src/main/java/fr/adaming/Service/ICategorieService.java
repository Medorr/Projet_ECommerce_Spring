package fr.adaming.Service;

import java.util.List;

import fr.adaming.model.Categorie;

public interface ICategorieService {
	
	public List<Categorie> getAllCategorieService();
	public Categorie getCategorieByIdService(Categorie cat);
	public Categorie ajouterCategorieService(Categorie cat);
	public int modifCategorieService(Categorie cat);
	public Categorie supprCategorieService(Categorie cat);
	public Categorie getCategorieByNomService(Categorie cat);
	
	public List<Categorie> getCategorieByNomOrIdService(Categorie cat);

}
