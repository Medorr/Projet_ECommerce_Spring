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
import org.primefaces.model.chart.PieChartModel;

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
	 private PieChartModel pieModel1;

	@PostConstruct
	public void init(){
		listeCategorie = catService.getAllCategorie();
		createPieModels();
		
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
	
	/**
	 * @return the pieModel1
	 */
	public PieChartModel getPieModel1() {
		return pieModel1;
	}
	/**
	 * @param pieModel1 the pieModel1 to set
	 */
	public void setPieModel1(PieChartModel pieModel1) {
		this.pieModel1 = pieModel1;
	}
	/** m�thodes : */
	public String ajoutCategorie() {
		this.categorie.setPhoto(file.getContents());
		int catAjout = catService.ajoutCategorie(this.categorie);

		if (catAjout != 0) {
			listeCategorie = catService.getAllCategorie();
			return "listeCategorieSteven";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("L'ajout de la Categorie a �chou� !"));
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
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La modification de la Categorie a �chou� !"));
			return "modifCategorieSteven";
		}

	}
	
	public String supprCategorie(){
		int catSuppr = catService.supprCategorie(this.categorie);
		
		if(catSuppr != 0){
			listeCategorie = catService.getAllCategorie();
			return "listeCategorieSteven";
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La suppression de la Categorie a �chou� !"));
			return "supprCategorieSteven";
		}
		
	}
	
	
	
	public String rechCategorie(){
		Categorie catRech = catService.rechCategorie(this.categorie);
		
		if(catRech != null){
			categorie = catRech;
			return "rechCategorieSteven";
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La cat�gorie recherch�e n'existe pas !"));
			return "rechCategorieSteven";
		}
		
	}
	
	
	private void createPieModels() {
        createPieModel1();
	}
	
        private void createPieModel1() {
            pieModel1 = new PieChartModel();
             
            pieModel1.set("Brand 1", 540);
            pieModel1.set("Brand 2", 325);
            pieModel1.set("Brand 3", 702);
            pieModel1.set("Brand 4", 421);
             
            pieModel1.setTitle("Simple Pie");
            pieModel1.setLegendPosition("w");
            pieModel1.setShadow(false);
        }
	
}
