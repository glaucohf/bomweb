<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/form.jsp">
	<tiles:putAttribute name="formAction" cascade="true">
		<c:url value="/tipodelinha" />
	</tiles:putAttribute>
	<tiles:putAttribute name="formLegend" cascade="true">
		Tipo de Linha
	</tiles:putAttribute>	
	<tiles:putAttribute name="formFields" cascade="true">
	<script type="text/javascript">
		$(function(){
			$(".submit").click(function(e) {
				if (! isCheckboxMarked()) {
					$("#errorTV").show();
					e.preventDefault();
				} else {
					$("#errorTV").hide();
				}

				if(! isFieldFilled("#codigo")) {
					$("#errorCodigo").show();
					e.preventDefault();
				} else {
					$("#errorCodigo").hide();
				}
				
				if(! isFieldFilled("#nome")) {
					$("#errorNome").show();
					e.preventDefault();
				} else {
					$("#errorNome").hide();
				}
			});
		});
	</script>
		<input type="hidden" name="entity.id" value="${t.id}">
		<input type="hidden" name="entity.active" value="${t.active}">

		<p>
			<label for="codigo">Sigla:</label>
			<input id="codigo" name="entity.sigla" value="${t.sigla}"  size="3" maxlength="3" minlength="1" /> *
			<label id="errorCodigo" class="error" style="display: none;">Este campo é obrigatório.</label> 
		</p>
	
		<p>
			<label for="nome">Descrição:</label>
			<input id="nome" name="entity.descricao" value="${t.descricao}" size="43" maxlength="32" minlength="2" /> * 
			<label id="errorNome" class="error" style="display: none;">Este campo é obrigatório.</label>
		</p>
		<div id="lista">
   			<fieldset>
   				<legend>Tipos de Veículos: <label id="errorTV" class="error" style="display: none;">Selecione ao menos um Tipo de Veículo.</label></legend>
   				<p>
					<table id="tb_veiculos" class="tablesorter">
						<thead>
							<tr>
								<td></td>
								<td>Código</td>
								<td>Descrição</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${tiposVeiculo}" var="tipoVeiculo" varStatus="i">
								<tr>
									<td>
										<input type="checkbox" class="require-one validate-checkbox-oneormore" name="tiposVeiculo[${tipoVeiculo.id}].id" value="${tipoVeiculo.id}"<c:if test="${tipoVeiculo.selecionado}"> checked="checked"</c:if>/>
									</td>
									<td>
										${tipoVeiculo.codigo}
									</td>
									<td>${tipoVeiculo.descricao}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</fieldset>
				<p></p>
		</div>
	</tiles:putAttribute>
</tiles:insertTemplate>