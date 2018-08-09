package xyz.yooniks.namemc.command;

import static xyz.yooniks.namemc.helper.MessageHelper.colored;

import java.io.InputStream;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.io.IOUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.yooniks.namemc.config.RewardConfig;
import xyz.yooniks.namemc.reward.RewardManager;

public class NameMCCommand implements CommandExecutor {

  private final ResponseReader responseReader = new ResponseReader();
  private final ExecutorService executorService = Executors.newSingleThreadExecutor();

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

    //i have never used "future" so if someone has solution for that i am waiting for pull request :D
    this.executorService.submit(() -> {
      final String response = this.responseReader.read(player.getUniqueId());

      if (response.contains("true")) {
        this.rewardManager.giveReward(player);
      } else {
        player.sendMessage(colored(RewardConfig.GIVE_REWARD$MESSAGES$HAVE_NEVER_GIVEN));
      }
    });
    return true;
  }

  static class ResponseReader {

    String read(UUID uuid) {
      try {
        final URL url = new URL(RewardConfig.NAMEMC$URL + uuid.toString());
        final InputStream inputStream = url.openStream();
        return IOUtils.toString(inputStream);
      }
      catch (Exception exception) {
        return "false";
      }
    }

  }

}
