package xyz.yooniks.namemc.reward.reward;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemReward implements Reward {

  private final ItemStack itemStack;

  public ItemReward(ItemStack itemStack) {
    this.itemStack = itemStack;
  }

  @Override
  public void give(Player player) {
    if (player.getInventory().firstEmpty() == -1) {
      player.getWorld().dropItemNaturally(player.getLocation(), this.itemStack);
      return;
    }
    player.getInventory().addItem(this.itemStack);
  }

}
