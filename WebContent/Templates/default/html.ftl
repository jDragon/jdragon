<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" >
<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="raghukr" />
	<meta name="keywords" content="jdragon,java,framework,web application" />
	<meta name="description" content="A demonstration of what can be accomplished visually through CSS-based design." />
	<meta name="robots" content="all" />

	<title>JDragon</title>
	${css}

</head>

<body id="css-zen-garden">

<div id="container">
	<div id="intro">
		<div id="pageHeader">
			<h1><span>JDragon</span></h1>
			<h2><span>The Beauty of <acronym title="jD">JDragon</acronym> Design</span></h2>
		</div>


		<div id="preamble">
		</div>
	</div>

<#if messages?exists>
	<div class="messages">
		<#list messages as msg>
		        ${msg} <br />
		</#list>
	</div>
</#if>

<#if errors?exists>
	<div class="errors">
		<#list errors as err>
		        ${err} <br />
		</#list>
	</div>
</#if>
	
		<h3><span>${title}</span></h3>
		${content}
	<div id="linkList">
		<#if right?exists>
		      ${right}
		</#if>

		<#if left?exists>
		      ${left}
		</#if>

	</div>
</div>

</body>
</html>
