package com.cup.sample.jmx;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import com.cup.jmx.modeler.Registry;
import com.cup.log.logging.Log;
import com.cup.log.logging.LogFactory;

public class JmxTest {

	 private static final Log log = LogFactory.getLog(JmxTest.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ObjectName on = null;

        try {

        	MBeanServer server = Registry.getRegistry(null, null).getMBeanServer();

        	on = new ObjectName("domianName" + ":name=router");
            Registry.getRegistry(null, null).registerComponent(new Router(), on, null);

            //web方法的管理 这些类才需要lib\jmx下的包
            com.sun.jdmk.comm.HtmlAdaptorServer adaptor = new com.sun.jdmk.comm.HtmlAdaptorServer();     
            // 向 Bean Server 注册适配器对象               
            server.registerMBean(adaptor,new ObjectName("adaptor:protocol=HTTP"));
            // 设置并启动 HTTP 适配器服务            
            adaptor.setPort(8888);                 
            adaptor.start();  

        } catch (MalformedObjectNameException e) {
            log.warn(" mbean error!");
        } catch (Exception e) {
            log.warn("mbean error!");
        }
		
	}

}
