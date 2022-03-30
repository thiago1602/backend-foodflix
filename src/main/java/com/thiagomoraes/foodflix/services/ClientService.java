package com.thiagomoraes.foodflix.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.thiagomoraes.foodflix.domain.Client;
import com.thiagomoraes.foodflix.dto.ClientDTO;
import com.thiagomoraes.foodflix.repositories.ClientRepository;
import com.thiagomoraes.foodflix.services.exception.DataIntegryException;
import com.thiagomoraes.foodflix.services.exception.ObjectNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repo;
	public Client find(Integer id) {
		Optional<Client> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Client.class.getName()));
	}
	
	public Client update(Client obj) {
		Client newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(obj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		
		catch(DataIntegrityViolationException e) {
			throw new DataIntegryException("Não é possivel excluir porque há entidades relacionadas");
		}
	}
	
	public List<Client> findAll(){
		return repo.findAll();
	}
	
	public Page<Client> findPage(Integer page, Integer linesPerPage, String orderBy, 
			String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Client fromDTO(ClientDTO objDto) {
		return new Client (objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
	
	public void updateData(Client newObj, Client obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		
	}
}
