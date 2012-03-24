
Ext.namespace('ExtExample.tree');

ExtExample.tree.dragDoubleTree = function(){
	this.init();
};

Ext.extend(ExtExample.tree.dragDoubleTree,Ext.util.Observable,{

	init:function(){
		//创建树形1	
		var treeOne = new Ext.tree.TreePanel({
			el:'tree-dragDoubleTree-treeOne',
			id:'tree-dragDoubleTree-treeOne-panel',
			title:'树1',
			autoScroll:true,
			containerScroll:true,
			rootVisible:true,
			enableDrag: true,//只允许拖
			loader:new Ext.tree.TreeLoader({
				dataUrl:'modules/tree/dragDoubleTreeOne.json'
			}),
			tools:[{
				id:'refresh',//根据id的不同会出现不同的按钮
				handler:function(){
					var tree = Ext.getCmp('tree-dragDoubleTree-treeOne-panel');
					tree.root.reload();//让根节点重新加载
					tree.body.mask('数据加载中……', 'x-mask-loading');//给tree的body加上蒙版
					tree.root.expand(true,false,function(){
						tree.body.unmask();//全部展开之后让蒙版消失
					});
				}
			}]
		});
		
		var rootOne = new Ext.tree.AsyncTreeNode({
			id:'tree-dragDoubleTree-treeRootOne',
			text:'木叶中忍',
			draggable:false//是否可以拖拽
		});
		treeOne.setRootNode(rootOne);
		treeOne.render();
		rootOne.expand(true,false);
		
		//创建树形2	
		var treeTwo = new Ext.tree.TreePanel({
			el:'tree-dragDoubleTree-treeTwo',
			id:'tree-dragDoubleTree-treeTwo-panel',
			title:'树2',
			autoScroll:true,
			containerScroll:true,
			rootVisible:true,
			enableDrop: true,//只允许放置
			//enableDD:true,//即允许拖拽，又允许放置
			//dropConfig:{appendOnly:true},
			loader:new Ext.tree.TreeLoader({
				dataUrl:'modules/tree/dragDoubleTreeTwo.json'
			}),
			tools:[{
				id:'refresh',//根据id的不同会出现不同的按钮
				handler:function(){
					var tree = Ext.getCmp('tree-dragDoubleTree-treeTwo-panel');
					tree.root.reload();//让根节点重新加载
					tree.body.mask('数据加载中……', 'x-mask-loading');//给tree的body加上蒙版
					tree.root.expand(true,false,function(){
						tree.body.unmask();//全部展开之后让蒙版消失
					});
				}
			}]
		});
		
		var rootTwo = new Ext.tree.AsyncTreeNode({
			id:'tree-dragDoubleTree-treeRootTwo',
			text:'木叶三忍',
			draggable:false//是否可以拖拽
		});
		treeTwo.setRootNode(rootTwo);
		treeTwo.render();
		rootTwo.expand(true,false);
		
		
		/*
			为树2添加了nodedragover事件。
			目的在于能是树2的叶子节点能实现添加功能，原因是ext定义的过程中，tree的叶子节点在拖拽的过程中不允许添加其他节点。
				
		*/
		treeTwo.on("nodedragover", this.treeTwoNodedragover,this);
		treeTwo.on('nodedrop',this.treeTwoNodeDrop,this);
	},
	//-----------------模块销毁函数---------------------------
	destroy:function(){
		
	},
	treeTwoNodedragover:function(e){
		var n = e.target;
		var type = e.point;
		if(type=='append'){
			return true;
		}
		if (n.leaf) {
			n.leaf = false;
		}
		
		return false;
		
	},
	
	treeTwoNodeDrop:function(e){
		var curTree = e.tree;//得到当前的tree
		var tmpDropNode = e.dropNode;
		var tmpDropedNode = e.target;
		
		Ext.Ajax.request({
			url:'modules/tree/dragDoubleTree.php',
			method:'post',
			params:{begin:tmpDropNode.text,end:tmpDropedNode.text},
			success: function(response, option) {
				var result = response.responseText;
				alert(result);
			},
	
			failure: function(response, option) {
				//nodeEdited.setText(oldValue);//如果失败则将改过的节点恢复原状
				alert("异步通讯失败，请与管理员联系！");
			}
		});
	}
	
	
});