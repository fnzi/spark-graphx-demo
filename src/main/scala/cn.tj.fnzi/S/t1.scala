package cn.tj.fnzi.S

import scala.util.Random

object t1 {

  def main(args: Array[String]): Unit = {
//    createArr(10).foreach(println)

    println(createArr(10))
  }


  def createArr(n: Int): Array[Int] = {
    val arr = new Array[Int](n)
    val rand = new Random()
    for(ele <- arr)
      yield rand.nextInt(n)

  }
}
