package support

import org.scalatest.Stopper

object CustomStopper extends Stopper {
  @volatile var oneTestFailed = false

  def stopRequested: Boolean = oneTestFailed

  def requestStop(): Unit = {
    oneTestFailed = true
  }

  def hasFailed: Boolean = oneTestFailed

  def reset(): Unit = {
    oneTestFailed = false
  }
}