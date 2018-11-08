package br.com.fetranspor.bomweb.entity;

import br.com.decatron.framework.util.Check;
import br.com.fetranspor.bomweb.components.Utils;
import br.com.fetranspor.bomweb.exception.CloneFailException;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.Index;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

/**
 * The Class LinhaVigencia.
 */
@Entity
@Audited
@Table( name = "linha_vigencia" )
@Inheritance( strategy = InheritanceType.JOINED )
public class LinhaVigencia
	extends InformacoesLog
	implements Cloneable
{

	/**
	 * The Constant DIGITOS_CODIGO.
	 */
	public final static int DIGITOS_CODIGO = 9;

	/**
	 * Logger for this class
	 */
	private static final Log LOG = LogFactory.getLog( LinhaVigencia.class );

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = -6124850005522219313L;

	/**
	 * Instantiates a new linha vigencia.
	 */
	public LinhaVigencia()
	{
		super();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @throws CloneFailException
	 * @see java.lang.Object#clone()
	 */
	@Override
	public LinhaVigencia clone()
		throws CloneFailException
	{

		final LinhaVigencia linhaVigencia = new LinhaVigencia();

		// TODO vir da tela
		try
		{
			PropertyUtils.copyProperties( linhaVigencia, this );
			if ( Check.isNotNull( this.secoes ) )
			{
				linhaVigencia.setSecoes( new ArrayList<Secao>() );
				for ( final Secao secao : this.secoes )
				{
					final Secao secaoClonada = secao.clone();
					// Vir da tela
					secaoClonada.setActive( Boolean.TRUE );
					secaoClonada.setLinhaVigencia( linhaVigencia );
					linhaVigencia.getSecoes().add( secaoClonada );
				}

				// FBW-234 dois objetos hibernate não podem compratilha a mesma instancia de uma
				// collection
				final List<LinhaVigenciaTipoDeLinha> linhasVigenciaTiposDeLinhaOld = linhaVigencia
					.getLinhasVigenciaTiposDeLinha();
				// FBW-392
				if ( linhasVigenciaTiposDeLinhaOld != null )
				{
					final List<LinhaVigenciaTipoDeLinha> linhasVigenciaTiposDeLinhaNew = new ArrayList<LinhaVigenciaTipoDeLinha>(
						linhasVigenciaTiposDeLinhaOld );
					linhaVigencia.setLinhasVigenciaTiposDeLinha( linhasVigenciaTiposDeLinhaNew );
				}

			}
		}
		catch ( final IllegalAccessException e )
		{
			throw new CloneFailException( e );
		}
		catch ( final InvocationTargetException e )
		{
			throw new CloneFailException( e );
		}
		catch ( final NoSuchMethodException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
		}

		linhaVigencia.setId( null );
		return linhaVigencia;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return Retorna os atributos do objeto como string.
	 */
	public String getAtributosComoString()
	{
		String secoesString = "";
		final List<Secao> secoesList = getSecoes();
		if ( Check.isNotNull( secoesList ) )
		{
			for ( final Secao secao : secoesList )
			{
				secoesString += String.format(
					"Seção %s: %s - %s ",
					secao.getCodigo(),
					secao.getPontoInicial(),
					secao.getPontoFinal() );
			}// for

		}// if
		final String linhaVigenciaComoString = String
			.format(
				"Status da linha: %s, Empresa: %s, Código da Linha: %s, Número da Linha: %s, Ponto Inicial: %s, Ponto Final: %s, Via: %s, Tipo da Ligação: %s, Tipos de Linha: %s, Piso I A-B (Km): %s, Piso I B-A (Km): %s, Piso II A-B (Km): %s, Piso II B-A (Km): %s, Extensão A-B: %s, Extensão B-A: %s, Hierarquização: %s, Data Vigência: %s, Observação: %s, Seções: %s",
				getStatus(),
				getEmpresa().getNome(),
				getCodigo(),
				getNumeroLinha(),
				getPontoInicial(),
				getPontoFinal(),
				getVia(),
				getTipoLigacao(),
				getTiposDeLinha(),
				getPiso1AB(),
				getPiso1BA(),
				getPiso2AB(),
				getPiso2BA(),
				getExtensaoAB(),
				getExtensaoBA(),
				getHierarquizacao(),
				getDataInicio(),
				getObservacao(),
				secoesString );

		return linhaVigenciaComoString;
	}// func

	/**
	 * Gets the codigo.
	 *
	 * @return the codigo
	 */
	public String getCodigo()
	{
		return this.codigo;
	}

	/**
	 * <p>
	 * </p>
	 * <p>
	 * FBW-319
	 * </p>
	 *
	 * @see br.com.fetranspor.bomweb.entity.BomWebEntityBase#getComboString()
	 */
	@Override
	public String getComboString()
	{
		final String codigo = getCodigo();
		final String codigoStr = toString( codigo );
		final String shortComboString = getShortComboString();
		final String result = String.format( "%s %s", codigoStr, shortComboString );
		return result;
	}// func

	/**
	 * Gets the data criacao.
	 *
	 * @return the data criacao
	 */
	public Date getDataCriacao()
	{
		return this.dataCriacao;
	}

	/**
	 * Gets the data inicio.
	 *
	 * @return the data inicio
	 */
	public Date getDataInicio()
	{
		return this.dataInicio;
	}

	/**
	 * Gets the data inicio formated.
	 *
	 * @return the data inicio formated
	 */
	public String getDataInicioFormated()
	{
		final SimpleDateFormat format = new SimpleDateFormat( "dd/MM/yyyy" );
		String source = "";
		if ( Check.isNotNull( this.dataInicio ) )
		{
			source = format.format( this.dataInicio );
		}
		return source;
	}

	/**
	 * Gets the data termino.
	 *
	 * @return the data termino
	 */
	public Date getDataTermino()
	{
		return this.dataTermino;
	}

	/**
	 * Gets the duracao viagem fora pico ab.
	 *
	 * @return the duracao viagem fora pico ab
	 */
	public String getDuracaoViagemForaPicoAB()
	{
		return this.duracaoViagemForaPicoAB;
	}

	/**
	 * Gets the duracao viagem fora pico ba.
	 *
	 * @return the duracao viagem fora pico ba
	 */
	public String getDuracaoViagemForaPicoBA()
	{
		return this.duracaoViagemForaPicoBA;
	}

	/**
	 * Gets the duracao viagem pico ab.
	 *
	 * @return the duracao viagem pico ab
	 */
	public String getDuracaoViagemPicoAB()
	{
		return this.duracaoViagemPicoAB;
	}

	/**
	 * Gets the duracao viagem pico ba.
	 *
	 * @return the duracao viagem pico ba
	 */
	public String getDuracaoViagemPicoBA()
	{
		return this.duracaoViagemPicoBA;
	}

	/**
	 * <p>
	 * </p>
	 * <p>
	 * FBW-487 FBW-483
	 * </p>
	 *
	 * @return Returns the edicaoRetroativaJistificativa.
	 * @see #edicaoRetroativaJustificativa
	 */
	public String getEdicaoRetroativaJustificativa()
	{
		return this.edicaoRetroativaJustificativa;
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
	 * Gets the extensao ab.
	 *
	 * @return the extensao ab
	 */
	public Double getExtensaoAB()
	{
		final Double piso1ab = getPiso1AB();
		final Double piso2ab = getPiso2AB();
		if ( ( piso1ab == null ) || ( piso2ab == null ) )
		{
			return null;
		}// if
		return piso1ab + piso2ab;
	}

	/**
	 * Gets the extensao ba.
	 *
	 * @return the extensao ba
	 */
	public Double getExtensaoBA()
	{
		final Double piso2ba = getPiso2BA();
		final Double piso1ba = getPiso1BA();
		if ( ( piso1ba == null ) || ( piso2ba == null ) )
		{
			return null;
		}// if
		return piso1ba + piso2ba;
	}

	/**
	 * Gets the extensao total.
	 *
	 * @return the extensao total
	 */
	public Double getExtensaoTotal()
	{
		final Double extensaoAB = getExtensaoAB();
		final Double extensaoBA = getExtensaoBA();
		if ( ( extensaoAB == null ) || ( extensaoBA == null ) )
		{
			return null;
		}// id
		return extensaoAB + extensaoBA;
	}

	/**
	 * Gets the hierarquizacao.
	 *
	 * @return the hierarquizacao
	 */
	public String getHierarquizacao()
	{
		return this.hierarquizacao;
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
	 * Gets the id linha vigencia antiga.
	 *
	 * @return the id linha vigencia antiga
	 */
	public Long getIdLinhaVigenciaAntiga()
	{
		return this.idLinhaVigenciaAntiga;
	}

	/**
	 * Gets the itinerario ida.
	 *
	 * @return the itinerario ida
	 */
	public String getItinerarioIda()
	{
		return this.pontoInicial + " - " + this.pontoFinal;
	}

	/**
	 * Gets the itinerario volta.
	 *
	 * @return the itinerario volta
	 */
	public String getItinerarioVolta()
	{
		return this.pontoFinal + " - " + this.pontoInicial;
	}

	/**
	 * Gets the linha.
	 *
	 * @return the linha
	 */
	public Linha getLinha()
	{
		return this.linha;
	}

	/**
	 * Gets the linhas vigencia tipos de linha.
	 *
	 * @return the linhas vigencia tipos de linha
	 */
	public List<LinhaVigenciaTipoDeLinha> getLinhasVigenciaTiposDeLinha()
	{
		return this.linhasVigenciaTiposDeLinha;
	}

	/**
	 * Gets the listagem secoes.
	 *
	 * @return the listagem secoes
	 */
	public String getListagemSecoes()
	{
		final StringBuffer listagem = new StringBuffer();
		final List<Secao> secoes = getSecoes();

		if ( ( secoes != null ) && !secoes.isEmpty() )
		{
			for ( final Secao secao : secoes )
			{
				listagem.append( secao.getCodigo() ).append( " \n" );
			}
			listagem.delete( listagem.length() - 2, listagem.length() );
		}

		return listagem.toString();
	}

	/**
	 * Gets the listagem tipo de veiculos utilizados.
	 *
	 * @return the listagem tipo de veiculos utilizados
	 */
	public String getListagemTipoDeVeiculosUtilizados()
	{

		final Set<TipoDeVeiculo> tiposDeVeiculos = getTipoDeVeiculosUtilizados();
		final StringBuffer listagem = new StringBuffer();

		if ( ( tiposDeVeiculos != null ) && !tiposDeVeiculos.isEmpty() )
		{
			for ( final TipoDeVeiculo tipo : tiposDeVeiculos )
			{
				listagem.append( tipo );
				listagem.append( ", " );
			}
			listagem.delete( listagem.length() - 2, listagem.length() );
		}

		return listagem.toString();
	}

	/**
	 * Gets the listagem tipos de linha.
	 *
	 * @return the listagem tipos de linha
	 */
	public String getListagemTiposDeLinha()
	{
		final StringBuffer listagem = new StringBuffer();

		final List<TipoDeLinha> tiposLinha = getTiposDeLinha();
		if ( ( tiposLinha != null ) && !tiposLinha.isEmpty() )
		{
			for ( final TipoDeLinha tipo : tiposLinha )
			{
				listagem.append( tipo.getSigla() );
				listagem.append( ", " );
			}
			listagem.delete( listagem.length() - 2, listagem.length() );
		}

		return listagem.toString();
	}

	/**
	 * Gets the numero linha.
	 *
	 * @return the numero linha
	 */
	public String getNumeroLinha()
	{
		return this.numeroLinha;
	}

	/**
	 * Gets the observacao.
	 *
	 * @return the observacao
	 */
	public String getObservacao()
	{
		return this.observacao;
	}

	/**
	 * Gets the pico fim manha ab.
	 *
	 * @return the pico fim manha ab
	 */
	public String getPicoFimManhaAB()
	{
		return this.picoFimManhaAB;
	}

	/**
	 * Gets the pico fim manha ba.
	 *
	 * @return the pico fim manha ba
	 */
	public String getPicoFimManhaBA()
	{
		return this.picoFimManhaBA;
	}

	/**
	 * Gets the pico fim tarde ab.
	 *
	 * @return the pico fim tarde ab
	 */
	public String getPicoFimTardeAB()
	{
		return this.picoFimTardeAB;
	}

	/**
	 * Gets the pico fim tarde ba.
	 *
	 * @return the pico fim tarde ba
	 */
	public String getPicoFimTardeBA()
	{
		return this.picoFimTardeBA;
	}

	/**
	 * Gets the pico inicio manha ab.
	 *
	 * @return the pico inicio manha ab
	 */
	public String getPicoInicioManhaAB()
	{
		return this.picoInicioManhaAB;
	}

	/**
	 * Gets the pico inicio manha ba.
	 *
	 * @return the pico inicio manha ba
	 */
	public String getPicoInicioManhaBA()
	{
		return this.picoInicioManhaBA;
	}

	/**
	 * Gets the pico inicio tarde ab.
	 *
	 * @return the pico inicio tarde ab
	 */
	public String getPicoInicioTardeAB()
	{
		return this.picoInicioTardeAB;
	}

	/**
	 * Gets the pico inicio tarde ba.
	 *
	 * @return the pico inicio tarde ba
	 */
	public String getPicoInicioTardeBA()
	{
		return this.picoInicioTardeBA;
	}

	/**
	 * Gets the piso1 ab.
	 *
	 * @return the piso1 ab
	 */
	public Double getPiso1AB()
	{
		return this.piso1AB;
	}

	/**
	 * Gets the piso1 ba.
	 *
	 * @return the piso1 ba
	 */
	public Double getPiso1BA()
	{
		return this.piso1BA;
	}

	/**
	 * Gets the piso2 ab.
	 *
	 * @return the piso2 ab
	 */
	public Double getPiso2AB()
	{
		return this.piso2AB;
	}

	/**
	 * Gets the piso2 ba.
	 *
	 * @return the piso2 ba
	 */
	public Double getPiso2BA()
	{
		return this.piso2BA;
	}

	/**
	 * Gets the ponto final.
	 *
	 * @return the ponto final
	 */
	public String getPontoFinal()
	{
		return this.pontoFinal;
	}

	/**
	 * Gets the ponto inicial.
	 *
	 * @return the ponto inicial
	 */
	public String getPontoInicial()
	{
		return this.pontoInicial;
	}

	/**
	 * Gets the secoes.
	 *
	 * @return the secoes
	 */
	public List<Secao> getSecoes()
	{
		if ( this.secoes != null )
		{
			Collections.sort( this.secoes );
		}
		return this.secoes;
	}

	/**
	 * Gets the secoes sem secao00.
	 *
	 * @return the secoes sem secao00
	 */
	public List<Secao> getSecoesSemSecao00()
	{
		final List<Secao> secoes = new ArrayList<Secao>();
		if ( this.secoes == null )
		{
			return null;
		}

		secoes.addAll( this.secoes );
		for ( int i = 0; i < secoes.size(); i++ )
		{
			if ( Secao.COD_SECAO_OBRIGATORIA.equals( secoes.get( i ).getCodigo() ) )
			{
				secoes.remove( secoes.get( i ) );
				break;
			}
		}
		return secoes;
	}

	/**
	 * <p>
	 * </p>
	 * <p>
	 * FBW-319
	 * </p>
	 *
	 * @return
	 */
	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.fetranspor.bomweb.entity.BomWebEntityBase#getShortComboString()
	 */
	@Override
	public String getShortComboString()
	{
		final String pontoInicial = getPontoInicial();
		final String pontoFinal = getPontoFinal();

		final String pontoInicialStr = toString( pontoInicial );
		final String pontoFinalStr = toString( pontoFinal );

		final String result = String.format( "%s - %s", pontoInicialStr, pontoFinalStr );
		return result;
	}// func

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public StatusLinha getStatus()
	{
		return this.status;
	}

	/**
	 * Gets the tipo de veiculos utilizados.
	 *
	 * @return the tipo de veiculos utilizados
	 */
	public Set<TipoDeVeiculo> getTipoDeVeiculosUtilizados()
	{
		final Set<TipoDeVeiculo> tiposVeiculo = new HashSet<TipoDeVeiculo>();
		if ( this.linhasVigenciaTiposDeLinha != null )
		{
			for ( final LinhaVigenciaTipoDeLinha lvTipoDeLinha : this.linhasVigenciaTiposDeLinha )
			{
				if ( lvTipoDeLinha.isActive() )
				{
					tiposVeiculo.addAll( lvTipoDeLinha.getTiposDeVeiculoUtilizados() );
				}
			}
		}

		return tiposVeiculo;
	}

	/**
	 * Gets the tipo ligacao.
	 *
	 * @return the tipo ligacao
	 */
	public String getTipoLigacao()
	{
		return this.tipoLigacao;
	}

	/**
	 * Gets the tipos de linha.
	 *
	 * @return the tipos de linha
	 */
	public List<TipoDeLinha> getTiposDeLinha()
	{
		final List<TipoDeLinha> tiposLinha = new ArrayList<TipoDeLinha>();
		if ( this.linhasVigenciaTiposDeLinha != null )
		{
			for ( final LinhaVigenciaTipoDeLinha lvTipoDeLinha : this.linhasVigenciaTiposDeLinha )
			{
				if ( lvTipoDeLinha.isActive() )
				{
					tiposLinha.add( lvTipoDeLinha.getTipoDeLinha() );
				}
			}
		}

		return tiposLinha;
	}

	/**
	 * Gets the via.
	 *
	 * @return the via
	 */
	public String getVia()
	{
		return this.via;
	}

	/**
	 * Checks if is bom linha fechado.
	 *
	 * @return true, if is bom linha fechado
	 */
	public boolean isBomLinhaFechado()
	{
		return this.bomLinhaFechado;
	}

	/**
	 * Checks if is status ativa.
	 *
	 * @return true, if is status ativa
	 */
	public boolean isStatusAtiva()
	{
		if ( Check.isNotNull( this.status ) )
		{
			if ( this.status.equals( StatusLinha.ATIVA ) )
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if is vigente.
	 *
	 * @return true, if is vigente
	 */
	public boolean isVigente()
	{
		boolean retorno = false;
		final Date hoje = new Date();

		if ( this.dataInicio != null )
		{
			// DataInicio eh igual ou anterior a hoje.
			if ( Utils.compareToSemHora( this.dataInicio, hoje ) <= 0 )
			{
				if ( Check.isNull( this.dataTermino ) || ( Utils.compareToSemHora( this.dataTermino, hoje ) >= 0 ) )
				{
					retorno = true;
				}
			}
		}
		return retorno;
	}

	/**
	 * Sets the bom linha fechado.
	 *
	 * @param bomLinhaFechado
	 *            the new bom linha fechado
	 */
	public void setBomLinhaFechado( final boolean bomLinhaFechado )
	{
		this.bomLinhaFechado = bomLinhaFechado;
	}

	/**
	 * Sets the codigo.
	 *
	 * @param codigo
	 *            the new codigo
	 */
	public void setCodigo( final String codigo )
	{
		this.codigo = codigo;
	}

	/**
	 * Sets the data criacao.
	 *
	 * @param dataCriacao
	 *            the new data criacao
	 */
	public void setDataCriacao( final Date dataCriacao )
	{
		this.dataCriacao = dataCriacao;
	}

	/**
	 * Sets the data inicio.
	 *
	 * @param dataInicio
	 *            the new data inicio
	 */
	public void setDataInicio( final Date dataInicio )
	{
		this.dataInicio = dataInicio;
	}

	/**
	 * Sets the data termino.
	 *
	 * @param dataTermino
	 *            the new data termino
	 */
	public void setDataTermino( final Date dataTermino )
	{
		this.dataTermino = dataTermino;
	}

	/**
	 * Sets the duracao viagem fora pico ab.
	 *
	 * @param duracaoViagemForaPicoAB
	 *            the new duracao viagem fora pico ab
	 */
	public void setDuracaoViagemForaPicoAB( final String duracaoViagemForaPicoAB )
	{
		this.duracaoViagemForaPicoAB = duracaoViagemForaPicoAB;
	}

	/**
	 * Sets the duracao viagem fora pico ba.
	 *
	 * @param duracaoViagemForaPicoBA
	 *            the new duracao viagem fora pico ba
	 */
	public void setDuracaoViagemForaPicoBA( final String duracaoViagemForaPicoBA )
	{
		this.duracaoViagemForaPicoBA = duracaoViagemForaPicoBA;
	}

	/**
	 * Sets the duracao viagem pico ab.
	 *
	 * @param duracaoViagemPicoAB
	 *            the new duracao viagem pico ab
	 */
	public void setDuracaoViagemPicoAB( final String duracaoViagemPicoAB )
	{
		this.duracaoViagemPicoAB = duracaoViagemPicoAB;
	}

	/**
	 * Sets the duracao viagem pico ba.
	 *
	 * @param duracaoViagemPicoBA
	 *            the new duracao viagem pico ba
	 */
	public void setDuracaoViagemPicoBA( final String duracaoViagemPicoBA )
	{
		this.duracaoViagemPicoBA = duracaoViagemPicoBA;
	}

	/**
	 * <p>
	 * </p>
	 * <p>
	 * FBW-487 FBW-483
	 * </p>
	 *
	 * @param edicaoRetroativaJustificativa
	 *            The edicaoRetroativaJustificativa to set.
	 * @see #edicaoRetroativaJustificativa
	 */
	public void setEdicaoRetroativaJustificativa( final String edicaoRetroativaJustificativa )
	{
		this.edicaoRetroativaJustificativa = edicaoRetroativaJustificativa;
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
	 * Sets the hierarquizacao.
	 *
	 * @param hierarquizacao
	 *            the new hierarquizacao
	 */
	public void setHierarquizacao( final String hierarquizacao )
	{
		this.hierarquizacao = hierarquizacao;
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
	 * Sets the id linha vigencia antiga.
	 *
	 * @param idLinhaVigenciaAntiga
	 *            the new id linha vigencia antiga
	 */
	public void setIdLinhaVigenciaAntiga( final Long idLinhaVigenciaAntiga )
	{
		this.idLinhaVigenciaAntiga = idLinhaVigenciaAntiga;
	}

	/**
	 * Sets the linha.
	 *
	 * @param linha
	 *            the new linha
	 */
	public void setLinha( final Linha linha )
	{
		this.linha = linha;
	}

	/**
	 * Sets the linhas vigencia tipos de linha.
	 *
	 * @param linhasVigenciaTiposDeLinha
	 *            the new linhas vigencia tipos de linha
	 */
	public void setLinhasVigenciaTiposDeLinha( final List<LinhaVigenciaTipoDeLinha> linhasVigenciaTiposDeLinha )
	{
		this.linhasVigenciaTiposDeLinha = linhasVigenciaTiposDeLinha;
	}

	/**
	 * Sets the numero linha.
	 *
	 * @param numeroLinha
	 *            the new numero linha
	 */
	public void setNumeroLinha( final String numeroLinha )
	{
		this.numeroLinha = numeroLinha;
	}

	/**
	 * Sets the observacao.
	 *
	 * @param observacao
	 *            the new observacao
	 */
	public void setObservacao( final String observacao )
	{
		this.observacao = observacao;
	}

	/**
	 * Sets the pico fim manha ab.
	 *
	 * @param picoFimManhaAB
	 *            the new pico fim manha ab
	 */
	public void setPicoFimManhaAB( final String picoFimManhaAB )
	{
		this.picoFimManhaAB = picoFimManhaAB;
	}

	/**
	 * Sets the pico fim manha ba.
	 *
	 * @param picoFimManhaBA
	 *            the new pico fim manha ba
	 */
	public void setPicoFimManhaBA( final String picoFimManhaBA )
	{
		this.picoFimManhaBA = picoFimManhaBA;
	}

	/**
	 * Sets the pico fim tarde ab.
	 *
	 * @param picoFimTardeAB
	 *            the new pico fim tarde ab
	 */
	public void setPicoFimTardeAB( final String picoFimTardeAB )
	{
		this.picoFimTardeAB = picoFimTardeAB;
	}

	/**
	 * Sets the pico fim tarde ba.
	 *
	 * @param picoFimTardeBA
	 *            the new pico fim tarde ba
	 */
	public void setPicoFimTardeBA( final String picoFimTardeBA )
	{
		this.picoFimTardeBA = picoFimTardeBA;
	}

	/**
	 * Sets the pico inicio manha ab.
	 *
	 * @param picoInicioManhaAB
	 *            the new pico inicio manha ab
	 */
	public void setPicoInicioManhaAB( final String picoInicioManhaAB )
	{
		this.picoInicioManhaAB = picoInicioManhaAB;
	}

	/**
	 * Sets the pico inicio manha ba.
	 *
	 * @param picoInicioManhaBA
	 *            the new pico inicio manha ba
	 */
	public void setPicoInicioManhaBA( final String picoInicioManhaBA )
	{
		this.picoInicioManhaBA = picoInicioManhaBA;
	}

	/**
	 * Sets the pico inicio tarde ab.
	 *
	 * @param picoInicioTardeAB
	 *            the new pico inicio tarde ab
	 */
	public void setPicoInicioTardeAB( final String picoInicioTardeAB )
	{
		this.picoInicioTardeAB = picoInicioTardeAB;
	}

	/**
	 * Sets the pico inicio tarde ba.
	 *
	 * @param picoInicioTardeBA
	 *            the new pico inicio tarde ba
	 */
	public void setPicoInicioTardeBA( final String picoInicioTardeBA )
	{
		this.picoInicioTardeBA = picoInicioTardeBA;
	}

	/**
	 * Sets the piso1 ab.
	 *
	 * @param piso1ab
	 *            the new piso1 ab
	 */
	public void setPiso1AB( final Double piso1ab )
	{
		this.piso1AB = piso1ab;
	}

	/**
	 * Sets the piso1 ba.
	 *
	 * @param piso1ba
	 *            the new piso1 ba
	 */
	public void setPiso1BA( final Double piso1ba )
	{
		this.piso1BA = piso1ba;
	}

	/**
	 * Sets the piso2 ab.
	 *
	 * @param piso2ab
	 *            the new piso2 ab
	 */
	public void setPiso2AB( final Double piso2ab )
	{
		this.piso2AB = piso2ab;
	}

	/**
	 * Sets the piso2 ba.
	 *
	 * @param piso2ba
	 *            the new piso2 ba
	 */
	public void setPiso2BA( final Double piso2ba )
	{
		this.piso2BA = piso2ba;
	}

	/**
	 * Sets the ponto final.
	 *
	 * @param pontoFinal
	 *            the new ponto final
	 */
	public void setPontoFinal( final String pontoFinal )
	{
		this.pontoFinal = pontoFinal;
	}

	/**
	 * Sets the ponto inicial.
	 *
	 * @param pontoInicial
	 *            the new ponto inicial
	 */
	public void setPontoInicial( final String pontoInicial )
	{
		this.pontoInicial = pontoInicial;
	}

	/**
	 * Sets the secoes.
	 *
	 * @param secoes
	 *            the new secoes
	 */
	public void setSecoes( final List<Secao> secoes )
	{
		this.secoes = secoes;
	}

	/**
	 * Sets the status.
	 *
	 * @param status
	 *            the new status
	 */
	public void setStatus( final StatusLinha status )
	{
		this.status = status;
	}

	/**
	 * Sets the tipo ligacao.
	 *
	 * @param tipoLigacao
	 *            the new tipo ligacao
	 */
	public void setTipoLigacao( final String tipoLigacao )
	{
		this.tipoLigacao = tipoLigacao;
	}

	/**
	 * Sets the via.
	 *
	 * @param via
	 *            the new via
	 */
	public void setVia( final String via )
	{
		this.via = via;
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
		return getPontoInicial() + " - " + getPontoFinal();
	}

	/**
	 * The bom linha fechado.
	 */
	@Transient
	private transient boolean bomLinhaFechado;

	/**
	 * The codigo.
	 */
	@NotNull
	@Index( name = "idx_linhavigencia_codigo" )
	private String codigo;

	/**
	 * The data criacao.
	 */
	@Column( name = "data_criacao" )
	private Date dataCriacao;

	/**
	 * The data inicio.
	 */
	@Temporal( TemporalType.DATE )
	@Column( name = "data_inicio" )
	@Index( name = "idx_linhavigencia_inicio" )
	private Date dataInicio;

	/**
	 * The data termino.
	 */
	@Temporal( TemporalType.DATE )
	@Column( name = "data_termino" )
	@Index( name = "idx_linhavigencia_fim" )
	private Date dataTermino;

	/**
	 * The duracao viagem fora pico ab.
	 */
	private String duracaoViagemForaPicoAB;

	/**
	 * The duracao viagem fora pico ba.
	 */
	private String duracaoViagemForaPicoBA;

	/**
	 * The duracao viagem pico ab.
	 */
	private String duracaoViagemPicoAB;

	/**
	 * The duracao viagem pico ba.
	 */
	private String duracaoViagemPicoBA;

	/**
	 * <p>
	 * Field <code>edicaoRetroativaJistificativa</code>
	 * </p>
	 * <p>
	 * FBW-487 FBW-483
	 * </p>
	 */
	@Column( name = "edicao_retroativa_justificativa", length = 5000 )
	private String edicaoRetroativaJustificativa;

	/**
	 * The empresa.
	 */
	@NotNull
	@ManyToOne
	@NotAudited
	private Empresa empresa;

	/**
	 * The hierarquizacao.
	 */
	private String hierarquizacao;

	/**
	 * The id.
	 */
	@Id
	@GeneratedValue
	private Long id;

	/**
	 * The id linha vigencia antiga.
	 */
	private Long idLinhaVigenciaAntiga;

	/**
	 * The linha.
	 */
	@NotNull
	@NotAudited
	@ManyToOne
	private Linha linha;

	/**
	 * The linhas vigencia tipos de linha.
	 */
	@NotAudited
	@OneToMany( mappedBy = "linhaVigencia", cascade = CascadeType.ALL )
	private List<LinhaVigenciaTipoDeLinha> linhasVigenciaTiposDeLinha;

	/**
	 * The numero linha.
	 */
	private String numeroLinha;

	/**
	 * The observacao.
	 */
	private String observacao;

	/**
	 * The pico fim manha ab.
	 */
	private String picoFimManhaAB;

	/**
	 * The pico fim manha ba.
	 */
	private String picoFimManhaBA;

	/**
	 * The pico fim tarde ab.
	 */
	private String picoFimTardeAB;

	/**
	 * The pico fim tarde ba.
	 */
	private String picoFimTardeBA;

	/**
	 * The pico inicio manha ab.
	 */
	private String picoInicioManhaAB;

	/**
	 * The pico inicio manha ba.
	 */
	private String picoInicioManhaBA;

	/**
	 * The pico inicio tarde ab.
	 */
	private String picoInicioTardeAB;

	/**
	 * The pico inicio tarde ba.
	 */
	private String picoInicioTardeBA;

	/**
	 * The piso1 ab.
	 */
	private Double piso1AB;

	/**
	 * The piso1 ba.
	 */
	private Double piso1BA;

	/**
	 * The piso2 ab.
	 */
	private Double piso2AB;

	/**
	 * The piso2 ba.
	 */
	private Double piso2BA;

	/**
	 * The ponto final.
	 */
	@NotNull
	@Column( name = "ponto_final" )
	@Index( name = "idx_linhavigencia_pf" )
	private String pontoFinal;

	/**
	 * The ponto inicial.
	 */
	@NotNull
	@Column( name = "ponto_inicial" )
	@Index( name = "idx_linhavigencia_pi" )
	private String pontoInicial;

	/**
	 * The secoes.
	 */
	@NotAudited
	@OneToMany( mappedBy = "linhaVigencia", cascade = CascadeType.ALL )
	private List<Secao> secoes;

	/**
	 * The status.
	 */
	@NotNull
	private StatusLinha status;

	/**
	 * The tipo ligacao.
	 */
	private String tipoLigacao;

	/**
	 * The via.
	 */
	private String via;
}
