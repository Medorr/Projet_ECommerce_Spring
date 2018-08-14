package fr.adaming.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "produits")
public class Produit implements Serializable {

	/** Declaration des Attributs */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_p")
	private Long idProduit;
	private String designation;
	private String description;
	private Double prix;
	private int quantite;
	@Transient
	private boolean selectionne;

	@Lob
	private byte[] photo;

	@Transient
	private String image;
	
	/**
	 * Transformation des associations UML en JAVA
	 */
	@ManyToOne
	@JoinColumn(name = "cat_id", referencedColumnName = "id_cat")
	private Categorie categorie;

	@OneToMany(mappedBy = "produit")
	private List<LigneCommande> listeLigneCo;

	/** Declaration des Constructeurs */
	public Produit() {
		super();
	}

	public Produit(String designation, String description, Double prix, int quantite, boolean selectionne,
			byte[] photo) {
		super();
		this.designation = designation;
		this.description = description;
		this.prix = prix;
		this.quantite = quantite;
		this.selectionne = selectionne;
		this.photo = photo;
	}

	public Produit(Long id, String designation, String description, Double prix, int quantite, boolean selectionne,
			byte[] photo) {
		super();
		this.idProduit = id;
		this.designation = designation;
		this.description = description;
		this.prix = prix;
		this.quantite = quantite;
		this.selectionne = selectionne;
		this.photo = photo;
	}

	/** Getters et setters */

	public String getDesignation() {
		return designation;
	}

	/**
	 * @return the idProduit
	 */
	public Long getIdProduit() {
		return idProduit;
	}

	/**
	 * @param idProduit
	 *            the idProduit to set
	 */
	public void setIdProduit(Long idProduit) {
		this.idProduit = idProduit;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrix() {
		return prix;
	}

	public void setPrix(Double prix) {
		this.prix = prix;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public boolean isSelectionne() {
		return selectionne;
	}

	public void setSelectionne(boolean selectionne) {
		this.selectionne = selectionne;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public List<LigneCommande> getListeLigneCo() {
		return listeLigneCo;
	}

	public void setListeLigneCo(List<LigneCommande> listeLigneCo) {
		this.listeLigneCo = listeLigneCo;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	/** Declaration de la methode toString */
	@Override
	public String toString() {
		return "Produit [id=" + idProduit + ", designation=" + designation + ", description=" + description + ", prix="
				+ prix + ", quantite=" + quantite + ", selectionne=" + selectionne + "]";
	}

}
