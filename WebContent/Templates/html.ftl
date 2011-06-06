<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">
<HTML>
<HEAD>
<TITLE></TITLE>
<META NAME="Generator" CONTENT="TextPad 4.6">
<META NAME="Author" CONTENT="?">
<META NAME="Keywords" CONTENT="?">
<META NAME="Description" CONTENT="?">
</HEAD>

<BODY>
<div style="color: ff0000; text-align: center">
	<#if errors?exists>
		<#list errors?keys as errkeys>
		        ${errkeys}:${errors[errkeys]} <br />
		</#list>
	</#if>
</div>

<TABLE ALIGN="left" BORDER=0 CELLSPACING=0 CELLPADDING=0 WIDTH="100%">

<TR ALIGN="left">
	<TD> 
		<#if left?exists>
		      ${left}
		</#if>
	</TD>
	<TD>
		${content}
	</TD>
	<TD>
		<#if right?exists>
		      ${right}
		</#if>
	</TD>
</TR>
</TABLE>

</BODY>
</HTML>
