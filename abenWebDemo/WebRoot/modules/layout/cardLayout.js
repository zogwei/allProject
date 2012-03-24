
Ext.namespace('ExtExample.layout');

ExtExample.layout.cardLayout = function(){
	this.init();
};

Ext.extend(ExtExample.layout.cardLayout,Ext.util.Observable,{
	init:function(){
		
		var navHandler = function(direction){

			var wizard = Ext.getCmp('wizard').layout;
			var prev = Ext.getCmp('move-prev');
			var next = Ext.getCmp('move-next');
			var activeId = wizard.activeItem.id;
	
			if (activeId == 'card-0') {
				if (direction == 1) {
					wizard.setActiveItem(1);
					prev.setDisabled(false);
				}
			} else if (activeId == 'card-1') {
				if (direction == -1) {
					wizard.setActiveItem(0);
					prev.setDisabled(true);
				} else {
					wizard.setActiveItem(2);
					next.setDisabled(true);
				}
			} else if (activeId == 'card-2') {
				if (direction == -1) {
					wizard.setActiveItem(1);
					next.setDisabled(false);
				}
			}
		};
		
		
		var mainPanel = new Ext.Panel({
			renderTo:'layout-cardLayout-main',
			title:'card布局练习',
			id:'wizard',
			layout:'card',//在此设置为card布局
			activeItem: 0,//让向导页处于第一个位置
			width:400,
			height:300,
			bbar: [{
				id: 'move-prev',
				text: '上一步',
				handler: navHandler.createDelegate(this, [-1]),
				disabled: true
			}, '->',{
				id: 'move-next',
				handler: navHandler.createDelegate(this, [-1]),
				text: '下一步'
			}],
			items: [{
				id: 'card-0',
				html: '<h1>哈哈！<br />欢迎用咱的向导。</h1><p>第一步，一共三步</p>'
			},{
				id: 'card-1',
				html: '<p>第二步，一共三步</p>'
			},{
				id: 'card-2',
				html: '<h1>恭喜恭喜，完成了。</h1><p>第三步，一共三步，最后一步了。</p>'
			}]

		});
		
	},
	//-----------------模块销毁函数---------------------------
	destroy:function(){
		
	}

});
