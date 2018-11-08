<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/template.jsp">
	<tiles:putAttribute name="conteudo">
		<strong class="titulo azul">Lista </strong><img src="<c:url value="/images/bomweb_setas.gif" />" /><strong class="titulo verde"><tiles:insertAttribute name="formLegend" /></strong>
		<c:if test="${not disableFilter}">
		<a href="#" class="accordion">[ Ocultar Filtro ]</a>
		</c:if>
		<div id="filtro">
		<c:if test="${not disableFilter}">
			<img src="<c:url value="/images/bomweb_filtros_box_up.jpg" />" />
		</c:if>
			<form id="formPesquisa" action="<tiles:insertAttribute name="formAction" />/list" method="post">
			<c:if test="${not disableFilter}">
				<fieldset>
					<tiles:insertAttribute name="searchFormFields" />
		    		<input type="submit" name="btn_pesquisa" value="Pesquisar" >
					<input class="reset" type="reset" value="Limpar"/>
		    	</fieldset>
		    </c:if>
			</form>
			<c:if test="${not disableFilter}">
			<img src="<c:url value="/images/bomweb_filtros_box_dn.jpg" />" />
			</c:if>
		</div>
		<br />
		<tiles:insertAttribute name="beforeTable" ignore="true" />
		<div id="tab_relatorio" style="width: 100%; overflow: auto;">
			<table id="tb_list" class="tablesorter">
				<thead>
					<tr>
						<tiles:insertAttribute name="tableHeaderColumns" />
					</tr>
				</thead>
				<tbody>
					<tiles:insertAttribute name="tableBodyColumns" />
				</tbody>
			</table>
		</div>
		<tiles:insertAttribute name="afterTable" ignore="true" />
		<c:if test="${not disableExport}">		
			<c:if test="${not empty tList or not empty tarifaList}">
			<a id="linkExport" href="<tiles:insertAttribute name="formAction" ignore="true"/>/exportar">Exportar</a>
			</c:if>
		</c:if>
    </tiles:putAttribute>
</tiles:insertTemplate>