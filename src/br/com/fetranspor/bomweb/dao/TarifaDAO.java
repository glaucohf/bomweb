package br.com.fetranspor.bomweb.dao;

import static br.com.decatron.framework.dsl.date.DateDsl.date;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.ioc.Component;
import br.com.decatron.framework.dao.VRaptorHibernateDAO;
import br.com.decatron.framework.exception.DaoException;
import br.com.decatron.framework.provider.VRaptorProvider;
import br.com.decatron.framework.util.Check;
import br.com.fetranspor.bomweb.comparator.TarifaComparator;
import br.com.fetranspor.bomweb.dto.FiltroTarifaDTO;
import br.com.fetranspor.bomweb.entity.Bom;
import br.com.fetranspor.bomweb.entity.BomLinha;
import br.com.fetranspor.bomweb.entity.BomSecao;
import br.com.fetranspor.bomweb.entity.Empresa;
import br.com.fetranspor.bomweb.entity.Secao;
import br.com.fetranspor.bomweb.entity.Tarifa;
import br.com.fetranspor.bomweb.entity.Usuario;

/**
 * The Class TarifaDAO.
 */
@Component
public class TarifaDAO
	extends VRaptorHibernateDAO<Tarifa>
{

	/**
	 * Instantiates a new tarifa dao.
	 *
	 * @param session
	 *            the session
	 * @param provider
	 *            the provider
	 */
	public TarifaDAO( final Session session, final VRaptorProvider provider )
	{
		super( session, provider );
	}

	/**
	 * <p>
	 * Pega todos as id dos BOMs pelo codigo da linha e maiores que o MM/yyyy da InicioVigencia da
	 * tarifa
	 * </p>
	 * .
	 *
	 * @author marcio.dias
	 * @param codLinha
	 *            the cod linha
	 * @param mesAnoInicioVigencia
	 *            the mes ano inicio vigencia
	 * @return the list
	 */
	public List<Bom> buscarIdBom( final String codLinha, final String mesAnoInicioVigencia )
	{

		final StringBuffer sql = new StringBuffer( "" );
		/*
		 * select b.id from bom b, linha_vigencia l where b.empresa_id = l.empresa_id AND
		 * STR_TO_DATE(b.mes_referencia,'%m/%Y') >= STR_TO_DATE('06/2013','%m/%Y') AND l.codigo =
		 * '101001000'
		 */
		sql.append( "select b from Bom b, LinhaVigencia l " );
		sql.append( " where b.empresa = l.empresa " );
		sql
			.append( " and STR_TO_DATE(b.mesReferencia,'%m/%Y') >= STR_TO_DATE('"
				+ mesAnoInicioVigencia
				+ "','%m/%Y') " );
		sql.append( " and l.codigo = :codLinha " );
		final Query q = this.session.createQuery( sql.toString() );
		q.setParameter( "codLinha", codLinha );

		return q.list();
	}

	/**
	 * <p>
	 * Todos os ids do bom linha pela id do BOM
	 * </p>
	 * .
	 *
	 * @author marcio.dias
	 * @param idBom
	 *            the id bom
	 * @return the list
	 */
	public List<BomLinha> buscarIdBomLinha( final Bom idBom )
	{

		final StringBuffer sql = new StringBuffer( "" );
		/*
		 * select bl.id from bom_linha bl where bl.bom_id = 1632
		 */
		sql.append( "select bl from BomLinha bl " );
		sql.append( " where bl.bom = :idBom " );
		final Query q = this.session.createQuery( sql.toString() );
		q.setParameter( "idBom", idBom );

		return q.list();
	}

	/**
	 * <p>
	 * Pega todos as id dos BOMs pelo codigo da linha e maiores que o MM/yyyy da InicioVigencia da
	 * tarifa
	 * </p>
	 * .
	 *
	 * @author marcio.dias
	 * @param empresa
	 *            the empresa
	 * @param mesAnoInicioVigencia
	 *            the mes ano inicio vigencia
	 * @return the list
	 */
	public List<Bom> buscarIdBomPorIdEmpresa( final Empresa empresa, final String mesAnoInicioVigencia )
	{

		final StringBuffer sql = new StringBuffer( "" );

		sql.append( "select b from Bom b, LinhaVigencia l " );
		sql.append( " where b.empresa = l.empresa " );
		sql
			.append( " and STR_TO_DATE(b.mesReferencia,'%m/%Y') >= STR_TO_DATE('"
				+ mesAnoInicioVigencia
				+ "','%m/%Y') " );
		sql.append( " and l.empresa = :empresa " );
		sql.append( " group by b.id " );
		final Query q = this.session.createQuery( sql.toString() );
		q.setParameter( "empresa", empresa );

		return q.list();
	}

	/**
	 * Buscar tarifa.
	 *
	 * @param id
	 *            the id
	 * @return the tarifa
	 */
	public Tarifa buscarTarifa( final Long id )
	{

		final StringBuffer sql = new StringBuffer( "" );

		sql.append( "select t from Secao s, Tarifa t " );
		sql.append( " where t.secao.id = s.id " );
		sql.append( " and t.active = 1 and s.active = 1 " );
		sql.append( " and t.id = :idTarifa " );
		final Query q = this.session.createQuery( sql.toString() );
		q.setParameter( "idTarifa", id );

		return ( Tarifa ) q.uniqueResult();
	}

	/**
	 * <p>
	 * Busca tarifa pelo cod da linha e da secao
	 * </p>
	 * .
	 *
	 * @author marcio.dias
	 * @param codLinha
	 *            the cod linha
	 * @param secao
	 *            the secao
	 * @return Tarifa tarifa
	 */
	public List<Tarifa> buscarTarifaCodLinhaSecao( final String codLinha, final String secao )
	{

		final StringBuffer sql = new StringBuffer( "" );

		sql.append( "select t from Secao s, Tarifa t, LinhaVigencia l " );
		sql.append( " where t.secao.id = s.id " );
		sql.append( " and t.active = 1 " );
		sql.append( " and s.codigo = :secao " );
		sql.append( " and s.linhaVigencia = l.id " );
		sql.append( " and l.codigo = :codLinha " );
		sql.append( " order by t.inicioVigencia " );
		final Query q = this.session.createQuery( sql.toString() );
		q.setParameter( "secao", secao );
		q.setParameter( "codLinha", codLinha );

		return q.list();
	}

	/**
	 * Buscar tarifas ainda nao vigentes.
	 *
	 * @param tarifa
	 *            the tarifa
	 * @return the list
	 */
	public List<Tarifa> buscarTarifasAindaNaoVigentes( final Tarifa tarifa )
	{
		final Date hoje = new Date();
		return this.session
			.createCriteria( Tarifa.class )
			.add( Restrictions.eq( "secao", tarifa.getSecao() ) )
			.add( Restrictions.eq( "active", Boolean.TRUE ) )
			.add( Restrictions.gt( "inicioVigencia", hoje ) )
			.list();
	}

	/**
	 * <p>
	 * Retorna todas as tarifas da secao de uma empresa ativa a partir de uma data de vigencia
	 * inicial
	 * </p>
	 * .
	 *
	 * @author marcio.dias
	 * @param tarifa
	 *            the tarifa
	 * @param dataIniVig
	 *            the data ini vig
	 * @return List<Tarifa>
	 */
	@SuppressWarnings( "unchecked" )
	public List<Tarifa> buscarTarifasPorDataVigentes( final Tarifa tarifa, final Date dataIniVig )
	{
		// final Date hoje = new Date();
		return this.session
			.createCriteria( Tarifa.class )
			.add( Restrictions.eq( "secao", tarifa.getSecao() ) )
			.add( Restrictions.eq( "active", Boolean.TRUE ) )
			.add( Restrictions.gt( "inicioVigencia", dataIniVig ) )
			.list();
	}

	/**
	 * Busca tarifa anterior.
	 *
	 * @param secao
	 *            the secao
	 * @return the tarifa
	 */
	//TODO fixme
	public Tarifa buscaTarifaAnterior( final Secao secao )
	{
		final Date hoje = new Date();
		final DetachedCriteria maxFimVigencia = DetachedCriteria.forClass( Tarifa.class ).setProjection(
			Property.forName( "fimVigencia" ).max() );
		return ( Tarifa ) this.session
			.createCriteria( Tarifa.class )
			.add( Restrictions.eq( "secao", secao ) )
			.add( Restrictions.eq( "active", Boolean.TRUE ) )
			.add( Restrictions.le( "inicioVigencia", hoje ) )
			.add( Restrictions.isNotNull( "fimVigencia" ) )
			.add( Property.forName( "fimVigencia" ).eq( maxFimVigencia ) )
			.uniqueResult();
	}
	
	
	/**
	 * <p>
	 * </p>
	 * FBW-36.
	 *
	 * @param secao
	 *            the secao
	 * @param mesAno
	 *            the mes ano
	 * @return the tarifa
	 */
	public Tarifa buscaPrimeiraTarifaMes( final Secao secao, final String mesAno )
	{
		final int mesBom = Integer.valueOf( mesAno.split( "/" )[0] ) - 1;
		final int anoBom = Integer.valueOf( mesAno.split( "/" )[1] );

		final Date fimMes = date().withMonth( mesBom ).withYear( anoBom ).lastDayOfMonth().toDate();
		final Date inicioMes = date().withMonth( mesBom ).withYear( anoBom ).firstDayOfMonth().toDate();

		final List<Tarifa> tarifas = this.session
			.createCriteria( Tarifa.class )
			.add( Restrictions.eq( "secao", secao ) )
			.add( Restrictions.eq( "active", Boolean.TRUE ) )
			.add( Restrictions.le( "inicioVigencia", fimMes ) )
			.add( Restrictions.or( Restrictions.isNull( "fimVigencia" ), Restrictions.ge( "fimVigencia", inicioMes ) ) )
			.list();

		Collections.sort( tarifas, new TarifaComparator() );
		if ( tarifas.size() > 0 )
		{
			return tarifas.get( 0 );
		}
		return null;
	}


	/**
	 * <p>
	 * </p>
	 *
	 * @param data
	 * @param secao
	 * @return
	 */
	public Tarifa buscaTarifaAtivaEm( final Date data, final Secao secao )
	{
		return ( Tarifa ) this.session
			.createCriteria( Tarifa.class )
			.add( Restrictions.eq( "secao", secao ) )
			.add( Restrictions.le( "inicioVigencia", data ) )
			.add( Restrictions.or( Restrictions.isNull( "fimVigencia" ), Restrictions.ge( "fimVigencia", data ) ) )
			.uniqueResult();
	}

	/**
	 * Busca tarifa atual.
	 *
	 * @param secao
	 *            the secao
	 * @return the tarifa
	 */
	public Tarifa buscaTarifaAtual( final Secao secao )
	{
		final Date hoje = new Date();
		return ( Tarifa ) this.session
			.createCriteria( Tarifa.class )
			.add( Restrictions.eq( "secao", secao ) )
			.add( Restrictions.eq( "active", Boolean.TRUE ) )
			.add( Restrictions.le( "inicioVigencia", hoje ) )
			.add( Restrictions.or( Restrictions.isNull( "fimVigencia" ), Restrictions.ge( "fimVigencia", hoje ) ) )
			.uniqueResult();
	}

	/**
	 * <p>
	 * </p>
	 * FBW-36.
	 *
	 * @param secao
	 *            the secao
	 * @param mesAno
	 *            the mes ano
	 * @return the tarifa
	 */
	public Tarifa buscaTarifaMes( final Secao secao, final String mesAno )
	{
		final int mesBom = Integer.valueOf( mesAno.split( "/" )[0] ) - 1;
		final int anoBom = Integer.valueOf( mesAno.split( "/" )[1] );

		final Date fimMes = date().withMonth( mesBom ).withYear( anoBom ).lastDayOfMonth().toDate();
		final Date inicioMes = date().withMonth( mesBom ).withYear( anoBom ).firstDayOfMonth().toDate();

		final List<Tarifa> tarifas = this.session
			.createCriteria( Tarifa.class )
			.add( Restrictions.eq( "secao", secao ) )
			.add( Restrictions.eq( "active", Boolean.TRUE ) )
			.add( Restrictions.le( "inicioVigencia", fimMes ) )
			.add( Restrictions.or( Restrictions.isNull( "fimVigencia" ), Restrictions.ge( "fimVigencia", inicioMes ) ) )
			.list();

		Collections.sort( tarifas, new TarifaComparator() );
		if ( tarifas.size() > 0 )
		{
			return tarifas.get( tarifas.size() - 1 );
		}
		return null;
	}

	/**
	 * Busca tarifas.
	 *
	 * @param secoes
	 *            the secoes
	 * @return the list
	 */
	@SuppressWarnings( "unchecked" )
	public List<Tarifa> buscaTarifas( final List<Secao> secoes )
	{
		return this.session
			.createCriteria( Tarifa.class )
			.add( Restrictions.eq( "active", Boolean.TRUE ) )
			.add( Restrictions.in( "secao", secoes ) )
			.list();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param secoes
	 * @return
	 */
	public List<Tarifa> buscaTarifasDeSecoes( final List<Secao> secoes )
	{
		return this.session.createCriteria( Tarifa.class ).add( Restrictions.in( "secao", secoes ) ).list();
	}

	/**
	 * Busca tarifas futuras e atual.
	 *
	 * @param secoes
	 *            the secoes
	 * @return the list
	 */
	@SuppressWarnings( "unchecked" )
	public List<Tarifa> buscaTarifasFuturasEAtual( final List<Secao> secoes )
	{
		final Date hoje = new Date();
		return this.session
			.createCriteria( Tarifa.class )
			.add( Restrictions.eq( "active", Boolean.TRUE ) )
			.add( Restrictions.or( Restrictions.ge( "fimVigencia", hoje ), Restrictions.isNull( "fimVigencia" ) ) )
			.add( Restrictions.in( "secao", secoes ) )
			.list();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param data
	 * @param secao
	 * @return
	 */
	public List<Tarifa> buscaTarifasPosteriores( final Date data, final Secao secao )
	{
		return this.session
			.createCriteria( Tarifa.class )
			.add( Restrictions.eq( "secao", secao ) )
			.add( Restrictions.gt( "inicioVigencia", data ) )
			.list();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param secao
	 * @return Retorna todas as tarifas de uma seção.
	 */
	public List<Tarifa> buscaTodasAsTarifas( final Secao secao )
	{
		return this.session.createCriteria( Tarifa.class ).add( Restrictions.eq( "secao", secao ) ).list();
	}

	/**
	 * Deleta bom secao.
	 *
	 * @param bomLinha
	 *            the bom linha
	 */
	@SuppressWarnings( "unchecked" )
	public void deletaBomSecao( final BomLinha bomLinha )
	{

		final StringBuffer sql = new StringBuffer( "" );

		sql.append( "select bs from BomSecao bs " );
		sql.append( " where bs.bomLinha = :bomLinha " );
		final Query q = this.session.createQuery( sql.toString() );
		q.setParameter( "bomLinha", bomLinha );
		final List<BomSecao> lista = q.list();
		// return q.list();
		for ( final BomSecao bomSecao : lista )
		{
			this.session.delete( bomSecao );
		}

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
	public void delete( Tarifa entity )
		throws DaoException
	{
		entity = get( entity.getId() );
		entity.setActive( Boolean.FALSE );
		super.update( entity );
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @author marcio.dias
	 * @param bom
	 *            the bom
	 */
	public void deleteBom( final Bom bom )
	{

		this.session.delete( bom );

	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @author marcio.dias
	 * @param bomLinha
	 *            the bom linha
	 */
	public void deleteBomLinha( final BomLinha bomLinha )
	{

		this.session.delete( bomLinha );

	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @author marcio.dias
	 * @param ids
	 *            the ids
	 */
	public void deleteBomSecao( final String ids )
	{
		new StringBuffer( "" );

		final SQLQuery query = this.session.createSQLQuery( "delete from BomSecao where bomLinha.id in (" + ids + ")" );
		query.executeUpdate();
		// q.executeUpdate();
		// return q.list();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param entity
	 * @throws DaoException
	 */
	public void deleteFisicamente( Tarifa entity )
		throws DaoException
	{
		entity = get( entity.getId() );
		this.session.delete( entity );

	}

	/**
	 * Filtro.
	 *
	 * @param filtro
	 *            the filtro
	 * @return the list
	 */
	@SuppressWarnings( "unchecked" )
	public List<Tarifa> filtro( final FiltroTarifaDTO filtro )
	{

		final StringBuffer sql = new StringBuffer( "" );
		Query q;

		final Usuario user = ( Usuario ) getUser();

		sql.append( "select t from Tarifa t " );
		sql.append( "inner join fetch t.secao s " );
		sql.append( "inner join fetch s.linhaVigencia l " );
		sql.append( "inner join l.empresa e " );
		sql.append( "inner join fetch l.linha li " );
		sql.append( "where t.active = 1 " );
		sql.append( "and (t.fimVigencia is null or t.fimVigencia >= DATE_FORMAT(Now(), '%Y-%m-%d')) " );
		sql.append( "and (l.dataTermino is null or l.dataTermino >= DATE_FORMAT(Now(), '%Y-%m-%d')) " );
		sql.append( "and ( (l.dataInicio <= DATE_FORMAT(Now(), '%Y-%m-%d')) " );
		sql.append( "	or ( (l.dataInicio > DATE_FORMAT(Now(), '%Y-%m-%d')) " );
		sql.append( "		and l.linha.id not in(" );
		sql.append( "			select l1.linha.id from Empresa e1, LinhaVigencia l1 " );
		sql.append( "			where e1.id = l1.empresa and l1.active = 1 and e1.active = 1 " );
		sql.append( "			and (l1.dataTermino is null or l1.dataTermino >= DATE_FORMAT(Now(), '%Y-%m-%d')) " );
		sql.append( "			and (l1.dataInicio <= DATE_FORMAT(Now(), '%Y-%m-%d'))))) " );

		if ( Check.isNull( filtro ) )
		{
			sql.append( "and (t.inicioVigencia <= DATE_FORMAT(Now(), '%Y-%m-%d')) " );
			if ( Check.isNotNull( user ) && Check.isNotNull( user.getEmpresa() ) )
			{
				sql.append( " and e.id = :idEmpresa " );
				q = this.session.createQuery( sql.toString() );
				q.setParameter( "idEmpresa", Long.valueOf( user.getEmpresa().getId() ) );
				return q.list();
			}
			q = this.session.createQuery( sql.toString() );
			return q.list();
		}

		if ( Check.isNotNull( filtro.empresa )
			&& Check.isNotBlank( filtro.empresa )
			&& !filtro.empresa.contains( "undefined" )
			&& !filtro.empresa.contains( "Selecione" ) )
		{
			sql.append( " and e.id = :idEmpresa " );
		}

		if ( filtro.tarifaFutura )
		{
			sql.append( "and (t.inicioVigencia > DATE_FORMAT(Now(), '%Y-%m-%d')) " );
			if ( Check.isNotNull( filtro.valorFutura ) && Check.isNotBlank( filtro.valorFutura ) )
			{
				sql.append( " and t.valor = :valorTarifa" );
			}
		}
		else
		{
			sql.append( "and (t.inicioVigencia <= DATE_FORMAT(Now(), '%Y-%m-%d')) " );
			if ( Check.isNotNull( filtro.valor ) && Check.isNotBlank( filtro.valor ) )
			{
				sql.append( " and t.valor = :valorTarifa" );
			}
		}

		if ( Check.isNotNull( filtro.linha )
			&& Check.isNotBlank( filtro.linha )
			&& !filtro.linha.contains( "undefined" )
			&& !filtro.linha.contains( "Selecione" ) )
		{
			sql.append( " and l.id = :idLinha " );
		}

		if ( Check.isNotNull( filtro.secao )
			&& Check.isNotBlank( filtro.secao )
			&& !filtro.secao.contains( "undefined" )
			&& !filtro.secao.contains( "Selecione" ) )
		{
			sql.append( " and s.id = :idSecao " );
		}

		if ( Check.isNotNull( user ) && Check.isNotNull( user.getEmpresa() ) )
		{
			sql.append( " and e.id = :idEmpresa " );
		}

		sql.append( " order by e.id, l.id, s.id, t.inicioVigencia " );

		q = this.session.createQuery( sql.toString() );

		if ( Check.isNotNull( user ) && Check.isNotNull( user.getEmpresa() ) )
		{
			q.setParameter( "idEmpresa", Long.valueOf( user.getEmpresa().getId() ) );
		}

		if ( Check.isNotNull( filtro.empresa )
			&& Check.isNotBlank( filtro.empresa )
			&& !filtro.empresa.contains( "undefined" )
			&& !filtro.empresa.contains( "Selecione" ) )
		{
			q.setParameter( "idEmpresa", ( Long.valueOf( filtro.empresa ) ) );
		}

		if ( Check.isNotNull( filtro.linha )
			&& Check.isNotBlank( filtro.linha )
			&& !filtro.linha.contains( "undefined" )
			&& !filtro.linha.contains( "Selecione" ) )
		{
			q.setParameter( "idLinha", Long.valueOf( filtro.linha ) );
		}

		if ( Check.isNotNull( filtro.secao )
			&& Check.isNotBlank( filtro.secao )
			&& !filtro.secao.contains( "undefined" )
			&& !filtro.secao.contains( "Selecione" ) )
		{
			q.setParameter( "idSecao", Long.valueOf( filtro.secao ) );
		}

		if ( filtro.tarifaFutura && Check.isNotNull( filtro.valorFutura ) && Check.isNotBlank( filtro.valorFutura ) )
		{
			q.setParameter( "valorTarifa", BigDecimal.valueOf( Double.valueOf( filtro.valorFutura ) ) );
		}

		if ( !filtro.tarifaFutura && Check.isNotNull( filtro.valor ) && Check.isNotBlank( filtro.valor ) )
		{
			q.setParameter( "valorTarifa", BigDecimal.valueOf( Double.valueOf( filtro.valor ) ) );
		}

		return q.list();
	}

	/**
	 * Find historico.
	 *
	 * @param tarifa
	 *            the tarifa
	 * @return the list
	 */
	@SuppressWarnings( "unchecked" )
	public List<Tarifa> findHistorico( final Tarifa tarifa )
	{
		final String hql = "from Tarifa t where t.secao = :secao and t.active = 1 order by t.inicioVigencia";
		return this.session.createQuery( hql ).setParameter( "secao", tarifa.getSecao() ).list();
	}

	/**
	 * Find valor tarifas.
	 *
	 * @param isListaFuturas
	 *            the is lista futuras
	 * @return the list
	 */
	public List<String> findValorTarifas( final boolean isListaFuturas )
	{

		final StringBuffer sql = new StringBuffer( "" );
		Query q;

		final Usuario user = ( Usuario ) getUser();

		sql.append( "select distinct t.valor from Empresa e, LinhaVigencia l, Secao s, Tarifa t " );
		sql.append( "where e.id = l.empresa and l.id = s.linhaVigencia and s.id = t.secao and t.active = 1 " );
		sql.append( "and (t.fimVigencia is null or t.fimVigencia >= DATE_FORMAT(Now(), '%Y-%m-%d')) " );
		sql.append( "and (l.dataTermino is null or l.dataTermino >= DATE_FORMAT(Now(), '%Y-%m-%d')) " );
		sql.append( "and ( (l.dataInicio <= DATE_FORMAT(Now(), '%Y-%m-%d')) " );
		sql.append( "	or ( (l.dataInicio > DATE_FORMAT(Now(), '%Y-%m-%d')) " );
		sql.append( "		and not exists(" );
		sql.append( "			select 1 from Empresa e1, LinhaVigencia l1 " );
		sql.append( "			where e1.id = l1.empresa and l1.active = 1 and e1.active = 1 " );
		sql.append( "			and (l1.dataTermino is null or l1.dataTermino >= DATE_FORMAT(Now(), '%Y-%m-%d')) " );
		sql.append( "			and (l1.dataInicio <= DATE_FORMAT(Now(), '%Y-%m-%d')) " );
		sql.append( "			and (l1.linha = l.linha) ))) " );

		if ( isListaFuturas )
		{
			sql.append( "and (t.inicioVigencia >= DATE_FORMAT(Now(), '%Y-%m-%d')) " );
		}
		else
		{
			sql.append( "and (t.inicioVigencia <= DATE_FORMAT(Now(), '%Y-%m-%d')) " );
		}

		if ( Check.isNotNull( user ) && Check.isNotNull( user.getEmpresa() ) )
		{
			sql.append( " and e.id = :idEmpresa " );
		}
		sql.append( " order by t.valor " );

		q = this.session.createQuery( sql.toString() );
		if ( Check.isNotNull( user ) && Check.isNotNull( user.getEmpresa() ) )
		{
			q.setParameter( "idEmpresa", Long.valueOf( user.getEmpresa().getId() ) );
		}

		return q.list();
	}

	/**
	 * Gets the por id tarifa antiga.
	 *
	 * @param id
	 *            the id
	 * @return the por id tarifa antiga
	 */
	@SuppressWarnings( "unchecked" )
	public List<Tarifa> getPorIdTarifaAntiga( final Long id )
	{
		return this.session
			.createCriteria( Tarifa.class )
			.add( Restrictions.eq( "active", Boolean.TRUE ) )
			.add( Restrictions.eq( "idTarifaAntiga", id ) )
			.list();
	}

	/**
	 * Tarifa futura.
	 *
	 * @param secao
	 *            the secao
	 * @return the tarifa
	 */
	public Tarifa tarifaFutura( final Secao secao )
	{
		final Date hoje = new Date();
		return ( Tarifa ) this.session
			.createCriteria( Tarifa.class )
			.add( Restrictions.eq( "secao", secao ) )
			.add( Restrictions.eq( "active", Boolean.TRUE ) )
			.add( Restrictions.gt( "inicioVigencia", hoje ) )
			.uniqueResult();
	}
}
