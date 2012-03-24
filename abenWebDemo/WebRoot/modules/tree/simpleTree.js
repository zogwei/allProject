//��ʼ�̳���ҳ���module��
Ext.namespace('ExtExample.tree');

ExtExample.tree.simpleTree = function(){
	this.init();
} 

Ext.extend(ExtExample.tree.simpleTree,Ext.util.Observable,{
	init:function(){
	
		//����tree����弴TreePanel
		var tree = new Ext.tree.TreePanel({
			
			title:'simpleTree��wϰ',
			id:'extExample-tree-simpleTree',//����һ��id
			autoScroll:true,//�Զ����ֹ���
			enableDD:false,//�Ƿ�֧����קЧ��
			containerScroll: true,//�Ƿ�֧�ֹ���
			rootVisible:true,//�Ƿ���ʾ��ڵ�
			//selModel:new Ext.tree.MultiSelectionModel(), 
			loader:new Ext.tree.TreeLoader({
				dataUrl:'modules/tree/simpleTree.json'//�첽��ȡ��url
			}),
			/*
				����tree����õ�һ��toolbar���������������ˢ�°�ť
			*/
			tools:[{
				id:'refresh',//���id�Ĳ�ͬ����ֲ�ͬ�İ�ť
				handler:function(){
					var tree = Ext.getCmp('extExample-tree-simpleTree');
					tree.root.reload();//�ø�ڵ����¼���
					tree.body.mask('��ݼ����С���', 'x-mask-loading');//��tree��body�����ɰ�
					tree.root.expand(true,false,function(){
						tree.body.unmask();//ȫ��չ��֮�����ɰ���ʧ
					});
				}
			}]
		});
		
		//������ڵ�
		var root = new Ext.tree.AsyncTreeNode({
			text:'ľҶ��',
			draggable:false,//�Ƿ������ק
			id:'extExample-tree-simpleTree-treeRoot'
		});
		
		//������ĸ�ڵ�
		tree.setRootNode(root);
		/*
			�ø�ڵ�չ���������������ֱ�Ϊ
			1.[Boolean deep]�������Ϊtrue��ȫ���ڵ�ȫ��չ��
			2.[Boolean anim]�������Ϊtrue����չ��ʱ����Ķ���Ч��
			3.[Function callback] һ��ص���ȫ��չ��֮����õ�
		*/
		root.expand(true,false);
		//��tree�Ľڵ���ӵ���¼�
		tree.on('click',this.treeClick,this);
		
		var mainPanel = new Ext.Panel({
			renderTo:'tree-simpleTree-main',
			layout:'fit',
			width:160,
			height:270,
			items:[tree]
		});
		
	},
	//-----------------ģ����ٺ���---------------------------
	destroy:function(){
		
	},
	//tree����¼�ʵ��,������¼��л���һ�����Ϊ���ǰ�ڵ�
	treeClick:function(node){
		var nodeId = node.id;
		var nodeText = node.text;
		alert("�ڵ��idΪ��"+nodeId+"--�ڵ������Ϊ��"+nodeText)
	}
	
});
