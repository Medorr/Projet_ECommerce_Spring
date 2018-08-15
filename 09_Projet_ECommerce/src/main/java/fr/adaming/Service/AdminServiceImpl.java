package fr.adaming.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IAdminDao;
import fr.adaming.model.Admin;

@Service("adService")
@Transactional
public class AdminServiceImpl implements IAdminService {

	/**
	 * Transformation de l'association UML en JAVA et injection dependance
	 */
	@Autowired
	private IAdminDao adDao;

	@Override
	public Admin isExist(Admin admin) {
		return adDao.isExist(admin);
	}
}
