package com.tydic.sso.Util;

import java.util.Properties;
import org.apache.commons.lang.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: houdc
 * Date: 2010-5-24
 * Time: 20:54:16
 * To change this template use File | Settings | File Templates.
 */

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