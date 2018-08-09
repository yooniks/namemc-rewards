package xyz.yooniks.namemc.reward;

import com.google.common.collect.ImmutableSet;
import org.bukkit.entity.Player;
import xyz.yooniks.namemc.reward.basic.Reward;

public interface RewardManager {

  void giveReward(Player player);

  void addReward(Reward reward);

  RewardValidator getRewardValidator();

  ImmutableSet<Reward> getRewards();

}
