package fr.adaming.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;

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
	private List<Produit> listeProduitPromo;
	private HorizontalBarChartModel horizontalBarModel;

	private UploadedFile file;
	
	@PostConstruct
	public void init(){
		listeProduit = prService.getAllProduit();
		createBarModels();
		
		// récupérer les 2 produits les + en stock :
		listeProduitPromo = prService.getAllProduit();
		Collections.sort(listeProduitPromo, new Comparator<Produit>() {
			@Override
			public int compare(Produit produit2, Produit produit1) {
				// TODO Auto-generated method stub
				return  Integer.compare(produit1.getQuantite(), produit2.getQuantite());
			}});
		List<Produit> listeProduitPromo2 = new ArrayList<Produit>();
		for (int i=0;i<2;i++){
			listeProduitPromo2.add(listeProduitPromo.get(i));
		}
		// les mettre à -20% :
		for(Produit pr : listeProduitPromo2){
			pr.setPrix(pr.getPrix()*4/5);
		}
		listeProduitPromo = listeProduitPromo2;
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
	public List<Produit> getListeProduitPromo() {
		return listeProduitPromo;
	}
	public void setListeProduitPromo(List<Produit> listeProduitPromo) {
		this.listeProduitPromo = listeProduitPromo;
	}
	public HorizontalBarChartModel getHorizontalBarModel() {
		return horizontalBarModel;
	}
	public void setHorizontalBarModel(HorizontalBarChartModel horizontalBarModel) {
		this.horizontalBarModel = horizontalBarModel;
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
	
	private void createBarModels() {
        createHorizontalBarModel();
    }
	
	 private void createHorizontalBarModel() {
	        horizontalBarModel = new HorizontalBarChartModel();
	 
	        ChartSeries quantité = new ChartSeries();
	        
	   for(Produit p: listeProduit){
		   listeProduit = prService.getAllProduit();
		   quantité.setLabel("Quantité");
	        quantité.set(p.getDesignation(), p.getQuantite());
	   }
	 
	        horizontalBarModel.addSeries(quantité);
	
	 
	        horizontalBarModel.setLegendPosition("e");
	        horizontalBarModel.setStacked(true);
	         
	        Axis xAxis = horizontalBarModel.getAxis(AxisType.X);
	        xAxis.setLabel("Quantité");
	         
	        Axis yAxis = horizontalBarModel.getAxis(AxisType.Y);
	        yAxis.setLabel("Produits");
	        yAxis.setTickFormat("%.10f");
	        
	    }
	

}
