

//开始继承主页面的module类
Ext.namespace('ExtExample.tree');

ExtExample.tree.checkboxTree = function(){
	this.init();
} 

Ext.extend(ExtExample.tree.checkboxTree,Ext.util.Observable,{
		   
	init:function(){
		//创建tree的面板即TreePanel
		var tree = new Ext.tree.TreePanel({
			onlyLeafCheckable: false,//对树所有结点都可选
			checkModel: "cascade",
			title:'checkboxTree的练习',
			id:'extExample-tree-checkboxTree',//设置一个id
			autoScroll:true,//自动出现滚动条
			enableDD:false,//是否支持拖拽效果
			containerScroll: true,//是否支持滚动条
			rootVisible:true,//是否显示跟节点
			loader:new Ext.tree.TreeLoader({
				dataUrl:'modules/tree/checkboxTree.json',//异步读取的url
				baseAttrs: { uiProvider: Ext.tree.TreeCheckNodeUI } //加载继承组件
			}),
			tools:[{
				id:'refresh',//根据id的不同会出现不同的按钮
				handler:function(){
					var tree = Ext.getCmp('extExample-tree-checkboxTree');
					tree.root.reload();//让根节点重新加载
					tree.body.mask('数据加载中……', 'x-mask-loading');//给tree的body加上蒙版
					tree.root.expand(true,false,function(){
						tree.body.unmask();//全部展开之后让蒙版消失
					});
				}
			}]
		});

		var root = new Ext.tree.AsyncTreeNode({
			text:'木叶村',
			draggable:false,//是否可以拖拽
			id:'extExample-tree-checkboxTree-treeRoot'
		});
		
		tree.setRootNode(root);
		root.expand(true,false);
		//添加check事件
		tree.on("check",this.treeCheckAction,this);
		
		var myInput = new Ext.form.TextField({
			renderTo:'tree-checkboxTree-input',
			fieldLabel: '所选id',
			id:'myInput',
			name: 'name',
			width:	400						 
		});
		
		
		var mainPanel = new Ext.Panel({
			renderTo:'tree-checkboxTree-main',
			layout:'fit',
			width:160,
			height:270,
			items:[tree]
		});
	},//-----------------模块销毁函数---------------------------
	destroy:function(){
		
	}	,
	treeCheckAction:function(node,checked){
		var tree = Ext.getCmp('extExample-tree-checkboxTree');
		var str = tree.getChecked('id');
		var myInput = Ext.getCmp('myInput');
		myInput.setValue(str);
	}
});
