package com.cup.sample.createbean;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.xml.sax.SAXException;

import com.cup.createbean.XmlConfiguration;
import com.cup.createbean.util.Resource;
import com.cup.util.DomUtil;

public class CreatebeanTest {
	private static final com.cup.log.logging.Log log=
	        com.cup.log.logging.LogFactory.getLog( CreatebeanTest.class );

	public static void main(String[] args) {
		 XmlConfiguration last = null;
		 
		 String[] myarg = {"E:/myDoc/project2/CUP/src/com/cup/sample/createbean/testBean.properties",
				            "E:/myDoc/project2/CUP/src/com/cup/sample/createbean/sample1.xml" };
		 args = myarg;
		 
         Object[] obj = new Object[myarg.length];
         
         Properties properties = new Properties();
		
		try {
			
			for (String arg : args)
            {
                if (arg.toLowerCase(Locale.ENGLISH).endsWith(".properties"))
//                	//E:\myDoc\project2\CUP\testBean.properties 
                    properties.load(Resource.newResource(arg).getInputStream());
                	
            }
			
			for (int i = 0; i < args.length; i++)
            {
                if (!args[i].toLowerCase(Locale.ENGLISH).endsWith(".properties"))
                {
                    XmlConfiguration configuration = new XmlConfiguration(Resource.newResource(args[i]).getURL());
                    if (last != null)
                        configuration.getIdMap().putAll(last.getIdMap());
                    if (properties.size() > 0)
                    {
                        Map<String, String> props = new HashMap<>();
                        for (Object key : properties.keySet())
                        {
                            props.put(key.toString(),String.valueOf(properties.get(key)));
                        }
                        configuration.getProperties().putAll(props);
                    }
                    obj[i] = configuration.configure();
                    last = configuration;
                }
            }
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
