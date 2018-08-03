package xyz.yooniks.namemc.helper;

import static xyz.yooniks.namemc.helper.MessageHelper.colored;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public final class ItemParserHelper {
  //https://github.com/FunnyGuilds/FunnyGuilds/blob/master/src/main/java/net/dzikoysk/funnyguilds/util/Parser.java

  private ItemParserHelper() {
  }

  public static ItemStack parseItem(String string) {
    String[] split = string.split(" ");
    String[] typeSplit = split[1].split(":");
    String subtype = typeSplit.length > 1 ? typeSplit[1] : "0";

    Material mat = parseMaterial(typeSplit[0]);

    int stack;
    int data;

    try {
      stack = Integer.parseInt(split[0]);
      data = Integer.parseInt(subtype);
    } catch (NumberFormatException e) {
      //FunnyLogger.parser("Unknown size: " + split[0]);
      stack = 1;
      data = 0;
    }

    ItemBuilder item = new ItemBuilder(mat, stack, data);

    for (int i = 2; i < split.length; i++) {
      String str = split[i];

      if (str.contains("name")) {
        String[] splitName = str.split(":");
        item.setName(StringUtils
            .replace(colored(String.join(":", Arrays.copyOfRange(splitName, 1, splitName.length))),
                "_", " "), true);
      } else if (str.contains("lore")) {
        String[] splitLore = str.split(":");
        String loreArgs = String.join(":", Arrays.copyOfRange(splitLore, 1, splitLore.length));
        String[] lores = loreArgs.split("#");
        List<String> lore = new ArrayList<>();

        for (String s : lores) {
          lore.add(StringUtils.replace(StringUtils.replace(colored(s), "_", " "), "{HASH}", "#"));
        }

        item.setLore(lore);
      } else if (str.contains("enchant")) {
        String[] parse = str.split(":");
        String enchantName = parse[1];
        int level;

        try {
          level = Integer.parseInt(parse[2]);
        } catch (NumberFormatException e) {
          //FunnyLogger.parser("Unknown enchant level: " + split[2]);
          level = 1;
        }

        Enchantment enchant = Enchantment.getByName(enchantName.toUpperCase());
        if (enchant == null) {
          //FunnyLogger.parser("Unknown enchant: " + parse[1]);
        }

        item.addEnchant(enchant, level);
      } else if (str.contains("skullowner")) {
        if (item.getMeta() instanceof SkullMeta) {
          ((SkullMeta) item.getMeta()).setOwner(str.split(":")[1]);
          item.refreshMeta();
        }
      } else if (str.contains("armorcolor")) {
        if (item.getMeta() instanceof LeatherArmorMeta) {
          String[] color = str.split(":")[1].split("_");

          try {
            ((LeatherArmorMeta) item.getMeta()).setColor(Color
                .fromRGB(Integer.parseInt(color[0]), Integer.parseInt(color[1]),
                    Integer.parseInt(color[2])));
            item.refreshMeta();
          } catch (NumberFormatException e) {
            //FunnyLogger.parser("Unknown armor color: " + str.split(":")[1]);
          }
        }
      }

    }
    return item.getItem();
  }

    public static Material parseMaterial(String string) {
      if (string == null) {
        return Material.AIR;
      }
      final Material material = Material.valueOf(string);
      return material == null ? Material.AIR : material;
    }

}
