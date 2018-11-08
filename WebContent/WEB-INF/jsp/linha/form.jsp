<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="security" uri="http://taglib.decatron.com.br/security" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="pt_BR"/>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/form.jsp">
	<tiles:putAttribute name="formAction" cascade="true">
		<c:url value="/linha" />
	</tiles:putAttribute>
	<tiles:putAttribute name="formLegend" cascade="true">
	<script type="text/javascript" src="<c:url value="/jscript/formLinha.js" />"></script>
	<script type="text/javascript">

		</script>
		Linha
	</tiles:putAttribute>
	<tiles:putAttribute name="header" cascade="true">
		<script type="text/javascript">
			function showVeiculo(obj) {
				index = obj.selectedIndex;
				count = obj.options.length;
				for(i=1;i<count;i++)
					document.getElementById('veic'+(i-1)).style.display = 'none';
				if(index > 0)
					document.getElementById('veic'+(index-1)).style.display = 'block';
			}
			
			<security:hasRole roleName="Empresa">
				<c:if test="${not isStatusAtiva}">
					$(document).ready(function() {
						$('form input[type=submit]').attr('disabled', 'disabled');
					});
				</c:if>
			</security:hasRole>	
				
			</script>
	</tiles:putAttribute>
	<tiles:putAttribute name="formFields" cascade="true">
	
		<input type="hidden" name="entity.futuro" value="${futuro}">
		<input type="hidden" name="entity.id" value="${linha.id}">
		<input type="hidden" name="entity.active" value="${linha.active}">
		<input type="hidden" name="entity.linhaVigente.id" value="${linha.linhaVigente.id}">
		<input type="hidden" name="entity.linhaVigente.active" value="${linha.linhaVigente.active}">

		<security:hasRole roleName="Detro,Detro_aud,Detro_Admin,Detro,Detro_nivel_1,Detro_nivel_2,Detro_nivel_3">
		<p>
			<label for="status">Status da Linha: </label>
   			<select id="status" name="entity.linhaVigente.status" class="required">
             	<c:forEach var="item" items="${statusLinha}">
             		<c:if test="${operation == 'insert'}">
	               		<option value="${item.valor}" <c:if test="${item.valor == 0}">selected="true"</c:if>><c:out value="${item.nomeFormatado}"></c:out></option>
             		</c:if>
             		<c:if test="${operation == 'edit'}">
	               		<option value="${item.valor}" <c:if test="${linha.linhaVigente.status.valor == item.valor}">selected="true"</c:if>><c:out value="${item.nomeFormatado}"></c:out></option>
             		</c:if>
             	</c:forEach>
			</select> * 
		</p>
		</security:hasRole>
		
		<security:hasRole roleName="Empresa">
		<p>
			<label for="status">Status da Linha: </label>
   			<select id="status" name="status" disabled="disabled">
             	<c:forEach var="item" items="${statusLinha}">
             		<option value="${item.valor}" <c:if test="${linha.linhaVigente.status.valor == item.valor}">selected="true"</c:if>><c:out value="${item.nomeFormatado}"></c:out></option>
             	</c:forEach>
			</select>
			<input type="hidden" name="entity.linhaVigente.status" value="${linha.linhaVigente.status}">
		</p>
		<c:if test="${not isStatusAtiva}">
			<p>
				<b>Esta linha não está ativa e por isso não pode ser modificada.</b>
			</p>
		</c:if>
		</security:hasRole>
		
		<security:notHasRole roleName="Empresa">
		<p>
			<label for="buscaEmp">Empresa: </label>

			<input type="text" id="buscaEmp" name="entity.linhaVigente.empresa.nome" value="${linha.linhaVigente.empresa.nome}" onblur="limpaId(this,'idEmpresa')" style="width:350px;" class="required"/>  *
			<input type="hidden" id="idEmpresa" name="entity.linhaVigente.empresa.id" value="${linha.linhaVigente.empresa.id}"/>
		</p>
		</security:notHasRole>
		
		<security:hasRole roleName="Empresa">
			<input type="hidden" name="entity.linhaVigente.empresa.id" value="${entity.linhaVigente.empresa.id}"/>
		</security:hasRole>
		
		<p>
			<label for="codigo">Código da Linha:</label>
			<input id="codigo" type="text" name="entity.linhaVigente.codigo" value="${linha.linhaVigente.codigo}" class="required" size="9" number="true" maxlength="9" minlength="9" <security:hasRole roleName="Empresa">readOnly="readOnly" </security:hasRole> /> * 
		</p>
   		<p>
			<label for="numeroLinha">Número da Linha:</label>
			<input type="text" id="numeroLinha" value="${linha.linhaVigente.numeroLinha}" name="entity.linhaVigente.numeroLinha" size="9" maxlength="9" <security:hasRole roleName="Empresa">readOnly="readOnly" </security:hasRole> > 
		</p>   		
		<p id="pNomeLinha">
			<label for="nomeLinha">Nome da Linha:</label>
			<label id="nomeLinha"></label>
			<br/>
		</p>
		<p>
			<label for="linhaPontoInicial">Ponto Inicial:</label>
			<input id="linhaPontoInicial" type="text" name="entity.linhaVigente.pontoInicial" value="${linha.linhaVigente.pontoInicial}" class="required" size="30" minlength="2" <security:hasRole roleName="Empresa">readOnly="readOnly" </security:hasRole> /> * 
		</p>
		<p>
			<label for="linhaPontoFinal">Ponto Final:</label>
			<input id="linhaPontoFinal" type="text" name="entity.linhaVigente.pontoFinal" value="${linha.linhaVigente.pontoFinal}" class="required" size="30" minlength="2" <security:hasRole roleName="Empresa">readOnly="readOnly" </security:hasRole> /> * 
		</p>
   		<p>
			<label for="via">Via:</label>
			<input type="text" id="via" value="${linha.linhaVigente.via}" name="entity.linhaVigente.via" size="30" <security:hasRole roleName="Empresa">readOnly="readOnly" </security:hasRole> > 
		</p>   		
   		<p>
			<label for="tipoLigacao">Tipo da Ligação:</label>
			<input type="text" id="tipoLigacao" value="${linha.linhaVigente.tipoLigacao}" name="entity.linhaVigente.tipoLigacao" class="required" size="30" <security:hasRole roleName="Empresa">readOnly="readOnly" </security:hasRole> > * 
		</p>   		
		
		<security:hasRole roleName="Empresa">
		<p>
		<label for="tipoDeLinha">Tipos de linha:</label> 
		<select id="tipoDeLinhaSelect" name="tipoDeLinhaSelect" onchange="javascript:showVeiculo(this)" class="required" <c:if test="${not isStatusAtiva}">disabled="disabled"</c:if>>
			<option value="" selected="true">Selecione:</option>
			<c:forEach var="item" items="${tipoLinhas}" varStatus="i">
				<option value="${item.id}" <c:if test="${i.index eq 0}">selected="true"</c:if>>
					<c:out value="${item.descricao}"></c:out>
				</option>
			</c:forEach>
		</select> *
		</p>
		<p>
		<c:if test="${operation == 'insert'}">
			<c:forEach var="item" items="${tipoLinhas}" varStatus="i">
			<div id='veic${i.index}' style="display: none;">
             	<label for="tipoDeVeiculo${item.id}">Tipos de Veículos Utilizados:</label>
  					<c:forEach var="itemVeic" items="${item.tiposDeVeiculoPermitidos}" varStatus="j">
  						<input id="tipoDeVeiculo" type="checkbox" class="require-one validate-checkbox-oneormore" name="tipoDeVeiculos[(${fn:length(item.tiposDeVeiculoPermitidos)}*${i.index})+${j.index}].id" value="${itemVeic.id}${STR_SEPARA_IDS}${item.id}" <c:if test="${itemVeic.selecionado}">checked="checked"</c:if>><c:out value="${itemVeic.descricao}" />
            		</c:forEach>
				<span class="errorCheck" style="display: none;">Selecione ao menos um Tipo de Veículo.</span>            		
          	</div>
            </c:forEach>
		</c:if>
		<c:if test="${operation == 'edit' and isStatusAtiva}">
			<c:forEach var="item" items="${tipoLinhas}" varStatus="i">
			<div id='veic${i.index}' <c:choose><c:when test="${i.index eq 0}">style="display: block;"</c:when><c:otherwise>style="display: none;"</c:otherwise></c:choose>>
             	<label for="tipoDeVeiculo${item.id}">Tipos de Veículos Utilizados:</label>
  					<c:forEach var="itemVeic" items="${item.tiposDeVeiculoPermitidos}" varStatus="j">
  						<input id="tipoDeVeiculo" type="checkbox" class="require-one validate-checkbox-oneormore" name="tipoDeVeiculos[(${fn:length(item.tiposDeVeiculoPermitidos)}*${i.index})+${j.index}].id" value="${itemVeic.id}${STR_SEPARA_IDS}${item.id}" <c:if test="${itemVeic.selecionado}">checked="checked"</c:if> ><c:out value="${itemVeic.descricao}" />
            		</c:forEach>
				<span class="errorCheck" style="display: none;">Selecione ao menos um Tipo de Veículo.</span>            		
          	</div>
            </c:forEach>
		</c:if>
   		</p>
   		</security:hasRole>
   		<security:hasRole roleName="Detro,Detro_aud,Detro_Admin,Detro_nivel_1,Detro_nivel_2,Detro_nivel_3">
   		<p>
   		    <label for="tipoDeLinha">Tipos de Linha:</label>
   			<c:forEach var="item" items="${tipoLinhas}" varStatus="i">
  				<input id="tipoDeLinha" type="checkbox" class="require-one validate-checkbox-oneormore" name="tipoLinhas[${i.index}].id" value="${item.id}" <c:if test="${item.selecionado}">checked="checked"</c:if>><c:out value="${item.descricao}" />&nbsp;&nbsp;
            </c:forEach> <span class="errorCheck" style="display: none;">Selecione ao menos um Tipo de Linha.</span> *
   		</p>
   		</security:hasRole>
   		<span id="erroPiso" style="display: none;"></span>
		<div id="pisos">
   				<table width="90%">
						<tbody>
							<tr>
								<td id="tdPisos" style="padding-bottom: 10px;">
									<label for="piso1AB">Piso I A-B (Km):</label>
								</td>
								<td style="padding-bottom: 10px;" nowrap="nowrap">
									<input id="piso1AB" type="text" name="entity.linhaVigente.piso1AB" value="<fmt:formatNumber type='number' minFractionDigits='2' value='${linha.linhaVigente.piso1AB}'/>" size="7" maxlength="8" minlength="1" number="true" <security:hasRole roleName="Empresa"> class="multi-required" </security:hasRole> /><security:hasRole roleName="Empresa"> *</security:hasRole>  
								</td>
								<td align="right" style="padding-bottom: 10px;" nowrap="nowrap">
									<label for="piso2AB">Piso II A-B (Km):</label>
								</td>
								<td style="padding-bottom: 10px;" nowrap="nowrap"> 
									<input id="piso2AB" type="text" name="entity.linhaVigente.piso2AB" value="<fmt:formatNumber type='number' minFractionDigits='2' value='${linha.linhaVigente.piso2AB}'/>" size="7" maxlength="8" minlength="1" number="true" <security:hasRole roleName="Empresa"> class="multi-required" </security:hasRole> /><security:hasRole roleName="Empresa"> *</security:hasRole>  
								</td>
								<td style="padding-bottom: 10px;" nowrap="nowrap"> 
									<span id="totalPisoAB">Extensão A-B: 0,00</span>
								</td>
							</tr>
							<tr>	
								<td>
									<label for="piso1BA">Piso I B-A (Km):</label> 
								</td>
								<td>
									<input id="piso1BA" type="text" name="entity.linhaVigente.piso1BA" value="<fmt:formatNumber type='number' minFractionDigits='2' value='${linha.linhaVigente.piso1BA}'/>" size="7" maxlength="8" minlength="1" number="true" <security:hasRole roleName="Empresa"> class="multi-required" </security:hasRole> /><security:hasRole roleName="Empresa"> *</security:hasRole> 
								</td>
								<td>
									<label for="piso2BA">Piso II B-A (Km):</label>
								</td>
								<td> 
									<input id="piso2BA" type="text" name="entity.linhaVigente.piso2BA" value="<fmt:formatNumber type='number' minFractionDigits='2' value='${linha.linhaVigente.piso2BA}'/>" size="7" maxlength="8" minlength="1" number="true" <security:hasRole roleName="Empresa"> class="multi-required" </security:hasRole> /><security:hasRole roleName="Empresa"> *</security:hasRole>  
								</td>
								<td>
								 	<span id="totalPisoBA">Extensão B-A: 0,00</span>
								</td>
							</tr>
						</tbody>
				</table>
		</div>   		
   		
   		<p>
			<label for="hierarquizacao">Hierarquização:</label>
			<input type="text" id="hierarquizacao" value="${linha.linhaVigente.hierarquizacao}" name="entity.linhaVigente.hierarquizacao" size="30" <security:hasRole roleName="Empresa">readOnly="readOnly" </security:hasRole> > 
		</p>   		
   		<p>
			<label for="dataInicioVigencia">Data Vigência:</label>
			<input type="text" id="dataInicioVigencia" value="${linha.linhaVigente.dataInicioFormated}" name="entity.linhaVigente.dataInicio" class="required" maxlength="10" size="10" <security:hasRole roleName="Empresa">readOnly="readOnly" </security:hasRole> > * 
		</p>

   		<c:if test="${operation eq 'edit'}">
			<security:hasRole roleName="Detro,Detro_aud,Detro_Admin,Detro_nivel_1,Detro_nivel_2,Detro_nivel_3">
			<c:if test="${futuro eq '0' && vigente eq '1'}">
   			<p>
				<label for="numeroProcesso">Observação:</label>
				<textarea rows="3" cols="60" id="numeroProcesso" name="entity.linhaVigente.observacao"  <security:hasRole roleName="Empresa">readOnly="readOnly" </security:hasRole>>${linha.linhaVigente.observacao}</textarea>
			</p>
			</c:if>
				<input type="hidden" name="justificaivaHidden" value="0">
			</security:hasRole>
		</c:if>
		<security:hasRole roleName="Empresa">
		<div id="horarioPicoAB">
		<fieldset>
   				<label id="legendPicoAB" style="text-decoration: underline;">Horário de Pico A-B:</label>
   				<table width="95%">
						<thead>
							<tr>
								<td>
									<label for="duracaoViagemPicoAB">Tempo de viagem médio em horário de pico (minutos):</label>
								</td>
								<td>
									<input type="text" id="duracaoViagemPicoAB" name="entity.linhaVigente.duracaoViagemPicoAB" value="${linha.linhaVigente.duracaoViagemPicoAB}" size="6" maxlength="6" class="digits multi-required" <c:if test="${not isStatusAtiva}">readOnly="readOnly"</c:if>/> *  
								</td>
								<td  align="right">
									<label for="picoInicioManhaAB">Intervalo de Pico manhã:</label> 
									<input type="text" id="picoInicioManhaAB" name="entity.linhaVigente.picoInicioManhaAB" value="${linha.linhaVigente.picoInicioManhaAB}" size="5" maxlength="5" class="inputhora multi-required" <c:if test="${not isStatusAtiva}">readOnly="readOnly"</c:if> />  
									às
									<input type="text" id="picoInicioManhaAB" name="entity.linhaVigente.picoFimManhaAB" value="${linha.linhaVigente.picoFimManhaAB}" size="5" maxlength="5" class="inputhora multi-required" <c:if test="${not isStatusAtiva}">readOnly="readOnly"</c:if> /> * 
								</td>
							</tr>
							<tr>	
								<td>
									<label for="duracaoViagemForaPicoAB">Tempo de viagem médio fora do horário de pico (minutos):</label> 
								</td>
								<td>
									<input type="text" id="duracaoViagemForaPicoAB" name="entity.linhaVigente.duracaoViagemForaPicoAB" value="${linha.linhaVigente.duracaoViagemForaPicoAB}" size="6" maxlength="6" class="digits multi-required" <c:if test="${not isStatusAtiva}">readOnly="readOnly"</c:if> /> * 
								</td>
								<td  align="right">
									<label for="picoInicioTardeAB">Intervalo de Pico tarde:</label> 
									<input type="text" id="picoInicioTardeAB" name="entity.linhaVigente.picoInicioTardeAB" value="${linha.linhaVigente.picoInicioTardeAB}" size="5" maxlength="5" class="inputhora multi-required" <c:if test="${not isStatusAtiva}">readOnly="readOnly"</c:if> />  
									às
									<input type="text" id="picoInicioTardeAB" name="entity.linhaVigente.picoFimTardeAB" value="${linha.linhaVigente.picoFimTardeAB}" size="5" maxlength="5" class="inputhora multi-required" <c:if test="${not isStatusAtiva}">readOnly="readOnly"</c:if> /> * 
								</td>
							</tr>
						</thead>
				</table>
   		</fieldset>
		</div>
		<p />
		<div id="horarioPicoBA">
		<fieldset>
   				<label id="legendPicoBA" style="text-decoration: underline;">Horário de Pico B-A:</label>
   				<table width="95%">
						<thead>
							<tr>
								<td>
									<label for="duracaoViagemPicoBA">Tempo de viagem médio em horário de pico (minutos):</label>
								</td>
								<td>
									<input type="text" id="duracaoViagemPicoBA" name="entity.linhaVigente.duracaoViagemPicoBA" value="${linha.linhaVigente.duracaoViagemPicoBA}" size="6" maxlength="6" class="digits multi-required" <c:if test="${not isStatusAtiva}">readOnly="readOnly"</c:if> /> * 
								</td>
								<td align="right">
									<label for="picoInicioManhaBA">Intervalo de Pico manhã:</label> 
									<input type="text" id="picoInicioManhaBA" name="entity.linhaVigente.picoInicioManhaBA" value="${linha.linhaVigente.picoInicioManhaBA}" size="5" maxlength="5"  class="inputhora multi-required" <c:if test="${not isStatusAtiva}">readOnly="readOnly"</c:if> /> 
									às
									<input type="text" id="picoInicioManhaBA" name="entity.linhaVigente.picoFimManhaBA" value="${linha.linhaVigente.picoFimManhaBA}" size="5" maxlength="5" class="inputhora multi-required" <c:if test="${not isStatusAtiva}">readOnly="readOnly"</c:if> /> * 
								</td>
							</tr>
							<tr>	
								<td>
									<label for="duracaoViagemForaPicoBA">Tempo de viagem médio fora do horário de pico (minutos):</label> 
								</td>
								<td>
									<input type="text" id="duracaoViagemForaPicoBA" name="entity.linhaVigente.duracaoViagemForaPicoBA" value="${linha.linhaVigente.duracaoViagemForaPicoBA}" size="6" maxlength="6" class="digits multi-required" <c:if test="${not isStatusAtiva}">readOnly="readOnly"</c:if> /> * 
								</td>
								<td align="right">
									<label for="picoInicioTardeBA">Intervalo de Pico tarde:</label> 
									<input type="text" id="picoInicioTardeBA" name="entity.linhaVigente.picoInicioTardeBA" value="${linha.linhaVigente.picoInicioTardeBA}" size="5" maxlength="5" class="inputhora multi-required" <c:if test="${not isStatusAtiva}">readOnly="readOnly"</c:if> /> 
									às
									<input type="text" id="picoInicioTardeBA" name="entity.linhaVigente.picoFimTardeBA" value="${linha.linhaVigente.picoFimTardeBA}" size="5" maxlength="5" class="inputhora multi-required" <c:if test="${not isStatusAtiva}">readOnly="readOnly"</c:if> /> * 
								</td>
							</tr>
						</thead>
				</table>
   		</fieldset>
		</div>
		</security:hasRole>
		<security:notHasRole roleName="Empresa">
   		<div id="lista">
   			<fieldset>
   				<legend>Seções:</legend>
   				<p>
					<input id="addSecao" type="button" value="Nova Seção" />
    				<input id="rmSecao" type="button" value="Delete Seção" />
					<table id="tb_secoes" class="tablesorter">
						<thead>
							<tr>
								<td></td>
								<td>Código</td>
								<td>Ponto Inicial da Seção</td>
								<td>Ponto Final da Seção</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${secoes}" var="sec" varStatus="i">
								<tr>
									<td><input type="checkbox" name="remove" /></td>
									<td>
										<input type="hidden" name="secoes[${i.index}].id" value="${sec.id}" />
										<input type="text" id="codigo${i.index}" name="secoes[${i.index}].codigo" value="${sec.codigo}" class="required" size="2" maxlength="2" minlength="2" number="true" />
									</td>
									<td><input type="text" name="secoes[${i.index}].pontoInicial" value="${sec.pontoInicial}" class="required" size="25" maxlength="25" minlength="2" /></td>
									<td><input type="text" name="secoes[${i.index}].pontoFinal" value="${sec.pontoFinal}" class="required" size="25" maxlength="25" minlength="2" /></td>
								</tr>
							</c:forEach>			
						</tbody>
					</table>
				</fieldset>
				<p></p>
		</div>

<div id="modalView2" style="display: none;" class="simplemodal-data">

	<div class="white_content_header">
		<img src="/bomweb/images/ico_modal_alert.png" align="absmiddle"> Alerta
	</div>
	<div class="messageJust white_content_content">
	</div>
	<div class="white_content_footer">
		<input id="btnSimEdicaoRetroativa" type="button" value="Sim">
		<input class="simplemodal-close" type="button" value="Não">
	</div>

</div>
<div id="justRequerida" style="display: none;" class="simplemodal-data">
	
       	<div class="white_content_header">
			<img src="/bomweb/images/ico_modal_alert.png" align="absmiddle"> Alerta
		</div>
		Por favor, informe uma justificativa.
		<br />
		<div class="white_content_footer">
			<input type="button" value="OK" class="simplemodal-close">
		</div>    		
		
</div>

				<div id='confirm' style="display: none;">
			<div class="white_content_header">
				<img src="<c:url value="/images/ico_modal_alert.png" />" align="absmiddle"> Alerta
			</div>
		
			<div class='message'></div><br>
			
			<div class="white_content_footer">
				<input type="button" value="Sim" class="yes simplemodal-close">
				<input type="button" value="Não" class="simplemodal-close">
			</div>
		</div>			
		</security:notHasRole>
       </tiles:putAttribute>
       	
</tiles:insertTemplate>