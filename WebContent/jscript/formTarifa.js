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
										label: item.nome,
										data: item.id,
										value: item.nome									
									};
								}));
							}
						});
					},
					minLength: 0,
					select: function(event, ui) { 
						$("#buscaLinha").removeAttr("disabled");	
						$("#idEmpresa").val(ui.item.data);
					 },
					open: function() {
						//$( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
					},
					close: function() {
						//$( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
					}
				});
			});
	
		$(function() {

			$( "#buscaLinha" ).autocomplete({
				width:252,
				source: function( request, response ) {
					$.ajax({
						url: pathName + "/tarifa/buscaLinhasSemTarifa.json?term="+$( "#buscaLinha" ).val() + "&idEmpresa="+$("#idEmpresa").val(),
						dataType: "json",
						data: {
							featureClass: "P",
							style: "full",
							maxRows: 12,
							name_startsWith: request.term
						},
						success: function( data ) {
							
							response( $.map( data.linhas, function( item ) {
								return {
									label: item.pontoInicial + ' - ' + item.pontoFinal,
									data: item.id,
									value: item.pontoInicial + ' - ' + item.pontoFinal									
								};
							}));
						}
					});
				},
				minLength: 0,
				select: function(event, ui) { 
					$("#idLinha").val(ui.item.data);
				 },
				open: function() {
					//$( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
				},
				close: function() {
					bomweb.updateChildSelect($('#idLinha'), $("#secao"), pathName + "/tarifa/buscaSecoesSemTarifa.json" , "id", ["codigo", "juncao"]);
				}
			});
		});
/////////////////////////////////////////////

$( document ).ready( function()
{
 var submitJustificativa = function(e) 
   {			
        if( $("#justificativa").val() == "" )
        {
        	fechaModalPanel();
        	window.setTimeout( function(){bomweb.openModalDefaultPanel({ data:
        		"<div class='white_content_header'><img src='../../images/ico_modal_alert.png' align='absmiddle'> Alerta</div>"
				+ "<div class='white_content_content'><ul><li class='liModal'>Por favor, informe uma justificativa.</li></ul><br/></div>"
				+ "<div class='white_content_footer'><input type='button' value='Fechar' onclick='javascript:fechaModalPanel()'class='simplemodal-close'></div>"});}, 250 );
        } 
        else
        {        	
	        	var $justificativa =  $("[id=justificativa]");
	        	var $justificaivaHidden =  $("[name=justificaivaHidden]");
	        	var justificativaValue = $justificativa.val();
	        	$justificaivaHidden.val(justificativaValue);    
	        	fechaModalPanel();
	        	$( '#form' ).submit();
        }
    };

	var funcClick = function( event )
	{
		event.preventDefault();

		var id = $( "[name='entity.id']" ).val();
		var isInsert = id == "";

		var param1Key = "dataVigencia";
		var param1Value = $( "[id='dataInicioVigencia']" ).val();

		var param2Key = "empresaId";
		var param2Value = $( "[id='idEmpresa']" ).val();

		// FBW-503 FBW-505	
		if ( isInsert || ( $.trim( param1Value ) == "" ) || ( $.trim( param2Value ) == "" ) )
		{
			$( '#form' ).submit();
			return;
		}//if

		var urlBase = pathName + "/tarifa/valida.json";
		var urlParameters = {};
		urlParameters[ param1Key ] = param1Value;
		urlParameters[ param2Key ] = param2Value;

		var callback = function( data )
		{
			var $messageJust = $(".messageJust");
			$messageJust.html("");
			var isDetroAdmin = data.isDetroAdmin;
			var canExecute = data.canExecute;
			var msg = data.warnMessage;

			if ( canExecute )
			{
				$( '#form' ).submit();
				return;
			}
			if ( isDetroAdmin )
			{
			    $messageJust.append("<p>Todos os registros posteriores à data de início de vigência informada e os BOMs listados abaixo serão apagados.</p><label>BOMs:</label><div class='popup-msg'>" + msg + "</div><p>Deseja continuar?</p>");
				bomweb.openModalDefaultPanel({modalId: "#modalView2", data: "" });
			}
			else
			{
				bomweb.openModalDefaultPanel(
				{
					data:"<div class='white_content_header'><img src='../../images/ico_modal_alert.png' align='absmiddle'> Alerta</div>"
						+ "<div class='white_content_content'><ul><li class='liModal'>Somente é possível fazer edições futuras.</li></ul><br/></div>"
						+ "<div class='white_content_footer'><input type='button' value='OK' class='simplemodal-close'></div>" ,
				});
			}
		};

		$.getJSON( urlBase, urlParameters, callback );
		return false;
	};

	var validateSubmit = $( ".submit" ).bind( "click", funcClick );

	var bindJustifyKeypress = function( event )
	{
		var $justifyTextArea = $( "#justificativa" );
		var $justifyConfirmButton = $( ".save-justify" );

		
		$justifyTextArea.live( "keyup", function()
		{
			var $this = $( this );
			var thisVal = $this.val();
			
			if( thisVal.length > 0 )
			{
				$justifyConfirmButton.attr( 'disabled', false );
			}
			else
			{
				$justifyConfirmButton.attr( 'disabled', true );
			}
			
		});
	};	
	
	var openJustificativa = function( event )
	{
		var $simpleModalContainer = $( "#simplemodal-container" );
		
		var $popupContentToChange = $simpleModalContainer.find( ".white_content_content" );
		var $popupFooterToChange = $simpleModalContainer.find( ".white_content_footer" );
		
		$popupContentToChange.html( "Insira uma justificativa para a edição retroativa:<textarea id='justificativa' rows='10' cols='200' class='required'></textarea>" );
		$popupFooterToChange.html( "<input id='btnSalvarJustificaEdicaoRetroativa' type='button' value='Confirmar' class='save-justify' disabled='disabled'><input class='simplemodal-close' type='button' value='Cancelar' onclick='javascript:fechaModalPanel()'>");
	
		bindJustifyKeypress();
	};
	
    var submitJustificativa = $( "#btnSalvarJustificaEdicaoRetroativa" ).live( "click", submitJustificativa );
	
	var openJustificativa = $( "#btnSimEdicaoRetroativa" ).bind( "click", openJustificativa );
	
	var submitForm = $( ".yes simplemodal-close" ).bind( "click", function( event )
	{		
		$( '#form' ).submit();
	});
	
});

