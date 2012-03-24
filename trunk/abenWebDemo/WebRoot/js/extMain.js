// ext实例展示的js文件
Ext.BLANK_IMAGE_URL = 'images/public/s.gif"';
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

	// 实例菜单树形
	menuTree : new Ext.tree.TreePanel({
				title : '实例菜单',
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
							dataUrl : 'extmenutree.json'
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
				text : 'ExtJs2.0 实例展示',
				draggable : false,
				id : 'source'
			}),

	// 主要显示区
	main : new Ext.TabPanel({
				region : 'center',
				enableTabScroll : true,
				activeTab : 0,
				margins : '0 5 5 0',
				items : [new Ext.Panel({
							id : "workPing",
							title : "首页",
							border : false,
							autoLoad : 'info.html'
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
					msg : "页面加载中……"
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
					var jsStr = "";// 创建一个空字符串，用来装载接受的js文件内容
					var num = tabJsArray.length;
					for (var i = 0; i < num; i++) {
						// 由于异步加载的html不能以<script
						// src=xxx.js>的方式加载javascript所以在此再调用一个ajax异步加载模块的js文件
						var tabjs = tabJsArray[i].js;// 取得数组中的js文件
						var currentIndex = i;// 获取当前js文件的索引
						Ext.Ajax.request({
									method : 'POST',// 为了不丢失js文件内容，所以要选择post的方式加载js文件
									url : tabJsArray[i].js,
									scope : this,
									success : function(response) {

										jsStr += response.responseText;// 把每次加载的内容都存入jsStr中

										if (currentIndex == num - 1) {// 如果当前索引号为最后一个时开始创建应用的实例
											// 获取模块类
											this[node.id] = eval(jsStr);
											// 实例化模块类
											model = new this[node.id]();
											loadmask.hide();
										}

										// 之所以要重写tabpanel的destroy函数，是因为在要执行每个实例类的自定义的destroy函数。
										// 原因在于，用IE在有些情况下不能完全释放实例。
										// 比如“树的高级应用（一）”实例中的window对象，如果该对象不执行destroy函数时再此打开时会报错。

										tab.on('destroy', function() {
													model.destroy();
												});
									},
									failure : function(response) {
										Ext.Msg.show({
													title : "错误信息",
													msg : "加载页面核心文件时发生错误！",
													buttons : Ext.MessageBox.OK,
													icon : Ext.MessageBox.ERROR
												});
										loadmask.hide();
									}
								});
					}
					if (b == false) {
						Ext.Msg.show({
									title : "错误信息",
									msg : "加载页面超时或是页面连接不正确！",
									buttons : Ext.MessageBox.OK,
									icon : Ext.MessageBox.ERROR
								});
						loadmask.hide();
					}
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