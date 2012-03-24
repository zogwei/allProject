package org.jasig.cas.authentication.principal;

import java.util.Properties;
import org.apache.commons.lang.StringUtils;

public class SystemConfigProperties
{
  public static Properties systemConfigProperties;

  static
  {
    try
    {
      systemConfigProperties = PropertiesUtils.loadResource("system-config.properties");
    }
    catch (Exception localException) {
    }
  }

  public static String get(String key) {
    if (systemConfigProperties != null) {
      return systemConfigProperties.getProperty(key);
    }
    return null;
  }

  public static String get(String key, String defaultValue) {
    String value = get(key);
    return (StringUtils.isEmpty(value)) ? defaultValue : value;
  }
}