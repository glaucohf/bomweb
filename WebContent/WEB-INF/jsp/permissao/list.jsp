<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/template.jsp">
	<tiles:putAttribute name="conteudo">
		<div id="filtro">
			<span><strong class="titulo azul">Permissões </strong><img src="<c:url value="/images/bomweb_setas.gif" />" /><strong class="titulo verde"> Perfil</strong></span>
			<img src="<c:url value="/images/bomweb_filtros_box_up.jpg" />" />
			<form name="formPerfil" action="<c:url value="/permissao/save"/>">
				<fieldset>
		     		<p>
						<label for="perfil">Perfil:</label>
						<select id="perfil" name="perfil.id" class="required">
							<option value="">Selecione o Perfil</option>
							<c:forEach var="item" items="${perfis}">
								<option value="${item.id}" <c:if test="${perfil.id eq item.id}">selected="selected"</c:if>><c:out value="${item.nome}"></c:out></option>
							</c:forEach>
						</select>
						<input id="btn_submit_perfil" type="submit" value="Selecionar" />
					</p>
				</fieldset>
		</div>
		<img src="<c:url value="/images/bomweb_filtros_box_dn.jpg" />" />
			<p></p>
			<c:if test="${not empty permissoes}">
			<fieldset><legend>Permissões / Ações</legend>
				<ul class="treeview" id="tree">
				<c:forEach var="recurso" items="${permissoes}">
				 	<li><span class="folder">${recurso.nome}</span>
	 			 		<ul>
				 			<c:forEach var="acao" items="${recurso.acoes}">
								<li>
									<span class="file">
										<input type="checkbox" name="permissoes[${acao.id}].id" value="${acao.id}"<c:if test="${acao.checked}"> checked="checked"</c:if>/>${acao.nome}
									</span>
								</li>
				 			</c:forEach>
				 		</ul>
				 	</li>
				 </c:forEach>
				</ul>
			</fieldset>	
			<p>
			<input id="btn_salvar_submit_perfil" type="submit" value="Salvar"/>
			<input id="btnCancelar" type="button" value="Cancelar"  data-urlRetorno="<c:url value="/configuracao/list"/>" />
			</p>
			</c:if>
		</form>
    </tiles:putAttribute>
</tiles:insertTemplate>