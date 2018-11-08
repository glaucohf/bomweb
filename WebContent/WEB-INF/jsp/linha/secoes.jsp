<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/template.jsp">
	<tiles:putAttribute name="conteudo">
		<h1>Histórico - Seção</h1>
		<table id="tb_list" class="tablesorter">
			<thead>
				<tr>
					<th>Código</th>
					<th>Ponto Inicial</th>
					<th>Ponto Final</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${secaoList}" var="secao">
				<tr>
					<td>${secao.codigo}</td>
					<td>${secao.pontoInicial}</td>
					<td>${secao.pontoFinal}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<input type="button" id="btnCancelar" value="Cancelar" style="display: none;" data-urlRetorno="-1"/>
	</tiles:putAttribute>
</tiles:insertTemplate>