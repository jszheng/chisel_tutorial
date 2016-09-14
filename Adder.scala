/**
  * Created by User on 2016/09/14.
  */
package TutorialExamples

import Chisel._


class Adder(val n: Int) extends Module {
  val io = new Bundle {
    val A = UInt(INPUT, n)
    val B = UInt(INPUT, n)
    val Cin = UInt(INPUT, 1)

    val Sum = UInt(OUTPUT, n)
    val Cout = UInt(OUTPUT, 1)
  }

  // create a vector of Full Adders
  val FAs = Vec(n, Module(new FullAdder()).io)
  val carry = Wire(Vec(n + 1, UInt(width = 1)))
  val sum = Wire(Vec(n, Bool()))

  carry(0) := io.Cin

  for (i <- 0 until n) {
    FAs(i).a := io.A(i)
    FAs(i).b := io.B(i)
    FAs(i).cin := carry(i)

    carry(i + 1) := FAs(i).cout
    sum(i) := FAs(i).sum.toBool()
  }

  io.Sum := sum.toBits.toUInt()
  io.Cout := carry(n)
}

class AdderTests(c: Adder) extends Tester(c) {
  for (t <- 0 until 4) {
    val rnd0 = rnd.nextInt(c.n)
    val rnd1 = rnd.nextInt(c.n)
    val rnd2 = rnd.nextInt(1)

    poke(c.io.A, rnd0)
    poke(c.io.B, rnd1)
    poke(c.io.Cin, rnd2)
    step(1)
    val rsum = UInt(rnd0 + rnd1 + rnd2, width = c.n + 1)
    expect(c.io.Sum, rsum(c.n - 1, 0).litValue())
    expect(c.io.Cout, rsum(c.n).litValue())
  }
}

/*
object AdderMain {
  def main(args: Array[String]): Unit = {
    val tutArgs = args.slice(1, args.length)
    chiselMainTest(tutArgs, () => Module(new Adder(8))) {
      c => new AdderTests(c)
    }
  }
}*/
