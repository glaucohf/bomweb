<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://taglib.decatron.com.br/security" %>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/template.jsp">
	<tiles:putAttribute name="header">
		<script type="text/javascript" src="<c:url value="/jscript/jquery.dataTables.min.js" />"></script>
		<script type="text/javascript" src="<c:url value="/jscript/ListPage.js" />"></script>
	</tiles:putAttribute>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
	<tiles:putAttribute name="formAction" cascade="true">
		<c:url value="/bom" />
	</tiles:putAttribute>
	<tiles:putAttribute name="conteudo">
	<script type="text/javascript">

		var listPage;
		$(document).ready(function() {
			listPage = new ListPage(pathName + "/bom/pendentes", "BOM");
			
			listPage.setSearchFieldsIds(["idEmpresa", "mesAnoInicio", "mesAnoFim", "selectStatus"]);
			
			listPage.addColumnDefinition("Empresa", "empresa", {"sWidth": "50%"});
			listPage.addColumnDefinition("Mês/Ano de Referência", "mesAnoReferencia", { fnRender: createRowDateValueRenderFunction( "mesAnoReferencia" ) });
			listPage.addColumnDefinition("Status", "statusPendencia");
			listPage.addColumnDefinition("Limite de Entrega", "dataLimiteFechamento", { fnRender: createRowDateValueRenderFunction( "dataLimiteFechamento" ) });
			listPage.ignoreActionColumn();
			
		});	
		
		//Auto complete da Busca
		$(function() {

			$( "#buscaEmp" ).autocomplete({
				width:352,
				source: function( request, response ) {
					$.ajax({
						url: pathName + "/empresa/busca.json?term="+$( "#buscaEmp" ).val(),
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
				 }
			});
			
			$("#btn_pesquisa").click(function()
				{
					listPage.initialize();
				}
			);

			$("#mesAnoInicio").mask("99/9999");
			$("#mesAnoFim").mask("99/9999");
				
		});
			

	</script>
		<strong class="titulo azul">Pendentes </strong><img src="<c:url value="/images/bomweb_setas.gif" />" /><strong class="titulo verde"> BOM</strong>
		<a href="#" class="accordion">[ Ocultar Filtro ]</a>
		<div id="filtro">
			<img src="<c:url value="/images/bomweb_filtros_box_up.jpg" />" />
			<form id="formPesquisa" action='<c:url value="/bom/pendentes" />' method="post">
				<fieldset>
					<security:notHasRole roleName="Empresa">
					<p>
						<label for="buscaEmp">Empresa:</label>
						<input type="text" id="buscaEmp" name="filtro.nomeEmpresa" value="${filtro.nomeEmpresa}" onblur="limpaId(this,'idEmpresa')" style="width:350px;"/>
						<input type="text" id="idEmpresa" name="filtro.empresa" value="${filtro.empresa}" style="visibility: hidden;"/>
					</p>
					</security:notHasRole>
					<p>
						<label for="datepicker">Mês/Ano Inicial:</label>
						<input type="text" id="mesAnoInicio" value="${filtro.dataInicial}" name="filtro.dataInicial" maxlength="8" size="8">
					</p>
					
					<p>
					    <label for="datepicker">Mês/Ano Final:</label>
						<input type="text" id="mesAnoFim" value="${filtro.dataFinal}" name="filtro.dataFinal" maxlength="8" size="8" >
					</p>
					<p>
						<label for="selectStatus">Status:</label>
						<select id="selectStatus" name="filtro.status">
							<c:forEach var="item" items="${status}">
								<option value="${item}" <c:if test="${item == filtro.status}">selected="selected"</c:if>><c:out value="${item}"/></option>
							</c:forEach>
						</select>
					</p>
		    		<input type="button" name="btn_pesquisa" value="Pesquisar" id="btn_pesquisa">
		    		<input class="reset" type="reset" value="Limpar"/>
		    	</fieldset>
		    	</form>
			<img src="<c:url value="/images/bomweb_filtros_box_dn.jpg" />" />
		</div>
		<br />
		<div id="divDataTable" style="width: 100%; overflow: auto;">
		</div>
		<security:hasPermission target="Bom" action="exportar pendentes" >
		<a id="linkExportPendentes" href="<tiles:insertAttribute name="formAction" ignore="true"/>/exportarPendentes">Exportar</a>
		</security:hasPermission>
	</tiles:putAttribute>
</tiles:insertTemplate>