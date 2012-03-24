
//开始继承主页面的module类
Ext.namespace('ExtExample.tree');

ExtExample.tree.asynchronousTree = function(){
	this.init();
} 

Ext.extend(ExtExample.tree.asynchronousTree,Ext.util.Observable,{
	init:function(){
	
		//创建tree的面板即TreePanel
		var tree = new Ext.tree.TreePanel({
			
			title:'异步加载树的练习',
			id:'extExample-tree-asynchronousTree',//设置一个id
			autoScroll:true,//自动出现滚动条
			enableDD:false,//是否支持拖拽效果
			containerScroll: true,//是否支持滚动条
			rootVisible:true,//是否显示跟节点
			loader:new Ext.tree.TreeLoader({
				dataUrl:'modules/tree/asynchronousTree.php?id='//异步读取的url
			}),
			/*
				这是tree中最常用的一个toolbar，比如在上面添加刷新按钮
			*/
			tools:[{
				id:'refresh',//根据id的不同会出现不同的按钮
				handler:function(){
					var tree = Ext.getCmp('extExample-tree-asynchronousTree');
					tree.loader.dataUrl = "modules/tree/asynchronousTree.php"; 
					tree.root.reload();//让根节点重新加载
					tree.body.mask('数据加载中……', 'x-mask-loading');//给tree的body加上蒙版
					tree.root.expand(true,false,function(){
						tree.body.unmask();//全部展开之后让蒙版消失
					});
				}
			}]
		});
		
		//创建根节点
		var root = new Ext.tree.AsyncTreeNode({
			text:'木叶村',
			draggable:false,//是否可以拖拽
			id:'extExample-tree-asynchronousTree-treeRoot'
		});
		
		//设置树的根节点
		tree.setRootNode(root);
		/*
			让根节点展开这个函数有三个参数分别为
			1.[Boolean deep]如果设置为true则将全部节点全部展开
			2.[Boolean anim]如果设置为true则在展开时伴随的动画效果
			3.[Function callback] 一个回调函数，当全部展开之后调用的
		*/
		root.expand();
		
		
		//给tree的节点添加点击事件
		tree.on('click',this.treeClick,this);
		
		
		//在更改beforload事件
		tree.on('beforeload',this.treeBeforeload,this);
		
		
		
		var mainPanel = new Ext.Panel({
			renderTo:'tree-asynchronousTree-main',
			layout:'fit',
			width:160,
			height:270,
			items:[tree]
		});
		
	},
	
	//-----------------模块销毁函数---------------------------
	destroy:function(){
		
	},

	//tree点击事件实现,在这个事件中会有一个参数，为点击当前节点
	treeClick:function(node){
		if(!node.isLeaf()){
			node.expand();
		}
	},
	
	
	//当重新加载时更改读取的内容
	treeBeforeload:function(node){
		var tree = Ext.getCmp('extExample-tree-asynchronousTree');
		var treeid = node.id;
		if(treeid!='extExample-tree-asynchronousTree-treeRoot'){
			var url = 'modules/tree/asynchronousTree.php?id='+treeid;
			//在load之前重新封装dataUrl
			tree.loader.dataUrl = url; 
		} 
	}
	
});
