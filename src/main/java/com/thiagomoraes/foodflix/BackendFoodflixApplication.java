package com.thiagomoraes.foodflix;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.thiagomoraes.foodflix.domain.Categoria;
import com.thiagomoraes.foodflix.domain.Cidade;
import com.thiagomoraes.foodflix.domain.Client;
import com.thiagomoraes.foodflix.domain.Endereco;
import com.thiagomoraes.foodflix.domain.Estado;
import com.thiagomoraes.foodflix.domain.Produto;
import com.thiagomoraes.foodflix.domain.enums.TipoClient;
import com.thiagomoraes.foodflix.repositories.CategoriaRepository;
import com.thiagomoraes.foodflix.repositories.CidadeRepository;
import com.thiagomoraes.foodflix.repositories.ClientRepository;
import com.thiagomoraes.foodflix.repositories.EnderecoRepository;
import com.thiagomoraes.foodflix.repositories.EstadoRepository;
import com.thiagomoraes.foodflix.repositories.ProdutoRepository;

@SpringBootApplication
public class BackendFoodflixApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	
	public static void main(String[] args) {
		SpringApplication.run(BackendFoodflixApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null,"Informática");
		Categoria cat2 = new Categoria(null,"Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList( p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Marilia", est1);
		Cidade c2 = new Cidade(null, "Varginia", est2);
		Cidade c3 = new Cidade(null, "BH", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Client cli1 = new Client(null, "Thiago", "thiago@gmail.com", "998897799", TipoClient.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("998897799", "998897788"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "250", "Apto 203", "Jardim", "38854", cli1, c1);
		Endereco e2 = new Endereco(null, "Rua Flor", "250", "Apto 203", "Teo", "38854", cli1, c1);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clientRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
	}

}

