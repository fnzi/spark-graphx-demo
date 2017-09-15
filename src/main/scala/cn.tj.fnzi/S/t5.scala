package cn.tj.fnzi.S

import scala.collection.mutable.ArrayBuffer

object t5 {
  def main (args: Array[String])= {
    val a=ArrayBuffer(1,2,2,23,3,4,5,6,3)
    val b=ArrayBuffer[Int]()
    b.++=(a.distinct)
    b.foreach(println)

    val name = "qwertyui";

    println(name.dropWhile(x => {println(x);x=='y'}))
    println(name.drop(2))
    println(name.dropRight(2))

  }
}
