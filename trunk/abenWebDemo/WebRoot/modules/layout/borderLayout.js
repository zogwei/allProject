
Ext.namespace('ExtExample.layout');
ExtExample.layout.borderLayout = function(){
	this.init();
} 

Ext.extend(ExtExample.layout.borderLayout,Ext.util.Observable,{
	init:function(){
		
		
		/*
			创建本部容器采用panel作为容器
		*/
		var northPanel = new Ext.Panel({
			title:'北',//设置panel的标题
			region:'north',//用来制定该panel处于哪个位置
			height:50,//设置panel的高度
			split: true,//添加分割线是否可以改变该panel的大小
			minSize: 40,//设置拖动的最小拖动值
        	maxSize: 100,//设置拖动的最大拖动值
			collapsible:true,//是否让panel能自动缩放
			collapseMode:'mini',//在分割线处出现按钮
			html:'北部容器'
		});
		
		var southPanel = new Ext.Panel({
			title:'南',
			region:'south',
			width:100,
			height:50,
			split: true,//添加分割线是否可以改变该panel的大小
			minSize: 40,//设置拖动的最小拖动值
        	maxSize: 100,//设置拖动的最大拖动值
			collapsible:true,//是否让panel能自动缩放
			collapseMode:'mini',//在分割线处出现按钮
			html:'南部容器'
		});
		
		var westPanel = new Ext.Panel({
			title:'西',
			region:'west',
			width:100,
			height:50,
			split: true,//添加分割线是否可以改变该panel的大小
			minSize: 40,//设置拖动的最小拖动值
        	maxSize: 100,//设置拖动的最大拖动值
			collapsible:true,//是否让panel能自动缩放
			collapseMode:'mini',//在分割线处出现按钮
			html:'西部容器'
		});
		
		var eastPanel = new Ext.Panel({
			title:'东',
			region:'east',
			width:100,
			height:50,
			split: true,//添加分割线是否可以改变该panel的大小
			minSize: 40,//设置拖动的最小拖动值
        	maxSize: 100,//设置拖动的最大拖动值
			collapsible:true,//是否让panel能自动缩放
			collapseMode:'mini',//在分割线处出现按钮
			html:'东部容器'
		});
		
		var centerPanel = new Ext.Panel({
			title:'中',
			region:'center',
			html:'中部容器'
		});
		
		
		/*
		创建一个整体的容器，用来填充其他的容器
		并将这个容器renderTo（渲染）到页面的某一个标签上
		*/
		var mainPanel = new Ext.Panel({
			renderTo:'layout-borderlayout-main',
			layout:'border',
			border:true,
			margins:'5 5 5 5',
			height:400,
			items:[northPanel,southPanel,westPanel,eastPanel,centerPanel]
		});
	
	},
	//-----------------模块销毁函数---------------------------
	destroy:function(){
		
	}
	
});