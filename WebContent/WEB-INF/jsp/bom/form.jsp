<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/template.jsp">
	<tiles:putAttribute name="conteudo">
		<div id="inclusao">
			<span><strong class="titulo azul">Novo BOM </strong></span>
			<img src="<c:url value="/images/bomweb_filtros_box_up.jpg" />" />
			<form id="formBom" action="<c:url value="/bom/generate" />" method="post">
				<fieldset>
					<p>
						<label for="mes_referencia">Mês/Ano de Referência</label> 
						<input type="text" id="mes_referencia" name="bom.mesReferencia" class="required" size="8" />
					</p>
					<input type="submit" value="Salvar">
					<input type="button" id="btnCancelar" value="Cancelar" style="display: none;">
				</fieldset>
			</form>
			<img src="<c:url value="/images/bomweb_filtros_box_dn.jpg" />" />
		</div>
    </tiles:putAttribute>
</tiles:insertTemplate>