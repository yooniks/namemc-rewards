import static org.junit.Assert.assertTrue;

import java.util.UUID;
import org.junit.Test;
import xyz.yooniks.namemc.reward.RewardValidator;
import xyz.yooniks.namemc.reward.impl.RewardValidatorImpl;

public class RewardValidatorTests {

  @Test
  public void validateTest() {
    final UUID uuidToValidate = UUID.randomUUID();

    final RewardValidator validator = new RewardValidatorImpl();
    validator.addGivenReward(uuidToValidate);

    assertTrue(validator.validate(uuidToValidate));
  }

}
