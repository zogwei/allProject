package com.tydic.sso.Util;

import java.io.InputStream;
import java.util.Properties;
/**
 * Created by IntelliJ IDEA.
 * User: houdc
 * Date: 2010-5-24
 * Time: 20:55:00
 * To change this template use File | Settings | File Templates.
 */

public class PropertiesUtils
{
  public static Properties loadResource(String pathFile)
    throws Exception
  {
    Properties props = new Properties();
    InputStream is = PropertiesUtils.class.getClassLoader().getResourceAsStream(pathFile);
    try
    {
      props.load(is);
    }
    finally {
      if (is != null) {
        is.close();
      }
    }

    return props;
  }
}
