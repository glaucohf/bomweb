package br.com.fetranspor.bomweb.comparator;

import br.com.decatron.framework.util.Check;
import br.com.fetranspor.bomweb.entity.Tarifa;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;

/**
 * <p>
 * </p>
 * FBW-36.
 *
 * @author ryniere
 * @version 1.0 Created on Jul 29, 2013
 */
public class TarifaComparator
	implements Comparator<Tarifa>
{

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @param o1
	 *            the o1
	 * @param o2
	 *            the o2
	 * @return the int
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare( final Tarifa o1, final Tarifa o2 )
	{
		final Date endDate1 = o1.getFimVigencia();
		final Date endDate2 = o2.getFimVigencia();

		// Queremos retornar a tarifa sem fim de vigência
		if ( Check.isNull( endDate1 ) && Check.isNotNull( endDate2 ) )
		{
			return 1;
		}
		else if ( Check.isNotNull( endDate1 ) && Check.isNull( endDate2 ) )
		{
			return -1;
		}

		final Date startDate1 = o1.getInicioVigencia();
		final Date startDate2 = o2.getInicioVigencia();

		final int compareTo = startDate1.compareTo( startDate2 );

		// Caso as datas sejam iguais deve retornar a de menor valor
		if ( compareTo == 0 )
		{
			final BigDecimal value1 = o1.getValor();
			final BigDecimal value2 = o2.getValor();

			// Retorna o menor valor
			return value1.compareTo( value2 );
		}

		return compareTo;
	}

}
