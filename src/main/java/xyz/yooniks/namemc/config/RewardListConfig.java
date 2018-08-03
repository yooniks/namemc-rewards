package xyz.yooniks.namemc.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bukkit.Bukkit;
import xyz.yooniks.namemc.config.system.ConfigExtender;
import xyz.yooniks.namemc.config.system.Configuration;

public class RewardListConfig extends ConfigExtender {

  public RewardListConfig(Configuration configuration) {
    super(configuration);
  }

  public static List<String> REWARD_LIST = new ArrayList<>(
      Collections.singletonList(Bukkit.getOfflinePlayer("yooniks_tendev").getUniqueId().toString()));

}
