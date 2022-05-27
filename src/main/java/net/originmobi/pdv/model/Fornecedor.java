package net.originmobi.pdv.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CNPJ;

@Entity
public class Fornecedor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@NotNull(message = "Nome Fantasia é obrigatório")
	@Size(max = 250, message = "Tamanho máximo de 250 caracteres")
	private String nome_fantasia;

	@NotNull(message = "Nome é obrigatório")
	@Size(max = 205, message = "Tamanho máximo de 250 caracteres")
	private String nome;

	@NotNull(message = "CNPJ é obrigatório")
	@CNPJ(message = "CNPJ inválido")
	@Column(unique = true)
	@Size(max = 18)
	private String cnpj;

	@NotNull(message = "Inscrição Estadual é obrigatório")
	private String inscricao_estadual;

	@NotNull
	private int ativo;

	@Size(max = 150, message = "Tamanho máximo permitido de 150 caracteres")
	private String observacao;
	private Date data_cadastro;

	@OneToMany
	private List<Telefone> telefone;

	@ManyToOne
	private Endereco endereco;

	public Fornecedor() {
	}

	public Fornecedor(Long codigo, String nome_fantasia, String nome, String cnpj, String escricao_estadual, int ativo,
			Date data_cadastro, Endereco endereco, List<Telefone> telefone) {
		super();
		this.codigo = codigo;
		this.nome_fantasia = nome_fantasia;
		this.nome = nome;
		this.cnpj = cnpj;
		this.inscricao_estadual = escricao_estadual;
		this.ativo = ativo;
		this.data_cadastro = data_cadastro;
		this.endereco = endereco;
		this.telefone = telefone;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome_fantasia() {
		return nome_fantasia;
	}

	public void setNome_fantasia(String nome_fantasia) {
		this.nome_fantasia = nome_fantasia;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getInscricao_estadual() {
		return inscricao_estadual;
	}

	public void setInscricao_estadual(String inscricao_estadual) {
		this.inscricao_estadual = inscricao_estadual;
	}

	public int getAtivo() {
		return ativo;
	}

	public void setAtivo(int ativo) {
		this.ativo = ativo;
	}

	public Date getData_cadastro() {
		return data_cadastro;
	}

	public void setData_cadastro(Date data_cadastro) {
		this.data_cadastro = data_cadastro;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Telefone> getTelefone() {
		return telefone;
	}

	public void setTelefone(List<Telefone> telefone) {
		this.telefone = telefone;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public static final class FornecedorBuilder {
		private Long codigo;
		private String nome_fantasia;
		private String nome;
		private String cnpj;
		private String inscricao_estadual;
		private int ativo;
		private String observacao;
		private Date data_cadastro;
		private List<Telefone> telefone;
		private Endereco endereco;

		private FornecedorBuilder() {
		}

		public static FornecedorBuilder aFornecedor() {
			return new FornecedorBuilder();
		}

		public FornecedorBuilder withCodigo(Long codigo) {
			this.codigo = codigo;
			return this;
		}

		public FornecedorBuilder withNome_fantasia(String nome_fantasia) {
			this.nome_fantasia = nome_fantasia;
			return this;
		}

		public FornecedorBuilder withNome(String nome) {
			this.nome = nome;
			return this;
		}

		public FornecedorBuilder withCnpj(String cnpj) {
			this.cnpj = cnpj;
			return this;
		}

		public FornecedorBuilder withInscricao_estadual(String inscricao_estadual) {
			this.inscricao_estadual = inscricao_estadual;
			return this;
		}

		public FornecedorBuilder withAtivo(int ativo) {
			this.ativo = ativo;
			return this;
		}

		public FornecedorBuilder withObservacao(String observacao) {
			this.observacao = observacao;
			return this;
		}

		public FornecedorBuilder withData_cadastro(Date data_cadastro) {
			this.data_cadastro = data_cadastro;
			return this;
		}

		public FornecedorBuilder withTelefone(List<Telefone> telefone) {
			this.telefone = telefone;
			return this;
		}

		public FornecedorBuilder withEndereco(Endereco endereco) {
			this.endereco = endereco;
			return this;
		}

		public Fornecedor build() {
			Fornecedor fornecedor = new Fornecedor();
			fornecedor.setCodigo(codigo);
			fornecedor.setNome_fantasia(nome_fantasia);
			fornecedor.setNome(nome);
			fornecedor.setCnpj(cnpj);
			fornecedor.setInscricao_estadual(inscricao_estadual);
			fornecedor.setAtivo(ativo);
			fornecedor.setObservacao(observacao);
			fornecedor.setData_cadastro(data_cadastro);
			fornecedor.setTelefone(telefone);
			fornecedor.setEndereco(endereco);
			return fornecedor;
		}
	}
}
