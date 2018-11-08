<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/template.jsp">
	<tiles:putAttribute name="conteudo">
	
		<div class="white_content_header">
			<img src="<c:url value="/images/ico_modal_ok.png" />" align="absmiddle"> Histórico - Tarifa
		</div>

		<div class="white_content_content_historico">	
			<table id="tb_list" class="tablesorter">
				<thead>
					<tr>
						<th>Código da Linha</th>
						<th>Empresa</th>
						<th>Linha</th>
						<th>Seção</th>
						<th>Valor</th>
						<th>Início Vigência</th>
						<th>Fim Vigência</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${tarifaList}" var="tar">
					<tr>
						<td>${tar.secao.linhaVigencia.codigo}</td>
						<td>${tar.secao.linhaVigencia.empresa.nome}</td>
						<td>${tar.secao.linhaVigencia.itinerarioIda}</td>
						<td>${tar.secao.juncao}</td>
						<td><fmt:formatNumber minFractionDigits="2" maxFractionDigits="2" value="${tar.valor}"/></td>
						<td><fmt:formatDate value="${tar.inicioVigencia}" pattern="dd/MM/yyyy"/></td>
						<td><fmt:formatDate value="${tar.fimVigencia}" pattern="dd/MM/yyyy"/></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>

		<div class="white_content_footer">
			<input type="button" value="Fechar" class="simplemodal-close">
		</div>
	</tiles:putAttribute>
</tiles:insertTemplate>		