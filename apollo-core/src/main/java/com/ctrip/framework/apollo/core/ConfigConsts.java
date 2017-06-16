package com.ctrip.framework.apollo.core;

import java.io.File;

public interface ConfigConsts {
  String NAMESPACE_APPLICATION = "application";
  String CLUSTER_NAME_DEFAULT = "default";
  String CLUSTER_NAMESPACE_SEPARATOR = "+";
  String APOLLO_CLUSTER_KEY = "apollo.cluster";
  String CONFIG_FILE_CONTENT_KEY = "content";
  String NO_APPID_PLACEHOLDER = "ApolloNoAppIdPlaceHolder";
  String PROPERTIES_DIR = System.getProperty("user.home") + File.separator + ".yunnex";
  String CONFIG_FILE_NAME = "boot";
}
