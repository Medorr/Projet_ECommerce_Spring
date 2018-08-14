package fr.adaming.model;

import java.util.ArrayList;
import java.util.List;

/**Cest une classe transiente: non persistence. On n'a pas d'annotation @Entity*/
public class Panier extends LigneCommande{
	
	/**Les attributs */
	private int idPan;
	
	/** transfo de la relation UML */
	List<LigneCommande> listeLignesCommande = new ArrayList<LigneCommande>();
	
	/**Constructeur vide */
	public Panier() {
		super();
	}
	/** constructeur sans id*/
	public Panier(List<LigneCommande> listeLignesCommande) {
		super();
		this.listeLignesCommande = listeLignesCommande;
	}
	/**constructeur avec id*/
	public Panier(int idPan, List<LigneCommande> listeLignesCommande) {
		super();
		this.idPan = idPan;
		this.listeLignesCommande = listeLignesCommande;
	}
	/**Getters et setters */
	public int getIdPan() {
		return idPan;
	}

	public void setIdPan(int idPan) {
		this.idPan = idPan;
	}
	public List<LigneCommande> getListeLignesCommande() {
		return listeLignesCommande;
	}
	public void setListeLignesCommande(List<LigneCommande> listeLignesCommande) {
		this.listeLignesCommande = listeLignesCommande;
	}
	
	
	
	
	}
