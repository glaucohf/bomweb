<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://taglib.decatron.com.br/security" %>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/listWithDataTable.jsp">
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
	<tiles:putAttribute name="formLegend" cascade="true">
	<script type="text/javascript">
	<!--
	var listPage;
	$(document).ready(function()
	{
		listPage = new ListPage("${pageContext.request.contextPath}/linha", "linha");
		
		listPage.setSearchFieldsIds(["idEmpresa", "codigo", "pontoFinal", "pontoInicial"]);
		
		listPage.addColumnDefinition("Empresa", "empresa");
		listPage.addColumnDefinition("Linha", "linha");
		listPage.addColumnDefinition("Seções", "secoes");
		listPage.addColumnDefinition("Nome da Linha", "nomeDaLinha");
		listPage.addColumnDefinition("Status", "status");
		listPage.addColumnDefinition("Tipos de Veículos Utilizados", "tiposDeVeiculosUtilizados");
		listPage.addColumnDefinition("Extensão A-B", "extensaoAB");
		listPage.addColumnDefinition("Extensão B-A", "extensaoBA");
		listPage.addColumnDefinition("Data de Início", "dataDeInicio");
		listPage.addColumnDefinition("Data de Término", "dataDeTermino");
		
		listPage.setCreateEditLinkCallback(function(row) {
			if (row.aData.temPerfilDetro || row.aData.temPerfilDetroAdmin || row.aData.temPerfilDetro_nivel_1 || row.aData.temPerfilDetro_nivel_2 || row.aData.temPerfilDetro_nivel_3 || row.aData.temPerfilDetro_aud ) {
				if (row.aData.podeEditar) {
					if (row.aData.temLinhaFutura) {
						return "<a href='edit-confirm/" + row.aData.id + "' onclick='javascript:ListPage.showModalPanel(this, event)' title='Editar'><img src='../images/editar.png' /></a> &nbsp;";
					}
					else {
						return "<a href='edit/" + row.aData.id + "' data-futuro='false' title='Editar'><img src='../images/editar.png' /></a> &nbsp;";
					}
				}
			}
			else if (row.aData.temPerfilEmpresa) {
				return "<a href='edit/" + row.aData.id + "' title='Editar'><img src='../images/editar.png' /></a>";
			}
			else {
				return "";
			}
		});
	});

	// Autocomplete do campo empresa.
	$(function() {
		$("#buscaEmp").autocomplete(
			{
			width: 352,
			source: function( request, response ) {
				$.ajax({
					url: "${pageContext.request.contextPath}/empresa/busca.json?term="+$( "#buscaEmp" ).val(),
					dataType: "json",
					data: {
						featureClass: "P",
						style: "full",
						maxRows: 12,
						name_startsWith: request.term
					},
					success: function( data ) {
						response( $.map( data.empresa, function( item ) {
							return {
								value: item.nome,
								data: item.id,
								label: item.nome,
							};
						}));
					}
				});
			},
			minLength: 0,
			select: function(event, ui) { 
				$("#idEmpresa").val(ui.item.data);
			 },
			open: function() {
				$( this ).removeClass( "ui-corner-all" ).addClass( "autoCompleteSize" );
			},
			close: function() {
				$( this ).removeClass( "ui-corner-top" ).addClass( "autoCompleteSize" );
			}
		});
		
	});

	// -->
	</script>
	
	Linha
	</tiles:putAttribute>
	<tiles:putAttribute name="formAction" cascade="true">
		<c:url value="/linha" />
	</tiles:putAttribute>
	<tiles:putAttribute name="searchFormFields" cascade="true">
		<p>
			<security:notHasRole roleName="Empresa">
				<label for="buscaEmp">Empresa:</label>
				<input type="text" id="buscaEmp" name="entityForSearch.linhaVigente.empresa.nome" value="${entityForSearch.linhaVigente.empresa.nome}" onblur="limpaId(this,'idEmpresa')" style="width:350px;"/>
				<input style="visibility: hidden;" type="text" id="idEmpresa" name="entityForSearch.linhaVigente.empresa.id" value="${entityForSearch.linhaVigente.empresa.id}"/>
			</security:notHasRole>
		</p>
		<p>	
			<label for="codigo">Código da Linha:</label>
			<input id="codigo" name="entityForSearch.linhaVigente.codigo" value="${entityForSearch.linhaVigente.codigo}" size="9" maxlength="9"/>
		</p>
		<p>
			<label for="pontoInicial">Ponto Inicial:</label>
			<input id="pontoInicial" name="entityForSearch.linhaVigente.pontoInicial" value="${entityForSearch.linhaVigente.pontoInicial}">
		</p>
		<p>	
			<label for="pontoFinal">Ponto Final:</label>
			<input id="pontoFinal" name="entityForSearch.linhaVigente.pontoFinal" value="${entityForSearch.linhaVigente.pontoFinal}">
		</p>
	</tiles:putAttribute>
	<tiles:putAttribute name="beforeTable" cascade="true">
		<security:hasPermission target="Linha" action="incluir">
			<a id="insert" href="<c:url value="/linha/insert" />" class="buttom_azul">Nova Linha</a>
		</security:hasPermission>
	</tiles:putAttribute>
	<tiles:putAttribute name="afterTable" cascade="true">
		<c:if test="${not disableExport}">		
			<c:if test="${not empty tList or not empty tarifaList}">
			<a id="linkExportSecao" href="<tiles:insertAttribute name="formAction" ignore="true"/>/exportarComSecoes">Exportar c/ Seções</a>			
			</c:if>
		</c:if>
	</tiles:putAttribute>
</tiles:insertTemplate>	