<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" >
<head>
	<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="author" content="Dave Shea" />
	<meta name="keywords" content="design, css, cascading, style, sheets, xhtml, graphic design, w3c, web standards, visual, display" />
	<meta name="description" content="A demonstration of what can be accomplished visually through CSS-based design." />
	<meta name="robots" content="all" />

	<title>css Zen Garden: The Beauty in CSS Design</title>
	${css}

</head>

<body onload="window.defaultStatus='css Zen Garden: The Beauty in CSS Design';" id="css-zen-garden">

<div id="container">
	<div id="intro">
		<div id="pageHeader">
			<h1><span>css Zen Garden</span></h1>
			<h2><span>The Beauty of <acronym title="Cascading Style Sheets">CSS</acronym> Design</span></h2>
		</div>


		<div id="preamble">
		</div>
	</div>
<div class="messages">
	<#if messages?exists>
		<#list messages as msg>
		        ${msg} <br />
		</#list>
	</#if>
</div>
<div class="errors">
	<#if errors?exists>
		<#list errors as err>
		        ${err} <br />
		</#list>
	</#if>
</div>
		<h3><span>${title}</span></h3>
		${content}
	<div id="linkList">

		<#if left?exists>
		      ${left}
		</#if>
		<#if right?exists>
		      ${right}
		</#if>

	</div>
</div>

</body>
</html>
