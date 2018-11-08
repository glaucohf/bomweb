ListPageTest = TestCase("ListPageTest");

// ---

var listPage;

ListPageTest.prototype.setUp = function () {
	listPage = new ListPage("/bomweb/entidadeDeTeste/dataTableResult.json", "Entidade De Teste");
	
	$ = recorderMock("dataTable", "val", "attr");
	
	var dataTableConfig = {
        "bProcessing": true,
        "bServerSide": true,
        "bLengthChange": false,
        "bFilter": false,
        "sAjaxSource": "/bomweb/entidadeDeTeste/dataTableResult.json",
        "sPaginationType": "full_numbers",
        "fnServerParams": null,
	    "aoColumns": [
		 	{ "sTitle": "Nome", "mDataProp" : "nome" },
		 	{ "sTitle": "Número", "mDataProp" : "numero" }, 
		 	{ "sTitle": "Ações", "mDataProp" : "acoes", "fnRender": renderizaColunaAcoes } 
		],
	 	"oLanguage": {
	 		"sInfo": "A pesquisa retornou _TOTAL_ Entidade De Teste(s). Mostrando Entidade De Teste(s) _START_ à _END_.",
	 		"sEmptyTable": "A pesquisa não retornou nenhum(a) Entidade de Teste.",
	 		"sProcessing": "Pesquisando...",
            "oPaginate": {
                "sFirst": "Primeira Página",
                "sLast": "Última Página",
                "sNext": "Próxima",
                "sPrevious": "Anterior"
            }
        }
    };
	
	expect($.dataTable.__calls[0].arguments).to(eql, [dataTableConfig]);
	// TODO:
	$.dataTable.__return(function(call) {
		var selector = call.previous.arguments[0];
		
		selector.fnServerParams.apply();
		
		return null;
	});
};

//---

ListPageTest.prototype.testSePreencheRequisicaoComOsDadosDosCamposDePesquisaAntesDeBuscarDadosNoServidor = function() {
	$.val.__return(function(call) {
		var selector = call.previous.arguments[0];
		return { "#nome": "Teste",
			     "#numero": "123"}[selector];
	});
	
	expect($.attr.__calls[0].arguments).to(eql, ["name"]);
	$.attr.__return(function(call) {
		var selector = call.previous.arguments[0];
		return { "#nome": "entityForSearch.nome",
			     "#numero": "entityForSearch.numero"}[selector];
	});
	
	//---
	
	listPage.setSearchFieldIds(["nome", "numero"]);
	
	listPage.initialize();
};

//---

ListPageTest.prototype.testSeRenderizaOBotaoDeEditarQuandoTemPerfilDetroOuDetroAdminEPodeEditar = function() {
	fail();
};

//---

ListPageTest.prototype.testSeRenderizaOBotaoDeEditarQuandoTemPerfilEmpresaEPodeEditar = function() {
	fail();
};

//---

ListPageTest.prototype.testSeRenderizaOBotaoDeExcluirQuandoPodeExcluir = function() {
	fail();
};

//---

ListPageTest.prototype.testSeRenderizaOBotaoDeVisualizarHistoricoQuandoPodeVisualizarHistorico = function() {
	fail();
};

//---

ListPageTest.prototype.testSeCriaTabelaVaziaComIdDataTable = function() {
	fail();
};

//---

ListPageTest.prototype.testSeCriaDataTableNaTabelaComIdDataTable = function() {
	fail();
};

//---

ListPageTest.prototype.testSeConfiguraOClickDoBotaoComIdPesquisaParaLimparATabela = function() {
	fail();
};

//---

ListPageTest.prototype.testSeConfiguraAsInformacoesDeColunaDoDataTable = function() {
	fail();
};

//---

ListPageTest.prototype.testSeTraduzOsTextosDoDataTable = function() {
	fail();
};

//---