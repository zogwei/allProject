Ext.namespace('ExtExample.tree');

ExtExample.tree.advancedTree01 = function(){
	this.init();
};

Ext.extend(ExtExample.tree.advancedTree01,Ext.util.Observable,{

	//树形的右键菜单

	treeRightMenu: new Ext.menu.Menu({
		id: 'theContextMenu',
		items:[{
				id:'addNode',
				text:'添加',
				menu:[
					{
						id:'insertNode',
						text:'添加兄弟节点'
					},
					{
						id:'appendNode',
						text:'添加孩子节点'
					}
				]
			},'-',{
				id:'delNode',
				text:'删除'
			},{
				id:'modifNode',
				text:'修改'
			},{
				id:'veiwNode',
				text:'查看'
			}]
	}),
	
	//树形面板
	tree:new Ext.tree.TreePanel({
		id:'tree-advancedTree01-tree',
		region:'west',
		autoScroll:true,
		containerScroll:true,
		loader:new Ext.tree.TreeLoader({
			dataUrl:'modules/tree/advancedTree01.json'
		}),
		enableDD:true,//是否支持拖拽效果
		rootVisible:true
	}),
	//根节点
	root : new Ext.tree.AsyncTreeNode({
		text:'木叶村',
		draggable:false,//是否可以拖拽
		id:'tree-advancedTree01-treeRoot'
	}),
	
	//树形编辑器
	treeEditer : new Ext.tree.TreeEditor(
		Ext.getCmp('tree-advancedTree01-tree'),//将tree组建的实例放入
		{
			id:'tree-advancedTree01-treeEditor',
			allowBlank: false//输入的值不可以为空
		}
	),
	
	//弹出窗口
	win: new Ext.Window({//创建弹出岗位的window容器
		maskDisabled:false,
		id:'tree-advancedTree01-win',
		modal : true,//是否为模式窗口
		constrain:true,//窗口只能在viewport指定的范围
		closable:true,//窗口是否可以关闭
		closeAction:'hide',
		//autoDestroy:true,
		layout:'fit',
		width:240,
		height:150,
		plain:true,
		items:[
			{
				id:'tree-advancedTree01-win-view',
				border:false
			}
		]
	}),

	init:function(){
		
		var topInfoPanel = new Ext.Panel({
			region:'north',
			height:150,
			split:true,
			border:false,
			minSize:2,
			maxSize:200,
			autoScroll:true,
			containerScroll:true,
			collapseMode:'mini',//在分割线处出现按钮
			contentEl:'tree-advancedTree01-info'
		});
		
		
		
		//本例的主角，TreePanel-----------------------------------
		
		this.tree.setRootNode(this.root);
		this.root.expand(true,false);
		
		//给tree添加事件
		this.tree.on('contextmenu',this.treeRightKeyAction,this);
		
		//****开始绑定右键菜单事件*************
		Ext.getCmp('insertNode').on('click',this.insertNodeAction,this);
		Ext.getCmp('appendNode').on('click',this.appendNodeAction,this);
		Ext.getCmp('delNode').on('click',this.delNodeAction,this);
		Ext.getCmp('modifNode').on('click',this.modifNodeAction,this);
		Ext.getCmp('veiwNode').on('click',this.veiwNodeAction,this);
		//--开始组装面板-----------------------------------------------------------
		var centerMainPanel = new Ext.Panel({
			region:'center',
			layout:'fit',
			contentEl:'tree-advancedTree01-center',
			border:false,
			items:[this.tree]
		});
		
		//创建例子的整体面板
		var mainPanel = new Ext.Panel({
			renderTo:'tree-advancedTree01-main',
			id:'tree-advancedTree01-mainPanel',
			layout:'border',
			height:480,
			items:[topInfoPanel,centerMainPanel]
		});
		
	},
	

	//-----------------模块销毁函数---------------------------
	destroy:function(){
		this.win.destroy();//将win窗口销毁，否则在IE中会报错
	},


	//右键弹出菜单事件
	treeRightKeyAction:function(node,e){
		e.preventDefault();
		node.select();
		this.treeRightMenu.showAt(e.getXY());
	},

	//添加兄弟节点事件实现
	insertNodeAction:function(){
		var treeEditor = this.treeEditer;//得到tree编辑器
		var tree = this.tree;//得到树形面板
		var selectedNode = tree.getSelectionModel().getSelectedNode();
		var selectedParentNode = selectedNode.parentNode;
		var newNode = new Ext.tree.TreeNode({
			text:'新建节点'+selectedNode.id
		});
		if(selectedParentNode==null){
			selectedNode.appendChild(newNode);
		}else{
			selectedParentNode.insertBefore(newNode,selectedNode);
		}

		setTimeout(function(){
			treeEditor.editNode = newNode;
			treeEditor.startEdit(newNode.ui.textNode);
		}, 10);
	},
	//添加孩子节点事件实现
	appendNodeAction:function(){
		var treeEditor = this.treeEditer;//得到tree编辑器
		var tree = this.tree;//得到树形面板
		var selectedNode = tree.getSelectionModel().getSelectedNode();
		if(selectedNode.isLeaf()){
			selectedNode.leaf = false;
		}				
		var newNode = selectedNode.appendChild(
			new Ext.tree.TreeNode({
				text:'新建节点'+selectedNode.id
			})
		);
		newNode.parentNode.expand(true,true,function(){
			tree.getSelectionModel().select(newNode);
			setTimeout(function(){
				treeEditor.editNode = newNode;
				treeEditor.startEdit(newNode.ui.textNode);
			}, 10);
		});//将上级树形展开
		
	},
	//删除节点事件实现
	delNodeAction:function(){
		var tree = this.tree;//得到树形面板
		var selectedNode = tree.getSelectionModel().getSelectedNode();//得到选中的节点
		selectedNode.remove();
	},
	//修改节点事件实现
	modifNodeAction:function(){
		var treeEditor = this.treeEditer;//得到tree编辑器
		var tree = this.tree;//得到树形面板
		var selectedNode = tree.getSelectionModel().getSelectedNode();//得到选中的节点
		treeEditor.editNode = selectedNode;
		treeEditor.startEdit(selectedNode.ui.textNode);
	},
	//查看事件实现
	veiwNodeAction:function(){
		var tree = this.tree;//得到树形面板
		var win = this.win;//得到弹出窗口组建
		var viewPanel = Ext.getCmp('tree-advancedTree01-win-view');
		var selectedNode = tree.getSelectionModel().getSelectedNode();//得到选中的节点
		var tmpid = selectedNode.attributes.id;
		var tmpname = selectedNode.attributes.text;
		var tmpdes = selectedNode.attributes.des;
		
		win.setTitle(tmpname+'的介绍');
		win.show();

		var dataObj = {
			id: tmpid,
			name: tmpname,
			des:tmpdes
		}
		var tmpTpl = new Ext.Template([
			'<div style="margin:10px"><div style="margin:10px">编号:{id}</div>',
			'<div style="margin:10px">名称:{name}</div>',
			'<div style="margin:10px">描述:{des}</div></div>'
		]);
		
		tmpTpl.overwrite(viewPanel.body, dataObj);
		
	}
});
