<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/template.jsp">
	<tiles:putAttribute name="conteudo">
		<h1>Histórico - Linha</h1>
		<table id="tb_list" class="tablesorter">
			<thead>
				<tr>
					<th>Empresa</th>
					<th>Linha</th>
					<th>Código</th>
					<th>Data de Inicio</th>
					<th>Data de Término</th>
					<th>Status</th>
					<th>Seções</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${linhaVigenciaList}" var="lv">
				<tr>
					<td>${lv.empresa.nome}</td>
					<td>${lv.itinerarioIda}</td>
					<td>${lv.codigo}</td>
					<td><fmt:formatDate value="${lv.dataInicio}" pattern="dd/MM/yyyy"/></td>
					<td><fmt:formatDate value="${lv.dataTermino}" pattern="dd/MM/yyyy"/></td>
					<td>${lv.status.nomeFormatado}</td>
					<td>
					<c:forEach items="${lv.secoes}" var="secao">
					<c:out value="${secao.codigo}"></c:out><br>
					</c:forEach>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<input type="button" id="btnCancelar" value="Voltar" style="display: none;" data-urlRetorno="<c:url value="/linha/list"/>"/>
	</tiles:putAttribute>
</tiles:insertTemplate>