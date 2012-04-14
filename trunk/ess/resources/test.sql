use ess;

insert into tbl_tenant(tenantName,createdDate,isValid,isDefault,description) 
values('system',1324203964,1,1,'i am system tenant');
insert into tbl_tenant(tenantName,createdDate,isValid,isDefault,description) 
values('test',1324204014,1,2,'i am test tenant');

insert into tbl_employee(account,password,empName,sex,phone,address,cardNo,state,category,isValid,createdDate,description,tenantId) 
values('admin','admin','admin',1,null,null,null,1,1,1,1324204283,'i am system admin',1);

insert into tbl_employee(account,password,empName,sex,phone,address,cardNo,state,category,isValid,createdDate,description,tenantId) 
values('wanghao','wanghao','wanghao',1,13266688976,null,null,1,1,1,1324204465,'i am a sale man',2);

insert into tbl_supplier(supplierName,linkman,phone,address,isValid,createdDate,description,tenantId) 
values('jiangyizhao','jiangyizhao',13123456789,null,1,1324204465,'i am supplier jiangyizhao',2);

insert into tbl_customer(customerName,linkman,phone,telNum,address,isValid,createdDate,description,employeeId,tenantId) 
values('isoftstone','chenming','13545678910','3333','weixin',1,1324204954,'i am customer chenming',2,2);

insert into tbl_spec(specName,description,isValid,tenantId) 
values('900*50*18',null,1,2);
insert into tbl_spec(specName,description,isValid,tenantId) 
values('900*65*18',null,1,2);
insert into tbl_spec(specName,description,isValid,tenantId) 
values('900*70*18',null,1,2);
insert into tbl_floor_category(categoryName,description,isValid,tenantId)
 values('floorCategory1',null,1,2);
insert into tbl_floor_category(categoryName,description,isValid,tenantId)
 values('floorCategory2',null,1,2);
 
 insert into tbl_vein(veinName,description,isValid,tenantId)
 values('vein1',null,1,2);
 insert into tbl_vein(veinName,description,isValid,tenantId)
 values('vein2',null,1,2);
 insert into tbl_vein(veinName,description,isValid,tenantId)
 values('vein3',null,1,2);
 
 insert into tbl_color_code(colorCodeName,description,isValid,tenantId)
 values('ColorCode1',null,1,2);
 insert into tbl_color_code(colorCodeName,description,isValid,tenantId)
 values("ColorCode2",null,1,2);
 
 insert into tbl_floor(number,floorName,supplierId,categoryId,specId,colorCodeId,veinId,sellPrice,bookPrice,amountPrice,detailPrice,createdDate,isValid,description,tenantId) 
 values('20001','floor name20001',1,1,1,1,1,55,200,200,200,1324206999,1,null,2);
  insert into tbl_floor(number,floorName,supplierId,categoryId,specId,colorCodeId,veinId,sellPrice,bookPrice,amountPrice,detailPrice,createdDate,isValid,description,tenantId) 
 values('20002','floor name20002',1,1,2,1,2,34,200,200,200,1324207139,1,null,2);
  insert into tbl_floor(number,floorName,supplierId,categoryId,specId,colorCodeId,veinId,sellPrice,bookPrice,amountPrice,detailPrice,createdDate,isValid,description,tenantId) 
 values('20003','floor name20003',1,2,1,2,2,108,200,200,200,1324207151,1,null,2);
  insert into tbl_floor(number,floorName,supplierId,categoryId,specId,colorCodeId,veinId,sellPrice,bookPrice,amountPrice,detailPrice,createdDate,isValid,description,tenantId) 
 values('20004','floor name20004',1,2,2,1,1,79,200,200,200,1324207163,1,null,2);
  insert into tbl_floor(number,floorName,supplierId,categoryId,specId,colorCodeId,veinId,sellPrice,bookPrice,amountPrice,detailPrice,createdDate,isValid,description,tenantId) 
 values('20005','floor name20005',1,1,1,1,2,86,200,200,200,1324207173,1,null,2);



 
 insert into tbl_storage (floorId,area,count,tenantId) values(1,9990,1078920,2);
 insert into tbl_storage (floorId,area,count,tenantId) values(2,9990,789210,2);
 insert into tbl_storage (floorId,area,count,tenantId) values(3,9990,859140,2);
 insert into tbl_storage (floorId,area,count,tenantId) values(4,10000,790000,2);
 insert into tbl_storage (floorId,area,count,tenantId) values(5,10000,860000,2);
 

insert into tbl_order(orderNo,customerId,operatorId,isValid,amount,imprest,refund,received,currentState,operateDate,deliveryDate,desription,tenantId)
  values('dd201112222318',1,1,1,1688,100,0,0,1,1324370008,1324370008,'',2);
insert into tbl_order(orderNo,customerId,operatorId,isValid,amount,imprest,refund,received,currentState,operateDate,deliveryDate,desription,tenantId)
  values('dd201112222386',1,2,1,2730,100,0,2730,2,1324370008,1324370008,'',2);
insert into tbl_order(orderNo,customerId,operatorId,isValid,amount,imprest,refund,received,currentState,operateDate,deliveryDate,desription,tenantId)
  values('dd201112222389',1,2,1,2160,100,0,0,3,1324370008,1324370008,'',2);

insert into tbl_in_storage(floorId,length,width,quantity,area,price,count,operator,isValid,createdDate,description,tenantId)
values(1,1,1,10000,10000,108,1080000,2,1,1324370008,null,2);
insert into tbl_in_storage(floorId,length,width,quantity,area,price,count,operator,isValid,createdDate,description,tenantId)
values(2,1,1,10000,10000,79,790000,2,1,1324370055,null,2);
insert into tbl_in_storage(floorId,length,width,quantity,area,price,count,operator,isValid,createdDate,description,tenantId)
values(3,1,1,10000,10000,86,860000,2,1,1324370066,null,2);
insert into tbl_in_storage(floorId,length,width,quantity,area,price,count,operator,isValid,createdDate,description,tenantId)
values(4,1,1,10000,10000,79,790000,2,1,1324370077,null,2);
insert into tbl_in_storage(floorId,length,width,quantity,area,price,count,operator,isValid,createdDate,description,tenantId)
values(5,1,1,10000,10000,86,860000,2,1,1324370088,null,2);

insert into tbl_order_item(orderId,floorId,sellPrice,area,amount)
values(1,1,108,5,540);
insert into tbl_order_item(orderId,floorId,sellPrice,area,amount)
values(1,2,79,8,632);
insert into tbl_order_item(orderId,floorId,sellPrice,area,amount)
values(1,3,86,6,516);

insert into tbl_order_item(orderId,floorId,sellPrice,area,amount)
values(2,1,108,10,1080);
insert into tbl_order_item(orderId,floorId,sellPrice,area,amount)
values(2,2,79,10,790);
insert into tbl_order_item(orderId,floorId,sellPrice,area,amount)
values(2,3,86,10,860);

insert into tbl_order_item(orderId,floorId,sellPrice,area,amount)
values(3,1,108,20,2160);


insert into tbl_order_state_trace(stateId,operateDate,orderId,description)
values(1,1324350278,1,null);

insert into tbl_order_state_trace(stateId,operateDate,orderId,description)
values(1,1324350600,2,null);
insert into tbl_order_state_trace(stateId,operateDate,orderId,description)
values(2,1324350699,2,null);

insert into tbl_order_state_trace(stateId,operateDate,orderId,description)
values(1,1324350744,3,null);
insert into tbl_order_state_trace(stateId,operateDate,orderId,description)
values(2,1324350888,3,null);
insert into tbl_order_state_trace(stateId,operateDate,orderId,description)
values(3,1324350999,3,null);

 insert into tbl_floor_picpath (picPath,floorId) values('1.jpg',1);
 insert into tbl_floor_picpath (picPath,floorId) values('2.jpg',1);
 insert into tbl_floor_picpath (picPath,floorId) values('3.jpg',1);
 	
 
 
 insert into tbl_daily_sales_stats(tenantId,employeeId,salesAmount,salesDate)
 values(2,2,2730,1324310400);
 insert into tbl_daily_sales_stats(tenantId,employeeId,salesAmount,salesDate)
 values(2,2,2730,1324370008);
 
insert into tbl_monthly_sales_stats(tenantId,employeeId,salesAmount,salesDate)
 values(2,2,2730,1324310400);
 
 insert into tbl_storage_info(floorId,countInStorage,countOrder,countOrderCancel)
 values(1,1,2,1);
 insert into tbl_storage_info(floorId,countInStorage,countOrder,countOrderCancel)
 values(2,1,1,0);
 insert into tbl_storage_info(floorId,countInStorage,countOrder,countOrderCancel)
 values(3,1,1,0);
 insert into tbl_storage_info(floorId,countInStorage,countOrder,countOrderCancel)
 values(4,1,0,0);
 insert into tbl_storage_info(floorId,countInStorage,countOrder,countOrderCancel)
 values(5,1,0,0);
 