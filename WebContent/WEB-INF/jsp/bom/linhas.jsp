<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://taglib.decatron.com.br/security" %>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/template.jsp">
	<tiles:putAttribute name="conteudo">
	<strong class="titulo azul">BOM </strong><img src="<c:url value="/images/bomweb_setas.gif" />" /><strong class="titulo verde"> ${bom.mesReferencia}</strong>
	<form id="formBom" action="<c:url value="/bom/linhas/inoperantes" />" method="post">
		<input type="hidden" id="bom" name="bom.id" value="${bom.id}">
		<div id="passos" align="center">
		<span>Linhas do BOM</span> <b>></b> <span style="color:#bbb">Linha</span> <b>></b> <span style="color:#bbb">Seções da Linha</span> <b>></b> <span style="color:#bbb">Seção</span>
		</div>
		<table class="tablesorter">
			<thead>
				<tr>
					<th>Nome da Linha</th>
					<th>Código</th>
					<th>Tipo Veículo</th>
<!--						<th>Inoperante</th>-->
					<th>Status</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="bomLinha" items="${bomLinhaList}" varStatus="i">
				<tr>
					<td>
						<input type="hidden" name="linhas[${i.index}].id" value="${bomLinha.id}" />
						<c:if test="${bomLinha.status ne 'FECHADO' && bomLinha.status ne 'INOPERANTE'}"><a href='<c:url value="/bom/linha/${bomLinha.id}" />'></c:if><c:out value="${bomLinha.linhaVigencia.itinerarioIda}" /><c:if test="${bomLinha.status ne 'FECHADO' && bomLinha.status ne 'INOPERANTE'}"></a></c:if>
					</td>
					<td><c:if test="${bomLinha.status ne 'FECHADO' && bomLinha.status ne 'INOPERANTE'}"><a href='<c:url value="/bom/linha/${bomLinha.id}" />'></c:if><c:out value="${bomLinha.linhaVigencia.codigo}" /><c:if test="${bomLinha.status ne 'FECHADO' && bomLinha.status ne 'INOPERANTE'}"></a></c:if></td>
					<td><c:if test="${bomLinha.status ne 'FECHADO' && bomLinha.status ne 'INOPERANTE'}"><a href='<c:url value="/bom/linha/${bomLinha.id}" />'></c:if><c:out value="${bomLinha.tipoDeVeiculo.descricao}" /><c:if test="${bomLinha.status ne 'FECHADO' && bomLinha.status ne 'INOPERANTE'}"></a></c:if></td>
<!--						<td><input type="checkbox" name="linhas[${i.index}].inoperante"<c:if test="${bomLinha.status eq 'INOPERANTE'}"> checked="checked""</c:if> class="linhaInoperante" /></td>-->
					<td><c:if test="${bomLinha.status ne 'FECHADO' && bomLinha.status ne 'INOPERANTE'}"><a href='<c:url value="/bom/linha/${bomLinha.id}" />'></c:if><c:out value="${bomLinha.status}" /><c:if test="${bomLinha.status ne 'FECHADO' && bomLinha.status ne 'INOPERANTE'}"></a></c:if></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<security:hasPermission target="Bom" action="exportar">
			<a id="linkExportBOM" href="<c:url value="/bom/exportar"/>">Exportar</a>
		</security:hasPermission>
		<security:hasPermission target="Bom" action="importar">
			<a id="uploadBOM" href="<c:url value="/bom/formUpload"/>">Importar</a>
		</security:hasPermission>
		<security:hasPermission target="Bom" action="recriar linhas">
			<a id="linkListLinhasRecriarBOM" href="<c:url value="/bom/listLinhasRecriar"/>">Recriar Linhas</a>
		</security:hasPermission>
		<br/><br/><br/><br/>
		<input type="button" id="btnCancelar" value="Voltar" style="display: none;" data-urlRetorno="<c:url value="/bom/list"/>" />
		<security:hasPermission target="Bom" action="fechar">
			<c:if test="${bom.status eq 'PREENCHIDO'}">
				<a href='<c:url value="/bom" />/${bom.id}/fechar-confirm' style="text-decoration: none;" class="link_modal_view"  id="fecharBom"><input type="button" alt="Fechar BOM" value="Fechar"> </a>
			</c:if>
		</security:hasPermission>
		</form>
    </tiles:putAttribute>
</tiles:insertTemplate>