package br.com.fetranspor.bomweb.business;

import br.com.caelum.vraptor.ioc.Component;
import br.com.decatron.framework.business.VRaptorBusiness;
import br.com.decatron.framework.exception.BusinessException;
import br.com.decatron.framework.exception.DaoException;
import br.com.decatron.framework.provider.VRaptorProvider;
import br.com.fetranspor.bomweb.dao.PerfilPermissaoDAO;
import br.com.fetranspor.bomweb.dto.RecursoDTO;
import br.com.fetranspor.bomweb.entity.Perfil;
import br.com.fetranspor.bomweb.entity.PerfilPermissao;
import br.com.fetranspor.bomweb.entity.Permissao;

import java.util.List;

/**
 * The Class PerfilPermissaoBusiness.
 */
@Component
public class PerfilPermissaoBusiness
	extends VRaptorBusiness<PerfilPermissao>
{

	/**
	 * Instantiates a new perfil permissao business.
	 *
	 * @param provider
	 *            the provider
	 * @param dao
	 *            the dao
	 */
	public PerfilPermissaoBusiness( final VRaptorProvider provider, final PerfilPermissaoDAO dao )
	{
		super( provider );
		this.dao = dao;
		this.perfilPermissaoDAO = dao;
	}

	/**
	 * Acoes.
	 *
	 * @return the list
	 */
	public List<String> acoes()
	{
		return this.perfilPermissaoDAO.acoes();
	}

	/**
	 * Perfis.
	 *
	 * @return the list
	 */
	public List<Perfil> perfis()
	{
		return this.perfilPermissaoDAO.perfis();
	}

	/**
	 * Preenche dto permissoes.
	 *
	 * @param perfil
	 *            the perfil
	 * @return the list
	 */
	public List<RecursoDTO> preencheDTOPermissoes( final Perfil perfil )
	{
		return this.perfilPermissaoDAO.preencheDTOPermissoes( perfil );
	}

	/**
	 * Save.
	 *
	 * @param perfil
	 *            the perfil
	 * @param permissoesSelecionadas
	 *            the permissoes selecionadas
	 * @throws BusinessException
	 *             the business exception
	 */
	public void save( final Perfil perfil, final List<Permissao> permissoesSelecionadas )
		throws BusinessException
	{

		final List<Permissao> permissoesBanco = this.perfilPermissaoDAO.permissoes( perfil );
		for ( final Permissao permissao : permissoesSelecionadas )
		{
			if ( permissoesBanco.contains( permissao ) )
			{
				permissoesBanco.remove( permissao );
			}
			else
			{
				final PerfilPermissao perfilPermissao = new PerfilPermissao( perfil.getId(), permissao.getId() );
				this.save( perfilPermissao );
			}
		}

		for ( final Permissao permissao : permissoesBanco )
		{
			final PerfilPermissao perfilPermissao = new PerfilPermissao( perfil.getId(), permissao.getId() );
			try
			{
				this.perfilPermissaoDAO.delete( perfilPermissao );
			}
			catch ( final DaoException e )
			{
				throw new BusinessException( e );
			}
		}
	}

	/**
	 * The perfil permissao dao.
	 */
	private final PerfilPermissaoDAO perfilPermissaoDAO;
}
