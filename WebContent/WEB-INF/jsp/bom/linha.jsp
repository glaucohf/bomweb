<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="pt_BR"/>
<tiles:insertTemplate template="/WEB-INF/jsp/layout/template.jsp">

	<tiles:putAttribute name="conteudo">
		<script>
			function validarCancelar(){
				$('#btnCancelar').hide();
				$('#confirmCancelar').show();
			}

			function submitPreencherSecoes(){
				$('#actionPreencherSecoes').val('true');
			}
		</script>
		
		<strong class="titulo azul">BOM </strong><img src="<c:url value="/images/bomweb_setas.gif" />" /><strong class="titulo verde"> ${bomLinha.linhaVigencia.itinerarioIda}</strong>
		
		<form id="formBom" action="<c:url value="/bom/linha/save" />" method="post">
			<input type="hidden" id="linha" name="entity.id" value="${bomLinha.id}" />
			<input type="hidden" id="bom" name="entity.bom.id" value="${bomLinha.bom.id}" />
			<input type="hidden" id="actionPreencherSecoes" name="actionPreencherSecoes" value="false" />
			<div class="separador"></div>
			<div id="passos" align="center">
				<span style="color:#bbb">Linhas do BOM</span> <b>></b> <span>Linha</span> <b>></b> <span style="color:#bbb">Seções da Linha</span> <b>></b> <span style="color:#bbb">Seção</span>
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
			<table cellpadding="0" cellspacing="0" class="bg_tabelaBOM tabelaBOM">
				<tbody>
					<tr>
				    	<td align="center">
							<label for="frota">Frota</label> 
							<input type="text" id="frota" name="entity.frota" value="${bomLinha.frota}" tabindex="1" class="digits" style="margin-right: 15px;" size="4" onchange="javascript:validarCancelar()"/>
							<label for="capacidade">Número de assentos médios por carro</label> 
							<input type="text" id="capacidade" name="entity.capacidade" value="${bomLinha.capacidade}" tabindex="2" class="digits" style="margin-right: 15px;" size="4"/>
							<label for="responsavel">Responsável pela Empresa no Período</label> 
							<input type="text" id="responsavel" value="${bomLinha.bom.responsavel}" readonly="readonly" size="30" onchange="javascript:validarCancelar()"/>
						</td>
					</tr>
				</tbody>
			</table>
			<table cellpadding="0" cellspacing="0" class="bg_tabelaBOM tabelaBOM">
				<tbody>
					<tr>
						<td width="180">
							<strong>(A-B) IDA</strong>
							<br/>
							<strong>${bomLinha.linhaVigencia.itinerarioIda}</strong><br/>
							<br/>
							Piso I (KM)<br/>
							<input type="text" id="piso1AB" name="entity.linhaVigencia.piso1AB" value="<fmt:formatNumber type='number' minFractionDigits='2'>${bomLinha.linhaVigencia.piso1AB}</fmt:formatNumber>" readonly="readonly" size="12"/><br/>	
							Piso II (KM)<br/>
							<input type="text" id="piso2AB" name="entity.linhaVigencia.piso2AB" value="<fmt:formatNumber type='number' minFractionDigits='2'>${bomLinha.linhaVigencia.piso2AB}</fmt:formatNumber>" readonly="readonly" size="12"/><br/>	
							<input type="hidden" id="extensaoAB" name="entity.linhaVigencia.extensaoAB" value="<fmt:formatNumber type='number' minFractionDigits='2'>${bomLinha.linhaVigencia.extensaoAB}</fmt:formatNumber>" readonly="readonly" size="12"/>	
							KM Percorrida Mensal: <span id="kmPercorridaMensalAB" class="informacao">0,00</span>
						</td>
						<td width="160" align="center">
							<br/>
							<label for="viagensOrdinariasAB">Viagens ordinárias</label><br/> 
							<input type="text" id="viagensOrdinariasAB" name="entity.viagensOrdinariasAB" value="${bomLinha.viagensOrdinariasAB}" tabindex="3" class="digits" size="5" onchange="javascript:validarCancelar()"/>
							<br/><br/>
							<label for="viagensExtraordinariasAB">Viagens extraordinárias</label><br/>
							<input type="text" id="viagensExtraordinariasAB" name="entity.viagensExtraordinariasAB" value="${bomLinha.viagensExtraordinariasAB}" tabindex="4" class="digits" size="5" onchange="javascript:validarCancelar()"/>
						</td>
						<td align="right">
							<br/>
							<label for="duracaoViagemPicoAB">Tempo de viagem em horário de Pico:
								<input type="text" id="duracaoViagemPicoAB" name="entity.linhaVigencia.duracaoViagemPicoAB" class="digits" value="${bomLinha.linhaVigencia.duracaoViagemPicoAB}" <c:if test="${not empty bomLinha.linhaVigencia.duracaoViagemPicoAB}">readonly="readonly"</c:if> size="4"/>
							</label>
							<br/>
							<label for="picoInicioManhaAB">Intervalo de Pico manhã em horas: 
								<input type="text" id="picoInicioManhaAB" name="entity.linhaVigencia.picoInicioManhaAB" class="inputhora" value="${bomLinha.linhaVigencia.picoInicioManhaAB}" <c:if test="${not empty bomLinha.linhaVigencia.picoInicioManhaAB}">readonly="readonly"</c:if> />
								às
								<input type="text" id="picoFimManhaAB" name="entity.linhaVigencia.picoFimManhaAB" class="inputhora" value="${bomLinha.linhaVigencia.picoFimManhaAB}" <c:if test="${not empty bomLinha.linhaVigencia.picoFimManhaAB}">readonly="readonly"</c:if> />
							</label>
							<br/>
							<br/>
							<label for="duracaoViagemForaPicoAB">Tempo de viagem Fora do horário de Pico: 
								<input type="text" id="duracaoViagemForaPicoAB" name="entity.linhaVigencia.duracaoViagemForaPicoAB" class="digits"  value="${bomLinha.linhaVigencia.duracaoViagemForaPicoAB}" <c:if test="${not empty bomLinha.linhaVigencia.duracaoViagemForaPicoAB}">readonly="readonly"</c:if> size="4"/>
							</label>
							<br/>
							<label for="picoInicioTardeAB">Intervalo de Pico tarde em horas: 
								<input type="text" id="picoInicioTardeAB" name="entity.linhaVigencia.picoInicioTardeAB" class="inputhora" value="${bomLinha.linhaVigencia.picoInicioTardeAB}" <c:if test="${not empty bomLinha.linhaVigencia.picoInicioTardeAB}">readonly="readonly"</c:if> />
								às
								<input type="text" id="picoFimTardeAB" name="entity.linhaVigencia.picoFimTardeAB" class="inputhora" value="${bomLinha.linhaVigencia.picoFimTardeAB}" <c:if test="${not empty bomLinha.linhaVigencia.picoFimTardeAB}">readonly="readonly"</c:if> />
								<br/>
							</label>
						</td>
					</tr>
				</tbody>
			</table>

			<table cellpadding="0" cellspacing="0" class="bg_tabela2 tabelaBOM">
				<tbody>
					<tr>
						<td width="180">
							<strong>(B-A) VOLTA</strong>
							<br/>
							<strong>${bomLinha.linhaVigencia.itinerarioVolta}</strong>
							<br/>
							<br/>
							Piso I (KM)					
							<br/>
							<input type="text" id="piso1BA" name="entity.linhaVigencia.piso1BA" value="<fmt:formatNumber type='number' minFractionDigits='2'>${bomLinha.linhaVigencia.piso1BA}</fmt:formatNumber>" readonly="readonly" size="12"/><br/>
							Piso II (KM)
							<br/>	
							<input type="text" id="piso2BA" name="entity.linhaVigencia.piso2BA" value="<fmt:formatNumber type='number' minFractionDigits='2'>${bomLinha.linhaVigencia.piso2BA}</fmt:formatNumber>" readonly="readonly" size="12"/><br/>	
							<input type="hidden" id="extensaoBA" name="entity.linhaVigencia.extensaoBA" value="<fmt:formatNumber type='number' minFractionDigits='2'>${bomLinha.linhaVigencia.extensaoBA}</fmt:formatNumber>" readonly="readonly" size="12"/>	
							KM percorrida Mensal: <span id="kmPercorridaMensalBA" class="informacao">0,00</span>
						</td>
						<td width="160" align="center">
							<br/>
							<label for="viagensOrdinariasBA">Viagens ordinárias</label><br/> 
							<input type="text" id="viagensOrdinariasBA" name="entity.viagensOrdinariasBA" value="${bomLinha.viagensOrdinariasBA}" tabindex="5" class="digits" size="5" onchange="javascript:validarCancelar()"/>
							<br/><br/>
							<label for="viagensExtraordinariasBA">Viagens extraordinárias</label><br/> 
							<input type="text" id="viagensExtraordinariasBA" name="entity.viagensExtraordinariasBA" value="${bomLinha.viagensExtraordinariasBA}" tabindex="6" class="digits" size="5" onchange="javascript:validarCancelar()"/>
						</td>
						<td align="right">
							<br/>
							<label for="duracaoViagemPicoBA">Tempo de viagem em horário de Pico: 
								<input type="text" id="duracaoViagemPicoBA" name="entity.linhaVigencia.duracaoViagemPicoBA" class="digits" value="${bomLinha.linhaVigencia.duracaoViagemPicoBA}" <c:if test="${not empty bomLinha.linhaVigencia.duracaoViagemPicoBA}">readonly="readonly"</c:if> size="4"/>
							</label>
							<br/>
							<label for="picoInicioManhaBA">Intervalo de Pico manhã em horas: 
								<input type="text" id="picoInicioManhaBA" name="entity.linhaVigencia.picoInicioManhaBA" class="inputhora" value="${bomLinha.linhaVigencia.picoInicioManhaBA}" <c:if test="${not empty bomLinha.linhaVigencia.picoInicioManhaBA}">readonly="readonly"</c:if> />
								às
								<input type="text" id="picoFimManhaBA" name="entity.linhaVigencia.picoFimManhaBA" class="inputhora" value="${bomLinha.linhaVigencia.picoFimManhaBA}" <c:if test="${not empty bomLinha.linhaVigencia.picoFimManhaBA}">readonly="readonly"</c:if> />
							</label>
							<br/>
							<br/>
							<label for="duracaoViagemForaPicoBA">Tempo de viagem Fora do horário de Pico: 
								<input type="text" id="duracaoViagemForaPicoBA" name="entity.linhaVigencia.duracaoViagemForaPicoBA" class="digits"  value="${bomLinha.linhaVigencia.duracaoViagemForaPicoBA}" <c:if test="${not empty bomLinha.linhaVigencia.duracaoViagemForaPicoBA}">readonly="readonly"</c:if> size="4"/>
							</label>
							<br/>
							<label for="picoInicioTardeBA">Intervalo de Pico tarde em horas: 
								<input type="text" id="picoInicioTardeBA" name="entity.linhaVigencia.picoInicioTardeBA" class="inputhora" value="${bomLinha.linhaVigencia.picoInicioTardeBA}" <c:if test="${not empty bomLinha.linhaVigencia.picoInicioTardeBA}">readonly="readonly"</c:if> />
								às
								<input type="text" id="picoFimTardeBA" name="entity.linhaVigencia.picoFimTardeBA" class="inputhora" value="${bomLinha.linhaVigencia.picoFimTardeBA}" <c:if test="${not empty bomLinha.linhaVigencia.picoFimTardeBA}">readonly="readonly"</c:if> />
							</label>
							<br/>
						</td>
					</tr>
				</tbody>
			</table>
			<br/><br/><br/><br/>
			<center><strong class="titulo">Total</strong></center>
			<table width="500" border="0" cellspacing="0" cellpadding="0" align="center">
				
				<tr>
					<!-- //FBW-74 - Comentado até termos uma solução de layout que não induza ao erro nas contas 
					<td width="176" align="center">
						<span id="totalExtensaoKM" class="informacao">0.00</span>
					</td>
					<td width="275" align="center">
						<span id="totalViagensOrdinarias" class="informacao">0</span>
					</td>
					<td width="176" align="center">
						<span id="totalViagensExtraordinarias" class="informacao">0</span>
					</td>
					//FBW-74  fim comentário -->
					<td width="176" align="center">
						<span id="totalKmPercorridaMensal" class="informacao">0.00</span>
					</td>
				</tr>
				<tr>
					<!-- //FBW-74 - Comentado até termos uma solução de layout que não induza ao erro nas contas
					<td align="center"><img src="<c:url value="/images/ico_extensao.gif" />" width="176" height="32"><br /> Extens&atilde;o em KM</td>
					<td align="center"><img src="<c:url value="/images/ico_ordinarias.gif" />" width="176" height="32"><br /> Viagens ordin&aacute;rias</td>
					<td align="center"><img src="<c:url value="/images/ico_extraordinarias.gif" />" width="176" height="32"><br/> Viagens extraordin&aacute;rias</td>
					//FBW-74  fim comentário -->
					<td align="center"><img src="<c:url value="/images/ico_km_percorrida.gif" />" width="176" height="32"><br /> Km percorrida Mensal</td>
				</tr>
			</table>
			
			<div style="margin-top: 20px; margin-bottom: 20px">
				<input type="submit" value="Finalizar preenchimento" tabindex="7" />
				<input type="submit" value="Salvar e preencher seções" tabindex="8" onclick="javascript:submitPreencherSecoes()"/>
				<input type="button" id="btnCancelar" value="Cancelar" tabindex="9" style="display: none;" data-urlRetorno="<c:url value="/bom/${bomLinha.bom.id}/linhas"/>">
				<a class="link_modal_view" id="confirmCancelar" style="text-decoration:none; display: none;" tabindex="9" href="<c:url value="/bom/linha/${bomLinha.id}/confirm-cancelar"/>"><input type="button" value="Cancelar"/></a>
			</div>
			
		</form>
    </tiles:putAttribute>
</tiles:insertTemplate>