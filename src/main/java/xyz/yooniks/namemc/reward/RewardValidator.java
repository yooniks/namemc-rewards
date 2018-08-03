package xyz.yooniks.namemc.reward;

import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.UUID;

public interface RewardValidator {

  boolean validate(UUID uuid);

  ImmutableSet<UUID> getGivenRewards();

  void addGivenRewards(List<UUID> uuids);

  void addGivenReward(UUID uuid);
}
