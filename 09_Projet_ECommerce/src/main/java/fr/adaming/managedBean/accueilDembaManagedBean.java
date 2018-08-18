package fr.adaming.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;

import fr.adaming.Service.ICategorieServiceSteven;
import fr.adaming.Service.IProduitService;
import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;


@ManagedBean(name="accMB")
@RequestScoped
public class accueilDembaManagedBean implements Serializable{
	
	//les attributs
	private List<Categorie> listeCategorie;
	private Categorie categorie;
	private UploadedFile file;
	private String selection;
	   private List<Produit> listeP;
	   private String orientation="horizontal";
	
	@ManagedProperty(value="#{catServiceSteven}")
	private ICategorieServiceSteven catService;
	//Un getter et setters pour l'asso
	public ICategorieServiceSteven getCatService() {
		return catService;
	}
	public void setCatService(ICategorieServiceSteven catService) {
		this.catService = catService;
	}
	
	//La methode init
	@PostConstruct
	public void init(){
		listeCategorie=catService.getAllCategorie();
	}
		

	      
	
	//Constructeur vide	
	public accueilDembaManagedBean() {
		super();
		this.categorie=new Categorie();
	}
	//Getters et setters
	public List<Categorie> getListeCategorie() {
		return listeCategorie;
	}
	public void setListeCategorie(List<Categorie> listeCategorie) {
		this.listeCategorie = listeCategorie;
	}
	
	
	public Categorie getCategorie() {
		return categorie;
	}
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
	public UploadedFile getFile() {
		return file;
	}
	public void setFile(UploadedFile file) {
		this.file = file;
	}
		
	public String getSelection() {
		return selection;
	}
	public void setSelection(String selection) {
		this.selection = selection;
	}
	
	public String getOrientation() {
		return orientation;
	}
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	/** méthodes : */
	public String ajoutCategorie() {
		this.categorie.setPhoto(file.getContents());
		int catAjout = catService.ajoutCategorie(this.categorie);

		if (catAjout != 0) {
			listeCategorie = catService.getAllCategorie();
			return "listeCategorieSteven";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("L'ajout de la Categorie a échoué !"));
			return "ajoutCategorieSteven";
		}

	}
	
	public String modifCategorie() {
		
		this.categorie.setPhoto(file.getContents());
		int catModif = catService.modifCategorie(this.categorie);

		if (catModif != 0) {
			listeCategorie = catService.getAllCategorie();
			return "listeCategorieSteven";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La modification de la Categorie a échoué !"));
			return "modifCategorieSteven";
		}

	}
	
	public String supprCategorie(){
		int catSuppr = catService.supprCategorie(this.categorie);
		
		if(catSuppr != 0){
			listeCategorie = catService.getAllCategorie();
			return "listeCategorieSteven";
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La suppression de la Categorie a échoué !"));
			return "supprCategorieSteven";
		}
		
	}
	
	
	
	public String rechCategorie(){
		Categorie catRech = catService.rechCategorieById(this.categorie);
		
		if(catRech != null){
			categorie = catRech;
			return "rechCategorieSteven";
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La catégorie recherchée n'existe pas !"));
			return "rechCategorieSteven";
		}		
	}	
}
