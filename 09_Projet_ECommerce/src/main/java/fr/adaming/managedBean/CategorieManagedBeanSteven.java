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

import fr.adaming.Service.ICategorieServiceSteven;
import fr.adaming.model.Categorie;

@ManagedBean(name="catMBSteven")
@RequestScoped
public class CategorieManagedBeanSteven implements Serializable{
	
	@ManagedProperty(value="#{catServiceSteven}")
	private ICategorieServiceSteven catService;
	public ICategorieServiceSteven getCatService() {
		return catService;
	}
	public void setCatService(ICategorieServiceSteven catService) {
		this.catService = catService;
	}
	
	private List<Categorie> listeCategorie;
	private Categorie categorie;
	private UploadedFile file; 

	@PostConstruct
	public void init(){
		listeCategorie = catService.getAllCategorie();
		
	}
	public CategorieManagedBeanSteven() {
		super();
		this.categorie = new Categorie();
	}
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
		Categorie catRech = catService.rechCategorie(this.categorie);
		
		if(catRech != null){
			categorie = catRech;
			return "rechCategorieSteven";
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La catégorie recherchée n'existe pas !"));
			return "rechCategorieSteven";
		}
		
	}
	
	
	
	
	
	
}
