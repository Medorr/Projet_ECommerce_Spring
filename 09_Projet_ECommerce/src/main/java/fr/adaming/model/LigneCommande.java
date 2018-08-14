package fr.adaming.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ligneCommandes")
public class LigneCommande implements Serializable {
	
	/** Declaration des attributs
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_lc")
	private int id;
	private int quantite;
	private double prix;
	
	/** Transformation association UML en JAVA*/
	@ManyToOne
	@JoinColumn(name="co_id", referencedColumnName="id_co")
	private Commande commande;
	
	@ManyToOne
	@JoinColumn(name="p_id", referencedColumnName="id_p")
	private Produit produit;
	
	/**
	 Constructeurs
	 */
	public LigneCommande() {
		super();
	}
	/**
	 * @param quantité
	 * @param prix
	 */
	public LigneCommande(int quantite, double prix) {
		super();
		this.quantite = quantite;
		this.prix = prix;
	}
	/**
	 * @param id
	 * @param quantité
	 * @param prix
	 */
	public LigneCommande(int id, int quantite, double prix) {
		super();
		this.id = id;
		this.quantite = quantite;
		this.prix = prix;
	}
	/** 
	Getters et setters
	 * @return the quantité
	 */
	public int getQuantite() {
		return quantite;
	}
	/**
	 * @param quantité the quantité to set
	 */
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the prix
	 */
	public double getPrix() {
		return prix;
	}
	/**
	 * @param prix the prix to set
	 */
	public void setPrix(double prix) {
		this.prix = prix;
	}
	/**
	 * @return the commande
	 */
	public Commande getCommande() {
		return commande;
	}
	/**
	 * @param commande the commande to set
	 */
	public void setCommande(Commande commande) {
		this.commande = commande;
	}
	/**
	 * @return the produit
	 */
	public Produit getProduit() {
		return produit;
	}
	/**
	 * @param produit the produit to set
	 */
	public void setProduit(Produit produit) {
		this.produit = produit;
	}
	
	
	/* methode ToString
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LigneCommande [quantite=" + quantite + ", prix=" + prix + "]";
	}
	
	
	
	
	

}
