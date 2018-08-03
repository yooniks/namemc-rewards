package xyz.yooniks.namemc.config.system;

import java.io.File;

public final class ConfigHelper {

  private ConfigHelper() {
  }

  public static Config create(File file, Class clazz) {
    return new Config(file, clazz);
  }

}
