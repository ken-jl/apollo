package com.ctrip.framework.apollo.core.enums;

import com.google.common.base.Preconditions;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
public enum Env{
  LOCAL, DEV, FWS, FAT, UAT, LPT, PRO, TOOLS, DEFAULT,DEV_A,DEV_B,TEST_A,TEST_B,TEST_C,STAGING,STAGING_MIRROR,PROD;

  public static Env fromString(String env) {
    Env environment = EnvUtils.transformEnv(env);
    Preconditions.checkArgument(environment != null, String.format("Env %s is invalid", env));
    return environment;
  }
}
