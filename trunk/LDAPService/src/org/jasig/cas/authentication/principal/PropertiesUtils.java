package org.jasig.cas.authentication.principal;

import java.io.InputStream;
import java.util.Properties;

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