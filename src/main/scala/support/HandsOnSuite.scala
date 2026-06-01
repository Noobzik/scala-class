// HandsOnScala.scala
/*package support

import scala.concurrent.duration._
import scala.concurrent.{Await, Awaitable}
import scala.language.experimental.macros

import org.scalatest.{Args, SucceededStatus}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.exceptions.{TestFailedException, TestPendingException}
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.Matcher
import org.scalatest.matchers.should.Matchers
import org.scalatest.time.{Millis, Seconds, Span}
*/
package support

import org.scalatest._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.exceptions.{TestFailedException, TestPendingException}
import org.scalatest.matchers.Matcher
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatest.matchers.should.Matchers

import org.scalatest.{Args, SucceededStatus}
import scala.concurrent.duration._
import scala.concurrent.{Await, Awaitable}
import scala.language.experimental.macros
import scala.language.postfixOps

trait HandsOnSuite extends AnyFunSpec with Matchers with ScalaFutures {
  implicit val suite: HandsOnSuite = this
  implicit val defaultPatience: PatienceConfig =
    PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))
//  implicit val defaultPatience = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))
  val timeout: Duration = 1 second
  val state: State = State.load()

  def await[T](awaitable: Awaitable[T]): T = Await.result(awaitable, timeout)

  def DeleteMeToContinue(message: String) = throw new PauseException(message)

  def __ : Matcher[Any] = throw new TestPendingException

  def section = describe _

  def exercice(testName: String)(testFun: Unit)(implicit suite: HandsOnSuite): Unit = macro ExerciceMacro.apply

  def testExercice(testName: String)(testFun: => Unit)(ctx: TestContext): Unit = {
//    println(s"[DEBUG] Registering test: $testName")  // add this
//    println(s"[CTX] $testName: lines ${ctx.startLine}-${ctx.endLine}, code length=${ctx.code.length}")
    it(testName) {
//      println(s"[DEBUG] Running test: $testName")     // add this
      try {
        testFun
      } catch {
        case e: PauseException       => throw MyException.pause(suite, ctx, e)
        case e: TestPendingException => throw MyException.pending(suite, ctx, e)
        case e: NotImplementedError  => throw MyException.notImplemented(suite, ctx, e)
        case e: TestFailedException  => throw MyException.failed(suite, ctx, e)
        case e: Throwable            => throw MyException.unknown(suite, ctx, e)
      }
    }
  }

  override def run(testName: Option[String], args: Args) = {
//    CustomStopper.reset()
    super.run(testName, args)
  }

  protected override def runTest(testName: String, args: Args) = {
    if (!CustomStopper.hasFailed) {
      super.runTest(
        testName,
        args.copy(
          reporter = new CustomReporter(args.reporter),
          stopper = CustomStopper
        )
      )
    } else {
      SucceededStatus
    }
  }
}