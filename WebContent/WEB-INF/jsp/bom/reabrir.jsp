<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/template.jsp">
	<tiles:putAttribute name="info">
		<span class="informacao">${bom.empresa.nome} - ${bom.mesReferencia}</span>
	</tiles:putAttribute>
	<tiles:putAttribute name="conteudo">
		<form id="form" name="form" action="<c:url value="/bom/reabertura" />" method="post">
			<input type="hidden" name="bom.id" value="${bom.id}" />
			<table class="tablesorter">
				<thead>
					<tr>
						<th align="center"><input id="checkAllBomLinhas" type="checkbox" /></th>
						<th>Código da Linha</th>
						<th>Nome da Linha</th>
						<th>Tipo Veículo</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="bomLinha" items="${bomLinhaList}" varStatus="i">
					<tr>
						<td align="center">
							<textarea id="justificativa${i.index}" name="linhas[${i.index}].ultimaJustificativa" class="justificativa" style="display:none"></textarea>
							<input id="checkBomLinha${i.index}" type="checkbox" name="linhas[${i.index}].id" value="${bomLinha.id}" class="checkBomLinha" data-index="${i.index}" />
						</td>
						<td><c:out value="${bomLinha.linhaVigencia.codigo}" /></td>
						<td><c:out value="${bomLinha.linhaVigencia.itinerarioIda}" /></td>
						<td><c:out value="${bomLinha.tipoDeVeiculo.descricao}" /></td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			<input type="submit" id="btnSalvar" value="Reabrir" />
			<input type="button" id="btnCancelar" value="Cancelar" data-urlRetorno="<c:url value="/bom/list"/>" />
		</form>
    </tiles:putAttribute>
    <tiles:putAttribute name="modalViewConteudo">
       	<div class="white_content_header">
			<img src="<c:url value="/images/ico_modal_alert.png" />" align="absmiddle"> Alerta
		</div>
		
		<div class="white_content_content">
			Insira uma justificativa para a reabertura:<br/>
			<input type="hidden" id="index" value="Teste" />
			<input type="checkbox" id="salvo" value="false" style="display:none" />
			<textarea id="justificativa" rows="10" cols="200"></textarea>
		</div>
		
		<div class="white_content_footer">
			<input id="btnSalvarJustificaBomLinha" type="button" value="Confirmar" />
			<input class="simplemodal-close" type="button" value="Cancelar" />
		</div>
    </tiles:putAttribute>
</tiles:insertTemplate>