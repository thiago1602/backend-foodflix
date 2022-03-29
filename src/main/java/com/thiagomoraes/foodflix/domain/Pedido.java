package com.thiagomoraes.foodflix.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Pedido implements Serializable{

	private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private Integer id;
		
		@JsonFormat(pattern="dd/MM/yyyy HH:mm")
		private Date instante;
		
		@JsonIgnore
		@OneToOne(cascade=CascadeType.ALL, mappedBy="pedido")
		private Pagamento pagamento;
		
		@JsonIgnore
		@ManyToOne
		@JoinColumn(name="client_id")
		private Client client;
		
		@ManyToOne
		@JoinColumn(name="endereco_de_entrega_id")
		private Endereco enderecoEntrega;
		
		@OneToMany(mappedBy="id.pedido")
		private Set<ItemPedido> itens = new HashSet<>();
		public Pedido() {
			
		}

		public Pedido(Integer id, Date instante, Client client, Endereco enderecoEntrega) {
			super();
			this.id = id;
			this.instante = instante;
			this.client = client;
			this.enderecoEntrega = enderecoEntrega;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Date getInstante() {
			return instante;
		}

		public void setInstante(Date instante) {
			this.instante = instante;
		}

		public Pagamento getPagamento() {
			return pagamento;
		}

		public void setPagamento(Pagamento pagamento) {
			this.pagamento = pagamento;
		}

		public Client getClient() {
			return client;
		}

		public void setClient(Client client) {
			this.client = client;
		}

		public Endereco getEnderecoEntrega() {
			return enderecoEntrega;
		}

		public void setEnderecoEntrega(Endereco enderecoEntrega) {
			this.enderecoEntrega = enderecoEntrega;
		}
		
		public Set<ItemPedido> getItens() {
			return itens;
		}

		public void setItens(Set<ItemPedido> itens) {
			this.itens = itens;
		}

		@Override
		public int hashCode() {
			return Objects.hash(id);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Pedido other = (Pedido) obj;
			return Objects.equals(id, other.id);
		}

		
		
}
