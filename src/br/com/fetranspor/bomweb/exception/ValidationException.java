package br.com.fetranspor.bomweb.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class ValidationException.
 */
public class ValidationException
	extends Exception
{

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1365384242575080500L;

	/**
	 * Instantiates a new validation exception.
	 *
	 * @param validationErrors
	 *            the validation errors
	 */
	public ValidationException( final List<String> validationErrors )
	{
		super( validationErrors.toString() );
		this.validationErrors = validationErrors;
	}

	/**
	 * Instantiates a new validation exception.
	 *
	 * @param message
	 *            the message
	 */
	public ValidationException( final String message )
	{
		super( message );
		this.validationErrors = new ArrayList<String>();
		this.validationErrors.add( message );
	}

	/**
	 * Gets the validations errors.
	 *
	 * @return the validations errors
	 */
	public List<String> getValidationsErrors()
	{
		return this.validationErrors;
	}

	/**
	 * The validation errors.
	 */
	private final List<String> validationErrors;
}
