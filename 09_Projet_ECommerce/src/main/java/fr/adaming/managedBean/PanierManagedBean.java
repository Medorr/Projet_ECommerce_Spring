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

	

	/** méthodes : */
	public String ajoutArticle() {
		System.out.println("La catégorie de l'article ajouté est : "+this.article.getProduit().getCategorie().getNom());
		
		this.article.setPrix(this.article.getProduit().getPrix()*this.article.getQuantite());
		
		/** si le produit est déjà présent dans le panier, incrémenter la quantité et le prix*/
		for (LigneCommande lc : this.panier.getListeLignesCommande()){
			if (lc.getProduit().getIdProduit()==article.getProduit().getIdProduit()){
				lc.setQuantite(lc.getQuantite()+this.article.getQuantite());
				lc.setPrix(lc.getPrix()+article.getPrix());
				b = true;
			}
		}
		/** si le produit n'est pas présent dans le panier, rajouter une ligne de commande */
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
		
		return "panier";
	}
	public String supprArticle() {
		
		/** trouver l'id de la LigneCommande à supprimer dans le panier et récupérer son index*/
		for (LigneCommande lc : this.panier.getListeLignesCommande()){
			
			if (lc.getProduit()
					.getIdProduit()==
					article.getProduit()
					.getIdProduit()){
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
		
		/** modification du stock du produit ajouté */
		article.getProduit().setQuantite(article.getProduit().getQuantite()-article.getQuantite());
		prService.modifProduit(article.getProduit());
		
		comService.sendMail(commande);
		return "confirmationCommande";
	}
	
	public void destroyWorld() {
        addMessage("System Error", "Please try again later.");
    }
     
    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
	

}
