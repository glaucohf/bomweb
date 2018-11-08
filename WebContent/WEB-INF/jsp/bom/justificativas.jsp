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
						<th>Código da Linha</th>
						<th>Nome da Linha</th>
						<th>Tipo Veículo</th>
						<th>Status</th>
						<th>Justificativa</th>
						<th>Ação</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="bomLinha" items="${bomLinhaList}" varStatus="i">
					<tr>
						<!--<td align="center">
							<input id="checkBomLinha${i.index}" type="checkbox" name="linhas[${i.index}].id" value="${bomLinha.id}" class="checkBomLinha" data-index="${i.index}" />
						</td>-->
						<td><c:out value="${bomLinha.linhaVigencia.codigo}" /></td>
						<td><c:out value="${bomLinha.linhaVigencia.itinerarioIda}" /></td>
						<td><c:out value="${bomLinha.tipoDeVeiculo.descricao}" /></td>
						<td><c:out value="${bomLinha.status}" /></td>
						<td>
							<a href="#" class="justificativaBomLinha" data-index="${i.index}">
								<c:out value="${bomLinha.previaJustificativa}"/>
							</a>
							<textarea id="justificativa${i.index}" style="display:none"><c:out value="${bomLinha.ultimaJustificativa}"/></textarea>
						</td>
						<td>
							<a href="<c:url value='/bom/justificativas/historico/${bomLinha.id}' />" title="Histórico">
								<img src="<c:url value="/images/historico.png" />" alt="Histórico" />	
							</a>
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			<input type="button" id="btnCancelar" value="Voltar" data-urlRetorno="<c:url value="/bom/list"/>" />
		</form>
    </tiles:putAttribute>
    <tiles:putAttribute name="modalViewConteudo">
    	<div class="white_content_header">
			<img src="<c:url value="/images/ico_modal_ok.png" />" align="absmiddle"> Informação
		</div>
		
		<div class="white_content_content">
			Justificativa para a reabertura:<br/>
			<textarea id="justificativa" disabled="disabled" rows="10" cols="200" style="text-align: left"></textarea>
		</div>
		
		<div class="white_content_footer">
			<input type="button" value="Fechar" class="simplemodal-close">
		</div>
    </tiles:putAttribute>
</tiles:insertTemplate>