<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://taglib.decatron.com.br/security" %>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/template.jsp">
	<tiles:putAttribute name="header">
		<script type="text/javascript" src="<c:url value="/jscript/jquery.dataTables.min.js" />"></script>
		<script type="text/javascript" src="<c:url value="/jscript/ListPage.js" />"></script>
	</tiles:putAttribute>
	<tiles:putAttribute name="conteudo">
	<script type="text/javascript" src="<c:url value="/jscript/listBom.js" />"></script>	
		<security:notHasRole roleName="Empresa">
		<strong class="titulo azul">Lista </strong><img src="<c:url value="/images/bomweb_setas.gif" />" /><strong class="titulo verde"> BOM</strong>
		<a href="#" class="accordion">[ Ocultar Filtro ]</a>
		<div id="filtro">
			<img src="<c:url value="/images/bomweb_filtros_box_up.jpg" />" />
			<form id="formPesquisa" action='<c:url value="/bom/list" />' method="post">
				<fieldset>
					<p>
						<label for="buscaEmp">Empresa:</label>
						<input type="text" id="buscaEmp" name="bom.empresa.nome" value="${bom.empresa.nome}" onblur="limpaId(this,'idEmpresa')" style="width:350px;"/>
						<input type="text" id="idEmpresa" name="bom.empresa.id" value="${bom.empresa.id}" style="visibility: hidden;"/>
					</p>
					<p>					
						<label for="mes_referencia">M�s/Ano de Refer�ncia:</label> 
						<input type="text" id="mes_referencia" name="bom.mesReferencia" value="${bom.mesReferencia}" size="7"/>
					</p>
		    		<input type="button" name="btn_pesquisa" value="Pesquisar" id="btn_pesquisa">
		    		<input class="reset" type="reset" value="Limpar"/>
		    		<security:hasPermission target="Bom" action="pendentes" >
						<input type="button" value="BOMs Pendentes" style="position:relative;left:70%;" onclick="window.location='<c:url value="/bom/pendentes" />'"/>
					</security:hasPermission>
		    	</fieldset>
			</form>
			<img src="<c:url value="/images/bomweb_filtros_box_dn.jpg" />" />
		</div>
		</security:notHasRole>
		<input type="hidden" value="${perfil.nome}" id="perfil"/>
		<security:hasRole roleName="Empresa">
		<strong class="titulo azul">Lista </strong><img src="<c:url value="/images/bomweb_setas.gif" />" /><strong class="titulo verde"> BOM</strong>
		</security:hasRole>
		<br />
		<security:hasPermission target="Bom" action="novo" >
		<a id="insert" href="<c:url value="/bom/insert" />" class="buttom_azul">Novo BOM</a>
		</security:hasPermission>
		<security:hasRole roleName="Empresa">
		<security:hasPermission target="Bom" action="pendentes" >
		<a id="linkPendentes" href="<c:url value="/bom/pendentes"/>">BOMs Pendentes</a><br />
		</security:hasPermission>
		</security:hasRole>
		<div id="divDataTable" style="width: 100%; overflow: auto;">
		</div>
		
<div id="modalView" style="display: none;" class="simplemodal-data">
	<div class="white_content_header">
		<img src="/bomweb/images/ico_modal_alert.png" align="absmiddle"> Alerta
	</div>
	
	<div class="messageReabrir white_content_content">
	</div>
	
	<div class="white_content_footer">
		<input id="btnSimReabrirBom" type="button" value="Sim">
		<input class="simplemodal-close" type="button" value="N�o">
	</div>

</div>
    </tiles:putAttribute>
</tiles:insertTemplate>