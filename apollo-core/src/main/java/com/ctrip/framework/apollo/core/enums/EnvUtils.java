package com.ctrip.framework.apollo.core.enums;

import com.ctrip.framework.apollo.core.utils.StringUtils;

public final class EnvUtils {
  
  public static Env transformEnv(String envName) {
    if (StringUtils.isBlank(envName)) {
      return null;
    }
    switch (envName.trim().toUpperCase()) {
      case "LPT":
        return Env.LPT;
      case "FAT":
      case "FWS":
        return Env.FAT;
      case "UAT":
        return Env.UAT;
      case "PRO":
     // case "PROD": //just in case
        return Env.PRO;
      case "DEV":
        return Env.DEV;
      case "LOCAL":
        return Env.LOCAL;
      case "DEFAULT":
        return Env.DEFAULT;

      case "DEV_A":
        return Env.DEV_A;
      case "DEV_B":
        return Env.DEV_B;
      case "TEST_A":
        return Env.TEST_A;
      case "TEST_B":
        return Env.TEST_B;
      case "TEST_C":
        return Env.TEST_C;
      case "STAGING":
        return Env.STAGING;
      case "STAGING_MIRROR":
        return Env.STAGING_MIRROR;
      case "PROD":
        return Env.PROD;

      default:
        return null;
    }
  }
}
