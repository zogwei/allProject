package com.cup.sample.digester;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

import com.cup.digester.Digester;
import com.cup.digester.SetNextRule;
import com.cup.log.logging.Log;
import com.cup.log.logging.LogFactory;
import com.cup.sample.digester.bean.DataSources;


public class DigesterTest {

	 private static final Log log = LogFactory.getLog(DigesterTest.class);
	 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DigesterTest test = new DigesterTest();
		DataSources temp = test.getDataSources();
		log.debug(temp.toString());
	}
	
	private DataSources getDataSources(){
		DataSources obj = null;
		Digester digester = createStartDigester();

        InputSource inputSource = null;
        InputStream inputStream = null;
        File file = null;
        try {
            file = new File("E:/myDoc/project/CUP/src/com/cup/sample/digester/digesterSimple.xml");;
            inputStream = new FileInputStream(file);
            inputSource = new InputSource(file.toURI().toURL().toString());
        } catch (Exception e) {
           log.error("get digester config file error!");
        }
		
        try {
        	obj= new DataSources();
            inputSource.setByteStream(inputStream);
            digester.push(obj);
            digester.parse(inputSource);
        } catch (Exception e) {
            log.error("digester config file error!");
            obj = null;
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                // Ignore
            }
        }
        return obj;
	}
	
	 protected Digester createStartDigester() {
	        long t1=System.currentTimeMillis();
	        // Initialize the digester
	        Digester digester = new Digester();

	        //
//	        digester.addObjectCreate("data-sources/data-source",
//	                                 "com.cup.sample.digester.bean.DataSource",
//	                                 "className");
//	        digester.addSetProperties("data-sources/data-source");
//	        digester.addSetNext("data-sources/data-source",
//	                            "addDataSource",
//	                            "com.cup.sample.digester.bean.DataSource");

	        //为演示rule 和ruleset添加的代码，其功能与上相同
	        //可以自定义rule，其实现有的addObjectCreate功能等就是添加已有的rule
	        digester.addRuleSet(new DataSourceRuleSet("data-sources"));
	        digester.addRule("data-sources/data-source", new SetNextRule("addDataSource", "com.cup.sample.digester.bean.DataSource"));
	        
	        long t2=System.currentTimeMillis();
	        if (log.isDebugEnabled()) {
	            log.debug("Digester created " + ( t2-t1 ));
	        }
	        return (digester);

	    }

}
