var listPage;
$(document).ready(function() {


	listPage = new ListPage(pathName + "/bom", "BOM");
	
	listPage.setSearchFieldsIds(["idEmpresa", "mes_referencia"]);
	
	listPage.addColumnDefinition("Empresa", "empresa", {"sWidth": "50%"});
			listPage.addColumnDefinition("Mês/Ano de Referência", "mesAnoReferencia", { fnRender: createRowDateValueRenderFunction( "mesAnoReferencia" ) } );
	
	listPage.setRenderActionColumnCallback(function(row) {
		var html = '';
		
		if (row.aData.podePreencher && row.aData.status != 'FECHADO') {
			html += "<a href='" + pathName + "/bom/" + row.aData.id + "/linhas'>Preencher</a> &nbsp;";
			
		}
		if (row.aData.podeFechar && row.aData.status == 'PREENCHIDO') {
			html += "<a href='" + pathName + "/bom/" + row.aData.id + "/fechar-confirm' onclick='javascript:ListPage.showModalPanel(this, event)' id='fecharBom'>Fechar</a> &nbsp;";
		}
		if (row.aData.podeVisualizar && row.aData.status == 'FECHADO') {
			html += "<a href='" + pathName + "/bom/" + row.aData.id + "/visualizar' target='_blank'>Visualizar</a> &nbsp;";
		}
		if (row.aData.podeReabrir) {
			if (row.aData.status == 'FECHADO' && row.aData.canReopen) {
				html += "<a id=reabrirBom href='" + pathName + "/bom/" + row.aData.id + "/reabrir' name='" + row.aData.reopenStatus + "'>Reabrir</a> &nbsp;";
			}
			if (row.aData.status == 'REABERTO' || (row.aData.status == 'PREENCHIDO' && row.aData.statusPendencia == 'Reaberto')) {
				html += "<a href='" + pathName + "/bom/" + row.aData.id + "/justificativas'>Visualizar Justificativas</a> &nbsp;";
			}
		}
		
		return html;
	});
	
	if($("#perfil").val() == 'Empresa'){
		listPage.initialize();
	}
		
	
});

//Auto complete da Busca
$(function() {

	$( "#buscaEmp" ).autocomplete({
		width:352,
		source: function( request, response ) {
			$.ajax({
				url: pathName + "/empresa/busca.json?term="+$( "#buscaEmp" ).val(),
				dataType: "json",
				data: {
					featureClass: "P",
					style: "full",
					maxRows: 12,
					name_startsWith: request.term
				},
				success: function( data ) {
					response( $.map( data.empresa, function( item ) {
						return {
							value: item.nome,
							data: item.id,
							label: item.nome,
						};
					}));
				}
			});
		},
		minLength: 0,
		select: function(event, ui) { 
			$("#idEmpresa").val(ui.item.data);
		 }
	});
	
	$("#btn_pesquisa").click(function()
		{
		listPage.initialize();
		}
	);
});
	
/////////////////////////////////////////////

$( document ).ready( function()
{
	var LINK_REABRIR_BOM_EMPRESA;
	var getMessageReopen = function( timesReopened )
	{
		switch( timesReopened )
		{			
			case '0':
				return "O BOM só pode ser reaberto 2 vezes, você só poderá reabrir esse BOM mais uma vez.<br />";
				
			case '1':
				return "Essa é a segunda vez que esse BOM é reaberto, ele não poderá ser reaberto novamente.<br />";
		}
	}
	var reabrirBom = function(event)
	{
		if($("#perfil").val() == 'Empresa')
		{
			var $linkReabrirBom = $( "#reabrirBom" );
			var timesReopened = $linkReabrirBom.context.activeElement.name;
			LINK_REABRIR_BOM_EMPRESA = $linkReabrirBom.context.activeElement.href;
			var $messageReabrirBom = $(".messageReabrir");
			$messageReabrirBom.html("");
			$messageReabrirBom.append("<p>");
			$messageReabrirBom.append( getMessageReopen( timesReopened ) );
			$messageReabrirBom.append("Deseja reabrir esse BOM?");
			$messageReabrirBom.append("</p>");
			console.log( $messageReabrirBom.html() );
			bomweb.openModalDefaultPanel({modalId: "#modalView", data: "" });
			return false;				
		}		
		return true;		
	}
	
	var submitBom = function(event)
		{	
			location.replace(LINK_REABRIR_BOM_EMPRESA);
		};

    var clickReabrirBom = $( "#reabrirBom" ).live( "click", reabrirBom );
    var submitReabrirBom = $( "#btnSimReabrirBom" ).live( "click", submitBom );
	
});

