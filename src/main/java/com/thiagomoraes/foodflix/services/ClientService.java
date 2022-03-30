package com.thiagomoraes.foodflix.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.thiagomoraes.foodflix.domain.Cidade;
import com.thiagomoraes.foodflix.domain.Client;
import com.thiagomoraes.foodflix.domain.Endereco;
import com.thiagomoraes.foodflix.domain.enums.TipoClient;
import com.thiagomoraes.foodflix.dto.ClientDTO;
import com.thiagomoraes.foodflix.dto.ClientNewDTO;
import com.thiagomoraes.foodflix.repositories.ClientRepository;
import com.thiagomoraes.foodflix.repositories.EnderecoRepository;
import com.thiagomoraes.foodflix.services.exception.DataIntegryException;
import com.thiagomoraes.foodflix.services.exception.ObjectNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Client find(Integer id) {
		Optional<Client> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Client.class.getName()));
	}
	public Client Insert(Client obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
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
			throw new DataIntegryException("Não é possivel excluir porque há pedidos relacionadas");
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
	
	
		public Client fromDTO(ClientNewDTO objDto) {
			Client cli = new Client(null, objDto.getNome(), objDto.getEmail(),					
					objDto.getCpfOuCnpj(),
					TipoClient.toEnum(objDto.getTipo()));
			Cidade cid = new Cidade();
			Endereco end = new Endereco(null, objDto.getLogradouro(), 
					objDto.getNumero(),
					objDto.getComplemento(),
					objDto.getBairro(), objDto.getCep(),
					cli, cid);
			cli.getEnderecos().add(end);
			cli.getTelefones().add(objDto.getTelefone1());
			if(objDto.getTelefone2()!=null) {
				cli.getTelefones().add(objDto.getTelefone2());
			}
			if(objDto.getTelefone3()!=null) {
				cli.getTelefones().add(objDto.getTelefone3());
			}
			
			return cli;
		}
	
}
