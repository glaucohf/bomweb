<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/template.jsp">
	<tiles:putAttribute name="conteudo">
		<div class="white_content_header">
			<img src="<c:url value="/images/ico_modal_alert.png" />" align="absmiddle"> Alerta
		</div>
		<div class="white_content_content">
				Essa linha cont�m altera��es programadas. <br />
			 	Deseja alterar a linha atual ou a altera��o futura? <br/>
				OBS: Altera��es na linha atual descartar�o as altera��o programadas para o futuro.
		</div>
		<div class="white_content_footer">
			<input type="button" value="Atual" onclick="window.location='<c:url value="/linha/edit" />/${linha.id}'">
			<input type="button" value="Futura" onclick="window.location='<c:url value="/linha/edit-futuro" />/${linha.id}'">
			<input type="button" value="Cancelar" class="simplemodal-close">
		</div>
    </tiles:putAttribute>
</tiles:insertTemplate>