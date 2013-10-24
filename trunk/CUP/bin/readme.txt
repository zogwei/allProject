--------------------------------------------------
、log
	来自
	1、
	2、
.1 依赖项目
	
	
.2 依赖jar
	
；
.3 包
	

--------------------------------------------------

1、digester
	来自tomcat7.0
1.1 依赖项目
	com.cup.util

1.2 依赖jar
	

1.3 包	
	com.cup.digester
	
--------------------------------------------------
2、jmx 
	来自tomcat7.0，
	
	默认情况下在被管理类的包中查找mbeans-descriptors.xml，使用digester将xml装换成对象树
	流程：
	  
	 1、mbeans-descriptors.xml 描述了被管理对象的元信息，包括属性，操作，通知等
     2、modeler的Registry加载这个文件获得元信息，生成ManagedBean，
     3、ManagedBean 通过参数为真正被管理对象的createMBean方法生成 DynamicMBean
     4、通过Registry的getMBeanServer()方法获得MBeanServer
     5、MBeanServer调用registerMBean ( mbean, oname)方法注册被管理的MBean
	
2.1 依赖项目：
    digester ，util，logging
2.2 依赖jar
	
2.3 包	
	com.cup.jmx.modeler
	com.cup.jmx.modeler.modules	
2.4 其他
	jmxtest中的web方法的管理 这些类才需要lib\jmx下的包
--------------------------------------------------
3、log
	来自Netty 4.0
	1、在JCL common-loging、slf4j、jul、log4j的基础上又封装了一层，可以任意选择那个实现
	2、优先slf4j，然后common-loging，最后jul，但由于common-loging一定有实现（最差情况就是自己common-loging自己的简单实现），故jul没用
	3、根据自己的需要添加lib\log下面的实现到classpath中，配置文件需要直接放到src下
	
3.1 依赖项目
	
	
3.2 依赖jar
	commons-logging-1.1.1.jar
	slf4j-api-1.7.4.jar
	
3.3 包
	com.cup.log.logging
	com.cup.log.logging.internal
	