<%@ page contentType="text/html; charset=UTF-8" %>    
<html>   
<head>   
<title>模板页面</title>   
<script type="text/javascript">     
var from_subscriberpk = new Ext.FormPanel({   
    labelAlign : 'left',   
    labelWidth : 140,   
    frame : true,   
    title : '查询',   
    region : 'north',   
    collapseMode : 'mini',   
    split : true,   
    height : 140,   
    minSize : 120,   
    maxSize : 200,   
    split : true,   
    collapsible : true,   
    margins : '5 5 0 5',   
    items : [{   
        layout : 'column',   
        items : [{   
            columnWidth : .4,   
            layout : 'form',   
            defaults : {   
                anchor : '93%'  
            },   
            items : [{   
                xtype : 'textfield',   
                fieldLabel : 'TEXT',   
                name : ''  
            }]   
        }, {   
            columnWidth : .4,   
            layout : 'form',   
            defaults : {   
                anchor : '93%'  
            },   
            items : [{   
                xtype : 'textfield',   
                fieldLabel : 'TEXT',   
                name : ''  
            }]   
        }]   
    }, {   
        layout : 'column',   
        items : [{   
            columnWidth : .4,   
            layout : 'form',   
            defaults : {   
                anchor : '93%'  
            },   
            items : [{   
                xtype : 'datefield',   
                fieldLabel : 'TEXT',   
                name : ''  
            }]   
        }, {   
            columnWidth : .4,   
            layout : 'form',   
            defaults : {   
                anchor : '93%'  
            },   
            items : [{   
                xtype : 'datefield',   
                fieldLabel : 'TEXT',   
                name : ''  
            }]   
        }, {   
            columnWidth : .2,   
            layout : 'table',   
            items : [{   
                xtype : 'button',   
                width : '70',   
                // iconCls : 'icon-add',   
                xtype : 'easyButton',   
                text : '查 询'  
            }, {   
                xtype : 'button',   
                width : '70',   
                //iconCls : 'icon-add',   
                 xtype : 'easyButton',   
                style : 'margin-left: 5px',   
                text : '清 空'  
            }]   
        }]   
    }]   
});   
  
// ------------------Grid 部分-----------------------   
function rendercz(value, cellmeta, record, rowIndex, columnIndex, store) {   
    return "<img src = 'images/edit.gif'  title='操作' align='center' onclick='tab_subscriberpk(\""  
            + record.data["region"]+"\",\""+ record.data["pwd"]+"\")' />";   
};   
  
function rowdblclickfn(grid, rowIndex, e){// 双击事件   
    var row = grid_subscriberpk.store.getById(grid.store.data.items[rowIndex].id);      
    tab_subscriberpk(row.get("region"),row.get("pwd"));    
}     
  
function tab_subscriberpk(id,code){// 打开TAB   
        var url = 'MyJsp.jsp';   
        var tab = panel.findById('tab-'+id);   
        if (tab == undefined){   
                    tab = panel.add({   
                         id:'tab-'+id,   
                         title: code,   
                         iconCls: 'icon-nav-p1',   
                         closable:true,// 通过html载入目标页   
                         // html:'<iframe id="'+id+'" scrolling="auto"   
                            // frameborder="0" width="100%" height="100%"   
                            // src="'+url+'"></iframe>'   
                         autoLoad: url   
                    });   
                    panel.setActiveTab(tab);   
            }else{   
                panel.activate(tab)   
            }   
 }  
  
function getrecordarry(records,field) {   
        var result = [];   
        for(var i = 0; i < records.length; i++) {   
            result.push(records[i].get(field));   
        }   
        return result;   
}   
  
// 删除操作   
function deletesubscriberpk(){   
        var records = grid_subscriberpk.getSelectionModel().getSelections();// 删除多行   
        if (records) {   
            Ext.MessageBox.confirm('确认删除','确定要删除所选记录?',function(btn){   
            if (btn == 'yes'){   
                Ext.Ajax.request({   
                    url:'SubscriberPk/extdelete.do?ids='+getrecordarry(records, 'subsid'),   
                    method:'POST',   
                    success:function(response){   
                        var data = Ext.util.JSON.decode(response.responseText);   
                        if (data.success == true){   
                            Ext.Msg.show({title:'成功提示',msg:data.msg,buttons:Ext.Msg.OK,icon: Ext.MessageBox.INFO});   
                            ds_subscriberpk.load();   
                        }   
                        else{   
                            Ext.MessageBox.alert('警告',data.msg);   
                        }   
                    },scope:this  
                });   
              }   
           },this);   
        }else {   
            Ext.Msg.show({title:'提示信息',msg:'请选择需删除的记录!',buttons:Ext.Msg.OK,icon:Ext.Msg.INFO});   
        }   
}   
  
var fom_addsubscriberpk =new Ext.FormPanel({   
            labelWidth:100,   
            labelAlign:'right',   
            frame:true,   
            autoScroll:true,// 滚动条   
            items:[{   
                    xtype:'panel',   
                    layout:'column',   
                    width:400,   
                    defaults:{border:false}   
                    }   
                    ,{xtype:'hidden',fieldLabel:'subsid',name:'subsid',width:288}   
                    ,{xtype:'textfield',fieldLabel:'region',name:'region',width:288}   
                    ,{xtype:'textfield',fieldLabel:'pwd',name:'pwd',width:288}   
                    ,{xtype:'textfield',fieldLabel:'createdate',name:'createdate',width:288}   
                    ,{xtype:'textfield',fieldLabel:'score',name:'score',width:288}   
                    ,{xtype:'textfield',fieldLabel:'startdate',name:'startdate',width:288}   
                    ,{xtype:'textfield',fieldLabel:'invaliddate',name:'invaliddate',width:288}   
                    ,{xtype:'textfield',fieldLabel:'substype',name:'substype',width:288}   
                    ,{xtype:'textfield',fieldLabel:'active',name:'active',width:288}   
                    ,{xtype:'textfield',fieldLabel:'status',name:'status',width:288}   
            ],   
            buttons:[{   
                text:'保存',   
                handler:function(){// 保存操作   
                if (fom_addsubscriberpk.form.isValid() == false){   
                    return;   
                }   
                fom_addsubscriberpk.form.submit({   
                url:'SubscriberPk/extsave.do',   
                success:function(form,action){   
                      Ext.MessageBox.alert('警告',action.result.msg);   
                      win_addsubscriberpk.hide();   
                      grid_subscriberpk.getStore().reload();   
                   },   
                 scope:this,   
                 failure:function(form,action){   
                      Ext.MessageBox.alert('警告',action.result.msg);   
                   }   
                 })   
                }   
                },{   
                     text:'取消',   
                     handler:function(){win_addsubscriberpk.hide();}   
                }]   
             }   
);   
  
var win_addsubscriberpk = new Ext.Window(   
    {title:'添加记录',iconCls : 'icon-add',width:535,height:400,border:false,resizable:false,autoHeight:true,modal:true,closeAction:'hide',   
    items:[fom_addsubscriberpk]   
});   
  
var fom_upsubscriberpk =new Ext.FormPanel({   
            labelWidth:100,   
            labelAlign:'right',   
            frame:true,   
            autoScroll:true,// 滚动条   
            items:[{   
                    xtype:'panel',   
                    layout:'column',   
                    width:400,   
                    defaults:{border:false}   
                    }   
                    ,{xtype:'hidden',fieldLabel:'subsid',name:'subsid',width:288}   
                    ,{xtype:'textfield',fieldLabel:'region',name:'region',width:288}   
                    ,{xtype:'textfield',fieldLabel:'pwd',name:'pwd',width:288}   
                    ,{xtype:'textfield',fieldLabel:'createdate',name:'createdate',width:288}   
                    ,{xtype:'textfield',fieldLabel:'score',name:'score',width:288}   
                    ,{xtype:'textfield',fieldLabel:'startdate',name:'startdate',width:288}   
                    ,{xtype:'textfield',fieldLabel:'invaliddate',name:'invaliddate',width:288}   
                    ,{xtype:'textfield',fieldLabel:'substype',name:'substype',width:288}   
                    ,{xtype:'textfield',fieldLabel:'active',name:'active',width:288}   
                    ,{xtype:'textfield',fieldLabel:'status',name:'status',width:288}   
            ],   
            buttons:[{   
                text:'修改',   
                handler:function(){   
                if (fom_upsubscriberpk.form.isValid() == false){   
                    return;   
                }   
                fom_upsubscriberpk.form.submit({   
                url:'SubscriberPk/extupdate.do',   
                success:function(form,action){   
                      Ext.MessageBox.alert('警告',action.result.msg);   
                      win_upsubscriberpk.hide();   
                      grid_subscriberpk.getStore().reload();   
                   },   
                 scope:this,   
                 failure:function(form,action){   
                      Ext.MessageBox.alert('警告',action.result.msg);   
                   }   
                 })   
                }   
                },{   
                     text:'取消',   
                     handler:function(){win_upsubscriberpk.hide();}   
                }]   
             }   
);   
  
var win_upsubscriberpk = new Ext.Window(   
    {title:'修改记录',iconCls : 'icon-editp',width:535,height:400,border:false,resizable:false,autoHeight:true,modal:true,closeAction:'hide',   
    items:[fom_upsubscriberpk]});   
  
function upsubscriberpk(){   
       var records = grid_subscriberpk.getSelectionModel().getSelections();   
       if (records.length==0) {   
            Ext.Msg.alert("提示", "请选择要修改的记录");   
            return;   
        }   
       if (records.length!=1) {   
            Ext.Msg.alert("提示", "请只选择1条要修改的记录");   
            return;   
        }   
        fom_upsubscriberpk.form.loadRecord(records[0]);   
        win_upsubscriberpk.show();   
}   
  
sm = new Ext.grid.CheckboxSelectionModel();   
  
ds_subscriberpk =new Ext.data.Store({   
    url:'SubscriberPk/extlist.do',   
    reader:new Ext.data.JsonReader({   
    root:'list',   
    totalProperty:'totalSize',   
    id:'id'  
    },   
     ['subsid','region','pwd','createdate','score','startdate','invaliddate','substype','active','status','cz']),   
    baseParams:{limit:10},   
    remoteSort:true  
});   
  
cm_subscriberpk = new Ext.grid.ColumnModel([   
    sm,new Ext.grid.RowNumberer()   
    ,{header:'region',width:100,sortable:true,dataIndex:'region'}   
    ,{header:'pwd',width:100,sortable:true,dataIndex:'pwd'}   
    ,{header:'createdate',width:100,sortable:true,dataIndex:'createdate'}   
    ,{header:'score',width:100,sortable:true,dataIndex:'score'}   
    ,{header:'startdate',width:100,sortable:true,dataIndex:'startdate'}   
    ,{header:'invaliddate',width:100,sortable:true,dataIndex:'invaliddate'}   
    ,{header:'substype',width:100,sortable:true,dataIndex:'substype'}   
    ,{header:'active',width:100,sortable:true,dataIndex:'active'}   
    ,{header:'status',width:100,sortable:true,dataIndex:'status'}   
    ,{header : '操作',width:40,dataIndex :'cz',renderer : rendercz}   
]);   
  
cm_subscriberpk.defaultSortable = true;   
  
grid_subscriberpk= new Ext.grid.EditorGridPanel({   
    margins : '0 5 5 5',   
    store : ds_subscriberpk,   
    sm : sm,   
    cm : cm_subscriberpk,   
    stripeRows : true,   
    viewConfig : {   
        forceFit : true  
    },   
    loadMask : {   
        msg : '正在加载数据，请等待...'  
    },   
    region : 'center',   
    // clicksToEdit : 1,   
    trackMouseOver : true,   
    tbar : [{   
        text : '添加',   
        iconCls : 'icon-add',   
        handler : function() {win_addsubscriberpk.show()}   
        },'-',{   
        text:'删除',   
        iconCls:'icon-dela',   
        handler : function() {deletesubscriberpk()}   
        },'-',{   
        text:'修改',   
        iconCls:'icon-editp',   
        handler : function() {upsubscriberpk()}   
        },'-',{   
        text : '刷新',   
        iconCls : 'icon-ref',   
        handler : function() {   
            ds_subscriberpk.load();   
        }   
    },'->','<span style="color:blue;">双击表格可查看  </span>'],   
    bbar : new Ext.PagingToolbar({   
        pageSize:10,   
        store:ds_subscriberpk,   
        displayInfo:true  
    })   
});   
  
grid_subscriberpk.addListener('rowdblclick', rowdblclickfn);      
  
ds_subscriberpk.load({   
    params:{start:0},   
    callback:function(r, options, success){   
        if (success) {   
            storeLoadMask.hide();   
        }   
    }   
});   
  
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
    }],   
    items : [from_subscriberpk, grid_subscriberpk]   
});   
</script>   
</head>   
<body><div id="subscriberpkdiv" style="width:100%;height:100%;"></div></body>   
</html>  
