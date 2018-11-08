<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/template.jsp">
	<tiles:putAttribute name="conteudo">
	<strong class="titulo azul">Recriar Linhas - BOM </strong><img src="<c:url value="/images/bomweb_setas.gif" />" /><strong class="titulo verde"> ${bom.mesReferencia}</strong>
	<br/><br/>
		<form id="form" name="form" action="<c:url value="/bom/recriarLinhas" />" method="post">
			<input type="hidden" name="bom.id" value="${bom.id}" />
			Selecione as linhas que serão recriadas.<br/>
			Todos os dados preenchidos serão perdidos. 
			<c:if test="${bom.temBomLinhaFechado}">
				<br/><br/>
				<span style="color: red">
				Atenção: Existem linhas com pares Linha/Tipo de Veículo fechados que, por isso, não poderão ser recriadas.
				</span>
			</c:if>
			<c:if test="${bom.temLinhaExcluida}">
				<br/><br/>
				<span style="color: red">
				Atenção: Existem linhas que foram excluídas e que, por isso, não poderão ser recriadas.
				</span>
			</c:if>
			<table class="tablesorter">
				<thead>
					<tr>
						<th></th>
						<th>Código da Linha</th>
						<th>Nome da Linha</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="linhaVigencia" items="${linhaVigenciaList}" varStatus="i">
					<tr>
						<td align="center">
							<c:if test="${(not linhaVigencia.bomLinhaFechado) and linhaVigencia.active}">
								<input type="checkbox" name="linhas[${i.index}].id" value="${linhaVigencia.id}" data-index="${i.index}" />
							</c:if>
						</td>
						<td><span <c:if test="${linhaVigencia.bomLinhaFechado }"> style="color: gray"</c:if>><c:out value="${linhaVigencia.codigo}" /></span></td>
						<td><span <c:if test="${linhaVigencia.bomLinhaFechado }"> style="color: gray"</c:if>><c:out value="${linhaVigencia.itinerarioIda}" /></span></td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:if test="${bom.temBomLinhaAberto}">
				<input type="submit" value="Recriar">
			</c:if>
			<input type="button" id="btnCancelar" value="Voltar" data-urlRetorno="<c:url value="/bom/${bom.id }/linhas"/>" />
		</form>
    </tiles:putAttribute>
</tiles:insertTemplate>