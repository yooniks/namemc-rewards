package xyz.yooniks.namemc.helper;

import static xyz.yooniks.namemc.helper.MessageHelper.colored;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public final class ItemBuilder {
  //https://github.com/FunnyGuilds/FunnyGuilds/blob/master/src/main/java/net/dzikoysk/funnyguilds/util/ItemBuilder.java

  private final ItemStack itemStack;
  private final ItemMeta itemMeta;

  public ItemBuilder(final Material material) {
    this.itemStack = new ItemStack(material);
    this.itemMeta = itemStack.getItemMeta();
  }

  public ItemBuilder(final Material material, int stack) {
    this.itemStack = new ItemStack(material, stack);
    this.itemMeta = itemStack.getItemMeta();
  }

  public ItemBuilder(final Material material, int stack, int data) {
    this.itemStack = new ItemStack(material, stack, (short) data);
    this.itemMeta = itemStack.getItemMeta();
  }

  public ItemBuilder(final ItemStack itemStack) {
    this.itemStack = itemStack;
    this.itemMeta = itemStack.getItemMeta();
  }

  public void refreshMeta() {
    this.itemStack.setItemMeta(itemMeta);
  }

  public ItemBuilder setName(String name) {
    this.setName(name, true);
    return this;
  }

  public ItemBuilder setName(String name, boolean color) {
    this.itemMeta.setDisplayName(color ? colored(name) : name);
    this.refreshMeta();

    return this;
  }

  public ItemBuilder setLore(List<String> lore) {

    this.itemMeta.setLore(colored(lore));
    this.refreshMeta();

    return this;
  }

  public ItemBuilder setLore(String... lore) {
    return setLore(Arrays.asList(lore));
  }

  public ItemBuilder addEnchant(Enchantment enchant, int level) {
    this.itemMeta.addEnchant(enchant, level, true);
    this.refreshMeta();

    return this;
  }

  public ItemBuilder setFlag(ItemFlag flag) {
    this.itemMeta.addItemFlags(flag);
    this.refreshMeta();

    return this;
  }

  public ItemStack getItem() {
    return this.itemStack;
  }

  public ItemMeta getMeta() {
    return this.itemMeta;
  }
}