package xyz.yooniks.namemc.reward.basic;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandReward implements Reward {

  private final String command;

  public CommandReward(String command) {
    this.command = command;
  }

  @Override
  public void give(Player player) {
    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
        StringUtils.replace(this.command, "%name%", player.getName()));
  }

}
