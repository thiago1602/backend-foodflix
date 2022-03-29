package com.thiagomoraes.foodflix.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiagomoraes.foodflix.domain.Pedido;
import com.thiagomoraes.foodflix.repositories.PedidoRepository;
import com.thiagomoraes.foodflix.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	public Pedido buscar(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
}
