
Ext.namespace('ExtExample.layout');

ExtExample.layout.accordionLayout = function(){
	this.init();
};

Ext.extend(ExtExample.layout.accordionLayout,Ext.util.Observable,{
	init:function(){
		/**/
		var treePanel = new Ext.tree.TreePanel({
			title:'树形列表',
			id:'extExample-layout-accordion-tree',
			autoScroll:true, 
			enableDD:true,//是否支持拖拽效果
			containerScroll: true,//是否支持滚动条
			rootVisible:true,//是否显示跟节点
			loader:new Ext.tree.TreeLoader({  
				dataUrl:'extExampleTree.json'  
			}),
			tools:[{
				id:'refresh',
				handler:function(){
					var tree = Ext.getCmp('extExample-layout-accordion-tree');
					tree.root.reload();
				}
			}]
		});
		//菜单根节点
		var root = new Ext.tree.AsyncTreeNode({
			text:'ExtJs2.0 实例展示',
			draggable:false,
			id:'extExample-layout-accordion-treeRoot'
		});
		
		treePanel.setRootNode(root);
		root.expand();
		
		
		//创建autoLoad的panel
		
		var autoLoadPanel = new Ext.Panel({
			title:'异步加载的Panel',
			autoLoad:{url:'modules/layout/accordionLayout-tmp1.html', scripts:true}
		});
		
		
		var mainPanel = new Ext.Panel({
			renderTo:'layout-accordionLayout-main',
			title:'accordion练习',
			layout:'accordion',//在此设置为accordion布局
			/*
			layoutConfig为布局的配置参数主要有三个属性分别为:
				1.titleCollapse:默认是就是true，点击标题就可以折叠子面板，如果设置成false，就只能点击title右边的图标折叠子面板。
				2.animate:展开折叠的时候是否使用动画效果呢。
				3.activeOnTop:默认值是false，进行展开折叠后，子面板的顺序不会改变，
							  如果改成true，就会随着展开折叠改变顺序，就是总把展开的子面板放在最上头啦。

			*/
			layoutConfig: {
            	titleCollapse: true,
            	animate: true,
            	activeOnTop: false
        	},
			width:260,
			height:400,
			items:[
				treePanel,
				autoLoadPanel,
				{title:'临时的panel',html:'临时的panel'}
			]
		});
		
	},
	//-----------------模块销毁函数---------------------------
	destroy:function(){
		
	}

});
