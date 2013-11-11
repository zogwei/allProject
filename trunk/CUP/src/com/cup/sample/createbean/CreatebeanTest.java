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

public class CreatebeanTest {

	public static void main(String[] args) {
		 XmlConfiguration last = null;
         Object[] obj = new Object[args.length];
         
         Properties properties = null;
		
		try {
			
			for (String arg : args)
            {
                if (arg.toLowerCase(Locale.ENGLISH).endsWith(".properties"))
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
