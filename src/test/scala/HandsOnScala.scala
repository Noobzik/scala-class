import support.CustomStopper
import org.scalatest.{Args, Suites}

class HandsOnScala extends Suites(
  new exercices.e00_start,
  new exercices.e01_syntaxe,
  new exercices.e02_objet,
  new exercices.e03_collections,
  new exercices.e04_types_fonctionnels,
  new exercices.e05_fonction_dordre_superieur,
  new exercices.e06_currying,
  new exercices.e07_implicit,
  new exercices.end
) {
  override def run(testName: Option[String], args: Args) = {
    CustomStopper.reset()  // reset ONCE before all suites
    super.run(testName, args)
  }
}