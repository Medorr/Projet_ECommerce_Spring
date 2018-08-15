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

import fr.adaming.Service.ICategorieService;
import fr.adaming.model.Categorie;
@ManagedBean(name="catMB")
@RequestScoped
public class CategorieManagedBean implements Serializable{
	
	//Les attributs
	private Categorie categorie;
	private boolean indice;
	private List<Categorie> listeCat;	
	private UploadedFile file;
	
	//**************************************************************
	// Transformation de l'asso UML en JAVA
	//***************************************************************
	@ManagedProperty(value="#{catService}")
	private ICategorieService catService;
	//Setter pour l'injection de dependance
	public void setCatService(ICategorieService catService) {
		this.catService = catService;
	}
	public ICategorieService getCatService() {
		return catService;
	}
	//*******************************************************************
	//Constructeur vide pour le MB
	//*******************************************************************
	public CategorieManagedBean() {
		super();
		this.categorie=new Categorie();
	}
	
	// Methode init qui sera instancier juste apres le MB
	@PostConstruct
	public void init(){
		this.listeCat=catService.getAllCategorieService();
		this.indice=false;
	}
	
	//Les getters et setters 
	public Categorie getCategorie() {
		return categorie;
	}
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
	public boolean isIndice() {
		return indice;
	}
	public void setIndice(boolean indice) {
		this.indice = indice;
	}
	public List<Categorie> getListeCat() {
		return listeCat;
	}
	public void setListeCat(List<Categorie> listeCat) {
		this.listeCat = listeCat;
	}
	
	public UploadedFile getFile() {
		return file;
	}
	public void setFile(UploadedFile file) {
		this.file = file;
	}
	// Les methodes metiers
	public String ajoutCategorie(){
		if(file!=null){
		this.categorie
		.setPhoto(
				file.
				getContents());
		}
		
		Categorie catBD=catService.ajouterCategorieService(this.categorie);
		
		if(catBD.getId()!=0){
			
			//recuperer la liste des categorie
			listeCat=catService.getAllCategorieService();
			
			return "accueilCat";
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Désolé l'ajout à échouer"));
			return "ajoutCat";
		}
	}
	
	public String modifCategorie(){
		//recup une cat dans la BD
		int catBD=catService.modifCategorieService(this.categorie);
		
		if(catBD!=0){
			
			///Recup de la liste
			listeCat=catService.getAllCategorieService();
			return "accueilCat";
		}else{
		
		
		return "modifCat";
	}	
	}
	
	public String supprCategorie(){
		Categorie CatBD=catService.supprCategorieService(this.categorie);
		
		if(CatBD.getId()!=0){
			//recuperer la liste et me l'afficher 
			listeCat=catService.getAllCategorieService();
			return "accueilCat";
			
		}else{
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La suppression à échouer"));
		return "supprCat";
	}
	}
	public String getCategorieById(){
		
		Categorie catBD=catService.getCategorieByIdService(this.categorie);
		
		if(catBD.getId()!=0){
			this.categorie=catBD;
		}else{		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("la recherche à échouer"));
	}		return "rechCat";
	}
}
