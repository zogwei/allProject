Ext.namespace('ExtExample.tree');

ExtExample.tree.dragTree = function(){
	this.init();
};

Ext.extend(ExtExample.tree.dragTree,Ext.util.Observable,{
	
	init:function(){
		
		var tree = new Ext.tree.TreePanel({
			el:'tree-dragTree-treePanel',
			title:'tree节点拖拽练习',
			autoScroll:true,
			containerScroll:true,
			rootVisible:true,
			enableDD:true,
			loader:new Ext.tree.TreeLoader({
				dataUrl:'modules/tree/simpleTree.json'
			})
		});
		
		var root = new Ext.tree.AsyncTreeNode({
			id:'tree-dragTree-treeRoot',
			text:'木叶村',
			draggable:false//是否可以拖拽
		});
		
		
		tree.setRootNode(root);
		tree.render();
		root.expand(true,false);
		/*
			现在开始解析tree拖拽的事件。
			常用的事件有nodedrop以及nodedragover事件。这两个事件足以将拖拽功能完成。
			首先是nodedrop事件，它有一个参数对象e，这个对象还有三个很主要的属性，分别为：
			  1.dropNode为在拖动时鼠标抓住的节点
			  2.target为将要放置在某处的节点
			  3.point为被放置的状态，分别有append表示添加，above节点的上方，below节点的下方。
			其次是nodedragover事件，从名字上就可以看出，他是当节点被拖到某个节点上所执行的事件。
			它也有相同的参数对象e，该对象和的属性与nodedrop的e对象属性一致。
		*/
		tree.on('nodedrop',this.treeNodeDrop,this);
	},
	//-----------------模块销毁函数---------------------------
	destroy:function(){
		
	},
	/*nodedrop事件的实现函数，可以用ajax向后台传送数据。*/
	treeNodeDrop:function(e){
		var curTree = e.tree;//得到当前的tree
		var tmpDropNode = e.dropNode;
		var tmpDropedNode = e.target;
		var dropType = e.point;
		
		Ext.Ajax.request({
			url:'modules/tree/dragTree.php',
			method:'post',
			params:{begin:tmpDropNode.text,end:tmpDropedNode.text,type:dropType},
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
