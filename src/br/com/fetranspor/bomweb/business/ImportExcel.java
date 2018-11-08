package br.com.fetranspor.bomweb.business;

import br.com.fetranspor.bomweb.dto.ImportDTO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class ImportExcel.
 */
public class ImportExcel
{

	/**
	 * Logger for this class
	 */
	private static final Log LOG = LogFactory.getLog( ImportExcel.class );

	/**
	 * Content reading.
	 *
	 * @param fileInputStream
	 *            the file input stream
	 * @return the list
	 */
	public List<ImportDTO> contentReading( final InputStream fileInputStream )
	{
		final List<ImportDTO> listImport = new ArrayList<ImportDTO>();
		WorkbookSettings ws = null;
		Workbook workbook = null;
		Sheet s = null;
		Cell rowData[] = null;
		int rowCount = 0;
		int totalSheet = 0;

		try
		{
			ws = new WorkbookSettings();
			ws.setLocale( new Locale( "en", "EN" ) );
			ws.setEncoding( "ISO-8859-1" );
			workbook = Workbook.getWorkbook( fileInputStream, ws );

			totalSheet = workbook.getNumberOfSheets();
			if ( totalSheet > 0 )
			{
				if ( LOG.isInfoEnabled() )
				{
					LOG.info( "Total Sheet Found:" + totalSheet );
				}
				for ( int j = 0; j < totalSheet; j++ )
				{
					if ( LOG.isInfoEnabled() )
					{
						LOG.info( "Sheet Name:" + workbook.getSheet( j ).getName() );
					}
				}
			}

			// Getting Default Sheet i.e. 0
			s = workbook.getSheet( 0 );

			// Reading Individual Cell
			getHeadingFromXlsFile( s );

			// Total Total No Of Rows in Sheet, will return you no of rows that are occupied with
			// some data
			if ( LOG.isInfoEnabled() )
			{
				LOG.info( "Total Rows inside Sheet:" + s.getRows() );
			}
			rowCount = s.getRows();

			// Total Total No Of Columns in Sheet
			if ( LOG.isInfoEnabled() )
			{
				LOG.info( "Total Column inside Sheet:" + s.getColumns() );
			}
			s.getColumns();

			ImportDTO importDTO;

			// Reading Individual Row Content - skipping header
			for ( int i = 1; i < rowCount; i++ )
			{
				// Get Individual Row
				rowData = s.getRow( i );
				importDTO = new ImportDTO();
				final List<String> values = new ArrayList<String>();
				for ( final Cell element : rowData )
				{
					values.add( element.getContents() );
				}
				importDTO.setValues( values );
				listImport.add( importDTO );
			}
			workbook.close();
		}
		catch ( final IOException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
		}
		catch ( final BiffException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
		}
		return listImport;
	}

	// Returns the Headings used inside the excel sheet
	/**
	 * Gets the heading from xls file.
	 *
	 * @param sheet
	 *            the sheet
	 * @return the heading from xls file
	 */
	private void getHeadingFromXlsFile( final Sheet sheet )
	{
		final int columnCount = sheet.getColumns();
		for ( int i = 0; i < columnCount; i++ )
		{
			if ( LOG.isInfoEnabled() )
			{
				LOG.info( sheet.getCell( i, 0 ).getContents() );
			}
		}
	}

	/**
	 * Ler arquivo.
	 *
	 * @param filePath
	 *            the file path
	 * @return the list
	 */
	private List<ImportDTO> lerArquivo( final String filePath )
	{
		List<ImportDTO> li = new ArrayList<ImportDTO>();
		FileInputStream fs = null;
		try
		{
			fs = new FileInputStream( new File( filePath ) );
			li = contentReading( fs );
		}
		catch ( final IOException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
		}
		catch ( final Exception e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
		}
		finally
		{
			try
			{
				fs.close();
			}
			catch ( final IOException e )
			{
				if ( LOG.isErrorEnabled() )
				{
					LOG.error( e, e );
				}// if
			}
		}
		return li;
	}

	/**
	 * Load arquivo.
	 *
	 * @param arquivo
	 *            the arquivo
	 * @return the list
	 */
	public List<ImportDTO> loadArquivo( final File arquivo )
	{
		try
		{
			final ImportExcel xlReader = new ImportExcel();
			final List<ImportDTO> listImport = xlReader.lerArquivo( arquivo.getAbsolutePath() );
			return listImport;
		}
		catch ( final Exception e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			return null;
		}
	}
}
