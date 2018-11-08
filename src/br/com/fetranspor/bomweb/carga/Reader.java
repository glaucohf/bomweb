package br.com.fetranspor.bomweb.carga;

import java.io.File;
import java.util.List;

/**
 * The Interface Reader.
 */
public interface Reader
{

	/**
	 * Read data.
	 *
	 * @param file
	 *            the file
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<LinhaRecord> readData( File file )
		throws Exception;
}
