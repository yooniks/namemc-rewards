package xyz.yooniks.namemc.reward.impl;

import com.google.common.collect.ImmutableSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import xyz.yooniks.namemc.config.RewardListConfig;
import xyz.yooniks.namemc.reward.RewardValidator;

public class RewardValidatorImpl implements RewardValidator {

  private final Set<UUID> given = new HashSet<>();

  @Override
  public boolean validate(UUID uuid) {
    return !this.given.contains(uuid);
  }

  @Override
  public ImmutableSet<UUID> getGivenRewards() {
    return ImmutableSet.copyOf(this.given);
  }

  @Override
  public void addGivenReward(UUID uuid) {
    RewardListConfig.REWARD_LIST.add(uuid.toString());
    this.given.add(uuid);
  }

  @Override
  public void addGivenRewards(List<UUID> uuids) {
    this.given.addAll(uuids);
  }

}
