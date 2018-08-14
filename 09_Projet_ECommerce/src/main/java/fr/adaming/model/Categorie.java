package fr.adaming.model;

import java.io.Serializable;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/** Classe développée par Demba */
@Entity
@Table(name="categories")
public class Categorie implements Serializable{
	
	/**Declaration des attributs*/
	@Id //**@Id permet l'auto incrementation de l'id*/
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_cat") /** id_cat pour la différencier de id_co (commande) et id_cl (catégorie)  */
	private Long id;
	private String nom;
	@Lob
	private byte[] photo;
	@Transient
	private String image;
	private String description;
	
	/**Transformation de l'asso UML en JAVA*/
	@OneToMany(mappedBy="categorie", cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private List<Produit> listeProduits;
	
	/**Constructeur vide */
	public Categorie() {
		super();
	}

	/**Constructeur avec id*/

	public Categorie(Long id, String nom, byte[] photo, String image, String description) {
		super();
		this.id = id;
		this.nom = nom;
		this.photo = photo;
		this.image = image;
		this.description = description;

	}	/**Constructeur sans id*/
	public Categorie(String nom, byte[] photo, String image, String description) {
		super();
		this.nom = nom;
		this.photo = photo;
		this.image = image;
		this.description = description;
	}

	/**Getters et setters */

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
	

	public List<Produit> getListeProduits() {
		return listeProduits;
	}

	public void setListeProduits(List<Produit> listeProduits) {
		this.listeProduits = listeProduits;
	}

	/**Methodes toString*/
	@Override
	public String toString() {
		return "Categorie [idCat=" + id + ", nom=" + nom + ", photo=" + photo + ", description=" + description + "]";
	}
}
