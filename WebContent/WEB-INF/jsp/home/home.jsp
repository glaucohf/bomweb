<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/template.jsp">
	<tiles:putAttribute name="header">
	</tiles:putAttribute>
	<tiles:putAttribute name="conteudo">
	<strong class="titulo azul">Principal </strong><img src="<c:url value="/images/bomweb_setas.gif" />" /><strong class="titulo verde"> BOMWeb</strong>
	<div id="home">
		<p style="text-align: justify;">
		Ol�! Voc� acaba de acessar um sistema on-line criado especialmente para simplificar o canal de comunica��o entre as Empresas Operadoras de Transporte P�blico do Estado do Rio de Janeiro e o Detro-RJ.
		</p>
		<p style="text-align: justify;">
		Com ele, o envio do Boletim de Opera��o Mensal (BOM) ficar� mais simples, mais seguro e mais transparente. 
		Este sistema muda apenas a forma de enviar o boletim ao Detro. Os dados a serem transmitidos continuam os mesmos. 
		</p>
		<p style="text-align: justify;">
		Qualquer computador com acesso � Internet permite a utiliza��o deste sistema. Acesse o menu de op��es � esquerda da tela, insira os dados e conhe�a as vantagens deste sistema.
		</p>
	</div>
    </tiles:putAttribute>
</tiles:insertTemplate>