package br.com.fetranspor.bomweb.util;

import br.com.decatron.framework.exception.MailException;
import br.com.decatron.framework.mail.Mail;
import br.com.decatron.framework.util.Property;

/**
 * <p>
 * </p>
 *
 * @author fred
 * @version 1.0 Created on Jul 16, 2015
 */
public class UtilitiesMail
{

	/**
	 * <p>
	 * Field <code>DEFAULT_HEADER</code>
	 * </p>
	 */
	private static String DEFAULT_HEADER;

	/**
	 * <p>
	 * Field <code>DEFAULT_FOOTER</code>
	 * </p>
	 */
	private static String DEFAULT_FOOTER;

	static
	{
		final Property propertyDummy = new Property();
		final Property realProperty = propertyDummy.from( "bomweb.properties" );
		DEFAULT_HEADER = realProperty.getProperties( "email_cabecalho" );
		DEFAULT_FOOTER = realProperty.getProperties( "email_rodape" );
	}

	/**
	 * <p>
	 * </p>
	 */
	protected UtilitiesMail()
	{
		super();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param from
	 * @param to
	 * @param subject
	 * @param body
	 * @throws MailException
	 */
	public static void sendMail( final String from, final String to, final String subject, final String body )
		throws MailException
	{
		UtilitiesMail.sendMail( from, to, subject, body, null );
	}// func

	/**
	 * <p>
	 * </p>
	 *
	 * @param from
	 * @param to
	 * @param subject
	 * @param body
	 * @param attachmentPath
	 * @throws MailException
	 */
	public static void sendMail(
		final String from,
		final String to,
		final String subject,
		final String body,
		final String attachmentPath )
		throws MailException
	{
		final String header = DEFAULT_HEADER;
		final String footer = DEFAULT_FOOTER;

		sendMail( from, to, subject, header, body, footer, attachmentPath );
	}// func

	/**
	 * <p>
	 * </p>
	 *
	 * @param from
	 * @param to
	 * @param subject
	 * @param header
	 * @param body
	 * @param footer
	 * @param attachmentPath
	 * @throws MailException
	 */
	public static void sendMail(
		final String from,
		final String to,
		final String subject,
		final String header,
		final String body,
		final String footer,
		final String attachmentPath )
		throws MailException
	{
		final Mail mail = new Mail()
			.from( from )
			.to( to )
			.withSubject( subject )
			.withHeader( header )
			.withBody( body )
			.withFooter( footer );

		if ( attachmentPath != null )
		{
			mail.withAttachment( attachmentPath );
		}// if
		mail.send();

		if ( attachmentPath != null )
		{
			UtilitiesIO.deleteFile( attachmentPath );
		}// if
	}

}// func

