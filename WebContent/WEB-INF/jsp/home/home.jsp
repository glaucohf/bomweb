<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/template.jsp">
	<tiles:putAttribute name="header">
	</tiles:putAttribute>
	<tiles:putAttribute name="conteudo">
	<strong class="titulo azul">Principal </strong><img src="<c:url value="/images/bomweb_setas.gif" />" /><strong class="titulo verde"> BOMWeb</strong>
	<div id="home">
		<p style="text-align: justify;">
		Olá! Você acaba de acessar um sistema on-line criado especialmente para simplificar o canal de comunicação entre as Empresas Operadoras de Transporte Público do Estado do Rio de Janeiro e o Detro-RJ.
		</p>
		<p style="text-align: justify;">
		Com ele, o envio do Boletim de Operação Mensal (BOM) ficará mais simples, mais seguro e mais transparente. 
		Este sistema muda apenas a forma de enviar o boletim ao Detro. Os dados a serem transmitidos continuam os mesmos. 
		</p>
		<p style="text-align: justify;">
		Qualquer computador com acesso à Internet permite a utilização deste sistema. Acesse o menu de opções à esquerda da tela, insira os dados e conheça as vantagens deste sistema.
		</p>
	</div>
    </tiles:putAttribute>
</tiles:insertTemplate>