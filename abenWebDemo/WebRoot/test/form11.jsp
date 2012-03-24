Ext.onReady(function() {

       Ext.QuickTips.init();// 浮动信息提示

       Ext.form.Field.prototype.msgTarget = 'side';// 设置控件的错误信息显示位置，主要可选的位置有：qtip,title,under,side,[elementId]

       Ext.BLANK_IMAGE_URL = 'resources/images/default/s.gif';// 替换图片文件地址为本地

       var simpleForm = new Ext.FormPanel({

              renderTo : document.body,

              labelAlign : 'left',

              title : '表单例子',

              buttonAlign : 'right',

              bodyStyle : 'padding:5px',

              width : 600,

              frame : true,

              labelWidth : 80,

              items : [{

                                   layout : 'column',// columnlayout

                                   border : false,// 没有边框

                                   labelSeparator : '：',// 标题的分隔符号我们用中文冒号代替英文的冒号（labelSeparator:'：'）。

                                   // coulmnLayout里的控件将定义在items里

                                   items : [{

                                                        columnWidth : .5,// 设置了该列的宽度占总宽度的50%（columnWidth:.5）

                                                        layout : 'form',// formlayout用来放置控件

                                                        border : false,// 没有边框

                                                        items : [{

                                                                             xtype : 'textfield',// 在formlayout里有一个类型为textfield'（xtype:'textfield'）的输入控件

                                                                             fieldLabel : '姓名',// 控件标题为姓名

                                                                             name : 'name',// 输入框（input）的name属性设置“name”

                                                                             anchor : '90%'// 输入框的长度为列宽减去标题的宽度后的90%（anchor:'90%'），余下的10%的是给显示错误信息图标用的。

                                                                      }]

                                                 }, {

                                                        columnWidth : .25,// 在加入性别的radio控件时就要注意了，这里需要加入两个radio，第一radio是有标题的，第二是没有的，而且两个radio加起来的宽度应该正好是余下的列宽（50%）

                                                        layout : 'form',

                                                        border : false,

                                                        items : [{

                                                                             style : 'margin-top:5px',// 设置一个css属性，顶部的外补丁为5px（style:'margin-top:5px'），原因是为了选择按钮和标题对齐，大家可以将该属性去掉然后看看效果。

                                                                             xtype : 'radio',// Formlayout里加入了一个类型为radio的控件

                                                                             fieldLabel : '性别',// 控件的标题是性别

                                                                             boxLabel : '男',// 控件的选择显示文本是男

                                                                             name : 'sex',// input的name属性值是sex

                                                                             checked : true,// 该控件默认是已选的

                                                                             inputValue : '男',// 控件的值（value）是男

                                                                             anchor : '95%'// input的宽度是95%

                                                                      }]

                                                 }, {

                                                        columnWidth : .25,// 我们已经设置了3列，3列的列宽分别为50%、25%、25%

                                                        layout : 'form',

                                                        labelWidth : 0,// 标题的宽度设置为0

                                                        labelSeparator : '',// 标题分隔符号为空

                                                        hideLabels : true,// 第二个raido控件的列设置就有所不同，因为它不需要标题，所以要设置隐藏标题

                                                        border : false,

                                                        items : [{

                                                                             style : 'margin-top:5px',

                                                                             xtype : 'radio',

                                                                             fieldLabel : '',

                                                                             boxLabel : '女',

                                                                             name : 'sex',

                                                                             inputValue : '女',

                                                                             anchor : '95%'

                                                                      }]

                                                 }]

                            }

                            // 上面是第一行,以下每行布局同上！

                            , {

                                   layout : 'column',

                                   border : false,

                                   labelSeparator : '：',

                                   items : [{

                                                        columnWidth : .5,

                                                        layout : 'form',

                                                        border : false,

                                                        items : [{

                                                                             xtype : 'datefield',// 控件的类型为datefield

                                                                             fieldLabel : '出生日期',

                                                                             name : 'birthday',

                                                                             anchor : '90%'

                                                                      }]

                                                 }, {

                                                        columnWidth : .5,

                                                        layout : 'form',

                                                        border : false,

                                                        items : [{

                                                               xtype : 'combo',// 控件的类型设置成combo

                                                               // 这里定义了一个sotre属性，就是选择值存储的地方，因为是在客户端的数据，所以使用了一个简单存储（SimpleStore）。

                                                               store : new Ext.data.SimpleStore({

                                                                      // 通过一个数组定义了returnValue和displayText两个字段。retrunValue字段指定是提交给后台的值，displayText字段指定是在下拉中显示的选择值。

                                                                      fields : ["returnValue", "displayText"],

                                                                      // 定义了几组数据.每组数据都是根据fiedls的定义来组成的，数组里第一个值就是retrunValue的值，第二个值就是displayText的值，例如[1,'小学']，就表示retrunValue是1，displayText是小学。

                                                                      data : [[1, '小学'], [2, '初中'],

                                                                                    [3, '高中'], [4, '中专'],

                                                                                    [5, '大专'], [6, '大学']]

                                                               }),

                                                               valueField : "returnValue",// 设置下拉选择框的值

                                                               displayField : "displayText",// 设置下拉选择框的显示文本

                                                               mode : 'local',// 数据是在本地

                                                               forceSelection : true,// 必须选择一个选项

                                                               blankText : '请选择学历',// 提交form时，该项如果没有选择，则提示错误信息"请选择学历"

                                                               emptyText : '选择学历',// 在没有选择值时显示为选择学历

                                                               hiddenName : 'education',// 大家要注意的是hiddenName和name属性，name只是下拉列表的名称，作用是可通过，而hiddenName才是提交到后台的input的name。如果没有设置hiddenName，在后台是接收不到数据的，这个大家一定要注意。

                                                               editable : false,// 该下拉列表只允许选择，不允许输入

                                                               triggerAction : 'all',// 因为这个下拉是只能选择的，所以一定要设置属性triggerAction为all，不然当你选择了某个选项后，你的下拉将只会出现匹配选项值文本的选择项，其它选择项是不会再显示了，这样你就不能更改其它选项了。

                                                               allowBlank : false,// 该选项值不允许为空

                                                               fieldLabel : '学历',// 控件的标题是学历

                                                               name : 'education',// 再次提醒，name只是下拉列表的名称

                                                               anchor : '90%'// input的宽度是90%

                                                        }]

                                                 }]

                            }

                            // 上面是第二行，下面我们要创建一个checkbox选项输入。checkbox的设置和radio的设置大同小异，大家注意列宽的定义就行。

                            , {

                                   layout : 'column',

                                   border : 'false',

                                   labelSeparator : '：',

                                   items : [{

                                                        columnWidth : .35,

                                                        layout : 'form',

                                                        border : false,

                                                        items : [{

                                                                             xtype : 'checkbox',

                                                                             fieldLabel : '权限组',

                                                                             boxLabel : '系统管理员',

                                                                             name : 'popedom',

                                                                             inputValue : '1',

                                                                             anchor : '95%'

                                                                      }]

                                                 }, {

                                                        columnWidth : .2,

                                                        layout : 'form',

                                                        labelWidth : 0,

                                                        labelSeparator : '',

                                                        hideLabels : true,

                                                        border : false,

                                                        items : [{

                                                                             xtype : 'checkbox',

                                                                             fieldLabel : '',

                                                                             boxLabel : '管理员',

                                                                             name : 'pepedom',

                                                                             inputValue : '2',

                                                                             anchor : '95%'

                                                                      }]

                                                 }, {

                                                        columnWidth : .2,

                                                        layout : 'form',

                                                        labelWidth : 0,

                                                        labelSeparator : '',

                                                        hideLabels : true,

                                                        border : false,

                                                        items : [{

                                                                             xtype : 'checkbox',

                                                                             fieldLabel : '',

                                                                             boxLabel : '普通用户',

                                                                             name : 'pepedom',

                                                                             inputValue : '3',

                                                                             anchor : '95%'

                                                                      }]

                                                 }, {

                                                        columnWidth : .25,

                                                        layout : 'form',

                                                        labelWidth : 0,

                                                        labelSeparator : '',

                                                        hideLabels : true,

                                                        border : false,

                                                        items : [{

                                                                             xtype : 'checkbox',

                                                                             fieldLabel : '',

                                                                             boxLabel : '访客',

                                                                             name : 'pepedom',

                                                                             inputValue : '4',

                                                                             anchor : '95%'

                                                                      }]

                                                 }]

 

                            }

                            // 上面是第三行，下面的两个输入框主要是测试通过vtypes属性来验证输入框的输入的

                            , {

                                   layout : 'column',

                                   border : false,

                                   labelSeparator : '：',

                                   items : [{

                                                        columnWidth : .5,

                                                        layout : 'form',

                                                        border : false,

                                                        items : [{

                                                                             xtype : 'textfield',

                                                                             fieldLabel : '电子邮件',

                                                                             name : 'email',

                                                                             vtype : 'email',// 这里的定义和普通的文本输入框没什么区别，只是多了一个vtypes的属性定义。Vtypes里总共定义了email、url、alpha和alphanum四种类型数据格式，email和url这个不用介绍了，呵呵。alpha是字母和下划线的组合，alphanum是字母、下划线和数字的组合。

                                                                             allowBlank : false,

                                                                             anchor : '90%'

                                                                      }]

                                                 }, {

                                                        columnWidth : .5,

                                                        layout : 'form',

                                                        border : false,

                                                        items : [{

                                                                             xtype : 'textfield',

                                                                             fieldLabel : '个人主页',

                                                                             name : 'url',

                                                                             vtype : 'url',

                                                                             anchor : '90%'

                                                                      }]

                                                 }]

                            }

                            // 上面是第四行,下面要加入一个tabpanel，加入3个tab页。

                            , {

                                   xtype : 'tabpanel',// 注意将xtype类型设置为'tabpanel'

                                   plain : true,// 将标签页头的背景设置为透明的

                                   activeTab : 0,// 当前活动的标签页是第一页

                                   height : 235,// 高度设置为235px

                                   defaults : {

                                          bodyStyle : 'padding:10px'

                                   },// tab页的面板使用内补丁10px

                                   items : [

                                                 // 第一页主要有两个输入控件，一个是vtypes类型alphanum的登录输入框和一个密码输入框。

                                                 {

                                          title : '登录信息',// 标签标题是登录信息

                                          layout : 'form',// 控件容器是formlayout

                                          defaults : {

                                                 width : 230

                                          },// 控件的默认宽度是230px

                                          defaultType : 'textfield',// 默认控件类型是textfield

 

                                          items : [{

                                                               fieldLabel : '登录名',

                                                               name : 'loginID',

                                                               allowBlank : false,

                                                               vtype : 'alphanum',

                                                               allowBlank : false

                                                        }, {

                                                               inputType : 'password',// 密码输入框需要定义input控件的类型为password

                                                               fieldLabel : '密码',

                                                               name : 'password',

                                                               allowBlank : false

                                                        }]

                                   },

                                                 // 第二个标签页里有numberfield、timefield和textfield三个控件

                                                 {

                                                        title : '数字时间字母',

                                                        layout : 'form',

                                                        defaults : {

                                                               width : 230

                                                        },

                                                        defaultType : 'textfield',

 

                                                        items : [{

                                                                             xtype : 'numberfield',// 只能输入数字的输入控件

                                                                             fieldLabel : '数字',

                                                                             name : 'number'

                                                                      }, {

                                                                             xtype : 'timefield',// 时间输入控件

                                                                             fieldLabel : '时间',

                                                                             name : 'time'

                                                                      }, {

                                                                             fieldLabel : '纯字母',

                                                                             name : 'char',

                                                                             vtype : 'alpha'

                                                                      }]

                                                 }, {

                                                        title : '文本区域',

                                                        layout : 'fit',// 为了让textarea自适应面板容器，使用了fitlayout作为它的布局

                                                        items : {

                                                               xtype : 'textarea',// 设置类型为textarea

                                                               id : 'area',

                                                               fieldLabel : ''

                                                        }

                                                 }]

                            }],

              // 为form添加按钮了，在formpanel的buttons属性中我们加入了一个保存按钮和取消按钮

              buttons : [{

                                   // 在buttons里定义的按钮默认是Ext.Button，所以按钮的属性定义可以查看Ext.Button的API。在这里两个按钮都没用到其它属性，只是设置了显示文本（text）和单击事件。

                                   text : '保存',

                                   handler : function() {

                                          // 在formpanel类中，form属性指向的是formpanle里的basicform对象，我们可通过formpanle.form来使用该basicform对象。在被例子，我们已经将formpanel对象赋值给了simpleForm这个变量，所以我们可以通过simpleForm.form访问面板里的basicform对象。

                                          if (simpleForm.form.isValid()) {

                                                 // 保存按钮要做的就是先做basicform的客户端验证，验证通过了则设置该按钮状态为disable，防止2次提交。然后调用simpleForm.form.doAction方法提交数据

                                                 this.disabled = true;

                                                 // doAction方法的第一个参数“submit”的意思是表示执行的是提交操作，提交的后台页面是test.jsp（url:'test.asp'），提交方式是post（method:'post'），没有其它提交参数（params:''）

                                                 simpleForm.form.doAction('submit', {

                                                                      url : 'resForm.jsp',

                                                                      method : 'post',

                                                                      params : '',

                                                                      // 提交成功后执行success定义的函数，后台返回的数据格式是需要我们注意的，一定要json格式，而且必须包含“success:true”，不然不会执行success定义的函数。

                                                                      // success定义的函数返回两个参数，第一是form本身，第二个是ajax返回的响应结果，在action.result这个json数组了保存了后台返回的数据。

                                                                      success : function(form, action) {

                                                                             // 例如返回的json结构是"{success:true,data:'成功保存！'}"，

                                                                             Ext.Msg.alert('操作',

                                                                                           action.result.data);

                                                                             this.disabled = false;

                                                                      },

                                                                      // 定义failure函数，就是网络通讯存在问题的时候将显示错误信息。

                                                                      failure : function() {

                                                                             Ext.Msg.alert('操作', '保存失败！');

                                                                             this.disabled = false;

                                                                      }

                                                               });

                                          }

                                          // 如果想form按以前的老办法提交，可以在formpanel的定义中加入一下设置：

                                          // onSubmit: Ext.emptyFn,

                                          // submit: function() {

                                          // this.getEl().dom.submit();}

                                          // 第一个设置的目的是取消formpanel的默认提交函数。第二就是设置新的提交方式为旧方式提交。

 

                                   }

                            }, {

                                   // 取消按钮就是简单的reset一下form的控件

                                   text : '取消',

                                   handler : function() {

                                          simpleForm.form.reset();

                                   }

                            }]

       });

 

});

 

再来看看前台HTML文件：

 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Insert title here</title>

<link rel="stylesheet" type="text/css" href="resources/css/ext-all.css" />

<script type="text/javascript" src="resources/js/ext-base.js"></script>

<script type="text/javascript" src="resources/js/ext-all.js"></script>

<script type="text/javascript" src="resources/js/ext-lang-zh_CN.js"></script>

<script type="text/javascript" src="resources/js/form.js"></script>

</head>

<body></body>

</html>

