<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/template.jsp">
	<tiles:putAttribute name="conteudo">
		<div class="white_content_header">
			<img src="<c:url value="/images/ico_modal_alert.png" />" align="absmiddle"> Alerta
		</div>
		<div class="white_content_content">
			As tarifas futuras ser�o substitu�das ao realizar esta importa��o. Deseja Confirmar?<br/>
		</div>
		<div class="white_content_footer">
			<input type="button" value="Confirmar" onclick="window.location='<c:url value="/tarifa/formUpload"/>'">
			<input type="button" value="Cancelar" class="simplemodal-close">
		</div>
	</tiles:putAttribute>		
</tiles:insertTemplate>