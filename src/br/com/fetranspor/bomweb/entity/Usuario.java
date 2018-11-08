package br.com.fetranspor.bomweb.entity;

import br.com.decatron.framework.security.annotation.UserChangePassword;
import br.com.decatron.framework.security.annotation.UserClass;
import br.com.decatron.framework.security.annotation.UserEnabled;
import br.com.decatron.framework.security.annotation.UserName;
import br.com.decatron.framework.security.annotation.UserPassword;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

/**
 * The Class Usuario.
 */
@UserClass
@Entity
@Audited
@Table( name = "usuarios" )
public class Usuario
	extends InformacoesLog
{

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = -5537971287168610410L;

	/**
	 * Instantiates a new usuario.
	 */
	public Usuario()
	{
		super();
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail()
	{
		return this.email;
	}

	/**
	 * Gets the empresa.
	 *
	 * @return the empresa
	 */
	public Empresa getEmpresa()
	{
		return this.empresa;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.decatron.framework.entity.EntityBase#getId()
	 */
	@Override
	public Integer getId()
	{
		return this.id;
	}

	/**
	 * Gets the login.
	 *
	 * @return the login
	 */
	public String getLogin()
	{
		return this.login;
	}

	/**
	 * Gets the nome.
	 *
	 * @return the nome
	 */
	public String getNome()
	{
		return this.nome;
	}

	/**
	 * Gets the perfil.
	 *
	 * @return the perfil
	 */
	public String getPerfil()
	{
		return this.perfil;
	}

	/**
	 * Gets the senha.
	 *
	 * @return the senha
	 */
	public String getSenha()
	{
		return this.senha;
	}

	/**
	 * Gets the senha nao criptografada.
	 *
	 * @return the senha nao criptografada
	 */
	public String getSenhaNaoCriptografada()
	{
		return this.senhaNaoCriptografada;
	}

	/**
	 * Checks if is ativo.
	 *
	 * @return true, if is ativo
	 */
	public boolean isAtivo()
	{
		return this.ativo;
	}

	// ----------------------------------------------------------------------

	/**
	 * Checks if is trocar senha.
	 *
	 * @return true, if is trocar senha
	 */
	public boolean isTrocarSenha()
	{
		return this.trocarSenha;
	}

	// ----------------------------------------------------------------------

	/**
	 * Sets the ativo.
	 *
	 * @param ativo
	 *            the new ativo
	 */
	public void setAtivo( final boolean ativo )
	{
		this.ativo = ativo;
	}

	/**
	 * Sets the email.
	 *
	 * @param email
	 *            the new email
	 */
	public void setEmail( final String email )
	{
		this.email = email;
	}

	/**
	 * Sets the empresa.
	 *
	 * @param empresa
	 *            the new empresa
	 */
	public void setEmpresa( final Empresa empresa )
	{
		this.empresa = empresa;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId( final Integer id )
	{
		this.id = id;
	}

	/**
	 * Sets the login.
	 *
	 * @param login
	 *            the new login
	 */
	public void setLogin( final String login )
	{
		this.login = login;
	}

	/**
	 * Sets the nome.
	 *
	 * @param nome
	 *            the new nome
	 */
	public void setNome( final String nome )
	{
		this.nome = nome;
	}

	/**
	 * Sets the perfil.
	 *
	 * @param perfil
	 *            the new perfil
	 */
	public void setPerfil( final String perfil )
	{
		this.perfil = perfil;
	}

	/**
	 * Sets the senha.
	 *
	 * @param senha
	 *            the new senha
	 */
	public void setSenha( final String senha )
	{
		this.senha = senha;
	}

	/**
	 * Sets the senha nao criptografada.
	 *
	 * @param senhaNaoCriptografada
	 *            the new senha nao criptografada
	 */
	public void setSenhaNaoCriptografada( final String senhaNaoCriptografada )
	{
		this.senhaNaoCriptografada = senhaNaoCriptografada;
	}

	/**
	 * Sets the trocar senha.
	 *
	 * @param trocarSenha
	 *            the new trocar senha
	 */
	public void setTrocarSenha( final boolean trocarSenha )
	{
		this.trocarSenha = trocarSenha;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return getLogin();
	}

	/**
	 * The id.
	 */
	@Id
	@GeneratedValue
	private Integer id;

	/**
	 * The nome.
	 */
	private String nome;

	/**
	 * The login.
	 */
	@UserName
	@NotNull
	@Index( name = "idx_usuario_login" )
	private String login;

	/**
	 * The senha.
	 */
	@NotAudited
	@UserPassword
	@NotNull
	private String senha;

	/**
	 * The senha nao criptografada.
	 */
	@Transient
	private transient String senhaNaoCriptografada;

	/**
	 * The ativo.
	 */
	@UserEnabled
	@NotNull
	private boolean ativo;

	/**
	 * The empresa.
	 */
	@NotAudited
	@ManyToOne
	private Empresa empresa;

	/**
	 * The email.
	 */
	@NotNull
	private String email;

	/**
	 * The trocar senha.
	 */
	@NotAudited
	@NotNull
	@Column( name = "trocar_senha" )
	@UserChangePassword
	private boolean trocarSenha;

	/**
	 * The perfil.
	 */
	@Transient
	private transient String perfil;
}
