<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:setLocale value="pt_BR"/>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/template.jsp">
	<tiles:putAttribute name="conteudo">
	<script type="text/javascript">
	//Desabilita campos da tarifa anterior no preenchimento da secao
	$(document).ready(function() {
		enableDisablePreenchimento();
		enableDisableTarifaAnterior();
	});
	function enableDisableTarifaAnterior()
	{
		if($("#tarifaAnterior").val() == $("#tarifaAtual").val())
		{
			$("#passageiroAnteriorAB").val(0);
			$("#passageiroAnteriorAB").attr('readonly', 'readonly');
			$("#passageiroAnteriorBA").val(0);
			$("#passageiroAnteriorBA").attr('readonly', 'readonly');

			$("#passageiroAnteriorAB").attr('tabindex', '-1');	
			$("#passageiroAnteriorBA").attr('tabindex', '-1');			
		}
		else {
			$("#passageiroAnteriorAB").removeAttr('readonly');
			$("#passageiroAnteriorBA").removeAttr('readonly');

			$("#passageiroAnteriorAB").attr('tabindex', '3');	
			$("#passageiroAnteriorBA").attr('tabindex', '4');			
		}
	}
	function enableDisablePreenchimento()
	{
		
		if($("#semPassageiros:checked").size() > 0)
		{
			$("#totalPassageiro").text('0');
			$("#totalGeralValor").text('0');
			$("#passageiroAB").attr('readonly', 'readonly');
			$("#passageiroAB").val(0);
			$("#passageiroBA").attr('readonly', 'readonly');
			$("#passageiroBA").val(0);

			$("#passageiroAnteriorAB").attr('readonly', 'readonly');
			$("#passageiroAnteriorAB").val(0);
			$("#passageiroAnteriorBA").attr('readonly', 'readonly');
			$("#passageiroAnteriorBA").val(0);

			$("#tarifaAtual").attr('readonly', 'readonly');
			$("#tarifaAnterior").attr('readonly', 'readonly');
			$("#tarifaAtual").val($("#tarifaAnterior").val());
		
			$("#passageiroAB").unmaskMoney();
			$("#passageiroBA").unmaskMoney();
			$("#passageiroAnteriorAB").unmaskMoney();
			$("#passageiroAnteriorBA").unmaskMoney();
		}
		else {
			$("#tarifaAtual").removeAttr('readonly');
			$("#tarifaAnterior").removeAttr('readonly');
			
			$("#passageiroAB").removeAttr('readonly');
			$("#passageiroBA").removeAttr('readonly');
			
			if($("#tarifaAnterior").val() != $("#tarifaAtual").val()){
				$("#passageiroAnteriorAB").removeAttr('readonly');
				$("#passageiroAnteriorBA").removeAttr('readonly');
			}

			$("#passageiroAB").maskMoney({allowZero:"true", thousands:".", decimal:"", precision: 0 });
			$("#passageiroAnteriorAB").maskMoney({allowZero:"true", thousands:".", decimal:"", precision: 0 });
			$("#passageiroBA").maskMoney({allowZero:"true", thousands:".", decimal:"", precision: 0 });
			$("#passageiroAnteriorBA").maskMoney({allowZero:"true", thousands:".", decimal:"", precision: 0 });
		}
	}

	function enableSetValueDefaultTarifaAnterior(){
		if($("#tarifaAnterior").val() == $("#tarifaAtual").val()){

			$("#passageiroAnteriorAB").attr('readonly', 'readonly');
			$("#passageiroAnteriorAB").val(0);
	
			$("#passageiroAnteriorBA").attr('readonly', 'readonly');
			$("#passageiroAnteriorBA").val(0);

			$("#passageiroAnteriorAB").attr('tabindex', '-1');	
			$("#passageiroAnteriorBA").attr('tabindex', '-1');
		}
		else{
			if($("#passageiroAnteriorAB").attr('readonly') == true){
					
				$("#passageiroAnteriorAB").removeAttr('readonly');
				$("#passageiroAnteriorBA").removeAttr('readonly');
				$("#passageiroAnteriorAB").val(0);
				$("#passageiroAnteriorBA").val(0);

				$("#passageiroAnteriorAB").attr('tabindex', '3');	
				$("#passageiroAnteriorBA").attr('tabindex', '4');
			}
		}
	}
	
	</script>
	<fmt:setLocale value="pt_BR"/>
	<span><strong class="titulo azul">${bomSecao.bomLinha.linhaVigencia.itinerarioIda} </strong><img src="<c:url value="/images/bomweb_setas.gif" />" /><strong class="titulo verde"> ${bomSecao.secao.codigo} - ${bomSecao.secao.juncao}</strong></span>
		<form id="formBom" action="<c:url value="/bom/secao/save" />" method="post">
			<input type="hidden" id="linha" name="entity.id" value="${bomSecao.id}" />
			<input type="hidden" id="linha" name="entity.bomLinha.id" value="${bomSecao.bomLinha.id}" />
			<div class="separador"></div>
	
			<div id="passos" align="center">
				<span style="color:#bbb">Linhas do BOM</span> <b>></b> <span style="color:#bbb">Linha</span> <b>></b> <span style="color:#bbb">Seções da Linha</span> <b>></b> <span>Seção</span>
			</div>
	
			<c:if test="${bomSecao.secao.codigo != '00'}">
				<label>Sem Passageiros:&nbsp;</label><input type="checkbox" id="semPassageiros" name="entity.inoperante"<c:if test="${bomSecao.status eq 'INOPERANTE'}"> checked="checked""</c:if> onchange="javascript:enableDisablePreenchimento(this)"/>
			</c:if>
			<table class="bg_tabela tabelaBOM">
			  	<tr>
			    	<td width="33%" class="padding" align="center">
			    		<br/>
			    		<strong>(A-B) IDA</strong>
			    		<br/>
						${bomSecao.secao.pontoInicial} - ${bomSecao.secao.pontoFinal}
					</td>
					<td width="33%" align="center">
						<label for="passageiroAB">Quantidade de Passageiros*</label>
						<br/> 
						<input type="text" id="passageiroAB" name="entity.passageiroAB" value="<fmt:formatNumber type='number' pattern='#,###'>${bomSecao.passageiroAB}</fmt:formatNumber>" class="required" tabindex="1" size="5"/>
						<br/>
					</td>
					<td align="center">
						<label for="passageiroAnteriorAB">Quantidade de Passageiros - Anterior *</label>
						<br/>
						<input type="text" id="passageiroAnteriorAB" name="entity.passageiroAnteriorAB" value="<fmt:formatNumber type='number' pattern='#,###'>${bomSecao.passageiroAnteriorAB}</fmt:formatNumber>"  class="required" size="5"/>
						<br/>
					</td>
				</tr>
			</table>
			<table class="bg_tabela2 tabelaBOM">
			  	<tr>
			    	<td width="33%" class="padding" align="center">
			    		<br/>
			    		<strong>(B-A) VOLTA</strong>
			    		<br/>
						${bomSecao.secao.pontoFinal} - ${bomSecao.secao.pontoInicial}
					</td>
					<td width="33%" align="center">
						<label for="passageiroBA">Quantidade de Passageiros*</label>
						<br/> 
						<input type="text" id="passageiroBA" name="entity.passageiroBA" value="<fmt:formatNumber type='number' pattern='#,###'>${bomSecao.passageiroBA}</fmt:formatNumber>" class="required" tabindex="2" size="5"/>
						<br/>
					</td>
					<td align="center">
						<label for="passageiroAnteriorBA">Quantidade de Passageiros - Anterior *</label> 
						<br/>
						<input type="text" id="passageiroAnteriorBA" name="entity.passageiroAnteriorBA" value="<fmt:formatNumber type='number' pattern='#,###'>${bomSecao.passageiroAnteriorBA}</fmt:formatNumber>" class="required" size="5"/>
						<br/>
					</td>
				</tr>
			</table>
			<div id="totalizadorBomSecao">
				<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
					<tr>
						<td width="51%" valign="top" colspan="2">
			     			<strong>Tarifas</strong>
						</td>
			     		<td width="49%" valign="top">
		               		<strong>Totais</strong>
						</td>
					</tr>
					<tr>
			     		<td width="10%">			     		
							Atual:
<!-- 							<br/>
							
							<br/> -->
						</td>
						<td>
							<input type="text" id="tarifaAtual" name="entity.tarifa" class="required" value="<fmt:formatNumber type='number' minFractionDigits='2'>${bomSecao.tarifa}</fmt:formatNumber>" size="5" onblur="javascript:enableSetValueDefaultTarifaAnterior()"/>
						</td>
			     		<td>
	               			<table width="500">
								<tbody>
									<tr>
										<td><strong>Receita:</strong></td>
										<td><span id="totalGeralValor">0,00</span></td>
										<td><strong>Receita - Anterior:</strong></td>
										<td><span id="totalGeralValorAnterior">0,00</span></td>
										<td><strong>Passageiros:</strong></td>
										<td><span id="totalPassageiro">0</span></td>
									</tr>
								</tbody>
							</table>
						</td>
		       		</tr>
		       		<tr>
		       			<td>
		       				Anterior:
		       			</td>
			       		<td>
 							<input type="text" id="tarifaAnterior" name="entity.tarifaAnterior" class="required" value="<fmt:formatNumber type='number' minFractionDigits='2'>${bomSecao.tarifaAnterior}</fmt:formatNumber>" size="5" onblur="javascript:enableSetValueDefaultTarifaAnterior()"/></td>
			       		<td>
			       		</td>
		       		</tr>
	      		</table>
			</div>
			<p>
				<input type="submit" value="Confirmar" />
				<input type="button" id="btnCancelar" value="Cancelar" style="display: none;" data-urlRetorno="<c:url value="/bom/linha/secoes/${bomSecao.bomLinha.id}"/>">
			</p>
		</form>
		<div id="camposObrigatorios"><c:out value="* Campo obrigatório."/></div>
    </tiles:putAttribute>
</tiles:insertTemplate>