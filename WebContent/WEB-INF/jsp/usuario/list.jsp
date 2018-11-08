<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://taglib.decatron.com.br/security" %>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/list.jsp">
	<tiles:putAttribute name="formLegend" cascade="true">
	Usuário
	</tiles:putAttribute>
	<tiles:putAttribute name="formAction" cascade="true">
		<c:url value="/usuario" />
	</tiles:putAttribute>	
	<tiles:putAttribute name="searchFormFields" cascade="true">
		<p>
			<label for="perfil">Perfil:</label>
			<select id="perfilUsuario" name="entityForSearch.perfil.id">
				<option value="" Selected="true">Selecione um perfil</option>
				<c:forEach var="item" items="${perfis}">
					<option value="${item.id}" <c:if test="${entityForSearch.perfil.id == item.id}">selected="true"</c:if>><c:out value="${item.nome}"></c:out></option>
				</c:forEach>
			</select>
		</p>
		<p>
			<label for="nome">Nome:</label>
			<input id="nome" name="entityForSearch.usuario.nome" value="${entityForSearch.usuario.nome}" />
		</p>	
		</tiles:putAttribute>	
		<tiles:putAttribute name="beforeTable" cascade="true">
			<security:hasPermission target="Usuario" action="incluir">
				<a id="insert" href="<c:url value="/usuario/insert" />" class="buttom_azul">Novo Usuário</a>
			</security:hasPermission>
		</tiles:putAttribute>
	<tiles:putAttribute name="tableHeaderColumns" cascade="true">
		<th>Perfil</th>
		<th>Nome</th>
		<th>Login</th>
		<th>Email do Responsável</th>		
		<th>Ações</th>
	</tiles:putAttribute>
	<tiles:putAttribute name="tableBodyColumns" cascade="true">
		<c:forEach items="${tList}" var="up">
			<tr>
				<td>${up.perfil.nome}</td>
				<td>${up.usuario.nome}</td>
				<td>${up.usuario.login}</td>
				<td>${up.usuario.email}</td>
				<td nowrap="nowrap">
				<security:hasPermission target="Usuario" action="editar"> 
					<a href="edit/${up.usuario.id}" title="Editar"><img src="<c:url value="/images/editar.png" />" /></a> &nbsp;
				</security:hasPermission>
				<security:hasPermission target="Usuario" action="excluir">
					<a href="delete-confirm/${up.usuario.id}" class="link_modal_view" title="Excluir"><img src="<c:url value="/images/excluir.png" />" /></a>  &nbsp;
				</security:hasPermission>
				<security:hasPermission target="Usuario" action="redefinir senha">
					<a href="redefineSenha-confirm/${up.usuario.id}" class="link_modal_view" title="Gerar nova senha"><img src="<c:url value="/images/gerar_senha.png" />" /></a> 
				</security:hasPermission>
				</td>
			</tr>
		</c:forEach>
	</tiles:putAttribute>
	<tiles:putAttribute name="csvFileName" cascade="true">usuario</tiles:putAttribute>	
</tiles:insertTemplate>