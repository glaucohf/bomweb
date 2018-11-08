<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="/WEB-INF/custom-functions.tld" %>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/form.jsp">
	<tiles:putAttribute name="formAction" cascade="true">
		<c:url value="/usuario" />
	</tiles:putAttribute>
	<tiles:putAttribute name="formLegend" cascade="true">
	<script type="text/javascript">

		//Auto complete da Busca
		$(function() {

			$( "#buscaEmp" ).autocomplete({
				width:352,
				source: function( request, response ) {
					$.ajax({
						url: "${pageContext.request.contextPath}/usuario/busca.json?term="+$( "#buscaEmp" ).val(),
						dataType: "json",
						data: {
							featureClass: "P",
							style: "full",
							maxRows: 12,
							name_startsWith: request.term
						},
						success: function( data ) {
							response( $.map( data.empresa, function( item ) {
								return {
									value: item.nome,
									data: item.id,
									label: item.nome,
								};
							}));
						}
					});
				},
				minLength: 0,
				select: function(event, ui) { 
					$("#empresaUsuario").val(ui.item.data);
					setNomeUsuario($("#empresaUsuario"));
				 }
			});
		});

	</script>
		Usuário
	</tiles:putAttribute>
	<tiles:putAttribute name="formFields" cascade="true">
		<input type="hidden" name="entity.usuario.id" value="${usuarioPerfil.usuario.id}">
		<p>
			<label for="perfilUsuario">Perfil:</label>
   			<select id="perfilUsuario" name="entity.perfil.id" class="required" <c:if test="${operation eq 'edit'}">disabled="disabled"</c:if> />
   				<option value="" Selected="true">Selecione um perfil</option>
             	<c:forEach var="item" items="${perfis}">
               		<option value="${item.id}" <c:if test="${usuarioPerfil.perfil.id == item.id}">selected="true"</c:if>><c:out value="${item.nome}"></c:out></option>
             	</c:forEach>
			</select> * 
		</p>
	   	<div id="divEmpresaUsuario" <c:if test="${not usuarioPerfil.perfil.empresa}">style="display: none"</c:if>>
			<p>
				<label for="empresaUsuario">Empresa:</label>
				<input type="text" id="buscaEmp" name="entity.usuario.empresa.nome" value="${usuarioPerfil.usuario.empresa.nome}" onblur="limpaId(this,'empresaUsuario');" <c:if test="${operation eq 'edit'}">disabled="disabled"</c:if> style="width:350px;"/>
				<input type="text" id="empresaUsuario" name="entity.usuario.empresa.id" value="${usuarioPerfil.usuario.empresa.id}" style="visibility: hidden;"/>
			</p>
		</div>
		<p>
			<label for="nomeUsuario">Nome:</label>
			<input type="text" id="nomeUsuario" name="entity.usuario.nome" value="${usuarioPerfil.usuario.nome}" class="required" /> * 
		</p>
		<p>
			<label for="loginUsuario">Login:</label>
			<input type="text" id="loginUsuario" name="entity.usuario.login" value="${usuarioPerfil.usuario.login}" class="required" <c:if test="${operation eq 'edit'}">readonly="readonly"</c:if> /> * 
		</p>
		<p>
			<label for="emailUsuario">Email do Responsável:</label>
			<input type="text" id="emailUsuario" name="entity.usuario.email" value="${usuarioPerfil.usuario.email}" class="email required" size="40" maxlength="100" /> * 
		</p>
	</tiles:putAttribute>
</tiles:insertTemplate>