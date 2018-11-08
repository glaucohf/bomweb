package br.com.fetranspor.bomweb.dto;

import br.com.decatron.framework.util.Check;
import br.com.fetranspor.bomweb.entity.Bom;
import br.com.fetranspor.bomweb.entity.BomLinha;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * The Class BomPdfDTO.
 */
public class BomPdfDTO
{

	/**
	 * Instantiates a new bom pdf dto.
	 */
	BomPdfDTO()
	{
	}

	/**
	 * Gets the ano.
	 *
	 * @return the ano
	 */
	public String getAno()
	{
		return getMesReferencia().substring( 3, 7 );
	}

	/**
	 * Gets the bom linhas dto.
	 *
	 * @return the bom linhas dto
	 */
	public List<BomLinhaPdfDTO> getBomLinhasDto()
	{
		return this.bomLinhasDto;
	}

	/**
	 * Gets the data fechamento formatada.
	 *
	 * @return the data fechamento formatada
	 */
	public String getDataFechamentoFormatada()
	{
		if ( this.bom.getDataFechamento() == null )
		{
			return "[data indisponível]";
		}
		final SimpleDateFormat df = new SimpleDateFormat( "dd/MM/yyyy - HH:mm" );
		return df.format( this.bom.getDataFechamento() );
	}

	/**
	 * Gets the linhas dto.
	 *
	 * @return the linhas dto
	 */
	public List<LinhaPdfDTO> getLinhasDto()
	{
		return this.linhasDto;
	}

	/**
	 * Gets the mes.
	 *
	 * @return the mes
	 */
	public String getMes()
	{
		return getMesReferencia().substring( 0, 2 );
	}

	/**
	 * Gets the mes referencia.
	 *
	 * @return the mes referencia
	 * @throws AssertionError
	 *             the assertion error
	 */
	protected String getMesReferencia()
		throws AssertionError
	{
		final String mesReferencia = this.bom.getMesReferencia();

		// Na prática não ocorre.
		if ( Check.isNull( mesReferencia ) )
		{
			throw new AssertionError();
		}

		return mesReferencia;
	}

	/**
	 * Gets the nome empresa.
	 *
	 * @return the nome empresa
	 */
	public String getNomeEmpresa()
	{
		return this.bom.getEmpresa().getNome();
	}

	/**
	 * The bom.
	 */
	Bom bom;

	/**
	 * The bom linhas.
	 */
	List<BomLinha> bomLinhas;

	/**
	 * The linhas dto.
	 */
	List<LinhaPdfDTO> linhasDto = new LinkedList<LinhaPdfDTO>();

	/**
	 * The bom linhas dto.
	 */
	List<BomLinhaPdfDTO> bomLinhasDto = new LinkedList<BomLinhaPdfDTO>();
}
