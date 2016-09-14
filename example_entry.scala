/**
  * Created by User on 2016/09/14.
  */
package TutorialExamples

import Chisel._

// use command like
// sbt compile
// sbt "run ### --backend c --genHarness --compile --test --vcd --targetDir output"
// sbt "run ### --backend v --genHarness --targetDir output"
// sbt "run ### --backend dot --targetDir output"

object TutorialExamples {
  def main(args: Array[String]): Unit = {
    val tutArgs = args.slice(1, args.length)
    args(0) match {
      case "GCD" =>
        chiselMainTest(tutArgs, () => Module(new GCD())) {
          c => new GCDTests(c)
        }
      case "Adder" =>
        chiselMainTest(tutArgs, () => Module(new Adder(8))) {
          c => new AdderTests(c)
        }
      case "FullAdder" =>
        chiselMainTest(tutArgs, () => Module(new FullAdder())) {
          c => new FullAdderTests(c)
        }
    }
  }
}
