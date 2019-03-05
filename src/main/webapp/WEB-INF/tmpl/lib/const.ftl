<#escape x as x?html>
<#assign root = "" />
<#assign gContextPath = "" />
<#assign mimgURLPrefix = "http://mimg.127.net" >
<#if local?default(true)>
	<#assign root = gContextPath />
    <#assign miscPath = "${gContextPath}/misc" />
<#else>
	<#assign root = "${mimgURLPrefix}/hxm" />
    <#assign miscPath = "${mimgURLPrefix}/hxm/moneykeeper/misc" />
</#if>
<#assign ver = '${ver!}' />

<#assign jsPath = "" />
<#assign stylePath = "" />
<#assign jsRoot = root />
<#assign cssRoot = root />
<#assign imgRoot = cssRoot + stylePath />
<#assign uid = cssRoot + stylePath />
<#setting url_escaping_charset='UTF-8'>
</#escape>
