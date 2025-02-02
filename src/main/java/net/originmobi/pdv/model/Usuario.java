package net.originmobi.pdv.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@NotBlank(message = "Usuário não pode ser vazio")
	@Size(min = 3, max = 20, message = "Tamanha minimo de 4 caracteres e máximo de 20 para usuario")
	private String user;

	@NotBlank(message = "Senha não pode ser vazia")
	private String senha;
	private Date data_cadastro;

	@OneToOne
	private Pessoa pessoa;

	@ManyToMany
	@JoinTable(joinColumns = @JoinColumn(name = "usuario_codigo"), inverseJoinColumns = @JoinColumn(name = "grupo_usuario_codigo"))
	private List<GrupoUsuario> grupousuario;

	@ManyToMany
	private List<Permissoes> permissoes;

	public Usuario() {
	}

	public Usuario(String senha) {
		this.senha = senha;
	}

	public Usuario(Long codigo, String user, String senha, Date data_cadastro, Pessoa pessoa,
				   List<GrupoUsuario> grupousuario, List<Permissoes> permissoes) {
		this.codigo = codigo;
		this.user = user;
		this.senha = senha;
		this.data_cadastro = data_cadastro;
		this.pessoa = pessoa;
		this.grupousuario = grupousuario;
		this.permissoes = permissoes;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Date getData_cadastro() {
		return data_cadastro;
	}

	public void setData_cadastro(Date data_cadastro) {
		this.data_cadastro = data_cadastro;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<GrupoUsuario> getGrupoUsuario() {
		return grupousuario;
	}

	public void setGrupoUsuario(List<GrupoUsuario> grupousuario) {
		this.grupousuario = grupousuario;
	}

	public List<Permissoes> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<Permissoes> permissoes) {
		this.permissoes = permissoes;
	}

	public static final class UsuarioBuilder {
		private Long codigo;
		private String user;
		private String senha;
		private Date data_cadastro;
		private Pessoa pessoa;
		private List<GrupoUsuario> grupousuario;
		private List<Permissoes> permissoes;

		private UsuarioBuilder() {
		}

		public static UsuarioBuilder anUsuario() {
			return new UsuarioBuilder();
		}

		public UsuarioBuilder withCodigo(Long codigo) {
			this.codigo = codigo;
			return this;
		}

		public UsuarioBuilder withUser(String user) {
			this.user = user;
			return this;
		}

		public UsuarioBuilder withSenha(String senha) {
			this.senha = senha;
			return this;
		}

		public UsuarioBuilder withSenha(String senha, boolean criptografar) {
			if (!criptografar) return this.withSenha(senha);
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			this.senha = encoder.encode(senha);
			return this;
		}

		public UsuarioBuilder withData_cadastro(Date data_cadastro) {
			this.data_cadastro = data_cadastro;
			return this;
		}

		public UsuarioBuilder withPessoa(Pessoa pessoa) {
			this.pessoa = pessoa;
			return this;
		}

		public UsuarioBuilder withGrupousuario(List<GrupoUsuario> grupousuario) {
			this.grupousuario = grupousuario;
			return this;
		}

		public UsuarioBuilder withPermissoes(List<Permissoes> permissoes) {
			this.permissoes = permissoes;
			return this;
		}

		public Usuario build() {
			Usuario usuario = new Usuario();
			usuario.setCodigo(codigo);
			usuario.setGrupoUsuario(grupousuario);
			usuario.setUser(user);
			usuario.setSenha(senha);
			usuario.setData_cadastro(data_cadastro);
			usuario.setPessoa(pessoa);
			usuario.setPermissoes(permissoes);
			return usuario;
		}
	}
}
