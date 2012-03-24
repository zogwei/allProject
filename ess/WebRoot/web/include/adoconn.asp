<%
DB="./App_Data/#oyaya!crm.mdb"
Set fso = Server.CreateObject("Scripting.FileSystemObject")
if fso.FolderExists(server.MapPath("include"))=false then
DB="../"&DB
end if
set fso=nothing
path=Server.MapPath(DB)
set conn=server.createobject("adodb.Connection")
connstr="provider=Microsoft.Jet.OLEDB.4.0;Data Source="&path
conn.Open connstr
Set rs=server.CreateObject("adodb.recordset")

Function fclose()
 Set rs=Nothing
 Set conn=nothing
End Function



Function SafeRequest(ParaName)
Dim ParaValue
ParaValue=Request(ParaName)
if IsNumeric(ParaValue) = True then
SafeRequest=ParaValue
exit Function
elseIf Instr(LCase(ParaValue),"select ") > 0 or Instr(LCase(ParaValue),"insert ") > 0 or Instr(LCase(ParaValue),"delete from") > 0 or Instr(LCase(ParaValue),"count(") > 0 or Instr(LCase(ParaValue),"drop table") > 0 or Instr(LCase(ParaValue),"update ") > 0 or Instr(LCase(ParaValue),"truncate ") > 0 or Instr(LCase(ParaValue),"asc(") > 0 or Instr(LCase(ParaValue),"mid(") > 0 or Instr(LCase(ParaValue),"char(") > 0 or Instr(LCase(ParaValue),"xp_cmdshell") > 0 or Instr(LCase(ParaValue),"exec master") > 0 or Instr(LCase(ParaValue),"net localgroup administrators") > 0 or Instr(LCase(ParaValue)," and ") > 0 or Instr(LCase(ParaValue),"net user") > 0 or Instr(LCase(ParaValue)," or ") > 0 Or InStr(LCase(ParaValue),"""")>0 Or InStr(LCase(ParaValue),"'")>0 then 
Response.Write "请不要在函数中加入非法字符！" 
Response.end
else 
SafeRequest=ParaValue 
End If 
End function 

%>
