package slogo.compiler.command;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import slogo.compiler.Comment;

class CommandTest {
  //private Turtle myModel;

  @Test
  void testGetClassName() {
    Comment c = new Comment("test comment");
    assertEquals("Comment", c.getClass().getSimpleName());
  }

}