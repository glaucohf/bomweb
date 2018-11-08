package br.com.fetranspor.bomweb.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.com.decatron.framework.dao.VRaptorHibernateDAO;
import br.com.decatron.framework.provider.AbstractProvider;
import br.com.fetranspor.bomweb.entity.Configuracao;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * The Class ConfiguracaoDAO.
 */
@Component
public class ConfiguracaoDAO
	extends VRaptorHibernateDAO<Configuracao>
{

	/**
	 * Instantiates a new configuracao dao.
	 *
	 * @param session
	 *            the session
	 * @param provider
	 *            the provider
	 */
	public ConfiguracaoDAO( final Session session, final AbstractProvider provider )
	{
		super( session, provider );
	}

	/**
	 * Atualizar configuracao.
	 *
	 * @param configuracao
	 *            the configuracao
	 */
	public void atualizarConfiguracao( final Configuracao configuracao )
	{
		final String hql = "update Configuracao set valor = :valor where id = :id";
		this.session
			.createQuery( hql )
			.setParameter( "valor", configuracao.getValor() )
			.setParameter( "id", configuracao.getId() )
			.executeUpdate();
	}

	/**
	 * Buscar configuracao.
	 *
	 * @param idConfiguracao
	 *            the id configuracao
	 * @return the configuracao
	 */
	private Configuracao buscarConfiguracao( final Long idConfiguracao )
	{
		return ( Configuracao ) this.session
			.createCriteria( Configuracao.class )
			.add( Restrictions.eq( "id", idConfiguracao ) )
			.uniqueResult();
	}

	/**
	 * Buscar configuracao bom.
	 *
	 * @return the configuracao
	 */
	public Configuracao buscarConfiguracaoBom()
	{
		return ( Configuracao ) this.session.get( Configuracao.class, this.ID_CONFIGURACAO_BOM );
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public Configuracao buscarConfiguracaoBomReaberturaPelaEmpresa()
	{
		return ( Configuracao ) this.session.get( Configuracao.class, this.ID_CONFIGURACAO_BOM_REABERTURA_PELA_EMPRESA );
	}

	/**
	 * Buscar configuracao bom reabertura.
	 *
	 * @return the configuracao
	 */
	public Configuracao buscarConfiguracaoBomReaberturaPeloDetro()
	{
		return ( Configuracao ) this.session.get( Configuracao.class, this.ID_CONFIGURACAO_BOM_REABERTURA_PELO_DETRO );
	}

	/**
	 * Buscar configuracao data inicio sistema.
	 *
	 * @return the configuracao
	 */
	public Configuracao buscarConfiguracaoDataInicioSistema()
	{
		return buscarConfiguracao( this.ID_CONFIGURACAO_DATA_INICIO_SISTEMA );
	}

	/**
	 * Buscar configuracao email detro.
	 *
	 * @return the configuracao
	 */
	public Configuracao buscarConfiguracaoEmailDetro()
	{
		return buscarConfiguracao( this.ID_CONFIGURACAO_EMAIL_DETRO );
	}

	/**
	 * Buscar configuracoes bom.
	 *
	 * @return the list
	 */
	public List<Configuracao> buscarConfiguracoesBom()
	{
		final Collection configuracoesId = new HashSet();
		configuracoesId.add( this.ID_CONFIGURACAO_BOM );
		configuracoesId.add( this.ID_CONFIGURACAO_BOM_REABERTURA_PELO_DETRO );
		configuracoesId.add( this.ID_CONFIGURACAO_BOM_REABERTURA_PELA_EMPRESA );
		return this.session.createCriteria( Configuracao.class ).add( Restrictions.in( "id", configuracoesId ) ).list();
	}

	/**
	 * The id configuracao bom.
	 */
	private final Long ID_CONFIGURACAO_BOM = Long.valueOf( "1" );

	/**
	 * The id configuracao bom reabertura.
	 */
	private final Long ID_CONFIGURACAO_BOM_REABERTURA_PELO_DETRO = Long.valueOf( "2" );

	/**
	 * The id configuracao data inicio sistema.
	 */
	private final Long ID_CONFIGURACAO_DATA_INICIO_SISTEMA = Long.valueOf( "3" );

	/**
	 * The id configuracao email detro.
	 */
	private final Long ID_CONFIGURACAO_EMAIL_DETRO = Long.valueOf( "4" );

	/**
	 * <p>
	 * Field <code>ID_CONFIGURACAO_BOM_REABERTURA_PELA_EMPRESA</code>
	 * </p>
	 */
	private final Long ID_CONFIGURACAO_BOM_REABERTURA_PELA_EMPRESA = Long.valueOf( "5" );

}
