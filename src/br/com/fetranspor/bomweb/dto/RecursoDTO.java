package br.com.fetranspor.bomweb.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class RecursoDTO.
 */
public class RecursoDTO
{

	/**
	 * Gets the acoes.
	 *
	 * @return the acoes
	 */
	public List<AcaoDTO> getAcoes()
	{
		return this.acoes;
	}

	/**
	 * Gets the id perfil.
	 *
	 * @return the id perfil
	 */
	public Integer getIdPerfil()
	{
		return this.idPerfil;
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
	 * Sets the acoes.
	 *
	 * @param acoes
	 *            the new acoes
	 */
	public void setAcoes( final List<AcaoDTO> acoes )
	{
		this.acoes = acoes;
	}

	/**
	 * Sets the id perfil.
	 *
	 * @param idPerfil
	 *            the new id perfil
	 */
	public void setIdPerfil( final Integer idPerfil )
	{
		this.idPerfil = idPerfil;
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
	 * The nome.
	 */
	private String nome;

	/**
	 * The id perfil.
	 */
	private Integer idPerfil;

	/**
	 * The acoes.
	 */
	private List<AcaoDTO> acoes = new ArrayList<AcaoDTO>();

}
