package fr.adaming.managedBean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import fr.adaming.Service.IAdminService;
import fr.adaming.model.Admin;

@ManagedBean(name="adMB")
@RequestScoped
public class AdminManagedBean {
	
	/**
	 * declaration des attributs
	 */
	private Admin admin;
	/**
	 * Transformation de l'association UML en JAVA et injection dependance
	 */
	@ManagedProperty("adService")
	private IAdminService adService;
	
	/**
	 * @param admService
	 */
	public void setAdmService(IAdminService adService) {
		this.adService = adService;
	}
	
	/**
	 * Getters et setters
	 */
	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	/**
	 * Constructeur vide + instancier un admin
	 */
	public AdminManagedBean() {
		super();
		this.admin = new Admin();
	}
	
	/**
	 * Methodes
	 */
	/** Connection de l'administrateur */
	public String seConnecter() {
		Admin connectAd = adService.isExist(admin);
		
		if (connectAd != null) {

			/** Ajouter l'admin connecté dans la session */
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("adSession", connectAd);

			return "accueilAd";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Attention!!!Login ou Password erroné"));
			return "login";
		}

	}

	/** Deconnexion de l'admnistrateur */
	public String logout() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext ec = context.getExternalContext();

		final HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		request.getSession(false).invalidate();

		return "login";
	}

}
