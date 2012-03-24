// JavaScript Document
Ext.namespace('ExtExample.dataView');


Ext.DataView.DragSelector = function(cfg){
    cfg = cfg || {};
    var view, regions, proxy, tracker;
    var rs, bodyRegion, dragRegion = new Ext.lib.Region(0,0,0,0);
    var dragSafe = cfg.dragSafe === true;

    this.init = function(dataView){
        view = dataView;
        view.on('render', onRender);
    };

    function fillRegions(){
        rs = [];
        view.all.each(function(el){
            rs[rs.length] = el.getRegion();
        });
        bodyRegion = view.el.getRegion();
    }

    function cancelClick(){
        return false;
    }

    function onBeforeStart(e){
        return !dragSafe || e.target == view.el.dom;
    }

    function onStart(e){
        view.on('containerclick', cancelClick, view, {single:true});
        if(!proxy){
            proxy = view.el.createChild({cls:'x-view-selector'});
        }else{
            proxy.setDisplayed('block');
        }
        fillRegions();
        view.clearSelections();
    }

    function onDrag(e){
        var startXY = tracker.startXY;
        var xy = tracker.getXY();

        var x = Math.min(startXY[0], xy[0]);
        var y = Math.min(startXY[1], xy[1]);
        var w = Math.abs(startXY[0] - xy[0]);
        var h = Math.abs(startXY[1] - xy[1]);

        dragRegion.left = x;
        dragRegion.top = y;
        dragRegion.right = x+w;
        dragRegion.bottom = y+h;

        dragRegion.constrainTo(bodyRegion);
        proxy.setRegion(dragRegion);

        for(var i = 0, len = rs.length; i < len; i++){
            var r = rs[i], sel = dragRegion.intersect(r);
            if(sel && !r.selected){
                r.selected = true;
                view.select(i, true);
            }else if(!sel && r.selected){
                r.selected = false;
                view.deselect(i);
            }
        }
    }

    function onEnd(e){
        if(proxy){
            proxy.setDisplayed(false);
        }
    }

    function onRender(view){
        tracker = new Ext.dd.DragTracker({
            onBeforeStart: onBeforeStart,
            onStart: onStart,
            onDrag: onDrag,
            onEnd: onEnd
        });
        tracker.initEl(view.el);
    }
};


ExtExample.dataView.simpleDataView = function(){
	this.init();
} 

Ext.extend(ExtExample.dataView.simpleDataView,Ext.util.Observable,{
	
	
	init:function(){
		
		
		
		var store = new Ext.data.JsonStore({
			url: 'modules/dataView/simpleDataView.json',
			root: 'data',
			fields: [
				{name:'group'},
				{name:'users'}
			]
		});
		store.load();

		var tpl = new Ext.XTemplate(
			'<tpl for=".">',
				'<div>{group}</div>',
				'<tpl for="users">',
					'<div class="thumb-wrap" id="{name}">',
						'<div>武将：{name}</div>',
						'<span class="x-editable">{shortName}</span>',
					'</div>',
				'</tpl>',
			'</tpl>',
			'<div class="x-clear"></div>'
		);

		
		var dataView = new Ext.DataView({
			store: store,
			tpl: tpl,
			frame:true,
			autoHeight:true,
			autoScroll:true,
			multiSelect: true,
			overClass:'x-view-over',
			itemSelector:'div.thumb-wrap',
			emptyText: 'No images to display',
			
			listeners: {
            	selectionchange: {
            		fn: function(dv,nodes){
						
						//alert(nodes.id)
						var mainPanel = Ext.getCmp('dataView-simpleDataView-main-panel');
            			var l = nodes.length;
            			var s = l != 1 ? 's' : '';
            			mainPanel.setTitle('Simple DataView ('+l+' item'+s+' selected)');
            		}
            	}
            }
		})


		
		var mainPanel = new Ext.Panel({
			renderTo:'dataView-simpleDataView-main'	,
			id:'dataView-simpleDataView-main-panel',
			title:'简单的数据窗口',
			layout:'fit',
			width:640,
			height:320,
			autoScroll:true,
			items:[dataView]
		});
		
	},
	//-----------------模块销毁函数---------------------------
	destroy:function(){
		
	}
});