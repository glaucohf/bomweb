package br.com.fetranspor.bomweb.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.com.decatron.framework.dao.VRaptorHibernateDAO;
import br.com.decatron.framework.exception.DaoException;
import br.com.decatron.framework.provider.VRaptorProvider;
import br.com.decatron.framework.util.Check;
import br.com.fetranspor.bomweb.dto.FiltroBomPendenteDTO;
import br.com.fetranspor.bomweb.dto.FiltroRelatorioDTO;
import br.com.fetranspor.bomweb.dto.RelatorioDTO;
import br.com.fetranspor.bomweb.entity.Bom;
import br.com.fetranspor.bomweb.entity.BomLinha;
import br.com.fetranspor.bomweb.entity.BomSecao;
import br.com.fetranspor.bomweb.entity.Empresa;
import br.com.fetranspor.bomweb.entity.Justificativa;
import br.com.fetranspor.bomweb.entity.Linha;
import br.com.fetranspor.bomweb.entity.LinhaVigencia;
import br.com.fetranspor.bomweb.entity.Secao;
import br.com.fetranspor.bomweb.entity.Status;
import br.com.fetranspor.bomweb.entity.StatusFiltroBomPendente;
import br.com.fetranspor.bomweb.entity.StatusLinha;
import br.com.fetranspor.bomweb.entity.TipoDeVeiculo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

/**
 * The Class BomDAO.
 */
@Component
public class BomDAO
	extends VRaptorHibernateDAO<Bom>
{

	/**
	 * Instantiates a new bom dao.
	 *
	 * @param session
	 *            the session
	 * @param provider
	 *            the provider
	 * @param linhaDAO
	 *            the linha dao
	 */
	public BomDAO( final Session session, final VRaptorProvider provider, final LinhaDAO linhaDAO )
	{
		super( session, provider );
		this.linhaDAO = linhaDAO;
	}

	/**
	 * Busca bom linha anterior.
	 *
	 * @param linha
	 *            the linha
	 * @param tipoDeVeiculo
	 *            the tipo de veiculo
	 * @param mesReferencia
	 *            the mes referencia
	 * @return the bom linha
	 */
	public BomLinha buscaBomLinhaAnterior(
		final Linha linha,
		final TipoDeVeiculo tipoDeVeiculo,
		final String mesReferencia )
	{

		final int mesBom = Integer.valueOf( mesReferencia.split( "/" )[0] ) - 2;
		final int anoBom = Integer.valueOf( mesReferencia.split( "/" )[1] );

		final String mesAnterior = br.com.decatron.framework.dsl.date.DateDsl
			.date()
			.withMonth( mesBom )
			.withYear( anoBom )
			.format( "dd/MM" );

		final String hql = " from "
			+ " BomLinha"
			+ " where "
			+ " linhaVigencia in ( select id from LinhaVigencia where linha = :linha ) "
			+ " and "
			+ " tipoDeVeiculo = :tipoVeiculo "
			+ " and "
			+ " bom = ( from Bom where mesReferencia = :mes ) ";

		return ( BomLinha ) this.session
			.createQuery( hql )
			.setParameter( "linha", linha )
			.setParameter( "tipoVeiculo", tipoDeVeiculo )
			.setParameter( "mes", mesAnterior )
			.uniqueResult();

	}

	// -- BOM

	/**
	 * Busca bom secao anterior.
	 *
	 * @param secao
	 *            the secao
	 * @param tipoDeVeiculo
	 *            the tipo de veiculo
	 * @param mesReferencia
	 *            the mes referencia
	 * @return the bom secao
	 */
	public BomSecao buscaBomSecaoAnterior(
		final Secao secao,
		final TipoDeVeiculo tipoDeVeiculo,
		final String mesReferencia )
	{

		final int mesBom = Integer.valueOf( mesReferencia.split( "/" )[0] ) - 2;
		final int anoBom = Integer.valueOf( mesReferencia.split( "/" )[1] );

		final String mesAnterior = br.com.decatron.framework.dsl.date.DateDsl
			.date()
			.withMonth( mesBom )
			.withYear( anoBom )
			.format( "MM/yyyy" );

		final String hql = " from "
			+ " BomSecao"
			+ " where "
			+ " secao = :secao "
			+ " and "
			+ " bomLinha.bom.id in ( select id from Bom where mesReferencia = :mes ) "
			+ " and "
			+ " bomLinha.tipoDeVeiculo = :tipo";

		return ( BomSecao ) this.session
			.createQuery( hql )
			.setParameter( "secao", secao )
			.setParameter( "mes", mesAnterior )
			.setParameter( "tipo", tipoDeVeiculo )
			.uniqueResult();
	}

	/**
	 * Buscar bom linha.
	 *
	 * @param id
	 *            the id
	 * @return the bom linha
	 */
	public BomLinha buscarBomLinha( final Long id )
	{
		return ( BomLinha ) this.session
			.createCriteria( BomLinha.class )
			.add( Restrictions.eq( "id", id ) )
			.setFetchMode( "secoes", FetchMode.JOIN )
			.setResultTransformer( Criteria.DISTINCT_ROOT_ENTITY )
			.uniqueResult();
	}

	/**
	 * Buscar bom secao.
	 *
	 * @param id
	 *            the id
	 * @return the bom secao
	 */
	public BomSecao buscarBomSecao( final Long id )
	{
		return ( BomSecao ) this.session.get( BomSecao.class, id );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param secoes
	 * @return
	 */
	public List<BomSecao> buscarBomSecaoPorSecoes( final List<Secao> secoes )
	{
		return this.session.createCriteria( BomSecao.class ).add( Restrictions.in( "secao", secoes ) ).list();
	}

	/**
	 * Buscar bom secoes por secao.
	 *
	 * @param secao
	 *            the secao
	 * @return the list
	 */
	@SuppressWarnings( "unchecked" )
	public List<BomSecao> buscarBomSecoesPorSecao( final Secao secao )
	{
		return this.session.createCriteria( BomSecao.class ).add( Restrictions.eq( "secao", secao ) ).list();
	}

	/**
	 * Buscar justificativas por bom linha.
	 *
	 * @param bomLinha
	 *            the bom linha
	 * @return the list
	 */
	@SuppressWarnings( "unchecked" )
	public List<Justificativa> buscarJustificativasPorBomLinha( final BomLinha bomLinha )
	{
		return this.session
			.createCriteria( Justificativa.class )
			.add( Restrictions.eq( "bomLinha", bomLinha ) )
			.setResultTransformer( Criteria.DISTINCT_ROOT_ENTITY )
			.list();
	}

	// -- LINHA

	/**
	 * Buscar linhas vigencia do bom.
	 *
	 * @param bom
	 *            the bom
	 * @return the list
	 */
	@SuppressWarnings( "unchecked" )
	public List<LinhaVigencia> buscarLinhasVigenciaDoBom( final Bom bom )
	{
		final Criteria criteria = this.session.createCriteria( BomLinha.class ).add( Restrictions.eq( "bom", bom ) );

		criteria.setProjection( Projections.distinct( Projections.property( "linhaVigencia" ) ) );
		return criteria.list();
	}

	/**
	 * Buscar linhas vigencia por status bom linha.
	 *
	 * @param bom
	 *            the bom
	 * @param status
	 *            the status
	 * @return the list
	 */
	@SuppressWarnings( "unchecked" )
	public List<LinhaVigencia> buscarLinhasVigenciaPorStatusBomLinha( final Bom bom, final Status[] status )
	{
		final Criteria criteria = this.session.createCriteria( BomLinha.class ).add( Restrictions.eq( "bom", bom ) );
		if ( ( status != null ) && ( status.length > 0 ) )
		{
			criteria.add( Restrictions.in( "status", status ) );
		}

		criteria.setResultTransformer( Criteria.DISTINCT_ROOT_ENTITY );
		criteria.setProjection( Projections.property( "linhaVigencia" ) );
		return criteria.list();
	}

	/**
	 * Delete.
	 *
	 * @param bomLinha
	 *            the bom linha
	 */
	public void delete( final BomLinha bomLinha )
	{
		if ( bomLinha == null )
		{
			throw new IllegalArgumentException();
		}

		this.session.delete( bomLinha );
		this.session.flush();
	}

	/**
	 * Delete.
	 *
	 * @param justificativa
	 *            the justificativa
	 */
	public void delete( final Justificativa justificativa )
	{
		if ( justificativa == null )
		{
			throw new IllegalArgumentException();
		}

		this.session.delete( justificativa );
		this.session.flush();
	}

	/**
	 * Fechar.
	 *
	 * @param bom
	 *            the bom
	 * @throws DaoException
	 *             the dao exception
	 */
	public void fechar( final Bom bom )
		throws DaoException
	{

		String hql;

		// // Seção
		// hql =
		// "update BomSecao set status = :status where bomLinha.id in ( select id from BomLinha where bom.id = :bomId )";
		// session.createQuery(hql)
		// .setParameter("bomId", bom.getId())
		// .setParameter("status", Status.FECHADO)
		// .executeUpdate();
		//
		// Linha
		hql = "update BomLinha set status = :status where bom.id = :bomId";
		this.session
			.createQuery( hql )
			.setParameter( "bomId", bom.getId() )
			.setParameter( "status", Status.FECHADO )
			.executeUpdate();

		// Bom
		final Criteria criteriaBom = this.session.createCriteria( Bom.class );
		criteriaBom.add( Restrictions.eq( "id", bom.getId() ) );
		final Bom bomPersistido = ( Bom ) criteriaBom.uniqueResult();
		bomPersistido.setStatus( Status.FECHADO );
		bomPersistido.setDataFechamento( br.com.decatron.framework.dsl.date.DateDsl.date().toDate() );
		update( bomPersistido );
	}

	// -- SECAO

	/**
	 * Gets the bom secoes fora da lista.
	 *
	 * @param bom
	 *            the bom
	 * @param idsBomSecao
	 *            the ids bom secao
	 * @param status
	 *            the status
	 * @return the bom secoes fora da lista
	 */
	@SuppressWarnings( "unchecked" )
	public List<BomSecao> getBomSecoesForaDaLista( final Bom bom, final List<Long> idsBomSecao, final Status[] status )
	{
		final Criteria criteria = this.session.createCriteria( BomSecao.class );
		criteria.createAlias( "bomLinha", "bomLinha" );
		criteria.add( Restrictions.eq( "bomLinha.bom", bom ) );

		if ( ( idsBomSecao != null ) && ( idsBomSecao.size() > 0 ) )
		{
			criteria.add( Restrictions.not( Restrictions.in( "id", idsBomSecao ) ) );
		}

		if ( ( status != null ) && ( status.length > 0 ) )
		{
			criteria.add( Restrictions.in( "status", status ) );
		}

		criteria.setResultTransformer( Criteria.DISTINCT_ROOT_ENTITY );
		return criteria.list();
	}

	/**
	 * Gets the empresas com bom fechado.
	 *
	 * @return the empresas com bom fechado
	 */
	@SuppressWarnings( "unchecked" )
	public List<Empresa> getEmpresasComBomFechado()
	{
		String hql = " select distinct b.empresa from Bom b " + " where b.status = :status ";

		hql += " order by b.empresa.nome ";

		return this.session.createQuery( hql ).setParameter( "status", Status.FECHADO ).list();
	}

	/**
	 * Gets the empresas com bom fechado excluindo empresa.
	 *
	 * @param empresa
	 *            the empresa
	 * @return the empresas com bom fechado excluindo empresa
	 */
	@SuppressWarnings( "unchecked" )
	public List<Empresa> getEmpresasComBomFechadoExcluindoEmpresa( final Empresa empresa )
	{
		String hql = " select distinct b.empresa from Bom b "
			+ " where b.status = :status "
			+ " and b.empresa.id = "
			+ empresa.getId();

		hql += " order by b.empresa.nome ";

		return this.session.createQuery( hql ).setParameter( "status", Status.FECHADO ).list();
	}

	/**
	 * Gets the linhas do bom pelos ids empresas.
	 *
	 * @param idsEmpresas
	 *            the ids empresas
	 * @return the linhas do bom pelos ids empresas
	 */
	@SuppressWarnings( "unchecked" )
	public List<Linha> getLinhasDoBomPelosIdsEmpresas( final List<Long> idsEmpresas )
	{
		final String hql = " select distinct bl.linhaVigencia.linha from BomLinha bl "
			+ " join bl.bom b "
			+ " where b.status = "
			+ Status.FECHADO.ordinal()
			+ " and b.empresa.id in (:idsEmpresa) ";

		return this.session.createQuery( hql ).setParameterList( "idsEmpresa", idsEmpresas ).list();
	}

	// FIXME: Otimizar!
	/**
	 * Gets the linhas vigencia do bom pelos ids empresas.
	 *
	 * @param idsEmpresas
	 *            the ids empresas
	 * @return the linhas vigencia do bom pelos ids empresas
	 */
	public List<LinhaVigencia> getLinhasVigenciaDoBomPelosIdsEmpresas( final List<Long> idsEmpresas )
	{
		final List<Linha> linhas = getLinhasDoBomPelosIdsEmpresas( idsEmpresas );
		final List<Long> idsLinhas = new ArrayList<Long>();
		for ( final Linha linha : linhas )
		{
			idsLinhas.add( linha.getId() );
		}
		return getLinhasVigenciaDoBomPelosIdsLinhas( idsLinhas );
	}

	/**
	 * Gets the linhas vigencia do bom pelos ids linhas.
	 *
	 * @param idsLinhas
	 *            the ids linhas
	 * @return the linhas vigencia do bom pelos ids linhas
	 */
	@SuppressWarnings( "unchecked" )
	public List<LinhaVigencia> getLinhasVigenciaDoBomPelosIdsLinhas( final List<Long> idsLinhas )
	{

		if ( Check.isEmpty( idsLinhas ) )
		{
			return new ArrayList<LinhaVigencia>();
		}

		final String hql = " select lv from LinhaVigencia lv "
			+ " join lv.linha lin "
			+ " join fetch lv.empresa emp "
			+ " where ((lin.active = 1 and lv.dataInicio <= DATE_FORMAT(Now(), '%Y-%m-%d') "
			+ " and ( "
			+ "		((DATE_FORMAT(Now(), '%Y-%m-%d') <= lv.dataTermino) or (lv.dataTermino is null and lv.active = 1)) "
			+ // lv vigentes
			" 		or ( (DATE_FORMAT(Now(), '%Y-%m-%d') > lv.dataTermino) "
			+ "		      and lv.linha.id not in (select lin2.id from LinhaVigencia lv2 join lv2.linha lin2 where (DATE_FORMAT(Now(), '%Y-%m-%d') <= lv2.dataTermino) or lv2.dataTermino is null )  "
			+ // lvs passadas sem lv vigente
			" 			) "
			+ " ) ) or (lin.active = 0 and lv.idLinhaVigenciaAntiga = null))"
			+ " and lin.id in (:idsLinhas) ";
		return this.session.createQuery( hql ).setParameterList( "idsLinhas", idsLinhas ).list();
	}

	/**
	 * Gets the tipos veiculo de bom linha.
	 *
	 * @return the tipos veiculo de bom linha
	 */
	@SuppressWarnings( "unchecked" )
	public List<TipoDeVeiculo> getTiposVeiculoDeBomLinha()
	{
		final String hql = " select distinct bl.tipoDeVeiculo from  BomLinha bl "
			+ " join bl.bom b "
			+ " where b.status = "
			+ Status.FECHADO.ordinal();

		return this.session.createQuery( hql ).list();
	}

	/**
	 * List pendentes com filtro.
	 *
	 * @param filtro
	 *            the filtro
	 * @param prazoBomAberto
	 *            the prazo bom aberto
	 * @param prazoBomReaberto
	 *            the prazo bom reaberto
	 * @return the list
	 */
	@SuppressWarnings( "unchecked" )
	public List<Bom> listPendentesComFiltro(
		final FiltroBomPendenteDTO filtro,
		final Integer prazoBomAberto,
		final Integer prazoBomReaberto )
	{
		final Criteria criteria = this.session.createCriteria( Bom.class );
		criteria.add( Restrictions.ne( "status", Status.FECHADO ) );
		criteria.createAlias( "empresa", "emp" );
		if ( Check.isNotBlank( filtro.getEmpresa() ) )
		{
			criteria.add( Restrictions.eq( "emp.id", Long.parseLong( filtro.getEmpresa() ) ) );
		}
		if ( Check.isNotBlank( filtro.getDataInicial() ) )
		{
			criteria.add( Restrictions
				.sqlRestriction( "str_to_date(concat('01/',mes_referencia), '%d/%m/%Y') >= str_to_date(concat('01/','"
					+ filtro.getDataInicial()
					+ "'), '%d/%m/%Y') " ) );
		}
		if ( Check.isNotBlank( filtro.getDataFinal() ) )
		{
			criteria.add( Restrictions
				.sqlRestriction( "str_to_date(concat('01/',mes_referencia), '%d/%m/%Y') <= str_to_date(concat('01/','"
					+ filtro.getDataFinal()
					+ "'), '%d/%m/%Y') " ) );
		}
		if ( StatusFiltroBomPendente.Aberto.name().equals( filtro.status ) )
		{
			criteria.add( Restrictions.isNull( "dataReabertura" ) );
		}
		else if ( StatusFiltroBomPendente.Reaberto.name().equals( filtro.status ) )
		{
			criteria.add( Restrictions.isNotNull( "dataReabertura" ) );
		}

		criteria.add( Restrictions.or( Restrictions.and(
			Restrictions.isNull( "dataReabertura" ),
			Restrictions.sqlRestriction( "date_add(str_to_date(concat('01/', mes_referencia), '%d/%m/%Y'), INTERVAL "
				+ prazoBomAberto
				+ " DAY) < DATE(NOW())" ) ), Restrictions.and(
			Restrictions.isNotNull( "dataReabertura" ),
			Restrictions.sqlRestriction( "date_add(data_reabertura, INTERVAL "
				+ prazoBomReaberto
				+ " DAY) < DATE(NOW())" ) ) ) );
		criteria.add( Restrictions.like( "emp.active", true ) );
		criteria.setResultTransformer( Criteria.DISTINCT_ROOT_ENTITY );

		return criteria.list();
	}

	/**
	 * Monta relatorio com filtro.
	 *
	 * @param filtro
	 *            the filtro
	 * @param joinComSecao
	 *            the join com secao
	 * @return the list
	 */
	@SuppressWarnings( "unchecked" )
	public List<RelatorioDTO> montaRelatorioComFiltro( final FiltroRelatorioDTO filtro, final boolean joinComSecao )
	{
		final StringBuffer sql = new StringBuffer( "" );
		Query query;

		sql
			.append( "select b.mes_referencia as bomMesReferencia, b.responsavel as bomResponsavel, lv.ponto_inicial as lvPontoInicial, lv.ponto_final as lvPontoFinal, lv.codigo as lvCodigo, bl.capacidade as blCapacidade, bl.frota as blFrota, bl.viagens_extraordinarias_ab as blViagExtraAB, bl.viagens_extraordinarias_ba as blViagExtraBA " );
		sql
			.append( ", bl.viagens_ordinarias_ab as blViagOrdAB, bl.viagens_ordinarias_ba as blViagOrdBA, tv.descricao as tvDescricao, emp.nome as empNome " );
		sql
			.append( ", lv.piso1AB as lvPiso1AB, lv.piso2AB as lvPiso2AB, lv.piso1BA as lvPiso1BA, lv.piso2BA as lvPiso2BA, lv.picoInicioManhaAB as lvPicoInicioManhaAB, lv.picoInicioManhaBA as lvPicoInicioManhaBA, lv.picoFimManhaAB as lvPicoFimManhaAB, lv.picoFimManhaBA as lvPicoFimManhaBA " );
		sql
			.append( ", lv.picoInicioTardeAB as lvPicoInicioTardeAB, lv.picoInicioTardeBA as lvPicoInicioTardeBA, lv.picoFimTardeAB as lvPicoFimTardeAB, lv.picoFimTardeBA as lvPicoFimTardeBA, lv.duracaoViagemPicoAB as lvDuracaoViagPicoAB " );
		sql
			.append( ", lv.duracaoViagemPicoBA as lvDuracaoViagPicoBA, lv.duracaoViagemForaPicoAB as lvDuracaoViagForaPicoAB, lv.duracaoViagemForaPicoBA as lvDuracaoViagForaPicoBA, bl.status as blStatus " );
		sql
			.append( ", lv.hierarquizacao as lvHierarquizacao, lv.numeroLinha as lvNumeroLinha, lv.status as lvStatus, lv.tipoLigacao as lvTipoLigacao, lv.via as lvVia " );
		if ( joinComSecao )
		{
			sql
				.append( ", sec.codigo as codigoSecao, sec.pontoInicial as secPontoInicial, sec.pontoFinal as secPontoFinal, bsec.passageiro_ab as bsecPassAB, bsec.passageiro_anterior_ab bsecPassAntAB " );
			sql.append( ", bsec.passageiro_ba as bsecPassBA, bsec.passageiro_anterior_ba as bsecPassAntBA " );
			sql
				.append( ", bsec.status as bsecStatus, bsec.total_passageiros_ab as bsecTotPassAB, bsec.total_passageiros_ba as bsecTotPassBA, bsec.total_receita as bsecTotReceita " );
			sql
				.append( ", bsec.tarifa as bsecTarifa, bsec.tarifaAtual as bsecTarifaAtual, bsec.tarifaAnterior as bsecTarifaAnt " );
		}
		sql.append( " from bom_linha bl " );
		sql.append( "inner join tipos_veiculo tv on bl.tipo_veiculo_id = tv.id " );
		sql.append( "inner join linha_vigencia lv on lv.id = bl.linha_vigencia_id " );
		sql.append( "inner join bom b on b.id = bl.bom_id " );
		sql.append( "inner join empresas emp on b.empresa_id = emp.id " );
		if ( joinComSecao )
		{
			sql.append( "inner join bom_secao bsec on bsec.bom_linha_id = bl.id " );
			sql.append( "inner join secoes sec on bsec.secao_id = sec.id " );
		}

		final List<RelatorioDTO> relatoriosDto = new ArrayList<RelatorioDTO>();
		if ( Check.isNotNull( filtro )
			&& Check.isNotEmpty( filtro.getEmpresas() )
			&& Check.isNotEmpty( filtro.getLinhas() )
			&& Check.isNotEmpty( filtro.getTiposVeiculo() )
			&& Check.isNotNull( filtro.getDataInicial() )
			&& Check.isNotNull( filtro.getDataFinal() ) )
		{

			sql.append( " where emp.id in (" );
			for ( final String empresa : filtro.getEmpresas() )
			{
				sql.append( empresa );
				sql.append( "," );
			}
			sql.deleteCharAt( sql.length() - 1 );
			sql.append( ") " );

			final List<Linha> listaLinhas = this.linhaDAO.getLinhasDeLinhasVigencia( filtro.getLinhas() );
			sql.append( " and lv.linha_id in (" );
			for ( int i = 0; i < listaLinhas.size(); i++ )
			{
				sql.append( listaLinhas.get( i ).getId() );
				sql.append( "," );
			}
			sql.deleteCharAt( sql.length() - 1 );
			sql.append( ") " );

			sql.append( " and tv.id in (" );
			for ( final String tipoVeiculo : filtro.getTiposVeiculo() )
			{
				sql.append( tipoVeiculo );
				sql.append( "," );
			}
			sql.deleteCharAt( sql.length() - 1 );
			sql.append( ") " );

			sql.append( " and str_to_date(b.mes_referencia, '%m/%Y') >= "
				+ " str_to_date('"
				+ filtro.getDataInicial()
				+ "', '%m/%Y') " );
			sql.append( " and str_to_date(b.mes_referencia, '%m/%Y') <= "
				+ " str_to_date('"
				+ filtro.getDataFinal()
				+ "', '%m/%Y') " );

			// Apenas BOMs fechados.
			sql.append( " and b.status = " + Status.FECHADO.ordinal() );

			query = this.session.createSQLQuery( sql.toString() ).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP );

			final List<Object> recordSet = query.list();
			for ( final Object object : recordSet )
			{
				final RelatorioDTO relatorioDto = new RelatorioDTO();

				final Map<Object, Object> record = ( Map<Object, Object> ) object;

				relatorioDto.setMesReferencia( ( String ) record.get( "bomMesReferencia" ) );
				relatorioDto.setResponsavelBOM( ( String ) record.get( "bomResponsavel" ) );
				relatorioDto.setNomeLinha( ( record.get( "lvPontoInicial" ) != null )
					&& ( record.get( "lvPontoFinal" ) != null ) ? record.get( "lvPontoInicial" ).toString()
					+ " - "
					+ record.get( "lvPontoFinal" ).toString() : null );
				relatorioDto.setCodigoLinha( ( String ) record.get( "lvCodigo" ) );
				relatorioDto.setCapacidadeLinha( ( Integer ) record.get( "blCapacidade" ) );
				relatorioDto.setFrota( ( Integer ) record.get( "blFrota" ) );
				relatorioDto.setViagensExtraordinariasAB( ( Integer ) record.get( "blViagExtraAB" ) );
				relatorioDto.setViagensExtraordinariasBA( ( Integer ) record.get( "blViagExtraBA" ) );
				relatorioDto.setViagensOrdinariasAB( ( Integer ) record.get( "blViagOrdAB" ) );
				relatorioDto.setViagensOrdinariasBA( ( Integer ) record.get( "blViagOrdBA" ) );
				relatorioDto.setDescricaoTipoVeiculo( ( String ) record.get( "tvDescricao" ) );
				relatorioDto.setEmpresa( ( String ) record.get( "empNome" ) );
				relatorioDto.setPiso1AB( record.get( "lvPiso1AB" ) != null
					? ( ( Double ) record.get( "lvPiso1AB" ) )
					: null );
				relatorioDto.setPiso1BA( record.get( "lvPiso1BA" ) != null
					? ( ( Double ) record.get( "lvPiso1BA" ) )
					: null );
				relatorioDto.setPiso2AB( record.get( "lvPiso2AB" ) != null
					? ( ( Double ) record.get( "lvPiso2AB" ) )
					: null );
				relatorioDto.setPiso2BA( record.get( "lvPiso2BA" ) != null
					? ( ( Double ) record.get( "lvPiso2BA" ) )
					: null );
				relatorioDto.setPicoInicioManhaAB( ( String ) record.get( "lvPicoInicioManhaAB" ) );
				relatorioDto.setPicoInicioManhaBA( ( String ) record.get( "lvPicoInicioManhaBA" ) );
				relatorioDto.setPicoFimManhaAB( ( String ) record.get( "lvPicoFimManhaAB" ) );
				relatorioDto.setPicoFimManhaBA( ( String ) record.get( "lvPicoFimManhaBA" ) );
				relatorioDto.setPicoInicioTardeAB( ( String ) record.get( "lvPicoInicioTardeAB" ) );
				relatorioDto.setPicoInicioTardeBA( ( String ) record.get( "lvPicoInicioTardeBA" ) );
				relatorioDto.setPicoFimTardeAB( ( String ) record.get( "lvPicoFimTardeAB" ) );
				relatorioDto.setPicoFimTardeBA( ( String ) record.get( "lvPicoFimTardeBA" ) );

				// FBW-63
				final String duracaoViagPicoAB = ( String ) record.get( "lvDuracaoViagPicoAB" );
				relatorioDto.setDuracaoViagemPicoAB( ( duracaoViagPicoAB == null ) || ( duracaoViagPicoAB.equals( "" ) )
					? null
					: Integer.parseInt( duracaoViagPicoAB ) );

				final String duracaoViagPicoBA = ( String ) record.get( "lvDuracaoViagPicoBA" );
				relatorioDto.setDuracaoViagemPicoBA( ( duracaoViagPicoBA == null ) || ( duracaoViagPicoBA.equals( "" ) )
					? null
					: Integer.parseInt( duracaoViagPicoBA ) );

				final String duracaoViagForaPicoAB = ( String ) record.get( "lvDuracaoViagForaPicoAB" );
				relatorioDto.setDuracaoViagemForaPicoAB( ( duracaoViagForaPicoAB == null )
					|| ( duracaoViagForaPicoAB.equals( "" ) ) ? null : Integer.parseInt( duracaoViagForaPicoAB ) );

				final String duracaoViagForaPicoBA = ( String ) record.get( "lvDuracaoViagForaPicoBA" );
				relatorioDto.setDuracaoViagemForaPicoBA( ( duracaoViagForaPicoBA == null )
					|| ( duracaoViagForaPicoBA.equals( "" ) ) ? null : Integer.parseInt( duracaoViagForaPicoBA ) );

				final String linhaOperou = record.get( "blStatus" ) != null
					? record.get( "blStatus" ).toString()
					: null;

				if ( Check.isNotNull( linhaOperou )
					&& Integer.valueOf( linhaOperou ).equals( Status.INOPERANTE.ordinal() ) )
				{
					relatorioDto.setLinhaOperou( "Não" );
				}
				else
				{
					relatorioDto.setLinhaOperou( "Sim" );
				}

				relatorioDto.setHierarquizacao( ( String ) record.get( "lvHierarquizacao" ) );
				relatorioDto.setNumeroLinha( ( String ) record.get( "lvNumeroLinha" ) );
				relatorioDto.setTipoLigacao( ( String ) record.get( "lvTipoLigacao" ) );
				relatorioDto.setVia( ( String ) record.get( "lvVia" ) );
				final Integer statusLinha = record.get( "lvStatus" ) != null
					? ( Integer ) record.get( "lvStatus" )
					: null;
				relatorioDto.setStatusLinha( StatusLinha.getNome( statusLinha ) );

				if ( joinComSecao )
				{
					relatorioDto.setCodigoSecao( record.get( "codigoSecao" ).toString() );
					relatorioDto.setJuncaoSecao( ( record.get( "secPontoInicial" ) != null )
						&& ( record.get( "secPontoFinal" ) != null ) ? record.get( "secPontoInicial" ).toString()
						+ " - "
						+ record.get( "secPontoFinal" ).toString() : null );
					relatorioDto.setPassageirosAB( ( Integer ) record.get( "bsecPassAB" ) );
					relatorioDto.setPassageirosAnteriorAB( ( Integer ) record.get( "bsecPassAntAB" ) );
					relatorioDto.setPassageirosBA( ( Integer ) record.get( "bsecPassBA" ) );
					relatorioDto.setPassageirosAnteriorBA( ( Integer ) record.get( "bsecPassAntBA" ) );
					relatorioDto.setSecaoSemPassageiro( record.get( "bsecStatus" ) != null ? ( ( ( Integer ) record
						.get( "bsecStatus" ) ).equals( Status.INOPERANTE.ordinal() ) ? "Sim" : "Não" ) : null );
					relatorioDto.setTotalPassageirosAB( ( Integer ) record.get( "bsecTotPassAB" ) );
					relatorioDto.setTotalPassageirosBA( ( Integer ) record.get( "bsecTotPassBA" ) );
					relatorioDto.setTotalReceita( record.get( "bsecTotReceita" ) != null ? ( ( BigDecimal ) record
						.get( "bsecTotReceita" ) ).doubleValue() : null );

					final Double tarifaVigente = record.get( "bsecTarifa" ) != null ? ( ( BigDecimal ) record
						.get( "bsecTarifa" ) ).doubleValue() : null;
					final Double tarifaAtual = record.get( "bsecTarifaAtual" ) != null ? ( ( BigDecimal ) record
						.get( "bsecTarifaAtual" ) ).doubleValue() : null;
					relatorioDto.setTarifaAtual( tarifaVigente );
					relatorioDto.setTarifaAnterior( record.get( "bsecTarifaAnt" ) != null ? ( ( BigDecimal ) record
						.get( "bsecTarifaAnt" ) ).doubleValue() : null );
					relatorioDto.setTarifaPromocional( ( tarifaVigente != null )
						&& ( tarifaAtual != null )
						&& !tarifaAtual.equals( tarifaVigente ) ? "Sim" : "Não" );
				}

				relatoriosDto.add( relatorioDto );
			}
		}

		return relatoriosDto;
	}

	/**
	 * Pega linha vigencia.
	 *
	 * @param idLinhaVigencia
	 *            the id linha vigencia
	 * @return the linha vigencia
	 */
	public LinhaVigencia pegaLinhaVigencia( final Long idLinhaVigencia )
	{
		if ( idLinhaVigencia == null )
		{
			throw new IllegalArgumentException();
		}

		return ( LinhaVigencia ) this.session.get( LinhaVigencia.class, idLinhaVigencia );
	}

	/**
	 * Pesquisar linhas.
	 *
	 * @param bom
	 *            the bom
	 * @return the list
	 */
	@SuppressWarnings( "unchecked" )
	public List<BomLinha> pesquisarLinhas( final Bom bom )
	{
		return this.session
			.createCriteria( BomLinha.class )
			.add( Restrictions.eq( "bom", bom ) )
			.setResultTransformer( Criteria.DISTINCT_ROOT_ENTITY )
			.list();
	}

	/**
	 * Pesquisar linhas por bom e linha vigencia.
	 *
	 * @param bom
	 *            the bom
	 * @param linhaVigencia
	 *            the linha vigencia
	 * @return the list
	 */
	@SuppressWarnings( "unchecked" )
	public List<BomLinha> pesquisarLinhasPorBomELinhaVigencia( final Bom bom, final LinhaVigencia linhaVigencia )
	{
		if ( ( bom == null ) || ( linhaVigencia == null ) )
		{
			throw new IllegalArgumentException();
		}

		return this.session
			.createCriteria( BomLinha.class )
			.add( Restrictions.eq( "bom", bom ) )
			.add( Restrictions.eq( "linhaVigencia", linhaVigencia ) )
			.setResultTransformer( Criteria.DISTINCT_ROOT_ENTITY )
			.list();
	}

	/**
	 * Pesquisar linhas por status.
	 *
	 * @param bom
	 *            the bom
	 * @param status
	 *            the status
	 * @return the list
	 */
	@SuppressWarnings( "unchecked" )
	public List<BomLinha> pesquisarLinhasPorStatus( final Bom bom, final Status[] status )
	{
		final Criteria criteria = this.session.createCriteria( BomLinha.class ).add( Restrictions.eq( "bom", bom ) );
		if ( ( status != null ) && ( status.length > 0 ) )
		{
			criteria.add( Restrictions.in( "status", status ) );
		}

		criteria.setResultTransformer( Criteria.DISTINCT_ROOT_ENTITY );
		return criteria.list();
	}

	/**
	 * Pesquisar por.
	 *
	 * @param empresa
	 *            the empresa
	 * @return the list
	 */
	@SuppressWarnings( "unchecked" )
	public List<Bom> pesquisarPor( final Empresa empresa )
	{
		return this.session
			.createCriteria( Bom.class )
			.add( Restrictions.eq( "empresa", empresa ) )
			.addOrder( Order.asc( "mesReferencia" ) )
			.list();
	}

	/**
	 * Pesquisar por.
	 *
	 * @param empresa
	 *            the empresa
	 * @param mesReferencia
	 *            the mes referencia
	 * @return the bom
	 */
	public Bom pesquisarPor( final Empresa empresa, final String mesReferencia )
	{
		return ( Bom ) this.session
			.createCriteria( Bom.class )
			.add( Restrictions.eq( "empresa", empresa ) )
			.add( Restrictions.eq( "mesReferencia", mesReferencia ) )
			.uniqueResult();
	}

	/**
	 * Reabertura.
	 *
	 * @param bom
	 *            the bom
	 * @param justificativas
	 *            the justificativas
	 * @param linhas
	 *            the linhas
	 * @throws DaoException
	 *             the dao exception
	 */
	public void reabertura( final Bom bom, final List<Justificativa> justificativas, final List<BomLinha> linhas )
		throws DaoException
	{

		String hql;

		final Criteria crit = this.session.createCriteria( Bom.class );
		crit.add( Restrictions.eq( "id", bom.getId() ) );
		final Bom bomPersistido = ( Bom ) crit.uniqueResult();
		bomPersistido.setStatus( Status.REABERTO );
		bomPersistido.setDataReabertura( br.com.decatron.framework.dsl.date.DateDsl.date().toDate() );
		update( bomPersistido );

		for ( final BomLinha bomLinha : linhas )
		{
			if ( bomLinha.getId() != null )
			{
				hql = "update BomLinha set status = :status, ultimaJustificativa = :ultimaJustificativa where id = :id";
				this.session
					.createQuery( hql )
					.setParameter( "status", Status.PREENCHIDO )
					.setParameter( "id", bomLinha.getId() )
					.setParameter( "ultimaJustificativa", bomLinha.getUltimaJustificativa() )
					.executeUpdate();
			}
		}

		for ( final Justificativa justificativa : justificativas )
		{
			this.session.save( justificativa );
		}

	}

	/**
	 * Save.
	 *
	 * @param bomLinha
	 *            the bom linha
	 */
	public void save( final BomLinha bomLinha )
	{
		if ( bomLinha == null )
		{
			throw new IllegalArgumentException();
		}

		this.session.save( bomLinha );
		this.session.flush();
	}

	/**
	 * Save.
	 *
	 * @param bomSecao
	 *            the bom secao
	 */
	public void save( final BomSecao bomSecao )
	{
		this.session.save( bomSecao );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param bom
	 * @return
	 * @see br.com.decatron.framework.dao.AbstractDao#search(br.com.decatron.framework.entity.EntityBase)
	 */
	@SuppressWarnings( "unchecked" )
	@Override
	public List<Bom> search( final Bom bom )
	{
		final Criteria criteria = this.session.createCriteria( Bom.class );

		criteria.setFetchMode( "empresa", FetchMode.JOIN );
		criteria.add( Restrictions.or(
			Restrictions.isNotNull( "dataReabertura" ),
			Restrictions.eq( "status", Status.FECHADO ) ) );

		if ( Check.isNotNull( bom ) )
		{
			if ( Check.isNotBlank( bom.getMesReferencia() ) )
			{
				criteria.add( Restrictions.eq( "mesReferencia", bom.getMesReferencia() ) );
			}
			if ( Check.isNotNull( bom.getEmpresa() ) && Check.isNotNull( bom.getEmpresa().getId() ) )
			{
				criteria.add( Restrictions.eq( "empresa", bom.getEmpresa() ) );
			}
		}

		final List list = criteria.list();
		return list;
	}

	/**
	 * Update.
	 *
	 * @param bomLinha
	 *            the bom linha
	 */
	public void update( final BomLinha bomLinha )
	{
		this.session.update( bomLinha );
	}

	/**
	 * Update.
	 *
	 * @param bomSecao
	 *            the bom secao
	 */
	public void update( final BomSecao bomSecao )
	{
		this.session.update( bomSecao );
	}

	/**
	 * Update.
	 *
	 * @param justificativa
	 *            the justificativa
	 */
	public void update( final Justificativa justificativa )
	{
		if ( justificativa == null )
		{
			throw new IllegalArgumentException();
		}

		this.session.update( justificativa );
	}

	/**
	 * The linha dao.
	 */
	private final LinhaDAO linhaDAO;

}
