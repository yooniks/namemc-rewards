package xyz.yooniks.namemc.command;

import static xyz.yooniks.namemc.helper.MessageHelper.colored;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;
import javax.net.ssl.HttpsURLConnection;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.yooniks.namemc.NameMCRewardsPlugin;
import xyz.yooniks.namemc.config.RewardConfig;
import xyz.yooniks.namemc.reward.RewardManager;

public class NameMCCommand implements CommandExecutor {

  private final ResponseReader responseReader = new ResponseReader();

  private final RewardManager rewardManager;

  public NameMCCommand(RewardManager rewardManager) {
    this.rewardManager = rewardManager;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("only player");
      return true;
    }
    final Player player = ((Player) sender);
    if (!this.rewardManager.getRewardValidator().validate(player.getUniqueId())) {
      player.sendMessage(colored(RewardConfig.GIVE_REWARD$MESSAGES$ERROR));
      return true;
    }

    player.sendMessage(RewardConfig.NAMEMC$RESPONSE_REQUEST_MESSAGE);

    Bukkit.getScheduler().runTaskAsynchronously(NameMCRewardsPlugin.getPlugin(NameMCRewardsPlugin.class),
        () -> {
      final String response;
      try {
        response = this.responseReader.read(player.getUniqueId());
      }
      catch (IOException ex) {
        player.sendMessage(colored("&cCannot read url content! " + ex.getMessage()));
        return;
      }

      if (response.contains("true")) {
        this.rewardManager.giveReward(player);
      } else {
        player.sendMessage(colored(RewardConfig.GIVE_REWARD$MESSAGES$HAVE_NEVER_GIVEN));
      }
    });
    return true;
  }

  static class ResponseReader {

    String read(UUID uuid) throws IOException {
      final URL url = new URL(RewardConfig.NAMEMC$URL + uuid.toString());
      final HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
      con.setRequestMethod("GET");

      return new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
    }
  }

}
