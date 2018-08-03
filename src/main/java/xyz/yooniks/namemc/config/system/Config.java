package xyz.yooniks.namemc.config.system;

import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config implements Configuration {

  private final File file;
  private final Class<?> clazz;

  public Config(File file, Class<?> clazz) {
    this.file = file;
    this.clazz = clazz;
    this.reload();
  }

  @Override
  public synchronized void reload() {
    File configFile = checkFile(this.file);
    try {
      YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
      this.parse(this.clazz, config);
      config.save(configFile);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public synchronized void save() {
    File configFile = checkFile(this.file);
    try {
      YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
      parseSave(this.clazz, config);
      config.save(configFile);
    } catch (Exception ex) {
      ex.printStackTrace();
    }

  }

}
