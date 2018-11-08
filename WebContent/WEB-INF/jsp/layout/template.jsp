<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://taglib.decatron.com.br/security" %>
<%@ taglib prefix="message" uri="http://taglib.decatron.com.br/message" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="pt_BR"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>BomWeb</title>
		<meta http-equiv="X-UA-Compatible" content="IE=8" >
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/estilos.css" />">
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.ui.all.css"/>">
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.simplemodal.css" />">
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.treeview.css" />">
		<script type="text/javascript" src="<c:url value="/jscript/jquery-1.5.min.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/jscript/jquery.ui.1.8.min.js" />"></script>
		<script type="text/javascript" src="<c:url value="/jscript/jquery.simplemodal.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/jscript/jquery.maskedinput-1.3.min.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/jscript/jquery.validate.min.js" />"></script>
		<script type="text/javascript" src="<c:url value="/jscript/jquery.tablesorter.min.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/jscript/jquery.multiselect.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/jscript/jquery.treeview.js" />"></script>	
		<script type="text/javascript" src="<c:url value="/jscript/jquery.maskMoney.js" />"></script>
		<script type="text/javascript" src="<c:url value="/jscript/jquery.loading.min.js" />"></script>
		<script type="text/javascript" src="<c:url value="/jscript/jshashtable-2.1.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/jscript/jquery.numberformatter-1.2.2.min.js"/>"></script>

		<script type="text/javascript">
		var pathName = "${pageContext.request.contextPath}";	
		</script>
		
		<tiles:insertAttribute name="header" ignore="true" />
		<script type="text/javascript" src="<c:url value="/jscript/bomweb.js?3" />"></script>
		<script type="text/javascript" src="<c:url value="/jscript/bom.js?3" />"></script>	
	</head>
<body onunload="javascript: bomweb.hideLoading();">
<div id="page">
	<div id="header"></div>
	<div id="access_control">
		<div>
			${token.user.nome} [ ${token.roles[0].nome} ] &nbsp;&nbsp;
			<ul>
				<li><a tabindex="-1" href="<c:url value="/trocarsenha" />">Trocar Senha</a></li>
				<li><a tabindex="-1" href="<c:url value="/sair" />">Sair</a></li>
			</ul>
		</div>
	</div>
	<div id="container">	
		<div class="separador"></div>
		<div id="menu">
			<ul>
				<security:hasPermission target="Empresa" action="listar">
				<li><a tabindex="-1" href="<c:url value="/empresa/list" />">Empresa</a></li>
				</security:hasPermission>
				<security:hasPermission target="Linha" action="listar">
				<li><a tabindex="-1" href="<c:url value="/linha/list" />">Linha</a></li>
				</security:hasPermission>
				<security:hasPermission target="TipoVeiculo" action="listar">
				<li><a tabindex="-1" href="<c:url value="/tipodeveiculo/list" />">Tipo de Veículo</a></li>
				</security:hasPermission>
				<security:hasPermission target="TipoLinha" action="listar">
				<li><a tabindex="-1" href="<c:url value="/tipodelinha/list" />">Tipo de Linha</a></li>
				</security:hasPermission>				
				<security:hasPermission target="Tarifa" action="listar">
				<li><a tabindex="-1" href="<c:url value="/tarifa/list" />">Tarifa</a></li>
				</security:hasPermission> 
				<security:hasPermission target="Usuario" action="listar">
				<li><a tabindex="-1" href="<c:url value="/usuario/list" />">Usuário</a></li>
				</security:hasPermission>
				<security:hasPermission target="Bom" action="listar">
				<li><a tabindex="-1" href="<c:url value="/bom/list" />">BOM</a></li>
				</security:hasPermission>
				<security:hasPermission target="Relatorio" action="exportar">
				<li><a tabindex="-1" href="<c:url value="/relatorio/list" />">Relatório</a></li>	
				</security:hasPermission>
				<security:hasPermission target="Log" action="listar">
				<li><a tabindex="-1" href="<c:url value="/log/list" />">Log</a></li>
				</security:hasPermission>
				<security:hasPermission target="Configuracao" action="listar">
				<li><a tabindex="-1" href="<c:url value="/configuracao/list" />">Configurações</a></li>
				</security:hasPermission>
				<security:hasPermission target="TarifaRetroativa" action="upload"> 
				<li><a tabindex="-1" href="<c:url value="/tarifaRetroativa/formUpload" />" >Tarifa Retroativa</a></li>
				</security:hasPermission>
				<security:hasPermission target="ImportaLinha" action="upload"> 
				<li><a tabindex="-1" href="<c:url value="/importaLinha/formUpload" />" >Importar Linhas</a></li>
				</security:hasPermission>
				<li><a tabindex="-1" href="<c:url value="/manual/download" />">Manual</a></li>				
			</ul>
		</div>
		<c:if test="${not empty ViewMessages or not empty errors or not empty mensagem}">
		<c:if test="${not empty ViewMessages and fn:length(ViewMessages) gt 1}">
   			<div id="messages" style="display: none">
   				<div class="white_content_header">
					<c:if test="${ViewMessages[0].severity eq 'ERROR'}">
						<img src="<c:url value="/images/ico_modal_error.png" />" align="absmiddle"> Erro
					</c:if>
					<c:if test="${ViewMessages[0].severity eq 'WARN'}">
						<img src="<c:url value="/images/ico_modal_alert.png" />" align="absmiddle"> Alerta
					</c:if>
					<c:if test="${ViewMessages[0].severity eq 'INFO'}">
						<img src="<c:url value="/images/ico_modal_ok.png" />" align="absmiddle"> Informação
					</c:if>
				</div>
				<div class="white_content_content">
	   				<div>
						<ul>
							<c:forEach var="message" items="${ViewMessages}">
							<p><li type="disc" style="text-align: justify">${message.message}</li></p>
							</c:forEach>
						</ul>
					</div>
				</div>
				<div class="white_content_footer">
					<input type="button" value="Fechar" class="simplemodal-close">
				</div>
			</div>			
		</c:if>
		<c:if test="${empty ViewMessages or fn:length(ViewMessages) lt 2}">
			<div id="messages" style="display: none">
				<div class="white_content_header">
					<c:if test="${not empty errors}">
						<img src="<c:url value="/images/ico_modal_error.png" />" align="absmiddle"> Erro
					</c:if>
					<c:if test="${not empty mensagem}">
						<img src="<c:url value="/images/ico_modal_alert.png" />" align="absmiddle"> Alerta
					</c:if>
					<c:if test="${not empty ViewMessages}">
						<c:if test="${ViewMessages[0].severity eq 'ERROR'}">
							<img src="<c:url value="/images/ico_modal_error.png" />" align="absmiddle"> Erro
						</c:if>
						<c:if test="${ViewMessages[0].severity eq 'WARN'}">
							<img src="<c:url value="/images/ico_modal_alert.png" />" align="absmiddle"> Alerta
						</c:if>
						<c:if test="${ViewMessages[0].severity eq 'INFO'}">
							<img src="<c:url value="/images/ico_modal_ok.png" />" align="absmiddle"> Informação
						</c:if>
					</c:if>
				</div>
				<div class="white_content_content">
					<ul>
						<c:if test="${not empty mensagem}">
							<li>${mensagem}</li>
						</c:if>
						<c:if test="${not empty errors}">
							<c:forEach var="error" items="${errors}">
								<li>${error.message}</li>
							</c:forEach>
						</c:if>
						<c:if test="${not empty ViewMessages}">
							<c:forEach var="message" items="${ViewMessages}">
								<li>${message.message}</li>
							</c:forEach>
						</c:if>
					</ul>
				</div>
				<div class="white_content_footer">
					<input type="button" value="Fechar" class="simplemodal-close">
				</div>
			</div>
		</c:if>
		<script type="text/javascript">
		$(function(){
			bomweb.openModalPanelMessages();
		});
		
		</script>
		</c:if>
	    <div id="info">
		    <tiles:insertAttribute name="info" ignore="true" />   
	  	</div>
		<div id="result">
			<tiles:insertAttribute name="conteudo" />
		</div>
	</div>
	<div class="separador"></div>
	<img src="<c:url value="/images/bomweb_footer_space.gif" />" />
	<div id="footer">
		<div id="version_control">${version}</div>
	</div>
</div>
<div id="modalView" style="display: none">
	<tiles:insertAttribute name="modalViewConteudo" ignore="true" />
</div>
</body>
</html>