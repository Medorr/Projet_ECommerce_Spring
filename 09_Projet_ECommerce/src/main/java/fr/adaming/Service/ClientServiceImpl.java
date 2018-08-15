package fr.adaming.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IClientDao;
import fr.adaming.model.Client;
@Service("clService")
@Transactional
public class ClientServiceImpl implements IClientService{
	
	//Transformation de l'association uml en java
	@Autowired
	private IClientDao clDao;

	@Override
	public Client enregistrerClient(Client cl) {
		return clDao.enregistrerClient(cl);
	}

	@Override
	public int modifClient(Client cl) {
		return clDao.modifClient(cl);
	}

	@Override
	public Client supprClient(Client cl) {
		return clDao.supprClient(cl);
	}

	@Override
	public List<Client> getAllClient() {
		return clDao.getAllClient();
	}

	@Override
	public Client getClientById(Client cl) {
		return clDao.getClientById(cl);
	}

	@Override
	public List<Client> getClientByNomOrId(Client cl) {
		return clDao.getClientByNomOrId(cl);
	}

}
