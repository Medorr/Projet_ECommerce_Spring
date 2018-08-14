package fr.adaming.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
/** Classe développée par Steven */
@Entity
@Table(name="commandes")
public class Commande implements Serializable{
	private static final long serialVersionUID = 7190090467064659786L;
	
	/** Déclaration des attributs */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_co")
	private Long idCommande;
	private Date dateCommande;
	
	/** Transformation de l'association UML en Java */
	@ManyToOne
	@JoinColumn(name="cl_id", referencedColumnName="id_cl")
	private Client client;
	
	@OneToMany(mappedBy="commande")
	private List<LigneCommande> listeLigneCo;
	
	
	/** Constructeurs */
	public Commande() {
		super();
	}
	public Commande(Date dateCommande) {
		super();
		this.dateCommande = dateCommande;
	}
	public Commande(Long idCommande, Date dateCommande) {
		super();
		this.idCommande = idCommande;
		this.dateCommande = dateCommande;
	}
	
	/** Getters et setters */
	public Long getIdCommande() {
		return idCommande;
	}
	public void setIdCommande(Long idCommande) {
		this.idCommande = idCommande;
	}
	public Date getDateCommande() {
		return dateCommande;
	}
	public void setDateCommande(Date dateCommande) {
		this.dateCommande = dateCommande;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public List<LigneCommande> getListeLigneCo() {
		return listeLigneCo;
	}
	public void setListeLigneCo(List<LigneCommande> listeLigneCo) {
		this.listeLigneCo = listeLigneCo;
	}
	
	/** toString */
	@Override
	public String toString() {
		return "Commande [idCommande=" + idCommande + ", dateCommande=" + dateCommande + "]";
	}
	
	

}
