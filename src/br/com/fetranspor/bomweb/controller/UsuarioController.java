package br.com.fetranspor.bomweb.controller;

import static br.com.caelum.vraptor.view.Results.json;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import br.com.decatron.framework.exception.BusinessException;
import br.com.decatron.framework.provider.VRaptorProvider;
import br.com.decatron.framework.util.Check;
import br.com.fetranspor.bomweb.business.EmpresaBusiness;
import br.com.fetranspor.bomweb.business.UsuarioPerfilBusiness;
import br.com.fetranspor.bomweb.entity.Empresa;
import br.com.fetranspor.bomweb.entity.Perfil;
import br.com.fetranspor.bomweb.entity.Usuario;
import br.com.fetranspor.bomweb.entity.UsuarioPerfil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class UsuarioController.
 */
@Resource
public class UsuarioController
	extends BomWebController<UsuarioPerfil>
{

	/**
	 * Logger for this class
	 */
	private static final Log LOG = LogFactory.getLog( UsuarioController.class );

	/**
	 * Instantiates a new usuario controller.
	 *
	 * @param provider
	 *            the provider
	 * @param business
	 *            the business
	 * @param empresaBusiness
	 *            the empresa business
	 * @param result
	 *            the result
	 * @param validator
	 *            the validator
	 */
	public UsuarioController(
		final VRaptorProvider provider,
		final UsuarioPerfilBusiness business,
		final EmpresaBusiness empresaBusiness,
		final Result result,
		final Validator validator )
	{
		super( provider, business, result, validator );
		this.empresaBusiness = empresaBusiness;
		this.usuarioPerfilBusiness = business;

		final Perfil perfil = ( Perfil ) getRoles().iterator().next();
		if ( !perfil.isEmpresa() )
		{
			this.autoList = Boolean.FALSE;
		}
		else
		{
			this.autoList = Boolean.TRUE;
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @see br.com.decatron.framework.controller.CrudController#beforeEdit()
	 */
	@Override
	protected void beforeEdit()
	{
		super.beforeEdit();
		if ( LOG.isInfoEnabled() )
		{
			LOG.info( this.entity.getUsuario().getEmpresa() );
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @see br.com.decatron.framework.controller.CrudController#beforeInsert()
	 */
	@Override
	public void beforeInsert()
	{
		super.beforeInsert();
		final List<Empresa> empresas = this.empresaBusiness.search( null );
		final List<Long> idEmpresasComUsuario = new ArrayList<Long>();

		if ( Check.isNotEmpty( empresas ) )
		{
			final List<Usuario> usuariosAtivos = this.usuarioPerfilBusiness.buscarListagemUsuariosAtivos();

			for ( final Empresa empresa : empresas )
			{
				for ( final Usuario usuario : usuariosAtivos )
				{
					if ( usuario.getEmpresa().getId().equals( empresa.getId() ) )
					{
						idEmpresasComUsuario.add( empresa.getId() );
						break;
					}
				}
			}
		}

		this.result.include( "perfis", this.usuarioPerfilBusiness.perfis() );
		this.result.include( "empresas", this.empresaBusiness.search( null ) );
		this.result.include( "idEmpresasComUsuario", idEmpresasComUsuario.toArray( new Long[0] ) );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @see br.com.decatron.framework.controller.ListController#beforeList()
	 */
	@Override
	protected void beforeList()
	{
		super.beforeList();
		this.result.include( "perfis", this.usuarioPerfilBusiness.perfis() );
		final Perfil perfil = ( Perfil ) getRoles().iterator().next();
		if ( perfil.isEmpresa() )
		{
			this.result.include( "disableFilter", true );
			this.result.include( "disableExport", true );
		}
	}

	/**
	 * Busca empresa selecionada.
	 *
	 * @param parentId
	 *            the parent id
	 */
	@Get
	@Path( "/usuario/buscaEmpresaSelecionada.json" )
	public void buscaEmpresaSelecionada( final String parentId )
	{

		final List<Empresa> empresas = new ArrayList<Empresa>();
		final Empresa empresa = this.empresaBusiness.get( Long.valueOf( parentId ) );
		empresas.add( empresa );
		this.result.use( Results.json() ).withoutRoot().from( empresas ).serialize();
	}

	/**
	 * Busca json.
	 *
	 * @param term
	 *            the term
	 */
	@Get
	@Path( "/usuario/busca.json" )
	public void buscaJson( final String term )
	{
		this.result
			.use( json() )
			.from( this.empresaBusiness.buscaEmpresasSemUsuarioByNome( term == null ? "" : term ), "empresa" )
			.exclude( "uuid", "active", "excludeProperties", "codigo", "emailContato", "responsavel", "dataCriacao" )
			.serialize();
	}

	/**
	 * Confirm delete usuario.
	 *
	 * @param usuario
	 *            the usuario
	 * @return the usuario perfil
	 */
	public UsuarioPerfil confirmDeleteUsuario( final Usuario usuario )
	{
		return this.usuarioPerfilBusiness.buscarUsuarioPerfil( usuario );
	}

	/**
	 * Confirm redefine senha usuario.
	 *
	 * @param usuario
	 *            the usuario
	 * @return the usuario perfil
	 */
	public UsuarioPerfil confirmRedefineSenhaUsuario( final Usuario usuario )
	{
		return this.usuarioPerfilBusiness.buscarUsuarioPerfil( usuario );
	}

	/**
	 * Delete user.
	 *
	 * @param usuario
	 *            the usuario
	 */
	public void deleteUser( final Usuario usuario )
	{
		final UsuarioPerfil usuarioPerfil = new UsuarioPerfil();
		usuarioPerfil.setUsuario( usuario );
		super.delete( usuarioPerfil );
	}

	/**
	 * Edits the user.
	 *
	 * @param usuario
	 *            the usuario
	 * @return the usuario perfil
	 */
	public UsuarioPerfil editUser( final Usuario usuario )
	{
		this.result.include( "operation", "edit" );
		this.result.include( "empresas", this.empresaBusiness.search( null ) );
		this.result.include( "perfis", this.usuarioPerfilBusiness.perfis() );
		return this.usuarioPerfilBusiness.buscarUsuarioPerfil( usuario );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.decatron.framework.controller.Controller#excelAttributesName()
	 */
	@Override
	public String[] excelAttributesName()
	{
		return new String[]{"perfil.nome",
							"usuario.login",
							"usuario.nome",
							"usuario.email"};
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.decatron.framework.controller.Controller#excelFileName()
	 */
	@Override
	public String excelFileName()
	{
		final SimpleDateFormat df = new SimpleDateFormat( "ddMMyyyy" );
		return "Usuários_" + df.format( Calendar.getInstance().getTime() ) + ".xls";
	}

	// Excel

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.decatron.framework.controller.Controller#excelHeader()
	 */
	@Override
	public String[] excelHeader()
	{
		return new String[]{"Perfil",
							"Login",
							"Nome",
							"Email do Responsável"};
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.decatron.framework.controller.vraptor.VRaptorCrudController#onErrorSave()
	 */
	@Override
	protected String onErrorSave()
	{
		this.result.include( "perfis", this.usuarioPerfilBusiness.perfis() );
		this.result.include( "empresas", this.empresaBusiness.search( null ) );
		this.result.include( "usuarioPerfil", this.entity );
		return super.onErrorSave();
	}

	/**
	 * Redefine senha usuario.
	 *
	 * @param usuario
	 *            the usuario
	 */
	public void redefineSenhaUsuario( final Usuario usuario )
	{
		try
		{
			final String mensagem = "A sua senha foi redefinida pelo administrador do BOMWEB.\n\n";
			this.usuarioPerfilBusiness.redefineSenhaUsuario( usuario.getId(), mensagem );
			addInfoMessage( "A senha foi redefinida e enviada ao usuário." );
		}
		catch ( final BusinessException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e );
		}
		finally
		{
			this.result.redirectTo( this ).list();
		}
	}

	/**
	 * The usuario perfil business.
	 */
	private final UsuarioPerfilBusiness usuarioPerfilBusiness;

	/**
	 * The empresa business.
	 */
	private final EmpresaBusiness empresaBusiness;

}
