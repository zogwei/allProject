
Ext.namespace('ExtExample.layout');

ExtExample.layout.portalLayout = function(){
	this.init();
};


Ext.extend(ExtExample.layout.portalLayout,Ext.util.Observable,{
	init:function(){
	
		var mainPanel = new Ext.Panel({
			layout:'fit',
			renderTo:'layout-portalLayout-main',
			title:'布局拖拽练习',
			width:700,
			height:400,
			items:[{
				xtype:'portal',
				margins:'5 5 5 0',
				items:[{
					columnWidth:.33,
					style:'padding:10px 0 10px 10px',
					items:[{
						title: 'Another Panel 1',
						html: 'adsa'
					}]
				},{
					columnWidth:.33,
					style:'padding:10px 0 10px 10px',
					items:[{
						title: 'Another Panel 2',
						html: 'adsa'
					},{
						title: 'Another Panel 3',
						html: 'adsa'
					}]
				},{
					columnWidth:.33,
					style:'padding:10px',
					items:[{
						title: 'Another Panel 4',
						html: 'adsa'
					},{
						title: 'Another Panel 5',
						html: 'adsa'
					}]
				}]
			}]
		});
		
	},
	//-----------------模块销毁函数---------------------------
	destroy:function(){
		
	}
});
