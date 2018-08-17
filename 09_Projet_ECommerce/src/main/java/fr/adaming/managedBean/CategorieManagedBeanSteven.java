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
import fr.adaming.Service.IProduitService;
import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;

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
	@ManagedProperty(value="#{prService}")
	private IProduitService prodService;

	public IProduitService getProdService() {
		return prodService;
	}
	public void setProdService(IProduitService prodService) {
		this.prodService = prodService;
	}
	private List<Categorie> listeCategorie;
	private Categorie categorie;
	private UploadedFile file; 
	 private PieChartModel pieModel1;
	 private List<Produit> listeP;

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
	/**
	 * @return the listeP
	 */
	public List<Produit> getListeP() {
		return listeP;
	}
	/**
	 * @param listeP the listeP to set
	 */
	public void setListeP(List<Produit> listeP) {
		this.listeP = listeP;
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
	
	
	private void createPieModels() {
        createPieModel1();
	}
	
        private void createPieModel1() {
            pieModel1 = new PieChartModel();
             
            for( Categorie cat : listeCategorie ){
            	listeP = prodService.getProduitByIdCat(cat);
            	pieModel1.set(cat.getNom(), listeP.size());
            }
             
          //  pieModel1.setTitle(" Repartition du stock");
            pieModel1.setLegendPosition("s");
            pieModel1.setShadow(false);        
            pieModel1.setShowDataLabels(true);

          
        }
	
}
