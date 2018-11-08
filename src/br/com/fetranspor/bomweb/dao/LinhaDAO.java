package br.com.fetranspor.bomweb.dao;

import static br.com.decatron.framework.dsl.date.DateDsl.date;

import br.com.caelum.vraptor.ioc.Component;
import br.com.decatron.framework.dao.VRaptorHibernateDAO;
import br.com.decatron.framework.exception.DaoException;
import br.com.decatron.framework.provider.VRaptorProvider;
import br.com.decatron.framework.util.Check;
import br.com.fetranspor.bomweb.entity.Empresa;
import br.com.fetranspor.bomweb.entity.Linha;
import br.com.fetranspor.bomweb.entity.LinhaVigencia;
import br.com.fetranspor.bomweb.entity.Perfil;
import br.com.fetranspor.bomweb.entity.Secao;
import br.com.fetranspor.bomweb.entity.TipoDeVeiculo;
import br.com.fetranspor.bomweb.entity.Usuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * The Class LinhaDAO.
 */
@Component
public class LinhaDAO
	extends VRaptorHibernateDAO<Linha>
{

	/**
	 * <p>
	 * Field <code>LOG</code>
	 * </p>
	 */
	private static final Log LOG = LogFactory.getLog( LinhaDAO.class );

	/**
	 * Instantiates a new linha dao.
	 *
	 * @param session
	 *            the session
	 * @param provider
	 *            the provider
	 */
	public LinhaDAO( final Session session, final VRaptorProvider provider )
	{
		super( session, provider );
	}

	/**
	 * Atualizar linha vigencia.
	 *
	 * @param linhaVigencia
	 *            the linha vigencia
	 */
	public void atualizarLinhaVigencia( final LinhaVigencia linhaVigencia )
	{
		this.session.update( linhaVigencia );
	}

	// ----------------------------------------------------------------------

	/**
	 * Busca linhas auto complete.
	 *
	 * @param term
	 *            the term
	 * @param idEmpresa
	 *            the id empresa
	 * @return the list
	 */
	public List<LinhaVigencia> buscaLinhasAutoComplete( final String term, final String idEmpresa )
	{
		String hql = "select distinct lv from LinhaVigencia lv "
			+ " inner join fetch lv.linha l "
			+ " where lv.id = (select min(lv2.id) "
			+ "				from LinhaVigencia lv2 "
			+ "				where ((DATE_FORMAT(Now(), '%Y-%m-%d') <= lv2.dataTermino) or lv2.dataTermino is null) "
			+ "				and lv2.linha.id = l.id "
			+ "				and lv2.empresa.id = :idEmpresa "
			+ "				and lv2.active = true) "
			+ " and lv.linha.id = l.id ";
		if ( Check.isNotBlank( term ) )
		{
			hql += " and (lv.pontoInicial like :term " + " or lv.pontoFinal like :term)";
		}

		hql += " order by lv.pontoInicial, lv.pontoFinal ";

		final Query q = this.session.createQuery( hql );
		q.setParameter( "idEmpresa", Long.parseLong( idEmpresa ) );

		if ( Check.isNotBlank( term ) )
		{
			q.setParameter( "term", "%" + term + "%" );
		}

		return q.list();
	}

	// ----------------------------------------------------------------------

	/**
	 * Busca linhas sem tarifa auto complete.
	 *
	 * @param term
	 *            the term
	 * @param idEmpresa
	 *            the id empresa
	 * @return the list
	 */
	public List<LinhaVigencia> buscaLinhasSemTarifaAutoComplete( final String term, final String idEmpresa )
	{
		String hql = "select distinct lv from LinhaVigencia lv "
			+ " inner join fetch lv.linha l "
			+ " inner join fetch lv.secoes sec "
			+ " where lv.id = (select min(lv2.id) "
			+ "				from LinhaVigencia lv2 "
			+ "				where ((DATE_FORMAT(Now(), '%Y-%m-%d') <= lv2.dataTermino) or lv2.dataTermino is null) "
			+ "				and lv2.linha.id = l.id "
			+ "				and lv2.empresa.id = :idEmpresa "
			+ "				and lv2.active = true) "
			+ " and lv.linha.id = l.id "
			+ " and sec.id not in ( "
			+ " 	select t1.secao from Tarifa t1 "
			+ "	where t1.active = 1 "
			+ "	and (t1.inicioVigencia > DATE_FORMAT(Now(), '%Y-%m-%d') "
			+ "		or (t1.inicioVigencia <= DATE_FORMAT(Now(), '%Y-%m-%d') and (t1.fimVigencia is null or t1.fimVigencia >= DATE_FORMAT(Now(), '%Y-%m-%d') ) ) "
			+ "	) "
			+ ") ";

		if ( Check.isNotBlank( term ) )
		{
			hql += " and (lv.pontoInicial like :term " + " or lv.pontoFinal like :term)";
		}

		hql += " order by lv.pontoInicial, lv.pontoFinal ";

		final Query q = this.session.createQuery( hql.toString() );
		q.setParameter( "idEmpresa", Long.parseLong( idEmpresa ) );

		if ( Check.isNotBlank( term ) )
		{
			q.setParameter( "term", "%" + term + "%" );
		}

		return q.list();
	}

	/**
	 * Busca linhas vigencia.
	 *
	 * @param empresa
	 *            the empresa
	 * @return the list
	 */
	@SuppressWarnings( "unchecked" )
	public List<LinhaVigencia> buscaLinhasVigencia( final Empresa empresa )
	{

		final String hql = "select lv from LinhaVigencia lv, Linha l "
			+ "where lv.id = (select min(lv2.id) "
			+ "				from LinhaVigencia lv2 "
			+ "				where (lv2.dataInicio <= DATE_FORMAT(Now(), '%Y-%m-%d')) "
			+ "				and ((DATE_FORMAT(Now(), '%Y-%m-%d') <= lv2.dataTermino) or lv2.dataTermino is null)"
			+ "				and lv2.linha.id = l.id "
			+ "				and lv2.empresa.id = :idEmpresa "
			+ "				and lv2.active = true)"
			+ "and lv.linha.id = l.id ";

		return this.session.createQuery( hql ).setParameter( "idEmpresa", empresa.getId() ).list();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param data
	 * @param linha
	 * @param codigo
	 * @return Retorna todas as linha_vigencia que contempla a data informada. Esse método é para
	 *         ser usado tento como referência a data de inicio de uma tarifa, onde as
	 *         linhas_vigencias retornadas são as que se relacionam com a data da tarifa informada.
	 */
	public List<LinhaVigencia> buscaLinhasVigenciaAPartirDe( final Date data, final Linha linha, final String codigo )
	{
		return this.session
			.createCriteria( LinhaVigencia.class )
			.add( Restrictions.eq( "linha", linha ) )
			.add( Restrictions.eq( "codigo", codigo ) )
			.add(
				Restrictions.or( Restrictions.and(
					Restrictions.le( "dataInicio", data ),
					Restrictions.ge( "dataTermino", data ) ), Restrictions.or(
					Restrictions.and( Restrictions.le( "dataInicio", data ), Restrictions.isNull( "dataTermino" ) ),
					Restrictions.ge( "dataInicio", data ) ) ) )
			.list();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param data
	 * @param idLinha
	 * @param codigo
	 * @return Retorna todas as LinhasVigencia que são posteriores à data informada.
	 */
	public List<LinhaVigencia> buscaLinhasVigenciaPosteriores( final Date data, final Long idLinha, final String codigo )
	{
		return this.session
			.createCriteria( LinhaVigencia.class )
			.add( Restrictions.eq( "linha.id", idLinha ) )
			.add( Restrictions.eq( "codigo", codigo ) )
			.add( Restrictions.gt( "dataInicio", data ) )
			.list();
	}

	/**
	 * Busca linhas vigentes ou futuras.
	 *
	 * @param empresa
	 *            the empresa
	 * @return the list
	 */
	@SuppressWarnings( "unchecked" )
	public List<LinhaVigencia> buscaLinhasVigentesOuFuturas( final Empresa empresa )
	{
		final String hql = "select lv from LinhaVigencia lv, Linha l "
			+ "where lv.id = (select min(lv2.id) "
			+ "				from LinhaVigencia lv2 "
			+ "				where ((DATE_FORMAT(Now(), '%Y-%m-%d') <= lv2.dataTermino) or lv2.dataTermino is null)"
			+ "				and lv2.linha.id = l.id "
			+ "				and lv2.empresa.id = :idEmpresa "
			+ "				and lv2.active = true)"
			+ "and lv.linha.id = l.id ";

		return this.session.createQuery( hql ).setParameter( "idEmpresa", empresa.getId() ).list();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param data
	 * @param idLinha
	 * @param codigoLinha
	 *            FBW-483
	 * @return
	 */
	public LinhaVigencia buscaLinhaVigenciaAtivaEm( final Date data, final Long idLinha, final String codigoLinha )
	{
		final Criteria criteria = this.session.createCriteria( LinhaVigencia.class );
		criteria.add( Restrictions.eq( "linha.id", idLinha ) );

		// FBW-483
		criteria.add( Restrictions.eq( "codigo", codigoLinha ) );

		criteria.add( Restrictions.le( "dataInicio", data ) );
		criteria.add( Restrictions.or( Restrictions.isNull( "dataTermino" ), Restrictions.ge( "dataTermino", data ) ) );
		final LinhaVigencia result = ( LinhaVigencia ) criteria.uniqueResult();
		return result;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param empresa
	 * @param codigo
	 * @return
	 */
	public Date buscarDataPrimeiraLinhaVigencia( final Empresa empresa, final String codigo )
	{
		return ( Date ) this.session
			.createCriteria( LinhaVigencia.class )
			.add( Restrictions.eq( "empresa", empresa ) )
			.add( Restrictions.eq( "codigo", codigo ) )
			.setProjection( Projections.min( "dataInicio" ) )
			.uniqueResult();
	}

	/**
	 * Buscar linha com vigencia futura.
	 *
	 * @param linha
	 *            the linha
	 * @return the linha
	 */
	public Linha buscarLinhaComVigenciaFutura( final Linha linha )
	{
		final String hql = "select new Linha("
			+ " lv.linha.id, "
			+ " lv.linha.active, "
			+ " lv, "
			+ " ( select count(lvx.id) from LinhaVigencia lvx where lvx.linha.id = l.id and lvx.active = true ) "
			+ " )"
			+ "from LinhaVigencia lv, Linha l "
			+ "where lv.id = ("
			+ "				select min(lv2.id) "
			+ "				from LinhaVigencia lv2 "
			+ "				where lv2.dataInicio > DATE_FORMAT(Now(), '%Y-%m-%d') "
			+ "				and lv2.linha.id = l.id "
			+ "				and lv2.active = true"
			+ "			   )"
			+ "and lv.linha.id = l.id ";
		return ( Linha ) this.session.createQuery( hql + " and lv.linha.id = " + linha.getId() ).uniqueResult();
	}

	/**
	 * Buscar linhas vigencia.
	 *
	 * @param idsLinha
	 *            the ids linha
	 * @return the list
	 */
	@SuppressWarnings( "unchecked" )
	public List<LinhaVigencia> buscarLinhasVigencia( final List<Long> idsLinha )
	{
		return this.session
			.createCriteria( LinhaVigencia.class )
			.add( Restrictions.in( "id", idsLinha ) )
			.setFetchMode( "linha", FetchMode.JOIN )
			.list();
	}

	/**
	 * Buscar linhas vigentes da empresa no mes ano.
	 *
	 * @param empresa
	 *            the empresa
	 * @param mesAno
	 *            the mes ano
	 * @return the list
	 */
	@SuppressWarnings( "unchecked" )
	public List<LinhaVigencia> buscarLinhasVigentesDaEmpresaNoMesAno( final Empresa empresa, final String mesAno )
	{

		final int mesBom = Integer.valueOf( mesAno.split( "/" )[0] ) - 1;
		final int anoBom = Integer.valueOf( mesAno.split( "/" )[1] );

		final Date dataInicio = date().withMonth( mesBom ).withYear( anoBom ).lastDayOfMonth().toDate();
		final Date dataFim = date().withMonth( mesBom ).withYear( anoBom ).firstDayOfMonth().toDate();

		final String hql = "select lv from LinhaVigencia lv "
			+ "where lv.id in (select max(lv2.id) from LinhaVigencia lv2 "
			+ "	where (lv2.dataInicio <= :dataInicio) "
			+ "	and (lv2.dataTermino >= :dataFim or lv2.dataTermino is null) "
			+ "	and lv2.empresa.id = :idEmpresa "
			+ "	and lv2.active = true "
			+ "	group by lv2.linha.id)";

		return this.session
			.createQuery( hql )
			.setParameter( "idEmpresa", empresa.getId() )
			.setParameter( "dataInicio", dataInicio )
			.setParameter( "dataFim", dataFim )
			.list();
	}

	/**
	 * Buscar linha vigencia.
	 *
	 * @param linha
	 *            the linha
	 * @return the linha vigencia
	 */
	public LinhaVigencia buscarLinhaVigencia( final LinhaVigencia linha )
	{
		return ( LinhaVigencia ) this.session
			.createCriteria( LinhaVigencia.class )
			.add( Restrictions.eq( "id", linha.getId() ) )
			.uniqueResult();
	}

	/**
	 * Buscar linha vigencia de uma secao.
	 *
	 * @param secao
	 *            the secao
	 * @return the linha vigencia
	 */
	public LinhaVigencia buscarLinhaVigenciaDeUmaSecao( final Secao secao )
	{
		final String sql = "select lv from LinhaVigencia lv "
			+ "join lv.secoes sec "
			+ "where sec = :secao "
			+ "and lv.active = true ";
		return ( LinhaVigencia ) this.session.createQuery( sql ).setParameter( "secao", secao ).uniqueResult();
	}

	/**
	 * Buscar linha vigencia futura.
	 *
	 * @param linha
	 *            the linha
	 * @return the linha vigencia
	 */
	public LinhaVigencia buscarLinhaVigenciaFutura( final Linha linha )
	{
		return ( LinhaVigencia ) this.session
			.createCriteria( LinhaVigencia.class )
			.add( Restrictions.eq( "active", Boolean.TRUE ) )
			.add( Restrictions.eq( "linha", linha ) )
			.add( Restrictions.gt( "dataInicio", new Date() ) )
			.uniqueResult();
	}

	/**
	 * <p>
	 * https://intranet.mindsatwork.com.br/jira/browse/FBW-153 Cancela a seção de uma linha
	 * </p>
	 *
	 * @author marcio.dias
	 * @param secao
	 *            the secao
	 */
	public void cancelaSecao( final Secao secao )
	{
		this.session.update( secao );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param entity
	 * @throws DaoException
	 * @see br.com.decatron.framework.dao.VRaptorHibernateDAO#delete(br.com.decatron.framework.entity.EntityBase)
	 */
	@Override
	public void delete( Linha entity )
		throws DaoException
	{
		entity = get( entity.getId() );
		final List<LinhaVigencia> vigencias = pesquisarLinhaVigencia( entity );
		for ( final LinhaVigencia linhaVigencia : vigencias )
		{
			for ( final Secao secao : linhaVigencia.getSecoes() )
			{
				secao.setActive( false );
				this.session.update( secao );
			}
			linhaVigencia.setActive( false );
			this.session.update( linhaVigencia );
		}
		entity.setActive( false );
		super.update( entity );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param linhaVigencia
	 * @throws DaoException
	 */
	public void deleteLinhaVigencia( final LinhaVigencia linhaVigencia )
		throws DaoException
	{
		this.session.delete( linhaVigencia );
	}

	/**
	 * Existe linha ativa usando tipo linha.
	 *
	 * @param idTipoLinha
	 *            the id tipo linha
	 * @return true, if successful
	 */
	public boolean existeLinhaAtivaUsandoTipoLinha( final Long idTipoLinha )
	{

		final StringBuffer sql = new StringBuffer();

		if ( Check.isNotBlank( idTipoLinha.toString() ) )
		{
			sql.append( "select 1 from linha_vigencia lin " );
			sql.append( "inner join linha_vigencia_tipos_linha ltl on lin.id = ltl.linhaVigencia_id " );
			sql.append( "where lin.active = 1 " );
			sql.append( "and ltl.tipoDeLinha_id = :idTipoLinha " );

			final Query q = this.session.createSQLQuery( sql.toString() );
			q.setParameter( "idTipoLinha", idTipoLinha );
			return q.list().isEmpty() ? false : true;
		}
		return false;
	}

	/**
	 * Existe linha ativa usando tipo veiculo.
	 *
	 * @param idTipoVeiculo
	 *            the id tipo veiculo
	 * @return true, if successful
	 */
	public boolean existeLinhaAtivaUsandoTipoVeiculo( final Long idTipoVeiculo )
	{

		final StringBuffer sql = new StringBuffer();

		if ( Check.isNotBlank( idTipoVeiculo.toString() ) )
		{
			sql.append( "select 1 from linha_vigencia lin " );
			sql.append( "inner join linha_vigencia_tipos_linha tl on lin.id = tl.linhavigencia_id " );
			sql
				.append( "inner join linha_vigencia_tipos_linha_tipos_linha_tipos_veiculo tv on tv.linha_tipo_linha_id = tl.tipoDeLinha_id " );
			sql.append( "where lin.active = 1 " );
			sql.append( "and tv.tipo_linha_tipo_veiculo_id = :idTipoVeiculo " );

			final Query q = this.session.createSQLQuery( sql.toString() );
			q.setParameter( "idTipoVeiculo", idTipoVeiculo );
			return q.list().isEmpty() ? false : true;
		}
		return false;
	}

	/**
	 * <p>
	 * FBW-135 Verifica se existe linhaVg pelo codigo
	 * </p>
	 * .
	 *
	 * @author marcio.dias
	 * @param codigo
	 *            the codigo
	 * @return true, if successful
	 */
	public boolean existeLinhaPorCodigo( final String codigo )
	{

		final StringBuffer sql = new StringBuffer();

		sql.append( "select 1 from linha_vigencia lin " );
		sql.append( "where codigo = :codigo " );

		final Query q = this.session.createSQLQuery( sql.toString() );
		q.setParameter( "codigo", codigo );
		return q.list().isEmpty() ? false : true;

	}
	
	
	/**
	 * <p>
	 * FBW-135 Verifica se existe linhaVg pelo codigo
	 * https://trello.com/c/Kc9eTPv5 - A linha tem que estar sem data de termino de vigência
	 * </p>
	 * .
	 *
	 * @author marcio.dias
	 * @param codigo
	 *            the codigo
	 * @return true, if successful
	 */
	public boolean existeLinhaPorCodigoAtiva( final String codigo )
	{

		final StringBuffer sql = new StringBuffer();

		sql.append( "select 1 from linha_vigencia lin " );
		sql.append( "where data_termino is null and codigo = :codigo " );

		final Query q = this.session.createSQLQuery( sql.toString() );
		q.setParameter( "codigo", codigo );
		return q.list().isEmpty() ? false : true;

	}

	/**
	 * Find secaoby code.
	 *
	 * @param code
	 *            the code
	 * @return the secao
	 */
	public Secao findSecaobyCode( final String code )
	{

		final Criteria criteria = this.session.createCriteria( Secao.class );
		criteria.add( Restrictions.eq( "active", true ) );

		if ( Check.isNotNull( code ) && Check.isNotBlank( code ) )
		{

			criteria.add( Restrictions.eq( "codigo", code ) );

		}

		return ( Secao ) criteria.uniqueResult();

	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param id
	 * @return
	 * @see br.com.decatron.framework.dao.VRaptorHibernateDAO#get(java.io.Serializable)
	 */
	@Override
	public Linha get( final Serializable id )
	{
		// TODO PrepareStatment
		return ( Linha ) this.session
			.createQuery( this.hqlComLinhasComDataFutura + " and lv.linha.id = " + id )
			.uniqueResult();
	}

	/**
	 * Gets the id linhas com linha futura.
	 *
	 * @return the id linhas com linha futura
	 */
	private List<Integer> getIdLinhasComLinhaFutura()
	{
		final Criteria criteria = this.session
			.createCriteria( LinhaVigencia.class )
			.add( Restrictions.ge( "dataTermino", Calendar.getInstance().getTime() ) )
			.add( Restrictions.ge( "active", true ) )
			.setProjection( Projections.property( "linha.id" ) );
		return criteria.list();
	}

	/**
	 * Gets the linhas de linhas vigencia.
	 *
	 * @param idsLinhasVigenciasComoString
	 *            the ids linhas vigencias como string
	 * @return the linhas de linhas vigencia
	 */
	@SuppressWarnings( "unchecked" )
	public List<Linha> getLinhasDeLinhasVigencia( final List<String> idsLinhasVigenciasComoString )
	{
		final List<Long> idsLinhasVigencias = new LinkedList<Long>();

		for ( final String idLinhaVigenciaComoString : idsLinhasVigenciasComoString )
		{
			idsLinhasVigencias.add( new Long( idLinhaVigenciaComoString ) );
		}

		final List<LinhaVigencia> linhasVigencias = this.session
			.createCriteria( LinhaVigencia.class )
			.add( Restrictions.in( "id", idsLinhasVigencias ) )
			.list();

		final Set<Linha> linhas = new HashSet<Linha>();
		if ( Check.isNotNull( linhasVigencias ) )
		{
			for ( final LinhaVigencia linhaVigencia : linhasVigencias )
			{
				linhas.add( linhaVigencia.getLinha() );
			}
		}

		return new ArrayList<Linha>( linhas );
	}

	/**
	 * Gets the linha vigencia by code.
	 *
	 * @param code
	 *            the code
	 * @return the linha vigencia by code
	 */
	public LinhaVigencia getLinhaVigenciaAtivaByCode( final String code )
	{
		try
		{
			final Date hoje = new Date();
			final LinhaVigencia uniqueResult = ( LinhaVigencia ) this.session
				.createCriteria( LinhaVigencia.class )
				.add( Restrictions.eq( "codigo", code ) )
				.add( Restrictions.le( "dataInicio", hoje ) )
				.add( Restrictions.or( Restrictions.isNull( "dataTermino" ), Restrictions.gt( "dataTermino", hoje ) ) )
				.uniqueResult();
			return uniqueResult;

		}// try
		catch ( final Exception e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if

			return null;
		}// catch
	}

	/**
	 * Gets the linha vigencia by code data termino.
	 *
	 * @param code
	 *            the code
	 * @param dataTermino
	 *            the data termino
	 * @return the linha vigencia by code data termino
	 */
	public LinhaVigencia getLinhaVigenciaByCodeDataTermino( final String code, final Date dataTermino )
	{
		return ( LinhaVigencia ) this.session
			.createCriteria( LinhaVigencia.class )
			.add( Restrictions.eq( "codigo", code ) )
			.add( Restrictions.eq( "dataTermino", dataTermino ) )
			.uniqueResult();
	}

	// FBW-135
	/**
	 * Gets the linha vigencia by code termino.
	 *
	 * @param code
	 *            the code
	 * @return the linha vigencia by code termino
	 */
	public LinhaVigencia getLinhaVigenciaByCodeTermino( final String code )
	{
		return ( LinhaVigencia ) this.session
			.createCriteria( LinhaVigencia.class )
			.add( Restrictions.eq( "codigo", code ) )
			.add( Restrictions.isNull( "dataTermino" ) )
			.uniqueResult();
	}

	/**
	 * Historico.
	 *
	 * @param entity
	 *            the entity
	 * @return the list
	 */
	public List<LinhaVigencia> historico( final Linha entity )
	{
		/*
		 * final Criteria criteria = this.session.createCriteria( LinhaVigencia.class ); if (
		 * Check.isNotNull( entity ) ) { criteria.add( Restrictions.eq( "linha.id", entity.getId() )
		 * ); } return criteria.list();
		 */

		// FBW-155 | o comentário acima é o cod antigo.

		final StringBuffer sql = new StringBuffer( this.hqlComLinhasComDataFuturaOtimizadoParaHistorico );

		sql.append( " and lv.codigo = :codigo " );
		sql.append( " and lv.linha =  :linha " );
		final Query q = this.session.createQuery( sql.toString() );
		q.setParameter( "codigo", entity.getLinhaVigente().getCodigo() );
		q.setParameter( "linha", entity.getLinhaVigente().getLinha() );
		final List list = q.list();
		return list;

	}

	/**
	 * Linha ja existe.
	 *
	 * @param codigo
	 *            the codigo
	 * @param idEmpresa
	 *            the id empresa
	 * @return true, if successful
	 */
	public boolean linhaJaExiste( final String codigo, final Long idEmpresa )
	{

		final StringBuffer sql = new StringBuffer( this.hqlComLinhasComDataFutura );

		if ( Check.isNotBlank( codigo ) )
		{
			sql.append( "and lv.codigo = :codigo " );
			sql.append( "and lv.empresa.id = :idEmpresa " );
			final Query q = this.session.createQuery( sql.toString() );
			q.setParameter( "codigo", codigo );
			q.setParameter( "idEmpresa", idEmpresa );
			return q.list().isEmpty() ? false : true;
		}
		return false;

	}

	/**
	 * Linha ja exite.
	 *
	 * @param codigo
	 *            the codigo
	 * @param idLinha
	 *            the id linha
	 * @param idEmpresa
	 *            the id empresa
	 * @return true, if successful
	 */
	public boolean linhaJaExite( final String codigo, final Long idLinha, final Long idEmpresa )
	{

		final StringBuffer sql = new StringBuffer( this.hqlComLinhasComDataFutura );

		if ( Check.isNotBlank( codigo ) )
		{
			sql.append( "and lv.codigo = :codigo " );
			sql.append( "and lv.linha.id != :idLinha " );
			sql.append( "and lv.empresa.id = :idEmpresa " );
			final Query q = this.session.createQuery( sql.toString() );
			q.setParameter( "codigo", codigo );
			q.setParameter( "idLinha", idLinha );
			q.setParameter( "idEmpresa", idEmpresa );
			return q.list().isEmpty() ? false : true;
		}
		return false;
	}

	/**
	 * Listar secoes.
	 *
	 * @param entity
	 *            the entity
	 * @return the list
	 */
	@SuppressWarnings( "unchecked" )
	public List<Secao> listarSecoes( final Linha entity )
	{
		final Criteria criteria = this.session.createCriteria( Secao.class ).add(
			Restrictions.eq( "active", Boolean.TRUE ) );
		if ( Check.isNotNull( entity )
			&& Check.isNotNull( entity.getLinhaVigente() )
			&& Check.isNotNull( entity.getLinhaVigente().getId() ) )
		{
			criteria.add( Restrictions.eq( "linhaVigencia", entity.getLinhaVigente() ) );
		}
		return criteria.list();
	}

	/**
	 * Listar secoes sem secao00.
	 *
	 * @param entity
	 *            the entity
	 * @return the list
	 */
	@SuppressWarnings( "unchecked" )
	public List<Secao> listarSecoesSemSecao00( final Linha entity )
	{
		final Criteria criteria = this.session
			.createCriteria( Secao.class )
			.add( Restrictions.eq( "active", Boolean.TRUE ) )
			.add( Restrictions.ne( "codigo", Secao.COD_SECAO_OBRIGATORIA ) );
		if ( Check.isNotNull( entity )
			&& Check.isNotNull( entity.getLinhaVigente() )
			&& Check.isNotNull( entity.getLinhaVigente().getId() ) )
		{
			criteria.add( Restrictions.eq( "linhaVigencia", entity.getLinhaVigente() ) );
		}
		criteria.addOrder( Order.asc( "codigo" ) );
		return criteria.list();
	}

	/**
	 * Pesquisar linhas.
	 *
	 * @param empresa
	 *            the empresa
	 * @return the list
	 */
	@SuppressWarnings( "unchecked" )
	public List<Linha> pesquisarLinhas( final Empresa empresa )
	{
		final StringBuffer sql = new StringBuffer( this.hql );
		sql.append( " and lv.empresa = :empresa " );

		return this.session.createQuery( sql.toString() ).setParameter( "empresa", empresa ).list();
	}

	/**
	 * Pesquisar linha vigencia.
	 *
	 * @param linha
	 *            the linha
	 * @return the list
	 */
	@SuppressWarnings( "unchecked" )
	private List<LinhaVigencia> pesquisarLinhaVigencia( final Linha linha )
	{
		return this.session.createCriteria( LinhaVigencia.class ).add( Restrictions.eq( "linha", linha ) ).list();
	}

	/**
	 * Pesquisar secoes por linha vigente secoes.
	 *
	 * @param entity
	 *            the entity
	 * @return the list
	 */
	@SuppressWarnings( "unchecked" )
	public List<Secao> pesquisarSecoesPorLinhaVigenteSecoes( final LinhaVigencia entity )
	{
		return this.session
			.createCriteria( Secao.class )
			.add( Restrictions.eq( "active", Boolean.TRUE ) )
			.add( Restrictions.eq( "linhaVigencia", entity ) )
			.addOrder( Order.asc( "codigo" ) )
			.list();
	}

	/**
	 * Pesquisar tipos veiculo.
	 *
	 * @param linhaVigenciaId
	 *            the linha vigencia id
	 * @return the list
	 */
	public List<TipoDeVeiculo> pesquisarTiposVeiculo( final String linhaVigenciaId )
	{
		final String hql = "select distinct tv from LinhaVigencia lv "
			+ " join lv.linhasVigenciaTiposDeLinha lvtl "
			+ " join lvtl.tiposLinhaTiposVeiculo tltv "
			+ " join tltv.tipoDeVeiculo tv "
			+ " where lv.id = :id";
		return this.session.createQuery( hql ).setParameter( "id", Long.valueOf( linhaVigenciaId ) ).list();
	}

	/**
	 * Removes the secao.
	 *
	 * @param s
	 *            the s
	 * @return true, if successful
	 */
	public boolean removeSecao( final Secao s )
	{
		s.setActive( false );
		this.session.update( s );
		return true;
	}

	/**
	 * Salvar linha vigencia.
	 *
	 * @param linhaVigencia
	 *            the linha vigencia
	 */
	public void salvarLinhaVigencia( final LinhaVigencia linhaVigencia )
	{
		this.session.save( linhaVigencia );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param entity
	 * @return
	 * @see br.com.decatron.framework.dao.AbstractDao#search(br.com.decatron.framework.entity.EntityBase)
	 */
	@SuppressWarnings( "unchecked" )
	@Override
	public List<Linha> search( final Linha entity )
	{

		final Perfil perfil = ( Perfil ) getRoles().iterator().next();
		final Usuario usuario = ( Usuario ) getUser();

		final StringBuffer sql = new StringBuffer( this.hqlComLinhasComDataFuturaOtimizado );

		if ( ( Check.isNotNull( entity ) ) && ( Check.isNotNull( entity.getLinhaVigente() ) ) )
		{

			if ( Check.isNotBlank( entity.getLinhaVigente().getCodigo() ) )
			{
				sql.append( "and lv.codigo like :codigo " );
			}

			if ( Check.isNotBlank( entity.getLinhaVigente().getPontoInicial() ) )
			{
				sql.append( "and (lv.pontoInicial like :inicial)" );
			}

			if ( Check.isNotBlank( entity.getLinhaVigente().getPontoFinal() ) )
			{
				sql.append( "and (lv.pontoFinal like :final)" );
			}
		}

		if ( perfil.isEmpresa() )
		{
			sql.append( "and lv.empresa.id = :empresa " );

		}
		else if ( Check.isNotNull( entity ) )
		{

			if ( Check.isNotNull( entity.getLinhaVigente().getEmpresa().getId() ) )
			{
				sql.append( "and lv.empresa.id = :empresa " );
			}
		}

		final Query query = this.session.createQuery( sql.toString() );

		if ( ( Check.isNotNull( entity ) ) && ( Check.isNotNull( entity.getLinhaVigente() ) ) )
		{

			if ( Check.isNotBlank( entity.getLinhaVigente().getCodigo() ) )
			{
				query.setParameter( "codigo", "%" + entity.getLinhaVigente().getCodigo().trim() + "%" );
			}

			if ( Check.isNotBlank( entity.getLinhaVigente().getPontoInicial() ) )
			{
				query.setParameter( "inicial", "%" + entity.getLinhaVigente().getPontoInicial().trim() + "%" );
			}

			if ( Check.isNotBlank( entity.getLinhaVigente().getPontoFinal() ) )
			{
				query.setParameter( "final", "%" + entity.getLinhaVigente().getPontoFinal().trim() + "%" );
			}
		}

		if ( perfil.isEmpresa() )
		{

			query.setParameter( "empresa", usuario.getEmpresa().getId() );

		}
		else if ( Check.isNotNull( entity ) )
		{

			if ( Check.isNotNull( entity.getLinhaVigente().getEmpresa().getId() ) )
			{
				query.setParameter( "empresa", entity.getLinhaVigente().getEmpresa().getId() );
			}
		}

		final List<LinhaVigencia> linhasVigentesEFuturas = query.list();

		final Set<Integer> idsLinhasComLinhaFutura = new HashSet<Integer>();
		idsLinhasComLinhaFutura.addAll( getIdLinhasComLinhaFutura() );

		final List<Linha> linhas = new ArrayList<Linha>();

		for ( final LinhaVigencia linhaVigencia : linhasVigentesEFuturas )
		{
			final Linha linha = new Linha(
				linhaVigencia.getLinha().getId(),
				linhaVigencia.getLinha().isActive(),
				linhaVigencia,
				idsLinhasComLinhaFutura.contains( linhaVigencia.getLinha().getId() ) );

			linhas.add( linha );
		}

		return linhas;
	}

	/**
	 * Secao ja existe.
	 *
	 * @param codigoSecao
	 *            the codigo secao
	 * @param idLinha
	 *            the id linha
	 * @param idSecao
	 *            the id secao
	 * @return true, if successful
	 */
	public boolean secaoJaExiste( final String codigoSecao, final Long idLinha, final Long idSecao )
	{
		final Criteria criteria = this.session.createCriteria( Secao.class ).add(
			Restrictions.eq( "active", Boolean.TRUE ) );

		if ( Check.isNotNull( codigoSecao ) )
		{
			criteria.add( Restrictions.eq( "codigo", codigoSecao ) );
		}

		if ( Check.isNotNull( idLinha ) )
		{
			criteria.add( Restrictions.eq( "linhaVigencia.id", idLinha ) );
		}

		if ( Check.isNotNull( idSecao ) )
		{
			criteria.add( Restrictions.ne( "id", idSecao ) );
		}

		return criteria.list().isEmpty() ? false : true;

	}

	/**
	 * Secao ja existe.
	 *
	 * @param codigoSecao
	 *            the codigo secao
	 * @param codigoLinha
	 *            the codigo linha
	 * @param codigoEmp
	 *            the codigo emp
	 * @return true, if successful
	 */
	public boolean secaoJaExiste( final String codigoSecao, final String codigoLinha, final String codigoEmp )
	{

		final StringBuffer sql = new StringBuffer();

		sql.append( "select lv from LinhaVigencia lv " );
		sql.append( "inner join lv.secoes sec " );
		sql.append( "inner join lv.empresa emp " );
		sql.append( "where sec.codigo = :codigoSecao " );
		sql.append( "and lv.codigo = :codigoLinha " );
		sql.append( "and emp.codigo = :codigoEmp " );
		sql.append( "and sec.active = 1 " );
		sql.append( "and lv.active = 1 " );

		final Query q = this.session.createQuery( sql.toString() );
		q.setParameter( "codigoSecao", codigoSecao );
		q.setParameter( "codigoLinha", codigoLinha );
		q.setParameter( "codigoEmp", codigoEmp );

		return q.list().isEmpty() ? false : true;
	}

	/**
	 * The hql.
	 */
	private final String hql = "select new Linha("
		+ " lv.linha.id, "
		+ " lv.linha.active, "
		+ " lv, "
		+ " ( select count(lvx.id) from LinhaVigencia lvx where lvx.linha.id = l.id and lvx.active = true and ((DATE_FORMAT(Now(), '%Y-%m-%d') <= lvx.dataTermino) or lvx.dataTermino is null)) "
		+ " )"
		+ "from LinhaVigencia lv, Linha l "
		+ "where lv.id = ("
		+ " 				select min(lv2.id) "
		+ "				from LinhaVigencia lv2 "
		+ "				where (lv2.dataInicio <= DATE_FORMAT(Now(), '%Y-%m-%d')) "
		+ "				and ((DATE_FORMAT(Now(), '%Y-%m-%d') <= lv2.dataTermino) or lv2.dataTermino is null) "
		+ "				and lv2.linha.id = l.id"
		+ "				and lv2.active = true "
		+ " 				) "
		+ "and lv.linha.id = l.id ";

	/**
	 * The hql com linhas com data futura.
	 */
	private final String hqlComLinhasComDataFutura = "select new Linha("
		+ " lv.linha.id, "
		+ " lv.linha.active, "
		+ " lv, "
		+ " ( select count(lvx.id) from LinhaVigencia lvx where lvx.linha.id = l.id and lvx.active = true and ((DATE_FORMAT(Now(), '%Y-%m-%d') <= lvx.dataTermino) or lvx.dataTermino is null) ) "
		+ " )"
		+ "from LinhaVigencia lv, Linha l "
		+ "where lv.id = ("
		+ " 				select min(lv2.id) "
		+ "				from LinhaVigencia lv2 "
		+ "				where ((DATE_FORMAT(Now(), '%Y-%m-%d') <= lv2.dataTermino) or lv2.dataTermino is null) "
		+ "				and lv2.linha.id = l.id"
		+ "				and lv2.active = true "
		+ " 				) "
		+ "and lv.linha.id = l.id ";

	/**
	 * The hql com linhas com data futura otimizado.
	 */
	private final String hqlComLinhasComDataFuturaOtimizado = "select distinct lv "
		+ " from LinhaVigencia lv "
		+ " inner join fetch lv.linha l "
		+ " inner join fetch lv.secoes sec "
		+ " where (lv.dataTermino >= DATE_FORMAT(Now(), '%Y-%m-%d') or lv.dataTermino is null) "
		+ " and (lv.dataInicio <= DATE_FORMAT(Now(), '%Y-%m-%d') or l.id not in ( "
		+ "				select lv2.linha.id from LinhaVigencia lv2 where lv2.dataTermino >= (DATE_FORMAT(Now(), '%Y-%m-%d')) and lv2.active = true)) "
		+ " and lv.active = true "
		// FBW-155
		+ " and sec.active = true ";

	/**
	 * <p>
	 * Field <code>hqlComLinhasComDataFuturaOtimizadoParaHistorico</code>
	 * </p>
	 */
	private final String hqlComLinhasComDataFuturaOtimizadoParaHistorico = "select distinct lv "
		+ " from LinhaVigencia lv "
		+ " inner join fetch lv.linha l "
		+ " inner join fetch lv.secoes sec "
		+ " where (lv.dataTermino >= DATE_FORMAT(Now(), '%Y-%m-%d') or lv.dataTermino <= DATE_FORMAT(Now(), '%Y-%m-%d') or lv.dataTermino is null) "
		+ " and (lv.dataInicio <= DATE_FORMAT(Now(), '%Y-%m-%d') or l.id not in ( "
		+ "				select lv2.linha.id from LinhaVigencia lv2 where lv2.dataTermino >= (DATE_FORMAT(Now(), '%Y-%m-%d')) and lv2.active = true)) "
		+ " and lv.active = true "
		+ " and sec.active = true ";

}
