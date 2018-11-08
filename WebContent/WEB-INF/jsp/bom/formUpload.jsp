<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/template.jsp">
	<tiles:putAttribute name="header">
		<script type="text/javascript" src="<c:url value="/jscript/jquery.tablesorter.min.js"/>"></script>
	</tiles:putAttribute>
	<tiles:putAttribute name="conteudo">
		<div id="inclusao">
			<span><strong class="titulo azul">Importar </strong><img src="<c:url value="/images/bomweb_setas.gif" />" /><strong class="titulo verde"> BOM</strong></span>
			<img src="<c:url value="/images/bomweb_filtros_box_up.jpg" />" />	
			<form action="<c:url value="/bom/upload" />" method="post" enctype="multipart/form-data">
				<input type="hidden" id="bom" name="bom.id" value="${bom.id}" />
				<fieldset>
					<p>
					Esta página permite o preenchimento do BOM, através da importação de uma planilha XLS.<br/>
					</p>
					<p>
						<label for="arquivo">Arquivo XLS:</label>
						<input type="file" id="file" name="file" class="required"> * 
					</p>
					<div id="camposObrigatorios"><c:out value="* Campo obrigatório."/></div>
					<input type="submit" value="Importar">
					<input type="button" id="btnCancelar" value="Cancelar" style="display: none;" data-urlRetorno="-1">
				</fieldset>
			</form>
			<img src="<c:url value="/images/bomweb_filtros_box_dn.jpg" />" />
		</div>			
	</tiles:putAttribute>
</tiles:insertTemplate>