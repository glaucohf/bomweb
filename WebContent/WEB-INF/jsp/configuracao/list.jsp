<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://taglib.decatron.com.br/security" %>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/template.jsp">
	<tiles:putAttribute name="conteudo">
		<div id="filtro">
			<span><strong class="titulo azul">Configurações </strong><img src="<c:url value="/images/bomweb_setas.gif" />" /><strong class="titulo verde"> Sistema</strong></span>
		</div>
		
			<ul>
				<security:hasPermission target="Configuracao" action="bom">
					<p>
						<li><a href="<c:url value="/configuracao/bom" />" id="insert" class="">BOM</a></li>
					</p>
				</security:hasPermission>
				<security:hasPermission target="Permissao" action="listar">
					<p>
						<li>
							<a href="<c:url value="/configuracao/permissao/list" />" id="insert" class="">Permissões</a>
						</li>
					</p>
				</security:hasPermission>
			</ul>
		
    </tiles:putAttribute>
</tiles:insertTemplate>