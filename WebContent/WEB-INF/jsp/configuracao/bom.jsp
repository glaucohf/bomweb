<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/template.jsp">
	<tiles:putAttribute name="conteudo">
		<strong class="titulo azul">Configurações </strong>
			<img src="<c:url value="/images/bomweb_setas.gif" />" />
		<strong class="titulo verde"> BOM</strong>
		<div id="filtro">
			<c:if test="${not disableFilter}">
				<img src="<c:url value="/images/bomweb_filtros_box_up.jpg" />" />
			</c:if>
			
			<form id="form" action="<c:url value="/configuracao/salvar"  />" method="post">
			<fieldset>
				<c:forEach items="${configuracaoList}" var="configuracao" varStatus="i">
					<input type="hidden" name="configuracoes[${i.index}].id" value="${configuracao.id}" />
					<p>
						<label for="valor${i.index}">${configuracao.nome}</label>
						<input type="text" id="valor${i.index}" name="configuracoes[${i.index}].valor" class="required" value="${configuracao.valor}" size="4" number="true" />
					</p>
				</c:forEach>
				<p>
					<input type="submit" value="Salvar" />
					<input type="button" id="btnCancelar" value="Cancelar" data-urlRetorno="<c:url value="/configuracao/list"/>" />
				</p>
			</fieldset>
			</form>
			
			<c:if test="${not disableFilter}">
				<img src="<c:url value="/images/bomweb_filtros_box_dn.jpg" />" />
			</c:if>
		</div>
    </tiles:putAttribute>
</tiles:insertTemplate>