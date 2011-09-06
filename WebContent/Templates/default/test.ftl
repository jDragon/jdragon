<h1>${welcome1}</h1>
<br /><br />
<h2>
${welcome2}
</h2> <br /><br />
${welcome2}
<br />

<#if sum?exists>
	<div>
      The Sum of numbers is: ${sum}
    </div>
</#if>

<#if form?exists>
	<div>
      ${form}
    </div>
</#if>

<#list args as arg>
        ${arg} <br />
</#list>
