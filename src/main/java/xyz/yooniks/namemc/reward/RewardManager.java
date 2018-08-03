package xyz.yooniks.namemc.reward;

import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.UUID;
import org.bukkit.entity.Player;
import xyz.yooniks.namemc.reward.reward.Reward;

public interface RewardManager {

  void giveReward(Player player);

  void addReward(Reward reward);

  boolean status(UUID uuid);

  void addGivenRewards(List<UUID> uuids);

  ImmutableSet<Reward> getRewards();

}
