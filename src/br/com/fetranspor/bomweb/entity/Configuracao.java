package br.com.fetranspor.bomweb.entity;

import br.com.decatron.framework.entity.EntityBase;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * The Class Configuracao.
 */
@Entity
@Table( name = "configuracoes" )
public class Configuracao
	extends EntityBase
{

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = -4145274922698000053L;

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public static String getVersion()
	{
		return "v1.16.0.4";
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.decatron.framework.entity.EntityBase#getId()
	 */
	@Override
	public Long getId()
	{
		return this.id;
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
	 * Gets the valor.
	 *
	 * @return the valor
	 */
	public String getValor()
	{
		return this.valor;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId( final Long id )
	{
		this.id = id;
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
	 * Sets the valor.
	 *
	 * @param valor
	 *            the new valor
	 */
	public void setValor( final String valor )
	{
		this.valor = valor;
	}

	/**
	 * The id.
	 */
	@Id
	private Long id;

	/**
	 * The nome.
	 */
	@NotNull
	private String nome;

	/**
	 * The valor.
	 */
	@NotNull
	private String valor;
}
