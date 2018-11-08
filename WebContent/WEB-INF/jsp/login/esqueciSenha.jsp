<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="message" uri="http://taglib.decatron.com.br/message" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>BomWeb</title>
<meta http-equiv="X-UA-Compatible" content="IE=8" >
<link rel="stylesheet" type="text/css" href="<c:url value="/css/estilos.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/styles.css" />">
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
<script type="text/javascript" src="<c:url value="/jscript/jquery.cookie.js" />"></script>
<script type="text/javascript" src="<c:url value="/jscript/jquery.treeview.js" />"></script>	
<script type="text/javascript" src="<c:url value="/jscript/jquery.maskMoney.js" />"></script>
<script type="text/javascript" src="<c:url value="/jscript/jquery.loading.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/jscript/bomweb.js?3" />"></script>
<script type="text/javascript" src="<c:url value="/jscript/bom.js?3" />"></script>
<script type="text/javascript">
	$("#login").focus();
</script>
</head>
<body onload="mensagem('${mensagem}');">
<div id="page">
	<div id="header"></div>
	<div id="container">
		<div class="separador"></div><!--
		<div id="erros">
			<c:forEach var="error" items="${errors}">
	  				${error.message} <br />
			</c:forEach>
		</div>
		--><div id="login">
			<div class="separador"></div>
			<span><strong>Redefinição de senha</strong></span>
			<message:displayMessages styleClass="error-login" />
			<form id="formNovaSenha" action="<c:url value="/novasenha" />" method="post">
				<fieldset>
				<img src="<c:url value="images/bomweb_menu_arrow.png" />"> Login <input id="login2" type="text" name="credentials.username" maxlength="30"/><br />
				<input type="image" id="novaSenha" src="<c:url value="images/bomweb_buttom_confirmar.png" />" class="buttom_entrar" />
				</fieldset>
			</form>
		</div>
	</div>
	<div class="separador"></div>
	<div id="footer">
		<div id="version_control">${version}</div>
	</div>
</div>
</body>
</html>