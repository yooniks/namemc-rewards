package xyz.yooniks.namemc.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import xyz.yooniks.namemc.config.system.ConfigExtender;
import xyz.yooniks.namemc.config.system.Configuration;

public class RewardConfig extends ConfigExtender {

  public RewardConfig(Configuration configuration) {
    super(configuration);
  }

  public static String GIVE_REWARD$MESSAGES$HAVE_NEVER_GIVEN = "&cYou have never given a like for this server on namemc.com!";
  public static String GIVE_REWARD$MESSAGES$ERROR = "&cYou have already given a rate for this server, you cannot get next reward!";
  public static String GIVE_REWARD$MESSAGES$SUCCESS = "&aThe server have just given you rewards! Thank you for the rate!";

  public static List<String> GIVE_REWARD$REWARD_ITEMS = new ArrayList<>(Arrays.asList("1 64", "2 1")); //item id | amount
  public static List<String> GIVE_REWARD$REWARD_COMMANDS = new ArrayList<>(
      Collections.singletonList("bc &7Gracz &a%name%&7 polubil nasz serwer na namemc (/namemc)!"));

  public static String NAMEMC$URL = "https://api.namemc.com/server/mylobby.pl/likes?profile=";
  public static String NAMEMC$RESPONSE_REQUEST_MESSAGE = "&aSending request to namemc and waiting to response..";

}
