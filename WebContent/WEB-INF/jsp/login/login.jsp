<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="message" uri="http://taglib.decatron.com.br/message" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>BomWeb</title>
<meta http-equiv="X-UA-Compatible" content="IE=8" >
<link rel="stylesheet" type="text/css" href="<c:url value="/css/estilos.css" />">
<script type="text/javascript" src="<c:url value="/jscript/jquery-1.5.min.js"/>"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("#login2").focus();
});
</script>
</head>
<body>
<div id="page">
	<div id="header"></div>
	<div id="container">
		<div class="separador"></div>
		<div id="login">
			<div class="separador"></div>
			<span><strong>Bem vindo ao Sistema Online BomWeb!</strong><br/>Entre com seus dados para ter acesso.</span><br />
			<message:displayMessages styleClass="error-login" />
			<form action="<c:url value="/autenticar" />" method="post">
				<fieldset>
					<img src="<c:url value="images/bomweb_menu_arrow.png" />"> Login <input id="login2" type="text" name="credentials.username" class="required" maxlength="30" tabindex="1"/><br />
					<img src="<c:url value="images/bomweb_menu_arrow.png" />"> Senha <input id="senha" type="password" name="credentials.password" class="required"  maxlength="30" tabindex="2"/><br />
					<span><a href="<c:url value="/esquecisenha" />" tabindex="4">Esqueceu sua senha, <strong>clique aqui</strong>.</a></span><br />
					<input type="image" src="<c:url value="images/bomweb_buttom_entrar.png" />" class="buttom_entrar" tabindex="3"/>
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