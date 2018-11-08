package br.com.fetranspor.bomweb.dto;

import br.com.decatron.framework.util.Check;
import br.com.fetranspor.bomweb.entity.BomLinha;
import br.com.fetranspor.bomweb.entity.LinhaVigencia;

import java.math.BigDecimal;
import java.util.List;

/**
 * The Class LinhaPdfDTO.
 */
public class LinhaPdfDTO
	implements Comparable<LinhaPdfDTO>
{

	/**
	 * Instantiates a new linha pdf dto.
	 *
	 * @param bomDto
	 *            the bom dto
	 * @param linhaVigencia
	 *            the linha vigencia
	 * @param bomLinhas
	 *            the bom linhas
	 */
	public LinhaPdfDTO( final BomPdfDTO bomDto, final LinhaVigencia linhaVigencia, final List<BomLinha> bomLinhas )
	{
		this.bomDto = bomDto;
		this.linhaVigencia = linhaVigencia;
		this.bomLinhas = bomLinhas;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param o
	 * @return
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo( final LinhaPdfDTO o )
	{
		if ( Check.isNotNull( this.linhaVigencia ) && Check.isNotNull( this.linhaVigencia.getCodigo() ) )
		{
			final LinhaVigencia outraLinha = o.linhaVigencia;
			if ( outraLinha != null )
			{
				return this.linhaVigencia.getCodigo().compareTo( outraLinha.getCodigo() );
			}
		}
		return -1;
	}

	/**
	 * Gets the bom dto.
	 *
	 * @return the bom dto
	 */
	public BomPdfDTO getBomDto()
	{
		return this.bomDto;
	}

	/**
	 * Gets the codigo.
	 *
	 * @return the codigo
	 */
	public String getCodigo()
	{
		return this.linhaVigencia.getCodigo();
	}

	/**
	 * Gets the kms percorridos.
	 *
	 * @return the kms percorridos
	 */
	public double getKmsPercorridos()
	{
		double kmsPercorridos = 0L;
		for ( final BomLinha bomLinha : this.bomLinhas )
		{
			kmsPercorridos += bomLinha.getKmsPercorridos();
		}

		return kmsPercorridos;
	}

	/**
	 * Gets the nome.
	 *
	 * @return the nome
	 */
	public String getNome()
	{
		return this.linhaVigencia.getPontoInicial() + " - " + this.linhaVigencia.getPontoFinal();
	}

	/**
	 * Gets the numero passageiros.
	 *
	 * @return the numero passageiros
	 */
	public long getNumeroPassageiros()
	{
		long numeroPassageiros = 0L;
		for ( final BomLinha bomLinha : this.bomLinhas )
		{
			numeroPassageiros += bomLinha.getNumeroPassageiros();
		}

		return numeroPassageiros;
	}

	/**
	 * Gets the numero viagens.
	 *
	 * @return the numero viagens
	 */
	public long getNumeroViagens()
	{
		long numeroViagens = 0L;
		for ( final BomLinha bomLinha : this.bomLinhas )
		{
			numeroViagens += bomLinha.getNumeroViagens();
		}

		return numeroViagens;
	}

	/**
	 * Gets the receita total.
	 *
	 * @return the receita total
	 */
	public BigDecimal getReceitaTotal()
	{
		BigDecimal totalReceita = BigDecimal.ZERO;
		for ( final BomLinha bomLinha : this.bomLinhas )
		{
			totalReceita = totalReceita.add( bomLinha.getTotalReceita() );
		}

		return totalReceita;
	}

	/**
	 * Gets the sigla codigo linha.
	 *
	 * @return the sigla codigo linha
	 */
	public String getSiglaCodigoLinha()
	{
		return this.linhaVigencia.getCodigo().substring( 0, 3 );
	}

	/**
	 * Gets the tipos linha.
	 *
	 * @return the tipos linha
	 */
	public String getTiposLinha()
	{
		return this.linhaVigencia.getListagemTiposDeLinha();
	}

	/**
	 * The bom dto.
	 */
	private final BomPdfDTO bomDto;

	/**
	 * The bom linhas.
	 */
	private final List<BomLinha> bomLinhas;

	/**
	 * The linha vigencia.
	 */
	private final LinhaVigencia linhaVigencia;
}
