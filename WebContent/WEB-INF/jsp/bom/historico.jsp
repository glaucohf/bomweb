<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/template.jsp">
	<tiles:putAttribute name="conteudo">
	
		<div class="white_content_header">
			<h1> Histórico - Justificativas</h1>
		</div>

		
			<table id="tb_list" class="tablesorter">
				<thead>
					<tr>
						<th>Empresa</th>
						<th>Mês/Ano de Referência</th>
						<th>Código da Linha</th>
						<th>Linha</th>
						<th>Justificativa</th>
						<th>Data</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${justList}" var="just" varStatus="i">
					<tr>
						<td>${just.bomLinha.bom.empresa.nome}</td>
						<td>${just.bomLinha.bom.mesReferencia}</td>
						<td>${just.bomLinha.linhaVigencia.codigo}</td>
						<td>${just.bomLinha.linhaVigencia.itinerarioIda}</td>
						<td>
							<a href="#" class="justificativaBomLinha" data-index="${i.index}">
								<c:out value="${bomLinha.previaJustificativa}"/>
							</a>
							<a href="#" class="justificativaBomLinha" title="Justificativa" data-index="${i.index}">
								<c:out value="${just.previaJustificativa}"/>
							</a>
							<textarea id="justificativa${i.index}" style="display:none"><c:out value="${just.descricao}"/></textarea>
						</td>
						<td><fmt:formatDate value="${just.dataCriacao}" pattern="dd/MM/yyyy"/></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<input type="button" id="btnCancelar" value="Voltar" style="display: none;" data-urlRetorno="<c:url value="/bom/${bom.id}/justificativas"/>"/>
		
	</tiles:putAttribute>
	
	 <tiles:putAttribute name="modalViewConteudo">
    	<div class="white_content_header">
			<img src="<c:url value="/images/ico_modal_ok.png" />" align="absmiddle"> Informação
		</div>
		
		<div class="white_content_content">
			Justificativa para a reabertura:<br/>
			<textarea id="justificativa" disabled="disabled" rows="10" cols="200"></textarea>
		</div>
		
		<div class="white_content_footer">
			<input type="button" value="Fechar" class="simplemodal-close">
		</div>
    </tiles:putAttribute>
</tiles:insertTemplate>		