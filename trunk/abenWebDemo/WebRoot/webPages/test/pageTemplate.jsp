<%@ page contentType="text/html; charset=utf-8" language="java"%> 
<html>   
<head>   
<title>模板页面</title>   
<script type="text/javascript">     
var panel_subscriberpkdiv = new Ext.Panel({   
    renderTo : 'subscriberpkdiv',   
    width : Ext.get("subscriberpkdiv").getWidth(),   
    height : Ext.get("subscriberpkdiv").getHeight(),   
    border : false,   
    layout : 'border',   
    tbar : ['->', {   
        text : 'TEXT',   
        iconCls : 'icon-add',   
        xtype : 'easyButton',   
        tooltip : 'TEXT'  
    }, '-', {   
        text : 'TEXT',   
        iconCls : 'icon-add',   
        xtype : 'easyButton',   
        tooltip : 'TEXT'  
    }]   
});   
</script>   
</head>   
<body><div id="subscriberpkdiv" style="width:100%;height:100%;"></div></body>   
</html>  
