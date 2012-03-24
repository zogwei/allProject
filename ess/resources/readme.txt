处理中午乱码问题：
创建数据库前修改配置my.ini文件，然后创建：
default-character-set=utf8
default-character-set=utf8

tbl_order表增加如下字段：
deliveryDate varchar(50),

添加订单信息是添加<deliveryDate>元素
<operatorId><!-- 下单人Id--></operatorId>
<deliveryDate>2012-02-23</deliveryDate> //没有时空

返回点单信息，增加了<deliveryDate>元素
<operateDate><!-- 下单日期 --></operateDate>
<deliveryDate>2012-02-23</deliveryDate> //没有时空


返回单个订单，增加了<deliveryDate>元素
<operator><!-- 下单人姓名 --></operator>
<deliveryDate>2012-02-23</deliveryDate> //没有时空