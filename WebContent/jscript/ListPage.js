/**
 * =====================================================
 * Script Base para todas as páginas de lista (list.jsp)
 * =====================================================
 */
var DEFAULT_CONTAINER_ELEMENT_ID = "divDataTable";
var DEFAULT_TABLE_ELEMENT_ID = "dataTable";
var DEFAULT_SEARCH_BUTTON_ID = "dataTableFetchData";

function ListPage(baseUrl, baseEntityName) {
	this.__baseUrl = baseUrl;
	this.__baseEntityName = baseEntityName;
	this.__searchFieldsIds = [];
	this.__containerElementId = DEFAULT_CONTAINER_ELEMENT_ID;
	this.__tableElementId = DEFAULT_TABLE_ELEMENT_ID;
	this.__searchButtonId = DEFAULT_SEARCH_BUTTON_ID;
	this.__dataTable = null;
	this.__columnsDefinitions = [];
	this.__jsonData = null;
	this.__ignoreActionColumn = false;
	this.__searchFormValidationFunction = null;
	this.__afterDataFetchCallback = null;

	this.reset = function() {
		if (this.__dataTable == null) {
			throw new Error("Deve-se chamar o método 'initialize()' primeiro.");
		}
		
		this.__dataTable.fnClearTable(false);
		this.__resetSearchFilterFields();
	};
	
	this.__resetSearchFilterFields = function() {
		this.__forEachSearchField(function(searchField) {
			// FIXME: Essa é uma maneira ingênua de limpar um campo.
			searchField.val("");
		});
	};
	
	/**
	 * Expects a function in the form:
	 * 
	 * 	function(json) {
	 * 		return [true|false];
	 * 	}
	 */
	this.setAfterDataFetchCallback = function(callback) {
		this.__afterDataFetchCallback = callback;
	};
	
	this.getColumnIndexByJSONPropertyName = function(jsonPropertyName) {
		for (var i = 0; i < this.__columnsDefinitions.length; i++) {
			var currentColumnJSONPropertyName = this.__columnsDefinitions[i]["mDataProp"];
			if (currentColumnJSONPropertyName == jsonPropertyName) {
				return i;
			}
		}
		
		return -1;
	};
	
	this.hideColumn = function(columnIndex) {
		if (this.__dataTable == null) {
			throw new Error("Deve-se chamar o método 'initialize()' primeiro.");
		}
		
		if (columnIndex == undefined || columnIndex == null || isNaN(columnIndex) || columnIndex < 0) {
			throw new Error();
		}
		
		this.__dataTable.fnSetColumnVis(columnIndex, false, false);
	};
	
	this.showColumn = function(columnIndex) {
		if (this.__dataTable == null) {
			throw new Error("Deve-se chamar o método 'initialize()' primeiro.");
		}
		
		if (columnIndex == undefined || columnIndex == null || isNaN(columnIndex) || columnIndex < 0) {
			throw new Error();
		}
		
		this.__dataTable.fnSetColumnVis(columnIndex, true, false);
	};
	
	this.ignoreActionColumn = function() {
		this.__ignoreActionColumn = true;
	};
	
	/**
	 * Expects a function in the form:
	 * 
	 * 	function() {
	 * 		return [true|false];
	 * 	}
	 */
	this.setSearchFormValidationFunction = function(searchFormValidationFunction) {
		this.__searchFormValidationFunction = searchFormValidationFunction;
	};
	
	this.setSearchFieldsIds = function(searchFieldsIds) {
		if (searchFieldsIds == undefined || searchFieldsIds == null || !(searchFieldsIds.length)) {
			throw new Error();
		}
		
		this.__searchFieldsIds = this.__searchFieldsIds.concat(searchFieldsIds);
	};
	
	this.setTableElementId = function(tableElementId) {
		if (tableElementId == undefined || tableElementId == null) {
			throw new Error();
		}
		
		this.__tableElementId = tableElementId;
	};
	
	this.setContainerElementId = function(containerElementId) {
		if (containerElementId == undefined || containerElementId == null) {
			throw new Error();
		}
		
		this.__containerElementId = containerElementId;
	};
	
	this.setTableElementId = function(tableElementId) {
		if (tableElementId == undefined || tableElementId == null) {
			throw new Error();
		}
		
		this.__tableElementId = tableElementId;
	};
	
	this.setSearchButtonId = function(searchButtonId) {
		if (searchButtonId == undefined || searchButtonId == null) {
			throw new Error();
		}
		
		this.__searchButtonId = searchButtonId;
	};
	
	this.addColumnDefinition = function(columnTitle, jsonPropertyName, columnProperties) {
		if (columnTitle == undefined || columnTitle == null) {
			throw new Error();
		}
		
		if (jsonPropertyName == undefined || jsonPropertyName == null) {
			throw new Error();
		}
		
		var columnDefinition = {"sTitle" : columnTitle, "mDataProp": jsonPropertyName };
		
		if (columnProperties != undefined && columnProperties != null) {
			for (var columnProperty in columnProperties) {
				var columnPropertyValue = columnProperties[columnProperty];
				columnDefinition[columnProperty] = columnPropertyValue;
			}
		}
		
		this.__columnsDefinitions.push(columnDefinition);
	};
	
	this.__createEmptyTable = function() {
		if ($("#" + this.__containerElementId).length == 0) {
			throw new Error("Não existe um elemento com id '" + this.__containerElementId + "'.");
		}
		
		$("#" + this.__containerElementId).html("<table class='tablesorter' id='" + this.__tableElementId + "'></table>");
	};
	
	this.__buildColumnDefinitions = function() {
		if (this.__ignoreActionColumn) {
			return this.__columnsDefinitions;
		} 
		else {
			return this.__columnsDefinitions.concat([this.__buildActionColumnDefinition()]);
		}
	};
	
	this.__buildActionColumnDefinition = function() {
		var $this = this;
		return { "sTitle": "Ações", "bSortable": false, "fnRender": function(row) { if (!row) return; return $this.__renderActionColumn(row); } };
	};
	
	this.setCreateEditLinkCallback = function(callback) {
		this.__createEditLink = callback;
	};
	
	this.setCreateHistoryLinkCallback = function(callback) {
		this.__createHistoryLink = callback;
	};
	
	this.setRenderActionColumnCallback = function(callback) {
		this.__renderActionColumn = callback;
	};
	
	this.__createEditLink = function(row) {
		if (row.aData.podeEditar) {
			return "<a href='edit/" + row.aData.id + "' data-futuro='false' title='Editar'><img src='../images/editar.png' /></a>";
		} 
		else {
			return "";
		}
	};
	
	this.__createDeleteLink = function(row) {
		if (row.aData.podeExcluir) {
			//debugger;
			return "<a href='delete-confirm/" + row.aData.id + "' onclick='javascript:ListPage.showModalPanel(this, event)' title='Excluir'><img src='../images/excluir.png' /></a>";
		}
		else {
			return "";
		}
	};
	
	this.__createHistoryLink = function(row) {
		if (row.aData.podeVerHistorico) {
			return "<a href='historico/" + row.aData.id + "' title='Histórico'><img src='../images/historico.png' alt='Histórico' /></a>";
		}
		else {
			return "";
		}
	};
	
	this.__renderActionColumn = function(row) {
		var html = "";
		
		html += this.__createEditLink(row);
		html += this.__createDeleteLink(row);
		html += this.__createHistoryLink(row);
		
        return html;
	};
	
	this.__forEachSearchField = function(closure) {
		for (var i = 0; i < this.__searchFieldsIds.length; i++) {
			var jQueryFieldId = "#" + this.__searchFieldsIds[i];
			var searchField = $(jQueryFieldId);
			if (searchField.length > 0) {
				closure(searchField);
			}
		}
	};
	
	this.__appendSearchFormFieldsToRequest = function(data) {
		if (!data) return;
		
		this.__forEachSearchField(function(searchField) {
			var searchFieldName = searchField.attr("name");
			var searchFieldValue;
			if (searchField.is(":checkbox")) {
				searchFieldValue = searchField.is(":checked");
			} 
			else {
				searchFieldValue = searchField.val();
			}
			
			if (searchFieldValue == null) searchFieldValue = "";
			
			data.push({ "name": searchFieldName, "value": searchFieldValue });
		});
	};
	
	this.fetchServerData = function() {
		if (this.__dataTable == null) {
			throw new Error("Deve-se chamar o método 'initialize()' primeiro.");
		}
		
		if (this.__searchFormValidationFunction != null && !this.__searchFormValidationFunction()) return;
		
		this.__dataTable.fnClearTable(true);
	};
	
	this.__createTranslationInfo = function() {
		return {
	 		"sInfo": "A pesquisa retornou _TOTAL_ " + this.__baseEntityName + "(s). Mostrando " + this.__baseEntityName + "(s) _START_ á _END_.",
	 		"sZeroRecords": "Não foram encontrados resultados.",
	 		"sEmptyTable": "Não foram encontrados resultados.",
	 		"sInfoEmpty": "Não há resultados.",
	 		"sProcessing": "Pesquisando...",
            "oPaginate": {
                "sFirst": "Primeira",
                "sLast": "Última",
                "sNext": "Próxima",
                "sPrevious": "Anterior"
            }
		};
	};
	
	this.__createDataTablePluginConfiguration = function() {
		var $this = this;
		return {
	        "bProcessing": true,
	        "bServerSide": true,
	        "bLengthChange": false,
	        "bFilter": false,
	        "iDisplayLength": 20, 
	        "sAjaxSource": this.__baseUrl + "/dataTableResult.json",
	        "sPaginationType": "full_numbers",
	        "fnServerParams": function(data) { $this.__appendSearchFormFieldsToRequest(data); },
	        "fnServerData": function(source, data, callback) { $this.__fetchData(source, data, callback); },
		    "aoColumns": this.__buildColumnDefinitions(),
		 	"oLanguage": this.__createTranslationInfo(),
		 	"fnRowCallback": function(row) { return $this.__adjustRowSizeCallback(row); }
		};
	};
	
	this.__fetchData = function(source, data, callback) {
		var $this = this;
		
		this.__jsonData = null;
		
        $.ajax({
          "dataType": "json", 
          "type": "POST", 
          "url": source, 
          "data": data, 
		  "success": function(json) { $this.__jsonData = json; if ($this.__afterDataFetchCallback != null) $this.__afterDataFetchCallback(json); callback(json); } 
        });
	};
	
	this.__adjustRowSizeCallback = function(row) {
		if (this.__ignoreActionColumn) return row;
		
		var actionColumnIndex = this.__columnsDefinitions.length;
		
		if (row.cells[actionColumnIndex]) {
			row.cells[actionColumnIndex].noWrap = true;
		}
		
		return row;
	};
	
	this.__startDataTablePlugin = function() {
		this.__dataTable = $("#" + this.__tableElementId).dataTable(this.__createDataTablePluginConfiguration());
	};
	
	this.__configureSearchForm = function() {
		var $this = this;
		$("#" + this.__searchButtonId).click(function() { $this.fetchServerData(); });
	};
	
	this.initialize = function() {
		this.__createEmptyTable();
		this.__startDataTablePlugin();
		this.__configureSearchForm();
		
		// Não é necessário.
		//this.fetchServerData();
	};
};

ListPage.showModalPanel = function(source, event) {
	if (event.preventDefault){ 
		event.preventDefault();
	} else {
		event.returnValue = false;
	}

	var width = $(source).data('width');
	var height = $(source).data('height');
	
	bomweb.openModalPanel($(source).attr('href'), width, height);
	
	return false;
};

/**
 * FBW-354
 * @param property
 * @returns {Function}
 */
function createRowDateValueRenderFunction( property )
{
	var formatRowDateValueFunc = function ( row )
	{
		var rowData = row.aData;
		var rowDateValue = rowData[property];
		var result = rowDateValue.dateStr;
		return result;
	};// func
	return formatRowDateValueFunc;
};
