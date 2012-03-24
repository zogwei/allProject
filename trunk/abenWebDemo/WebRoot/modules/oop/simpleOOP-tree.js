Ext.namespace('ExtExampleOopSimpleApp');

/*
开始创建一个tree面板的实例，并且这个树形面板中有tbar
*/
ExtExampleOopSimpleAppMyTree = function(){
	/*
		由于是继承Ext.tree.TreePanel，那么必须
	*/
	ExtExampleOopSimpleAppMyTree.superclass.constructor.call(this,{
		title:'simpleTree的练习',
		id:'extExample-oop-simpleApp-tree',
		autoScroll:true,
		enableDD:false,
		containerScroll: true,
		rootVisible:true,
		root:new Ext.tree.AsyncTreeNode({
			text:'aaa'
		}),
		loader:new Ext.tree.TreeLoader({
			dataUrl:'modules/oop/simpleOOP.json'
		})
	})
};

Ext.extend(ExtExampleOopSimpleAppMyTree,Ext.tree.TreePanel,{
	selectFeed:function(){
		alert()
	}
});