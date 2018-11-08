package br.com.fetranspor.bomweb.dto;

import br.com.decatron.framework.util.Check;
import br.com.fetranspor.bomweb.entity.BomLinha;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * The Class BomLinhaPdfDTO.
 */
public class BomLinhaPdfDTO
{

	/**
	 * Instantiates a new bom linha pdf dto.
	 */
	BomLinhaPdfDTO()
	{
	}

	/**
	 * Gets the assentos ofertados.
	 *
	 * @return the assentos ofertados
	 */
	public Long getAssentosOfertados()
	{
		return this.bomLinha.getCapacidade() * getTotalViagens();
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
	 * Gets the bom secoes dto.
	 *
	 * @return the bom secoes dto
	 */
	public List<BomSecaoPdfDTO> getBomSecoesDto()
	{
		return this.bomSecoesDto;
	}

	/**
	 * Gets the codigo linha.
	 *
	 * @return the codigo linha
	 */
	public String getCodigoLinha()
	{
		return this.bomLinha.getLinhaVigencia().getCodigo();
	}

	/**
	 * Gets the descricao tipo veiculo.
	 *
	 * @return the descricao tipo veiculo
	 */
	public String getDescricaoTipoVeiculo()
	{
		return this.bomLinha.getTipoDeVeiculo().getDescricao();
	}

	/**
	 * Gets the extensao piso1.
	 *
	 * @return the extensao piso1
	 */
	public double getExtensaoPiso1()
	{
		Double piso1AB = this.bomLinha.getPiso1AB();

		if ( Check.isNull( piso1AB ) )
		{
			piso1AB = 0.0;
		}

		Double piso1BA = this.bomLinha.getPiso1BA();

		if ( Check.isNull( piso1BA ) )
		{
			piso1BA = 0.0;
		}

		return piso1AB + piso1BA;
	}

	/**
	 * Gets the extensao piso1 ab.
	 *
	 * @return the extensao piso1 ab
	 */
	public double getExtensaoPiso1AB()
	{
		Double piso1AB = this.bomLinha.getPiso1AB();

		if ( Check.isNull( piso1AB ) )
		{
			piso1AB = 0.0;
		}

		return piso1AB;
	}

	/**
	 * Gets the extensao piso1 ba.
	 *
	 * @return the extensao piso1 ba
	 */
	public double getExtensaoPiso1BA()
	{
		Double piso1BA = this.bomLinha.getPiso1BA();

		if ( Check.isNull( piso1BA ) )
		{
			piso1BA = 0.0;
		}

		return piso1BA;
	}

	/**
	 * Gets the extensao piso2.
	 *
	 * @return the extensao piso2
	 */
	public double getExtensaoPiso2()
	{
		Double piso2AB = this.bomLinha.getPiso2AB();

		if ( Check.isNull( piso2AB ) )
		{
			piso2AB = 0.0;
		}

		Double piso2BA = this.bomLinha.getPiso2BA();

		if ( Check.isNull( piso2BA ) )
		{
			piso2BA = 0.0;
		}

		return piso2AB + piso2BA;
	}

	/**
	 * Gets the extensao piso2 ab.
	 *
	 * @return the extensao piso2 ab
	 */
	public double getExtensaoPiso2AB()
	{
		Double piso2AB = this.bomLinha.getPiso2AB();

		if ( Check.isNull( piso2AB ) )
		{
			piso2AB = 0.0;
		}

		return piso2AB;
	}

	/**
	 * Gets the extensao piso2 ba.
	 *
	 * @return the extensao piso2 ba
	 */
	public double getExtensaoPiso2BA()
	{
		Double piso2BA = this.bomLinha.getPiso2BA();

		if ( Check.isNull( piso2BA ) )
		{
			piso2BA = 0.0;
		}

		return piso2BA;
	}

	/**
	 * Gets the extensao total.
	 *
	 * @return the extensao total
	 */
	public Double getExtensaoTotal()
	{
		return getExtensaoPiso1() + getExtensaoPiso2();
	}

	/**
	 * Gets the frota.
	 *
	 * @return the frota
	 */
	public Integer getFrota()
	{
		return this.bomLinha.getFrota();
	}

	/**
	 * Gets the itinerario ida.
	 *
	 * @return the itinerario ida
	 */
	public String getItinerarioIda()
	{
		return this.bomLinha.getLinhaVigencia().getItinerarioIda();
	}

	/**
	 * Gets the kms percorridos.
	 *
	 * @return the kms percorridos
	 */
	public Double getKmsPercorridos()
	{
		return this.bomLinha.getKmsPercorridos();
	}

	/**
	 * Gets the kms percorridos piso1.
	 *
	 * @return the kms percorridos piso1
	 */
	public double getKmsPercorridosPiso1()
	{
		return ( getExtensaoPiso1AB() * ( getViagensOrdinariasAB() + getViagensExtraordinariasAB() ) )
			+ ( getExtensaoPiso1BA() * ( getViagensOrdinariasBA() + getViagensExtraordinariasBA() ) );
	}

	/**
	 * Gets the kms percorridos piso2.
	 *
	 * @return the kms percorridos piso2
	 */
	public double getKmsPercorridosPiso2()
	{
		return ( getExtensaoPiso2AB() * ( getViagensOrdinariasAB() + getViagensExtraordinariasAB() ) )
			+ ( getExtensaoPiso2BA() * ( getViagensOrdinariasBA() + getViagensExtraordinariasBA() ) );
	}

	/**
	 * Gets the numero linha.
	 *
	 * @return the numero linha
	 */
	public String getNumeroLinha()
	{
		return this.bomLinha.getLinhaVigencia().getNumeroLinha();
	}

	/**
	 * Gets the numero passageiros.
	 *
	 * @return the numero passageiros
	 */
	public Long getNumeroPassageiros()
	{
		long numeroPassageiros = 0L;
		for ( final BomSecaoPdfDTO secao : this.bomSecoesDto )
		{
			numeroPassageiros += secao.getNumeroPassageiros();
		}

		return numeroPassageiros;
	}

	/**
	 * Gets the receita total.
	 *
	 * @return the receita total
	 */
	public BigDecimal getReceitaTotal()
	{
		BigDecimal receitaTotal = BigDecimal.ZERO;
		for ( final BomSecaoPdfDTO secao : this.bomSecoesDto )
		{
			receitaTotal = receitaTotal.add( secao.getReceita() );
		}

		return receitaTotal;
	}

	/**
	 * Gets the sigla codigo linha.
	 *
	 * @return the sigla codigo linha
	 */
	public String getSiglaCodigoLinha()
	{
		return this.bomLinha.getLinhaVigencia().getCodigo().substring( 0, 3 );
	}

	/**
	 * Gets the tarifa00.
	 *
	 * @return the tarifa00
	 */
	public BigDecimal getTarifa00()
	{
		return this.tarifa00;
	}

	/**
	 * Gets the tarifa00 anterior.
	 *
	 * @return the tarifa00 anterior
	 */
	public BigDecimal getTarifa00Anterior()
	{
		return this.tarifa00Anterior;
	}

	/**
	 * Gets the tipo ligacao.
	 *
	 * @return the tipo ligacao
	 */
	public String getTipoLigacao()
	{
		return this.bomLinha.getLinhaVigencia().getTipoLigacao();
	}

	/**
	 * Gets the tipos linha.
	 *
	 * @return the tipos linha
	 */
	public String getTiposLinha()
	{
		return this.bomLinha.getLinhaVigencia().getListagemTiposDeLinha();
	}

	/**
	 * Gets the total viagens.
	 *
	 * @return the total viagens
	 */
	public long getTotalViagens()
	{
		return getViagensOrdinarias() + getViagensExtraordinarias();
	}

	/**
	 * Gets the via.
	 *
	 * @return the via
	 */
	public String getVia()
	{
		return this.bomLinha.getLinhaVigencia().getVia();
	}

	/**
	 * Gets the viagens extraordinarias.
	 *
	 * @return the viagens extraordinarias
	 */
	public long getViagensExtraordinarias()
	{
		return ( long ) getViagensExtraordinariasAB() + getViagensExtraordinariasBA();
	}

	/**
	 * Gets the viagens extraordinarias ab.
	 *
	 * @return the viagens extraordinarias ab
	 */
	public Integer getViagensExtraordinariasAB()
	{
		final Integer viagensExtraordinariasAB = this.bomLinha.getViagensExtraordinariasAB();
		if ( Check.isNull( viagensExtraordinariasAB ) )
		{
			return 0;
		}
		return viagensExtraordinariasAB;
	}

	/**
	 * Gets the viagens extraordinarias ba.
	 *
	 * @return the viagens extraordinarias ba
	 */
	public Integer getViagensExtraordinariasBA()
	{
		final Integer viagensExtraordinariasBA = this.bomLinha.getViagensExtraordinariasBA();
		if ( Check.isNull( viagensExtraordinariasBA ) )
		{
			return 0;
		}
		return viagensExtraordinariasBA;
	}

	/**
	 * Gets the viagens ordinarias.
	 *
	 * @return the viagens ordinarias
	 */
	public long getViagensOrdinarias()
	{
		return ( long ) getViagensOrdinariasAB() + getViagensOrdinariasBA();
	}

	/**
	 * Gets the viagens ordinarias ab.
	 *
	 * @return the viagens ordinarias ab
	 */
	public Integer getViagensOrdinariasAB()
	{
		final Integer viagensOrdinariasAB = this.bomLinha.getViagensOrdinariasAB();
		if ( Check.isNull( viagensOrdinariasAB ) )
		{
			return 0;
		}
		return viagensOrdinariasAB;
	}

	/**
	 * Gets the viagens ordinarias ba.
	 *
	 * @return the viagens ordinarias ba
	 */
	public Integer getViagensOrdinariasBA()
	{
		final Integer viagensOrdinariasBA = this.bomLinha.getViagensOrdinariasBA();
		if ( Check.isNull( viagensOrdinariasBA ) )
		{
			return 0;
		}
		return viagensOrdinariasBA;
	}

	/**
	 * The bom dto.
	 */
	BomPdfDTO bomDto;

	/**
	 * The bom linha.
	 */
	BomLinha bomLinha;

	/**
	 * The tarifa00.
	 */
	BigDecimal tarifa00;

	/**
	 * The tarifa00 anterior.
	 */
	BigDecimal tarifa00Anterior;

	/**
	 * The bom secoes dto.
	 */
	List<BomSecaoPdfDTO> bomSecoesDto = new LinkedList<BomSecaoPdfDTO>();
}
