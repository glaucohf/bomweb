package br.com.fetranspor.bomweb.carga;

import br.com.decatron.framework.util.Check;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class SQLLoadGenerator.
 */
public class SQLLoadGenerator
{

	/**
	 * The yyyy m mdd formatter.
	 */
	private static SimpleDateFormat yyyyMMddFormatter = new SimpleDateFormat( "yyyy-MM-dd" );

	/**
	 * The Constant DIRETORIO_ORIGEM.
	 */
	private static final String DIRETORIO_ORIGEM = "C:\\Users\\luiz.silva\\Documents\\Projetos\\Fetranspor\\BOMWEB\\Nova Carga\\";

	/**
	 * The Constant DIRETORIO_DESTINO.
	 */
	private static final String DIRETORIO_DESTINO = "C:\\Users\\luiz.silva\\Documents\\Projetos\\Fetranspor\\BOMWEB\\Nova Carga\\Carga\\";

	/**
	 * Logger for this class
	 */
	private static final Log LOG = LogFactory.getLog( SQLLoadGenerator.class );

	/**
	 * Instantiates a new SQL load generator.
	 */
	public SQLLoadGenerator()
	{
		this.codsEmpresas = new HashSet<String>();
		this.codsLinhas = new HashSet<String>();
		this.insertsEmpresas = new ArrayList<String>();
		this.insertsLinhas = new ArrayList<String>();
		this.insertsLinhasVigenciaTiposLinhas = new ArrayList<String>();
		this.insertsSecoesTarifas = new ArrayList<String>();
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 * @throws Exception
	 *             the exception
	 */
	public static void main( final String[] args )
		throws Exception
	{
		final SQLLoadGenerator g = new SQLLoadGenerator();
		g.reader = new XLSReader();
		g.generate( DIRETORIO_ORIGEM + "BASE FINAL BOMWEB_20121206-MOD.xls" );
	}

	/**
	 * Adicionar insert empresa.
	 *
	 * @param linhaRecord
	 *            the linha record
	 */
	private void adicionarInsertEmpresa( final LinhaRecord linhaRecord )
	{
		if ( this.codsEmpresas.contains( linhaRecord.getCodEmpresa() ) )
		{
			return;
		}

		this.codsEmpresas.add( linhaRecord.getCodEmpresa() );

		String insertEmpresa = "Insert Into empresas (ACTIVE, CODIGO, DATA_CRIACAO, EMAIL_CONTATO, NOME, RESPONSAVEL, INICIO_VIGENCIA_BOM) Values (1, ";
		insertEmpresa += "'" + linhaRecord.getCodEmpresa() + "', ";
		insertEmpresa += "'" + yyyyMMddFormatter.format( linhaRecord.getDtCriacao() ) + "', ";
		insertEmpresa += "'', ";
		insertEmpresa += "'" + linhaRecord.getNmEmpresa() + "', ";
		insertEmpresa += "'', '2012-01-01');";

		this.insertsEmpresas.add( insertEmpresa );

		if ( linhaRecord.getNovaLinha() != null )
		{
			adicionarInsertEmpresa( linhaRecord.getNovaLinha() );
		}
	}

	/**
	 * Adicionar insert linha.
	 *
	 * @param linhaRecord
	 *            the linha record
	 * @param temLinhaVigenciaAntiga
	 *            the tem linha vigencia antiga
	 */
	private void adicionarInsertLinha( final LinhaRecord linhaRecord, final boolean temLinhaVigenciaAntiga )
	{
		if ( this.codsLinhas.contains( linhaRecord.getCodLinha() ) )
		{
			return;
		}

		if ( LOG.isInfoEnabled() )
		{
			LOG.info( linhaRecord.getCodSecao() );
		}
		if ( ( linhaRecord.getNovaLinha() == null )
			|| !linhaRecord.getCodLinha().equals( linhaRecord.getNovaLinha().getCodLinha() ) )
		{
			this.codsLinhas.add( linhaRecord.getCodLinha() );
		}

		String insertLinha = "";
		if ( !temLinhaVigenciaAntiga )
		{
			insertLinha = "Insert Into linhas (ACTIVE) Values (1);\n";
		}

		insertLinha += "Insert Into linha_vigencia (ACTIVE, CODIGO, DATA_CRIACAO, DATA_INICIO, DATA_TERMINO, HIERARQUIZACAO, "
			+ "IDLINHAVIGENCIAANTIGA, NUMEROLINHA, OBSERVACAO, PISO1AB, PISO1BA, PISO2AB, PISO2BA, PONTO_INICIAL, PONTO_FINAL, "
			+ "STATUS, TIPOLIGACAO, VIA, EMPRESA_ID, LINHA_ID) Values (1, ";
		insertLinha += "'" + linhaRecord.getCodLinha() + "', ";
		insertLinha += "'" + yyyyMMddFormatter.format( linhaRecord.getDtCriacao() ) + "', ";
		insertLinha += "'" + yyyyMMddFormatter.format( linhaRecord.getDtInicio() ) + "', ";
		insertLinha += linhaRecord.getDtTermino() == null ? "NULL, " : "'"
			+ yyyyMMddFormatter.format( linhaRecord.getDtTermino() )
			+ "', ";
		insertLinha += "'', ";
		insertLinha += temLinhaVigenciaAntiga
			? "(select lv.id from (Select MAX(ID) as id From linha_vigencia) as lv), "
			: "NULL, ";
		insertLinha += "'" + linhaRecord.getNumLinha() + "', ";
		insertLinha += "NULL, ";
		insertLinha += ( Check.isBlank( linhaRecord.getPiso1() ) ? 0 : linhaRecord.getPiso1() ) + ", 0, ";
		insertLinha += ( Check.isBlank( linhaRecord.getPiso2() ) ? 0 : linhaRecord.getPiso2() ) + ", 0, ";
		final String pontoInicial = linhaRecord.getNmSecao().split( "-" )[0].trim();
		final String pontoFinal = linhaRecord.getNmSecao().split( "-" )[1].trim();
		insertLinha += "'" + pontoInicial + "', '" + pontoFinal + "', ";
		insertLinha += ( !"Ativa".equals( linhaRecord.getStatus().trim() ) && ( linhaRecord.getNovaLinha() == null )
			? 1
			: 0 ) + ", ";
		insertLinha += linhaRecord.getCodLinha().endsWith( "00" ) ? "'Linha', " : "'Serviço Complementar', ";
		insertLinha += "'" + linhaRecord.getVia() + "', ";
		insertLinha += "(Select ID From empresas Where Codigo = '" + linhaRecord.getCodEmpresa() + "'), ";
		insertLinha += "(Select MAX(ID) From linhas));";

		this.insertsLinhas.add( insertLinha );

		adicionarInsertLinhaVigenciaTiposLinha( linhaRecord );

		if ( linhaRecord.getNovaLinha() != null )
		{
			adicionarInsertLinha( linhaRecord.getNovaLinha(), true );
		}

	}

	/**
	 * Adicionar insert linha vigencia tipos linha.
	 *
	 * @param linhaRecord
	 *            the linha record
	 */
	private void adicionarInsertLinhaVigenciaTiposLinha( final LinhaRecord linhaRecord )
	{
		String insertLinhaVigenciaTiposLinha = "Insert Into linha_vigencia_tipos_linha (ACTIVE, LINHAVIGENCIA_ID, TIPODELINHA_ID) Values (1, ";
		insertLinhaVigenciaTiposLinha += "(Select ID From linha_vigencia Where CODIGO = '"
			+ linhaRecord.getCodLinha()
			+ "' and DATA_INICIO = '"
			+ yyyyMMddFormatter.format( linhaRecord.getDtInicio() )
			+ "'), ";
		insertLinhaVigenciaTiposLinha += "(Select ID From tipos_linha Where SIGLA = '"
			+ linhaRecord.getTipoLinha()
			+ "'));";

		this.insertsLinhasVigenciaTiposLinhas.add( insertLinhaVigenciaTiposLinha );
	}

	/**
	 * Adicionar insert secoes tarifas.
	 *
	 * @param linhaRecord
	 *            the linha record
	 * @param temSecaoAntiga
	 *            the tem secao antiga
	 */
	private void adicionarInsertSecoesTarifas( final LinhaRecord linhaRecord, final boolean temSecaoAntiga )
	{

		String insertSecaoTarifa = "Insert Into secoes (ACTIVE, CODIGO, DATA_CRIACAO, IDSECAOORIGEM, JUNCAO, PONTOINICIAL, PONTOFINAL, LINHAVIGENCIA_ID) Values (1, ";

		insertSecaoTarifa += "'" + linhaRecord.getSecao() + "', ";
		insertSecaoTarifa += "'" + yyyyMMddFormatter.format( linhaRecord.getDtCriacao() ) + "', ";
		insertSecaoTarifa += temSecaoAntiga ? "(select s.id from (Select MAX(ID) as id From secoes) as s), " : "NULL, ";
		insertSecaoTarifa += "NULL, ";
		final String pontoInicial = linhaRecord.getNmSecao().split( "-" )[0].trim();
		final String pontoFinal = linhaRecord.getNmSecao().split( "-" )[1].trim();
		insertSecaoTarifa += "'" + pontoInicial + "', '" + pontoFinal + "', ";
		insertSecaoTarifa += "(Select ID From linha_vigencia Where Codigo = '"
			+ linhaRecord.getCodLinha()
			+ "' and DATA_INICIO = '"
			+ yyyyMMddFormatter.format( linhaRecord.getDtInicio() )
			+ "'));\n";

		insertSecaoTarifa += "Insert Into tarifas (ACTIVE, IDTARIFAANTIGA, INICIOVIGENCIA, FIMVIGENCIA, MOTIVOCRIACAO, VALOR, SECAO_ID) Values (1, ";
		insertSecaoTarifa += temSecaoAntiga
			? "(select t.id from (Select MAX(ID) as id From tarifas) as t), "
			: "NULL, ";
		insertSecaoTarifa += "'" + yyyyMMddFormatter.format( linhaRecord.getDtInicio() ) + "', ";
		insertSecaoTarifa += linhaRecord.getDtTermino() == null ? "NULL, " : "'"
			+ yyyyMMddFormatter.format( linhaRecord.getDtTermino() )
			+ "', ";
		insertSecaoTarifa += "0, ";
		insertSecaoTarifa += linhaRecord.getTarifa() + ", ";
		insertSecaoTarifa += "(Select MAX(ID) From secoes));";

		this.insertsSecoesTarifas.add( insertSecaoTarifa );

		if ( linhaRecord.getNovaLinha() != null )
		{
			adicionarInsertSecoesTarifas( linhaRecord.getNovaLinha(), true );
		}
	}

	/**
	 * Escrever arquivo.
	 *
	 * @param inserts
	 *            the inserts
	 * @param nomeArquivo
	 *            the nome arquivo
	 */
	private void escreverArquivo( final List<String> inserts, final String nomeArquivo )
	{

		final File dir = new File( DIRETORIO_DESTINO );
		final File arq = new File( dir, nomeArquivo );

		if ( arq.exists() )
		{
			arq.delete();
		}

		try
		{
			arq.createNewFile();

			final FileWriter fileWriter = new FileWriter( arq, false );
			final PrintWriter printWriter = new PrintWriter( fileWriter );

			for ( final String insert : inserts )
			{
				printWriter.println( insert );
			}

			printWriter.flush();
			printWriter.close();

		}
		catch ( final IOException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
		}
	}

	/**
	 * Generate.
	 *
	 * @param filePath
	 *            the file path
	 * @throws Exception
	 *             the exception
	 */
	private void generate( final String filePath )
		throws Exception
	{
		final File file = new File( filePath );
		final List<LinhaRecord> data = this.reader.readData( file );

		for ( final LinhaRecord linhaRecord : data )
		{
			adicionarInsertEmpresa( linhaRecord );
			adicionarInsertLinha( linhaRecord, false );
			adicionarInsertSecoesTarifas( linhaRecord, false );
		}

		escreverArquivo( this.insertsEmpresas, "Script(3) Carga BOM-WEB EMPRESAS.txt" );
		escreverArquivo( this.insertsLinhas, "Script(4) Carga BOM-WEB LINHAS e LINHA_VIGENCIA.txt" );
		escreverArquivo(
			this.insertsLinhasVigenciaTiposLinhas,
			"Script(5) Carga BOM-WEB LINHA_VIGENCIA_TIPOS_LINHA.txt" );
		escreverArquivo( this.insertsSecoesTarifas, "Script(6) Carga BOM-WEB SECOES e TARIFAS.txt" );
	}

	/**
	 * The reader.
	 */
	Reader reader;

	/**
	 * The cods empresas.
	 */
	Set<String> codsEmpresas;

	/**
	 * The cods linhas.
	 */
	Set<String> codsLinhas;

	/**
	 * The inserts empresas.
	 */
	List<String> insertsEmpresas;

	/**
	 * The inserts linhas.
	 */
	List<String> insertsLinhas;

	/**
	 * The inserts linhas vigencia tipos linhas.
	 */
	List<String> insertsLinhasVigenciaTiposLinhas;

	/**
	 * The inserts secoes tarifas.
	 */
	List<String> insertsSecoesTarifas;

}
