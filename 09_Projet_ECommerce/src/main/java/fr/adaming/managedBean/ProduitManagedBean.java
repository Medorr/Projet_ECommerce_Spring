package fr.adaming.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;

import fr.adaming.Service.IProduitService;
import fr.adaming.model.Categorie;
import fr.adaming.model.LigneCommande;
import fr.adaming.model.Produit;

@ManagedBean(name="prMB")
@RequestScoped
public class ProduitManagedBean implements Serializable {

	@ManagedProperty(value="#{prService}")
	private IProduitService prService;
	public void setPrService(IProduitService prService) {
		this.prService = prService;
	}
	public IProduitService getPrService() {
		return prService;
	}

	private List<Produit> listeProduit;
	private Produit produit;
	private Categorie categorie;
	private LigneCommande ligneCommande;
	private List<Produit> listeProduitCat;

	private UploadedFile file;
	
	@PostConstruct
	public void init(){
		listeProduit = prService.getAllProduit();
		
	}

	public ProduitManagedBean() {
		super();
		this.produit = new Produit();
		this.categorie = new Categorie();
		
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public LigneCommande getLigneCommande() {
		return ligneCommande;
	}

	public void setLigneCommande(LigneCommande ligneCommande) {
		this.ligneCommande = ligneCommande;
	}

	public List<Produit> getListeProduit() {
		return listeProduit;
	}

	public void setListeProduit(List<Produit> listeProduit) {
		this.listeProduit = listeProduit;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public List<Produit> getListeProduitCat() {
		return listeProduitCat;
	}

	public void setListeProduitCat(List<Produit> listeProduitCat) {
		this.listeProduitCat = listeProduitCat;
	}

	/** méthodes : */
	public String ajoutProduit() {
		this.produit.setCategorie(this.categorie);
		this.produit.setPhoto(file.getContents());
		int prAjout = prService.ajoutProduit(this.produit);

		if (prAjout != 0) {
			listeProduit = prService.getAllProduit();
			return "listeProduit";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("L'ajout a échoué !"));
			return "ajoutProduit";
		}

	}
	
	public String modifProduit() {
		
		this.produit.setPhoto(file.getContents());
		int prModif = prService.modifProduit(this.produit);

		if (prModif != 0) {
			listeProduit = prService.getAllProduit();
			return "listeProduit";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La modification a échoué !"));
			return "modifProduit";
		}

	}
	
	public String supprProduit(){
		int prSuppr = prService.supprProduit(this.produit);
		
		if(prSuppr != 0){
			listeProduit = prService.getAllProduit();
			return "listeProduit";
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La suppression a échoué !"));
			return "supprProduit";
		}
		
	}
	
	public String rechProduitByIdCat(){
		List<Produit>prRechCat = prService.getProduitByIdCat(this.categorie);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("catEnCours", this.categorie);
		if(prRechCat != null){
			listeProduit = prRechCat;
			return "listeProduit";
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("ça a foiré !"));
			return "accueil";
		}
		
	}
	
	public String rechProduit(){
		Produit prRech = prService.rechProduit(this.produit);
		
		if(prRech != null){
			produit = prRech;
			return "rechProduit";
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Le produit recherché n'existe pas !"));
			return "rechProduit";
		}
		
	}
	
	

}
