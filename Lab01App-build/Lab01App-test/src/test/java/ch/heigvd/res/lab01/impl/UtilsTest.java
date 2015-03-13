package ch.heigvd.res.lab01.impl;

import java.util.logging.Logger;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Olivier Liechti
 */
public class UtilsTest {

  private static final Logger LOG = Logger.getLogger(UtilsTest.class.getName());

  @Test
  public void itShouldBePossibleToGetANewLineOnWindows() {
    String lines = "hello\r\nworld\r\n";
    String[] r1 = Utils.getNextLine(lines);
    String[] e1 = {"hello\r\n", "world\r\n"};
    assertArrayEquals(e1, r1);
    String[] r2 = Utils.getNextLine(r1[1]);
    String[] e2 = {"world\r\n", ""};
    assertArrayEquals(e2, r2);
  }

  @Test
  public void itShouldBePossibleToGetANewLineOnMacOS9() {
    String lines = "hello\rworld\r";
    String[] r1 = Utils.getNextLine(lines);
    String[] e1 = {"hello\r", "world\r"};
    
    assertArrayEquals(e1, r1);
    String[] r2 = Utils.getNextLine(r1[1]);
    String[] e2 = {"world\r", ""};

    System.out.println("Obtenu:");
    System.out.println(r2[0] + " | " + r2[1]);
    System.out.println("Attendu:");
    System.out.println(e2[0] + " | " + e2[1]);
    
    assertArrayEquals(e2, r2);
    
    
  }

  @Test
  public void itShouldBePossibleToGetANewLineOnUnix() {
    String lines = "hello\nworld\n";
    String[] r1 = Utils.getNextLine(lines);
    String[] e1 = {"hello\n", "world\n"};
    assertArrayEquals(e1, r1);
    String[] r2 = Utils.getNextLine(r1[1]);
    String[] e2 = {"world\n", ""};
    assertArrayEquals(e2, r2);
  }

  @Test
  public void itShouldReturnALineOnlyIfThereIsANewLineCharacterAtTheEnd() {
    String line = "this is a line without a new line character at the end";
    String[] r = Utils.getNextLine(line);
    String[] e = {"", line};
    assertArrayEquals(e, r);
  }

}
