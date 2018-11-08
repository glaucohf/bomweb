<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/template.jsp">
	<tiles:putAttribute name="header">
		<script type="text/javascript" src="<c:url value="/jscript/jquery.validate.js" />"></script>
		<script type="text/javascript" src="<c:url value="/jscript/jquery.tablesorter.min.js"/>"></script>
	</tiles:putAttribute>
	<tiles:putAttribute name="conteudo">
		<div id="inclusao">
			<span><strong class="titulo azul">Importar </strong><img src="<c:url value="/images/bomweb_setas.gif" />" /><strong class="titulo verde"> Tarifa</strong></span>
			<img src="<c:url value="/images/bomweb_filtros_box_up.jpg" />" />	
			<form id="formUpLoad" action="<c:url value="/tarifa/upload" />" method="post" enctype="multipart/form-data">
				<fieldset>
					<p>
					Esta página permite a atualização dos valores das tarifas, através da importação de uma planilha XLS.<br/>
					O arquivo com as tarifas pode ser obtido na página de listagem de tarifas (opção: Exportar), e após alterado, pode ser enviado pelo formulário abaixo.
					</p>
					<p>
						<label for="data">Data de Vigência:</label>
						<input type="text" id="dataInicioVigencia" name="data" class="required" maxlength="10" size="10">  * 
					</p>
					<p>
						<label for="arquivo">Arquivo XLS:</label>
						<input type="file" id="file" name="file" class="required"> * 
					</p>
					<div id="camposObrigatorios"><c:out value="* Campo obrigatório."/></div>
					<input type="button" class="upLoadTarifa" value="Importar">
					<input type="reset" class="reset" value="Limpar" style="display: none;" />
					<input type="button" id="btnCancelar" value="Cancelar" style="display: none;">
				</fieldset>
			</form>
			<img src="<c:url value="/images/bomweb_filtros_box_dn.jpg" />" />
		</div>		
		
		<div id='confirm' style="display: none;">
			<div class="white_content_header">
				<img src="<c:url value="/images/ico_modal_alert.png" />" align="absmiddle"> Alerta
			</div>
		
			<br/>
			<div class='message'></div><br/><br/><br/>
			
			<div class="white_content_footer">
				<input type="button" value="Sim" class="yes simplemodal-close">
				<input type="button" value="Não" class="simplemodal-close">
			</div>
		</div>	
	</tiles:putAttribute>
</tiles:insertTemplate>