package xyz.yooniks.namemc.helper;

import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.ChatColor;

public final class MessageHelper {

  private MessageHelper() {
  }

  public static String colored(String text) {
    return ChatColor.translateAlternateColorCodes('&', text);
  }

  public static List<String> colored(List<String> text) {
    return text.stream()
        .map(MessageHelper::colored)
        .collect(Collectors.toList());
  }

}
