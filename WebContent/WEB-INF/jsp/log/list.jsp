<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/template.jsp">
	<tiles:putAttribute name="conteudo">
       	<strong class="titulo azul">Log </strong><img src="<c:url value="/images/bomweb_setas.gif" />" /><strong class="titulo verde"> Sistema</strong>
		<c:if test="${not disableFilter}">
		<a href="#" class="accordion">[ Ocultar Filtro ]</a>
        <div id="filtro">
			<img src="<c:url value="/images/bomweb_filtros_box_up.jpg" />" />        
			<form id="form" action="<c:url value="/log/list" />" method="post">
				<fieldset>
					<p>
						<label for="entidade">Entidade:</label>
						<select id="entidade" name="filtro.entidade" class="required">
							<option value="" Selected="true">Selecione uma entidade</option>
							<c:forEach var="item" items="${entidades}">
								<option value="${item.valueEntidade}"><c:out value="${item.labelEntidade}"></c:out></option>
							</c:forEach>
						</select> * 
					</p>
					<p>
						<label for="operacao">Operação:</label>
						<select id="operacao" name="filtro.revisionType">
							<option value="" Selected="true">Selecione uma operação</option>
								<option value="0"><c:out value="Inclusão"></c:out></option>
								<option value="1"><c:out value="Alteração"></c:out></option>
								<option value="2"><c:out value="Deleção"></c:out></option>
						</select> 
					</p>
					<p>
						<label for="usuario">Usuário:</label>
						<select id="usuario" name="filtro.idUsuario">
							<option value="" selected="selected">Selecione um usuário</option>
							<c:forEach var="item" items="${usuarios}">
								<option value="${item.usuario.id}"><c:out value="${item.usuario.nome}"></c:out></option>
							</c:forEach>
						</select>
					</p>
					<p>
						<label for="datepicker">Data Inicial:</label>
						<input type="text" id="datainicio" value="${filtro.dataInicial}" name="filtro.dataInicial" class="required" maxlength="10" > * 
					</p>
					<p>
					    <label for="datepicker">Data Final:</label>
						<input type="text" id="datafim" value="${filtro.dataFinal}" name="filtro.dataFinal" class="required" maxlength="10" > * 
					</p>
					<div id="camposObrigatorios"><c:out value="* Campo obrigatório."/></div>
		    		<input type="submit" name="btn_pesquisa" value="Pesquisar" >
					<input class="reset" type="reset" value="Limpar"/>
		    	</fieldset>
			</form>
			<img src="<c:url value="/images/bomweb_filtros_box_dn.jpg" />" />
		</div>
		<br />
		</c:if>
		<div id="tab_relatorio" style="width: 100%; overflow: auto;">
        <table id="tb_list" class="tablesorter">
			<thead>
				<tr>
					<th>Entidade</th>
					<th>Data</th>
					<th>Operação</th>
					<th>Usuário</th>
					<th>Perfil</th>
					<th>IP</th>
					<th>De</th>
					<th>Para</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${logDTOList}" var="emp">
					<tr>
						<td>${emp.entidade}</td>
						<td><f:formatDate pattern="dd/MMM/yyyy HH:mm:ss" value="${emp.data}"/></td>
						<td>${emp.operacao}</td>
						<td>${emp.usuario}</td>
						<td>${emp.perfil}</td>
						<td>${emp.enderecoIP}</td>
						<td>${emp.dadosAntigos}</td>
						<td>${emp.dadosNovos}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</div> 
    </tiles:putAttribute>
</tiles:insertTemplate>