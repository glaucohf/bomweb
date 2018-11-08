<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://taglib.decatron.com.br/security" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/listWithDataTable.jsp">
	<tiles:putAttribute name="formLegend" cascade="true">
	<script type="text/javascript"><!--
			//Auto complete da Busca
	
	var listPage;
	$(document).ready(function() {
		listPage = new ListPage("${pageContext.request.contextPath}/tarifa", "tarifa");
		
		listPage.setSearchFieldsIds(["idEmpresa", "idLinha", "secao", "filtroTarifaFutura", "selectValorListTarifa", "selectValorListTarifaFutura"]);
		//listPage.setLastRowIndex(9);
		listPage.addColumnDefinition("Código da Linha", "codigoLinha", {"sWidth": "12%"});
		listPage.addColumnDefinition("Empresa", "empresa");
		listPage.addColumnDefinition("Linha", "linha");
		listPage.addColumnDefinition("Código da Seção", "codigoSecao", {"sWidth": "8%"});
		listPage.addColumnDefinition("Seção", "secao");
		listPage.addColumnDefinition("Valor", "valor", {"sWidth": "7%"});
		listPage.addColumnDefinition("Início Vigência", "inicioVigencia", {"sWidth": "10%"});
		listPage.addColumnDefinition("Fim Vigência", "fimVigencia", {"sWidth": "10%"});

		listPage.setCreateEditLinkCallback(function(row) {
			if (row.aData.podeEditar) {
				if (row.aData.isFutura || row.aData.temTarifaFutura) {
					return "<a href='erro-edit-futura/" + row.aData.id + "' onclick='javascript:ListPage.showModalPanel(this, event)' title='Editar'><img src='../images/editar.png'/></a> &nbsp;";
				}
				else {
					return "<a href='edit/" + row.aData.id + "' title='Editar'><img src='../images/editar.png' alt='Editar' /></a> &nbsp;";
				}
			}
			else {
				return "";
			}
		});

		listPage.setCreateHistoryLinkCallback(function(row) {
			if (row.aData.podeVerHistorico) {
				return "<a href='historico/" + row.aData.id + "' onclick='javascript:ListPage.showModalPanel(this, event)' data-width='650' data-height='350' title='Histórico'><img src='../images/historico.png' alt='Histórico' /></a>";
			}
			else {
				return "";
			}
		});

	});
			
	$(function() {

		$( "#buscaEmp" ).autocomplete({
			width:352,
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
								label: item.nome,
								data: item.id,
								value: item.nome									
							};
						}));
					}
				});
			},
			minLength: 0,
			select: function(event, ui) { 
				$("#buscaLinha").removeAttr("disabled");	
				$("#idEmpresa").val(ui.item.data);
			 },
			open: function() {
				//$( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
			},
			close: function() {
				//$( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
			}
		});
	});
	
	function changeValorTarifas(checkbox){
		if(checkbox.checked){
			$("#selectValorListTarifa").hide();
			$("#selectValorListTarifaFutura").show();
		}else{
			$("#selectValorListTarifa").show();
			$("#selectValorListTarifaFutura").hide();
		}
	}

	$(function() {

		$( "#buscaLinha" ).autocomplete({
			width:252,
			source: function( request, response )
			{
				$.ajax({
					url: "${pageContext.request.contextPath}/tarifa/buscaLinhas.json?term="+$( "#buscaLinha" ).val() + "&idEmpresa="+$("#idEmpresa").val(),
					dataType: "json",
					data: {
						featureClass: "P",
						style: "full",
						maxRows: 12,
						name_startsWith: request.term
					},
					success: function( data )
					{
						response( $.map( data.linhas, function( item )
						{
							// FBW-319
							var textToShow = item.comboString;
							return {
								label: textToShow,
								data: item.id,
								value: textToShow									
							};
						}));
					}
				});
			},
			minLength: 0,
			select: function(event, ui) { 
				$("#idLinha").val(ui.item.data);
			 },
			open: function() {
				//$( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
			},
			close: function() {
				bomweb.updateChildSelect($('#idLinha'), $("#secao"), "${pageContext.request.contextPath}/tarifa/buscaSecoes.json" , "id", ["codigo", "juncao"]);
			}
		});
	});
		
	</script>
	Tarifa
	</tiles:putAttribute>
	<tiles:putAttribute name="formAction" cascade="true">
		<c:url value="/tarifa" />
	</tiles:putAttribute>	
	<tiles:putAttribute name="searchFormFields" cascade="true">
		<security:notHasRole roleName="Empresa">
		<p>
			<label for="buscaEmp">Empresa:</label>
			<input id="buscaEmp" name="filtro.nomeEmpresa" value="${filtro.nomeEmpresa}" onblur="limpaId(this,'idEmpresa')" style="width:350px;"/>
			<input style="visibility: hidden;" type="text" id="idEmpresa" name="filtro.empresa" value="${filtro.empresa}" />
		</p>
		</security:notHasRole>
		<p>
			<label for="buscaLinha">Linha:</label>
			<input type="text" id="buscaLinha" <c:if test="${empty filtro.nomeEmpresa}">disabled="disabled"</c:if> name="filtro.nomeLinha" value="${filtro.nomeLinha}" onblur="limpaId(this,'idLinha')" style="width:250px;"/> 
			
			<img class="loading" alt="Carregando" src="<c:url value="/images/carregando.gif" />">
			<input style="visibility: hidden;" type="text" id="idLinha" name="filtro.linha" value="${filtro.linha}"/>
		</p>
		<p>
			<label for="secao">Seção:</label>
			<select id="secao" name="filtro.secao" <c:if test="${empty secoes}">disabled="disabled"</c:if>>
				<option value="">Selecione</option>
				<c:forEach var="item" items="${secoes}">
					<option value="${item.id}" <c:if test="${item.id == filtro.secao}">selected="selected"</c:if>><c:out value="${item.codigo} - ${item.juncao}"></c:out></option>
				</c:forEach>
			</select>
			<img class="loading" alt="Carregando" src="<c:url value="/images/carregando.gif" />">
		</p>
		<p>
			<label for="empresa">Tarifa futura:</label>
			<input type="checkbox" id="filtroTarifaFutura" name="filtro.tarifaFutura" onchange="javascript:changeValorTarifas(this)" <c:if test="${filtro.tarifaFutura}">checked="checked"</c:if>/>
		</p>
		<p>
			<label for="selectValorListTarifa">Valor:</label>
			<select id="selectValorListTarifa" name="filtro.valor" <c:if test="${filtro.tarifaFutura}">style="display: none;"</c:if>>
				<option value="" selected="selected">Selecione</option>
				<c:forEach var="item" items="${tarifasVigentes}">
					<option value="${item}" <c:if test="${item == filtro.valor}">selected="selected"</c:if>><fmt:formatNumber minFractionDigits="2" maxFractionDigits="2" value="${item}"/></option>
				</c:forEach>
			</select>
			<select id="selectValorListTarifaFutura" <c:if test="${!filtro.tarifaFutura}">style="display: none;"</c:if> name="filtro.valorFutura">
				<option value="" selected="selected">Selecione</option>
				<c:forEach var="item" items="${tarifasFuturas}">
					<option value="${item}" <c:if test="${item == filtro.valorFutura}">selected="selected"</c:if>><fmt:formatNumber minFractionDigits="2" maxFractionDigits="2" value="${item}"/></option>
				</c:forEach>
			</select>
		</p>
	</tiles:putAttribute>
	<tiles:putAttribute name="beforeTable" cascade="true">
		<security:hasPermission target="Tarifa" action="incluir">
			<a id="insert" href="<c:url value="/tarifa/insert" />" class="buttom_azul">Nova Tarifa</a>
		</security:hasPermission>
		<security:hasPermission target="Tarifa" action="upload"> 
			<a id="upload"  href="<c:url value="/tarifa/formUpload" />" >Importar</a>
		</security:hasPermission>
	</tiles:putAttribute>
</tiles:insertTemplate>