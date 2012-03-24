
//开始继承主页面的module类
Ext.namespace('ExtExample.tree');

ExtExample.tree.editNodeTree = function(){
	this.init();
} 

Ext.extend(ExtExample.tree.editNodeTree,Ext.util.Observable,{
	init:function(){
	
		//创建tree的面板即TreePanel
		var tree = new Ext.tree.TreePanel({
			
			title:'节点可直接编辑树的练习',
			id:'extExample-tree-editNodeTree',//设置一个id
			autoScroll:true,//自动出现滚动条
			enableDD:false,//是否支持拖拽效果
			containerScroll: true,//是否支持滚动条
			rootVisible:true,//是否显示跟节点
			loader:new Ext.tree.TreeLoader({
				dataUrl:'modules/tree/simpleTree.json'//异步读取的url
			}),
			tools:[{
				id:'refresh',
				handler:function(){
					var tree = Ext.getCmp('extExample-tree-editNodeTree');
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
			id:'extExample-tree-editNodeTree-treeRoot'
		});
		
		//设置树的根节点
		tree.setRootNode(root);

		root.expand(true,false);
		
		//给tree创建一个可修改的容器，即TreeEditor
		var treeEditer = new Ext.tree.TreeEditor(
			tree,//将tree组建的实例放入
			{
				allowBlank: false//输入的值不可以为空
			}
		);

		var mainPanel = new Ext.Panel({
			renderTo:'tree-editNodeTree-main',
			layout:'fit',
			width:250,
			height:270,
			items:[tree]
		});
		
		/*
			为创建的treeEditer添加事件
			有两个事件最为常用，一个为beforestartedit另一个为complete
			从名字就可以看出，beforestartedit事件是在编辑前的事件，因此可以通过它来判断那些节点可以编辑那些不可以。
			complete为编辑之后的事件，在这里面可以添加很多事件，比如添加一个Ext.Ajax向后台传送修改的值等等。
		*/
		treeEditer.on("beforestartedit", this.treeEditerBeforEdit,this);
		
		
		/*
			complete事件带有三个参数分别为
			1.Editor this 当前编辑组建即TreeEditor
			2.Mixed value 编辑之后的值
			3.Mixed startValue 编辑之前的值
		*/
    	treeEditer.on("complete",this.treeEditerComplete,this);
		
	},
	//-----------------模块销毁函数---------------------------
	destroy:function(){
		
	},
	treeEditerBeforEdit:function(treeEditer){
		var tempNode = treeEditer.editNode;//将要编辑的节点
		if(tempNode.isLeaf()){
			return true;
		}else{
			return false;	
		}
	},
	
	treeEditerComplete:function(treeEditer,newValue, oldValue){
		var nodeEdited = treeEditer.editNode;//得到编辑之后节点
		if(newValue==oldValue){
			return false;
		}else{
			//开始向后台传输数据并有editNodeTree.php进行处理
			Ext.Ajax.request({
				url: 'modules/tree/editNodeTree.php',
				method:'post',
				params:{empName:newValue,empOldName:oldValue},
				success: function(response, option) {
					var result = response.responseText;
					alert(result);
				},
	
				failure: function(response, option) {
					nodeEdited.setText(oldValue);//如果失败则将改过的节点恢复原状
					alert("异步通讯失败，请与管理员联系！");
				}
			});
		}
		
	}
	
});
