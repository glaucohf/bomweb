<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://taglib.decatron.com.br/security" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<tiles:insertTemplate template="/WEB-INF/jsp/layout/list.jsp">
	<tiles:putAttribute name="formLegend" cascade="true">
	Empresa
	</tiles:putAttribute>
	<tiles:putAttribute name="formAction" cascade="true">
		<c:url value="/empresa" />
	</tiles:putAttribute>	
	<tiles:putAttribute name="searchFormFields" cascade="true">
		<p>
			<label for="codigo">Código:</label>
			<input name="entityForSearch.codigo" value="${entityForSearch.codigo}" size="3" maxlength="3"/>
		</p>
		<p>
			<label for="nome">Nome:</label>
			<input name="entityForSearch.nome" value="${entityForSearch.nome}" />
		</p>
	</tiles:putAttribute>
	<tiles:putAttribute name="beforeTable" cascade="true">
		<security:hasPermission target="Empresa" action="incluir">
			<a id="insert" href="<c:url value="/empresa/insert" />">Nova Empresa</a>
		</security:hasPermission>
	</tiles:putAttribute>
	<tiles:putAttribute name="tableHeaderColumns" cascade="true">
		<th>Código da Empresa</th>
		<th>Nome da Empresa</th>
		<th>Operador</th>
		<th>Email</th>
		<th>Início da Vigência do BOM</th>
		<th>Ações</th>
	</tiles:putAttribute>
	<tiles:putAttribute name="tableBodyColumns" cascade="true">
		<c:forEach items="${tList}" var="emp">
			<tr>
				<td>${emp.codigo}</td>
				<td>${emp.nome}</td>
				<td>${emp.responsavel}</td>
				<td>${emp.emailContato}</td>
				<td><fmt:formatDate value="${emp.inicioVigenciaBom}" pattern="dd/MM/yyyy" type="both" timeStyle="long" dateStyle="long" /></td>
				
				<td nowrap="nowrap">
				<!--  
					<a href="view/${emp.id}" class="link_modal_view"><img src="<c:url value="/images/abrir.png" />" /></a> &nbsp;
				--> 
					<security:hasPermission target="Empresa" action="editar">
						<a href="edit/${emp.id}" title="Editar"><img src="<c:url value="/images/editar.png" />" /></a> &nbsp; 
					</security:hasPermission>
					<security:hasPermission target="Empresa" action="excluir">
						<a href="delete-confirm/${emp.id}" class="link_modal_view" title="Excluir"><img src="<c:url value="/images/excluir.png" />" /></a>
					</security:hasPermission>
				</td>
			</tr>
		</c:forEach>
	</tiles:putAttribute>
</tiles:insertTemplate>