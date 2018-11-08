package br.com.fetranspor.bomweb.components;

import br.com.caelum.vraptor.http.route.Router;
import br.com.caelum.vraptor.http.route.RoutesConfiguration;
import br.com.caelum.vraptor.http.route.Rules;
import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.resource.HttpMethod;
import br.com.decatron.framework.exception.BusinessException;
import br.com.fetranspor.bomweb.controller.BomController;
import br.com.fetranspor.bomweb.controller.ConfiguracaoController;
import br.com.fetranspor.bomweb.controller.EmpresaController;
import br.com.fetranspor.bomweb.controller.HomeController;
import br.com.fetranspor.bomweb.controller.LinhaController;
import br.com.fetranspor.bomweb.controller.LogController;
import br.com.fetranspor.bomweb.controller.LoginController;
import br.com.fetranspor.bomweb.controller.ManualController;
import br.com.fetranspor.bomweb.controller.PermissaoController;
import br.com.fetranspor.bomweb.controller.RelatorioController;
import br.com.fetranspor.bomweb.controller.TarifaController;
import br.com.fetranspor.bomweb.controller.TipodelinhaController;
import br.com.fetranspor.bomweb.controller.TipodeveiculoController;
import br.com.fetranspor.bomweb.controller.UsuarioController;

/**
 * The Class CustomRoutes.
 */
@Component
@ApplicationScoped
public class CustomRoutes
	implements RoutesConfiguration
{

	/**
	 * <p>
	 * </p>
	 *
	 * @param router
	 * @see br.com.caelum.vraptor.http.route.RoutesConfiguration#config(br.com.caelum.vraptor.http.route.Router)
	 */
	@Override
	public void config( final Router router )
	{
		new Rules( router )
		{

			@Override
			public void routes()
			{
				// Home
				routeFor( "/" ).is( HomeController.class ).home();
				routeFor( "/login" ).is( LoginController.class ).login();
				routeFor( "/esquecisenha" ).with( HttpMethod.GET ).is( LoginController.class ).esqueciSenha();
				routeFor( "/novasenha" ).with( HttpMethod.POST ).is( LoginController.class ).novaSenha( null );
				routeFor( "/autenticar" ).with( HttpMethod.POST ).is( LoginController.class ).autenticar( null );
				routeFor( "/acessonegado" ).with( HttpMethod.GET ).is( LoginController.class ).acessonegado();
				routeFor( "/trocarsenha" ).with( HttpMethod.GET ).is( LoginController.class ).trocarSenha();
				routeFor( "/trocarsenha" ).with( HttpMethod.POST ).is( LoginController.class ).trocarSenha( null );
				routeFor( "/sair" ).is( LoginController.class ).sair();

				// Empresa
				routeFor( "/empresa/view/{entity.id}" )
					.withParameter( "entity.id" )
					.matching( "\\d+" )
					.is( EmpresaController.class )
					.view( null );
				routeFor( "/empresa/list" ).is( EmpresaController.class ).list( null );
				routeFor( "/empresa/insert" ).is( EmpresaController.class ).insert();
				routeFor( "/empresa/save" ).with( HttpMethod.POST ).is( EmpresaController.class ).save( null );
				routeFor( "/empresa/edit/{entity.id}" )
					.withParameter( "entity.id" )
					.matching( "\\d+" )
					.is( EmpresaController.class )
					.edit( null );
				routeFor( "/empresa/update" ).with( HttpMethod.POST ).is( EmpresaController.class ).update( null );
				routeFor( "/empresa/delete-confirm/{entity.id}" )
					.withParameter( "entity.id" )
					.matching( "\\d+" )
					.is( EmpresaController.class )
					.confirmDelete( null );
				routeFor( "/empresa/delete/{entity.id}" )
					.withParameter( "entity.id" )
					.matching( "\\d+" )
					.is( EmpresaController.class )
					.delete( null );
				routeFor( "/empresa/exportar" )
					.with( HttpMethod.POST )
					.is( EmpresaController.class )
					.exportToExcel( null );

				// Linha
				routeFor( "/linha/list" ).is( LinhaController.class ).list( null );
				routeFor( "/linha/insert" ).is( LinhaController.class ).insert();
				routeFor( "/linha/save" ).with( HttpMethod.POST ).is( LinhaController.class ).save( null, null, null );
				routeFor( "/linha/edit-confirm/{entity.id}" )
					.withParameter( "entity.id" )
					.matching( "\\d+" )
					.is( LinhaController.class )
					.confirmEdit( null );
				routeFor( "/linha/edit/{entity.id}" )
					.withParameter( "entity.id" )
					.matching( "\\d+" )
					.is( LinhaController.class )
					.edit( null );
				routeFor( "/linha/edit-futuro/{entity.id}" )
					.withParameter( "entity.id" )
					.matching( "\\d+" )
					.is( LinhaController.class )
					.editFuturo( null );
				routeFor( "/linha/update" )
					.with( HttpMethod.POST )
					.is( LinhaController.class )
					.update( null, null, null, null );
				routeFor( "/linha/delete-confirm/{entity.id}" )
					.withParameter( "entity.id" )
					.matching( "\\d+" )
					.is( LinhaController.class )
					.confirmDelete( null );
				routeFor( "/linha/delete/{entity.id}" )
					.withParameter( "entity.id" )
					.matching( "\\d+" )
					.is( LinhaController.class )
					.delete( null );
				routeFor( "/linha/exportar" ).with( HttpMethod.POST ).is( LinhaController.class ).exportToExcel( null );
				routeFor( "/linha/exportarComSecoes" )
					.with( HttpMethod.POST )
					.is( LinhaController.class )
					.exportarComSecoes( null );
				routeFor( "/linha/historico/{entity.id}" )
					.withParameter( "entity.id" )
					.matching( "\\d+" )
					.is( LinhaController.class )
					.historico( null );
				routeFor( "/linha/secao/{linhaVigencia.id}" )
					.withParameter( "linhaVigencia.id" )
					.matching( "\\d+" )
					.is( LinhaController.class )
					.secoes( null );

				// Tipo Veiculo
				routeFor( "/tipodeveiculo/list" ).is( TipodeveiculoController.class ).list( null );
				routeFor( "/tipodeveiculo/insert" ).is( TipodeveiculoController.class ).insert();
				routeFor( "/tipodeveiculo/save" )
					.with( HttpMethod.POST )
					.is( TipodeveiculoController.class )
					.save( null );
				routeFor( "/tipodeveiculo/update" )
					.with( HttpMethod.POST )
					.is( TipodeveiculoController.class )
					.update( null );
				routeFor( "/tipodeveiculo/delete-confirm/{entity.id}" )
					.withParameter( "entity.id" )
					.matching( "\\d+" )
					.is( TipodeveiculoController.class )
					.confirmDelete( null );
				routeFor( "/tipodeveiculo/delete/{entity.id}" )
					.withParameter( "entity.id" )
					.matching( "\\d+" )
					.is( TipodeveiculoController.class )
					.delete( null );
				routeFor( "/tipodeveiculo/exportar" )
					.with( HttpMethod.POST )
					.is( TipodeveiculoController.class )
					.exportToExcel( null );

				// Tipo Linha
				routeFor( "/tipodelinha/list" ).is( TipodelinhaController.class ).list( null );
				routeFor( "/tipodelinha/insert" ).is( TipodelinhaController.class ).insert();
				routeFor( "/tipodelinha/save" )
					.with( HttpMethod.POST )
					.is( TipodelinhaController.class )
					.save( null, null );
				routeFor( "/tipodelinha/update" )
					.with( HttpMethod.POST )
					.is( TipodelinhaController.class )
					.update( null, null );
				routeFor( "/tipodelinha/delete-confirm/{entity.id}" )
					.withParameter( "entity.id" )
					.matching( "\\d+" )
					.is( TipodelinhaController.class )
					.confirmDelete( null );
				routeFor( "/tipodelinha/delete/{entity.id}" )
					.withParameter( "entity.id" )
					.matching( "\\d+" )
					.is( TipodelinhaController.class )
					.delete( null );
				routeFor( "/tipodelinha/exportar" )
					.with( HttpMethod.POST )
					.is( TipodelinhaController.class )
					.exportToExcel( null );

				// Tarifa
				// routeFor("/tarifa/list").is(TarifaController.class).list(null);
				routeFor( "/tarifa/confirm-import" ).is( TarifaController.class ).confirmImport();
				routeFor( "/tarifa/insert" ).is( TarifaController.class ).insert();
				routeFor( "/tarifa/save" ).with( HttpMethod.POST ).is( TarifaController.class ).save( null );
				routeFor( "/tarifa/edit/{entity.id}" )
					.withParameter( "entity.id" )
					.matching( "\\d+" )
					.is( TarifaController.class )
					.edit( null );
				routeFor( "/tarifa/erro-edit-futura/{entity.id}" )
					.withParameter( "entity.id" )
					.matching( "\\d+" )
					.is( TarifaController.class )
					.erroEditFutura( null );
				routeFor( "/tarifa/update" ).with( HttpMethod.POST ).is( TarifaController.class ).update( null );
				routeFor( "/tarifa/delete-confirm/{entity.id}" )
					.withParameter( "entity.id" )
					.matching( "\\d+" )
					.is( TarifaController.class )
					.confirmDelete( null );
				routeFor( "/tarifa/delete/{entity.id}" )
					.withParameter( "entity.id" )
					.matching( "\\d+" )
					.is( TarifaController.class )
					.delete( null );
				routeFor( "/tarifa/formUpload" ).is( TarifaController.class ).formUpload();
				routeFor( "/tarifa/upload" ).is( TarifaController.class ).upload( null, null );
				routeFor( "/tarifa/historico/{entity.id}" )
					.withParameter( "entity.id" )
					.matching( "\\d+" )
					.is( TarifaController.class )
					.historico( null );

				// Usuario
				routeFor( "/usuario/list" ).is( UsuarioController.class ).list( null );
				routeFor( "/usuario/insert" ).is( UsuarioController.class ).insert();
				routeFor( "/usuario/save" ).with( HttpMethod.POST ).is( UsuarioController.class ).save( null );
				routeFor( "/usuario/edit/{usuario.id}" )
					.withParameter( "usuario.id" )
					.matching( "\\d+" )
					.is( UsuarioController.class )
					.editUser( null );
				routeFor( "/usuario/update" ).with( HttpMethod.POST ).is( UsuarioController.class ).update( null );
				routeFor( "/usuario/delete-confirm/{usuario.id}" )
					.withParameter( "usuario.id" )
					.matching( "\\d+" )
					.is( UsuarioController.class )
					.confirmDeleteUsuario( null );
				routeFor( "/usuario/delete/{usuario.id}" )
					.withParameter( "usuario.id" )
					.matching( "\\d+" )
					.is( UsuarioController.class )
					.deleteUser( null );
				routeFor( "/usuario/redefineSenha-confirm/{usuario.id}" )
					.withParameter( "usuario.id" )
					.matching( "\\d+" )
					.is( UsuarioController.class )
					.confirmRedefineSenhaUsuario( null );
				routeFor( "/usuario/redefineSenha/{usuario.id}" )
					.withParameter( "usuario.id" )
					.matching( "\\d+" )
					.is( UsuarioController.class )
					.redefineSenhaUsuario( null );
				routeFor( "/usuario/exportar" )
					.with( HttpMethod.POST )
					.is( UsuarioController.class )
					.exportToExcel( null );

				// Log
				routeFor( "/log/list" ).is( LogController.class ).list( null );

				// Permisssao
				routeFor( "/configuracao/permissao/list" ).is( PermissaoController.class ).list();
				routeFor( "/configuracao/permissao/list/{perfil.id}" )
					.withParameter( "perfil.id" )
					.matching( "\\d+" )
					.is( PermissaoController.class )
					.list( null );
				routeFor( "/configuracao/permissao/save" )
					.with( HttpMethod.POST )
					.is( PermissaoController.class )
					.save( null, null );

				// BOM
				routeFor( "/bom/list" ).is( BomController.class ).list( null );
				routeFor( "/bom/insert" ).is( BomController.class ).insert();
				routeFor( "/bom/generate" ).with( HttpMethod.POST ).is( BomController.class ).generate( null );
				routeFor( "/bom/{bom.id}/reabrir" )
					.withParameter( "bom.id" )
					.matching( "\\d+" )
					.is( BomController.class )
					.reabrir( null );
				routeFor( "/bom/{bom.id}/justificativas" )
					.withParameter( "bom.id" )
					.matching( "\\d+" )
					.is( BomController.class )
					.justificativas( null );
				routeFor( "/bom/justificativas/historico/{bomLinha.id}" )
					.withParameter( "bomLinha.id" )
					.matching( "\\d+" )
					.is( BomController.class )
					.historico( null );
				try
				{
					routeFor( "/bom/{bom.id}/reabertura" )
						.with( HttpMethod.POST )
						.is( BomController.class )
						.reabertura( null, null );
				}
				catch ( final BusinessException e )
				{
					throw new RuntimeException( e );
				}
				routeFor( "/bom/{bom.id}/fechar" )
					.withParameter( "bom.id" )
					.matching( "\\d+" )
					.is( BomController.class )
					.fechar( null );
				routeFor( "/bom/{bom.id}/fechar-confirm" )
					.withParameter( "bom.id" )
					.matching( "\\d+" )
					.is( BomController.class )
					.confirmFechar( null );
				routeFor( "/bom/{bom.id}/visualizar" )
					.withParameter( "bom.id" )
					.matching( "\\d+" )
					.is( BomController.class )
					.visualizar( null );
				routeFor( "/bom/{bom.id}/linhas" )
					.withParameter( "bom.id" )
					.matching( "\\d+" )
					.is( BomController.class )
					.linhas( null );
				routeFor( "/bom/linhas/inoperantes" )
					.with( HttpMethod.POST )
					.is( BomController.class )
					.linhasInoperantes( null, null );
				routeFor( "/bom/linha/{bomLinha.id}/confirm-cancelar" )
					.withParameter( "bomLinha.id" )
					.matching( "\\d+" )
					.is( BomController.class )
					.confirmCancelarPreenchimentoLinha( null );
				routeFor( "/bom/linha/{bomLinha.id}" )
					.withParameter( "bomLinha.id" )
					.matching( "\\d+" )
					.is( BomController.class )
					.linha( null );
				routeFor( "/bom/linha/save" )
					.with( HttpMethod.POST )
					.is( BomController.class )
					.salvarLinha( null, null );
				routeFor( "/bom/linha/secoes/{bomLinha.id}" )
					.withParameter( "bomLinha.id" )
					.matching( "\\d+" )
					.is( BomController.class )
					.secoes( null );
				routeFor( "/bom/secao/{bomSecao.id}" )
					.withParameter( "bomSecao.id" )
					.matching( "\\d+" )
					.is( BomController.class )
					.secao( null );
				routeFor( "/bom/secao/save" ).with( HttpMethod.POST ).is( BomController.class ).salvarSecao( null );
				routeFor( "/bom/formUpload" ).with( HttpMethod.POST ).is( BomController.class ).formUpload( null );
				routeFor( "/bom/upload" ).is( BomController.class ).upload( null, null );
				routeFor( "/bom/pendentes" ).with( HttpMethod.POST ).is( BomController.class ).pendentes( null );
				routeFor( "/bom/exportarPendentes" )
					.with( HttpMethod.POST )
					.is( BomController.class )
					.exportarPendentes( null );
				routeFor( "/bom/exportar" ).with( HttpMethod.POST ).is( BomController.class ).exportar( null );
				routeFor( "/bom/listLinhasRecriar" )
					.with( HttpMethod.POST )
					.is( BomController.class )
					.listLinhasRecriar( null );
				routeFor( "/bom/recriarLinhas" )
					.with( HttpMethod.POST )
					.is( BomController.class )
					.recriarLinhas( null, null );

				// RELATORIO
				routeFor( "/relatorio/exportar" )
					.with( HttpMethod.POST )
					.is( RelatorioController.class )
					.exportar( null );

				// Configuração
				routeFor( "/configuracao/list" ).is( ConfiguracaoController.class ).list();
				routeFor( "/configuracao/bom" ).is( ConfiguracaoController.class ).bom();
				routeFor( "/configuracao/salvar" )
					.with( HttpMethod.POST )
					.is( ConfiguracaoController.class )
					.salvarConfiguracoes( null );

				// Configuração
				routeFor( "/manual/download" ).is( ManualController.class ).download();

				// Tarifa Retroativa
				// routeFor( "/tarifaRetroativa/list" ).is( TarifaController.class ).list( null );
				routeFor( "/tarifaRetroativa/formUpload" ).is( TarifaController.class ).tarifaRetroativa();

				// Importa Linha
				routeFor( "/importaLinha/formUpload" ).is( LinhaController.class ).importaLinha();

			}
		};
	}

}
