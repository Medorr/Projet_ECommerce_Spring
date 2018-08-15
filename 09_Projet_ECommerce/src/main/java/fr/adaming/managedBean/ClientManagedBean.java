package fr.adaming.managedBean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpSession;

import fr.adaming.Service.IClientService;
import fr.adaming.model.Client;

@ManagedBean(name = "clMB")
@RequestScoped
public class ClientManagedBean implements Serializable {

	/**
	 * declaration des attributs
	 */
	private Client cl;
	private boolean indice;
	private List<Client> clListe;
	private HttpSession maSession;

	/**
	 * Transformation de l'association UML en JAVA et injection dependance
	 */
	@ManagedProperty(value = "#{clService}")
	private IClientService cliService;

	/**
	 * Getter et Setter de cliService
	 */
	
	public void setCliService(IClientService cliService) {
		this.cliService = cliService;
	}

	public IClientService getCliService() {
		return cliService;
	}

	/**
	 * Constructeur vide + instanciation d'un client
	 */

	public ClientManagedBean() {
		super();
		this.cl = new Client();
	}

	@PostConstruct
	public void init() {
		maSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		this.indice = false;
		this.clListe = cliService.getAllClient();
	}

	/**
	 * autres getters et setters
	 */
	public Client getCl() {
		return cl;
	}

	public void setCl(Client cl) {
		this.cl = cl;
	}

	public boolean isIndice() {
		return indice;
	}

	public void setIndice(boolean indice) {
		this.indice = indice;
	}

	public List<Client> getClListe() {
		return clListe;
	}

	public void setClListe(List<Client> clListe) {
		this.clListe = clListe;
	}

	/**
	 * Methodes du client
	 */
	
	public String ajoutClient() {
		Client clEnr = cliService.enregistrerClient(cl);

		if (clEnr.getIdClient() != 0) {
			/** Recuperer la liste */
			List<Client> listeCl = cliService.getAllClient();

			return "paiement";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("l'ajout a échoué"));

			return "ajoutCl";
		}
	}
	
	public String modifClient() {
		int clModif = cliService.modifClient(cl);

		if (clModif != 0) {
			/** Recuperer la liste */
			List<Client> listeCl = cliService.getAllClient();

			return "accueilAd";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La modification du client a echoué"));

			return "modifCl";
		}
	}
	
	public String supprClient() {
		Client clSup = cliService.supprClient(cl);

		if (clSup.getIdClient() != 0) {
			/** Recuperer la liste */
			List<Client> listeCl = cliService.getAllClient();

			return "accueilAd";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La suppression du client a echoué"));

			return "supprCl";
		}

	}
	
	public String rechIdNom(){
		List<Client> listRech = cliService.getClientByNomOrId(cl);
		
		if(listRech != null){
			this.clListe = listRech;
			this.indice = true;
			
			
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Le ou les clients recherchés sont introuvables"));
			
		}
		return "rechCl";
		
	}
	public void validerTel(FacesContext context, UIComponent composant, Object value) throws ValidatorException {

		String valeur = (String) value;

		if (valeur.length() == 10 ) {

		}else{
			throw new ValidatorException(new FacesMessage("Le numero de telephone doit contenir 10 caractères"));
		}
	}
	
}
