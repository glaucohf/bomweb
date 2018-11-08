package br.com.fetranspor.bomweb.business;

import br.com.caelum.vraptor.ioc.Component;
import br.com.decatron.framework.business.VRaptorBusiness;
import br.com.decatron.framework.exception.DaoException;
import br.com.decatron.framework.provider.VRaptorProvider;
import br.com.fetranspor.bomweb.dao.TipoDeLinhaDAO;
import br.com.fetranspor.bomweb.dao.TipoDeVeiculoDAO;
import br.com.fetranspor.bomweb.entity.Linha;
import br.com.fetranspor.bomweb.entity.TipoDeLinha;
import br.com.fetranspor.bomweb.entity.TipoDeLinhaTipoDeVeiculo;
import br.com.fetranspor.bomweb.entity.TipoDeVeiculo;

import java.util.List;

/**
 * The Class TipoDeLinhaBusiness.
 */
@Component
public class TipoDeLinhaBusiness
	extends VRaptorBusiness<TipoDeLinha>
{

	/**
	 * Instantiates a new tipo de linha business.
	 *
	 * @param provider
	 *            the provider
	 * @param dao
	 *            the dao
	 * @param tipoDeVeiculoDAO
	 *            the tipo de veiculo dao
	 */
	public TipoDeLinhaBusiness(
		final VRaptorProvider provider,
		final TipoDeLinhaDAO dao,
		final TipoDeVeiculoDAO tipoDeVeiculoDAO )
	{
		super( provider );
		this.dao = dao;
		this.tipoDeVeiculoDAO = tipoDeVeiculoDAO;
	}

	/**
	 * Existe tipo linha ativa usando tipo veiculo.
	 *
	 * @param idTipoVeiculo
	 *            the id tipo veiculo
	 * @return true, if successful
	 */
	public boolean existeTipoLinhaAtivaUsandoTipoVeiculo( final Long idTipoVeiculo )
	{
		return ( ( TipoDeLinhaDAO ) this.dao ).existeTipoLinhaAtivaUsandoTipoVeiculo( idTipoVeiculo );
	}

	/**
	 * Findby code.
	 *
	 * @param sigla
	 *            the sigla
	 * @return the tipo de linha
	 */
	public TipoDeLinha findbyCode( final String sigla )
	{
		return ( ( TipoDeLinhaDAO ) this.dao ).findbySigla( sigla );
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param sigla
	 * @return
	 */
	public TipoDeLinha getTipoDeLinhaBySigla( final String sigla )
	{
		final TipoDeLinha tipoDeLinhaBySigla = ( ( TipoDeLinhaDAO ) this.dao ).getTipoDeLinhaBySigla( sigla );
		return tipoDeLinhaBySigla;
	}

	/**
	 * Listar tipos de linha.
	 *
	 * @param linha
	 *            the linha
	 * @return the list
	 */
	public List<TipoDeLinha> listarTiposDeLinha( final Linha linha )
	{
		return ( ( TipoDeLinhaDAO ) this.dao ).listarTiposDeLinha( linha );
	}

	/**
	 * Lista tipo veiculo.
	 *
	 * @param idTipoLinha
	 *            the id tipo linha
	 * @return the list
	 */
	public List<TipoDeVeiculo> listaTipoVeiculo( final Long idTipoLinha )
	{
		final TipoDeLinha tipoDeLinha = idTipoLinha == null ? null : this.dao.find( idTipoLinha );
		final List<TipoDeVeiculo> tiposDeVeiculo = this.tipoDeVeiculoDAO.search( null );
		for ( final TipoDeVeiculo tipoDeVeiculo : tiposDeVeiculo )
		{
			if ( tipoDeLinha != null )
			{
				tipoDeVeiculo.setSelecionado( tipoDeLinha.getTiposDeVeiculoPermitidos().contains( tipoDeVeiculo ) );
			}
		}

		return tiposDeVeiculo;
	}

	/**
	 * Recupera tipos linha selecionados.
	 *
	 * @param tiposLinhaSelecionados
	 *            the tipos linha selecionados
	 * @return the list
	 */
	public List<TipoDeLinha> recuperaTiposLinhaSelecionados( final List<TipoDeLinha> tiposLinhaSelecionados )
	{
		final List<TipoDeLinha> tiposLinha = this.dao.search( null );

		if ( tiposLinhaSelecionados != null )
		{
			for ( final TipoDeLinha tipoLinha : tiposLinha )
			{
				final boolean selecionado = tiposLinhaSelecionados.contains( tipoLinha );
				tipoLinha.setSelecionado( selecionado );
			}
		}

		return tiposLinha;
	}

	/**
	 * Save.
	 *
	 * @param tipoDeLinha
	 *            the tipo de linha
	 * @param tiposDeVeiculo
	 *            the tipos de veiculo
	 * @throws DaoException
	 *             the dao exception
	 */
	public void save( final TipoDeLinha tipoDeLinha, final List<TipoDeVeiculo> tiposDeVeiculo )
		throws DaoException
	{
		tipoDeLinha.setTiposDeVeiculoPermitidos( tiposDeVeiculo );
		this.dao.save( tipoDeLinha );
	}

	/**
	 * Seleciona utilizados.
	 *
	 * @param entity
	 *            the entity
	 * @return the list
	 */
	public List<TipoDeLinha> selecionaUtilizados( final Linha entity )
	{
		final List<TipoDeLinha> tiposDeLinha = entity.getLinhaVigente().getTiposDeLinha();
		for ( final TipoDeLinha tipoDeLinha : tiposDeLinha )
		{
			final List<TipoDeLinhaTipoDeVeiculo> tltipoVeiculo = tipoDeLinha.getTiposDeLinhaTiposVeiculo();
			for ( final TipoDeLinhaTipoDeVeiculo tipoDeLinhaTipoDeVeiculo : tltipoVeiculo )
			{
				final TipoDeVeiculo veiculo = tipoDeLinhaTipoDeVeiculo.getTipoDeVeiculo();
				final boolean selecionado = tipoDeLinha
					.getTiposDeVeiculoUtilizados( entity.getLinhaVigente() )
					.contains( tipoDeLinhaTipoDeVeiculo.getTipoDeVeiculo() );
				tipoDeLinhaTipoDeVeiculo.setTipoDeVeiculo( new TipoDeVeiculo(
					veiculo.getId(),
					veiculo.getDescricao(),
					selecionado ) );
			}
		}
		return tiposDeLinha;
	}

	/**
	 * Tipo de linha ja existe.
	 *
	 * @param sigla
	 *            the sigla
	 * @return true, if successful
	 */
	public boolean tipoDeLinhaJaExiste( final String sigla )
	{
		return ( ( TipoDeLinhaDAO ) this.dao ).tipoDeLinhaJaExiste( sigla );
	}

	/**
	 * Tipo de linha ja existe.
	 *
	 * @param sigla
	 *            the sigla
	 * @param id
	 *            the id
	 * @return true, if successful
	 */
	public boolean tipoDeLinhaJaExiste( final String sigla, final Long id )
	{
		return ( ( TipoDeLinhaDAO ) this.dao ).tipoDeLinhaJaExiste( sigla, id );
	}

	/**
	 * Update.
	 *
	 * @param tipoDeLinha
	 *            the tipo de linha
	 * @param tiposDeVeiculo
	 *            the tipos de veiculo
	 * @throws DaoException
	 *             the dao exception
	 */
	public void update( final TipoDeLinha tipoDeLinha, final List<TipoDeVeiculo> tiposDeVeiculo )
		throws DaoException
	{
		final TipoDeLinha tipoDeLinhaPersistida = this.dao.get( tipoDeLinha.getId() );

		final List<TipoDeLinhaTipoDeVeiculo> tltiposVeiculos = tipoDeLinhaPersistida.getTiposDeLinhaTiposVeiculo();
		for ( final TipoDeLinhaTipoDeVeiculo tltipoVeiculo : tltiposVeiculos )
		{
			if ( !tiposDeVeiculo.contains( tltipoVeiculo.getTipoDeVeiculo() ) )
			{
				( ( TipoDeLinhaDAO ) this.dao ).deleteTipoLinhaTipoVeiculo( tltipoVeiculo );
			}
			else
			{
				( ( TipoDeLinhaDAO ) this.dao ).ativaTipoLinhaTipoVeiculo( tltipoVeiculo );
			}
		}

		tipoDeLinhaPersistida.setTiposDeVeiculoPermitidos( tiposDeVeiculo );
		tipoDeLinhaPersistida.setSigla( tipoDeLinha.getSigla() );
		tipoDeLinhaPersistida.setDescricao( tipoDeLinha.getDescricao() );

		this.dao.update( tipoDeLinhaPersistida );
	}

	/**
	 * The tipo de veiculo dao.
	 */
	private final TipoDeVeiculoDAO tipoDeVeiculoDAO;

}
