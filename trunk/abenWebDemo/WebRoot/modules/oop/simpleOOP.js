
ExtExampleOopSimpleApp = function(){
	this.tree = new ExtExampleOopSimpleAppMyTree();
	this.initApp();
}
	
Ext.extend(ExtExampleOopSimpleApp,Ext.util.Observable,{
	
	tree:null,

	initApp:function(){
		
		var tree = this.tree;

		var mainPanel = new Ext.Panel({
			renderTo:'oop-simpleOOP-main',
			layout:'fit',
			width:160,
			height:270,
			items:[tree]
		});
		
	},
	destroy:function(){
		
	}
});




