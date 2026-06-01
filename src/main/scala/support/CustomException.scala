package support

import org.scalatest.exceptions.{TestFailedException, TestPendingException}

class PauseException(val message: String) extends RuntimeException

class MyException(val fileName: Option[String], val ctx: TestContext, val errors: Option[List[Int]], val message: Option[String], val cause: Throwable) extends Exception(message.getOrElse(""), cause) {}

class MyTestPauseException(fileName: Option[String], ctx: TestContext, errors: Option[List[Int]], message: Option[String], cause: Throwable) extends MyException(fileName, ctx, errors, message, cause) {}

class MyTestPendingException(fileName: Option[String], ctx: TestContext, errors: Option[List[Int]], message: Option[String], cause: Throwable) extends MyException(fileName, ctx, errors, message, cause) {}

class MyNotImplementedException(fileName: Option[String], ctx: TestContext, errors: Option[List[Int]], message: Option[String], cause: Throwable) extends MyException(fileName, ctx, errors, message, cause) {}

class MyTestFailedException(fileName: Option[String], ctx: TestContext, errors: Option[List[Int]], message: Option[String], cause: Throwable) extends MyException(fileName, ctx, errors, message, cause) {}

object MyException {
  private def exerciseFrame(stacks: List[StackTraceElement]): Option[StackTraceElement] =
    stacks.find(e => e.getClassName.startsWith("exercices.") && e.getMethodName.startsWith("$anonfun"))
      .orElse(stacks.find(_.getClassName.startsWith("exercices.")))

  private def exerciseFrames(stacks: List[StackTraceElement]): List[StackTraceElement] =
    stacks.filter(e => e.getClassName.startsWith("exercices.") && e.getMethodName.startsWith("$anonfun")) match {
      case Nil => stacks.filter(_.getClassName.startsWith("exercices."))
      case xs  => xs
    }

  private def nonEmpty[T](l: List[T]): Option[List[T]] =
    if (l.nonEmpty) Some(l) else None

  def pause(suite: HandsOnSuite, ctx: TestContext, e: PauseException): MyTestPauseException ={
    val stack = e.getStackTrace.find(e => e.getClassName.startsWith("exercices.") && e.getMethodName.startsWith("$anonfun"))
      .orElse(e.getStackTrace.find(_.getClassName.startsWith("exercices.")))
      .getOrElse(e.getStackTrace()(2))
    val location = getLocation(suite, stack)
    new MyTestPauseException(Some(location), ctx, None, Some(e.message), e)
  }

  def pending(suite: HandsOnSuite, ctx: TestContext, e: TestPendingException): MyTestPendingException = {
    val stack = e.getStackTrace.find(e => e.getClassName.startsWith("exercices.") && e.getMethodName.startsWith("$anonfun"))
      .orElse(e.getStackTrace.find(_.getClassName.startsWith("exercices.")))
      .getOrElse(e.getStackTrace()(2))
    val location = getLocation(suite, stack)
    val errors = List(stack.getLineNumber)
    new MyTestPendingException(Some(location), ctx, Some(errors), Some(Formatter.missingValue), e)
  }

  def notImplemented(suite: HandsOnSuite, ctx: TestContext, e: NotImplementedError): MyNotImplementedException = {
    val stacks = exerciseFrames(e.getStackTrace.toList)
    val target = stacks.lastOption
    val locationOpt = target.map(getLocation(suite, _))
    val errors = target.map(_.getLineNumber).toList
    new MyNotImplementedException(locationOpt, ctx, nonEmpty(errors), Some(Formatter.missingImplementation), e)
  }

  def failed(suite: HandsOnSuite, ctx: TestContext, e: TestFailedException): MyTestFailedException = {
    val locationOpt = e.failedCodeFileNameAndLineNumberString.map(getPackage(suite, _))
    val errors = e.failedCodeLineNumber.toList
    new MyTestFailedException(locationOpt, ctx, Some(errors), Option(e.getMessage), e)
  }

  def unknown(suite: HandsOnSuite, ctx: TestContext, e: Throwable): MyException = {
    val stacks = exerciseFrames(e.getStackTrace.toList)
    val target = stacks.lastOption
    val locationOpt = target.map(getLocation(suite, _))
    val errors = target.map(_.getLineNumber).toList
    new MyException(locationOpt, ctx, nonEmpty(errors), Some(e.toString), e)
  }
  private def getPackage(suite: HandsOnSuite, name: String): String =
    List("src", "test", "scala", suite.getClass.getPackage.getName, name).mkString(java.io.File.separator)

  private def getLocation(suite: HandsOnSuite, st: StackTraceElement): String =
    getPackage(suite, st.getFileName + ":" + st.getLineNumber)

  private def isSuiteClass(suite: HandsOnSuite, e: StackTraceElement): Boolean =
    e.getClassName.contains(suite.getClass.getName)


}