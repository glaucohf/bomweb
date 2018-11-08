<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="pt_BR"/>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/template.jsp">
	<tiles:putAttribute name="conteudo">
		<fmt:setLocale value="pt_BR"/>
		<strong class="titulo azul">BOM </strong><img src="<c:url value="/images/bomweb_setas.gif" />" /><strong class="titulo verde"> ${bomLinha.linhaVigencia.itinerarioIda}</strong>
		
		<input type="hidden" id="linha" name="entity.id" value="${bomLinha.id}" />
		<input type="hidden" id="bom" name="entity.bom.id" value="${bomLinha.bom.id}" />
		<div class="separador"></div>
		<br>
		<div id="passos" align="center">
			<span style="color:#bbb">Linhas do BOM</span> <b>></b> <span style="color:#bbb">Linha</span> <b>></b> <span>Seções da Linha</span> <b>></b> <span style="color:#bbb">Seção</span>
		</div>
		
		<table cellpadding="0" cellspacing="0" width="100%">
			<tbody>
				<tr>
					<td align="left" width="50%">
						<strong>Mês/Ano de Referência</strong>
						<span>${bomLinha.bom.mesReferencia}</span>
					</td>
					<td align="right"></td> 
				</tr>
			
				<tr>
			    	<td align="left" width="50%">
						<strong>Tipo de Veículo</strong> 
						<span>${bomLinha.tipoDeVeiculo.descricao}</span>
			    	</td>
			    	<td align="right">
						<strong>Tipo da Ligação</strong> 
						<span>${bomLinha.linhaVigencia.tipoLigacao}</span>
					</td>
				</tr>
				<tr>
			    	<td align="left">
						<strong>Código da Linha</strong> 
						<span>${bomLinha.linhaVigencia.codigo}</span>
			    	</td>
			    	<td align="right">
						<strong>Via</strong> 
						<span>${bomLinha.linhaVigencia.via}</span>
					</td>					
				</tr>
				<tr>
			    	<td align="left">
						<strong>Número da Linha</strong> 
						<span>${bomLinha.linhaVigencia.numeroLinha}</span>
			    	</td>
				</tr>
			</tbody>
		</table>
		<br>
		<table class="tablesorter">
			<thead>
				<tr>
					<th>Código da Seção</th>
					<th>Seção</th>
					<th>Passageiros (A-B)</th>
					<th>Passageiros (B-A)</th>
					<th>Total Receita</th>
				</tr>
			</thead>
			<tbody>
				<c:set var="receitaTotal" value="0.0" />
				<c:forEach var="bomSecao" items="${bomLinha.secoes}" varStatus="i">
				<c:set var="receitaTotal" value="${receitaTotal + bomSecao.totalReceita}" />
				<tr>
					<td>
						<input type="hidden" name="entity.secoes[${i.index}].id" value="${bomSecao.id}" />
						<a tabindex="-1" href='<c:url value="/bom/secao/${bomSecao.id}" />'>
						${bomSecao.secao.codigo}
						</a>
					</td>
					<td>
						<a tabindex="-1" href='<c:url value="/bom/secao/${bomSecao.id}" />'>
						${bomSecao.secao.pontoInicial} - ${bomSecao.secao.pontoFinal}
						</a>
					</td>
					<td>
						<a tabindex="-1" href='<c:url value="/bom/secao/${bomSecao.id}" />'>
						<fmt:formatNumber type='number' pattern='#,###'>${bomSecao.totalPassageirosAB}</fmt:formatNumber>
						</a>
					</td>
					<td>
						<a tabindex="-1" href='<c:url value="/bom/secao/${bomSecao.id}" />'>
						<fmt:formatNumber type='number' pattern='#,###'>${bomSecao.totalPassageirosBA}</fmt:formatNumber>
						</a>
					</td>
					<td>
						<a tabindex="-1" href='<c:url value="/bom/secao/${bomSecao.id}" />'>
						<fmt:formatNumber type="number" minFractionDigits="2">${bomSecao.totalReceita}</fmt:formatNumber>
						</a>
					</td>
				</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="4">-</td>
					<td><fmt:formatNumber type="number" minFractionDigits="2">${receitaTotal}</fmt:formatNumber></td>
				</tr>
			</tfoot>
		</table>
		
		<div style="margin-top: 20px; margin-bottom: 20px">
			<input type="button" id="btnFinalizar" value="Finalizar Preenchimento" tabindex="2" data-urlRetorno="<c:url value="/bom/${bomLinha.bom.id}/linhas"/>">
			<input type="button" id="btnCancelar" value="Voltar" tabindex="2" data-urlRetorno="<c:url value="/bom/linha/${bomLinha.id}"/>">
		</div>
    </tiles:putAttribute>
</tiles:insertTemplate>