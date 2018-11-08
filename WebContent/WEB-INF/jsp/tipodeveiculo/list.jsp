<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://taglib.decatron.com.br/security" %>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/list.jsp">
	<tiles:putAttribute name="formLegend" cascade="true">
	Tipo de Veículo
	</tiles:putAttribute>
	<tiles:putAttribute name="formAction" cascade="true">
		<c:url value="/tipodeveiculo" />
	</tiles:putAttribute>	
	<tiles:putAttribute name="searchFormFields" cascade="true">
		<p>
			<label for="codigo">Código:</label>
			<input id="codigo" name="entityForSearch.codigo" value="${entityForSearch.codigo}" size="3" maxlength="3"/>
		</p>
		<p>
			<label for="descricao">Tipo:</label>
			<input id="descricao" name="entityForSearch.descricao" value="${entityForSearch.descricao}" />
		</p>
	</tiles:putAttribute>
	<tiles:putAttribute name="beforeTable" cascade="true">
		<security:hasPermission target="TipoVeiculo" action="incluir">
			<a id="insert" href="<c:url value="/tipodeveiculo/insert" />" class="buttom_azul">Novo Tipo de Veículo</a>
		</security:hasPermission>
	</tiles:putAttribute>
	<tiles:putAttribute name="tableHeaderColumns" cascade="true">
		<th>Código do Tipo de Veículo</th>
		<th>Nome do Tipo de Veículo</th>
		<th>Ações</th>
	</tiles:putAttribute>
	<tiles:putAttribute name="tableBodyColumns" cascade="true">
		<c:forEach items="${tList}" var="tip">
			<tr>
				<td>${tip.codigo}</td>
				<td>${tip.descricao}</td>
				<td nowrap="nowrap">
					<security:hasPermission target="TipoVeiculo" action="excluir">
						<a href="delete-confirm/${tip.id}" class="link_modal_view" title="Excluir"><img src="<c:url value="/images/excluir.png" />" /></a> &nbsp;
					</security:hasPermission>
				</td>
			</tr>
		</c:forEach>
	</tiles:putAttribute>
</tiles:insertTemplate>