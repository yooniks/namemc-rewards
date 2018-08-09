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
import xyz.yooniks.namemc.reward.basic.CommandReward;
import xyz.yooniks.namemc.reward.basic.ItemReward;
import xyz.yooniks.namemc.reward.impl.RewardManagerImpl;

public final class NameMCRewardsPlugin extends JavaPlugin {

  private RewardManager rewardManager;

  @Override
  public void onEnable() {
    final boolean mkdirs = this.getDataFolder().mkdirs();

    this.rewardManager = new RewardManagerImpl();

    RewardConfig.GIVE_REWARD$REWARD_COMMANDS.forEach(
        command -> this.rewardManager.addReward(
            new CommandReward(command)));

    RewardConfig.GIVE_REWARD$REWARD_ITEMS.forEach(
        itemToParse -> this.rewardManager.addReward(
            new ItemReward(ItemParserHelper.parseItem(itemToParse))));

    ConfigHelper.create(new File(this.getDataFolder(), "config.yml"), RewardConfig.class);
    ConfigHelper.create(new File(this.getDataFolder(), "given_rewards.yml"), RewardListConfig.class);

    this.rewardManager.getRewardValidator().addGivenRewards(RewardListConfig.REWARD_LIST.stream()
        .map(UUID::fromString)
        .collect(Collectors.toList())
    );

    this.getServer().getScheduler().runTaskTimerAsynchronously(this, () -> {
      this.getLogger().info("Saving all rewards..");
      RewardListConfig.saveConfig();
      this.getLogger().info("Saving completed!");
    }, 0L, 20L * 180);

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
