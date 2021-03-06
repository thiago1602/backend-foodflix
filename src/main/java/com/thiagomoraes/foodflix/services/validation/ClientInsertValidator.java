package com.thiagomoraes.foodflix.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.thiagomoraes.foodflix.domain.Client;
import com.thiagomoraes.foodflix.domain.enums.TipoClient;
import com.thiagomoraes.foodflix.dto.ClientNewDTO;
import com.thiagomoraes.foodflix.repositories.ClientRepository;
import com.thiagomoraes.foodflix.resources.exception.FieldMessage;
import com.thiagomoraes.foodflix.services.validation.utils.BR;

public class ClientInsertValidator implements ConstraintValidator<ClientInsert, ClientNewDTO> {
	
	@Autowired
	private ClientRepository repo;
	@Override
	public void initialize(ClientInsert ann) {
	}

	@Override
	public boolean isValid(ClientNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
	
		if(objDto.getTipo().equals(TipoClient.PESSOAFISICA.getCod()) && 
				!BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("CpfOuCnpj", "CPF Inválido"));
		}
		if(objDto.getTipo().equals(TipoClient.PESSOAJURIDICA.getCod()) && 
				!BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("CpfOuCnpj", "CNPJ Inválido"));
		}
		
		

		// inclua os testes aqui, inserindo erros na lista

		Client aux = repo.findByEmail(objDto.getEmail());
		if(aux != null) {
			list.add(new FieldMessage("email", "Email já existe"));
		}
		
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}