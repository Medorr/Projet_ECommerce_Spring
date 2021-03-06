package fr.adaming.managedBean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletContext;
import javax.servlet.http.HttpSession;

import fr.adaming.Service.ICommandeService;
import fr.adaming.Service.ILigneCommandeService;
import fr.adaming.Service.IProduitService;
import fr.adaming.dao.ILigneCommandeDao;
import fr.adaming.model.Categorie;
import fr.adaming.model.Client;
import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;
import fr.adaming.model.Panier;
import fr.adaming.model.Produit;

@ManagedBean(name = "panMB")
@javax.faces.bean.RequestScoped
public class PanierManagedBean implements Serializable{
	private static final long serialVersionUID = 6088971269159960468L;
	/** attributs */
	private Panier panier;
	private LigneCommande article;
	private HttpSession maSession;
	private Double prixTotal = 0.0;
	private int nombreArticles;
	private Commande commande;
	private Boolean b = false;
	private int index;
	private String pluriel;
	
	@ManagedProperty(value="#{prService}")
	private IProduitService prService;
	public void setPrService(IProduitService prService) {
		this.prService = prService;
	}
	
	@ManagedProperty(value="#{lcService}")
	private ILigneCommandeService lcService;
	public void setLcService(ILigneCommandeService lcService) {
		this.lcService = lcService;
	}

	@ManagedProperty(value="#{comService}")
	private ICommandeService comService;
	public void setComService(ICommandeService comService) {
		this.comService = comService;
	}
	
	@PostConstruct
	public void init(){
		maSession=(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if(maSession.getAttribute("listeLcSession")!=null){
			this.panier.setListeLignesCommande((List<LigneCommande>) maSession.getAttribute("listeLcSession"));
			for(LigneCommande lc : this.panier.getListeLignesCommande()){
				prixTotal += lc.getPrix();
			}
			this.nombreArticles=this.panier.getListeLignesCommande().size();
			this.commande=new Commande();
			
		}
		// pluriel
		this.pluriel="";
		if(this.nombreArticles>1){
			this.pluriel="s";
		}
		
	}

	public PanierManagedBean() {
		super();
		this.panier = new Panier();
		this.article = new LigneCommande();
		this.article.setProduit(new Produit());
		this.article.getProduit().setCategorie(new Categorie());
	}

	public Panier getPanier() {
		return panier;
	}

	public void setPanier(Panier panier) {
		this.panier = panier;
	}	
	
	public LigneCommande getArticle() {
		return article;
	}

	public void setArticle(LigneCommande article) {
		this.article = article;
	}

	public HttpSession getMaSession() {
		return maSession;
	}

	public void setMaSession(HttpSession maSession) {
		this.maSession = maSession;
	}

	public Double getPrixTotal() {
		return prixTotal;
	}

	public void setPrixTotal(Double prixTotal) {
		this.prixTotal = prixTotal;
	}
	
	public int getNombreArticles() {
		return nombreArticles;
	}

	public void setNombreArticles(int nombreArticles) {
		this.nombreArticles = nombreArticles;
	}

	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}
	
	public Boolean getB() {
		return b;
	}

	public void setB(Boolean b) {
		this.b = b;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getPluriel() {
		return pluriel;
	}

	public void setPluriel(String pluriel) {
		this.pluriel = pluriel;
	}

	/** m�thodes : */
	public String ajoutArticle() {
		System.out.println("La cat�gorie de l'article ajout� est : "+this.article.getProduit().getCategorie().getNom());
		
		this.article.setPrix(this.article.getProduit().getPrix()*this.article.getQuantite());
		
		/** si le produit est d�j� pr�sent dans le panier, incr�menter la quantit� et le prix*/
		for (LigneCommande lc : this.panier.getListeLignesCommande()){
			if (lc.getProduit().getIdProduit()==article.getProduit().getIdProduit()){
				lc.setQuantite(lc.getQuantite()+this.article.getQuantite());
				lc.setPrix(lc.getPrix()+article.getPrix());
				b = true;
			}
		}
		/** si le produit n'est pas pr�sent dans le panier, rajouter une ligne de commande */
		if(b==false){
			this.panier.getListeLignesCommande().add(this.article);
		}
		
		/** ajouter le panier dans la session */
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listeLcSession", this.panier.getListeLignesCommande());
		/** calcul du prix total */
		prixTotal = 0.0;
		for(LigneCommande lc : this.panier.getListeLignesCommande()){
			prixTotal += lc.getPrix();
		}
		/** calcul du nombre d'articles */
		this.nombreArticles=this.panier.getListeLignesCommande().size();
		
		// v�rifier que la quantit� demand�e n'exc�de pas le stock du produit
		if (this.article.getQuantite()>this.article.getProduit().getQuantite()){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "La quantit� demand�e exc�de le stock ("+article.getProduit().getQuantite()+" produits en stocks) : nous vous livrerons apr�s un d�lai ind�termin�", "title"));
			FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("globalMessage");
		}
		
		//g�rer le pluriel
		this.pluriel="";
		if(this.nombreArticles>1){
			this.pluriel="s";
		}
		
		return "panier";
	}
	public String supprArticle() {
		
		/** trouver l'id de la LigneCommande � supprimer dans le panier et r�cup�rer son index*/
		for (LigneCommande lc : this.panier.getListeLignesCommande()){
			
			if (lc.getProduit().getIdProduit()==article.getProduit().getIdProduit()){
				index = this.panier.getListeLignesCommande().indexOf(lc);
			}
		}
		/** supprimer la ligne de commande dans le panier */
		this.panier.getListeLignesCommande().remove(index);
		
		/** ajouter le panier dans la session */
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listeLcSession", this.panier.getListeLignesCommande());
		/** calcul du prix total */
		prixTotal = 0.0;
		for(LigneCommande lc : this.panier.getListeLignesCommande()){
			prixTotal += lc.getPrix();
		}
		/** calcul du nombre d'articles */
		this.nombreArticles=this.panier.getListeLignesCommande().size();
		
		//g�rer le pluriel
		this.pluriel="";
				if(this.nombreArticles>1){
					this.pluriel="s";
				}
		
		return "panier";
	}
	
	public String passerCommande(){
		Date date = new Date();
		this.commande = new Commande(date);
		this.commande.setClient((Client) maSession.getAttribute("clSession"));
		comService.ajouterCommande(this.commande);

		
		for (LigneCommande lc : this.panier.getListeLignesCommande()){
			lc.setCommande(commande);
			lcService.ajoutLigneCommande(lc);
		}
		
		/** modification du stock du produit ajout� */
		article.getProduit().setQuantite(article.getProduit().getQuantite()-article.getQuantite());
		prService.modifProduit(article.getProduit());
		
		comService.sendMail(commande);
		return "confirmationCommande";
	}
	
	
    public String inscription(){
    	
    	if(panier.getListeLignesCommande().size()!=0){
    		return "inscription";
    	}else{
    		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Votre panier est vide", "title"));
    		FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("globalMessage");
    	return "panier";
    }
    }

}
