package fr.adaming.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Admin;

@Repository
public class AdminDaoImpl implements IAdminDao {

	/**
	 * declaration de l'attribut sessionFactory
	 */
	@Autowired
	private SessionFactory sf;

	/**
	 * Methode isExist(l'admin existe)
	 */
	@Override
	public Admin isExist(Admin admin) {
		try {
			/**
			 * Recuperer la session
			 */
			Session s = sf.getCurrentSession();

			/**
			 * Req HQL
			 */
			String req = "FROM Admin as adm WHERE adm.login=:pLogin AND adm.password=:pPassword";

			/**
			 * Query
			 */
			Query query = s.createQuery(req);

			/**
			 * Passage des parametres
			 */
			query.setParameter("pLogin", admin.getLogin());
			query.setParameter("pPassword", admin.getPassword());

			return (Admin) query.uniqueResult();

		} catch (Exception exep) {
			exep.printStackTrace();
			return null;
		}

	}

}
