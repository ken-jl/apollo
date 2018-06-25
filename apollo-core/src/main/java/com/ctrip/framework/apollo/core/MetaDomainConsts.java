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

    domains.put(Env.DEV_A,
                    env.getProperty("dev_a_meta", prop.getProperty("dev_a.meta", DEFAULT_META_URL)));
    domains.put(Env.DEV_B,
                    env.getProperty("dev_b_meta", prop.getProperty("dev_b.meta", DEFAULT_META_URL)));
    domains.put(Env.TEST_A,
                    env.getProperty("test_a_meta", prop.getProperty("test_a.meta", DEFAULT_META_URL)));
    domains.put(Env.TEST_B,
                    env.getProperty("test_b_meta", prop.getProperty("test_b.meta", DEFAULT_META_URL)));
    domains.put(Env.TEST_C,
                    env.getProperty("test_c_meta", prop.getProperty("test_c.meta", DEFAULT_META_URL)));
    domains.put(Env.STAGING,
                    env.getProperty("staging_meta", prop.getProperty("staging.meta", DEFAULT_META_URL)));
    domains.put(Env.STAGING_MIRROR,
                    env.getProperty("staging_mirror_meta", prop.getProperty("staging_mirror.meta", DEFAULT_META_URL)));
    domains.put(Env.PROD,
                    env.getProperty("prod_meta", prop.getProperty("prod.meta", DEFAULT_META_URL)));

  }

  public static String getDomain(Env env) {
    return String.valueOf(domains.get(env));
  }
}
