<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Empresa</title>
<script type="text/javascript" src="<c:url value="/jscript/jquery-latest.js" />"></script>
<script type="text/javascript" src="<c:url value="/jscript/jquery.validate.js" />"></script>

<style type="text/css">
	* { font-family: Verdana; font-size: 96%; }
	label { width: 10em; float: left; }
	label.error { float: none; color: red; padding-left: .5em; vertical-align: top; }
	p { clear: both; }
	.submit { margin-left: 12em; }
	em { font-weight: bold; padding-right: 1em; vertical-align: top; }
</style>
 <script>
  $(document).ready(function(){
    $("#iddoform").validate();
  });
 </script>
  
</head>
<body>
	<form id="iddoform" action="<c:url value="/empresa/save" />" method="post">
    <br>
    
    <table>
    	<tr>
    		<td> Código:</td>
    		<td> <input type="hidden" name="empresa.id" value="${empresa.id}" > 
    		<input  id="codigo" 
    				name="empresa.codigo" 
    				value="${empresa.codigo}" 
    				size="3"
    				class="required"
    				number="true"
    				maxlength="3"
    				minlength="3"
    				 /> 
    		</td>
    	</tr>
    	<tr>
    		<td> Nome:</td>
    		<td> <input id="nome" name="empresa.nome" value="${empresa.nome}" size="25" maxlength="25" class="required" minlength="2"/> </td>
    	</tr>
    	<tr>
    		<td> Responsável:</td>
    		<td> <input id="responsavel" name="empresa.responsavel" value="${empresa.responsavel}" size="25" maxlength="25" class="required" minlength="2"/> </td>
    	</tr>
    </table>
    <input class="submit" type="submit" value="Submit"/>
    </form>
</body>
</html>