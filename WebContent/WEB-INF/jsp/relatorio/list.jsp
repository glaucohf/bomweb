<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib prefix="fn" uri="/WEB-INF/custom-functions.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="security" uri="http://taglib.decatron.com.br/security" %>
<%@ taglib prefix="message" uri="http://taglib.decatron.com.br/message" %>
<fmt:setLocale value="pt_BR"/>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/template.jsp">
	<tiles:putAttribute name="header">
		<script type="text/javascript" src="<c:url value="/jscript/jquery.dataTables.min.js" />"></script>
		<script type="text/javascript" src="<c:url value="/jscript/ListPage.js" />"></script>
	</tiles:putAttribute>
	<tiles:putAttribute name="conteudo">
		<script type="text/javascript">
			var listPage;
			var COLUMN_DEFINITIONS = [ 	{ 0: "Empresa", 1: "empresa", 2: { "bVisible" : true }},
										{ 0: "Código da Linha", 1: "codigoLinha", 2: { "bVisible" : true }},
										{ 0: "Nome da Linha", 1: "nomeLinha", 2: { "bVisible" : true }},
										{ 0: "Mês de Referência", 1: "mesReferencia", 2: { "bVisible" : true }},
										{ 0: "Tipo de Veículo", 1: "descricaoTipoVeiculo", 2: { "bVisible" : true }},
										{ 0: "Número da Linha", 1: "numeroLinha", 2: { "bVisible" : false }},
										{ 0: "Responsável BOM", 1: "responsavelBOM", 2: { "bVisible" : false }},
										{ 0: "Capacidade da Linha", 1: "capacidadeLinha", 2: { "bVisible" : false }},
										{ 0: "Frota", 1: "frota", 2: { "bVisible" : false }},
										{ 0: "Viagens Extraordinárias A-B", 1: "viagensExtraordinariasAB", 2: { "bVisible" : false }},
										{ 0: "Viagens Extraordinárias B-A", 1: "viagensExtraordinariasBA", 2: { "bVisible" : false }},
										{ 0: "Viagens Ordinárias A-B", 1: "viagensOrdinariasAB", 2: { "bVisible" : false }},
										{ 0: "Viagens Ordinárias B-A", 1: "viagensOrdinariasBA", 2: { "bVisible" : false }},
										{ 0: "Extensão Piso I A-B", 1: "piso1AB", 2: { "bVisible" : false }},
										{ 0: "Extensão Piso I B-A", 1: "piso1BA", 2: { "bVisible" : false }},
										{ 0: "Extensão Piso II A-B", 1: "piso2AB", 2: { "bVisible" : false }},
										{ 0: "Extensão Piso II B-A", 1: "piso2BA", 2: { "bVisible" : false }},
										{ 0: "Início Horário de Pico Manhã - A-B", 1: "picoInicioManhaAB", 2: { "bVisible" : false }},
										{ 0: "Início Horário de Pico Manhã - B-A", 1: "picoInicioManhaBA", 2: { "bVisible" : false }},
										{ 0: "Fim Horário de Pico Manhã - A-B", 1: "picoFimManhaAB", 2: { "bVisible" : false }},
										{ 0: "Fim Horário de Pico Manhã - B-A", 1: "picoFimManhaBA", 2: { "bVisible" : false }},
										{ 0: "Início Horário de Pico Tarde - A-B", 1: "picoInicioTardeAB", 2: { "bVisible" : false }},
										{ 0: "Início Horário de Pico Tarde - B-A", 1: "picoInicioTardeBA", 2: { "bVisible" : false }},
										{ 0: "Fim Horário de Pico Tarde - A-B", 1: "picoFimTardeAB", 2: { "bVisible" : false }},
										{ 0: "Fim Horário de Pico Tarde - B-A", 1: "picoFimTardeBA", 2: { "bVisible" : false }},
										{ 0: "Duração Viagem Horário de Pico - A-B", 1: "duracaoViagemPicoAB", 2: { "bVisible" : false }},
										{ 0: "Duração Viagem Horário de Pico - B-A", 1: "duracaoViagemPicoBA", 2: { "bVisible" : false }},
										{ 0: "Duração Viagem Fora Horário de Pico - A-B", 1: "duracaoViagemForaPicoAB", 2: { "bVisible" : false }},
										{ 0: "Duração Viagem Fora Horário de Pico - B-A", 1: "duracaoViagemForaPicoBA", 2: { "bVisible" : false }},
										{ 0: "Hierarquização", 1: "hierarquizacao", 2: { "bVisible" : false }},
										{ 0: "Tipo da Ligação", 1: "tipoLigacao", 2: { "bVisible" : false }},
										{ 0: "Via", 1: "via", 2: { "bVisible" : false }},
										{ 0: "Status da Linha", 1: "statusLinha", 2: { "bVisible" : false }},
										{ 0: "Código da Seção", 1: "codigoSecao", 2: { "bVisible" : false }},
										{ 0: "Junção", 1: "juncaoSecao", 2: { "bVisible" : false }},
										{ 0: "Quantidade de Passageiros A-B", 1: "passageirosAB", 2: { "bVisible" : false }},
										{ 0: "Quantidade de Passageiros B-A", 1: "passageirosBA", 2: { "bVisible" : false }},
										{ 0: "Quantidade de Passageiros A-B - Anterior", 1: "passageirosAnteriorAB", 2: { "bVisible" : false }},
										{ 0: "Quantidade de Passageiros B-A - Anterior", 1: "passageirosAnteriorBA", 2: { "bVisible" : false }},
										{ 0: "Sem Passageiro", 1: "secaoSemPassageiro", 2: { "bVisible" : false }},
										{ 0: "Total Passageiros A-B", 1: "totalPassageirosAB", 2: { "bVisible" : false }},
										{ 0: "Total Passageiros B-A", 1: "totalPassageirosBA", 2: { "bVisible" : false }},
										{ 0: "Total Receita", 1: "totalReceita", 2: { "bVisible" : false }},
										{ 0: "Tarifa Atual", 1: "tarifaAtual", 2: { "bVisible" : false }},
										{ 0: "Tarifa Promocional", 1: "tarifaPromocional", 2: { "bVisible" : false }},
										{ 0: "Tarifa Anterior", 1: "tarifaAnterior", 2: { "bVisible" : false }}];
		
			$(document).ready(function() {
				criaListPage();
				configuraElementos();
			});

			function criaListPage() {
				listPage = new ListPage(pathName + "/relatorio", "relatório");

				listPage.ignoreActionColumn();
				
				listPage.setSearchFieldsIds(["selectEmpresaFormRelatorio", 
				             				"selectLinhaFormRelatorio", 
				             				"selectTipoVeiculoFormRelatorio", 
				             				"mesAnoInicio",
				             				"mesAnoFim",
				             				"camposRelatorio"]);

				for (var i = 0; i < COLUMN_DEFINITIONS.length; i++) {
					listPage.addColumnDefinition(COLUMN_DEFINITIONS[i][0], COLUMN_DEFINITIONS[i][1], COLUMN_DEFINITIONS[i][2]);
				}

				listPage.setAfterDataFetchCallback(habilitaBotaoExportar);
				listPage.setSearchFormValidationFunction(validaSePodeGerarRelatorio);
				
				listPage.initialize();
			}

			function configuraElementos() {
				var temPerfilEmpresa = ("${perfilEmpresa}" == "true");
				
				$("#selectEmpresaFormRelatorio").multiselect({
					checkAllText: "Marcar todas",
					uncheckAllText: "Desmarcar",
					noneSelectedText: "Selecione as empresas",
					selectedText: "# marcada(s)"
				});
				
				$("#selectLinhaFormRelatorio").multiselect({
					checkAllText: "Marcar todas",
					uncheckAllText: "Desmarcar",
					noneSelectedText: "Selecione as linhas",
					selectedText: "# marcada(s)"
				});
				
				$("#selectTipoVeiculoFormRelatorio").multiselect({
					checkAllText: "Marcar todos",
					uncheckAllText: "Desmarcar",
					noneSelectedText: "Selecione os veículos",
					selectedText: "# marcado(s)"
				});
				
				$("#camposRelatorio").multiselect({
					checkAllText: "Marcar todos",
					uncheckAllText: "Desmarcar",
					noneSelectedText: "Selecione os campos",
					selectedText: "# marcado(s)"
				});

				$("#camposRelatorio").bind("multiselectclick", camposRelatorio_onMultiSelectClick);
				$("#camposRelatorio").bind("multiselectcheckall", camposRelatorio_onMultiSelectCheckAll);
				$("#camposRelatorio").bind("multiselectuncheckall", camposRelatorio_onMultiSelectUncheckAll);

				if (!temPerfilEmpresa) {
					$("#divEmpresas").show();
					$("#selectLinhaFormRelatorio").multiselect("disable");
					$("#selectTipoVeiculoFormRelatorio").multiselect("disable");
				}
				
				$("#selectEmpresaFormRelatorio").multiselect({
					close: function(event, ui) {
						bomweb.updateMultiSelect($(this),
												 $("#selectLinhaFormRelatorio"), 
												 pathName + "/relatorio/buscaLinhas.json", 
												 "id", 
												 ["comboString"] 
												 );
					}
				});
				
				$("#selectLinhaFormRelatorio").multiselect({
					close: function(event, ui) {
						$("#selectTipoVeiculoFormRelatorio").multiselect("enable");
					}
				});
				
				$("#mesAnoInicio").mask("99/9999");
				$("#mesAnoFim").mask("99/9999");

				$("#resetButton").click(resetButton_onClick);
				$("#divDataTable").hide();
			}

			function hideDataTable() {
				$("#divDataTable").hide();
			}
			
			/**
			 * Extraído de: http://www.erichynds.com/jquery/jquery-ui-multiselect-widget/
			 * 
			 * event: the original event object
			 * 
			 * ui.value: value of the checkbox
			 * ui.text: text of the checkbox
			 * ui.checked: whether or not the input was checked or unchecked (boolean)
			 */
			function camposRelatorio_onMultiSelectClick(event, ui) {
				var columnIndex = listPage.getColumnIndexByJSONPropertyName(ui.value);

				// Se não encontra a coluna, dá erro.
				if (columnIndex == -1) throw new Error();

				// Não se deve poder selecionar uma das colunas permanentemente visíveis. 
				if (COLUMN_DEFINITIONS[columnIndex][2]["bVisible"]) throw new Error();

				// Se está selecionando, mostra a columa.
				if (ui.checked) {
					listPage.showColumn(columnIndex);
				}
				// Se está deselecionando, esconde a columa.
				else {
					listPage.hideColumn(columnIndex);
				}
				
				hideDataTable();
			}

			/**
			 * Extraído de: http://www.erichynds.com/jquery/jquery-ui-multiselect-widget/
			 * 
			 * event: the original event object
			 */
			function camposRelatorio_onMultiSelectCheckAll(event) {
				for (var i = 0; i < COLUMN_DEFINITIONS.length; i++) {
					// Mostra todas as colunas que não são permanentemente visíveis.
					if (!COLUMN_DEFINITIONS[i][2]["bVisible"]) {
						listPage.showColumn(i);
					}
				}
				
				hideDataTable();
			}

			/**
			 * Extraído de: http://www.erichynds.com/jquery/jquery-ui-multiselect-widget/
			 * 
			 * event: the original event object
			 */
			function camposRelatorio_onMultiSelectUncheckAll(event) {
				for (var i = 0; i < COLUMN_DEFINITIONS.length; i++) {
					// Esconde todas as colunas que não são permanentemente visíveis.
					if (!COLUMN_DEFINITIONS[i][2]["bVisible"]) {
						listPage.hideColumn(i);
					}
				}
				
				hideDataTable();
			}

			function resetButton_onClick(e) {
				var temPerfilEmpresa = ("${perfilEmpresa}" == "true");
				listPage.reset();
				$("#exportButton").hide();
				$("#selectEmpresaFormRelatorio").multiselect("uncheckAll");
				$("#selectLinhaFormRelatorio").multiselect("uncheckAll");
				if(!temPerfilEmpresa) {
					$("#selectLinhaFormRelatorio").multiselect("disable");
				}
				
				$("#selectTipoVeiculoFormRelatorio").multiselect("uncheckAll");
				$("#selectTipoVeiculoFormRelatorio").multiselect("disable");
				$("#camposRelatorio").multiselect("uncheckAll");
				$("#divDataTable").hide();
			}

			function habilitaBotaoExportar(json) {
				if (json.aaData.length > 0) {
					$("#divDataTable").show();
					$("#linkExportRelatorio").show();
				}
				else {
					$("#linkExportRelatorio").hide();
				}
			}

			function validaSePodeGerarRelatorio() {
				var temPerfilEmpresa = ("${perfilEmpresa}" == "true");
				var podeGerarRelatorio = true;
				
				if (!temPerfilEmpresa && !isMultiselectFilled("#selectEmpresaFormRelatorio")) {
					$("#errorEmpresa").show();
					podeGerarRelatorio = false;
				} 
				else {
					$("#errorEmpresa").hide();
				}
				
				if (!isMultiselectFilled("#selectLinhaFormRelatorio")) {
					$("#errorLinha").show();
					podeGerarRelatorio = false;
				} 
				else {
					$("#errorLinha").hide();
				}
									
				if (!isMultiselectFilled("#selectTipoVeiculoFormRelatorio")) {
					$("#errorTipoDeVeiculo").show();
					podeGerarRelatorio = false;
				} 
				else {
					$("#errorTipoDeVeiculo").hide();
				}

				if(!isFieldFilled("#mesAnoInicio")) {
					$("#errorMesAnoInicio").show();
					podeGerarRelatorio = false;
				} 
				else {
					$("#errorMesAnoInicio").hide();
				}	
								
				if(!isFieldFilled("#mesAnoFim")) {
					$("#errorMesAnoFim").show();
					podeGerarRelatorio = false;
				} 
				else {
					$("#errorMesAnoFim").hide();
				}

				return podeGerarRelatorio;
			}
		</script>
       	<strong class="titulo azul">Relatório </strong><img src="<c:url value="/images/bomweb_setas.gif" />" /><strong class="titulo verde"> BOM</strong>
		<c:if test="${not disableFilter}">
		<a href="#" class="accordion">[ Ocultar Filtro ]</a>
       	<div id="filtro">
			<img src="<c:url value="/images/bomweb_filtros_box_up.jpg" />" />
			<form id="form" method="post">
				<fieldset>
					<p>
						<label for="empresas">Empresa:</label>
						<c:if test="${perfilEmpresa}">
							<label>${empresa.nome}</label><br>
						</c:if>
						<div id="divEmpresas" style="display: none;">
							<select id="selectEmpresaFormRelatorio" multiple="multiple" name="filtro.empresasComoString">
							<c:forEach var="item" items="${empresas}">
								<option value="${item.id}" <c:if test="${fn:arrayStringContains(filtro.empresasComoArray, item.id)}">selected="selected"</c:if>><c:out value="${item.nome}"></c:out></option>
							</c:forEach>
							</select> * 
							<label id="errorEmpresa" class="error" for="selectEmpresaFormRelatorio" style="display: none;">Este campo é obrigatório.</label>
						</div>
					</p>
					<p>
						<label for="linhas">Linha:</label>
						<select id="selectLinhaFormRelatorio" multiple="multiple" name="filtro.linhasComoString">
						<c:forEach var="item" items="${linhas}">
							<option value="${item.id}" <c:if test="${fn:arrayStringContains(filtro.linhasComoArray, item.id)}">selected="selected"</c:if>><c:out value="${item.comboString}"></c:out></option>
						</c:forEach>
						</select> * 
						<label id="errorLinha" class="error" for="selectLinhaFormRelatorio" style="display: none;">Este campo é obrigatório.</label>
						<img class="loading" alt="Carregando" src="<c:url value="/images/carregando.gif" />">
					</p>
					<p>
						<label for="tipoVeiculos">Tipo de Veículo:</label>
						<select id="selectTipoVeiculoFormRelatorio" multiple="multiple" name="filtro.tiposVeiculoComoString">
						<c:forEach var="item" items="${tipoVeiculos}">
							<option value="${item.id}"><c:out value="${item.descricao}"></c:out></option>
						</c:forEach>
						</select> *
						<label id="errorTipoDeVeiculo" class="error" for="selectTipoDeVeiculoFormRelatorio" style="display: none;">Este campo é obrigatório.</label>
						<img class="loading" alt="Carregando" src="<c:url value="/images/carregando.gif" />">
					</p>
					<p>
						<label for="datepicker">Mês/Ano Inicial:</label>
						<input id="mesAnoInicio" type="text" name="filtro.dataInicial" maxlength="8" size="8" class="required"> *
						<label id="errorMesAnoInicio" class="error" for="selectEmpresaFormRelatorio" style="display: none;">Este campo é obrigatório.</label> 
					</p>
					<p>
					    <label for="datepicker">Mês/Ano Final:</label>
						<input id="mesAnoFim" type="text" name="filtro.dataFinal" maxlength="8" size="8" class="required"> *
						<label id="errorMesAnoFim" class="error" for="selectEmpresaFormRelatorio" style="display: none;">Este campo é obrigatório.</label> 
					</p>
					<p>
						<label for="camposRelatorio">Campos do Relatório:</label>
						<select id="camposRelatorio" multiple="multiple" name="filtro.camposRelatorioComoString">
							<option value="empresa" disabled="disabled" selected="selected">Empresa</option>
							<option value="mesReferencia" disabled="disabled" selected="selected">Mês de Referência</option>
							<option value="responsavelBOM">Responsável BOM</option>
							<optgroup label="Linha">
								<option value="nomeLinha" disabled="disabled" selected="selected">Nome da Linha</option>
								<option value="codigoLinha" disabled="disabled" selected="selected">Código da Linha</option>
								<option value="descricaoTipoVeiculo" disabled="disabled" selected="selected">Tipo de Veículo</option>
								<option value="numeroLinha">Número da Linha</option>
								<option value="capacidadeLinha">Capacidade da Linha</option>
								<option value="frota">Frota</option>
								<option value="viagensExtraordinariasAB">Viagens Extraordinárias A-B</option>
								<option value="viagensExtraordinariasBA">Viagens Extraordinárias B-A</option>
								<option value="viagensOrdinariasAB">Viagens Ordinárias A-B</option>
								<option value="viagensOrdinariasBA">Viagens Ordinárias B-A</option>
								<option value="piso1AB">Extensão Piso I A-B</option>
								<option value="piso1BA">Extensão Piso I B-A</option>
								<option value="piso2AB">Extensão Piso II A-B</option>
								<option value="piso2BA">Extensão Piso II B-A</option>
								<option value="picoInicioManhaAB">Início Horário de Pico Manhã - A-B</option>
								<option value="picoInicioManhaBA">Início Horário de Pico Manhã - B-A</option>
								<option value="picoFimManhaAB">Fim Horário de Pico Manhã - A-B</option>
								<option value="picoFimManhaBA">Fim Horário de Pico Manhã - B-A</option>
								<option value="picoInicioTardeAB">Início Horário de Pico Tarde - A-B</option>
								<option value="picoInicioTardeBA">Início Horário de Pico Tarde - B-A</option>
								<option value="picoFimTardeAB">Fim Horário de Pico Tarde - A-B</option>
								<option value="picoFimTardeBA">Fim Horário de Pico Tarde - B-A</option>
								<option value="duracaoViagemPicoAB">Duração Viagem Horário de Pico - A-B</option>
								<option value="duracaoViagemPicoBA">Duração Viagem Horário de Pico - B-A</option>
								<option value="duracaoViagemForaPicoAB">Duração Viagem Fora Horário de Pico - A-B</option>
								<option value="duracaoViagemForaPicoBA">Duração Viagem Fora Horário de Pico - B-A</option>
								<option value="hierarquizacao">Hierarquização</option>
								<option value="tipoLigacao">Tipo da Ligação</option>
								<option value="via">Via</option>
								<option value="statusLinha">Status da Linha</option>
							</optgroup>
							<optgroup label="Seção">
								<option value="codigoSecao">Código</option>
								<option value="juncaoSecao">Junção</option>
								<option value="passageirosAB">Quantidade de Passageiros A-B</option>
								<option value="passageirosAnteriorAB">Quantidade de Passageiros A-B - Anterior</option>
								<option value="passageirosBA">Quantidade de Passageiros B-A</option>
								<option value="passageirosAnteriorBA">Quantidade de Passageiros B-A - Anterior</option>
								<option value="secaoSemPassageiro">Sem Passageiro</option>
								<option value="totalPassageirosAB">Total Passageiros A-B</option>
								<option value="totalPassageirosBA">Total Passageiros B-A</option>
								<option value="totalReceita">Total Receita</option>
							</optgroup>
							<optgroup label="Tarifa">
								<option value="tarifaAtual">Tarifa Atual</option>
								<option value="tarifaAnterior">Tarifa Anterior</option>
							</optgroup>							
						</select> * 
					</p>
					<div id="camposObrigatorios"><c:out value="* Campo obrigatório."/></div>
					<security:hasPermission target="Relatorio" action="exportar">
	 			    	<input type="button" id="dataTableFetchData" value="Gerar Relatório"/>
		    		</security:hasPermission>
					<input type="button" id="resetButton" value="Limpar"/>
		    	</fieldset>
			</form>
			<img src="<c:url value="/images/bomweb_filtros_box_dn.jpg" />" />
			<security:hasPermission target="Relatorio" action="exportar">
				<div>
					<a id="linkExportRelatorio" href="<c:url value="/relatorio/exportar" />" class="buttom_verde">Exportar</a>
				</div>
			</security:hasPermission>
		</div>
		</c:if>
		<div id="divDataTable" style="width: 100%; overflow: auto;"></div>
    </tiles:putAttribute>
</tiles:insertTemplate>