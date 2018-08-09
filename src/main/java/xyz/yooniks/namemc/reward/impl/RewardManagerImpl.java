package xyz.yooniks.namemc.reward.impl;

import static xyz.yooniks.namemc.helper.MessageHelper.colored;

import com.google.common.collect.ImmutableSet;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.entity.Player;
import xyz.yooniks.namemc.config.RewardConfig;
import xyz.yooniks.namemc.reward.RewardManager;
import xyz.yooniks.namemc.reward.RewardValidator;
import xyz.yooniks.namemc.reward.basic.Reward;

public class RewardManagerImpl implements RewardManager {

  private final RewardValidator rewardValidator = new RewardValidatorImpl();

  private final Set<Reward> rewards = new HashSet<>();

  @Override
  public void giveReward(Player player) {
    this.rewardValidator.addGivenReward(player.getUniqueId());
    this.rewards.forEach(reward -> reward.give(player));

    player.sendMessage(colored(RewardConfig.GIVE_REWARD$MESSAGES$SUCCESS));
  }

  @Override
  public RewardValidator getRewardValidator() {
    return this.rewardValidator;
  }

  @Override
  public ImmutableSet<Reward> getRewards() {
    return ImmutableSet.copyOf(this.rewards);
  }

  @Override
  public void addReward(Reward reward) {
    this.rewards.add(reward);
  }

}
