<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/template.jsp">
	<tiles:putAttribute name="conteudo">
		<div id="inclusao">
			<span><strong class="titulo azul">Trocar Senha </strong></span>
			<img src="<c:url value="/images/bomweb_filtros_box_up.jpg" />" />
			<form action="<c:url value="/trocarsenha" />" method="post">
				<fieldset>
					<p>
						<label for="senhaatual">Senha Atual:</label> 
						<input id="senhaatual" type="password" name="changePasswordInformations.oldPassword" class="required" />
					</p>
					<p>
						<label for="novasenha">Nova Senha:</label> 
						<input id="novasenha" type="password" name="changePasswordInformations.newPassord" class="required" />
					</p>
					<p>
						<label for="confirmanovasenha">Confirmação de Senha:</label> 
						<input id="confirmanovasenha" type="password" name="changePasswordInformations.confirmPassword" class="required" />
					</p>
					<p>
					A senha deve possuir ao menos 8 caracteres, e ter ao menos um numeral e uma letra maiúscula.
					<p/>
					<div id="formControls">
						<input type="submit" value="Enviar" class="submit" /> 
						<input type="reset" value="Limpar" class="reset" />
					</div>
				</fieldset>
			</form>
			<img src="<c:url value="/images/bomweb_filtros_box_dn.jpg" />" />
		</div>
	</tiles:putAttribute>
</tiles:insertTemplate>