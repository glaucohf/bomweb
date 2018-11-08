package br.com.fetranspor.bomweb.carga;

import br.com.decatron.framework.util.Check;
import br.com.fetranspor.bomweb.business.ImportExcel;
import br.com.fetranspor.bomweb.dto.ImportDTO;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The Class XLSReader.
 */
public class XLSReader
	implements Reader
{

	/**
	 * The ddmmyyyy formatter.
	 */
	private static SimpleDateFormat ddmmyyyyFormatter = new SimpleDateFormat( "dd/MM/yyyy" );

	/**
	 * The ddmmyy formatter.
	 */
	private static SimpleDateFormat ddmmyyFormatter = new SimpleDateFormat( "dd/MM/yy" );

	/**
	 * The Constant QUANTIDADE_COLUNAS_POR_LINHA.
	 */
	private static final int QUANTIDADE_COLUNAS_POR_LINHA = 18;

	/**
	 * Ler dto.
	 *
	 * @param importDTO
	 *            the import dto
	 * @param colunaEmpresa
	 *            the coluna empresa
	 * @return the linha record
	 * @throws ParseException
	 *             the parse exception
	 */
	private LinhaRecord lerDTO( final ImportDTO importDTO, final int colunaEmpresa )
		throws ParseException
	{
		final LinhaRecord record = new LinhaRecord();
		int coluna = colunaEmpresa;
		record.setCodEmpresa( importDTO.getValues().get( coluna++ ) );
		record.setNmEmpresa( importDTO.getValues().get( coluna++ ) );
		record.setCodLinha( importDTO.getValues().get( coluna++ ) );
		record.setCodSecao( importDTO.getValues().get( coluna++ ) );
		record.setSecao( importDTO.getValues().get( coluna++ ) );
		record.setNumLinha( importDTO.getValues().get( coluna++ ) );
		record.setNmSecao( importDTO.getValues().get( coluna++ ) );
		record.setVia( importDTO.getValues().get( coluna++ ) );
		record.setTipoLinha( importDTO.getValues().get( coluna++ ) );
		coluna++;
		record.setTarifa( Float.parseFloat( importDTO.getValues().get( coluna++ ) ) );
		record.setPiso1( importDTO.getValues().get( coluna++ ) );
		record.setPiso2( importDTO.getValues().get( coluna++ ) );
		final String dtCriacao = importDTO.getValues().get( coluna++ );
		final Date dtInicio = ddmmyyFormatter.parse( importDTO.getValues().get( coluna++ ) );
		record.setDtCriacao( Check.isBlank( dtCriacao ) ? dtInicio : ddmmyyyyFormatter.parse( dtCriacao ) );
		record.setDtInicio( dtInicio );
		final String dtTermino = importDTO.getValues().get( coluna++ );
		record.setDtTermino( Check.isBlank( dtTermino ) ? null : ddmmyyFormatter.parse( dtTermino ) );
		record.setStatus( importDTO.getValues().get( coluna++ ) );

		return record;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param file
	 * @return
	 * @throws Exception
	 * @see br.com.fetranspor.bomweb.carga.Reader#readData(java.io.File)
	 */
	@Override
	public List<LinhaRecord> readData( final File file )
		throws Exception
	{
		final ImportExcel ie = new ImportExcel();
		final List<ImportDTO> dtos = ie.loadArquivo( file );

		final List<LinhaRecord> linhas = new ArrayList<LinhaRecord>();

		for ( final ImportDTO importDTO : dtos )
		{
			if ( Check.isEmpty( importDTO.getValues() ) )
			{
				break;
			}

			int colunaEmpresa = 0;
			final LinhaRecord linha = lerDTO( importDTO, colunaEmpresa );
			colunaEmpresa = colunaEmpresa + QUANTIDADE_COLUNAS_POR_LINHA;
			LinhaRecord novaLinha = null;
			while ( ( importDTO.getValues().size() > colunaEmpresa )
				&& Check.isNotBlank( importDTO.getValues().get( colunaEmpresa ) ) )
			{
				if ( novaLinha == null )
				{
					novaLinha = lerDTO( importDTO, colunaEmpresa );
					linha.setNovaLinha( novaLinha );
				}
				else
				{
					final LinhaRecord linhaAux = novaLinha;
					novaLinha = lerDTO( importDTO, colunaEmpresa );
					linhaAux.setNovaLinha( novaLinha );
				}
				colunaEmpresa = colunaEmpresa + QUANTIDADE_COLUNAS_POR_LINHA;
			}
			linhas.add( linha );
		}

		return linhas;
	}

}
