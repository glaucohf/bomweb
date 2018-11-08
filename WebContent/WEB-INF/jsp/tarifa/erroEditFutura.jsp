<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/template.jsp">
	<tiles:putAttribute name="conteudo">
		<div class="white_content_header">
			<img src="<c:url value="/images/ico_modal_error.png" />" align="absmiddle"> Erro
		</div>
		<div class="white_content_content">
		<c:choose>
			<c:when test="${tarifa.futura}">
				<c:choose>
					<c:when test="${tarifa.temTarifaAnterior}">
						Esta tarifa ainda não está vigente e por isso não pode ser editada. Exclua-a e edite a tarifa vigente da linha correspondente.
					</c:when>
					<c:otherwise>
						Esta tarifa ainda não está vigente e por isso não pode ser editada. Exclua-a e crie outra tarifa para a linha correspondente.
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				Existe uma tarifa futura para esta linha/seção. Para poder editá-la, exclua a tarifa futura.
			</c:otherwise>
		</c:choose>
		</div>
		<div class="white_content_footer">
			<input type="button" value="Fechar" class="simplemodal-close">
		</div>
    </tiles:putAttribute>
</tiles:insertTemplate>