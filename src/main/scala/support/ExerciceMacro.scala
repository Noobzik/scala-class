package support

import scala.reflect.macros.blackbox

object ExerciceMacro {
  def apply(
             c: blackbox.Context
           )(
             testName: c.Tree
           )(
             testFun: c.Tree
           )(
             suite: c.Tree
           ): c.Tree = {
    import c.universe._

    val pos = testFun.pos
    val code =
      if (pos != null && pos.source != null)
        pos.source.content.mkString
      else ""

    val (start, end) = testFun match {
      case Block(_, expr) if expr.pos != null && expr.pos.line > 0 =>
        (pos.line, expr.pos.line)
      case _ if pos != null && pos.line > 0 =>
        (pos.line, pos.line)
      case _ =>
        (0, 0)
    }

    q"""
      $suite.testExercice($testName)($testFun)(
        new support.TestContext($code, $start, $end)
      )
    """
  }
}