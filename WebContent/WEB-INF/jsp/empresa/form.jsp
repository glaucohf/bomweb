<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="security" uri="http://taglib.decatron.com.br/security" %>
<jsp:useBean id="now" class="java.util.Date" />
<tiles:insertTemplate template="/WEB-INF/jsp/layout/form.jsp">
	<tiles:putAttribute name="formAction" cascade="true">
		<c:url value="/empresa" />
	</tiles:putAttribute>
	<tiles:putAttribute name="formLegend" cascade="true">
		Empresa
	</tiles:putAttribute>
	<tiles:putAttribute name="formFields" cascade="true">
		<input type="hidden" name="entity.id" value="${t.id}">
		<input type="hidden" name="entity.active" value="${t.active}">
		<input type="hidden" name="entity.dataCriacao" value="${t.dataCriacao}">

		<p>
			<label for="codigo" class="obrigatorio">Código:</label>
			<input id="codigo" type="text" name="entity.codigo" value="${t.codigo}" class="required" size="3" maxlength="3" minlength="3" number="true" <c:if test="${perfilEmpresa || operation eq 'view' or perfilEmpresa || operation eq 'edit'}">readonly="readonly"</c:if> /> * 
		</p>
	
		<p>
			<label for="nome" class="obrigatorio">Nome:</label>
			<input id="nome" type="text" name="entity.nome" value="${t.nome}" class="required" size="60" maxlength="52" minlength="2" <c:if test="${perfilEmpresa || operation eq 'view'}">readonly="readonly"</c:if> /> * 
		</p>

		<p>
			<label for="responsavel" class="obrigatorio">Operador:</label>
			<input id="responsavel" type="text" name="entity.responsavel" value="${t.responsavel}" size="40" maxlength="40" minlength="2" <c:if test="${operation eq 'view'}">readonly="readonly"</c:if> <security:hasRole roleName="Empresa"> class="required" </security:hasRole> /> <security:hasRole roleName="Empresa"> *</security:hasRole>
		</p>

		<p>
			<label for="emailContato" class="obrigatorio">Email:</label>
			<input id="emailContato" type="text" name="entity.emailContato" value="${t.emailContato}" class="email" size="40" maxlength="40" <c:if test="${operation eq 'view'}">readonly="readonly"</c:if> <security:hasRole roleName="Empresa"> class="required email" </security:hasRole> /> <security:hasRole roleName="Empresa"> *</security:hasRole>    
		</p>
		
		<p>
			<label for="inicioVigenciaBom">Início da Vigência do BOM:</label>
			<c:choose>
			<c:when test="${empty t.id }">
				<input id="inicioVigenciaBom" type="text" name="entity.inicioVigenciaBom" value="<fmt:formatDate value='${now}' pattern='dd/MM/yyyy' type='both' timeStyle='long' dateStyle='long' />"
				 class="required" size="10" maxlength="10" minlength="10"  <c:if test="${perfilEmpresa || operation eq 'view' }">readonly="readonly"</c:if> /> *
			</c:when>
			<c:otherwise>
				<input id="inicioVigenciaBom" type="text" name="entity.inicioVigenciaBom" value="<fmt:formatDate value='${t.inicioVigenciaBom}' pattern='dd/MM/yyyy' type='both' timeStyle='long' dateStyle='long' />"
				 class="required" size="10" maxlength="10" minlength="10"  <c:if test="${perfilEmpresa || operation eq 'view' }">readonly="readonly"</c:if> /> *
			</c:otherwise>
			</c:choose> 
		</p>
		
	</tiles:putAttribute>
</tiles:insertTemplate>