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
 