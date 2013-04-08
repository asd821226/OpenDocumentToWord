FreeMarker Template example: ${message} 

=======================
===  Data List   ====
=======================
<#function zebra index>
  <#if (index % 2) == 0>
    <#return "white" />
  <#else>
    <#return "black" />
  </#if>
</#function>

<#assign keys = tableData?keys>
<#assign word_a = "A.test<br/>B.test<br/>C.test<br/>">
<#assign word_b = "A.test B.test C.test">

<#list keys as key>
    ${key}=${tableData[key].tableChineseName}
        <#list tableData[key].columnList as colData>
        1.=<#if tableData[key].primaryKey == colData.columnName>PK<#else>  </#if>=
        2.=${colData.notNull?string}=
        3.=<#if colData.notNull?string == 'true'>OK<#else>NOT OK</#if>
        4.=${colData_index + 1}=${zebra(colData_index)}
        5.=comment=${colData.note!"no comment"}=
        6.=detail=${colData.detail!"no detail"}=
        </#list>
</#list>

<#-- <#if "piceous"?contains("ice")>It contains "ice"</#if>   -->
<#if word_a?contains("<br/>") >
    <#list word_a?split("<br/>") as x>
    - ${x}
    </#list>
</#if>

<#if word_b?contains("<br/>") >xxx</#if>
