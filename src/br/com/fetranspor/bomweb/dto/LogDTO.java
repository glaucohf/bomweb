package br.com.fetranspor.bomweb.dto;

import java.util.Date;

/**
 * The Class LogDTO.
 */
public class LogDTO
{

	/**
	 * Gets the dados antigos.
	 *
	 * @return the dados antigos
	 */
	public String getDadosAntigos()
	{
		return this.dadosAntigos;
	}

	/**
	 * Gets the dados novos.
	 *
	 * @return the dados novos
	 */
	public String getDadosNovos()
	{
		return this.dadosNovos;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public Date getData()
	{
		return this.data;
	}

	/**
	 * Gets the endereco ip.
	 *
	 * @return the endereco ip
	 */
	public String getEnderecoIP()
	{
		return this.enderecoIP;
	}

	/**
	 * Gets the entidade.
	 *
	 * @return the entidade
	 */
	public String getEntidade()
	{
		return this.entidade;
	}

	/**
	 * Gets the operacao.
	 *
	 * @return the operacao
	 */
	public String getOperacao()
	{
		return this.operacao;
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
	 * Gets the usuario.
	 *
	 * @return the usuario
	 */
	public String getUsuario()
	{
		return this.usuario;
	}

	/**
	 * Sets the dados antigos.
	 *
	 * @param dadosAntigos
	 *            the new dados antigos
	 */
	public void setDadosAntigos( final String dadosAntigos )
	{
		this.dadosAntigos = dadosAntigos;
	}

	/**
	 * Sets the dados novos.
	 *
	 * @param dadosNovos
	 *            the new dados novos
	 */
	public void setDadosNovos( final String dadosNovos )
	{
		this.dadosNovos = dadosNovos;
	}

	/**
	 * Sets the data.
	 *
	 * @param data
	 *            the new data
	 */
	public void setData( final Date data )
	{
		this.data = data;
	}

	/**
	 * Sets the endereco ip.
	 *
	 * @param enderecoIP
	 *            the new endereco ip
	 */
	public void setEnderecoIP( final String enderecoIP )
	{
		this.enderecoIP = enderecoIP;
	}

	/**
	 * Sets the entidade.
	 *
	 * @param entidade
	 *            the new entidade
	 */
	public void setEntidade( final String entidade )
	{
		this.entidade = entidade;
	}

	/**
	 * Sets the operacao.
	 *
	 * @param operacao
	 *            the new operacao
	 */
	public void setOperacao( final String operacao )
	{
		this.operacao = operacao;
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
	 * Sets the usuario.
	 *
	 * @param usuario
	 *            the new usuario
	 */
	public void setUsuario( final String usuario )
	{
		this.usuario = usuario;
	}

	/**
	 * The entidade.
	 */
	private String entidade;

	/**
	 * The data.
	 */
	private Date data;

	/**
	 * The operacao.
	 */
	private String operacao;

	/**
	 * The usuario.
	 */
	private String usuario;

	/**
	 * The perfil.
	 */
	private String perfil;

	/**
	 * The dados novos.
	 */
	private String dadosNovos;

	/**
	 * The dados antigos.
	 */
	private String dadosAntigos;

	/**
	 * The endereco ip.
	 */
	private String enderecoIP;
}
