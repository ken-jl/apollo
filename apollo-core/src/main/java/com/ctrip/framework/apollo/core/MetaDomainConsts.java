package com.ctrip.framework.apollo.core;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.ctrip.framework.apollo.core.enums.Env;

/**
 * The meta domain will load the meta server from System environment first, if not exist, will load
 * from apollo-env.properties. If neither exists, will load the default meta url.
 * 
 * Currently, apollo supports local/dev/fat/uat/lpt/pro environments.
 */
public class MetaDomainConsts {

	private static final Logger logger = LoggerFactory.getLogger(MetaDomainConsts.class);
	
  private static Map<Env, Object> domains = new HashMap<>();

  public static final String DEFAULT_META_URL = "http://config.local";

  static {
    Properties prop = new Properties();
    Properties env = System.getProperties();
    /*prop = ResourceUtils.readConfigFile("apollo-env.properties", prop);
    Properties env = System.getProperties();
    domains.put(Env.LOCAL,
        env.getProperty("local_meta", prop.getProperty("local.meta", DEFAULT_META_URL)));
    domains.put(Env.DEV,
        env.getProperty("dev_meta", prop.getProperty("dev.meta", DEFAULT_META_URL)));
    domains.put(Env.FAT,
        env.getProperty("fat_meta", prop.getProperty("fat.meta", DEFAULT_META_URL)));
    domains.put(Env.UAT,
        env.getProperty("uat_meta", prop.getProperty("uat.meta", DEFAULT_META_URL)));
    domains.put(Env.LPT,
        env.getProperty("lpt_meta", prop.getProperty("lpt.meta", DEFAULT_META_URL)));
    domains.put(Env.PRO,
        env.getProperty("pro_meta", prop.getProperty("pro.meta", DEFAULT_META_URL)));*/
    try {
		prop = getBootCfg();
		domains.put(Env.DEV,
		        env.getProperty("dev_meta", prop.getProperty("config.server.url", DEFAULT_META_URL)));
	} catch (IOException e) {
		logger.error("加载启动配置文件异常", e);
	}
  }

  public static String getDomain(Env env) {
    return String.valueOf(domains.get(env));
  }
  
  private static Properties getBootCfg() throws IOException {
      String bootPath = System.getProperty("user.home") + File.separator + ".yunnex" + File.separator + "boot";
      File bootFile = new File(bootPath);
      if (!bootFile.exists() || !bootFile.isFile()) {
          logger.error("boot文件${user.home}/.yunnex/boot 不存在，请先配置该文件。");
      }
      FileSystemResource resource = new FileSystemResource(bootFile);
      return PropertiesLoaderUtils.loadProperties(resource);
  }
}
