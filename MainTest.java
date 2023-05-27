import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MainTest {

  @Test
  public void testV1() {
    Assertions.assertEquals(3, Main.findV1("abcabcbb"));
    Assertions.assertEquals(1, Main.findV1("bbbbb"));
    Assertions.assertEquals(3, Main.findV1("pwwkew"));
    Assertions.assertEquals(8, Main.findV1("abcdefgh"));
    Assertions.assertEquals(3, Main.findV1("dvdf"));
    Assertions.assertEquals(5, Main.findV1("anviaj"));
    Assertions.assertEquals(1, Main.findV1(" "));
    Assertions.assertEquals(2, Main.findV1("abba"));
  }

  @Test
  public void testV2() {
    Assertions.assertEquals(3, Main.findV2("abcabcbb"));
    Assertions.assertEquals(1, Main.findV2("bbbbb"));
    Assertions.assertEquals(3, Main.findV2("pwwkew"));
    Assertions.assertEquals(8, Main.findV2("abcdefgh"));
    Assertions.assertEquals(3, Main.findV2("dvdf"));
    Assertions.assertEquals(5, Main.findV2("anviaj"));
    Assertions.assertEquals(1, Main.findV2(" "));
    Assertions.assertEquals(2, Main.findV2("abba"));
  }
}
