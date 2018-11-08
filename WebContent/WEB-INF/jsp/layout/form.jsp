<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/template.jsp">
	<tiles:putAttribute name="conteudo">
		<span><strong class="titulo azul">Cadastro </strong><img src="<c:url value="/images/bomweb_setas.gif" />" /><strong class="titulo verde"><tiles:insertAttribute name="formLegend" /></strong></span>
		<div id="inclusao">
			<img src="<c:url value="/images/bomweb_filtros_box_up.jpg" />" />
			<c:choose>
				<c:when test="${operation == 'insert'}">
					<c:set var='op' value='save' />	
				</c:when>
				<c:when test="${operation == 'edit'}">
					<c:set var='op' value='update' />	
				</c:when>
			</c:choose>
			<form id="form" name="form" action="<tiles:insertAttribute name="formAction" />/${op}" method="post">
				<fieldset>
					<tiles:insertAttribute name="formFields" />
					<c:if test="${operation ne 'view'}">
						<div id="formControls"> 
							<div id="camposObrigatorios"><c:out value="* Campo obrigatório."/></div>
							<input type="submit" value="Salvar" class="submit" />
							<c:if test="${operation ne 'edit'}">
								<input type="reset" class="reset" value="Limpar" style="display: none;" />
							</c:if>  
							<input type="button" id="btnCancelar" value="Cancelar" style="display: none;" />
						</div>
					</c:if>
				</fieldset>
			</form>
			<img src="<c:url value="/images/bomweb_filtros_box_dn.jpg" />" />
		</div>
	</tiles:putAttribute>
</tiles:insertTemplate>