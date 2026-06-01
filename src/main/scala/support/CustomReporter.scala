package support

import org.scalatest.Reporter
import org.scalatest.events._

class CustomReporter(other: Reporter) extends Reporter {
  override def apply(event: Event): Unit = event match {
    case e: TestFailed =>
      e.throwable match {
        case Some(err: MyTestPauseException)      => sendInfo(e, Formatter.formatInfo(e.suiteName, e.testName, Some(err), pending = true))
        case Some(err: MyTestPendingException)    => sendInfo(e, Formatter.formatInfo(e.suiteName, e.testName, Some(err), pending = true))
        case Some(err: MyNotImplementedException) => sendInfo(e, Formatter.formatInfo(e.suiteName, e.testName, Some(err), pending = true))
        case Some(err: MyTestFailedException)     => sendInfo(e, Formatter.formatInfo(e.suiteName, e.testName, Some(err), pending = false))
        case Some(err: MyException)               => sendInfo(e, Formatter.formatInfo(e.suiteName, e.testName, Some(err), pending = false))
        case Some(_)                              => other(event)
        case None                                 => sendInfo(e, Formatter.formatInfo(e.suiteName, e.testName, None, pending = false))
      }

    case e: TestPending =>
      sendInfo(e, Formatter.formatInfo(e.suiteName, e.testName, None, pending = true))

    case _ =>
      other(event)
  }

//  private def sendInfo(event: Event, info: String): Unit = {
//    // Print directly to stdout — bypasses all ScalaTest/sbt reporter filtering
//    println(info)
//
//    event match {
//      case e: TestFailed =>
//        other(TestPending(
//          e.ordinal.next,
//          e.suiteName,
//          e.suiteId,
//          e.suiteClassName,
//          e.testName,
//          e.testText,
//          e.recordedEvents,
//          e.duration,
//          Some(MotionToSuppress),
//          e.location,
//          e.payload,
//          e.threadName,
//          e.timeStamp
//        ))
//      case _ =>
//        other(event)
//    }
//    CustomStopper.requestStop()
//  }
private def sendInfo(event: Event, info: String): Unit = {
  println(info)
  CustomStopper.requestStop()
}
}