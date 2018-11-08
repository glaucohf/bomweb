<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/form.jsp">
	<tiles:putAttribute name="formAction" cascade="true">
		<c:url value="/tipodeveiculo" />
	</tiles:putAttribute>
	<tiles:putAttribute name="formLegend" cascade="true">
		Tipo de Veículo
	</tiles:putAttribute>	
	<tiles:putAttribute name="formFields" cascade="true">
		<input type="hidden" name="entity.id" value="${t.id}">
		<input type="hidden" name="entity.active" value="${t.active}">

		<p>
			<label for="codigo">Código do Tipo de Veículo:</label>
			<input id="codigo" name="entity.codigo" value="${t.codigo}" class="required" size="3" maxlength="3" number="true" minlength="1" /> * 
		</p>
	
		<p>
			<label for="nome">Nome do Tipo de Veículo:</label>
			<input id="nome" name="entity.descricao" value="${t.descricao}" class="required" size="43" maxlength="32" minlength="2" /> * 
		</p>
	</tiles:putAttribute>
</tiles:insertTemplate>