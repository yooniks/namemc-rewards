package xyz.yooniks.namemc;

import java.io.File;
import java.util.UUID;
import java.util.stream.Collectors;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.yooniks.namemc.command.NameMCCommand;
import xyz.yooniks.namemc.config.RewardConfig;
import xyz.yooniks.namemc.config.RewardListConfig;
import xyz.yooniks.namemc.config.system.ConfigHelper;
import xyz.yooniks.namemc.helper.ItemParserHelper;
import xyz.yooniks.namemc.reward.RewardManager;
import xyz.yooniks.namemc.reward.impl.RewardManagerImpl;
import xyz.yooniks.namemc.reward.reward.CommandReward;
import xyz.yooniks.namemc.reward.reward.ItemReward;

public final class NameMCRewardsPlugin extends JavaPlugin {

  private RewardManager rewardManager;

  @Override
  public void onEnable() {
    final boolean mkdirs = this.getDataFolder().mkdirs();

    this.rewardManager = new RewardManagerImpl();

    RewardConfig.GIVE_REWARD$REWARD_COMMANDS.forEach(
        command -> this.rewardManager.addReward(new CommandReward(command)));

    RewardConfig.GIVE_REWARD$REWARD_ITEMS.forEach(
        itemToParse -> this.rewardManager.addReward(new ItemReward(ItemParserHelper.parseItem(itemToParse))));

    ConfigHelper.create(new File(this.getDataFolder(), "config.yml"), RewardConfig.class);
    ConfigHelper.create(new File(this.getDataFolder(), "given_rewards.yml"), RewardListConfig.class);

    this.rewardManager.addGivenRewards(RewardListConfig.REWARD_LIST.stream()
        .map(UUID::fromString)
        .collect(Collectors.toList())
    );

    this.getServer().getScheduler().runTaskTimerAsynchronously(this,
        RewardListConfig::saveConfig, 0L, 20L * 120);

    this.getCommand("namemc").setExecutor(new NameMCCommand(this.rewardManager));
  }

  @Override
  public void onDisable() {
    RewardListConfig.saveConfig();
  }

  public RewardManager getRewardManager() {
    return rewardManager;
  }

}
