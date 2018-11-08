package br.com.fetranspor.bomweb.business;

import br.com.caelum.vraptor.ioc.Component;
import br.com.decatron.framework.business.VRaptorBusiness;
import br.com.decatron.framework.exception.BusinessException;
import br.com.decatron.framework.provider.VRaptorProvider;
import br.com.decatron.framework.util.Check;
import br.com.fetranspor.bomweb.dao.EmpresaDAO;
import br.com.fetranspor.bomweb.entity.Empresa;

import java.util.List;

/**
 * The Class EmpresaBusiness.
 */
@Component
public class EmpresaBusiness
	extends VRaptorBusiness<Empresa>
{

	/**
	 * Instantiates a new empresa business.
	 *
	 * @param provider
	 *            the provider
	 * @param dao
	 *            the dao
	 * @param usuarioPerfilBusiness
	 *            the usuario perfil business
	 */
	public EmpresaBusiness(
		final VRaptorProvider provider,
		final EmpresaDAO dao,
		final UsuarioPerfilBusiness usuarioPerfilBusiness )
	{
		super( provider );
		this.dao = dao;
		setEmpresaDAO( dao );
		this.usuarioPerfilBusiness = usuarioPerfilBusiness;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param entity
	 * @throws BusinessException
	 * @see br.com.decatron.framework.business.AbstractBusiness#beforeDelete(br.com.decatron.framework.entity.EntityBase)
	 */
	@Override
	protected void beforeDelete( final Empresa entity )
		throws BusinessException
	{
		if ( this.usuarioPerfilBusiness.existeUsuarioAtivoDaEmpresa( entity.getId() ) )
		{
			throw new BusinessException( "Impossível excluir. Existem Usuários associados a esta Empresa." );
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param entity
	 * @throws BusinessException
	 * @see br.com.decatron.framework.business.AbstractBusiness#beforeSave(br.com.decatron.framework.entity.EntityBase)
	 */
	@Override
	protected void beforeSave( final Empresa entity )
		throws BusinessException
	{
		super.beforeSave( entity );
		if ( Check.isNull( entity.getCodigo() ) | ( entity.getCodigo().length() != 3 ) )
		{
			throw new BusinessException( "O código da empresa deve conter 3 digitos." );
		}
		if ( empresaJaExiste( entity.getCodigo() ) )
		{
			throw new BusinessException( "Empresa já existente. Favor verificar o código da empresa." );
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param entity
	 * @throws BusinessException
	 * @see br.com.decatron.framework.business.AbstractBusiness#beforeUpdate(br.com.decatron.framework.entity.EntityBase)
	 */
	@Override
	protected void beforeUpdate( final Empresa entity )
		throws BusinessException
	{
		super.beforeUpdate( entity );
		if ( empresaJaExiste( entity.getCodigo(), entity.getId() ) )
		{
			throw new BusinessException( "Empresa já existente. Favor verificar o código da empresa." );
		}
	}

	/**
	 * Busca empresas by codigo.
	 *
	 * @param codigo
	 *            the codigo
	 * @return the list
	 */
	public List<Empresa> buscaEmpresasByCodigo( final String codigo )
	{
		return getEmpresaDAO().buscaEmpresaByCodigo( codigo );
	}

	/**
	 * Busca empresas by nome.
	 *
	 * @param nome
	 *            the nome
	 * @return the list
	 */
	public List<Empresa> buscaEmpresasByNome( final String nome )
	{
		return getEmpresaDAO().buscaEmpresaByNome( nome );
	}

	/**
	 * Busca empresas by nome exato.
	 *
	 * @param nome
	 *            the nome
	 * @return the list
	 */
	public List<Empresa> buscaEmpresasByNomeExato( final String nome )
	{
		return getEmpresaDAO().buscaEmpresaByNomeExato( nome );
	}

	/**
	 * Busca empresas sem usuario by nome.
	 *
	 * @param nome
	 *            the nome
	 * @return the object
	 */
	public Object buscaEmpresasSemUsuarioByNome( final String nome )
	{
		return getEmpresaDAO().buscaEmpresaSemUsuarioByNome( nome );
	}

	/**
	 * Empresa ja existe.
	 *
	 * @param codigo
	 *            the codigo
	 * @return true, if successful
	 */
	public boolean empresaJaExiste( final String codigo )
	{
		return getEmpresaDAO().empresaJaExite( codigo );

	}

	/**
	 * Empresa ja existe.
	 *
	 * @param codigo
	 *            the codigo
	 * @param id
	 *            the id
	 * @return true, if successful
	 */
	public boolean empresaJaExiste( final String codigo, final Long id )
	{
		return getEmpresaDAO().empresaJaExite( codigo, id );
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @return Returns the empresaDAO.
	 * @see #empresaDAO
	 */
	private EmpresaDAO getEmpresaDAO()
	{
		return this.empresaDAO;
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @param empresaDAO
	 *            The empresaDAO to set.
	 * @see #empresaDAO
	 */
	private void setEmpresaDAO( final EmpresaDAO empresaDAO )
	{
		this.empresaDAO = empresaDAO;
	}

	/**
	 * The empresa dao.
	 */
	private EmpresaDAO empresaDAO;

	/**
	 * The usuario perfil business.
	 */
	private final UsuarioPerfilBusiness usuarioPerfilBusiness;
}
