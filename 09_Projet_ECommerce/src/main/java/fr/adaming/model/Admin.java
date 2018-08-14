package fr.adaming.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="administrateurs")
public class Admin implements Serializable{
	
	/**
	 * Declaration des attributs de Admin
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_admin")
	private Long id;
	private String login;
	private String password;
	
	//Declaration des constructeurs
	/**
	 * Constructeur sans params
	 */
	public Admin() {
		super();
	}
	/**
	 * @param login
	 * @param password
	 */
	public Admin(String login, String password) {
		super();
		this.login = login;
		this.password = password;
	}
	/**
	 * @param id
	 * @param login
	 * @param password
	 */
	public Admin(Long id, String login, String password) {
		super();
		this.id = id;
		this.login = login;
		this.password = password;
	}
	
	//Getters et setters
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}
	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/* Methode ToString
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Admin [id=" + id + ", login=" + login + ", password=" + password + "]";
	}

	
	
	
}
