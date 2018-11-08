<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/template.jsp">
	<tiles:putAttribute name="header">
		<script type="text/javascript" src="<c:url value="/jscript/jquery.dataTables.min.js" />"></script>
		<script type="text/javascript" src="<c:url value="/jscript/ListPage.js" />"></script>
	</tiles:putAttribute>
	
	<script type="text/javascript">
	
		function pesquisar() {
			listPage.initialize();
		}
	</script>
	
	<tiles:putAttribute name="conteudo">
		<strong class="titulo azul">Lista </strong><img src="<c:url value="/images/bomweb_setas.gif" />" /><strong class="titulo verde"><tiles:insertAttribute name="formLegend" /></strong>
		<c:if test="${not disableFilter}">
		<a href="#" class="accordion">[ Ocultar Filtro ]</a>
		</c:if>
		<div id="filtro">
		<c:if test="${not disableFilter}">
			<img src="<c:url value="/images/bomweb_filtros_box_up.jpg" />" />
		</c:if>
			<form id="formPesquisa" method="post">
			<c:if test="${not disableFilter}">
				<fieldset>
					<tiles:insertAttribute name="searchFormFields" />
		    		<input type="button" id="dataTableFetchData" value="Pesquisar" onclick="javascript:pesquisar()">
					<input class="reset" id="reseta" type="reset" value="Limpar"/>
		    	</fieldset>
		    </c:if>
			</form>
			<c:if test="${not disableFilter}">
			<img src="<c:url value="/images/bomweb_filtros_box_dn.jpg" />" />
			</c:if>
		</div>
		<br />
		<tiles:insertAttribute name="beforeTable" ignore="true" />
		<div id="divDataTable" style="width: 100%; overflow: auto;">
		</div>
		<tiles:insertAttribute name="afterTable" ignore="true" />
		<c:if test="${not disableExport}">		
			<c:if test="${not empty tList or not empty tarifaList}">
			<a id="linkExport" href="<tiles:insertAttribute name="formAction" ignore="true"/>/exportar">Exportar</a>
			</c:if>
		</c:if>
    </tiles:putAttribute>
</tiles:insertTemplate>