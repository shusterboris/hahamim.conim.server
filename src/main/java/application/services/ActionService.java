package application.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.entities.Proposal;
import application.services.repositories.ActionsDAO;

@Service
public class ActionService {
	@Autowired
	private ActionsDAO repo;
	
	public List<Proposal> findActionsAll(){
		return repo.findAll();
	}
}
