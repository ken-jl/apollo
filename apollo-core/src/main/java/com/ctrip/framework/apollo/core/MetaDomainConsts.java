package com.ctrip.framework.apollo.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ctrip.framework.apollo.core.enums.Env;
import com.ctrip.framework.apollo.core.utils.ResourceUtils;

/**
 * The meta domain will load the meta server from System environment first, if not exist, will load
 * from apollo-env.properties. If neither exists, will load the default meta url.
 * 
 * Currently, apollo supports local/dev/fat/uat/lpt/pro environments.
 */
public class MetaDomainConsts {

  private static final String CONFIG_SERVER_URL = "config.server.url";

  private static final Logger logger = LoggerFactory.getLogger(MetaDomainConsts.class);
	
  private static Map<Env, Object> domains = new HashMap<>();

  public static final String DEFAULT_META_URL = "http://config.local";

  static {
    Properties prop = new Properties();
    Properties env = System.getProperties();
    prop = ResourceUtils.readConfigFile(ConfigConsts.CONFIG_FILE_NAME, prop);

    domains.put(Env.LOCAL,
        env.getProperty("local_meta", prop.getProperty(CONFIG_SERVER_URL, DEFAULT_META_URL)));
    domains.put(Env.DEV,
        env.getProperty("dev_meta", prop.getProperty(CONFIG_SERVER_URL, DEFAULT_META_URL)));
    domains.put(Env.FAT,
        env.getProperty("fat_meta", prop.getProperty(CONFIG_SERVER_URL, DEFAULT_META_URL)));
    domains.put(Env.UAT,
        env.getProperty("uat_meta", prop.getProperty(CONFIG_SERVER_URL, DEFAULT_META_URL)));
    domains.put(Env.LPT,
        env.getProperty("lpt_meta", prop.getProperty(CONFIG_SERVER_URL, DEFAULT_META_URL)));
    domains.put(Env.PRO,
        env.getProperty("pro_meta", prop.getProperty(CONFIG_SERVER_URL, DEFAULT_META_URL)));
    domains.put(Env.DEFAULT,
        env.getProperty("default_meta", prop.getProperty(CONFIG_SERVER_URL, DEFAULT_META_URL)));
  }

  public static String getDomain(Env env) {
    return String.valueOf(domains.get(env));
  }
}
