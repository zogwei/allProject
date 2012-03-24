// ext实例展示的js文件
Ext.BLANK_IMAGE_URL = '/AbenDemo/images/public/s.gif"';
Ext.QuickTips.init();// 加载快速提示框
Ext.namespace('ExtExample');// 创建名字域
// 应用程序主页面
ExtExample.app = function() {
	// 空函数
};
Ext.extend(ExtExample.app, Ext.util.Observable, {
	// 顶端
	header : new Ext.BoxComponent({
				region : 'north',
				el : 'north',
				height : 30,
				margins : '5'
			}),
	// 低端
	footer : new Ext.BoxComponent({
				region : 'south',
				el : 'south',
				height : 25
			}),

	// 左边菜单树形
	menuTree : new Ext.tree.TreePanel({
				title : '系统菜单',
				region : 'west',
				id : 'extExample-tree',
				autoScroll : true,
				enableDD : false,// 是否支持拖拽效果
				containerScroll : true,// 是否支持滚动条
				split : true,
				width : 180,
				minSize : 175,
				maxSize : 300,
				rootVisible : true,// 是否显示跟节点
				// collapseMode:'mini',//在分割线处出现按钮
				collapsible : true,
				margins : '0 0 5 5',
				loader : new Ext.tree.TreeLoader({
							dataUrl : '/AbenDemo/js/frame/menutree.json'
						}),
				tools : [{
							id : 'refresh',
							handler : function() {
								var tree = Ext.getCmp('extExample-tree');
								tree.root.reload();
							}
						}]
			}),
	// 菜单根节点
	menuRoot : new Ext.tree.AsyncTreeNode({
				text : 'ABenWebDemo系统',
				draggable : false,
				id : 'source'
			}),

	// 中间主要显示区
	main : new Ext.TabPanel({
				region : 'center',
				enableTabScroll : true,
				activeTab : 0,
				margins : '0 5 5 0',
				items : [new Ext.Panel({
							id : "workPing",
							title : "首页",
							border : false,
							autoLoad : '/AbenDemo/mainInfo.jsp'
						})]
			}),

	// 初始化构造函数
	init : function() {

		this.menuTree.setRootNode(this.menuRoot);
		this.menuRoot.expand();

		// 给树形菜单添加事件
		this.menuTree.on('click', this.menuClickAction, this);

		// 给main的tab页面添加tabchange事件
		this.main.on('tabchange', this.changeTab, this);

		var myView = new Ext.Viewport({
					layout : 'border',
					border : false,
					items : [this.header, this.main, this.footer, this.menuTree]
				});

		// 新建一个mask
		this.loadMask = new Ext.LoadMask(this.main.body, {
					msg : "页面加载中.请稍等……"
				});

	},
	// 属性菜单的点击事件
	menuClickAction : function(node) {
		if (!node.isLeaf()) {
			return false;
		}
		// alert(node);
		var tabId = 'tab-' + node.id;
		var tabTitle = node.text;
		var tablink = node.attributes.link;
		var tabJsArray = node.attributes.jsArray;// 得到js文件存放的路径
		var tab = this.main.getComponent(tabId);// 得到tab组建

		if (!tab) {
			tab = this.main.add(new Ext.Panel({
						id : tabId,
						title : tabTitle,
						autoScroll : true,
						layout : 'fit',
						border : false,
						closable : true
					}));

			this.main.setActiveTab(tab);

			var loadmask = this.loadMask;

			var model;// 加载模块js预置变量名

			loadmask.show();
			// panel组建开始异步加载url的html
			tab.load({
				url : tablink,// 加载的url
				// 加载html成功后的回调函数
				callback : function(a, b, c) {
					if (b == false) {
						Ext.Msg.show({
									title : "错误信息",
									msg : "加载页面超时或是页面连接不正确！",
									buttons : Ext.MessageBox.OK,
									icon : Ext.MessageBox.ERROR
								});
					}
					loadmask.hide();
				},
				discardUrl : false,
				nocache : true,
				text : "",
				timeout : 3000,// 超时时间30ms
				scripts : true
			});
		} else {
			this.main.setActiveTab(tab);
		}
	},

	// change的实现
	changeTab : function(tab, newtab) {
		// 如果存在相应树节点，就选中,否则就清空选择状态
		var nodeId = newtab.id.replace('tab-', '');
		var node = this.menuTree.getNodeById(nodeId);
		if (node) {
			this.menuTree.getSelectionModel().select(node);
		} else {
			this.menuTree.getSelectionModel().clearSelections();
		}
	}

});

Ext.onReady(function() {
			var extExample = new ExtExample.app();

			extExample.init();
		});