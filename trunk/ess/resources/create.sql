drop database if exists ess;
create database ess;

use ess;

drop table if exists tbl_tenant;
create table tbl_tenant
(
	id int not null AUTO_INCREMENT,
	tenantName varchar(96) not null,
	createdDate int not null,
	isValid tinyint not null,
	isDefault tinyint not null,
	description varchar(256),
	primary key(id)
);

drop table if exists tbl_customer;
create table tbl_customer
(
	id int not null AUTO_INCREMENT,
	customerName varchar(64) not null,
	linkman varchar(64),
	phone varchar(50),
	telNum varchar(50),
	address varchar(256),
	isValid tinyint not null,
	createdDate int not null,
	description varchar(256),
	tenantId int not null,
	employeeId int not null,
	primary key(id)
);

drop table if exists tbl_employee;
create table tbl_employee
(
	id int not null AUTO_INCREMENT,
	account varchar(30) not null,
	password varchar(32) not null,
	empName varchar(64) not null,
	sex tinyint,
	phone varchar(50),
	address varchar(256),
	cardNo varchar(96),
	state tinyint,
	category int not null,
	isValid tinyint not null,
	createdDate int not null,
	description varchar(256),
	tenantId int not null,
	primary key(id)
);

drop table if exists tbl_supplier;
create table tbl_supplier
(
	id int not null AUTO_INCREMENT,
	supplierName varchar(64) not null,
	linkman varchar(64),
	phone varchar(50),
	address varchar(256),
	isValid tinyint not null,
	createdDate int not null,
	description varchar(256),
	tenantId int not null,
	primary key(id)
);

drop table if exists tbl_spec;
create table tbl_spec
(
	id int not null AUTO_INCREMENT,
	specName varchar(64) not null,
	description varchar(256),
	isValid tinyint not null,
	tenantId int not null,
	primary key(id)
);

drop table if exists tbl_floor_category;
create table tbl_floor_category
(
	id int not null AUTO_INCREMENT,
	categoryName varchar(96) not null,
	description varchar(256),
	isValid tinyint not null,
	tenantId int not null,
	primary key(id)
);

drop table if exists tbl_color_code;
create table tbl_color_code
(
	id int not null AUTO_INCREMENT,
	colorCodeName varchar(96) not null,
	description varchar(256),
	isValid tinyint not null,
	tenantId int not null,
	primary key(id)
);

drop table if exists tbl_vein;
create table tbl_vein
(
	id int not null AUTO_INCREMENT,
	veinName varchar(96) not null,
	description varchar(256),
	isValid tinyint not null,
	tenantId int not null,
	primary key(id)
);

drop table if exists tbl_floor;
create table tbl_floor
(
	id int not null AUTO_INCREMENT,
	number varchar(50) not null,
	floorName varchar(96) not null,
	supplierId int,
	categoryId int not null,
	specId int not null,
	colorCodeId int not null,
	veinId int not null,
	sellPrice decimal(16,4) not null,
	createdDate int not null,
	isValid tinyint not null,
	description varchar(256),
	tenantId int not null,
	primary key(id)
);

drop table if exists tbl_in_storage;
create table tbl_in_storage
(
	id int not null AUTO_INCREMENT,
	floorId int not null,
	length decimal(16,4) not null,
	width decimal(16,4) not null,
	quantity int not null,
	area decimal(16,4) not null,
	price decimal(10,4) not null,
	count decimal(16,4) not null,
	operator varchar(64),
	isValid tinyint not null,
	createdDate int not null,
	description varchar(256),
	tenantId int not null,
	primary key(id)
);

drop table if exists tbl_storage;
create table tbl_storage
(
	id int not null AUTO_INCREMENT,
	floorId int not null,
	area decimal(16,4) not null,
	count decimal(16,4) not null,
	tenantId int not null,
	primary key(id)
);

drop table if exists tbl_order;
create table tbl_order
(
	id int not null AUTO_INCREMENT,
	orderNo varchar(50) not null,
	customerId int not null,
	operatorId int not null,
	amount decimal(16,4) not null,
	imprest decimal(16,4),
	refund decimal(16,4),
	received decimal(16,4),
	currentState int not null,
	operateDate int not null,
	deliveryDate varchar(50),
	isValid tinyint not null,
	tenantId int not null,
	desription varchar(1000),
	primary key(id)
);

drop table if exists tbl_order_update;
create table tbl_order_update
(
	id int not null AUTO_INCREMENT,
	newOrderNo varchar(50) not null,
	oldOrderNo varchar(50) not null,
	operatorId int not null,
	currentState int not null,
	status varchar(10) ,
	primary key(id)
);

drop table if exists tbl_order_item;
create table tbl_order_item
(
	id int not null AUTO_INCREMENT,
	orderId int not null,
	floorId int not null,
	sellPrice decimal(16,4) not null,
	area decimal(16,4) not null,
	amount decimal(16,4) not null,
	primary key(id)
);

drop table if exists tbl_order_state_trace;
create table tbl_order_state_trace
(
	stateId int not null,
	operateDate int not null,
	orderId int not null,
	description varchar(256)
);

drop table if exists tbl_floor_picpath;
create table tbl_floor_picpath
(
	id int not null auto_increment,
	picPath varchar(96) not null,
	floorId int not null,
	primary key(id)
);

drop table if exists tbl_daily_sales_stats;
create table tbl_daily_sales_stats
( 
   tenantId int not null, 
   employeeId int not null, 
   salesAmount decimal(16,4) not null, 
   salesDate int not null 
);

drop table if exists tbl_monthly_sales_stats; 
create table tbl_monthly_sales_stats 
( 
   tenantId int not null, 
   employeeId int not null, 
   salesAmount decimal(16,4) not null, 
   salesDate int not null 
);

drop table if exists tbl_storage_info;
create table tbl_storage_info
(
   floorId int not null,
   countInStorage int,
   countOrder int,
   countOrderCancel int
);

use ess;

commit;