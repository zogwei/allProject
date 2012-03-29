处理中午乱码问题：
创建数据库前修改配置my.ini文件，然后创建：
default-character-set=utf8
default-character-set=utf8

tbl_order表增加如下字段：
deliveryDate varchar(50),
desription varchar(1000),

添加订单信息是添加<deliveryDate>元素
<operatorId><!-- 下单人Id--></operatorId>
<deliveryDate>2012-02-23</deliveryDate> //没有时空
<desription></desription> //没有时空

返回点单信息，增加了<deliveryDate>元素
<operateDate><!-- 下单日期 --></operateDate>
<deliveryDate>2012-02-23</deliveryDate> //没有时空
<desription></desription> //没有时空


返回单个订单，增加了<deliveryDate>元素
<operator><!-- 下单人姓名 --></operator>
<deliveryDate>2012-02-23</deliveryDate> //没有时空
<desription></desription> //没有时空



批量导入产品时，原来的第六列纹理去掉

tbl_vein 需要一条记录 id是1 veinName 是 vein
 insert into tbl_vein(veinName,description,isValid,tenantId)
 values('vein',null,1,2);
 
 
 
 
 ------
订单查询接口,要添加一个“客户名称”查询字段, 
<custName></custName>

客户端需要改的接口:
     1."添加客户","修改客户信息"接口,需要增加一个联系电话(直线)
        <telNum></telNum>修改和添加接口增加  
        
        code finished,no test
     
     
     2.需要新增一个"客户查询的接口",按客户名称,联系人,手机号码查询相关客户信息
       		就是客户修改的接口，只是指保留了 客户名称,联系人,手机号码 还要用户的id 这三个
       		
       		code finished,no test
     
     3.订单查询接口,要添加一个“客户名称”查询字段, 
     	<custName> 增加一个
     	
     	code finished, no test
     
     4.增加订单修改接口，凡是员工修改过的订单，得让管理员确认
                     参照订单新增接口，只是在<operatorId><!-- 下单人Id--></operatorId>后面增加一个
                       同级的<oldOrderId><!-- 修改的订单id--></oldOrderId>
         
        code finished,no test               
   
 后台服务端需要改:
      1.在产品入库跟入库信息修改时，增加一次密码重样输入的功能
        
      
      2.订单信息能批量导成的excel文档(excel文档所要的数据格式我在向厂家要)
        
      
      3.产品在被不同人员查看时显示不同的价格
          (现在一个产品主要有4种价格,进货价,批发价，零售价，建议销售价
		          进货价：是指工厂的进货价
		          批发价：是指工厂卖给商店的价格
		         零售价：是指商买给员工的价格
		         建议销售价：只是做为一个员工卖价的参考)
		         
      4.后台退货问题
                      只能按块退货,不足一块的退货不让其退货

 