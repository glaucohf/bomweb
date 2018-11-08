<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="pt_BR"/>

<tiles:insertTemplate template="/WEB-INF/jsp/layout/form.jsp">
	<tiles:putAttribute name="formAction" cascade="true">
		<c:url value="/tarifa" />
	</tiles:putAttribute>
	<tiles:putAttribute name="formLegend" cascade="true">
	<script type="text/javascript" src="<c:url value="/jscript/formTarifa.js" />"></script>

		Tarifa
	</tiles:putAttribute>
	<tiles:putAttribute name="formFields" cascade="true">
		<input type="hidden" name="entity.id" value="${t.id}">
		<input type="hidden" name="entity.active" value="${t.active}">
		<input type="hidden" name="justificaivaHidden" value="0">
		<c:if test="${t.id == null}">
			<p>
				<label for="buscaEmp">Empresa:</label>
				<input id="buscaEmp" name="entity.secao.linhaVigencia.empresa.nome" value="${t.secao.linhaVigencia.empresa.nome}" onblur="limpaId(this,'idEmpresa')" style="width:350px;" class="required"/> * 
				<input type="hidden" id="idEmpresa" name="entity.secao.linhaVigencia.empresa.id" value="${t.secao.linhaVigencia.empresa.id}" />
			</p>
			<p>
				<label for="buscaLinha">Linha:</label>
				<input type="hidden" id="idLinha" name="entity.secao.linhaVigencia.id" value="${t.secao.linhaVigencia.id}"/>
				<input type="text" id="buscaLinha" class="required" <c:if test="${empty t.secao.linhaVigencia.empresa.nome}">disabled="disabled"</c:if> <c:if test="${not empty t.secao.linhaVigencia.id}">value="${t.secao.linhaVigencia.itinerarioIda}"</c:if> onblur="limpaId(this,'idLinha')" style="width:250px;"/> * 
				
				<img class="loading" alt="Carregando" src="<c:url value="/images/carregando.gif" />">
			</p>
			<p>
				<label for="secao">Seção:</label>
				<select id="secao" name="entity.secao.id" <c:if test="${empty secoes}">disabled="disabled"</c:if> class="required" >
					<option value="">Selecione</option>
					<c:forEach var="item" items="${secoes}">
						<option value="${item.id}" <c:if test="${item.id == t.secao.id}">selected="selected"</c:if>><c:out value="${item.juncao}"></c:out></option>
					</c:forEach>				
				</select> * 
				<img class="loading" alt="Carregando" src="<c:url value="/images/carregando.gif" />">
			</p>
		</c:if>
		<c:if test="${t.id != null}"> 
			<p>
				<label for="emp">Empresa:</label>
				<input type="text" id="emp" value="${t.secao.linhaVigencia.empresa.nome}" name="entity.secao.empresa.linha.empresa.nome" disabled="disabled" style="width:350px;">
				<input type="hidden" id="idEmpresa" name="entity.secao.linhaVigencia.empresa.id" value="${t.secao.linhaVigencia.empresa.id}" />
			</p>
			<p>
				<label for="lin">Linha:</label>
				<input type="text" id="lin" value="${t.secao.linhaVigencia.itinerarioIda}" name="entity.secao.empresa.linha.itinerarioIda" disabled="disabled" style="width:250px;">
			</p>
			<p>
			    <c:if test="${t.id != null}"><input type="hidden" name="entity.secao.id" value="${t.secao.id}"></c:if>
				<label for="sec">Seção:</label>
				<input type="text" id="sec" value="${t.secao.codigo}" name="entity.secao.codigo" disabled="disabled">
			</p>
		</c:if>
		<p>
			<label for="valorTarifa">Valor:</label>
			<input type="text" id="valorTarifa" value="<fmt:formatNumber type='number' minFractionDigits='2' value='${t.valor}'/>" class="required" name="entity.valor" class="required" size="7" maxlength="7"> * 
			
		</p>
		<p>
			<label for="datepicker">Data:</label>
			<input type="text" id="dataInicioVigencia" value="${t.inicioVigenciaFormated}" name="entity.inicioVigencia" class="required" maxlength="10" >  * 
		</p>
		
		<div id="modalView2" style="display: none;" class="simplemodal-data">

	<div class="white_content_header">
		<img src="/bomweb/images/ico_modal_alert.png" align="absmiddle"> Alerta
	</div>
	<div class="messageJust white_content_content">
	</div>
	<div class="white_content_footer">
		<input id="btnSimEdicaoRetroativa" type="button" value="Sim">
		<input class="simplemodal-close" type="button" value="Não">
	</div>

</div>

		
       	</tiles:putAttribute>
</tiles:insertTemplate>